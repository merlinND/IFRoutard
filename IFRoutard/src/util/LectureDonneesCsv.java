package util;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import metier.modele.Circuit;
import metier.modele.Client;
import metier.modele.Conseiller;
import metier.modele.Depart;
import metier.modele.Pays;
import metier.modele.Sejour;
import metier.modele.Voyage;
import metier.service.ServiceClient;
import metier.service.ServiceEmploye;
import metier.service.ServiceVoyage;

/**
 * La classe LectureDonneesCsv permet (comme son nom l'indique) la lecture de données CSV
 * dans des fichiers. Elle doit être complétée et personnalisée pour interagir avec VOTRE couche
 * service pour la création effective des entités. Elle comprend initialement la lecture d'un fichier
 * Clients et d'un fichier Pays. Une méthode {@link main()} permet de tester cette classe avant de
 * l'intégrer dans le reste de votre code.
 * @author Équipe DASI - 2013/2014
 */

public class LectureDonneesCsv {

	/**
	 * Format de date pour la lecture des dates dans les fichiers CSV fournis.
	 */
	protected static DateFormat CSV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Format de date pour l'affichage à l'écran.
	 */
	protected static DateFormat USER_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * Le lecteur de fichier CSV.
	 * Il doit être initialisé avant l'appel aux méthodes de la classe.
	 */
	protected CSVReader lecteurFichier;

	/**
	 * Unique constructeur de la classe. Le fichier CSV donné en paramètre doit
	 * avoir le point-virgule ';' comme séparateur et être encodé en UTF-8. Le fichier est
	 * immédiatement ouvert (en lecture) par ce constructeur.
	 * @param cheminVersFichier Chemin vers le fichier CSV.
	 * @throws FileNotFoundException Si le chemin vers le fichier n'est pas valide ou le fichier non-lisible.
	 * @throws UnsupportedEncodingException Si l'encodage n'est pas supporté
	 */
	public LectureDonneesCsv(String cheminVersFichier) throws FileNotFoundException, UnsupportedEncodingException {

		this.lecteurFichier = new CSVReader(new InputStreamReader(new FileInputStream(cheminVersFichier), "UTF-8"), ';');
	}
	
	/**
	 * Ferme le fichier CSV. Les autres méthodes ne doivent plus être appelées après cela.
	 * @throws IOException 
	 */
	public void fermer() throws IOException {

		this.lecteurFichier.close();
	}

	/**
	 * Méthode statique pour lire une date à partir d'une chaîne de caractère.
	 * Adapté au format de date des fichiers CSV fournis, par exemple: 2014-02-01.
	 * @param date Chaîne de caractère représentant la date.
	 * @return La date interpétée ou la date actuelle en cas mauvais format en entrée.
	 */
	public static Date parseDate(String date) {
		try {
			return CSV_DATE_FORMAT.parse(date);
		} catch (ParseException ex) {
			return new Date();
		}
	}
	
	/**
	 * Méthode statique pour formater une date pour l'affichage.
	 * Par exemple: 01/02/2014.
	 * @param date Date à formater.
	 * @return Chaîne de caractère représentant la date.
	 */
	protected static String formatDate(Date date) {
		return USER_DATE_FORMAT.format(date);
	}

	/**
	 * Méthode statique pour afficher l'en-tête d'un fichier CSV lu par le lecteur.
	 * L'affichage se fait sur la "sortie d'erreur" (en rouge dans la console sous Netbeans).
	 * Le nom des colonnes est précédé de leur index dans le tableau (commençant à 0).
	 * @param colonnes le tableau des noms de colonnes.
	 */
	protected static void afficherEnTeteCsv(String[] colonnes) {
		
		for (int i=0; i<colonnes.length; i++) {
			if (i>0) {
				System.err.print("; " );
			}
			System.err.print("#" + Integer.toString(i) + " => " + colonnes[i] );
		}
		System.err.println();
	}
	
	/**
	 * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Client pour chaque ligne.
	 * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
	 * @throws IOException 
	 */
	public void lireClients(int limite) throws IOException {

		String[] nextLine;

		 // En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		//afficherEnTeteCsv(nextLine);

		List<Client> nouveauxClients = new ArrayList<Client>();
		// Lecture des lignes
		while ((nextLine = this.lecteurFichier.readNext()) != null) {

			nouveauxClients.add(instancierClient(nextLine));

			// Limite (ou -1 si pas de limite)
			if ( !(limite < 0) && (--limite < 1) ) {
				break;
			}
		}

		// On persiste tous ces nouveaux clients en une grande transaction
		ServiceClient.creerClients(nouveauxClients);
	}
	
	
	protected static Client instancierClient(String[] descriptionClient) {
		// Instanciation avec un mot de passe par défaut
		// car les données de test ne contiennent pas de mot de passe
		return instancierClient(descriptionClient, "password");
	}
	
