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

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o primeiro operando: ");
        double oper1 = scanner.nextDouble();

        System.out.print("Digite o segundo operando: ");
        double oper2 = scanner.nextDouble();

        System.out.println("Escolha a operação:");
        System.out.println("1 - Soma");
        System.out.println("2 - Subtração");
        System.out.println("3 - Divisão");
        System.out.println("4 - Multiplicação");
        System.out.print("Digite a opção: ");
        int operacao = scanner.nextInt();

        String result = "";

        try 
        {
            Socket clientSocket = new Socket("192.168.0.11", 9090);

            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());
    
            socketSaidaServer.writeBytes(operacao + "\n"); // envia código da operação (1 a 4)
            socketSaidaServer.writeBytes(oper1 + "\n");     // envia primeiro operando
            socketSaidaServer.writeBytes(oper2 + "\n");     // envia segundo operando
            socketSaidaServer.flush();

            BufferedReader messageFromServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            result = messageFromServer.readLine(); // lê o resultado

            System.out.println("Resultado = " + result);
S
            clientSocket.close();

        } 
        
        catch (IOException e) 
        {
            System.out.println("Erro ao conectar com o servidor: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }
}
