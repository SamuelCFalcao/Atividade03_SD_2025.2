import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculadoraServerSocket 
{

    public static void main(String[] args) 
	{
        ServerSocket welcomeSocket;
        DataOutputStream socketOutput;     
        BufferedReader socketEntrada;
        Calculadora calc = new Calculadora(); // Instância da classe que contém as operações

        try 
		{
            // Cria o socket do servidor ouvindo a porta 9090
            welcomeSocket = new ServerSocket(9090);
            int i = 0; // Contador de conexões

            System.out.println("Servidor no ar, aguardando conexões...");

            while (true) ]
			{
                // Aceita a conexão de um cliente
                Socket connectionSocket = welcomeSocket.accept();
                i++;
                System.out.println("Nova conexão recebida [" + i + "]");

                // Lê os dados enviados pelo cliente
                socketEntrada = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String operacaoStr = socketEntrada.readLine(); // código da operação
                String oper1Str = socketEntrada.readLine();     // primeiro operando
                String oper2Str = socketEntrada.readLine();     // segundo operando

                // Converte os valores recebidos
                int operacao = Integer.parseInt(operacaoStr);
                double oper1 = Double.parseDouble(oper1Str);
                double oper2 = Double.parseDouble(oper2Str);

                String result;

                // Realiza a operação de acordo com o código recebido
                switch (operacao) 
				{
                    case 1:
                        result = "" + calc.soma(oper1, oper2);
                        break;
                    case 2:
                        result = "" + calc.subtracao(oper1, oper2);
                        break;
                    case 3:
                        // Verifica se o divisor é zero
                        if (oper2 == 0) 
						{
                            result = "Erro: Divisão por zero";
                        } 
						
						else 
						{
                            result = "" + calc.divisao(oper1, oper2);
                        }
                        break;
                    case 4:
                        result = "" + calc.multiplicacao(oper1, oper2);
                        break;
                    default:
                        result = "Erro: Operação inválida";
                        break;
                }

                // Envia o resultado de volta ao cliente
                socketOutput = new DataOutputStream(connectionSocket.getOutputStream());
                socketOutput.writeBytes(result + '\n');
                System.out.println("Resultado enviado: " + result);

                // Fecha a conexão com o cliente
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