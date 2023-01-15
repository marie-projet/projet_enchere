
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author marie et théo
 * 
 * affiche le bilan des enchères actuelles de l'utilisateur
 * 
 */

public class PanneauShowEnchere extends GridPane {

    private MainPane main;
    private ObjetTable vEnchere;
    private ObjetTable vEnchereGagnante;
    private ObjetTable vEncherePerdante;
    private Button vbInfos;
    private ComboBox<String> categories;
    private TextField recherche;

    public PanneauShowEnchere(MainPane main) {
        this.main = main;
        this.main.getControleur().setEncheres(this);
        this.main.getControleur().setEtat(10);
        
        this.recherche=new TextField();
        this.recherche.setPromptText("Rechercher un mot-clé");
        this.recherche.setMaxWidth(170);
        this.add(this.recherche,1,0);
        
        this.categories=new ComboBox<String>();
        this.categories.setPromptText("Choisissez une catégorie");
        for (Categorie cat: Categorie.ListCategorie()){
            categories.getItems().addAll(cat.toString());
        }
        this.vbInfos=new Button("Infos");
        this.vbInfos.setMinWidth(60);
        this.vbInfos.setMaxWidth(60);
        this.add(categories,0,0);
        this.add(this.vbInfos,2,0);
        
        this.vbInfos.setOnAction ((i) -> {
            this.main.getControleur().infos();
        });
        
        categories.setOnAction ((i) -> {
            this.main.getControleur().categorie();
        });
        
        recherche.setOnAction ((i) -> {
            this.main.getControleur().recherche();
        });
        
               
        VBox vlEnchere = new VBox();
        vlEnchere.getChildren().add(new BigLabel("Vos enchères",20));
        try {
            List<Objet> datas = BdD.objetEncheri(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlEnchere.getChildren().add(this.vEnchere=new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
        vlEnchere.setAlignment(Pos.CENTER);
        this.add(vlEnchere,0,1);
        
        VBox vlEnchereGagnante = new VBox();
        vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
        try {
            List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                    this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow()));
            vlEnchereGagnante.getChildren().add(this.vEnchereGagnante=new ObjetTable(this.main,objetsEncheris));
        } catch (SQLException ex) {
            vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
        }
        JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
        vlEnchereGagnante.setAlignment(Pos.CENTER);
        this.add(vlEnchereGagnante,1,1);
        
        VBox vlEncherePerdante = new VBox();
        vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
        try {
            List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                    this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow()));
            vlEncherePerdante.getChildren().add(this.vEncherePerdante=new ObjetTable(this.main,objetsEncheris));
        } catch (SQLException ex) {
            vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
        }
        JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
        vlEncherePerdante.setAlignment(Pos.CENTER);
        this.add(vlEncherePerdante,2,1);
        this.setHgap(10);
        this.setVgap(20);
        this.setAlignment(Pos.CENTER);
        this.setHalignment(this.vbInfos, HPos.CENTER);
        this.setHalignment(this.categories, HPos.CENTER);
        this.setHalignment(this.recherche, HPos.CENTER);
 
    }

    public MainPane getMain() {
        return main;
    }

    public ObjetTable getvEnchere() {
        return vEnchere;
    }

    public ObjetTable getvEnchereGagnante() {
        return vEnchereGagnante;
    }

    public ObjetTable getvEncherePerdante() {
        return vEncherePerdante;
    }

    public Button getVbInfos() {
        return vbInfos;
    }


    public ComboBox<String> getCategories() {
        return categories;
    }

    public void setvEnchere(ObjetTable vEnchere) {
        this.vEnchere = vEnchere;
    }

    public void setvEnchereGagnante(ObjetTable vEnchereGagnante) {
        this.vEnchereGagnante = vEnchereGagnante;
    }

    public void setvEncherePerdante(ObjetTable vEncherePerdante) {
        this.vEncherePerdante = vEncherePerdante;
    }

    public TextField getRecherche() {
        return recherche;
    }
   
    
}

