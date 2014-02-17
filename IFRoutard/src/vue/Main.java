package vue;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import metier.modele.Circuit;
import metier.modele.Client;
import metier.modele.Conseiller;
import metier.modele.Depart;
import metier.modele.Devis;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.service.ServiceClient;
import metier.service.ServiceEmploye;
import metier.service.ServiceVoyage;
import util.LectureDonneesCsv;

/**
 *
 * @author Merlin & Romain
 */
public class Main
{
   
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{

		// Si on ne veut pas imposer de limite : utiliser -1
		int limite = 3;
		
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
		
		System.out.println("----- Liste de tous les pays -----");
		List<Pays> tousLesPays = ServiceVoyage.obtenirPays();
		Pays unPays;
		for (Pays p : tousLesPays) {
			System.out.println(p);
		}
		
		Conseiller monConseiller = new Conseiller("Conseiller", "Random", "random@domain.tld");
		monConseiller.addSpecialite(ServiceVoyage.obtenirPays("Algérie"));
		monConseiller.addSpecialite(ServiceVoyage.obtenirPays("Albanie"));
		ServiceEmploye.creerConseiller(monConseiller);
		
		System.out.println("\n----- Liste de tous les conseillers -----");
		List<Conseiller> tousLesConseillers = ServiceEmploye.obtenirConseillers();
		for (Conseiller c : tousLesConseillers)
			System.out.println(c);
		
		// Test de l'inscription interactive d'un client
		//System.out.println("\n");
		//Client nouveau = VuesClient.inscriptionInteractive();
		
		System.out.println("\n----- Liste de tous les clients -----");
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		for (Client c : tousLesClients)
			System.out.println(c);
		
		// Test de la connexion interactive d'un client
		System.out.println("\n");
		Client connecte = VuesClient.connexionInteractive();
		if (connecte != null) {
			System.out.println("Bienvenue, " + connecte.getPrenom() + " !");
		}
	}
}
