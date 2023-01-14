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
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class PanneauShowVenteTerminees extends GridPane {

    private MainPane main;
    private ObjetTable3 vObjetsEnVente;
    private ObjetTable3 vObjetsVendus;
    private ObjetTable2 vObjetsNonVendus;
    private ComboBox<String> categories;
    private Button vbInfos;
    private TextField recherche;
    
    

    public PanneauShowVenteTerminees(MainPane main) {
        this.main = main;
        this.main.getControleur().setVentesTerminees(this);
        this.main.getControleur().setEtat(22);
        
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
   
        VBox vlObjetsEnVente = new VBox();
        vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets mis en vente",20));
        try {
            List<Objet> datas = BdD.objetsEnVentePerime(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsEnVente.getChildren().add(this.vObjetsEnVente=new ObjetTable3(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
        vlObjetsEnVente.setAlignment(Pos.CENTER);
        this.add(vlObjetsEnVente,0,1);
        
        VBox vlObjetsVendus = new VBox();
        vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
        try {
            List<Objet> datas = BdD.objetsVendusPerime(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsVendus.getChildren().add(this.vObjetsVendus=new ObjetTable3(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
        vlObjetsVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsVendus,1,1);
        
        
        VBox vlObjetsPasVendus = new VBox();
        vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
        try {
            List<Objet> datas = BdD.objetsPasVendusPerime(
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
        this.setHalignment(this.vbInfos, HPos.CENTER);
        this.setHalignment(this.categories, HPos.CENTER);
        this.setHalignment(this.recherche, HPos.CENTER);
    }

    public ObjetTable3 getvObjetsEnVente() {
        return vObjetsEnVente;
    }

    public ObjetTable3 getvObjetsVendus() {
        return vObjetsVendus;
    }

    public ObjetTable2 getvObjetsNonVendus() {
        return vObjetsNonVendus;
    }


    public ComboBox<String> getCategories() {
        return categories;
    }

    public Button getVbInfos() {
        return vbInfos;
    }

    public void setvObjetsEnVente(ObjetTable3 vObjetsEnVente) {
        this.vObjetsEnVente = vObjetsEnVente;
    }

    public void setvObjetsVendus(ObjetTable3 vObjetsVendus) {
        this.vObjetsVendus = vObjetsVendus;
    }

    public void setvObjetsNonVendus(ObjetTable2 vObjetsNonVendus) {
        this.vObjetsNonVendus = vObjetsNonVendus;
    } 

    public TextField getRecherche() {
        return recherche;
    }
    
    
    }   

