package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Devis;
import util.JpaUtil;

/**
 * Couche : DAO
 * Objets métier : Devis
 * @author Romain
 */
public class DevisDao {
    /**
	 * Insère un nouveau devis dans la base de données.

	 * @param devis
	 */
	public static void creerDevis(Devis devis) {
		JpaUtil.obtenirEntityManager().persist(devis);
		if (devis.getClient() != null)
			JpaUtil.obtenirEntityManager().merge(devis.getClient());
		if (devis.getConseiller() != null)
			JpaUtil.obtenirEntityManager().merge(devis.getConseiller());
	}
	
	/**
	 * @return List<Devis> La liste de tous les devis existants
	 */
	public static List<Devis> obtenirDevis() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT d FROM Devis d");
		return (List<Devis>)query.getResultList();
	}
	
}
