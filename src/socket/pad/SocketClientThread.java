package socket.pad;
import java.io.*;
import java.net.*;

public class SocketClientThread extends Thread{
	//Flag que indica quando se deve terminar a execu��o
	private static boolean done = false;
	public static void main(String[] args) {
		try {
			//Tenta estabelecer a conex�o com o servidor
			Socket conexao = new Socket ("127.0.0.1", 2222);
			
			//Depois de iniciar a conex�o, deve-se obter os objetos
			PrintStream saida = new PrintStream(conexao.getOutputStream());
			
			//Envia antes de tudo o nome do usuario
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Entre com seu Nome: ");
			String meuNome = teclado.readLine();
			saida.println(meuNome);
			
			//Uma vez que tudo est� pronto, antes de iniciar o lopp principal,
			//Executar a thread de recep��o de mensagens
			Thread t = new SocketClientThread(conexao);
			t.start();
			
			//Loop principal: obtendo uma linha do digitada no teclado
			//E enviando para o servidor
			String linha;
			while(true) {
				//ler a linha digitada no teclado
				System.out.println(meuNome + "> ");
				linha = teclado.readLine();
				//antes de enviar, verifica se a conex�o n�o foi fechada
				if(done) {break;}
				
				//envia para o servidor
				saida.println(linha);
			}
			
		}catch (Exception e) {
			System.out.println("IOException: " + e);
		}
	}

	private Socket conexao;
	//Construtor
	public SocketClientThread(Socket s) {
		conexao = s;
	}
	
	//Execu��o da Thread
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			String linha;
			while(true) {
				//Pega o que o servidor enviou
				linha = entrada.readLine();
				if(linha == null) {
					System.out.println("Conex�o Encerrada!");
					break;
				}
				//Caso a linha n�o seja nula, deve-se imprimi-la
				System.out.println();
				System.out.println(linha);
				System.out.println("...> ");
			}
		}catch (Exception e) {
			System.out.println("IOException: " + e);
		}
		//sinaliza para o main que a conex�o encerrou
		done = true;
	}
	
}
