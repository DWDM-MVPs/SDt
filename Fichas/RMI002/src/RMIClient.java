import java.rmi.Naming;

public class RMIClient {
	public static void main(String[] args) {
		try
		{
			// Get placelist
			System.out.println("Localizar servidor de Objetos...");
			PlacesListInterface placesList = (PlacesListInterface) Naming.lookup("rmi://localhost:2022/placelist");

			// Create place
			Place p = new Place("3510", "Viseu");
			System.out.println("Invocar addPlace() ...");
			placesList.addPlace(p);

			// Get registry
			System.out.println("Obter o endereço do servidor no Registry() ...");
			ObjectRegistryInterface objectRegistry = (ObjectRegistryInterface) Naming.lookup("rmi://localhost:2023/registry");
			String addr = objectRegistry.resolve("3510");

			// Get place
			System.out.println("Invocar getPlace() no servidor de objetos...");
			PlacesListInterface registryPlacesList = (PlacesListInterface) Naming.lookup(addr);

			// Check
			if (registryPlacesList.getPlace("3510").getLocality().equals("Viseu"))
			{
				System.out.println("Teste bem sucedido!");
			}
			else
			{
				System.out.println("Teste falhou!");
			}
		} catch (Exception e)
		{
			System.out.println("Problemas de Comunicação\n" + e.getMessage());
		}
	}
}
