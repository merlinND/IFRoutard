package metier.service;

import dao.PaysDao;
import dao.VoyageDao;
import java.util.List;
import metier.modele.Circuit;
import metier.modele.Depart;
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
	 * @param inclureSansDeparts Laisser dans la liste les voyages qui n'ont pas de départ disponible
	 * @return 
	 */
	public static List<Voyage> obtenirVoyages(Boolean inclureSansDeparts) {
		JpaUtil.creerEntityManager();
		List<Voyage> result;
		if (inclureSansDeparts)
			result = VoyageDao.obtenirVoyages();
		else
			result = VoyageDao.obtenirVoyagesAyantDeparts();
		JpaUtil.fermerEntityManager();
		return result;
	}
	/**
	 * Obtenir la liste des voyages ayant des départs disponibles (tous types de voyage confondus)
	 * @return 
	 */
	public static List<Voyage> obtenirVoyages() {
		return obtenirVoyages(false);
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
	 * @param code
	 * @return Le voyage, ou null si le code ne correspond à aucun voyage
	 */
	public static Voyage obtenirVoyageParCode(String code) {
		JpaUtil.creerEntityManager();
		Voyage result = VoyageDao.obtenirVoyageParCode(code);
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
	
	
	/* ----------------------------------------------
	   DEPARTS
	   ---------------------------------------------- */
	
	/**
	 * Insère le départ donné en base de données.
	 * @param depart 
	 */
	public static void creerDepart(Depart depart) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		VoyageDao.creerDepart(depart);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Insère tous les départs donnés en base de données.
	 * @param departs
	 */
	public static void creerDeparts(List<Depart> departs) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		for (Depart d : departs) {
			VoyageDao.creerDepart(d);
		}
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
}
