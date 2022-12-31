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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class Encherir extends VBox {

    private MainPane main;

    private ObjetTable vPasEnchere;
    private ObjetTable vEnchereGagnante;
    private Button vbEncherir;
    private Button vbInfos;
    private Label categorie;
    private GridPane gpEnchere;
    private ComboBox<String> categories;
           

    public Encherir(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    public void reInit() {
        this.getChildren().clear();
        this.gpEnchere = new GridPane();
        this.main.getControleur().setEncherir(this);
        this.main.getControleur().changeEtat(11);
        
        this.main = main;
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
        
        this.categories.setOnAction ((i) -> {
            this.main.getControleur().categorie();
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

    public Label getCategorie() {
        return categorie;
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

    

    
    

}
