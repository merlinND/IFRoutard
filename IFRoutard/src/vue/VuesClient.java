package vue;

import java.io.PrintStream;
import java.util.Scanner;
import metier.modele.Client;
import metier.service.ServiceClient;
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
	 * TODO : gestion d'erreur
	 * TOOD : utiliser leur classe Saisie ?
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
	 * TODO : choisir comment gérer les erreurs d'e-mail / mot de passe
	 * @return Le client venant de se connecter, null sinon
	 */
	public static Client connexionInteractive() {
		System.out.println("----- Client : connexion interactive -----");
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
		
		if (valide)
			return client;
		else
			return null;
	}
	
}
