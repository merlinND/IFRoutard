package vue;

import java.io.IOException;
import java.util.List;
import metier.modele.Circuit;
import metier.modele.Client;
import metier.modele.Conseiller;
import metier.modele.Devis;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.service.ServiceClient;
import metier.service.ServiceDevis;
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
		fillDatabase(10);
		
		// TODO : créer un fake devis pour chaque client
		
		// TODO: affichage des clients inscrits avec un résumé de leurs devis et des conseillers associés
		
		// Test de l'inscription interactive d'un client
		System.out.println("\n");
		Client nouveau = VuesClient.inscriptionInteractive();
		
		// Test de la connexion interactive d'un client
		System.out.println("\n");
		Client clientConnecte = VuesClient.connexionInteractive();
		if (clientConnecte != null) {
			System.out.println("Bienvenue, " + clientConnecte.getPrenom() + " !");
		}
		
		// Test de l'établissement interactif d'un devis
		System.out.println("\n");
		Devis devis = null;
		do {
			devis = VuesClient.devisInteractif(clientConnecte);
		} while (devis == null);

		if (devis != null) {
			ServiceDevis.creerDevis(devis);
			
			System.out.println("\nDevis établi avec succès ! Envoi de l'e-mail récapitulatif :");
			VuesClient.envoyerEmailConfirmation(devis);
		}
	}
	
	/**
	 * Utilise les données de test fournies pour remplir la base.
	 * @param limite Le nombre d'éléments à insérer dans chaque table (-1 => tous)
	 */
	static void fillDatabase(int limite) {
		// Récupérer les données de test
		String fichierClients = "res/data/IFRoutard-Clients.csv",
				fichierPays = "res/data/IFRoutard-Pays.csv",
                fichierConseillers = "res/data/IFRoutard-Conseillers.csv",
                fichierVoyagesCircuits = "res/data/IFRoutard-Voyages-Circuits.csv",
                fichierVoyagesSejours = "res/data/IFRoutard-Voyages-Sejours.csv",
				fichierDeparts = "res/data/IFRoutard-Departs.csv";
		LectureDonneesCsv lecteur;
		try {
			lecteur = new LectureDonneesCsv(fichierClients);
			lecteur.lireClients(limite);
			lecteur.fermer();

			lecteur = new LectureDonneesCsv(fichierPays);
			lecteur.lirePays(limite);
			lecteur.fermer();
            
            lecteur = new LectureDonneesCsv(fichierConseillers);
			lecteur.lireConseillers(limite);
			lecteur.fermer();
                        
            lecteur = new LectureDonneesCsv(fichierVoyagesCircuits);
			lecteur.lireVoyagesCircuit(limite);
			lecteur.fermer();
                        
            lecteur = new LectureDonneesCsv(fichierVoyagesSejours);
			lecteur.lireVoyagesSejour(limite);
			lecteur.fermer();
			
            lecteur = new LectureDonneesCsv(fichierDeparts);
			lecteur.lireDeparts(limite);
			lecteur.fermer();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}
	
	static void printDatabaseSamples() {
		System.out.println("----- Liste de tous les pays -----");
		List<Pays> tousLesPays = ServiceVoyage.obtenirPays();
		Pays unPays;
		for (Pays p : tousLesPays) {
			System.out.println(p);
		}
		
		System.out.println("\n----- Liste de tous les séjours -----");
		List<Sejour> tousLesSejours = ServiceVoyage.obtenirSejours();
		for (Sejour s : tousLesSejours) {
			System.out.println(s);
		}
		
		System.out.println("\n----- Liste de tous les circuits -----");
		List<Circuit> tousLesCircuits = ServiceVoyage.obtenirCircuits();
		for (Circuit c : tousLesCircuits) {
			System.out.println(c);
		}
		
		System.out.println("\n----- Liste de tous les conseillers -----");
		List<Conseiller> tousLesConseillers = ServiceEmploye.obtenirConseillers();
		for (Conseiller c : tousLesConseillers)
			System.out.println(c);
		
		System.out.println("\n----- Liste des conseillers spécialistes d'Algérie -----");
		Pays algerie = ServiceVoyage.obtenirPays("Algérie");
		List<Conseiller> specialistes = ServiceEmploye.obtenirConseillersParSpecialite(algerie);
		for (Conseiller c : specialistes) {
			System.out.println(c);
		}
		
		System.out.println("\n----- Liste de tous les clients -----");
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		Client randomClient = null;
		for (Client c : tousLesClients) {
			System.out.println(c);
			randomClient = c;
		}
	}
}
