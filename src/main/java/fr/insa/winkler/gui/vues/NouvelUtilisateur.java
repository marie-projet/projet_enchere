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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author francois
 */
public class NouvelUtilisateur extends GridPane {

    private MainPane main;

    private TextField vtNom;
    private TextField vtPrenom;
    private TextField vtCodePostal;
    private TextField vtEmail;
    private PasswordField vtPass;
    private PasswordField vtPass2;
    private Button vbValidate;

    public NouvelUtilisateur(MainPane main) {
        this.main = main;
        this.vtNom = new TextField();
        this.vtPrenom = new TextField();
        this.vtCodePostal = new TextField();
        this.vtEmail = new TextField();
        this.vtPass = new PasswordField();
        this.vtPass2 = new PasswordField();
        this.vbValidate = new Button("Valider");
        this.vbValidate.setOnAction((event) -> {
            Connection con = this.main.getSession().getConBdD();
            String nom = this.vtNom.getText();
            String prenom = this.vtPrenom.getText();
            String codePostal = this.vtCodePostal.getText();
            String email = this.vtEmail.getText();
            String pass = this.vtPass.getText();
            String pass2 = this.vtPass2.getText();
            if(pass.equals(pass2)){
                try {
                    Utilisateur curU = BdD.ajouterUtilisateur(con, nom, prenom, email, pass,codePostal);
                    this.main.getSession().setCurUser(Optional.of(curU));
                    JavaFXUtils.showInfoInAlert("Utilisateur " + nom + " créé");
                    this.main.setMainContent(new MainAfterLogin(this.main));
                    this.main.setEntete(new EnteteAfterLogin(this.main));
                } catch (SQLException ex) {
               JavaFXUtils.showErrorInAlert("Problème BdD : " + ex.getLocalizedMessage());
                }
            
//                catch (BdD.NomExisteDejaException ex) {
//                JavaFXUtils.showErrorInAlert("Ce nom existe déjà, choississez en un autre");
            }

        });
        int lig = 0;
        this.add(new Label("nom : "), 0, lig);
        this.add(this.vtNom, 1, lig);
        lig++;
        this.add(new Label("prenom : "), 0, lig);
        this.add(this.vtPrenom, 1, lig);
        lig++;
        this.add(new Label("code postal : "), 0, lig);
        this.add(this.vtCodePostal, 1, lig);
        lig++;
        this.add(new Label("email : "), 0, lig);
        this.add(this.vtEmail, 1, lig);
        lig++;
        this.add(new Label("pass : "), 0, lig);
        this.add(this.vtPass, 1, lig);
        lig++;
        this.add(new Label("confirmation pass : "), 0, lig);
        this.add(this.vtPass2, 1, lig);
        lig++;
        this.add(this.vbValidate, 0, lig, 2, 1);
        lig++;
        }
 }


