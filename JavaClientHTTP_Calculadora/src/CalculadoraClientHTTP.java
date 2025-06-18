import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class CalculadoraClientHTTP
{

    public static void main(String[] args)
    {

        String[] operacoes = {"1", "2", "3", "4"}; // 1-soma, 2-subtrai, 3-divide, 4-multiplica
        int tentativasMax = 3;

        for (String operacao : operacoes)
        {
            boolean sucesso = false;
            int tentativas = 0;

            while (!sucesso && tentativas < tentativasMax)
            {
                try
                {
                    tentativas++;
                    System.out.println("\n--- Tentativa " + tentativas + " para operação " + operacao + " ---");

                    URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Enviando os parâmetros da operação
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write("oper1=15&oper2=5&operacao=" + operacao);
                    writer.flush();
                    writer.close();
                    os.close();

                    // Verifica se a resposta foi OK
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK)
                    {
                        // Recebe a resposta do servidor
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream(), "utf-8"));
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null)
                        {
                            response.append(responseLine.trim());
                        }
                        System.out.println("Resultado da operação [" + operacao + "]: " + response.toString());
                        sucesso = true;
                    }
                    else
                    {
                        System.out.println("Falha ao conectar. Código: " + responseCode);
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Erro na tentativa " + tentativas + ": " + e.getMessage());
                    if (tentativas == tentativasMax)
                    {
                        System.out.println("Falha definitiva após " + tentativasMax + " tentativas para operação " + operacao);
                    }
                }
            }
        }
    }
}
