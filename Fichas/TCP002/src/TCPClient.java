import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TCPClient {
	public static void main(String[] args) {
		Socket s = null;
		try
		{
			// Porta do servidor
			int serverPort = 7896;
			s = new Socket("localhost", serverPort);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			// Envia os dados para o servidor

			Person person = new Person( "Josefino", new Place("postal","locality") , 1999);

			ObjectOutputStream ous = new ObjectOutputStream(out);
			ous.writeObject(person);

			// Bloqueia à espera da resposta do servidor
			String data = in.readUTF();
			System.out.println("Received: " + data);
		} catch (UnknownHostException e)
		{
			System.out.println("Sock:" + e.getMessage());
		} catch (EOFException e)
		{
			System.out.println("IO:" +e.getStackTrace());
		} catch (IOException e)
		{
			System.out.println("IO:" + e.getMessage());
		} finally
		{
			if (s != null)
				try
				{
					s.close();
				} catch (IOException e)
				{
					System.out.println("Close:" + e.getMessage());
				}
		}
	}
}