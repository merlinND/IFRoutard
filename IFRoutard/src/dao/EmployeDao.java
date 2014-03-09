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
	 * Trouver les conseiller qui ont la spécialité demandée.
	 * @param specialite 
	 * @return Une liste de conseillers (potentiellement vide)
	 */
	public static List<Conseiller> obtenirParSpecialite(Pays specialite) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c FROM Conseiller c "
									+ "WHERE :specialite "
										+ "	IN (SELECT s.id FROM c.specialites s) ");
		query.setParameter("specialite", specialite.getId());
		return (List<Conseiller>)query.getResultList();
	}
}
