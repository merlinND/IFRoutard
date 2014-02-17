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
import metier.modele.Client;
import metier.modele.Pays;
import metier.service.ServiceClient;
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
	protected static Date parseDate(String date) {
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
		afficherEnTeteCsv(nextLine);
		
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
	
	/**
	 * Créée un Client à partir de sa description.
	 * La date de naissance est notamment interpétée comme un objet Date.
	 * @param descriptionClient Ligne du fichier CSV de Clients.
	 * @return L'instance de Client correspondante
	 */
	public Client instancierClient(String[] descriptionClient) {
		
		String civiliteS = descriptionClient[0];
		Client.Civilite civilite = Client.Civilite.fromString(civiliteS);
		String nom = descriptionClient[1];
		String prenom = descriptionClient[2];
		Date dateNaissance = parseDate(descriptionClient[3]);
		String adresse = descriptionClient[4];
		String telephone = descriptionClient[5];
		String email = descriptionClient[6];
		
		//System.out.println("Client: "+  civilite + " " + nom + " " + prenom + ", né le " + formatDate(dateNaissance) + ", habitant à " + adresse + ", téléphone: " + telephone + ", e-mail: " + email);
		
		// Mot de passe constant pour tout le monde car non précisé dans les données de test
		String motDePasse = "password";
		
		return new Client(nom, prenom, civilite, dateNaissance, 
						  telephone, email, adresse, motDePasse);
	}

	/**
	 * Lit le fichier CSV, affiche son en-tête, puis appelle la création de Pays pour chaque ligne.
	 * @param limite Nombre maximum de lignes à lire ou -1 pour ne pas limiter
	 * @throws IOException 
	 */
	public void lirePays(int limite) throws IOException {

		String[] nextLine;

		 // En-tete du fichier CSV
		nextLine = this.lecteurFichier.readNext();
		afficherEnTeteCsv(nextLine);
		
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
	public Pays instancierPays(String[] descriptionPays) {
		
		String nom = descriptionPays[0];
		String code = descriptionPays[1];
		String region = descriptionPays[2];
		String capitale = descriptionPays[3];
		String langues = descriptionPays[4];
		Integer superficie = Integer.parseInt(descriptionPays[5]);
		Float populationF = Float.parseFloat(descriptionPays[6]);
		Integer population = (int)(populationF * 1000);
		String regime = descriptionPays[7];
		
		//System.out.println("Pays: "+  nom + " [" + code + "] (" + regime + "), Capitale: " + capitale + ", Région: " + region + ", Langues: " + langues + ", " + superficie + " km², " + population + " millions d'hbitants");
		
		// À implémenter...
		return new Pays(code, nom, region, regime, superficie, population, langues, capitale);
		//Service.instancierPays(pays);
		
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
