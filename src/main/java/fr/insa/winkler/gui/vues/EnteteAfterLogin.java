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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author francois
 */
public class EnteteAfterLogin extends HBox {
    private MainPane main;
    
    private Button vbLogout;
    private Button vbEnchere;
    private Button vbVente;
    
    public EnteteAfterLogin(MainPane main) {
        this.main = main;
        this.setAlignment(Pos.TOP_LEFT);
        
        this.vbLogout = new Button("Logout");
        this.vbLogout.setMinWidth(60);
        this.vbLogout.setMaxWidth(60);
        this.vbLogout.setOnAction((event) -> {
            this.doLogout();
        });
        HBox categories =new HBox();
        categories.setAlignment(Pos.CENTER);
        this.vbEnchere = new Button("Enchères");
        this.vbEnchere.setMinWidth(70);
        this.vbEnchere.setMaxWidth(70);
        this.vbEnchere.setOnAction((event) -> {
            this.main.setMainContent(new MainAfterLogin(this.main));
        });
        this.vbVente = new Button("Ventes");
        this.vbVente.setMinWidth(60);
        this.vbVente.setMaxWidth(60);
        this.vbVente.setOnAction((event) -> {
            this.main.setMainContent(new MainVentes(this.main));
        });
        categories.getChildren().addAll(this.vbEnchere,this.vbVente);
        categories.setSpacing(50);
        this.getChildren().addAll(this.vbLogout,categories);
        this.setSpacing(250);
    }
    
    public void doLogout() {
        this.main.getSession().setCurUser(Optional.empty());
        this.main.setEntete(new EnteteLogin(this.main));
        this.main.setMainContent(new BienvenueMainVue(this.main));
    }
    
}
