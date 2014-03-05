package metier.service;

import dao.PaysDao;
import dao.VoyageDao;
import java.util.List;
import metier.modele.Circuit;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.modele.Voyage;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métier : Pays, Voyage, Depart
 * @author Merlin
 */
public class ServiceVoyage {

	/* ----------------------------------------------
	   PAYS
	   ---------------------------------------------- */
	
	/**
	 * Insère le pays donné en base de données.
	 * @param pays 
	 */
	public static void creerPays(Pays pays) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		PaysDao.creerPays(pays);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Insère tous les pays donnés en base de données.
	 * @param pays
	 */
	public static void creerPays(List<Pays> pays) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		for (Pays p : pays) {
			PaysDao.creerPays(p);
		}
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Obtenir la liste de <strong>tous</strong> les pays disponibles.
	 * @return 
	 */
	public static List<Pays> obtenirPays() {
		JpaUtil.creerEntityManager();
		List<Pays> result = PaysDao.obtenirPays();
		JpaUtil.fermerEntityManager();
		return result;
	}
	/**
	 * 
	 * @param nom Le nom du pays à trouver
	 * @return 
	 */
	public static Pays obtenirPays(String nom) {
		JpaUtil.creerEntityManager();
		Pays result = PaysDao.obtenirPays(nom);
		JpaUtil.fermerEntityManager();
		return result;
	}
	/**
	 * 
	 * @param code Le code du pays à trouver
	 * @return 
	 */
	public static Pays obtenirPaysParCode(String code) {
		JpaUtil.creerEntityManager();
		Pays result = PaysDao.obtenirPaysParCode(code);
		JpaUtil.fermerEntityManager();
		return result;
	}
	
	/* ----------------------------------------------
	   VOYAGES
	   ---------------------------------------------- */
	
	/**
	 * Insère le voyage donné en base de données.
	 * @param voyage 
	 */
	public static void creerVoyage(Voyage voyage) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		VoyageDao.creerVoyage(voyage);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Insère tous les voyages donnés en base de données.
	 * @param voyages
	 */
	public static void creerVoyages(List<Voyage> voyages) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		for (Voyage v : voyages) {
			VoyageDao.creerVoyage(v);
		}
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Obtenir la liste de tous les voyages, tous types de voyage confondus.
	 * @return 
	 */
	public static List<Voyage> obtenirVoyages() {
		JpaUtil.creerEntityManager();
		List<Voyage> result = VoyageDao.obtenirVoyages();
		JpaUtil.fermerEntityManager();
		return result;
	}
	/**
	 * Obtenir la liste des voyages de type Sejour.
	 * @return 
	 */
	public static List<Sejour> obtenirSejours() {
		JpaUtil.creerEntityManager();
		List<Sejour> result = VoyageDao.obtenirSejours();
		JpaUtil.fermerEntityManager();
		return result;
	}
	/**
	 * Obtenir la liste des voyages de type Circuit.
	 * @return 
	 */
	public static List<Circuit> obtenirCircuits() {
		JpaUtil.creerEntityManager();
		List<Circuit> result = VoyageDao.obtenirCircuits();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
	/**
	 * 
	 * @param nomPays
	 * @return La liste, ou null si le pays demandé pays n'existe pas
	 */
	public static List<Voyage> obtenirVoyagesParPays(String nomPays) {
		Pays pays = obtenirPays(nomPays);
		if (pays != null)
			return obtenirVoyagesParDestination(pays);
		else
			return null;
	}
	/**
	 * 
	 * @param pays
	 * @return La liste, potentiellement vide
	 */
	public static List<Voyage> obtenirVoyagesParDestination(Pays pays) {
		JpaUtil.creerEntityManager();
		List<Voyage> result = VoyageDao.obtenirVoyagesParDestination(pays);
		JpaUtil.fermerEntityManager();
		return result;
	}
}
