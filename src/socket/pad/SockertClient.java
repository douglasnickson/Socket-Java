package socket.pad;
// ClienteDeEco.java
import java.io.*;
import java.net.*;

public class SockertClient {
	public static void main(String args[]) {
	try {
		Socket conexao = new Socket("127.0.0.1", 2000);
		BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
		PrintStream saida = new PrintStream(conexao.getOutputStream());
		String linha;
		// objetos que permitem a leitura do teclado
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		// loop principal
		while (true) {
			// l� a linha do teclado
			System.out.print("Cliente > ");
			linha = teclado.readLine();
			// envia para o servidor
			saida.println(linha);
			// pega o que o servidor enviou
			linha = entrada.readLine();
			// Verifica se � linha v�lida, pois se for null a conex�o
			// foi interrompida. Se ocorrer isso, termina a execu��o.
			if (linha == null) {
			 System.out.println("Conex�o encerrada!");
			 break;
			}
			// se a linha n�o for nula, deve-se imprimi-la no v�deo
			System.out.println(linha);
		}
	}catch (IOException e) {
	   // caso ocorra alguma excess�o de E/S, mostre qual foi.
	   System.out.println("IOException: " + e);
	}
	}
}