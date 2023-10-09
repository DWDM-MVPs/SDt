import java.io.*;
import java.net.Socket;

public class Person implements Serializable {
	private String _name;
	private int _year;
	private Place _place;

	public Person(String name, Place place, int year) {
		this._name = name;
		this._year = year;
		this._place = place;
	}

	public String getName() {
		return this._name;
	}

	public int getYear() {
		return this._year;
	}

	public Place getPlace(){
		return this._place;
	}
}