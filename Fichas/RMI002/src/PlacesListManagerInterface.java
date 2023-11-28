import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlacesListManagerInterface extends Remote
{
	void addPlace(Place p) throws RemoteException, MalformedURLException, NotBoundException;

	String getPlaceListAddress(String objectId) throws RemoteException, MalformedURLException, NotBoundException;
}