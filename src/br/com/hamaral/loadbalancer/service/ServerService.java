package br.com.hamaral.loadbalancer.service;

import java.util.ArrayList;
import java.util.List;

import br.com.hamaral.loadbalancer.model.Server;
import br.com.hamaral.loadbalancer.model.User;

/**
 * 
 * Classe responsável por realizar os processamentos atrelados ao modelo Server.
 * 
 * @author Heitor Augusto Melecardi do Amaral
 *
 */
public class ServerService {

	/**
	 * 
	 * Método responsável por remover recursos (Usuários/Servidores) não utilizados.
	 * 
	 * @param tickId  (int): Identificador único do Tick que está sendo processado.
	 * @param servers (List<Server>): Lista de Servidores a serem verificados.
	 * @return List<Server>: Lista com os Servidores que ainda continuarão ativos.
	 */
	public List<Server> deleteInactiveResources(Integer tickId, List<Server> servers) {
		List<User> usersToDeallocate = new ArrayList<User>();
		List<Server> serversToRemove = new ArrayList<Server>();

		for (Server server : servers) {
			server.addTicksOnline();

			for (User user : server.getUsersAllocated()) {
				if (user.getEndAt().equals(tickId)) {
					usersToDeallocate.add(user);
				}
			}
			if (usersToDeallocate.size() > 0) {
				server.getUsersAllocated().removeAll(usersToDeallocate);
				usersToDeallocate.clear();
			}

			if (server.getUsersAllocated().size() == 0) {
				serversToRemove.add(server);
			}
		}

		if (serversToRemove.size() > 0) {
			servers.removeAll(serversToRemove);
		}
		return servers;
	}

	/**
	 * 
	 * Método responsável por distribuir e alocar os Usuários nos Servidores
	 * existentes, ou criar novas VM's.
	 * 
	 * @param usersToAllocate (List<User>): Lista de Usuários que deverão ser
	 *                        alocados em servidores.
	 * @param servers         (List<Server>): Lista de Servidores ativos no momento.
	 * @param umax            (int): Quantidade máxima de Usuários por Servidor.
	 * @return List<Server>: Lista de Servidores, Novos ou modificados, de acordo
	 *         com a alocação de novos Usuários.
	 */
	public List<Server> distributeUsersInServers(List<User> usersToAllocate, List<Server> servers, int umax) {
		int usersToAllocateQtd = 0;
		usersToAllocateQtd = usersToAllocate.size();

		boolean again = true;
		while (again) {
			for (Server server : servers) {
				if (usersToAllocateQtd > 0) {
					int availableToAllocateOnServer = (umax - server.getUsersAllocated().size());

					if (availableToAllocateOnServer > 0) {
						if (availableToAllocateOnServer < usersToAllocateQtd) {
							for (int j = 0; j < availableToAllocateOnServer; j++) {
								User user = new User();
								user = usersToAllocate.get(j);
								server.allocateUser(user);
								usersToAllocate.remove(j);
								usersToAllocateQtd -= 1;
							}
						} else {
							server.allocateUsers(usersToAllocate);
							usersToAllocateQtd = 0;
						}
					}
				}
			}

			if (usersToAllocateQtd > 0) {
				again = true;
			} else {
				again = false;
			}
			if (again) {
				servers.add(new Server(servers.size() + 1, new ArrayList<User>(), 1));
			}
		}
		return servers;
	}

}
