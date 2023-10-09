import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Connection extends Thread {
	private static final Map<Integer, Place> _places = new HashMap<>();
	DataOutputStream _out;
	Socket _clientSocket;

	public Connection(Socket clientSocket) {
		try
		{
			// Inicializa variáveis
			this._clientSocket = clientSocket;
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
			Request req = (Request) ois.readObject();

			if (req.getType().equals("new"))
			{
				int id = System.identityHashCode(req.getPlace());
				_places.put(id, req.getPlace());
				System.out.println("new place [" + id + "]");
				_out.writeUTF(String.valueOf(id));
			}
			else if (req.getType().equals("invoke"))
			{
				System.out.println("invoke [" + req.getObjectId() + "]");
				Place place = _places.getOrDefault(req.getObjectId(), null);
				if (place == null)
				{
					System.out.println("invalid objectId");
					_out.writeUTF("invalid objectid");
					return;
				}
				try
				{
					System.out.println(req.getMethod());
					_out.writeUTF(Place.class.getMethod(req.getMethod()).invoke(place).toString());
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
				{
					_out.writeUTF("invalid method");
				}
			}
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