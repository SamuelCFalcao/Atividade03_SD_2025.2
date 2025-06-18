import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Cliente RMI que consome os métodos remotos da calculadora
public class CalculadoraCliente
{
    public static void main(String[] args)
    {
        Registry reg;
        ICalculadora calc;

        try
        {
            // Localiza o registro RMI na porta 1099
            reg = LocateRegistry.getRegistry(1099);

            // Obtém a referência remota para a calculadora
            calc = (ICalculadora) reg.lookup("calculadora");

            // Chamada remota aos métodos da calculadora
            System.out.println("3 + 2 = " + calc.soma(3, 2));
            System.out.println("10 - 4 = " + calc.subtrai(10, 4));
            System.out.println("6 * 7 = " + calc.multiplica(6, 7));
            System.out.println("20 / 4 = " + calc.divide(20, 4));

            // Avaliação de expressão matemática
            String expressao = "3 + 4 * 2 - (1 + 1)";
            System.out.println("Expressão '" + expressao + "' = " + calc.executaExpressao(expressao));
        }
        catch (RemoteException | NotBoundException e)
        {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}