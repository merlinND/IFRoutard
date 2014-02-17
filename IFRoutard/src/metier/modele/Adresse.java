/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrateur
 */
@Embeddable
public class Adresse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    int numero;
    String rue;
    String complement;
    String ville;
    String pays;
    int codePostal;

    public Adresse() {
    }

    @Override
    public String toString() {
        return "metier.modele.Adresse[  ]"+" \n"+ numero +  " rue " + rue + "\n"+ "complement : " + complement + "\n"+ "codePostal : " + codePostal + " ville : " + ville + " pays : "+ pays +"\n";
    }
    
}
