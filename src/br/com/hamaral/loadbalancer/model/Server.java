package br.com.hamaral.loadbalancer.model;

import java.util.List;

import br.com.hamaral.loadbalancer.service.GenericService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável por definir o modelo Server.
 * 
 * @author Heitor Augusto Melecardi do Amaral
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Server {

	private Integer id;

	private List<User> usersAllocated;

	private Integer ticksOnline;

	/**
	 * Método para alocar um Usuário no Servidor
	 * 
	 * @param user(User): Usuário a ser alocado no Servidor.
	 */
	public void allocateUser(User user) {
		this.usersAllocated.add(user);
	}

	/**
	 * Método para alocar Usuários no Servidor.
	 * 
	 * @param users(List<User>): Usuários a serem alocados no Servidor.
	 */
	public void allocateUsers(List<User> users) {
		this.usersAllocated.addAll(users);
	}

	/**
	 * Método para contar quantos Ticks o Servidor está ativo.
	 */
	public void addTicksOnline() {
		this.ticksOnline++;
		GenericService.serversCost++;
	}
}
