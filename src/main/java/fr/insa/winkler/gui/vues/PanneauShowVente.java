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
public class PanneauShowVente extends GridPane {

    private MainPane main;

    public PanneauShowVente(MainPane main) {
        this.main = main;
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
        this.add(vlObjetsEnVente,0,0);
        
        VBox vlObjetsVendus = new VBox();
        vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
        try {
            List<Objet> datas = BdD.objetsVendus(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsVendus.getChildren().add(new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
        vlObjetsVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsVendus,1,0);
        
        
        VBox vlObjetsPasVendus = new VBox();
        vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
        try {
            List<Objet> datas = BdD.objetsPasVendus(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsPasVendus.getChildren().add(new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
        vlObjetsPasVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsPasVendus,2,0);
        this.setHgap(10);
    }
    }   

