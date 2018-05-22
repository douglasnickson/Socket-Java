package socket.pad;
import java.io.*;
import java.net.*;

public class SocketServer {

	public static void main(String[] args) {
		try {
			//Cria um Socket que fica escultando na porta 2000
			ServerSocket s = new ServerSocket(2000);
			//Loop Principal
			while(true) {
				//Aguarda alguem se conectar no servidor
				System.out.println("Esperando algu�m se conectar...");
				Socket conexao = s.accept();
				System.out.println("Conectou!");
				
				//Obtendo os objetos de controle de fluxo de comunica��o
				BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
				PrintStream saida = new PrintStream(conexao.getOutputStream());
				
				//Verifica se a linha recebida n�o e nula
				//Caso seja, a conex�o e encerrada
				String linha = entrada.readLine();
				while(linha != null && !(linha.trim().equals(""))) {
					saida.println("Servidor: " + linha);
					//Espera por uma nova linha
					linha = entrada.readLine();
				}
				//Se o cliente enviou uma linha em branco a conex�o e encerrada
				conexao.close();
			}
		}catch (Exception e) {
			System.out.println("IOException: " + e);
		}

	}

}
