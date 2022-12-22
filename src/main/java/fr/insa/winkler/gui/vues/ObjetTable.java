/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * Table pour afficher des utilisateurs.
 * voir https://devstory.net/11079/javafx-tableview
 * @author francois
 */
public class ObjetTable extends TableView {
    
    private MainPane main;
    
    private ObservableList<Objet> objets;
    
    public ObjetTable(MainPane main,List<Objet> objets) {
        this.main = main;
        this.objets = FXCollections.observableArrayList(objets);
        
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        TableColumn<Objet,String> cNom = 
                new TableColumn<>("nom");
        TableColumn<Objet,String> cPrix = 
                new TableColumn<>("prix");
        this.getColumns().addAll(cNom,cPrix);
        
        // si l'on ne veut pas d'espace supplémentaire
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        cNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
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
