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

import fr.insa.winkler.gui.MainPane;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author francois
 */
public class EnteteAfterLogin extends HBox {
    private MainPane main;
    
    private Button vbLogout;
    private Button vbListeDesUtilisateurs;
    private Button vbGestionAmours;
    
    public EnteteAfterLogin(MainPane main) {
        this.main = main;
        
        this.vbLogout = new Button("logout");
        this.vbLogout.setOnAction((event) -> {
            this.doLogout();
        });
        this.vbListeDesUtilisateurs = new Button("liste des utilisateurs");
        this.vbListeDesUtilisateurs.setOnAction((event) -> {
            //this.main.setMainContent(new ListeDesUtilisateurs(this.main));
        });
        this.vbGestionAmours = new Button("Gérer vos amours");
        this.vbGestionAmours.setOnAction((event) -> {
            //this.main.setMainContent(new MainAfterLogin(this.main));
        });
        this.getChildren().addAll(this.vbLogout,this.vbListeDesUtilisateurs,this.vbGestionAmours);
    }
    
    public void doLogout() {
        this.main.getSession().setCurUser(Optional.empty());
        this.main.setEntete(new EnteteLogin(this.main));
        this.main.setMainContent(new BienvenueMainVue(this.main));
    }
    
}
