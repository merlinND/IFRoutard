package metier.service;

import dao.DevisDao;
import java.util.List;
import metier.modele.Devis;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métiers : Devis
 * @author Romain
 */
public class ServiceDevis {
    /**
	 * Insère le devis donné en base de données.
	 * @param devis 
	 */
	public static void creerDevis(Devis devis) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		DevisDao.creerDevis(devis);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Insère tous les devis donnés en base de données.
	 * @param devis
	 */
	public static void creerDevis(List<Devis> devis) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		for (Devis d : devis) {
			DevisDao.creerDevis(d);
		}
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Obtenir la liste de tous les devis.
	 * @return 
	 */
	public static List<Devis> obtenirDevis() {
		JpaUtil.creerEntityManager();
		List<Devis> result = DevisDao.obtenirDevis();
		JpaUtil.fermerEntityManager();
		return result;
	}
}
