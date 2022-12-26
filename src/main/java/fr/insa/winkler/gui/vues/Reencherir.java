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
import fr.insa.winkler.projet.Objet;
import fr.insa.winkler.projet.Utilisateur;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        VBox vlEncherePerdante = new VBox();
        vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
        try {
            List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                    this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow()));
            vlEncherePerdante.getChildren().add(new ObjetTable(this.main,objetsEncheris));
        } catch (SQLException ex) {
            vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
        }
        vlEncherePerdante.setAlignment(Pos.CENTER);
        gpEnchere.add(vlEncherePerdante,0,0);
        
        this.vbEncherir = new Button("ENCHERIR >>");
        this.vbEncherir.setOnAction((event) -> {
            List<Objet> select = this.vEncherePerdante.getSelectedObjects();
            this.vEnchereGagnante.addObjects(select);
            this.vEncherePerdante.removeObjects(select);
        });
       
        VBox vbuttons = new VBox(this.vbEncherir);
        gpEnchere.add(vbuttons,1,0);
        vbuttons.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(vbuttons, HPos.CENTER);
        GridPane.setValignment(vbuttons, VPos.CENTER);
        

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
        gpEnchere.add(vlEnchere,2,0);
        this.getChildren().add(gpEnchere);
        gpEnchere.setHgap(50);
    }

}
