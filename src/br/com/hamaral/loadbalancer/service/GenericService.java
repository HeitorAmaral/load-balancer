package br.com.hamaral.loadbalancer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.hamaral.loadbalancer.model.Server;
import br.com.hamaral.loadbalancer.model.Tick;
import br.com.hamaral.loadbalancer.model.User;

/**
 * 
 * Classe responsável por realizar processamentos genéricos da aplicação.
 * 
 * @author Heitor Augusto Melecardi do Amaral
 *
 */
public class GenericService {

	private ServerService serverService = new ServerService();
	private FileService fileService = new FileService();
	public static Integer serversCost = 0;

	/**
	 * 
	 * Método que solicita o diretório no sistema do arquivo que será processado.
	 * 
	 * @return String: Diretório no sistema do arquivo a ser processado.
	 */
	public String getInputLocation() {
		System.out.println(
				"Para iniciar o script, informe o local do arquivo input.txt no sistema (Exemplo: C:\\input.txt) e aperte a tecla ENTER: ");
		@SuppressWarnings("resource")
		Scanner scannerIn = new Scanner(System.in);
		return scannerIn.nextLine();
	}

	/**
	 * Método responsável por validar o tamanho da lista de dados do arquivo.
	 * 
	 * @param listDataSize(Integer) Tamanho da lista recuperada do arquivo.
	 * @return boolean: Retorna um boolean, informando se foi realizada com sucesso.
	 */
	public boolean validateDataSize(Integer listDataSize) {
		if (listDataSize < 3) {
			throw new Error(
					"É necessário que seja informado no mínimo 3 linhas no arquivo input.txt (Deve-se informar o ttask, o umax, e o número de novos usuários a cada tick.)");
		} else {
			return true;
		}
	}

	/**
	 * 
	 * Método responsável por validar se o ttask está dentro dos limites.
	 * 
	 * @param ttask: Número referente a primeira linha do arquivo.
	 * @return boolean: Retorna um boolean, informando se foi realizada com sucesso
	 */
	public boolean validateTtask(Integer ttask) {
		if (ttask < 1 || ttask > 10) {
			throw new Error("O valor de ttask deve respeitar o seguinte limite: 1 ≤ ttask ≤ 10");
		} else {
			return true;
		}
	}

	/**
	 * 
	 * Método responsável por validar se o umax está dentro dos limites.
	 * 
	 * @param umax (Integer): Número referente a segunda linha do arquivo.
	 * @return boolean: Retorna um boolean, informando se foi realizada com sucesso.
	 */
	public boolean validateUmax(Integer umax) {
		if (umax < 1 || umax > 10) {
			throw new Error("O valor de umax deve respeitar o seguinte limite: 1 ≤ umax ≤ 10");
		} else {
			return true;
		}
	}

	/**
	 * Método que realiza o processamento dos dados do arquivo input.txt, e após as
	 * distribuições, grava o resultado no arquivo output.txt.
	 * 
	 * @return
	 */
	public boolean process(String filePath) {
		serversCost = 0;
		List<String> listData = new ArrayList<String>();

		while (listData.size() == 0) {
			if (filePath == "") {
				filePath = getInputLocation();
			}
			listData = fileService.readFile(filePath);
			if (listData.size() == 0) {
				filePath = "";
			}
		}

		validateDataSize(listData.size());

		int ttask = Integer.parseInt(listData.get(0));
		validateTtask(ttask);

		int umax = Integer.parseInt(listData.get(1));
		validateUmax(umax);

		List<Integer> listNewUsers = new ArrayList<Integer>();
		for (int i = 2; i < listData.size(); i++) {
			listNewUsers.add(Integer.parseInt(listData.get(i)));
		}

		// System.out.println("Valor de ttask = " + ttask);
		// System.out.println("Valor de umax = " + umax);

		int ticks = listNewUsers.size() + ttask;
		int itemsToComplete = (ticks - listNewUsers.size());

		for (int i = 0; i < itemsToComplete; i++) {
			listNewUsers.add(0);
		}

		List<Tick> listTicks = new ArrayList<Tick>();
		List<Server> listServers = new ArrayList<Server>();
		List<User> listUsers = new ArrayList<User>();
		List<String> listLinesToOutput = new ArrayList<String>();

		for (int i = 0; i < ticks; i++) {
			int tickId = (i + 1);
			listServers = serverService.deleteInactiveResources(tickId, listServers);

			int endTick = 0;

			int newUsers = listNewUsers.get(i);
			if (newUsers == 0) {
				if (i > 0) {
					endTick = listTicks.get(i - 1).getEndAt();
				}
			} else {
				endTick = (tickId + ttask);
			}

			List<User> listUsersPerTick = new ArrayList<User>();
			for (int j = 0; j < newUsers; j++) {
				User user = new User(listUsers.size() + 1, tickId, endTick);
				listUsers.add(user);
				listUsersPerTick.add(user);
			}

			// System.out.println(tickIdentifier + " - " + listUsersPerTick.size()
			// + " novos usuários contabilizados com finalização prevista no Tick = " +
			// endTick);
			listTicks.add(new Tick(tickId, newUsers, endTick));

			listServers = serverService.distributeUsersInServers(listUsersPerTick, listServers, umax);

			String output = "";
			for (int j = 0; j < listServers.size(); j++) {
				output = output.concat("" + listServers.get(j).getUsersAllocated().size());
				if ((j + 1) < listServers.size()) {
					output = output.concat(",");
				}
			}
			if ((i + 1) < ticks) {
				output = output.concat("\n");
			}

			listLinesToOutput.add(output);
			// System.out.println(output);
		}

		listLinesToOutput.add("0");
		// System.out.println("0");

		listLinesToOutput.add("\n" + serversCost);
		// System.out.println(serversCost);

		return fileService.writeFile(listLinesToOutput, filePath);

	}

}
