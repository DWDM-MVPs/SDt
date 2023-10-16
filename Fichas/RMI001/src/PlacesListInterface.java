import java.rmi.Remote;
import java.util.ArrayList;

public interface PlacesListInterface extends Remote {
	void addPlace(Place p);

	ArrayList<Place> allPlaces();
}
