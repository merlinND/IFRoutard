package metier.service;

import dao.EmployeDao;
import java.util.List;
import metier.modele.Conseiller;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métier : Employe, Conseiller
 * @author Merlin
 */
public class ServiceEmploye {

	/**
	 * Insère le pays donné en base de données.
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
	 * Obtenir la liste de <strong>tous</strong> les conseillers disponibles.
	 * @return 
	 */
	public static List<Conseiller> obtenirConseillers() {
		JpaUtil.creerEntityManager();
		List<Conseiller> result = EmployeDao.obtenirConseillers();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
	// TODO : méthodes de recherche de conseiller par leur spécialité
	
}
