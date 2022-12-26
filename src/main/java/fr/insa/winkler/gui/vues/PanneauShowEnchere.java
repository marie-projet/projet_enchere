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
import javafx.geometry.Pos;
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

    public PanneauShowEnchere(MainPane main) {
        this.main = main;
        VBox vlEnchere = new VBox();
        vlEnchere.getChildren().add(new BigLabel("Vos enchères",20));
        try {
            List<Objet> datas = BdD.objetEncheri(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlEnchere.getChildren().add(new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
        vlEnchere.setAlignment(Pos.CENTER);
        this.add(vlEnchere,0,0);
        
        VBox vlEnchereGagnante = new VBox();
        vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
        try {
            List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                    this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow(),BdD.objetEncheri(this.main.getSession
        ().getConBdD(), this.main.getSession
        ().getCurUser().orElseThrow()));
            vlEnchereGagnante.getChildren().add(new ObjetTable(this.main,objetsEncheris));
        } catch (SQLException ex) {
            vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
        }
        JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
        vlEnchereGagnante.setAlignment(Pos.CENTER);
        this.add(vlEnchereGagnante,1,0);
        
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
        JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
        vlEncherePerdante.setAlignment(Pos.CENTER);
        this.add(vlEncherePerdante,2,0);
        this.setHgap(10);
    }
    }   

