package br.com.hamaral.loadbalancer;

import br.com.hamaral.loadbalancer.service.GenericService;

/**
 * Classe principal da aplicação. Possuí o método para executar o script.
 * 
 * @author Heitor Augusto Melecardi do Amaral
 * 
 */
public class Main {

	private static GenericService genericService = new GenericService();

	/**
	 * Método principal da aplicação. É executado ao iniciar a aplicação.
	 * 
	 * @param args(String[]): Argumentos utilizados para inicar a aplicação.
	 */
	public static void main(String[] args) {
		init();
	}

	/**
	 * Método inicializador. Chama a função de processamento.
	 */
	private static void init() {
		System.out.println("| Balanceador de cargas em ambientes Cloud |");
		genericService.process("");
	}
}
