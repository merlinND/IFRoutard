package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Romain
 */
@Entity
public class Devis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int numero;
    private int nbPersonnes;
    @OneToOne
    private Conseiller conseiller;
    @OneToOne
    private Client client;
    @OneToOne
    private Depart depart;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreation;

    public Devis() {
    }

    public Devis(int numero, int nbPersonnes, Conseiller conseiller, Client client, Depart depart, Date date) {
	this.numero = numero;
	this.nbPersonnes = nbPersonnes;
	this.conseiller = conseiller;
	this.client = client;
	this.depart = depart;
	this.dateCreation = date;
    }
    
    
    public Long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Depart getDepart() {
	return depart;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }

    public void setDepart(Depart depart) {
	this.depart = depart;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
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
        if (!(object instanceof Devis)) {
            return false;
        }
        Devis other = (Devis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
                return "metier.modele.Devis[ id=" + id + " ]" + " \n" 
				+ "Numero : " + numero + " \n"+ "Nombre de personne : " + nbPersonnes + " \n"+ "Conseiller : " + conseiller.getNom() + ",\n"
				+ "Client : " + client.getNom()+ " \n" + "Départ :  " + depart.getDateDeDepart() + " \n"
				+ "Date du devis : " + dateCreation+ "\n";
    }
    
}
