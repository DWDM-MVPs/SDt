import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlacesManager implements PlacesListInterface, Serializable {
	private final List<Place> _placesList = new ArrayList<>();

	@Override
	public void addPlace(Place p) {
		this._placesList.add(p);
	}

	@Override
	public ArrayList<Place> allPlaces() {
		return (ArrayList<Place>) this._placesList;
	}
}
