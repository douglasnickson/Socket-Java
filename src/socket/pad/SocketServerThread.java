package socket.pad;
import java.io.*;
import java.net.*;
import java.util.*;

public class SocketServerThread extends Thread {
	
	private static Vector clientes;
	private Socket conexao;
	private String meuNome;
	
	public SocketServerThread(Socket s) {
		conexao = s;
	}

	public static void main(String[] args) {
		//Instancia o vetor de clientes conectados
		clientes = new Vector();
		
		try {
			//Criando socket que fica escultando na porta 2222
			ServerSocket s = new ServerSocket(2222);
			//Loop Principal
			while(true) {
				//Aguarda os clientes se conectarem
				System.out.println("Esperando alguém se conectar...");
				Socket conexao = s.accept();
				System.out.println("Conectou!");
				
				// Cria uma nova Thread para tratar essa conexão
				Thread t = new SocketServerThread(conexao);
				t.start();
			}
		}catch (Exception e) {
			System.out.println("IOException: " + e);
		}

	}

	//Execução da Thread
	public void run() {
		try {
			//Objetos que controlam o fluxo de execução
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			PrintStream saida = new PrintStream(conexao.getOutputStream());
			
			//Primeiramente Espera pelo nome do Cliente
			meuNome = entrada.readLine();
			
			//Agora verifica se a String e valida
			if(meuNome == null) {return;}
			
			//Quando o cliente se conecta, e adicionado no vetor
			clientes.add(saida);
			
			String linha = entrada.readLine();
			while(linha != null && !(linha.trim().equals(""))) {
				//Envia a linha para todos os clientes conectados
				sendToAll(saida, " disse: ", linha);
				//espera por uma nova linha
				linha = entrada.readLine();
			}
			
			//Quando o cliente enviar uma linha em branco, retira-se
			//Fluxo de saida do vetor de clientes e fecha-se a conexão.
			sendToAll(saida, " saiu ", "do chat!");
			clientes.remove(saida);
			conexao.close();
			
		}catch(Exception e){
			System.out.println("IOException: " + e);
		}
	}
	
	//Metodo que envia mensagens para todos, menos para o dono
	public void sendToAll(PrintStream saida, String acao, String linha) throws IOException{
		Enumeration e = clientes.elements();
		while(e.hasMoreElements()) {
			//obtem o fluxo de saida de um dos clientes
			PrintStream chat = (PrintStream) e.nextElement();
			//envia para todos menos para o dono
			if(chat != saida) {chat.println(meuNome + acao + linha);}
		}
	}
}
