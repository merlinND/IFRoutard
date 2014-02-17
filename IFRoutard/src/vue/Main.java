package vue;

import java.io.IOException;
import java.util.List;
import metier.modele.Client;
import metier.modele.Conseiller;
import metier.modele.Pays;
import metier.service.ServiceClient;
import metier.service.ServiceEmploye;
import metier.service.ServiceVoyage;
import util.LectureDonneesCsv;

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
		// Si on ne veut pas imposer de limite : utiliser -1
		int limite = 10;
		
		// Récupérer les données de test
		String fichierClients = "res/data/IFRoutard-Clients.csv";
		String fichierPays = "res/data/IFRoutard-Pays.csv";
		LectureDonneesCsv lecteur;
		try {
			lecteur = new LectureDonneesCsv(fichierClients);
			lecteur.lireClients(limite);
			lecteur.fermer();

			lecteur = new LectureDonneesCsv(fichierPays);
			lecteur.lirePays(limite);
			lecteur.fermer();
			
			// TODO : les autres objets métier
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
		
		Conseiller monConseiller = new Conseiller("Conseiller", "Random", "random@domain.tld");
		
		System.out.println("----- Liste de tous les pays -----");
		List<Pays> tousLesPays = ServiceVoyage.obtenirPays();
		Pays unPays;
		for (Pays p : tousLesPays) {
			System.out.println(p);
			if(Math.random() > 0.9)
				monConseiller.addSpecialite(p);
		}
		
		ServiceEmploye.creerConseiller(monConseiller);
		
		System.out.println("\n----- Liste de tous les conseillers -----");
		List<Conseiller> tousLesConseillers = ServiceEmploye.obtenirConseillers();
		for (Conseiller c : tousLesConseillers)
			System.out.println(c);
		
		// Test de l'inscription interactive d'un client
		System.out.println("\n");
		InscriptionClient.inscriptionInteractive();
		
		System.out.println("\n----- Liste de tous les clients -----");
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		for (Client c : tousLesClients)
			System.out.println(c);
	}
}
