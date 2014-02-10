package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import util.JpaUtil;

/**
 * Couche : DAO
 * Objet métier : Client
 * @author Merlin
 */
public class ClientDao
{
	
	/**
	 * Insère un nouveau client dans la base de données.
	 * @param client L'objet client à persister en base de données
	 */
	public static void creerClient(Client client) {
		JpaUtil.obtenirEntityManager().persist(client);
	}
	
	/**
	 * @return List<Client> La liste de tous les clients existants
	 */
	public static List<Client> obtenirClients() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c from Client c");
		return (List<Client>)query.getResultList();
	}
	
}
