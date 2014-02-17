/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Administrateur
 */
@Entity
@Inheritance ( strategy = InheritanceType.JOINED)
abstract public class Voyage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private int code;
    private String titre;
    private int nbJours;
    private String description;

    public Voyage() {
    }

    public Voyage(int code, String titre, int nbJours, String description) {
        this.code = code;
        this.titre = titre;
        this.nbJours = nbJours;
        this.description = description;
    }
     
     

    public Long getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public String getTitre() {
        return titre;
    }

    public int getNbJours() {
        return nbJours;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setNbJours(int nbJours) {
        this.nbJours = nbJours;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Voyage)) {
            return false;
        }
        Voyage other = (Voyage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "metier.modele.Voyage[ id=" + id + " ]"+ " \n"+ "code : " + code + " \n"+ "titre : " + titre + " \n"+ "nbJours : " + nbJours + " \n"+ "description : " + description;
    }
    
}

