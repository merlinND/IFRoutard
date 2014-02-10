package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
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
	// TODO: ajouter le champ devis
	
	public Conseiller() {
		super();
	}
	public Conseiller(String nom, String prenom, String email) {
		super(nom, prenom, email);
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
		
		return "metier.modele.Conseiller[ id=" + getId() + " ]\n"
				+ "Spécialités : " + descriptionSpecialites;
		
	}

}
