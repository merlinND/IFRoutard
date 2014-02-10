package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Merlin
 */
@Entity
public class Pays implements Serializable {
	private static final long serialVersionUID = 1L;
	public static enum Continent {
		ANTARCTIQUE,
		AMERIQUE_NORD,
		AMERIQUE_SUD,
		ASIE,
		EUROPE,
		OCEANIE
	};
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nom;
	private Continent continent;
	private int superficie;
	private int nbHabitants;
	private String langue;
	private String capitale;

	public Pays() {
	}
	public Pays(String nom) {
		this.nom = nom;
	}
	public Pays(String nom, Continent continent, int superficie, int nbHabitants, String langue, String capitale) {
		this.nom = nom;
		this.continent = continent;
		this.superficie = superficie;
		this.nbHabitants = nbHabitants;
		this.langue = langue;
		this.capitale = capitale;
	}
	
	/*
	 * GETTERS & SETTERS
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Continent getContinent() {
		return continent;
	}
	public void setContinent(Continent continent) {
		this.continent = continent;
	}
	public int getSuperficie() {
		return superficie;
	}
	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}
	public int getNbHabitants() {
		return nbHabitants;
	}
	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}
	public String getLangue() {
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
	public String getCapitale() {
		return capitale;
	}
	public void setCapitale(String capitale) {
		this.capitale = capitale;
	}
	
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Pays)) {
			return false;
		}
		Pays other = (Pays) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		//return "metier.modele.Pays[ id=" + id + ", nom="+ nom +" ]";
		return nom;
	}

}
