import java.rmi.RemoteException;

public interface PlacesListManagerInterface
{
	void addPlace(Place p) throws RemoteException;

	String getPlaceListAddress(String objectId) throws RemoteException;
}
