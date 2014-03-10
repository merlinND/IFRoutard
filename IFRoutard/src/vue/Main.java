package vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import metier.modele.Circuit;
import metier.modele.Client;
import metier.modele.Conseiller;
import metier.modele.Depart;
import metier.modele.Devis;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.modele.Voyage;
import metier.service.ServiceClient;
import metier.service.ServiceDevis;
import metier.service.ServiceEmploye;
import metier.service.ServiceVoyage;
import util.Aleatoire;
import util.LectureDonneesCsv;
import util.Saisie;

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
		while (menu()) {
			// Keep going.
		}
	}
	
	static Boolean menu() {
		List<Integer> choixPossibles = new ArrayList<Integer>();
		
		System.out.println("----- Menu ---------------------------------");
		choixPossibles.add(1);
		System.out.println("1. Charger les données de test");
		choixPossibles.add(2);
		System.out.println("2. Créer des devis aléatoires");
		choixPossibles.add(3);
		System.out.println("3. Afficher un résumé des conseillers");
		choixPossibles.add(4);
		System.out.println("4. Afficher un résumé des clients");
		choixPossibles.add(5);
		System.out.println("5. Inscription interactive");
		choixPossibles.add(6);
		System.out.println("6. Connexion et établissement d'un devis");
		choixPossibles.add(0);
		System.out.println("0. Quitter");
		
		Integer choix = Saisie.lireInteger("Votre choix : ", choixPossibles);
		System.out.println("--------------------------------------------\n");
		
		switch (choix) {
			case 1:
				remplirBaseDeDonnees(-1);
				break;
			case 2:
				creerDevisAleatoires();
				break;
			case 3:
				afficherResumeConseillers();
				break;
			case 4:
				afficherResumeClients();
				break;
			case 5:
				// Test de l'inscription interactive d'un client
				System.out.println("\n");
				VuesClient.inscriptionInteractive();
				break;
			case 6:
				// Test de la connexion interactive d'un client
				System.out.println("\n");
				Client clientConnecte = VuesClient.connexionInteractive();
				if (clientConnecte != null) {
					System.out.println("Bienvenue, " + clientConnecte.getPrenom() + " !");
				}
				
				// Test de l'établissement interactif d'un devis
				System.out.println("\n");
				Devis devis = VuesClient.devisInteractif(clientConnecte);
				if (devis != null) {
					ServiceDevis.creerDevis(devis);

					System.out.println("\nDevis établi avec succès ! Envoi de l'e-mail récapitulatif :");
					VuesClient.envoyerEmailConfirmation(devis);
				}
				break;
			case 0:
			default:
				return false;
		}
		return true;
	}
	
	/**
	 * Utilise les données de test fournies pour remplir la base.
	 * @param limite Le nombre d'éléments à insérer dans chaque table (-1 => tous)
	 */
	static void remplirBaseDeDonnees(int limite) {
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
	
	/**
	 * Affiche certains extraits du contenu de la base de données.
	 */
	static void afficherEchantillons() {
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
		for (Client c : tousLesClients) {
			System.out.println(c);
		}
	}

	/**
	 * Pour chaque client, crée un devis aléatoire.
	 */
	static void creerDevisAleatoires() {
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		List<Voyage> tousLesVoyages = ServiceVoyage.obtenirVoyages();
		
		for (Client c : tousLesClients) {
			Integer r;
			// Choix d'un voyage aléatoire
			r = Aleatoire.random(0, tousLesVoyages.size()-1);
			Voyage voyage = tousLesVoyages.get(r);
			// Choix d'un départ aléatoire pour ce voyage
			r = Aleatoire.random(0, voyage.getDeparts().size()-1);
			Depart depart = voyage.getDeparts().get(r);
			// Obtention du conseiller le moins occupé
			Conseiller conseiller = ServiceEmploye.obtenirSpecialiste(voyage.getDestination());
			
			//if (conseiller == null)
			//	System.err.println("Impossible de trouver un conseiller spécialiste de " + voyage.getDestination().getNom());
			
			// Choix aléatoire du nombre de voyageurs
			int nbVoyageurs = Aleatoire.random(1, 10);
			
			// Création et enregistrement du devis
			Devis devis = new Devis(c, depart, nbVoyageurs, conseiller);
			ServiceDevis.creerDevis(devis);
		}
	}
	
	/**
	 * Affiche un résumé des conseillers et de leurs spécialités
	 */
	static void afficherResumeConseillers() {
		System.out.println("----- Résumé de la base conseiller ---------");
		List<Conseiller> conseillers = ServiceEmploye.obtenirConseillers();
		
		for(Conseiller c : conseillers) {
			System.out.println(c.getNomComplet());
			System.out.print(" est spécialiste de : ");
			List<Pays> specialites = c.getSpecialites();
			for (Pays p : specialites)
				System.out.print(p.getNom() + ", ");
			System.out.print('\n');
		}
		System.out.println("--------------------------------------------\n");
	}
	
	/**
	 * Affiche un résumé des clients, de leurs devis et du conseiller associé
	 */
	static void afficherResumeClients() {
		System.out.println("----- Résumé de la base client -------------");
		List<Client> tousLesClients = ServiceClient.obtenirClients();
		
		for (Client c : tousLesClients) {
			System.out.println(c.getNomComplet() + " :");
			if (c.getDevis().size() > 0) {
				Devis d = c.getDevis().get(0);
				System.out.println("a acheté le voyage : " + d.getDepart().getVoyage().getTitre());
				if (d.getConseiller() != null)
					System.out.println("et est suivi par : " + d.getConseiller().getNomComplet());
			}
			System.out.print("\n");
		}
		System.out.println("--------------------------------------------\n");
	}
}
