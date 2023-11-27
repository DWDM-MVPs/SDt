import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PlacesManager extends UnicastRemoteObject implements PlacesListInterface {
	private final List<Place> _placesList = new ArrayList<>();
	private ObjectRegistryInterface _registry;

	public PlacesManager() throws RemoteException {
		super();
	}

	@Override
	public void addPlace(Place p) {
		this._placesList.add(p);
		
		try
		{
			if (_registry == null)
			{
				this._registry = (ObjectRegistryInterface) Naming.lookup("rmi://localhost:2023/registry");
			}
			this._registry.addObject(p.getPostalCode(), "rmi://localhost:2022/placelist");
		} catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}

		System.out.println("Added place: " + p.getPostalCode() + " " + p.getLocality());
	}

	@Override
	public ArrayList<Place> allPlaces() {
		return (ArrayList<Place>) this._placesList;
	}

	@Override
	public Place getPlace(String postalCode) {
		System.out.println("Getting place: " + postalCode);

		return this._placesList.stream().filter(p -> p.getPostalCode().equals(postalCode)).findFirst().orElse(null);
	}
}
