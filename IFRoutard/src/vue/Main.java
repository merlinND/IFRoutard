package vue;

import java.util.List;
import metier.modele.Client;
import metier.service.ServiceClient;

/**
 *
 * @author Merlin
 */
public class Main
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Client monClient = new Client("Merlin");
		ServiceClient.creerClient(monClient);
		Client monDeuxiemeClient = new Client("Romain");
		ServiceClient.creerClient(monDeuxiemeClient);
		System.out.println("Client créé avec succès.");
		
		System.out.println("Liste de tous les clients :");
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		for (Client c : tousLesClients)
			System.out.println(c);
	}
	
}
