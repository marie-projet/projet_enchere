
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.gui.MainPane;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author marie et théo
 * 
 * affichage des boutons deconnexion, enchères et ventes dans l'entête
 */
public class EnteteAfterLogin extends HBox {
    private MainPane main;
    
    private Button vbLogout;
    private Button vbEnchere;
    private Button vbVente;
    
    public EnteteAfterLogin(MainPane main) {
        this.main = main;
        this.setAlignment(Pos.TOP_LEFT);
        
        this.vbLogout = new Button("Déconnexion");
        this.vbLogout.setMinWidth(100);
        this.vbLogout.setMaxWidth(100);
        this.vbLogout.setOnAction((event) -> {
            this.doLogout();
        });
        HBox categories =new HBox();
        categories.setAlignment(Pos.CENTER);
        this.vbEnchere = new Button("Enchères");
        this.vbEnchere.setMinWidth(70);
        this.vbEnchere.setMaxWidth(70);
        this.vbEnchere.setOnAction((event) -> {
            this.main.setMainContent(new MainAfterLogin(this.main));
        });
        this.vbVente = new Button("Ventes");
        this.vbVente.setMinWidth(60);
        this.vbVente.setMaxWidth(60);
        this.vbVente.setOnAction((event) -> {
            this.main.setMainContent(new MainVentes(this.main));
        });
        categories.getChildren().addAll(this.vbEnchere,this.vbVente);
        categories.setSpacing(50);
        this.getChildren().addAll(this.vbLogout,categories);
        this.setSpacing(250);
    }
    
    public void doLogout() {
        this.main.getSession().setCurUser(Optional.empty());
        this.main.setEntete(new EnteteLogin(this.main));
        this.main.setMainContent(new BienvenueMainVue(this.main));
    }
    
}
