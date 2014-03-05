package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Merlin
 */
@Entity
public class Conseiller extends Employe implements Serializable {
	
	@OneToMany
	private List<Pays> specialites = new ArrayList<Pays>();

	@OneToMany
	private List<Devis> devis = new ArrayList<Devis>();
	public Conseiller() {
		super();
	}
	public Conseiller(String civilite, String nom, String prenom, Date dateDeNaissance, 
						String adresse, String telephone, String email) {
		super(civilite, nom, prenom, dateDeNaissance, adresse, telephone, email);
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public List<Pays> getSpecialites() {
		return specialites;
	}
	public void addSpecialite(Pays pays) {
		this.specialites.add(pays);
	}
	
	@Override
	public String toString() {
		String descriptionSpecialites = "";
		for (Pays p : getSpecialites())
			descriptionSpecialites += p + ", ";
		descriptionSpecialites = descriptionSpecialites.substring(0, descriptionSpecialites.length() - 2);
		
		return "metier.modele.Conseiller[ id=" + getId() + " ]\n"
				+ getCivilite() + ' ' + getPrenom() + ' ' + getNom() + '\n'
				+ "Spécialités : " + descriptionSpecialites + '\n';
		
	}

}
