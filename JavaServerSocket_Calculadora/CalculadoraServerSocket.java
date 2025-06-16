import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket {

	public static void main(String[] args) {
		
		ServerSocket welcomeSocket;                 // Socket do servidor para aceitar conexões
		DataOutputStream socketOutput;              // Saída de dados para o cliente
	    BufferedReader socketEntrada;               // Entrada de dados do cliente
	    Calculadora calc = new Calculadora();       // Instância da calculadora com operações
	    
		try {
			welcomeSocket = new ServerSocket(9090); // Servidor escutando na porta 9090
		    int i = 0; // Contador de clientes

	        System.out.println("Servidor no ar");

	        while(true) { 
	        	// Aceita nova conexão de cliente
	            Socket connectionSocket = welcomeSocket.accept(); 
	            i++;
	            System.out.println("Nova conexão");

	            // Lê os dados enviados pelo cliente
	            socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	            String operacao = socketEntrada.readLine(); // código da operação (1-4)
	            String oper1 = socketEntrada.readLine();     // primeiro operando
	            String oper2 = socketEntrada.readLine();     // segundo operando
	            
	            // Converte os valores recebidos
	            int op = Integer.parseInt(operacao);
	            double valor1 = Double.parseDouble(oper1);
	            double valor2 = Double.parseDouble(oper2);
	            
	            // Inicializa resultado
	            String result = "";

	            // Verifica qual operação realizar
	            if (op == 1) {
	            	result = "" + calc.soma(valor1, valor2);
	            } else if (op == 2) {
	            	result = "" + calc.subtracao(valor1, valor2);
	            } else if (op == 3) {
	            	// Verifica divisão por zero
	            	if (valor2 == 0) {
	            		result = "Erro: divisão por zero";
	            	} else {
	            		result = "" + calc.divisao(valor1, valor2);
	            	}
	            } else if (op == 4) {
	            	result = "" + calc.multiplicacao(valor1, valor2);
	            } else {
	            	result = "Erro: operação inválida";
	            }

	            // Envia o resultado ao cliente
	            socketOutput = new DataOutputStream(connectionSocket.getOutputStream());     	
	            socketOutput.writeBytes(result + '\n');
	            System.out.println(result);	           
	            socketOutput.flush();
	            socketOutput.close();
	            connectionSocket.close();
	        }

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}