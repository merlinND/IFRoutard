package metier.service;

import dao.ClientDao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import util.JpaUtil;

/**
 *
 * @author Merlin
 */
public class ServiceClient {

	public static void creerClient(Client client) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		ClientDao.createClient(client);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	public static List<Client> obtenirClients() {
		JpaUtil.creerEntityManager();
		List<Client> result = ClientDao.obtenirClients();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
}
