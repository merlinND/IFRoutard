package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Pays;
import util.JpaUtil;

/**
 * Couche : DAO
 * Objet métier : Pays
 * @author Merlin
 */
public class PaysDao {
	/**
	 * Insère un nouveau pays dans la base de données.
	 * @param pays L'objet pays à persister en base de données
	 */
	public static void creerPays(Pays pays) {
		JpaUtil.obtenirEntityManager().persist(pays);
	}
	
	/**
	 * @return List<Pays> La liste de tous les pays existants
	 */
	public static List<Pays> obtenirPays() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT p from Pays p");
		return (List<Pays>)query.getResultList();
	}
}
