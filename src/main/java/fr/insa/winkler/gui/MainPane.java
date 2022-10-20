/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.winkler.gui;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private Stage stage;
    
    
    public MainPane(Stage st){
        this.stage=st;
        this.mbUtilisateur=new MenuButton ("Utilisateur");
        this.message=new TextArea();
        this.message.setMinHeight(70);
        this.message.setMaxHeight(70);
        this.setTop(this.mbUtilisateur);
        this.setBottom(this.message);
        
        MenuItem menuItemConnexion = new MenuItem("Connexion");
        MenuItem menuItemNouvelUtilisateur = new MenuItem("Créer un compte");
        mbUtilisateur.getItems().addAll(menuItemConnexion,menuItemNouvelUtilisateur);
    }
}
