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
		Query query = em.createQuery("SELECT p FROM Pays p");
		return (List<Pays>)query.getResultList();
	}
	
	/**
	 * @param nom Le nom du pays à trouver
	 * @return Une instance si trouvé, null sinon
	 */
	public static Pays obtenirPays(String nom) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT p FROM Pays p "
									+ "WHERE p.nom=:nom ORDER BY p.nom");
		query.setParameter("nom", nom);
		List<Pays> results = (List<Pays>)query.getResultList();
		
		if (!results.isEmpty())
			return results.get(0);
		else
			return null;
	}
	
	/**
	 * @param code Le code du pays à trouver
	 * @return Une instance si trouvé, null sinon
	 */
	public static Pays obtenirPaysParCode(String code) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT p FROM Pays p "
									+ "WHERE p.code=:code ORDER BY p.code");
		query.setParameter("code", code);
		List<Pays> results = (List<Pays>)query.getResultList();
		
		if (!results.isEmpty())
			return results.get(0);
		else
			return null;
	}
}
