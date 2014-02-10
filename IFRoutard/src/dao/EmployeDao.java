package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Conseiller;
import metier.modele.Employe;
import util.JpaUtil;

/**
 * Couche : DAO
 * Objets métier : Conseiller
 * @author Merlin
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
		Query query = em.createQuery("SELECT c from Conseiller c");
		return (List<Conseiller>)query.getResultList();
	}
	
}
