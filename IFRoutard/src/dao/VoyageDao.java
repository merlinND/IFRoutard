package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Circuit;
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
	 * TODO : est-ce qu'il faudrait persister sur la sous-classe ?
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
	 * @return List<Sejour> La liste de tous les séjours existants
	 */
	public static List<Sejour> obtenirSejours() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT s from Sejour s");
		return (List<Sejour>)query.getResultList();
	}
	/**
	 * @return List<Circuit> La liste de tous les circuits existants
	 */
	public static List<Circuit> obtenirCircuits() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT c from Circuit c");
		return (List<Circuit>)query.getResultList();
	}

	public static List<Voyage> obtenirVoyagesParDestination(Pays pays) {
		EntityManager em = JpaUtil.obtenirEntityManager();
		// TODO : est-ce qu'il faudrait requêter sur les sous-types ?
		Query query = em.createQuery("SELECT v from Voyage v"
									+ "WHERE v.destination=:pays");
		query.setParameter("pays", pays);
		return (List<Voyage>)query.getResultList();
	}
}