/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Administrateur
 */
@Entity
public class Sejour extends Voyage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String residence;

   
    public Sejour() {
    }

    public Sejour(String residence, int code, String titre, int nbJours, String description, Pays destination) {
	super(code, titre, nbJours, description, destination);
	this.residence = residence;
    }     
    
    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }





    

    @Override
    public String toString() {
       return "metier.modele.Sejour[ id=" + getId() + " ]"+" \n"+ "residence : " 
	       + getResidence() + " \n"+ "code : " + getCode() + " \n"+ "titre : " 
	       + getTitre() + " \n"+ "nbJours : " + getNbJours() 
	       + " \n"+ "description : " + getDescription()+ " \n";
    }
    
}
