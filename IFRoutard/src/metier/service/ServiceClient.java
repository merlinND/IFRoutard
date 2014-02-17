package metier.service;

import dao.ClientDao;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import metier.modele.Client;
import util.JpaUtil;

/**
 * Couche : Service
 * Objets métiers : Client
 * @author Merlin
 */
public class ServiceClient {

	public static String chiffrerMotDePasse(String clair) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA-1").digest(clair.getBytes());
			return new String(digest);
		}
		catch (NoSuchAlgorithmException ex) {
			System.err.println("L'algorithme SHA-1 n'est pas supporté dans cette VM !");
			System.err.println("Le mot de passe n'a pas été chiffré.");
			return clair;
		}
	}
	
	/**
	 * Teste si le mot de passe fourni est bien celui du client.
	 * Algorithme de chiffrement utilisé : SHA1
	 * @param client Le client qui souhaite se connecter
	 * @param clair Le mot de passe en clair
	 * @return 
	 */
	public static Boolean testerMotDePasse(Client client, String clair) {
		return client.getHashMotDePasse().equals(chiffrerMotDePasse(clair));
	}
	
	/**
	 * Insérer le client donné en base de données.
	 * @param client 
	 */
	public static void creerClient(Client client) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		ClientDao.creerClient(client);
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Insérer les clients donnés en base de données.
	 * @param clients
	 */
	public static void creerClients(List<Client> clients) {
		JpaUtil.creerEntityManager();
		JpaUtil.ouvrirTransaction();
		for (Client c : clients) {
			ClientDao.creerClient(c);
		}
		JpaUtil.validerTransaction();
		JpaUtil.fermerEntityManager();
	}
	
	/**
	 * Obtenir tous les clients disponibles en base de données.
	 * @return 
	 */
	public static List<Client> obtenirClients() {
		JpaUtil.creerEntityManager();
		List<Client> result = ClientDao.obtenirClients();
		JpaUtil.fermerEntityManager();
		return result;
	}
	
	/**
	 * 
	 * @param email
	 * @return L'instance si elle existe, null sinon
	 */
	public static Client obtenirClientParEmail(String email) {
		JpaUtil.creerEntityManager();
		Client result = ClientDao.obtenirClientParEmail(email);
		JpaUtil.fermerEntityManager();
		return result;
	}
	
}
