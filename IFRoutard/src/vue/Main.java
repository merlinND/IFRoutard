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
		
		System.out.println("Liste de tous les pays :");
		List<Pays> tousLesPays = ServiceVoyage.obtenirPays();
		Pays unPays;
		for (Pays p : tousLesPays) {
			System.out.println(p);
			if(Math.random() > 0.9)
				monConseiller.addSpecialite(p);
		}
		
		ServiceEmploye.creerConseiller(monConseiller);
		
		System.out.println("\nListe de tous les conseillers :");
		List<Conseiller> tousLesConseillers = ServiceEmploye.obtenirConseillers();
		for (Conseiller c : tousLesConseillers)
			System.out.println(c);
		
		
		System.out.println("\nListe de tous les clients :");
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		for (Client c : tousLesClients)
			System.out.println(c);
		
	    Pays unAutrePays = new Pays("Espagne");
	    
	     Sejour sejourEnThailande = new Sejour("coral", 012, "au maroc", 10, "pas de description", unAutrePays);
	     System.out.println(sejourEnThailande.toString());
	     
	     Circuit circuitEuropeen = new Circuit("bus" , 1000, 2,"circuit au tour de l'Europe", 12, "pas de description", unAutrePays);
	     System.out.println(circuitEuropeen.toString());	     
	     Date date = new Date (2014, 01, 01);
	     Depart depart = new Depart(sejourEnThailande, 100,date,"Lyon", "unDepartFictif" );
	     System.out.println(depart.toString());
	     
	     Client clientTest = new Client ("georges", "martial", Client.Civilite.M, date, "06..", "@", "8 rue du domicil", "okoki");
	     Devis devis = new Devis(1, 1, monConseiller, clientTest, depart, date);
 	     System.out.println(devis.toString());
	}
	
}
