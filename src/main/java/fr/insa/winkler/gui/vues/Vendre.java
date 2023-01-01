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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author francois
 */
public class Vendre extends VBox {

    private MainPane main;

    private ObjetTable vPasEnchere;
    private ObjetTable vEnchereGagnante;
    private Button vbVendre;
    private TextField titre;
    private TextField description;
    private DatePicker dateFin;
    private TextField prixBase;
    private ComboBox<String> categorie;

    public Vendre(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    public void reInit() {
        this.getChildren().clear();         
        GridPane gpVente = new GridPane();
        gpVente.setHgap(70);
        gpVente.setAlignment(Pos.CENTER);
        this.main.getControleur().setVendre(this);
        
        VBox infos=new VBox();

        Label Ltitre=new Label("Titre:");
        this.titre=new TextField();
  
        
        Label Ldescription=new Label("Description :");
        this.description=new TextField();
        
        Label Ldatefin=new Label("Date de fin :");
        this.dateFin = new DatePicker();
        

        Label LprixBase=new Label("Prix de base :");
        this.prixBase=new TextField();

        

        Label Lcategorie=new Label("Categorie :");
        this.categorie=new ComboBox<String>();
            for (Categorie cat: Categorie.ListCategorie()){
                this.categorie.getItems().addAll(cat.toString());
            }
        this.vbVendre = new Button("METTRE EN VENTE");

        
        infos.getChildren().addAll(Ltitre,this.titre,Ldescription,this.description,Ldatefin,this.dateFin,LprixBase,this.prixBase,Lcategorie,this.categorie,vbVendre);
        infos.setSpacing(10);
        infos.setAlignment(Pos.CENTER);
        gpVente.add(infos,0,0);
        
       this.vbVendre.setOnAction ((i) -> {
            this.main.getControleur().vendre();
        });
        
        
        VBox vlObjetsEnVente = new VBox();
        vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
        try {
            List<Objet> datas = BdD.objetsEnVente(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsEnVente.getChildren().add(new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
        vlObjetsEnVente.setAlignment(Pos.CENTER);
        gpVente.add(vlObjetsEnVente,2,0);

        this.getChildren().add(gpVente);
    }

    public ObjetTable getvPasEnchere() {
        return vPasEnchere;
    }

    public ObjetTable getvEnchereGagnante() {
        return vEnchereGagnante;
    }

    public Button getVbVendre() {
        return vbVendre;
    }

    public TextField getTitre() {
        return titre;
    }

    public TextField getDescription() {
        return description;
    }

    public DatePicker getDateFin() {
        return dateFin;
    }

    public TextField getPrixBase() {
        return prixBase;
    }

    public ComboBox<String> getCategorie() {
        return categorie;
    }
  
    
    

}
