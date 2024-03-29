
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import java.sql.SQLException;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author marie et théo
 * 
 * affiche le bilan des ventes actuelles de l'utilisateur
 */
public class PanneauShowVente extends GridPane {

    private MainPane main;
    private ObjetTable vObjetsEnVente;
    private ObjetTable vObjetsVendus;
    private ObjetTable2 vObjetsNonVendus;
    private ComboBox<String> categories;
    private Button vbInfos;
    private TextField recherche;
    
    

    public PanneauShowVente(MainPane main) {
        this.main = main;
        this.main.getControleur().setVentes(this);
        this.main.getControleur().setEtat(20);
        
        this.recherche=new TextField();
        this.recherche.setPromptText("Rechercher un mot-clé");
        this.recherche.setMaxWidth(170);
        this.add(this.recherche,1,0);
                
        this.categories=new ComboBox<String>();
        this.categories.setPromptText("Choisissez une catégorie");
        for (Categorie cat: Categorie.ListCategorie()){
            categories.getItems().addAll(cat.toString());
        }        
        this.vbInfos=new Button("Infos");
        this.vbInfos.setMinWidth(60);
        this.vbInfos.setMaxWidth(60);
        this.add(categories,0,0);
        this.add(this.vbInfos,2,0);
        
        this.vbInfos.setOnAction ((i) -> {
            this.main.getControleur().infos();
        });
        
        categories.setOnAction ((i) -> {
            this.main.getControleur().categorie();
        });
        
        recherche.setOnAction ((i) -> {
            this.main.getControleur().recherche();
        });
   
        VBox vlObjetsEnVente = new VBox();
        vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
        try {
            List<Objet> datas = BdD.objetsEnVente(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsEnVente.getChildren().add(this.vObjetsEnVente=new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
        vlObjetsEnVente.setAlignment(Pos.CENTER);
        this.add(vlObjetsEnVente,0,1);
        
        VBox vlObjetsVendus = new VBox();
        vlObjetsVendus.getChildren().add(new BigLabel("Vos objets enchéris",20));
        try {
            List<Objet> datas = BdD.objetsVendus(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsVendus.getChildren().add(this.vObjetsVendus=new ObjetTable(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
        vlObjetsVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsVendus,1,1);
        
        
        VBox vlObjetsPasVendus = new VBox();
        vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non enchéris",20));
        try {
            List<Objet> datas = BdD.objetsPasVendus(
                    this.main.getSession().getConBdD(), this.main.getSession().getCurUser().orElseThrow());
            vlObjetsPasVendus.getChildren().add(this.vObjetsNonVendus=new ObjetTable2(this.main,datas));
        } catch (SQLException ex) {
            vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
        }
        JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
        vlObjetsPasVendus.setAlignment(Pos.CENTER);
        this.add(vlObjetsPasVendus,2,1);
        this.setHgap(10);
        this.setVgap(20);
        this.setHalignment(this.vbInfos, HPos.CENTER);
        this.setHalignment(this.categories, HPos.CENTER);
        this.setHalignment(this.recherche, HPos.CENTER);
    }

    public ObjetTable getvObjetsEnVente() {
        return vObjetsEnVente;
    }

    public ObjetTable getvObjetsVendus() {
        return vObjetsVendus;
    }

    public ObjetTable2 getvObjetsNonVendus() {
        return vObjetsNonVendus;
    }


    public ComboBox<String> getCategories() {
        return categories;
    }

    public Button getVbInfos() {
        return vbInfos;
    }

    public void setvObjetsEnVente(ObjetTable vObjetsEnVente) {
        this.vObjetsEnVente = vObjetsEnVente;
    }

    public void setvObjetsVendus(ObjetTable vObjetsVendus) {
        this.vObjetsVendus = vObjetsVendus;
    }

    public void setvObjetsNonVendus(ObjetTable2 vObjetsNonVendus) {
        this.vObjetsNonVendus = vObjetsNonVendus;
    } 

    public TextField getRecherche() {
        return recherche;
    }
    
    
    }   

