/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.winkler.gui;

import fr.insa.winkler.gui.vues.EnteteLogin;
import fr.insa.winkler.projet.BdD;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author mariewinkler
 */
public class MainPane extends BorderPane{
    //private File file;
    //private Controleur controleur; 
    private HBox entete;
    private ScrollPane mainContent;
    private SessionInfo session;

    private Stage stage;
    
        public void setEntete(Node c) {
        this.setTop(c);
    }
    
    public void setMainContent(Node c) {
        this.mainContent.setContent(c);
    }
    
  
    public MainPane(Stage st) {
        this.stage=st;
        this.session = new SessionInfo();
        this.mainContent = new ScrollPane();
        this.setCenter(this.mainContent);
        JavaFXUtils.addSimpleBorder(this.mainContent);
         try {
            this.session.setConBdD(BdD.defautConnect());
            this.setEntete(new EnteteLogin(this));
            this.setMainContent(new BienvenueMainVue(this));
        } catch (ClassNotFoundException | SQLException ex) {
            this.setMainContent(new BdDNonAccessible(this));
        }
        
    }

    public MenuButton getMbUtilisateur() {
        return mbUtilisateur;
    }

    public Button getbConnexion() {
        return bConnexion;
    }

    public Button getbNouvelUtilisateur() {
        return bNouvelUtilisateur;
    }

    public TextArea getMessage() {
        return message;
    }

    public ScrollPane getMainContent() {
        return mainContent;
    }

    public SessionInfo getSession() {
        return session;
    }

    public Stage getStage() {
        return stage;
    }
    
}
