
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**
 *
 * @author marie et théo
 * 
 * permet à l'utilisateur d'enchérir sur un nouvel objet
 * affiche les enchères gagnantes de l'utilisateur
 */
public class Encherir extends VBox {

    private MainPane main;

    private ObjetTable vPasEnchere;
    private ObjetTable vEnchereGagnante;
    private Button vbEncherir;
    private Button vbInfos;
    private GridPane gpEnchere;
    private ComboBox<String> categories;
    private TextField recherche;
           

    public Encherir(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    public void reInit() {
        this.getChildren().clear();
        this.main = main;
        this.gpEnchere = new GridPane();
        this.main.getControleur().setEncherir(this);
        this.main.getControleur().changeEtat(11); 
        
        this.recherche=new TextField();
        this.recherche.setPromptText("Rechercher un mot-clé");
        this.recherche.setMaxWidth(170);
        gpEnchere.add(this.recherche,1,0);
        
        this.categories=new ComboBox<String>();
        this.categories.setPromptText("Choisissez une catégorie");
        for (Categorie cat: Categorie.ListCategorie()){
            categories.getItems().addAll(cat.toString());
        }
        this.vbInfos=new Button("Infos");
        this.vbInfos.setMinWidth(60);
        this.vbInfos.setMaxWidth(60);
        gpEnchere.add(categories,0,0);
        gpEnchere.add(this.vbInfos,2,0);
        
        this.vbInfos.setOnAction ((i) -> {
            
            this.main.getControleur().infos();
        });
        
        this.vbEncherir = new Button("ENCHERIR >>");
        VBox vbuttons = new VBox(this.vbEncherir);
        gpEnchere.add(vbuttons,1,1);
        vbuttons.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(vbuttons, HPos.CENTER);
        GridPane.setValignment(vbuttons, VPos.CENTER);
        
        this.vbEncherir.setOnAction((event) -> {
            this.main.getControleur().encherir();
        });
        
        this.categories.setOnAction ((i) -> {
            this.main.getControleur().categorie();
        });
        
        recherche.setOnAction ((i) -> {
            this.main.getControleur().recherche();
        });
        
        
        
        VBox vlPasEnchere = new VBox();
        Label lPasEnchere = new Label("Objets en vente");
        lPasEnchere.setStyle("-fx-font-size: 20");
        vlPasEnchere.getChildren().add(lPasEnchere);
        try {
            List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            this.vPasEnchere = new ObjetTable(this.main,objetsPasEncheris);
            vlPasEnchere.getChildren().add(this.vPasEnchere);
        } catch (SQLException ex) {
            vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
        }
        JavaFXUtils.addSimpleBorder(vlPasEnchere, Color.BLUE, 2);
        vlPasEnchere.setAlignment(Pos.CENTER);
        gpEnchere.add(vlPasEnchere,0,1);
        
        

        VBox vlEnchere = new VBox();
        vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
        try {
            List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                    this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow()));
            this.vEnchereGagnante = new ObjetTable(this.main,objetsEncheris);
            vlEnchere.getChildren().add(this.vEnchereGagnante);
        } catch (SQLException ex) {
            vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
        }
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
        vlEnchere.setAlignment(Pos.CENTER);
        gpEnchere.add(vlEnchere,2,1);
        this.getChildren().add(gpEnchere);
        gpEnchere.setHgap(10);
        gpEnchere.setVgap(20);
        gpEnchere.setAlignment(Pos.CENTER);
        gpEnchere.setHalignment(this.vbInfos, HPos.CENTER);
        gpEnchere.setHalignment(this.categories, HPos.CENTER);
        gpEnchere.setHalignment(this.recherche, HPos.CENTER);
        gpEnchere.setValignment(this.vbEncherir, VPos.CENTER);
        
    }

    public MainPane getMain() {
        return main;
    }
    

    public ObjetTable getvPasEnchere() {
        return vPasEnchere;
    }

    public ObjetTable getvEnchereGagnante() {
        return vEnchereGagnante;
    }


    public Button getVbEncherir() {
        return vbEncherir;
    }

    public Button getVbInfos() {
        return vbInfos;
    }

    public ComboBox<String> getCategories() {
        return categories;
    }

    public void setvPasEnchere(ObjetTable vPasEnchere) {
        this.vPasEnchere = vPasEnchere;
    }

    public void setvEnchereGagnante(ObjetTable vEnchereGagnante) {
        this.vEnchereGagnante = vEnchereGagnante;
    }

    public GridPane getGpEnchere() {
        return gpEnchere;
    }

    public TextField getRecherche() {
        return recherche;
    }

    

    
    

}
