package vue;

import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;
import metier.modele.Client;
import metier.service.ServiceClient;
import util.LectureDonneesCsv;

/**
 * Cette classe fournit une méthode permettant d'inscrire interactivement
 * un client en mode console.
 * @author Merlin
 */
public class InscriptionClient {
	
	/**
	 * TODO : gestion d'erreur
	 * TOOD : utiliser leur classe Saisie ?
	 * @return Le Client nouvellement créé
	 */
	public static Client inscriptionInteractive() {
		Scanner input = new Scanner(System.in);
		PrintStream output = System.out;
		String[] description = new String[7];
		
		output.print("----- Client : inscription interactive -----\n");
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
	
}
