import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ObjectRegistry extends UnicastRemoteObject implements ObjectRegistryInterface
{
	private final Map<String, String> _objects = new HashMap<>();

	protected ObjectRegistry() throws RemoteException
	{
		super();
	}

	@Override
	public void addRManager(String objectID, String serverAddress) throws RemoteException
	{
		this._objects.put(objectID, serverAddress);
	}

	@Override
	public String resolve(String objectID) throws RemoteException
	{
		return _objects.get(objectID);
	}
}
