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
    
    public ObjetTable(MainPane main,List<Objet> utilisateurs) {
        this.main = main;
        this.objets = FXCollections.observableArrayList(objets);
        
        this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        TableColumn<Objet,String> cNom = 
                new TableColumn<>("nom");
        TableColumn<Objet,String> cRole = 
                new TableColumn<>("role");
        this.getColumns().addAll(cNom,cRole);
        
        // si l'on ne veut pas d'espace supplémentaire
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        cNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cRole.setCellValueFactory(new PropertyValueFactory<>("nomRole"));
        this.setItems(this.objets);
    }

    public List<Objet> getObjets() {
        return this.objets;
    }
    
    public List<Objet> getSelectedObjets() {
        return this.getSelectionModel().getSelectedItems();
    }
    
}
