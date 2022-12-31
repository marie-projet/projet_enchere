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
public class PanneauShowEnchere extends GridPane {

    private MainPane main;
    private ObjetTable vEnchere;
    private ObjetTable vEnchereGagnante;
    private ObjetTable vEncherePerdante;
    private Button vbInfos;
    private Label categorie;
    private ComboBox<String> categories;

    public PanneauShowEnchere(MainPane main) {
        this.main = main;
        this.main.getControleur().setEncheres(this);
        this.main.getControleur().setEtat(10);
        
        this.categorie=new BigLabel("                                            Categorie",20);
            this.categories=new ComboBox<String>();
            for (Categorie cat: Categorie.ListCategorie()){
                categories.getItems().addAll(cat.toString());
            }
        this.vbInfos=new Button("Infos");
        this.add(categorie,0,0);
        this.add(categories,1,0);
        this.add(this.vbInfos,2,0);
        
        this.vbInfos.setOnAction ((i) -> {
            this.main.getControleur().infos();
        });
        
        categories.setOnAction ((i) -> {
            this.main.getControleur().categorie();
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

    public Label getCategorie() {
        return categorie;
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
    
    
    

    
    
    
}

