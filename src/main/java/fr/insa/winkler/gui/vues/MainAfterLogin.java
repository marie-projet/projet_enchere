
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.gui.MainPane;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;


/**
 *
 * @author marie et théo
 * 
 * gère l'affichage des différentes fenêtres enchères
 */
public class MainAfterLogin extends VBox {

    private MainPane main;

    private Tab EnchereCurrentUser;
    private Tab encherir;
    private Tab reencherir;
    private Tab encheresTerminees;
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
        this.encheresTerminees = new Tab("Enchères terminées");
        this.encheresTerminees.setOnSelectionChanged((t) -> {
           this.encheresTerminees.setContent(new PanneauShowEnchereTerminees(this.main));
        });
        this.encheresTerminees.setContent(new PanneauShowEnchereTerminees(this.main));
        this.allTabs = new TabPane(this.EnchereCurrentUser,this.encherir,this.reencherir,this.encheresTerminees);
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
