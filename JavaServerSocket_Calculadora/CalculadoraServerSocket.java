import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket {

	public static void main(String[] args) {
		
		ServerSocket welcomeSocket;                 
		DataOutputStream socketOutput;             
	    BufferedReader socketEntrada;               
	    Calculadora calc = new Calculadora();     
	    
		try 
		{
			welcomeSocket = new ServerSocket(9090); 
		    int i = 0; 

	        System.out.println("Servidor no ar");

	        while(true) { 
	            Socket connectionSocket = welcomeSocket.accept(); 
	            i++;
	            System.out.println("Nova conexão");

	            socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	            String operacao = socketEntrada.readLine(); 
	            String oper1 = socketEntrada.readLine();     
	            String oper2 = socketEntrada.readLine();     
	            
	            
	            int op = Integer.parseInt(operacao);
	            double valor1 = Double.parseDouble(oper1);
	            double valor2 = Double.parseDouble(oper2);
	            
	            
	            String result = "";

	            if (op == 1) 
				{
	            	result = "" + calc.soma(valor1, valor2);
	            } 
				
				else if (op == 2) 
				{
	            	result = "" + calc.subtracao(valor1, valor2);
	            } 
				
				else if (op == 3) 
				{
	            	// Verifica divisão por zero
	            	if (valor2 == 0) 
					{
	            		result = "Erro: divisão por zero";
	            	} 
					
					else 
					{
	            		result = "" + calc.divisao(valor1, valor2);
	            	}
	            } 
				
				else if (op == 4) 
				{
	            	result = "" + calc.multiplicacao(valor1, valor2);
	            } 
				
				else 
				{
	            	result = "Erro: operação inválida";
	            }

	            socketOutput = new DataOutputStream(connectionSocket.getOutputStream());     	
	            socketOutput.writeBytes(result + '\n');
	            System.out.println(result);	           
	            socketOutput.flush();
	            socketOutput.close();
	            connectionSocket.close();
	        }

		} 
		
		catch (IOException e)
		{
			e.printStackTrace();
		} 
	}
}