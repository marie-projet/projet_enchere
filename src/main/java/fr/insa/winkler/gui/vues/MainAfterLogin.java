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
import fr.insa.winkler.projet.Categorie;
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
    private Tab encherir;
    private Tab reencherir;
    private TabPane allTabs;

    public MainAfterLogin(MainPane main) {
        this.main = main;
        this.EnchereCurrentUser = new Tab("Encheres de "
                + this.main.getSession().getUserName());
        this.EnchereCurrentUser.setOnSelectionChanged((t) -> {
            this.EnchereCurrentUser.setContent(new PanneauShowEnchere(this.main));
        });
        this.EnchereCurrentUser.setContent(new PanneauShowEnchere(this.main));
        this.encherir = new Tab("Enchérir");
        this.encherir.setOnSelectionChanged((t) -> {
            this.encherir.setContent(new Encherir(this.main));
        });
        this.encherir.setContent(new Encherir(this.main));
        this.reencherir = new Tab("Réenchérir");
        this.reencherir.setOnSelectionChanged((t) -> {
           this.reencherir.setContent(new Reencherir(this.main));
        });
        this.reencherir.setContent(new Reencherir(this.main));
        this.allTabs = new TabPane(this.EnchereCurrentUser,this.encherir,this.reencherir);
        this.getChildren().addAll(this.allTabs);
        this.allTabs.getSelectionModel().select(this.EnchereCurrentUser);
     }

    public MainPane getMain() {
        return main;
    }

    public Tab getEnchereCurrentUser() {
        return EnchereCurrentUser;
    }

    public Tab getEncherir() {
        return encherir;
    }

    public Tab getReencherir() {
        return reencherir;
    }

    public TabPane getAllTabs() {
        return allTabs;
    }

    public static double getUSE_PREF_SIZE() {
        return USE_PREF_SIZE;
    }

    public static double getUSE_COMPUTED_SIZE() {
        return USE_COMPUTED_SIZE;
    }

    public static double getBASELINE_OFFSET_SAME_AS_HEIGHT() {
        return BASELINE_OFFSET_SAME_AS_HEIGHT;
    }


}
