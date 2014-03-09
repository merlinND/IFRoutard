package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Romain
 */
@Entity
public class Depart implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float prix;
	@Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeDepart;
    private String ville;
    private String description;
    @ManyToOne
    private Voyage voyage; 
    
    public Depart() {
    }

    public Depart(float prix, Date dateDeDepart, String ville, String description) {
        this.prix = prix;
        this.dateDeDepart = dateDeDepart;
        this.ville = ville;
        this.description = description;
    }
	
    public float getPrix() {
        return prix;
    }

    public Date getDateDeDepart() {
        return dateDeDepart;
    }

    public String getVille() {
        return ville;
    }

    public String getDescription() {
        return description;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDateDeDepart(Date dateDeDepart) {
        this.dateDeDepart = dateDeDepart;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
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
        if (!(object instanceof Depart)) {
            return false;
        }
        Depart other = (Depart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "metier.modele.Depart[ "+ getId() +" ]"+" \n"+ prix +  " euros, de : " + ville 
				+  " le : " + dateDeDepart 
				+ " pour un voyage en direction de :"+ voyage.getDestination().getNom() 
				+"\n"+ "description : " + description +"\n";
    }
    
}
