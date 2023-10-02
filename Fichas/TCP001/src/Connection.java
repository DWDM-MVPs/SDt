import java.io.*;
import java.net.Socket;

public class Connection extends Thread {
	private DataInputStream _in;
	DataOutputStream _out;
	Socket _clientSocket;

	public Connection(Socket clientSocket) {
		try
		{
			// Inicializa variáveis
			this._clientSocket = clientSocket;
			this._in = new DataInputStream(this._clientSocket.getInputStream());
			this._out = new DataOutputStream(this._clientSocket.getOutputStream());

			// Executa o método run numa thread separada
			this.start();

		} catch (IOException ex)
		{
			System.out.println("Connection:" + ex.getMessage());
		}
	}

	public void run() {
		try
		{
			// Lê os dados do cliente
			//Person person = Person.read(this._clientSocket);

			DataInputStream inputStream = new DataInputStream(_clientSocket.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			Person person = (Person) ois.readObject();

			// Envia a mensagem (resposta) ao cliente
			this._out.writeUTF(person.getName());
		} catch (EOFException ex)
		{
			System.out.println("EOF:" + ex.getMessage());
		} catch (IOException ex)
		{
			System.out.println("IO:" + ex.getMessage());
		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		} finally
		{
			try
			{
				this._clientSocket.close();
			} catch (IOException ex)
			{
				System.out.println("Failed to close client connection.");
			}
		}
	}
}