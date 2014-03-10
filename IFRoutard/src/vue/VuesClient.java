package vue;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metier.modele.Circuit;
import metier.modele.Client;
import metier.modele.Conseiller;
import metier.modele.Depart;
import metier.modele.Devis;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.modele.Voyage;
import metier.service.ServiceClient;
import metier.service.ServiceEmploye;
import metier.service.ServiceVoyage;
import util.LectureDonneesCsv;
import util.Saisie;

/**
 * Cette classe fournit les méthodes interactives (mode console)
 * relatives à l'objet métier Client.
 * @author Merlin
 */
public class VuesClient {
	
	/**
	 * Inscription interactive d'un client en mode console.
	 * TODO : gestion d'erreur si besoin
	 * @return Le Client nouvellement créé
	 */
	public static Client inscriptionInteractive() {
		Scanner input = new Scanner(System.in);
		PrintStream output = System.out;
		String[] description = new String[7];
		
		output.println("----- Client : inscription interactive -----");
		output.print("Civilité (M | MME) : ");
		description[0] = input.nextLine();
		output.print("Nom : ");
		description[1] = input.nextLine();
		output.print("Prénom : ");
		description[2] = input.nextLine();
		output.print("Date de naissance (exemple: 1992-06-24) : ");
		description[3] = input.nextLine();
		output.print("Adresse : ");
		description[4] = input.nextLine();
		output.print("Téléphone : ");
		description[5] = input.nextLine();
		output.print("E-mail : ");
		description[6] = input.nextLine();
		
		output.print("Mot de passe : ");
		String motDePasse;
		// Si possible, lire le mot de passe en masquant les caractères tapés
		if (System.console() != null)
			motDePasse = new String(System.console().readPassword());
		else
			motDePasse = input.nextLine();
		
		Client nouveau = LectureDonneesCsv.instancierClient(description, motDePasse);
		ServiceClient.creerClient(nouveau);
		output.print("--------------------------------------------\n");
		
		return nouveau;
	}
	
	/**
	 * @return Le client venant de se connecter, null sinon
	 */
	public static Client connexionInteractive() {
		System.out.println("----- Client : connexion interactive -------");
		System.out.println("Pour annuler, tapez `exit`");
		
		Client client = null;
		String entree = "";
		while (client == null && !entree.equals("exit")) {
			entree = Saisie.lireChaine("E-mail : ");
			client = ServiceClient.obtenirClientParEmail(entree);
		}
		
		if (entree.equals("exit"))
			return null;
		
		Integer tentativesRestantes = 3;
		Boolean valide = false;
		while (tentativesRestantes > 0 && !valide) {
			entree = Saisie.lireChaine("Mot de passe : ");
			valide = ServiceClient.testerMotDePasse(client, entree);
			
			if (!valide) {
				tentativesRestantes--;
				System.err.println("Mot de passe incorrect, " 
						+ tentativesRestantes + " tentatives restantes.");
			}
		}
		System.out.println("--------------------------------------------\n");
		
		if (valide)
			return client;
		else
			return null;
	}
	
