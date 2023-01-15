
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
 * 
 * affichage de la page d'accueil avec titre et image
 */
public class BienvenueMainVue extends VBox{
    
    private MainPane main;
    
    public BienvenueMainVue(MainPane main) {
        this.main = main;
        this.setSpacing(25);
        BigLabel accueil=new BigLabel("Bienvenue sur notre site de vente aux enchères",39);
        this.getChildren().addAll(accueil);
        Image image1 = new Image(new File("src/main/java/fr/insa/winkler/gui/vues/image/enchere.png").toURI().toString());
        ImageView im=new ImageView();
        im.setImage(image1);
        im.setFitHeight(400);
        im.setFitWidth(640);
        this.getChildren().addAll(im);
        this.setMinWidth(780);
        this.setAlignment(Pos.CENTER);
    }
    
}
