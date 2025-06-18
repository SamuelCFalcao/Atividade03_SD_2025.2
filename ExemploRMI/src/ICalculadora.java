import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface remota que define os métodos que poderão ser invocados remotamente
public interface ICalculadora extends Remote
{
    // Método remoto para soma
    int soma(int a, int b) throws RemoteException;

    // Método remoto para subtração
    int subtrai(int a, int b) throws RemoteException;

    // Método remoto para multiplicação
    int multiplica(int a, int b) throws RemoteException;

    // Método remoto para divisão
    int divide(int a, int b) throws RemoteException;

    // Método remoto que executa expressões matemáticas como string
    int executaExpressao(String expressao) throws RemoteException;
}