	/**
	 * Créée un Client à partir de sa description.
	 * La date de naissance est notamment interpétée comme un objet Date.
	 * @param descriptionClient Ligne du fichier CSV de Clients.
	 * @param motDePasse Le mot de passe de ce client, en clair
	 * @return L'instance de Client correspondante
	 */
	public static Client instancierClient(String[] descriptionClient, String motDePasse) {
		
		String civiliteS = descriptionClient[0];
		Client.Civilite civilite = Client.Civilite.fromString(civiliteS);
		String nom = descriptionClient[1];
		String prenom = descriptionClient[2];
		Date dateNaissance = parseDate(descriptionClient[3]);
		String adresse = descriptionClient[4];
		String telephone = descriptionClient[5];
		String email = descriptionClient[6];
		
		//System.out.println("Client: "+  civilite + " " + nom + " " + prenom + ", né le " + formatDate(dateNaissance) + ", habitant à " + adresse + ", téléphone: " + telephone + ", e-mail: " + email);
		
		return new Client(nom, prenom, civilite, dateNaissance, 
						  telephone, email, adresse, motDePasse);
	}
	
	public void lirePays(int limite) throws IOException {
		String[] nextLine;

		 // En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		//afficherEnTeteCsv(nextLine);

		List<Pays> nouveauxPays = new ArrayList<Pays>();
		// Lecture des lignes
		while ((nextLine = this.lecteurFichier.readNext()) != null) {

			nouveauxPays.add(instancierPays(nextLine));

			// Limite (ou -1 si pas de limite)
			if ( !(limite < 0) && (--limite < 1) ) {
				break;
			}
		}

		// On persiste tous ces pays en une grande transaction
		ServiceVoyage.creerPays(nouveauxPays);
	}
	
	/**
	 * Créée un Pays à partir de sa description.
	 * La superficie et la population sont notamment interpétées comme des nombres.
	 * @param descriptionPays Ligne du fichier CSV de Pays.
	 * @return L'instance de Pays correspondant
	 */
	public static Pays instancierPays(String[] descriptionPays) {
		
		String nom = descriptionPays[0];
		String code = descriptionPays[1];
		String region = descriptionPays[2];
		String capitale = descriptionPays[3];
		String langues = descriptionPays[4];
		Integer superficie = (int)Float.parseFloat(descriptionPays[5]);
		Float populationF = Float.parseFloat(descriptionPays[6]);
		Integer population = (int)(populationF * 1000);
		String regime = descriptionPays[7];
		
		//System.out.println("Pays: "+  nom + " [" + code + "] (" + regime + "), Capitale: " + capitale + ", Région: " + region + ", Langues: " + langues + ", " + superficie + " km², " + population + " millions d'hbitants");
		
		return new Pays(code, nom, region, regime, superficie, population, langues, capitale);
	}
	
	/**
	 * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Pays pour chaque ligne.
	 * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
	 * @throws IOException 
	 */
	public void lireConseillers(int limite) throws IOException {
		String[] nextLine;
		
		 // En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		//afficherEnTeteCsv(nextLine);
		
		List<Conseiller> nouveauxConseillers = new ArrayList<Conseiller>();
		// Lecture des lignes
		while ((nextLine = this.lecteurFichier.readNext()) != null) {
		
			nouveauxConseillers.add(instancierConseillers(nextLine));
			
			// Limite (ou -1 si pas de limite)
			if ( !(limite < 0) && (--limite < 1) ) {
				break;
			}
		}
		
		ServiceEmploye.creerConseillers(nouveauxConseillers);
	}
	
	/**
	 * Créée un Conseiller à partir de sa description.
	 * @param descriptionConseillers Ligne du fichier CSV de Conseiller.
	 * @return L'instance de Pays correspondant
	 */
	public static Conseiller instancierConseillers(String[] descriptionConseillers) {
		
		String civiliteS = descriptionConseillers[0];
		String nom = descriptionConseillers[1];
		String prenom = descriptionConseillers[2];
		Date dateNaissance = parseDate(descriptionConseillers[3]);
		String adresse = descriptionConseillers[4];
		String telephone = descriptionConseillers[5];
		String email = descriptionConseillers[6];
		
		Conseiller c = new Conseiller(civiliteS, nom , prenom, dateNaissance,
									adresse, telephone, email);
		
		for (int i = 7; i < descriptionConseillers.length; ++i) {
			Pays specialite = ServiceVoyage.obtenirPaysParCode(descriptionConseillers[i]);
			if (specialite != null)
				c.addSpecialite(specialite);
		}
		
		return c;
	}
	
	/**
	 * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Voyage pour chaque ligne.
	 * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
	 * @throws IOException 
	 */
	public void lireVoyagesCircuit(int limite) throws IOException {
		String[] nextLine;
		// En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		//afficherEnTeteCsv(nextLine);
		
		List<Voyage> nouveauxVoyages = new ArrayList<Voyage>();
		// Lecture des lignes
		while ((nextLine = this.lecteurFichier.readNext()) != null) {
			Circuit circuit = instancierVoyageCircuit(nextLine);
			if (circuit != null)
				nouveauxVoyages.add(circuit);
			
			// Limite (ou -1 si pas de limite)
			if ( !(limite < 0) && (--limite < 1) ) {
				break;
			}
		}
		
		// On persiste tous ces pays en une grande transaction
		ServiceVoyage.creerVoyages(nouveauxVoyages);
	}
	
