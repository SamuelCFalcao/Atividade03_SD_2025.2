import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class CalculadoraClientSocket 
{

    public static void main(String[] args) 
    {

        // Scanner para ler entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Solicita os operandos ao usuário
        System.out.print("Digite o primeiro operando: ");
        double oper1 = scanner.nextDouble();

        System.out.print("Digite o segundo operando: ");
        double oper2 = scanner.nextDouble();

        // Exibe menu de operações
        System.out.println("Escolha a operação:");
        System.out.println("1 - Soma");
        System.out.println("2 - Subtração");
        System.out.println("3 - Divisão");
        System.out.println("4 - Multiplicação");
        System.out.print("Digite a opção: ");
        int operacao = scanner.nextInt();

        // Variável para armazenar o resultado
        String result = "";

        try 
        {
            // Conexão com o servidor usando IP e porta
            Socket clientSocket = new Socket("192.168.0.11", 9090);

            // Canal de saída de dados para o servidor
            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

            // Envia os dados para o servidor
            socketSaidaServer.writeBytes(operacao + "\n"); // envia código da operação (1 a 4)
            socketSaidaServer.writeBytes(oper1 + "\n");     // envia primeiro operando
            socketSaidaServer.writeBytes(oper2 + "\n");     // envia segundo operando
            socketSaidaServer.flush();

            // Lê a resposta do servidor
            BufferedReader messageFromServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            result = messageFromServer.readLine(); // lê o resultado

            // Exibe o resultado no console
            System.out.println("Resultado = " + result);

            // Fecha a conexão com o servidor
            clientSocket.close();

        } 
        
        catch (IOException e) 
        {
            // Trata exceções de rede
            System.out.println("Erro ao conectar com o servidor: " + e.getMessage());
            e.printStackTrace();
        }

        // Fecha o scanner
        scanner.close();
    }
}
