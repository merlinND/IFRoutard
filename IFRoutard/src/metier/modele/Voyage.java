/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    
    private String code;
    private String titre;
    private int nbJours;
    private String description;
    @OneToOne
    private String codeDestination;
    @OneToMany
    private List<Depart> depart = new ArrayList<Depart>();
    public Voyage() {
    }

    public Voyage(String code, String titre, int nbJours, String description, String codeDestination) {
	this.code = code;
	this.titre = titre;
	this.nbJours = nbJours;
	this.description = description;
	this.codeDestination = codeDestination;
    }   

    public Voyage(String code) {
        this.code = code;
    }


    public Long getId() {
        return id;
    }

    public String getCode() {
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

    public String getCodeDestination() {
        return codeDestination;
    }

    public List<Depart> getDepart() {
        return depart;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
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

    public void setCodeDestination(String codeDestination) {
	this.codeDestination = codeDestination;
    }

    public void setDestinations(List<Depart> depart) {
        this.depart = depart;
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


