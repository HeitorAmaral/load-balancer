package br.com.hamaral.loadbalancer;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import br.com.hamaral.loadbalancer.model.Server;
import br.com.hamaral.loadbalancer.model.User;
import br.com.hamaral.loadbalancer.service.FileService;
import br.com.hamaral.loadbalancer.service.GenericService;
import br.com.hamaral.loadbalancer.service.ServerService;

public class UnitTest {

	private Utils utils = new Utils();

	/**
	 * Método para testar se o método de escrita de arquivos está funcionando e
	 * gravando corretamente.
	 */
	@Test
	public void testDeveEscreverArquivo() {
		System.out.println("Executando testDeveEscreverArquivo...");
		utils = new Utils();
		FileService fileService = new FileService();
		boolean result = fileService.writeFile(utils.getMockDataString(), utils.getFilePath());
		assertTrue(result);
		utils.validate(result);
		new File(utils.getFilePath() + "output.txt").delete();
	}

	/**
	 * Método para testar se o método de leitura de arquivo está processando e
	 * contabilizando corretamente os dados.
	 */
	@Test
	public void testLeituraDeveContabilizarOitoLinhas() {
		System.out.println("Executando testLeituraDeveContabilizarOitoLinhas...");
		utils = new Utils();

		FileService fileService = new FileService();
		fileService.writeFile(utils.getMockDataString(), utils.getFilePath());
		List<String> dataReaded = fileService.readFile(utils.getFilePath() + "output.txt");
		boolean result = false;
		if (8 == dataReaded.size()) {
			result = true;
		}
		assertEquals(8, dataReaded.size());
		utils.validate(result);
		new File(utils.getFilePath() + "output.txt").delete();
	}

	/**
	 * Método para testar a validação da atribuição da lista de dados recuperada do
	 * arquivo.
	 */
	@Test
	public void testValidacaoDataReadedRetornaVerdadeiro() {
		System.out.println("Executando testValidacaoDataReadedRetornaVerdadeiro...");
		utils = new Utils();
		List<Integer> mockListData = utils.getMockDataInteger();
		GenericService genericService = new GenericService();
		boolean result = genericService.validateDataSize(mockListData.size());
		assertTrue(result);
		utils.validate(result);
	}

	/**
	 * Método para testar a validação da atribuição do parâmetro ttask.
	 */
	@Test
	public void testValidacaoTtaskRetornaVerdadeiro() {
		System.out.println("Executando testValidacaoTtaskRetornaVerdadeiro...");
		utils = new Utils();
		List<Integer> mockListData = utils.getMockDataInteger();
		GenericService genericService = new GenericService();
		boolean result = genericService.validateTtask(mockListData.get(0));
		assertTrue(result);
		utils.validate(result);
	}

	/**
	 * Método para testar a validação da atribuição do parâmetro umax.
	 */
	@Test
	public void testValidacaoUmaxRetornaVerdadeiro() {
		System.out.println("Executando testValidacaoUmaxRetornaVerdadeiro...");
		utils = new Utils();
		List<Integer> mockListData = utils.getMockDataInteger();
		GenericService genericService = new GenericService();
		boolean result = genericService.validateUmax(mockListData.get(1));
		assertTrue(result);
		utils.validate(result);
	}

	/**
	 * Método para testar se a distribuição de Usuários em Servidores está
	 * funcionando corretamente, alocando todos os usuários nos servidores.
	 */
	@Test
	public void testDistribuicaoDeUsuariosDeveAlocarTodosOsUsuariosRequeridos() {
		System.out.println("Executando testDistribuicaoDeUsuariosDeveAlocarTodosOsUsuariosRequeridos...");
		utils = new Utils();
		List<Server> listServers = utils.getMockDataServer();
		List<User> listUsersPerTick = utils.getMockDataListUsersPerTick();
		int umax = 2;

		ServerService serverService = new ServerService();

		int counter = 0;
		for (Server server : serverService.distributeUsersInServers(listUsersPerTick, listServers, umax)) {
			for (User user : server.getUsersAllocated()) {
				if (listUsersPerTick.contains(user)) {
					counter++;
				}
			}
		}

		if (counter < listUsersPerTick.size()) {
			fail("Erro. Faltaram usuários a serem alocados.");
			System.out.println("Erro\n-------------------------");
		} else {
			System.out.println("Sucesso\n-------------------------");
		}
	}

	/**
	 * Método para testar se a remoção de recursos (Usuários e Servidores) está
	 * sendo processada corretamente.
	 */
	@Test
	public void testRemocaoDeRecursosFinalizadosNaoDeveAcontecer() {
		System.out.println("Executando testRemocaoDeRecursosFinalizadosNaoDeveAcontecer...");
		utils = new Utils();
		ServerService serverService = new ServerService();
		int tickId = 2;

		List<Server> listServers = utils.getMockDataServer();
		boolean result = false;
		if (listServers.equals(serverService.deleteInactiveResources(tickId, listServers))) {
			result = true;
		}
		assertEquals(listServers, serverService.deleteInactiveResources(tickId, listServers));
		utils.validate(result);
	}

	/**
	 * Método para testar se o processamento completo está escrevendo e processando
	 * corretamente.
	 */
	@Test
	public void testProcessDeveEscreverArquivoRetornarVerdadeiro() {
		System.out.println("Executando testProcessDeveEscreverArquivoRetornarVerdadeiro...");
		utils = new Utils();
		GenericService genericService = new GenericService();
		FileService fileService = new FileService();
		fileService.writeFile(utils.getMockDataString(), utils.getFilePath());
		boolean result = genericService.process(utils.getFilePath() + "output.txt");
		assertTrue(result);
		utils.validate(result);
		new File(utils.getFilePath() + "output.txt").delete();
	}

}
