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
    private Button vbEnchere;
    private Button vbVente;
    
    public EnteteAfterLogin(MainPane main) {
        this.main = main;
        
        this.vbLogout = new Button("logout");
        this.vbLogout.setOnAction((event) -> {
            this.doLogout();
        });
        this.vbEnchere = new Button("Enchères");
        this.vbEnchere.setOnAction((event) -> {
            this.main.setMainContent(new PanneauShowEnchere(this.main));
        });
        this.vbVente = new Button("Ventes");
        this.vbVente.setOnAction((event) -> {
            //this.main.setMainContent(new MainAfterLogin(this.main));
        });
        this.getChildren().addAll(this.vbLogout,this.vbEnchere,this.vbVente);
    }
    
    public void doLogout() {
        this.main.getSession().setCurUser(Optional.empty());
        this.main.setEntete(new EnteteLogin(this.main));
        this.main.setMainContent(new BienvenueMainVue(this.main));
    }
    
}