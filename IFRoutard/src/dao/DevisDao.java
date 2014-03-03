/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Devis;
import util.JpaUtil;

/**
 *
 * @author Romain
 */
public class DevisDao {
    /**
	 * Insère un nouveau devis dans la base de données.

	 * @param devis
	 */
	public static void creerDevis(Devis devis) {
		JpaUtil.obtenirEntityManager().persist(devis);
	}
        /**
	 * @return List<Voyage> La liste de tous les voyages existants
	 */
	public static List<Devis> obtenirDevis() {
		EntityManager em = JpaUtil.obtenirEntityManager();
		Query query = em.createQuery("SELECT d from Devis d");
		return (List<Devis>)query.getResultList();
	}
	
}
