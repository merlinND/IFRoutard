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
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String code;
	private String nom;
	private String region;
	private String regime;
	/** Superficie (en km^2) */
	private int superficie;
	/** Population (en nombre d'habitants) */
	private int population;
	private String langue;
	private String capitale;

	public Pays() {
	}
	public Pays(String nom) {
		this.nom = nom;
	}
	public Pays(String code, String nom, String region, String regime,
			int superficie, int nbHabitants, String langue, String capitale) {
		this.code = code;
		this.nom = nom;
		this.region = region;
		this.regime = regime;
		this.superficie = superficie;
		this.population = nbHabitants;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegime() {
		return regime;
	}
	public void setRegime(String regime) {
		this.regime = regime;
	}
	public int getSuperficie() {
		return superficie;
	}
	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
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