	/**
	 * Créée un Voyage à partir de sa description.
	 * @param descriptionVoyage Ligne du fichier CSV de Voyage.
	 * @return L'instance de Voyage correspondant
	 */
	public static Circuit instancierVoyageCircuit(String[] descriptionVoyage) {

		String code = descriptionVoyage[1];
		String titre = descriptionVoyage[2];
		Integer duree = Integer.parseInt(descriptionVoyage[3]);
		String description = descriptionVoyage[4]; 
		String transport = descriptionVoyage[5];
		Float km = Float.parseFloat(descriptionVoyage[6]);
		
		String codeDestination = descriptionVoyage [0];
		Pays destination = ServiceVoyage.obtenirPaysParCode(codeDestination);
		
		if (destination != null)
			return new Circuit(transport, km, code, titre, duree, description, destination);
		else {
			System.err.println("Aucun pays de code " + codeDestination + " n'a été trouvé.");
			return null;
		}
	}
	public void lireVoyagesSejour(int limite) throws IOException {
		String[] nextLine;
		// En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		//afficherEnTeteCsv(nextLine);
		
		List<Voyage> nouveauxVoyages = new ArrayList<Voyage>();
		// Lecture des lignes
		while ((nextLine = this.lecteurFichier.readNext()) != null) {
			Sejour sejour = instancierVoyageSejour(nextLine);
			if (sejour != null)
				nouveauxVoyages.add(sejour);
			
			// Limite (ou -1 si pas de limite)
			if ( !(limite < 0) && (--limite < 1) ) {
				break;
			}
		}
		
		// On persiste tous ces voyages en une grande transaction
		ServiceVoyage.creerVoyages(nouveauxVoyages);
	}
	
	/**
	 * Créée un Voyage à partir de sa description.
	 * @param descriptionVoyage Ligne du fichier CSV de Voyage.
	 * @return L'instance de Voyage correspondant
	 */
	public static Sejour instancierVoyageSejour(String[] descriptionVoyage) {
		String code = descriptionVoyage[1];
		String titre = descriptionVoyage[2];
		String description = descriptionVoyage[4];
		String residence = descriptionVoyage[5];
		Integer duree = Integer.parseInt(descriptionVoyage[3]);
		
		String codeDestination = descriptionVoyage[0];
		Pays destination = ServiceVoyage.obtenirPaysParCode(codeDestination);
		
		if (destination != null)
			return new Sejour(residence, code, titre, duree, description, destination);
		else {
			System.err.println("Aucun pays de code " + codeDestination + " n'a été trouvé.");
			return null;
		}
	}
	
	public void lireDeparts(int limite) throws IOException {

		String[] nextLine;

		// En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		//afficherEnTeteCsv(nextLine);

		List<Depart> nouveauxDeparts = new ArrayList<Depart>();
		// Lecture des lignes
		while ((nextLine = this.lecteurFichier.readNext()) != null) {
			Depart depart = instancierDepart(nextLine);
			if (depart != null)
				nouveauxDeparts.add(depart);

			// Limite (ou -1 si pas de limite)
			if ( !(limite < 0) && (--limite < 1) ) {
				break;
			}
		}
		
		ServiceVoyage.creerDeparts(nouveauxDeparts);
	}
	
	public static Depart instancierDepart(String[] descriptionDepart) {		
		String codeVoyage = descriptionDepart[0];
		Voyage voyage = ServiceVoyage.obtenirVoyageParCode(codeVoyage);
		
		Date dateDepart = parseDate(descriptionDepart[1]);
		String ville = descriptionDepart[2];
		Float prix = Float.parseFloat(descriptionDepart[3]);
		String description = descriptionDepart[4];
		
		Depart d = new Depart(prix, dateDepart, ville, description);
		voyage.addDepart(d);
		
		return d;
	}
	
	
	/**
	 * Cette méthode main() permet de tester cette classe avant de l'intégrer dans votre code.
	 * Elle exploite initialement un fichier de Client et un fichier de Pays, en limitant la lecture aux
	 * 10 premiers éléments de chaque fichier.
	 * @param args non utilisé ici
	 */
	public static void main(String[] args) {
		
		try {
			String fichierClients = "res/data/IFRoutard-Clients.csv";
			LectureDonneesCsv lectureDonneesCsv_Clients = new LectureDonneesCsv(fichierClients);
			// Pour tester: limite à 10
			lectureDonneesCsv_Clients.lireClients(10);
			// Puis, quand tout est au point!
			//lectureDonneesCsv.lireClients(-1);
			lectureDonneesCsv_Clients.fermer();

			String fichierPays = "res/data/IFRoutard-Pays.csv";
			LectureDonneesCsv lectureDonneesCsv_Pays = new LectureDonneesCsv(fichierPays);
			lectureDonneesCsv_Pays.lirePays(10);
			lectureDonneesCsv_Pays.fermer();
			
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}

	}
}
