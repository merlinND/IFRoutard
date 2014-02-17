/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 *
 * @author Administrateur
 */
@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Civilite {M ,MME};
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String nom;
    private String prenom;
    private Civilite civilite;
    private String telephone;
    private  String email;
    private int code;
    private int hashMotDePasse;
    
    @Embedded
    private Adresse adresse;

    /*@Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeNaissance;*/

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Client() {
    }

    public Client(String nom, String prenom,Civilite civilite, String telephone, String email, Adresse adresse, int code, int hashMotDePasse/*, Date dateDeNaissance*/) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.code = code;
        this.hashMotDePasse = hashMotDePasse;
       /* this.dateDeNaissance = dateDeNaissance;*/
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

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public int getCode() {
        return code;
    }

    public int getHashMotDePasse() {
        return hashMotDePasse;
    }

  /*  public Date getDateDeNaissance() {
        return dateDeNaissance;
    }*/

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setHashMotDePasse(int hashMotDePasse) {
        this.hashMotDePasse = hashMotDePasse;
    }

   /* public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }*/
    
      public void setId(Long id) {
        this.id = id;
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
        return "metier.modele.Client[ id=" + id + " ]"+" \n"+ "nom : " + nom + " \n"+ "prénom : " + prenom + "\n"+ "civilité : " + civilite + "\n"+"téléphone : " + telephone + "\n"+ "code : " + code + "\n" + "email : " + email + "\n"+"adresse : "+ adresse + " \n"+ "hashMotDePasse : " + hashMotDePasse + "\n";
    }
    
}
