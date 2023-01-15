
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.gui.MainPane;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


/**
 *
 * @author marie et théo
 */
public class BdDNonAccessible extends VBox {
    
    private MainPane main;
    
    public BdDNonAccessible(MainPane main) {
        this.main = main;
        this.setSpacing(25);
        BigLabel erreur=new BigLabel("BDD non accesible",39);
        this.getChildren().addAll(erreur);
        this.setMinWidth(780);
        this.setAlignment(Pos.CENTER);
    }
    
}
