package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import util.JpaUtil;

/**
 *
 * @author Merlin
 */
public class ClientDao
{
	
	/**
	 * Insère un nouveau client dans la base de données.
	 * @param client L'objet client à persister en base de données
	 */
	public static void createClient(Client client) {
		JpaUtil.obtenirEntityManager().persist(client);
	}
	
	public static List<Client> obtenirClients() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c from Client c");
		List<Client> result = (List<Client>)query.getResultList();
		return result;
	}
	
}
