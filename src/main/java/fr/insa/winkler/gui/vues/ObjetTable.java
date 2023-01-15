
package fr.insa.winkler.gui.vues;

import fr.insa.winkler.gui.MainPane;
import fr.insa.winkler.projet.Objet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author marie et théo
 * 
 * affiche les objets en vente sous forme de table
 */
public class ObjetTable extends TableView {
    
    private MainPane main;
    
    private ObservableList<Objet> objets;
    
    public ObjetTable(MainPane main,List<Objet> objets) {
        this.main = main;
        this.objets = FXCollections.observableArrayList(objets);
        
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        
        TableColumn<Objet,String> cTitre = 
                new TableColumn<>("titre");
        TableColumn<Objet,String> cPrix = 
                new TableColumn<>("enchère actuelle");
        this.getColumns().addAll(cTitre,cPrix);
        
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        

        
        cTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        cPrix.setCellValueFactory(new PropertyValueFactory<>("prixActuel"));
        this.setItems(this.objets);
    }

    public List<Objet> getObjects() {
        return this.objets;
    }
    
    public List<Objet> getSelectedObjects() {
        return this.getSelectionModel().getSelectedItems();
    }
    
     public void addObjects(List<Objet> objets) {
        this.getItems().addAll(objets);
    }
    
    public void removeObjects(List<Objet> objets) {
        this.getItems().removeAll(objets);
    }
}
