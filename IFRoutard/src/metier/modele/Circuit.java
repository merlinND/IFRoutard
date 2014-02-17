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
public class Circuit extends Voyage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transport;
    private float nbKilometres;

    public Circuit() {
    }

    public Circuit(String transport, float nbKilometres, int code, String titre, int nbJours, String description, Pays destination) {
	super(code, titre, nbJours, description, destination);
	this.transport = transport;
	this.nbKilometres = nbKilometres;
    }

    

    public String getTransport() {
        return transport;
    }

    public float getNbKilometres() {
        return nbKilometres;
    }


    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setNbKilometres(float nbKilometres) {
        this.nbKilometres = nbKilometres;
    }


    @Override
    public String toString() {
         return "metier.modele.Circuit[ id=" + getId() + " ]"+" \n"+ "transport : " + getTransport() +" \n"+ "nbKilometres : " + getNbKilometres() + " \n"+ "code : " + getCode() + " \n"+ "titre : " + getTitre() + " \n"+ "nbJours : " + getNbJours() + " \n"+ "description : " + getDescription();

    }
    
}
