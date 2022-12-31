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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class PanneauShowVente extends GridPane {

    private MainPane main;
    private ObjetTable vObjetsEnVente;
    private ObjetTable vObjetsVendus;
    private ObjetTable2 vObjetsNonVendus;
    private Label categorie;
    private ComboBox<String> categories;
    private Button vbInfos;
    
    

    public PanneauShowVente(MainPane main) {
        this.main = main;
        this.main.getControleur().setVentes(this);
        this.main.getControleur().setEtat(20);
                
        this.categorie=new BigLabel("                                            Categorie",20);
            this.categories=new ComboBox<String>();
            for (Categorie cat: Categorie.ListCategorie()){
                categories.getItems().addAll(cat.toString());
            }
        Button infos=new Button("Infos");
        this.add(categorie,0,0);
        this.add(categories,1,0);
        this.add(infos,2,0);
        
        infos.setOnAction ((i) -> {

            this.main.getControleur().infos();
        });
        
        categories.setOnAction ((i) -> {
            List<String> a = new ArrayList<>();
            for (String s: categories.getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlObjetsEnVente = new VBox();
            vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> datas = BdD.objetsEnVente(
                            this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow(),categorieChoisie);
                    vlObjetsEnVente.getChildren().add(this.vObjetsEnVente=new ObjetTable(this.main,datas));
                } catch (SQLException ex) {
                    vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                }
            }
            else{
                try {
                    List<Objet> datas = BdD.objetsEnVente(
                            this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
                    vlObjetsEnVente.getChildren().add(this.vObjetsEnVente=new ObjetTable(this.main,datas));
                } catch (SQLException ex) {
                    vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                }
            }
            JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
            vlObjetsEnVente.setAlignment(Pos.CENTER);
            this.add(vlObjetsEnVente,0,1);
            
            VBox vlObjetsVendus = new VBox();
            vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> datas = BdD.objetsVendus(
                            this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow(),categorieChoisie);
                    vlObjetsVendus.getChildren().add(this.vObjetsVendus=new ObjetTable(this.main,datas));
                } catch (SQLException ex) {
                    vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                }
            }
            else{
                try {
                    List<Objet> datas = BdD.objetsVendus(
                            this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
                    vlObjetsVendus.getChildren().add(this.vObjetsVendus=new ObjetTable(this.main,datas));
                } catch (SQLException ex) {
                    vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                }
            } 
            JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
            vlObjetsVendus.setAlignment(Pos.CENTER);
            this.add(vlObjetsVendus,1,1);
            
            VBox vlObjetsPasVendus = new VBox();
            vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> datas = BdD.objetsPasVendus(
                            this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow(),categorieChoisie);
                    vlObjetsPasVendus.getChildren().add(this.vObjetsNonVendus=new ObjetTable2(this.main,datas));
                } catch (SQLException ex) {
                    vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                }
            }
            else{
                try {
                    List<Objet> datas = BdD.objetsPasVendus(
                            this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
                    vlObjetsPasVendus.getChildren().add(this.vObjetsNonVendus=new ObjetTable2(this.main,datas));
                } catch (SQLException ex) {
                    vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                }
            }
            JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
            vlObjetsPasVendus.setAlignment(Pos.CENTER);
            this.add(vlObjetsPasVendus,2,1);
            this.setHgap(10);
            this.setVgap(20);
        });
            
            
    
            
        VBox vlObjetsEnVente = new VBox();
        vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
        try {
            List<Objet> datas = BdD.objetsEnVente(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsEnVente.getChildren().add(this.vObjetsEnVente=new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
        vlObjetsEnVente.setAlignment(Pos.CENTER);
        this.add(vlObjetsEnVente,0,1);
        
        VBox vlObjetsVendus = new VBox();
        vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
        try {
            List<Objet> datas = BdD.objetsVendus(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsVendus.getChildren().add(this.vObjetsVendus=new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
        vlObjetsVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsVendus,1,1);
        
        
        VBox vlObjetsPasVendus = new VBox();
        vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
        try {
            List<Objet> datas = BdD.objetsPasVendus(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsPasVendus.getChildren().add(this.vObjetsNonVendus=new ObjetTable2(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
        vlObjetsPasVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsPasVendus,2,1);
        this.setHgap(10);
        this.setVgap(20);
    }

    public ObjetTable getvObjetsEnVente() {
        return vObjetsEnVente;
    }

    public ObjetTable getvObjetsVendus() {
        return vObjetsVendus;
    }

    public ObjetTable2 getvObjetsNonVendus() {
        return vObjetsNonVendus;
    }

    public Label getCategorie() {
        return categorie;
    }

    public ComboBox<String> getCategories() {
        return categories;
    }

    public Button getVbInfos() {
        return vbInfos;
    }
    
    
    }   

