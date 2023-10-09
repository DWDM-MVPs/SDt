import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

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

			ObjectOutputStream ous = new ObjectOutputStream(out);
			ous.writeObject(new Request(new Place("3510", "Viseu")));
			// Bloqueia à espera da resposta do servidor
			String data = in.readUTF();
			System.out.println("Received: " + data);

			ous.writeObject(new Request(Integer.parseInt(data), "getPostalCode"));
			// Bloqueia à espera da resposta do servidor
			String data2 = in.readUTF();
			System.out.println("Received: " + data2);
		} catch (UnknownHostException e)
		{
			System.out.println("Sock:" + e.getMessage());
		} catch (EOFException e)
		{
			System.out.println("IO:" + e.getMessage());
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