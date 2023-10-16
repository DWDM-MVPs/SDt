import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
	public static void main(String[] args) {
		Registry r = null;
		PlacesManager placeList = null;

		try
		{
			r = LocateRegistry.createRegistry(2022);
		} catch (RemoteException a)
		{
			a.printStackTrace();
			return;
		}

		try
		{
			placeList = new PlacesManager();
			r.rebind("placelist", placeList);

			System.out.println("Place server ready");

			Thread.sleep(2000L);
		} catch (Exception e)
		{
			System.out.println("Place server main " + e.getMessage());
		}
	}
}
