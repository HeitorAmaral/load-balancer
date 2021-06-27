package br.com.hamaral.loadbalancer.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * Classe responsável realizar o processamento de arquivos.
 * 
 * @author Heitor Augusto Melecardi do Amaral
 *
 */
public class FileService {

	/**
	 * 
	 * Método que realiza a leitura de arquivos. Realiza validações iniciais
	 * referente aos tipos de dados informados.
	 * 
	 * @param filePath (String): Diretório no sistema do arquivo a ser processado.
	 * @return List<String>: Dados do arquivo processado.
	 */
	public List<String> readFile(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			Scanner scannerRead = new Scanner(new FileReader(filePath));
			while (scannerRead.hasNextLine()) {
				String line = scannerRead.nextLine();
				if (line.length() > 0) {
					if (Pattern.matches("^\\d+$", line.trim())) {
						lines.add(line);
					} else if (lines.size() <= 1) {
						throw new Error("O valor \"" + line
								+ "\", informado para as variáveis ttask ou umax está inválido. Devem ser informados somente valores numéricos.");
					} else {
						System.err.println("A linha com a informação \"" + line
								+ "\" está sendo desconsiderada por não atender aos requisítos de dados. Devem ser informados somente valores numéricos.");
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println(
					"Ocorreu um erro durante a busca do arquivo. Tente inserir novamente o diretório do arquivo.");
			return new ArrayList<String>();
		}
		return lines;
	}

	/**
	 * 
	 * Método que realiza a criação e escrita de arquivos.
	 * 
	 * @param lines    (List<String>): Textos a serem gravados no arquivo.
	 * @param filePath (String): Diretório no sistema aonde o arquivo será gravado.
	 */
	public boolean writeFile(List<String> lines, String filePath) {
		String path = filePath.replace("input.txt", "");
		try {
			if (!filePath.contains("output.txt")) {
				path = path.concat("output.txt");
			}
			FileWriter fileWriter = new FileWriter(path);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			for (String line : lines) {
				printWriter.print(line);
			}
			fileWriter.close();
			System.out.printf("O arquivo output.txt foi gravado com sucesso no seguinte diretório: " + path + "\n");
			return true;
		} catch (IOException e) {
			throw new Error("Ocorreram erros durante a gravação do arquivo output.txt. Verifique se o diretório " + path
					+ " ainda está válido no sistema e tente novamente.");
		}
	}
}