	/**
	 * Permet à l'utilisateur de créer un devis :
	 * 1. Choix du type de recherche
	 * 2. Recherche par type de voyage ou par pays
	 * 3. Sélection du voyage parmi les propositions
	 * 4. Sélection du départ parmi les propositions
	 * 5. Confirmation du devis
	 * @param client Le client qui souhaite partir
	 * @return Le Devis créé, null sinon 
	 */
	public static Devis devisInteractif(Client client) {
		System.out.println("----- Établissement interactif d'un devis --");
		
		String question;
		Integer choix;
		
		// TODO: étape 1 (choix du type de recherche)
		
		// Choix du pays
		Pays destination = null;
		do {
			question = "\nEntrez le nom du pays de destination souhaité : ";
			String nomPays = Saisie.lireChaine(question);
			destination = ServiceVoyage.obtenirPays(nomPays);
			
			if (destination == null) {
				System.out.println("Il n'existe pas de voyages pour le pays demandé.");
			}
		} while (destination == null);
		
		// Liste des voyages de ce pays
		List<Voyage> voyages = ServiceVoyage.obtenirVoyagesParDestination(destination);
		System.out.println("\nVoyages à destination de " + destination.getNom() + " :");
		Integer i = 1;
		List<Integer> valeursPossibles = new ArrayList<Integer>();
		for (Voyage v : voyages) {
			if (v.getDeparts().size() > 0) {
				String typeVoyage = (v instanceof Circuit ? "[circuit]" : "[séjour]");
				System.out.println(i + ". " + typeVoyage + " " + v.getTitre());
				valeursPossibles.add(i);
				i++;
			}
		}
		
		if (i - 1 <= 0) {
			System.err.println("Aucun voyage n'a de départ pour ce pays.");
			return null;
		}
		
		question = "\nVotre choix (nombre de 1 à " + (i-1) + ") : ";
		choix = Saisie.lireInteger(question, valeursPossibles);
		Voyage voyage = voyages.get(choix - 1);
		
		// Liste des départs de ce voyage
		List<Depart> departs = voyage.getDeparts();
		System.out.println("\nDéparts disponibles :");
		i = 1;
		valeursPossibles.clear();
		for (Depart d : departs) {
			System.out.println(i + ". " + d.getDateDeDepart().toLocaleString()
								+ " de " + d.getVille()
								+ " à " + d.getPrix() + "€"
								+ " (" + d.getDescription() + ")");
			valeursPossibles.add(i);
			i++;
		}
		
		question = "\nVotre choix (nombre de 1 à " + (i-1) + ") : ";
		choix = Saisie.lireInteger(question, valeursPossibles);
		Depart depart = departs.get(choix - 1);
		
		Integer nbPersonnes;
		do {
			question = "\nCombien de personnes pour ce voyage ? ";
			nbPersonnes = Saisie.lireInteger(question);
		} while (nbPersonnes < 1);
		
		System.out.println("--------------------------------------------\n");
		
		
		Conseiller specialiste = ServiceEmploye.obtenirSpecialiste(voyage.getDestination());
		Devis devis = new Devis(client, depart, nbPersonnes, specialiste);
		
		return devis;
	}
	
	/**
	 * Simuler l'envoi d'un e-mail de confirmation au client,
	 * suite à l'établissement d'un devis.
	 * TODO: améliorer le format d'affichage des dates ?
	 * @param devis 
	 */
	public static void envoyerEmailConfirmation(Devis devis) {
		String email = devis.getClient().getEmail();
		String nom = devis.getClient().getNomComplet();
		
		System.out.println("--- E-mail de confirmation -----------------\n");
		String headers = "To: " + nom + "<"+email+">\n"
						+ "From: IF'Routard<devis@ifroutard.fr>\n"
						+ "Subject: Votre devis";
		
		
		String conseiller = devis.getConseiller().getNomComplet() 
					+ " ("+ devis.getConseiller().getEmail() +")";
		Voyage voyage = devis.getDepart().getVoyage();
		String specifique = "";
		if (voyage instanceof Circuit) {
			Circuit c = (Circuit)voyage;
			specifique = "Circuit (" + c.getNbJours() + " jours, "
						+ c.getNbKilometres() + "km, "
						+ c.getTransport() + ")";
		}
		else {
			Sejour s = (Sejour)voyage;
			specifique = "Séjour (" + s.getNbJours() + " jours, "
						+ s.getResidence() + ")";
		}
		float prixTotal = (devis.getDepart().getPrix() * devis.getNbPersonnes());
		
		String description = "Date : " + devis.getDateCreation() + "\n\n"
							+ "Votre conseiller pour ce voyage : " + conseiller + "\n\n"
							+ "Votre voyage : " + voyage.getTitre() + "\n"
							+ "Destination : " + voyage.getDestination().getNom() + "\n"
							+ specifique + "\n\n"
							+ "Départ : le " + devis.getDepart().getDateDeDepart()
									+ " de " + devis.getDepart().getVille() + "\n"
							+ "Transport aérien : " + devis.getDepart().getDescription() + "\n\n"
							+ voyage.getDescription() + "\n\n"
							+ "--------------------\n"
							+ "Nombre de voyageurs : " + devis.getNbPersonnes() + "\n"
							+ "Prix par personne : " + devis.getDepart().getPrix() + "€\n"
							+ "TOTAL : " + prixTotal + "€\n";
		
		
		String corps = "Cher " + nom + ",\n"
					+ "Veuillez trouver ci-dessous tous les détails de votre devis.\n\n"
					+ description;
		
		System.out.println(headers + '\n');
		System.out.println(corps);
		System.out.println("--------------------------------------------\n");
	}
}
