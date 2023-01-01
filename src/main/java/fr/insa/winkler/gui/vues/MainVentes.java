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

import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author francois
 */
public class MainVentes extends VBox {

    private MainPane main;

    private Tab VenteCurrentUser;
    private Tab vendre;
    private Tab ventesTerminees;

    private TabPane allTabs;

    public MainVentes(MainPane main) {
        this.main = main;
        this.VenteCurrentUser = new Tab("Ventes de "
                + this.main.getSession().getUserName());
        this.VenteCurrentUser.setOnSelectionChanged((t) -> {
            this.VenteCurrentUser.setContent(new PanneauShowVente(this.main));
        });
        this.VenteCurrentUser.setContent(new PanneauShowVente(this.main));
        this.vendre = new Tab("Vendre");
        this.vendre.setOnSelectionChanged((t) -> {
            this.vendre.setContent(new Vendre(this.main));
        });
        this.vendre.setContent(new Vendre(this.main));
        this.ventesTerminees = new Tab("Ventes terminées");
        this.ventesTerminees.setOnSelectionChanged((t) -> {
            this.ventesTerminees.setContent(new PanneauShowVenteTerminees(this.main));
        });
        this.vendre.setContent(new PanneauShowVenteTerminees(this.main));
        this.allTabs = new TabPane(this.VenteCurrentUser,this.vendre,this.ventesTerminees);
        this.getChildren().addAll(this.allTabs);
        this.allTabs.getSelectionModel().select(this.VenteCurrentUser);
     }


}
