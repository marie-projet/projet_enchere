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
public class Encherir extends VBox {

    private MainPane main;

    private ObjetTable vPasEnchere;
    private ObjetTable vEnchereGagnante;
    private Button vbValidate;
    private Button vbCancel;
    private Button vbEncherir;
    private Button vbDetails;

    public Encherir(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    private void reInit() {
        this.getChildren().clear();
        this.vbValidate = new Button("Valider les modifs.");
        this.vbValidate.setOnAction((event) -> {
            try {
                BdD.change(this.main.getSession().getConBdD(),
                        this.main.getSession().getCurUser().orElseThrow(),
                        this.vPasEnchere.getObjets());
                JavaFXUtils.showInfoInAlert("Modifications validées");
            } catch (SQLException ex) {
                JavaFXUtils.showErrorInAlert("Error","Problem BdD : " , ex.getLocalizedMessage());
            }
        });
        this.vbCancel = new Button("Annuler les dernières modifs.");
        this.vbCancel.setOnAction((event) -> {
            this.reInit();
        });
        HBox hlButtons = new HBox(this.vbValidate, this.vbCancel);
        this.getChildren().add(hlButtons);
 
        GridPane gpAime = new GridPane();
        VBox vlPasAimes = new VBox();
        Label lPasAime = new Label("vous n'aimez pas");
        lPasAime.setStyle("-fx-font-size: 30");
        vlPasAimes.getChildren().add(lPasAime);
        try {
            List<Utilisateur> datas = GestionBdD.quiNeSontPasAimesPar(
                    this.main.getSessionInfo().getConBdD(), this.main.getSessionInfo().getCurUser().orElseThrow());
            this.vpasAime = new UtilisateurTable(this.main,datas);
            vlPasAimes.getChildren().add(this.vpasAime);
        } catch (SQLException ex) {
            vlPasAimes.getChildren().add(new BigLabel("Probleme BdD",30));
        }
        gpAime.add(vlPasAimes,0,0);
        this.vbAdd = new Button("ADD >>");
        this.vbAdd.setOnAction((event) -> {
            List<Utilisateur> select = this.vpasAime.getSelectedUsers();
            this.vaime.addUsers(select);
            this.vpasAime.removeUsers(select);
        });
        this.vbRemove = new Button("<< REMOVE");
        this.vbRemove.setOnAction((event) -> {
            List<Utilisateur> select = this.vaime.getSelectedUsers();
            this.vpasAime.addUsers(select);
            this.vaime.removeUsers(select);

        });
        VBox vbuttons = new VBox(this.vbAdd, this.vbRemove);
        gpAime.add(vbuttons,1,0);
        vbuttons.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(vbuttons, HPos.CENTER);
        GridPane.setValignment(vbuttons, VPos.CENTER);
        

        VBox vlAime = new VBox();
        vlAime.getChildren().add(new BigLabel("vous aimez",30));
        try {
            List<Utilisateur> datas = GestionBdD.quiSontAimesPar(
                    this.main.getSessionInfo().getConBdD(), this.main.getSessionInfo().getCurUser().orElseThrow());
            this.vaime = new UtilisateurTable(this.main,datas);
            vlAime.getChildren().add(this.vaime);
        } catch (SQLException ex) {
            vlAime.getChildren().add(new BigLabel("Probleme BdD",30));
        }
        gpAime.add(vlAime,2,0);
        this.getChildren().add(gpAime);
    }

}
