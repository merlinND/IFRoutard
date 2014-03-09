package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Circuit;
import metier.modele.Depart;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.modele.Voyage;
import util.JpaUtil;

/**
 * Couche : DAO
 * Objet métier : Sejour, Circuit
 * @author Merlin
 */
public class VoyageDao {
	/**
	 * Insère un nouveau voyage dans la base de données.
	 * @param voyage
	 */
	public static void creerVoyage(Voyage voyage) {
		JpaUtil.obtenirEntityManager().persist(voyage);
	}
	
	/**
	 * @return List<Voyage> La liste de tous les voyages existants
	 */
	public static List<Voyage> obtenirVoyages() {
		List<Voyage> result = new ArrayList<Voyage>();
		result.addAll(obtenirSejours());
		result.addAll(obtenirCircuits());
		return result;
	}
	/**
	 * @return List<Voyage> La liste de tous les voyages qui ont des départs disponibles
	 */
	public static List<Voyage> obtenirVoyagesAyantDeparts() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		
		Query query = em.createQuery("SELECT v FROM Voyage v"
									+ " WHERE (SELECT count(d)"
											+ " FROM Depart d WHERE d.voyage = v) > 0");
		List<Voyage> results = (List<Voyage>)query.getResultList();
		return results;
	}
	/**
	 * @return List<Sejour> La liste de tous les séjours existants
	 */
	public static List<Sejour> obtenirSejours() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT s FROM Sejour s");
		return (List<Sejour>)query.getResultList();
	}
	/**
	 * @return List<Circuit> La liste de tous les circuits existants
	 */
	public static List<Circuit> obtenirCircuits() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c FROM Circuit c");
		return (List<Circuit>)query.getResultList();
	}

	/**
	 * @param code
	 * @return Le voyage, ou null si aucun voyage ne correspond à ce code 
	 */
	public static Voyage obtenirVoyageParCode(String code) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT v FROM Voyage v"
									+ " WHERE v.code=:code");
		query.setParameter("code", code);
		List<Voyage> results = (List<Voyage>)query.getResultList();
		if (results.size() > 0)
			return results.get(0);
		else
			return null;
	}
	
	public static List<Voyage> obtenirVoyagesParDestination(Pays pays) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT v FROM Voyage v"
									+ " WHERE v.destination=:pays");
		query.setParameter("pays", pays);
		return (List<Voyage>)query.getResultList();
	}
	
	
	/**
	 * Insère un nouveau départ dans la base de données.
	 * @param depart
	 */
	public static void creerDepart(Depart depart) {
		JpaUtil.obtenirEntityManager().persist(depart);
		// On met également à jour le voyage le contenant
		JpaUtil.obtenirEntityManager().merge(depart.getVoyage());
	}
}
