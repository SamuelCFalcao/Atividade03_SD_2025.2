import java.io.*;
import java.net.*;

public class CalculadoraServerSocket 
{

    public static void main(String[] args) throws IOException 
    {
        ServerSocket welcomeSocket = new ServerSocket(9090);
        Calculadora calc = new Calculadora();

        System.out.println("Servidor de calculadora iniciado...");

        while (true) 
        {
            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(
                    new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            int operacao = Integer.parseInt(inFromClient.readLine());
            double op1 = Double.parseDouble(inFromClient.readLine());
            double op2 = Double.parseDouble(inFromClient.readLine());

            double resultado = 0;

            switch (operacao) 
            {
                case 1:
                    resultado = calc.soma(op1, op2);
                    break;
                case 2:
                    resultado = calc.subtrai(op1, op2);
                    break;
                case 3:
                    resultado = calc.divide(op1, op2);
                    break;
                case 4:
                    resultado = calc.multiplica(op1, op2);
                    break;
                default:
                    outToClient.writeBytes("Operação inválida\n");
                    continue;
            }

            outToClient.writeBytes(resultado + "\n");
            connectionSocket.close();
        }
    }
}
