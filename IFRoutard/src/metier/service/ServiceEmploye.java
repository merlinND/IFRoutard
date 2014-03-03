package metier.service;

import dao.EmployeDao;
import java.util.List;
import metier.modele.Conseiller;
import metier.modele.Pays;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métier : Employe, Conseiller
 * @author Merlin, Romain
 */
public class ServiceEmploye {

	/**
	 * Insère le conseiller donné en base de données.
	 * @param conseiller 
	 */
	public static void creerConseiller(Conseiller conseiller) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		EmployeDao.creerEmploye(conseiller);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Insère tous les conseillers donnés en base de données.
	 * @param conseillers
	 */
	public static void creerConseillers(List<Conseiller> conseillers) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		for (Conseiller c : conseillers) {
			EmployeDao.creerEmploye(c);
		}
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Obtenir la liste de <strong>tous</strong> les conseillers disponibles.
	 * @return 
	 */
	public static List<Conseiller> obtenirConseillers() {
		JpaUtil.creerEntityManager();
		List<Conseiller> result = EmployeDao.obtenirConseillers();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
	/**
	 * 
	 * @param pays
	 * @return La liste (éventuellement vide) des conseillers spécialistes de ce pays
	 */
	public static List<Conseiller> obtenirConseillerParSpecialite(Pays pays) {
		JpaUtil.creerEntityManager();
		List<Conseiller> result = EmployeDao.obtenirParSpecialite(pays);
		JpaUtil.fermerEntityManager();
		return result;
	}
}
