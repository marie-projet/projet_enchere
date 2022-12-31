/*
    Copyright 2000- Francois de Bertrand de Beuvron

    This file is part of CoursBeuvron.

    CoursBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CoursBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import fr.insa.winkler.projet.Utilisateur;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class Reencherir extends VBox {

    private MainPane main;

    private ObjetTable vEncherePerdante;
    private ObjetTable vEnchereGagnante;
    private Button vbEncherir;
    private Button vbInfos;
    private Label categorie;
    private ComboBox<String> categories;
    private GridPane gpEnchere;

    public Reencherir(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    public void reInit() {
        this.getChildren().clear();
        this.gpEnchere = new GridPane();
        this.main = main;
        this.main.getControleur().setReencherir(this);
        this.main.getControleur().setEtat(12);
        
        this.categorie=new BigLabel("                                            Categorie",20);
            this.categories=new ComboBox<String>();
            for (Categorie cat: Categorie.ListCategorie()){
                categories.getItems().addAll(cat.toString());
            }
        this.vbInfos=new Button("Infos");
        this.vbInfos.setMinWidth(60);
        this.vbInfos.setMaxWidth(60);
        gpEnchere.add(categorie,0,0);
        gpEnchere.add(categories,1,0);
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
        
        categories.setOnAction ((i) -> {
            this.main.getControleur().categorie();
        });
      
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
        vlEncherePerdante.setAlignment(Pos.CENTER);
        JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
        gpEnchere.add(vlEncherePerdante,0,1);
    

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
        vlEnchere.setAlignment(Pos.CENTER);
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
        gpEnchere.add(vlEnchere,2,1);
        this.getChildren().add(gpEnchere);
        gpEnchere.setHgap(10);
        gpEnchere.setVgap(20);
        gpEnchere.setAlignment(Pos.CENTER);
        gpEnchere.setHalignment(this.vbInfos, HPos.CENTER);

    }

    public MainPane getMain() {
        return main;
    }

    public ObjetTable getvEncherePerdante() {
        return vEncherePerdante;
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

    public Label getCategorie() {
        return categorie;
    }

    public ComboBox<String> getCategories() {
        return categories;
    }

    public void setvEncherePerdante(ObjetTable vEncherePerdante) {
        this.vEncherePerdante = vEncherePerdante;
    }

    public void setvEnchereGagnante(ObjetTable vEnchereGagnante) {
        this.vEnchereGagnante = vEnchereGagnante;
    }

    public GridPane getGpEnchere() {
        return gpEnchere;
    }
    
    
    

}
    
    


