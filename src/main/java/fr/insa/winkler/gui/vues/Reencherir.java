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
    private Button vbValidate;
    private Button vbCancel;
    private Button vbEncherir;
    private Button vbDetails;

    public Reencherir(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    private void reInit() {
        this.getChildren().clear();
        GridPane gpEnchere = new GridPane();
        
        this.main = main;
        Label categorie=new BigLabel("                                            Categorie",20);
            ComboBox<String> categories=new ComboBox<String>();
            for (Categorie cat: Categorie.ListCategorie()){
                categories.getItems().addAll(cat.toString());
            }
        Button infos=new Button("Infos");
        gpEnchere.add(categorie,0,0);
        gpEnchere.add(categories,1,0);
        gpEnchere.add(infos,2,0);
        
        infos.setOnAction ((i) -> {
            if(this.vEncherePerdante != null){
                List<Objet> objetSelctionne=this.vEncherePerdante.getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.vEncherePerdante.getSelectionModel().clearSelection();
            }
            if(this.vEnchereGagnante != null){
                List<Objet>objetSelctionne=this.vEnchereGagnante.getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.vEnchereGagnante.getSelectionModel().clearSelection();
            }
        });
        
        this.vbEncherir = new Button("ENCHERIR >>");
        VBox vbuttons = new VBox(this.vbEncherir);
        gpEnchere.add(vbuttons,1,1);
        vbuttons.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(vbuttons, HPos.CENTER);
        GridPane.setValignment(vbuttons, VPos.CENTER);
        this.vbEncherir.setOnAction((event) -> {
            List<Objet> select=this.vEncherePerdante.getSelectedObjects();
                for (Objet obj:select){
                    String montantStr=JavaFXUtils.Encherir(obj.toString());
                    int v=1;
                    if (montantStr.equals("")){
                        v=0;
                    }
                    if(v==1){
                        try{
                            try{
                                Integer.parseInt(montantStr);                                
                            }
                            catch(Exception e){
                                JavaFXUtils.showErrorInAlert("Montant non valide, veuillez entrez un nombre entier");
                            }
                            BdD.ajouterEnchere(this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow(), Integer.parseInt(montantStr),obj.getId());
                        }
                        catch (SQLException ex) {
                            JavaFXUtils.showErrorInAlert("Montant non valide, veuillez entrez un nombre entier");
                        }                       
                    }
                }  
            this.reInit();
        });
        
        categories.setOnAction ((i) -> {
            List<String> a = new ArrayList<>();
            for (String s: categories.getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            
            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                            this.main.getSession
                ().getConBdD(), this.main.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
                ().getConBdD(), this.main.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    vlEncherePerdante.getChildren().add(this.vEncherePerdante=new ObjetTable(this.main,objetsEncheris));
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
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
            }
            vlEncherePerdante.setAlignment(Pos.CENTER);
            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            gpEnchere.add(vlEncherePerdante,0,1);


            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.main.getSession
                ().getConBdD(), this.main.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
                ().getConBdD(), this.main.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    this.vEnchereGagnante = new ObjetTable(this.main,objetsEncheris);
                    vlEnchere.getChildren().add(this.vEnchereGagnante);
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
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
            }
            vlEnchere.setAlignment(Pos.CENTER);
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
            gpEnchere.add(vlEnchere,2,1);
            this.getChildren().add(gpEnchere);
            gpEnchere.setHgap(10);
            gpEnchere.setVgap(20);
            gpEnchere.setAlignment(Pos.CENTER);
            gpEnchere.setHalignment(infos, HPos.CENTER);
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
        gpEnchere.setHalignment(infos, HPos.CENTER);

    }

}
