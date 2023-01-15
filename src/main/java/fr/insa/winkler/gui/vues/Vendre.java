
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author marie et théo
 * 
 * permet à l'utilisateur de mettre un objet en vente 
 * affiche les objets mis en vente par l'utilisateur
 */
public class Vendre extends VBox {

    private MainPane main;

    private ObjetTable vPasEnchere;
    private ObjetTable vEnchereGagnante;
    private Button vbVendre;
    private TextField titre;
    private TextField description;
    private DatePicker dateFin;
    private TextField prixBase;
    private ComboBox<String> categorie;

    public Vendre(MainPane main) {
        this.main = main;
        this.reInit();
    }
    
    public void reInit() {
        this.getChildren().clear();         
        GridPane gpVente = new GridPane();
        gpVente.setHgap(70);
        gpVente.setAlignment(Pos.CENTER);
        this.main.getControleur().setVendre(this);
        
        VBox infos=new VBox();

        Label Ltitre=new Label("Titre:");
        this.titre=new TextField();
        this.titre.setPromptText("Titre de l'objet");
  
        
        Label Ldescription=new Label("Description :");
        this.description=new TextField();
        this.description.setPromptText("Description de l'objet");
        
        Label Ldatefin=new Label("Date de fin :");
        this.dateFin = new DatePicker();
        this.dateFin.setPromptText("Date de fin de vente");
        

        Label LprixBase=new Label("Prix de base :");
        this.prixBase=new TextField();
        this.prixBase.setPromptText("Prix de mise en vente");

        

        Label Lcategorie=new Label("Categorie :");
        this.categorie=new ComboBox<String>();
            for (Categorie cat: Categorie.ListCategorie()){
                this.categorie.getItems().addAll(cat.toString());
            }
        this.categorie.getItems().remove(0);
        this.categorie.setPromptText("Catégorie de l'objet");
        this.vbVendre = new Button("METTRE EN VENTE");

        
        infos.getChildren().addAll(Ltitre,this.titre,Ldescription,this.description,Ldatefin,this.dateFin,LprixBase,this.prixBase,Lcategorie,this.categorie,vbVendre);
        infos.setSpacing(10);
        infos.setAlignment(Pos.CENTER);
        gpVente.add(infos,0,0);
        
       this.vbVendre.setOnAction ((i) -> {
            this.main.getControleur().vendre();
        });
        
        
        VBox vlObjetsEnVente = new VBox();
        vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
        try {
            List<Objet> datas = BdD.objetsEnVente(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsEnVente.getChildren().add(new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
        vlObjetsEnVente.setAlignment(Pos.CENTER);
        gpVente.add(vlObjetsEnVente,2,0);

        this.getChildren().add(gpVente);
    }

    public ObjetTable getvPasEnchere() {
        return vPasEnchere;
    }

    public ObjetTable getvEnchereGagnante() {
        return vEnchereGagnante;
    }

    public Button getVbVendre() {
        return vbVendre;
    }

    public TextField getTitre() {
        return titre;
    }

    public TextField getDescription() {
        return description;
    }

    public DatePicker getDateFin() {
        return dateFin;
    }

    public TextField getPrixBase() {
        return prixBase;
    }

    public ComboBox<String> getCategorie() {
        return categorie;
    }
  
    
    

}
