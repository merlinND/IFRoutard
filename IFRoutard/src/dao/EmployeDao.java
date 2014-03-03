package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Conseiller;
import metier.modele.Employe;
import metier.modele.Pays;
import util.JpaUtil;

/**
 * Couche : DAO
 * Objets métier : Conseiller
 * @author Romain
 */
public class EmployeDao
{
	
	/**
	 * Insère un nouveau conseiller dans la base de données.
	 * @param employe L'objet employe à persister en base de données
	 */
	public static void creerEmploye(Employe employe) {
		JpaUtil.obtenirEntityManager().persist(employe);
	}
	
	/**
	 * @return List<Conseiller> La liste de tous les conseillers existants
	 */
	public static List<Conseiller> obtenirConseillers() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c FROM Conseiller c");
		return (List<Conseiller>)query.getResultList();
	}
		/**
	 * @param specialite trouver les conseiller qui ont la spécialité specialite
	 * @return Une instance si trouvé, null sinon
	 */
	public static List<Conseiller> obtenirSpecialite(String specialite) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c from Conseiller c "
									+ "WHERE c.specialite=:specialite ORDER BY c.specialite");
		query.setParameter("specialite", specialite);
		List<Conseiller> results = (List<Conseiller>)query.getResultList();
		
		if (!results.isEmpty())
			return (List<Conseiller>) results.get(0);
		else
			return null;
	}
}
