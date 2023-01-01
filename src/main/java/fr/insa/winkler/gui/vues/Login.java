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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author francois
 */
public class Login extends GridPane{
    
    private MainPane main;
    
    private TextField TFnom;
    private TextField TFemail;
    private PasswordField PFpass;
    private Button bLogin;
    
    public Login(MainPane main) {
        this.main = main;
        this.setMinWidth(780);
        this.setPadding(new Insets(40,0,0,0));
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.TFemail= new TextField();
        this.TFemail.setPromptText("Email");
        this.PFpass = new PasswordField();
        this.PFpass.setPromptText("Mot de passe");
        this.bLogin = new Button("Connexion");
        int lig = 0;
        this.add(new Label("Email : "), 0, lig);
        this.add(this.TFemail, 1, lig);
        lig ++;
        this.add(new Label("Pass : "), 0, lig);
        this.add(this.PFpass, 1, lig);
        lig ++;
        this.add(this.bLogin, 0, lig,2,1);
        lig ++;
        this.bLogin.setOnAction((event) -> {
            this.doLogin();
        });
        this.setHalignment(this.bLogin,HPos.CENTER);
    }
    
    public void doLogin() {
        String email = this.TFemail.getText();
        String pass = this.PFpass.getText();
        try {
            Connection con = this.main.getSession().getConBdD();
            Optional<Utilisateur> user = BdD.connexionUtilisateur(con, email, pass);
            if(user.isEmpty()) {
                JavaFXUtils.showErrorInAlert("Utilisateur ou pass invalide");
            } else {
                this.main.getSession().setCurUser(user);
                this.main.setEntete(new EnteteAfterLogin(this.main));
                this.main.setMainContent(new MainAfterLogin(this.main));
            }
        } catch (SQLException ex) {
            JavaFXUtils.showErrorInAlert("Problème interne : " + ex.getLocalizedMessage());
        }        
    }
}
