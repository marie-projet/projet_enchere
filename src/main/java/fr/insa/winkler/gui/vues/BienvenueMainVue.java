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
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        this.setSpacing(25);
        BigLabel accueil=new BigLabel("Bienvenue sur notre site de vente aux enchères",39);
        this.getChildren().addAll(accueil);
        Image image1 = new Image(new File("src/main/java/fr/insa/winkler/gui/vues/image/enchere.png").toURI().toString());
        ImageView im=new ImageView();
        im.setImage(image1);
        im.setFitHeight(400);
        im.setFitWidth(640);
        this.getChildren().addAll(im);
        this.setMinWidth(780);
        this.setAlignment(Pos.CENTER);
        //if (ConfigGenerale.AFFICHE_RAZ_DATABASE) {
        //    this.getChildren().add(new InitOrResetDatabase(this.main));
        //}
    }
    
}
