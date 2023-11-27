import java.rmi.Naming;
import java.rmi.registry.Registry;

public class ReplicaManager
{
	public static void main(String[] args)
	{
		try{
		PlacesListManagerInterface placesListManager = (PlacesListManagerInterface) Naming.lookup("rmi://localhost:2022/placelist");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
