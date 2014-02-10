package metier.service;

import dao.PaysDao;
import java.util.List;
import metier.modele.Pays;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métier : Pays, Voyage, Depart
 * @author Merlin
 */
public class ServiceVoyage {

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
	 * Obtenir la liste de <strong>tous</strong> les pays disponibles.
	 * @return 
	 */
	public static List<Pays> obtenirPays() {
		JpaUtil.creerEntityManager();
		List<Pays> result = PaysDao.obtenirPays();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
}
