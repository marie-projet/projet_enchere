/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.winkler.gui;

import fr.insa.winkler.gui.vues.BienvenueMainVue;
import fr.insa.winkler.gui.vues.EnteteAfterLogin;
import fr.insa.winkler.gui.vues.EnteteLogin;
import fr.insa.winkler.projet.BdD;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
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
    private Controleur controleur; 

    private Stage stage;
    
        public void setEntete(Node c) {
        this.setTop(c);
    }
    
    public void setMainContent(Node c) {
        this.mainContent.setContent(c);
    }
    
  
    public MainPane(Stage st) {
        this.stage=st;
        this.controleur= new Controleur(this);
        this.session = new SessionInfo();
        this.mainContent = new ScrollPane();
        this.setCenter(this.mainContent);
        JavaFXUtils.addSimpleBorder(this.mainContent);
         try {
            this.session.setConBdD(BdD.defautConnect());
            Connection con = this.getSession().getConBdD();
            //Optional<Utilisateur> user = BdD.connexionUtilisateur(con, "bob@mail.com", "gg");
            //this.getSession().setCurUser(user);
            this.setEntete(new EnteteLogin(this));
            //this.setEntete(new EnteteAfterLogin(this));
            //this.setMainContent(new MainAfterLogin(this));
            this.setMainContent(new BienvenueMainVue(this));
        } catch (ClassNotFoundException | SQLException ex) {
            //this.setMainContent(new BdDNonAccessible(this));
        }
        
    }

    //public MenuButton getMbUtilisateur() {
        //return mbUtilisateur;
    //}

    //public Button getbConnexion() {
        //return bConnexion;
    //}

    //public Button getbNouvelUtilisateur() {
       // return bNouvelUtilisateur;
    //}

    //public TextArea getMessage() {
     //   return message;
    //}

    public ScrollPane getMainContent() {
        return mainContent;
    }

    public SessionInfo getSession() {
        return session;
    }

    public Stage getStage() {
        return stage;
    }

    public Controleur getControleur() {
        return controleur;
    }

    public HBox getEntete() {
        return entete;
    }
    
    
}
