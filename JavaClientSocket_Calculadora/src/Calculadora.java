public class Calculadora 
{
    public String sayHello(String nome, String sobrenome) 
    {
        return "Fala " + nome + " " + sobrenome;
    }

    public double soma(double oper1, double oper2) 
    {
        return oper1 + oper2;
    }

    public double subtrai(double oper1, double oper2) 
    {
        return oper1 - oper2;
    }

    public double multiplica(double oper1, double oper2) 
    {
        return oper1 * oper2;
    }

    public double divide(double oper1, double oper2) 
    {
        if (oper2 == 0) throw new ArithmeticException("Divisão por zero");
        return oper1 / oper2;
    }
}