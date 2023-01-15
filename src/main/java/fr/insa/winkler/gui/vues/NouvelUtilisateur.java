
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
 * @author marie et théo
 * 
 * permet à un nouvel utilisateur de s'inscrire
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
        this.setMinWidth(780);
        this.setPadding(new Insets(40,0,0,0));
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.vtNom = new TextField();
        this.vtNom.setPromptText("Nom");
        this.vtPrenom = new TextField();
        this.vtPrenom.setPromptText("Prénom");
        this.vtCodePostal = new TextField();
        this.vtCodePostal.setPromptText("Code postal");
        this.vtEmail = new TextField();
        this.vtEmail.setPromptText("Email");
        this.vtPass = new PasswordField();
        this.vtPass.setPromptText("Mot de passe");
        this.vtPass2 = new PasswordField();
        this.vtPass2.setPromptText("Mot de passe");
        this.vbValidate = new Button("Valider");
        this.vbValidate.setOnAction((event) -> {
        Connection con = this.main.getSession().getConBdD();
                if(this.vtNom.getText().equals("") || this.vtPrenom.getText().equals("") || this.vtCodePostal.getText().equals("") || this.vtEmail.getText().equals("") ||this.vtPass.getText().equals("") || this.vtPass2.getText().equals("")   ){
        JavaFXUtils.showErrorInAlert("Champs incomplets");
        throw new Error("Champs incomplets");
        }
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
                    JavaFXUtils.showInfoInAlert("Utilisateur " + prenom + " créé");
                    this.main.setMainContent(new MainAfterLogin(this.main));
                    this.main.setEntete(new EnteteAfterLogin(this.main));
                } catch (SQLException ex) {
               JavaFXUtils.showErrorInAlert("Problème BdD : " + ex.getLocalizedMessage());
                }
            
            }
            else{
                JavaFXUtils.showErrorInAlert("Mot de pass incorrect");
            }

        });
        this.setHalignment(this.vbValidate,HPos.CENTER);
        int lig = 0;
        this.add(new Label("Nom : "), 0, lig);
        this.add(this.vtNom, 1, lig);
        lig++;
        this.add(new Label("Prénom : "), 0, lig);
        this.add(this.vtPrenom, 1, lig);
        lig++;
        this.add(new Label("Code postal : "), 0, lig);
        this.add(this.vtCodePostal, 1, lig);
        lig++;
        this.add(new Label("Email : "), 0, lig);
        this.add(this.vtEmail, 1, lig);
        lig++;
        this.add(new Label("Pass : "), 0, lig);
        this.add(this.vtPass, 1, lig);
        lig++;
        this.add(new Label("Confirmation pass : "), 0, lig);
        this.add(this.vtPass2, 1, lig);
        lig++;
        this.add(this.vbValidate, 0, lig, 2, 1);
        lig++;
        }
 }


