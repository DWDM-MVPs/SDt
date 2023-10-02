import java.io.*;
import java.net.Socket;

public class Person implements Serializable {
	private String _name;
	private int _year;

	private final transient Socket _socket;

	public Person(Socket socket) {
		this._socket = socket;
	}

	public Person(Socket socket, String name, int year) {
		this(socket);

		this._name = name;
		this._year = year;
	}

	public String getName() {
		return this._name;
	}

	public int getYear() {
		return this._year;
	}

	public void send() {
		try
		{
			DataOutputStream outputStream = new DataOutputStream(this._socket.getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			oos.writeObject(this);
		} catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public static Person read(Socket socket) {
		try
		{
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			return (Person) ois.readObject();
		} catch (IOException | ClassNotFoundException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}