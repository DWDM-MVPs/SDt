import java.io.*;
import java.net.Socket;

public class Person implements Serializable {
	private String _name;
	private int _year;

	public Person(String name, int year) {
		this._name = name;
		this._year = year;
	}

	public String getName() {
		return this._name;
	}

	public int getYear() {
		return this._year;
	}
}