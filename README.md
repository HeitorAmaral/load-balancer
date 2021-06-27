# Balanceador de carga em ambientes Cloud

### Passo-a-passo para executar a aplicação:
1. Possuir o Java JRE na versão 8 instalado. [Java Download.](https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR)
2. Possuir a variável de ambiente JAVA_HOME e PATH atualizadas conforme a instalação do Java 8. [Como adicionar Java ao PATH.](https://mauriciogeneroso.medium.com/configurando-java-4-como-configurar-as-vari%C3%A1veis-java-home-path-e-classpath-no-windows-46040950638f)
3. Realizar o Download do arquivo "load-balancer.jar" localizado na aba de releases do repositório. Utilizar sempre o ultimo release. [Releases](https://github.com/HeitorAmaral/load-balancer/releases)
4. Após o download, abrir o Prompt de comando na pasta em que está alocado o arquivo com a extensão .jar e executar o seguinte comando:
```sh
java -jar load-balancer.jar
```
- Após isso, o programa iniciará a execução e deverá ser informado o local do arquivo input.txt, contendo as informações para seu funcionamento.

### Passo-a-passo para executar os testes implementados com JUnit:
1. Possuir o Java JRE na versão 8 instalado. [Java Download.](https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR)
2. Possuir a variável de ambiente JAVA_HOME e PATH atualizadas conforme a instalação do Java 8. [Como adicionar Java ao PATH.](https://mauriciogeneroso.medium.com/configurando-java-4-como-configurar-as-vari%C3%A1veis-java-home-path-e-classpath-no-windows-46040950638f)
3. Realizar o Download do arquivo "load-balancer-tests.jar" localizado na aba de releases do repositório. Utilizar sempre o ultimo release. [Releases](https://github.com/HeitorAmaral/load-balancer/releases)
4. Após o download, abrir o Prompt de comando na pasta em que está alocado o arquivo com a extensão .jar e executar o seguinte comando:
```sh
java -jar load-balancer-tests.jar
```
- Após isso, os testes iniciarão e ao final o programa informará os testes concluídos.
