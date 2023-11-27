import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ObjectRegistry extends UnicastRemoteObject implements ObjectRegistryInterface {
	private final Map<String, String> _objectsMap = new HashMap<>();

	public ObjectRegistry() throws RemoteException {
		super();
	}

	@Override
	public void addRManager(String objectId, String serverAddress) {
		if (this._objectsMap.containsKey(objectId))
		{
			System.out.println("Object already exists");
			return;
		}
		this._objectsMap.put(objectId, serverAddress);
		System.out.println("Registered object: " + objectId + " at " + serverAddress);
	}

	@Override
	public String resolve(String objectId) {
		System.out.println("Resolving object: " + objectId);

		String server = this._objectsMap.get(objectId);

		System.out.println("Resolved object: " + objectId + " at " + server);
		return server;
	}
}
