import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

// Implementação da interface ICalculadora
public class Calculadora implements ICalculadora
{
    private static final long serialVersionUID = 1L;

    // Contador de chamadas para rastrear chamadas aos métodos
    private static int chamadas = 0;

    // Implementação do método soma
    public int soma(int a, int b) throws RemoteException
    {
        System.out.println("Método soma chamado " + chamadas++);
        return a + b;
    }

    // Implementação do método subtrai
    public int subtrai(int a, int b) throws RemoteException
    {
        System.out.println("Método subtrai chamado " + chamadas++);
        return a - b;
    }

    // Implementação do método multiplica
    public int multiplica(int a, int b) throws RemoteException
    {
        System.out.println("Método multiplica chamado " + chamadas++);
        return a * b;
    }

    // Implementação do método divide
    public int divide(int a, int b) throws RemoteException
    {
        System.out.println("Método divide chamado " + chamadas++);
        if (b == 0) throw new ArithmeticException("Divisão por zero");
        return a / b;
    }

    // Implementação do método que avalia uma expressão matemática como string
    public int executaExpressao(String expressao) throws RemoteException
    {
        System.out.println("Método executaExpressao chamado " + chamadas++);
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        try
        {
            Object result = engine.eval(expressao); // Avalia a expressão
            return ((Number) result).intValue(); // Converte o resultado para int
        }
        catch (ScriptException | ClassCastException e)
        {
            throw new RemoteException("Erro ao avaliar expressão", e);
        }
    }

    // Método principal que inicia o servidor RMI
    public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException
    {
        Calculadora calculadora = new Calculadora(); // Cria instância da calculadora
        Registry reg;

        // Exporta o objeto remoto na porta 1100
        ICalculadora stub = (ICalculadora) UnicastRemoteObject.exportObject(calculadora, 1100);

        try
        {
            System.out.println("Criando registro RMI...");
            reg = LocateRegistry.createRegistry(1099); // Cria novo registro RMI
        }
        catch (Exception e)
        {
            // Se já existir, tenta obter o existente
            reg = LocateRegistry.getRegistry(1099);
        }

        // Registra o objeto remoto com o nome "calculadora"
        reg.rebind("calculadora", stub);
        System.out.println("Servidor RMI pronto.");
    }
}