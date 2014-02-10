package vue;

import java.util.List;
import metier.modele.Conseiller;
import metier.modele.Pays;
import metier.service.ServiceEmploye;
import metier.service.ServiceVoyage;

/**
 *
 * @author Merlin
 */
public class Main
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		Pays france = new Pays("France");
		ServiceVoyage.creerPays(france);
		Pays allemagne = new Pays("Allemagne");
		ServiceVoyage.creerPays(allemagne);
		Pays russie = new Pays("Russie");
		ServiceVoyage.creerPays(russie);
		
		System.out.println("Liste de tous les pays :");
		List<Pays> tousLesPays = ServiceVoyage.obtenirPays();
		for (Pays p : tousLesPays)
			System.out.println(p);
		
		Conseiller monConseiller = new Conseiller("Nimier-David", "Merlin", "merlin@nimierdavid.fr");
		monConseiller.addSpecialite(france);
		monConseiller.addSpecialite(russie);
		ServiceEmploye.creerConseiller(monConseiller);
		
		System.out.println("\nListe de tous les conseillers :");
		List<Conseiller> tousLesConseillers = ServiceEmploye.obtenirConseillers();
		for (Conseiller c : tousLesConseillers)
			System.out.println(c);
	}
	
}
