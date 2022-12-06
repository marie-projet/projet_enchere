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
public class MainAfterLogin extends VBox {

    private MainPane main;

    private Tab EnchereCurrentUser;
    private Tab changeEnchere;
    private TabPane allTabs;

    public MainAfterLogin(MainPane main) {
        this.main = main;

        this.EnchereCurrentUser = new Tab("Encheres de "
                + this.main.getSession().getUserName());
        this.EnchereCurrentUser.setOnSelectionChanged((t) -> {
            this.EnchereCurrentUser.setContent(new PanneauShowEnchere(this.main));
        });
        //this.aimeCurrentUser.setContent(new PanneauShowAime(this.main));
        this.changeEnchere = new Tab("modifier");
        this.changeEnchere.setOnSelectionChanged((t) -> {
            //this.changeAime.setContent(new PanneauModifyAime(this.main));
        });
        //this.changeAime.setContent(new PanneauModifyAime(this.main));
        this.allTabs = new TabPane(this.EnchereCurrentUser, this.changeEnchere);
        this.getChildren().addAll(this.allTabs);
        this.allTabs.getSelectionModel().select(this.EnchereCurrentUser);
     }


}
