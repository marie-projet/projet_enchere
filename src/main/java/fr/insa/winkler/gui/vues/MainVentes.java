
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.gui.JavaFXUtils;
import fr.insa.winkler.gui.MainPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author marie et théo
 * 
 * gère l'affichage des différentes fenêtres de ventes
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
