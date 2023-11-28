import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObjectRegistryInterface extends Remote {
	void addRManager(String objectId, String serverAddress) throws RemoteException;

	String resolve(String objectId)throws RemoteException;
}
