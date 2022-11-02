/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.winkler.gui;

import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author mariewinkler
 */
public class MainPane extends BorderPane{
    //private File file;
    //private Controleur controleur; 
    private MenuButton mbUtilisateur;
    private Button bConnexion;  
    private Button bNouvelUtilisateur; 
    private TextArea message; 
    private ScrollPane mainContent;

    private Stage stage;
    
    
    public MainPane(Stage st){
        this.stage=st;
        this.mainContent=new ScrollPane();
        this.mbUtilisateur=new MenuButton ("Utilisateur");
        this.message=new TextArea();
        this.message.setMinHeight(70);
        this.message.setMaxHeight(70);
        this.setTop(this.mbUtilisateur);
        this.setBottom(this.message);
        this.setCenter(this.mainContent);
        
        MenuItem menuItemConnexion = new MenuItem("Connexion");
        MenuItem menuItemNouvelUtilisateur = new MenuItem("Créer un compte");
        mbUtilisateur.getItems().addAll(menuItemConnexion,menuItemNouvelUtilisateur);
    }
}
