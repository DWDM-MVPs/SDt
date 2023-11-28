import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RMIReplicaManager extends UnicastRemoteObject implements PlacesListManagerInterface, Serializable
{
	List<String> placesList = new ArrayList<>();

	@Override
	public void addPlace(Place p) throws RemoteException
	{
		try
		{
			ObjectRegistryInterface objectRegistryInterface = (ObjectRegistryInterface) Naming.lookup("rmi://localhost:2023/registry");
			for (String addr : placesList)
			{
				PlacesListInterface placesListInterface = (PlacesListInterface) Naming.lookup(addr);
				placesListInterface.addPlace(p);
			}
			objectRegistryInterface.addRManager(p.getPostalCode(), "rmi://localhost:2024/replicamanager");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getPlaceListAddress(String objectId) throws RemoteException
	{
		try
		{
			// is objectId is null then return random address
			if (objectId == null)
			{
				return placesList.get(new Random().nextInt(placesList.size()));
			}
			// Get address that contains objectId in it
			for (String addr : placesList)
			{
				PlacesListInterface placesListInterface = (PlacesListInterface) Naming.lookup(addr);
				Place p = placesListInterface.getPlace(objectId);
				if (p != null)
				{
					return addr;
				}
			}
			return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public RMIReplicaManager() throws RemoteException
	{
		for (int i = 2025; i <= 2027; i++)
		{
			placesList.add("rmi://localhost:" + i + "/placelist");
		}
	}

	public static void main(String[] args)
	{
		Registry r = null;
		PlacesListManagerInterface placesListManagerInterface = null;
		try
		{
			placesListManagerInterface = new RMIReplicaManager();
			r = LocateRegistry.createRegistry(2024);
			r.rebind("replicamanager", placesListManagerInterface);
			System.out.println("ReplicaManager server ready");
		}
		catch (RemoteException a)
		{
			a.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Place server main " + e.getMessage());
		}
	}


}
