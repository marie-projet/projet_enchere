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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


/**
 *
 * @author francois
 */
public class BienvenueMainVue extends VBox{
    
    private MainPane main;
    
    public BienvenueMainVue(MainPane main) {
        this.main = main;
        //this.getChildren().add(new BigLabel("bienvenu dans ce super programme",30));
        this.getChildren().add(new Label("merci de vous connecter"));
        TextArea taMoche = new TextArea("Cette interface en javaFX est la traduction quasi à l'identique\n"
                + "d'une interface web faite en vaadin\n"
                + "==> elle n'était déjà pas très belle en vaadin,\n"
                + "==> on pourrait faire moins moche et plus adapté à JavaFX");
        taMoche.setEditable(false);
        this.getChildren().add(taMoche);
        //if (ConfigGenerale.AFFICHE_RAZ_DATABASE) {
        //    this.getChildren().add(new InitOrResetDatabase(this.main));
        //}
    }
    
}
