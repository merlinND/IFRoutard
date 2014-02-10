package metier.service;

import dao.ClientDao;
import java.util.List;
import metier.modele.Client;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métiers : Client
 * @author Merlin
 */
public class ServiceClient {

	/**
	 * Insérer le client donné en base de données.
	 * @param client 
	 */
	public static void creerClient(Client client) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		ClientDao.creerClient(client);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Obtenir tous les clients disponibles en base de données.
	 * @return 
	 */
	public static List<Client> obtenirClients() {
		JpaUtil.creerEntityManager();
		List<Client> result = ClientDao.obtenirClients();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
}
