/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import metier.service.ServiceClient;


/**
 *
 * @author Administrateur
 */
@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Civilite {
		M,
		MME;

		public static Civilite fromString(String string) {
			if(string.equalsIgnoreCase("M") || string.equalsIgnoreCase("M."))
				return M;
			else if (string.equalsIgnoreCase("MME") || string.equalsIgnoreCase("MME.")
					|| string.equalsIgnoreCase("MM")
					|| string.equalsIgnoreCase("MLLE") || string.equalsIgnoreCase("MLLE."))
				return MME;
			else
				throw new UnsupportedOperationException("Civilité inconnue : " + string);
		}
	};
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String nom;
    private String prenom;
    private Civilite civilite;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dateDeNaissance;
	
    private String telephone;
    private  String email;
    private String adresse;

	private String hashMotDePasse;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	@OrderBy("dateCreation DESC")
    private List<Devis> devis = new ArrayList<Devis>();

    public Client() {
    }

	/**
	 * 
	 * @param nom
	 * @param prenom
	 * @param civilite
	 * @param dateDeNaissance
	 * @param telephone
	 * @param email
	 * @param adresse
	 * @param motDePasse Le mot de passe en clair 
	 */
    public Client(String nom, String prenom, Civilite civilite, Date dateDeNaissance,
				String telephone, String email, String adresse, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
		this.dateDeNaissance = dateDeNaissance;
		
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        
		this.hashMotDePasse = ServiceClient.chiffrerMotDePasse(motDePasse);
    }
    
	public static long getSerialVersionUID() {
        return serialVersionUID;
    }
	public Long getId() {
        return id;
    }
	public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public Civilite getCivilite() {
        return civilite;
    }
	public String getNomComplet() {
		return getCivilite() + " " + getPrenom() + " " + getNom();
	}
	
	public Date getDateDeNaissance() {
        return dateDeNaissance;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getEmail() {
        return email;
    }
    public String getAdresse() {
        return adresse;
    }
    public String getHashMotDePasse() {
        return hashMotDePasse;
    }
	
	public List<Devis> getDevis() {
		return devis;
	}

	public void setId(Long id) {
        this.id = id;
    } 
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }
	public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
	}
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setHashMotDePasse(String hashMotDePasse) {
        this.hashMotDePasse = hashMotDePasse;
    }
	
	public void addDevis(Devis devis) {
		if (!this.devis.contains(devis))
			this.devis.add(devis);
	}
	public void removeDevis(Devis devis) {
		this.devis.remove(devis);
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
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "metier.modele.Client[ id=" + id + " ]" + " \n" 
				+ "Nom complet : " + civilite + " " + nom + " " + prenom + ",\n"
				+ "Téléphone : " + telephone + ", " + "E-mail : " + email + ", "
				+ "Adresse : " + adresse + "\n";
    }
    
}
