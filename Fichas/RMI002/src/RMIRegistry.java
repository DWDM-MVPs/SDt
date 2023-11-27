import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRegistry {
	public static void main(String[] args) {
		Registry r = null;
		ObjectRegistryInterface registry = null;

		try
		{
			r = LocateRegistry.createRegistry(2023);
		} catch (RemoteException a)
		{
			a.printStackTrace();
			return;
		}

		try
		{
			registry = new ObjectRegistry();
			r.rebind("registry", registry);

			System.out.println("Registry server ready");
		} catch (Exception e)
		{
			System.out.println("Registry server main " + e.getMessage());
		}
	}
}
