
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author marie et théo
 * 
 * affichage des boutons login et nouvel utilisateur dans l'entête
 */
public class EnteteLogin extends HBox {

    private MainPane main;
    
    private Button bLogin;
    private Button vNouvelUtilisateur;

    public EnteteLogin(MainPane main) {
        this.setAlignment(Pos.CENTER);

                this.main = main;
        
        this.bLogin = new Button("Connexion");
        this.bLogin.setMinWidth(90);
        this.bLogin.setMaxWidth(90);
        this.bLogin.setOnAction((event) -> {
            this.main.setMainContent(new Login(this.main));
        });
        this.vNouvelUtilisateur = new Button("Nouvel utilisateur");
        this.vNouvelUtilisateur.setMinWidth(130);
        this.vNouvelUtilisateur.setMaxWidth(130);
        this.vNouvelUtilisateur.setOnAction((t) -> {
            this.main.setMainContent(new NouvelUtilisateur(this.main));
        });
        this.getChildren().addAll(this.bLogin,this.vNouvelUtilisateur);
        this.setSpacing(50);
    }
}


