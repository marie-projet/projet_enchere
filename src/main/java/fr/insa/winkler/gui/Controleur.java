/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.winkler.gui;

import fr.insa.winkler.gui.vues.BigLabel;
import fr.insa.winkler.gui.vues.Encherir;
import fr.insa.winkler.gui.vues.ObjetTable;
import fr.insa.winkler.gui.vues.ObjetTable2;
import fr.insa.winkler.gui.vues.ObjetTable3;
import fr.insa.winkler.gui.vues.PanneauShowEnchere;
import fr.insa.winkler.gui.vues.PanneauShowEnchereTerminees;
import fr.insa.winkler.gui.vues.PanneauShowVente;
import fr.insa.winkler.gui.vues.PanneauShowVenteTerminees;
import fr.insa.winkler.gui.vues.Reencherir;
import fr.insa.winkler.gui.vues.Vendre;
import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author mariewinkler
 */
public class Controleur {
    
    private MainPane vue;
    private PanneauShowEnchere encheres;
    private PanneauShowEnchereTerminees encheresTerminees;
    private Encherir encherir;
    private Reencherir reencherir;
    private PanneauShowVente ventes;
    private PanneauShowVenteTerminees ventesTerminees;
    private Vendre vendre;
    private int etat;
    
    public Controleur (MainPane vue){
        this.vue=vue;
    }
    public void changeEtat(int nouvelEtat) {
        this.etat = nouvelEtat;
    }
    
    public void infos(){
        if(this.etat==10){
            if(this.encheres.getvEnchere() != null ){
                List<Objet> objetSelctionne=this.encheres.getvEnchere().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.encheres.getvEnchere().getSelectionModel().clearSelection();
            }
            if(this.encheres.getvEnchereGagnante() != null){
                List<Objet>objetSelctionne=this.encheres.getvEnchereGagnante().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.encheres.getvEnchereGagnante().getSelectionModel().clearSelection();
            }
            if(this.encheres.getvEncherePerdante() != null){
                List<Objet>objetSelctionne=this.encheres.getvEncherePerdante().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.encheres.getvEncherePerdante().getSelectionModel().clearSelection();
            }       
        }
        if(this.etat==11){
            if(this.encherir.getvPasEnchere()!=null){
                List<Objet> objetSelctionne=this.encherir.getvPasEnchere().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.encherir.getvPasEnchere().getSelectionModel().clearSelection();
            }
            if(this.encherir.getvEnchereGagnante() != null){
                List<Objet>objetSelctionne=this.encherir.getvEnchereGagnante().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.encherir.getvEnchereGagnante().getSelectionModel().clearSelection();
            }
        }
        if(this.etat==12){
           if(this.reencherir.getvEncherePerdante() != null){
                List<Objet> objetSelctionne=this.reencherir.getvEncherePerdante() .getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.reencherir.getvEncherePerdante() .getSelectionModel().clearSelection();
            }
            if(this.reencherir.getvEnchereGagnante()  != null){
                List<Objet>objetSelctionne=this.reencherir.getvEnchereGagnante() .getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.reencherir.getvEnchereGagnante() .getSelectionModel().clearSelection();
            }            
        }
        
        if(etat==13){
            if(this.encheresTerminees.getvEnchere() != null ){
                List<Objet> objetSelctionne=this.encheresTerminees.getvEnchere().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString2());
                }
                this.encheresTerminees.getvEnchere().getSelectionModel().clearSelection();
            }
            if(this.encheresTerminees.getvEnchereGagnante() != null){
                List<Objet>objetSelctionne=this.encheresTerminees.getvEnchereGagnante().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString2());
                }
                this.encheresTerminees.getvEnchereGagnante().getSelectionModel().clearSelection();
            }
            if(this.encheres.getvEncherePerdante() != null){
                List<Objet>objetSelctionne=this.encheresTerminees.getvEncherePerdante().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString2());
                }
                this.encheresTerminees.getvEncherePerdante().getSelectionModel().clearSelection();
            }       
        }
    if(etat==20){
        if(this.ventes.getvObjetsEnVente() != null){
                List<Objet> objetSelctionne=this.ventes.getvObjetsEnVente().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.ventes.getvObjetsEnVente().getSelectionModel().clearSelection();
            }
            if(this.ventes.getvObjetsVendus() != null){
                List<Objet>objetSelctionne=this.ventes.getvObjetsVendus().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.ventes.getvObjetsVendus().getSelectionModel().clearSelection();
            }
            if(this.ventes.getvObjetsNonVendus()!= null){
                List<Objet>objetSelctionne=this.ventes.getvObjetsNonVendus().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.ventes.getvObjetsNonVendus().getSelectionModel().clearSelection();
            }
    }
    
    if(etat==22){
        if(this.ventesTerminees.getvObjetsEnVente() != null){
                List<Objet> objetSelctionne=this.ventesTerminees.getvObjetsEnVente().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString2());
                }
                this.ventesTerminees.getvObjetsEnVente().getSelectionModel().clearSelection();
            }
            if(this.ventesTerminees.getvObjetsVendus() != null){
                List<Objet>objetSelctionne=this.ventesTerminees.getvObjetsVendus().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString2());
                }
                this.ventesTerminees.getvObjetsVendus().getSelectionModel().clearSelection();
            }
            if(this.ventesTerminees.getvObjetsNonVendus()!= null){
                List<Objet>objetSelctionne=this.ventesTerminees.getvObjetsNonVendus().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    JavaFXUtils.showInfoObjet(obj.toString2());
                }
                this.ventesTerminees.getvObjetsNonVendus().getSelectionModel().clearSelection();
            }
        }
    }
    
    
    
    
    
    public void encherir(){
        if(etat==11){
            List<Objet> select=this.encherir.getvPasEnchere().getSelectedObjects();
                for (Objet obj:select){
                    String montantStr=JavaFXUtils.Encherir(obj.toString());
                    int v=1;
                    if (montantStr.equals("")){
                        v=0;
                    }
                    if(v==1){
                        try{
                            try{
                                Integer.parseInt(montantStr);                                
                            }
                            catch(Exception e){
                                JavaFXUtils.showErrorInAlert("Montant non valide, veuillez entrez un nombre entier");
                            }
                            BdD.ajouterEnchere(this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(), Integer.parseInt(montantStr),obj.getId());
                        }
                        catch (SQLException ex) {
                            JavaFXUtils.showErrorInAlert("Montant non valide, veuillez entrez un nombre entier");
                        }                       
                    }
                }  
            this.encherir.reInit();
        }
        if(etat==12){
            List<Objet> select=this.reencherir.getvEncherePerdante().getSelectedObjects();
                for (Objet obj:select){
                    String montantStr=JavaFXUtils.Encherir(obj.toString());
                    int v=1;
                    if (montantStr.equals("")){
                        v=0;
                    }
                    if(v==1){
                        try{
                            try{
                                Integer.parseInt(montantStr);                                
                            }
                            catch(Exception e){
                                JavaFXUtils.showErrorInAlert("Montant non valide, veuillez entrez un nombre entier");
                            }
                            BdD.ajouterEnchere(this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(), Integer.parseInt(montantStr),obj.getId());
                        }
                        catch (SQLException ex) {
                            JavaFXUtils.showErrorInAlert("Montant non valide, veuillez entrez un nombre entier");
                        }                       
                    }
                }  
            this.reencherir.reInit();
        }
    }
    
    
    
    
    public void categorie(){
        if(etat==10){
            List<String> a = new ArrayList<>();
            for (String s: this.encheres.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères",20));
            
            if(this.encheres.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetEncheri(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.encheres.setvEnchere(new ObjetTable(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheres.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                    List<Objet> datas = BdD.objetEncheri(
                            this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                    this.encheres.setvEnchere(new ObjetTable(this.vue,datas));
                    vlEnchere.getChildren().add(this.encheres.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }                        
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetEncheri(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheres.getRecherche().getText(),categorieChoisie);
                        this.encheres.setvEnchere(new ObjetTable(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheres.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                    List<Objet> datas = BdD.objetEncheri(
                            this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheres.getRecherche().getText());
                    this.encheres.setvEnchere(new ObjetTable(this.vue,datas));
                    vlEnchere.getChildren().add(this.encheres.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }                        
                }
            }
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
        vlEnchere.setAlignment(Pos.CENTER);
        
        
        VBox vlEnchereGagnante = new VBox();
        vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
        if(this.encheres.getRecherche()==null){
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    this.encheres.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheres.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow()));
                    this.encheres.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheres.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        else{
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText(),categorieChoisie));
                    this.encheres.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheres.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText()));
                    this.encheres.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheres.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
        vlEnchereGagnante.setAlignment(Pos.CENTER);
                
        VBox vlEncherePerdante = new VBox();
        vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
        if(this.encheres.getRecherche()==null){
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    this.encheres.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheres.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                        this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow()));
                    this.encheres.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheres.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        else{
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText(),categorieChoisie));
                        this.encheres.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.encheres.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
            }
            else{
                try {
                List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                        this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText()));
                    this.encheres.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheres.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        } 
        JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
        vlEncherePerdante.setAlignment(Pos.CENTER);

        this.encheres.add(vlEnchere,0,1);
        this.encheres.add(vlEnchereGagnante,1,1);
        this.encheres.add(vlEncherePerdante,2,1);

        }
        
        
        
        if(etat==11){
            List<String> a = new ArrayList<>();
            for (String s: this.encherir.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlPasEnchere = new VBox();
            Label lPasEnchere = new Label("Objets en vente");
            lPasEnchere.setStyle("-fx-font-size: 20");
            vlPasEnchere.getChildren().add(lPasEnchere);
            
            if(this.encherir.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                   try {
                    List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                 this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                    this.encherir.setvPasEnchere(new ObjetTable(this.vue,objetsPasEncheris));
                    vlPasEnchere.getChildren().add(this.encherir.getvPasEnchere());
                    } catch (SQLException ex) {
                        vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
                else{
                    try {
                        List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.encherir.setvPasEnchere(new ObjetTable(this.vue,objetsPasEncheris));
                        vlPasEnchere.getChildren().add(this.encherir.getvPasEnchere());
                    } catch (SQLException ex) {
                        vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                   try {
                    List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                 this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encherir.getRecherche().getText(),categorieChoisie);
                    this.encherir.setvPasEnchere(new ObjetTable(this.vue,objetsPasEncheris));
                    vlPasEnchere.getChildren().add(this.encherir.getvPasEnchere());
                    } catch (SQLException ex) {
                        vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
                else{
                    try {
                        List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encherir.getRecherche().getText());
                        this.encherir.setvPasEnchere(new ObjetTable(this.vue,objetsPasEncheris));
                        vlPasEnchere.getChildren().add(this.encherir.getvPasEnchere());
                    } catch (SQLException ex) {
                        vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
            }
        JavaFXUtils.addSimpleBorder(vlPasEnchere, Color.BLUE, 2);
        vlPasEnchere.setAlignment(Pos.CENTER);
        
     
        VBox vlEnchere = new VBox();
        vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
        
        if(this.encherir.getRecherche()==null){
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    this.encherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchere.getChildren().add(this.encherir.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow()));
                    this.encherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchere.getChildren().add(this.encherir.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        else{
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encherir.getRecherche().getText(),categorieChoisie));
                    this.encherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchere.getChildren().add(this.encherir.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encherir.getRecherche().getText()));
                    this.encherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchere.getChildren().add(this.encherir.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);           
        vlEnchere.setAlignment(Pos.CENTER);
        this.encherir.getGpEnchere().add(vlPasEnchere,0,1);
        this.encherir.getGpEnchere().add(vlEnchere,2,1);
        }
        
        
        
        
        if(etat==12){
            List<String> a = new ArrayList<>();
            for (String s: this.reencherir.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            
            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
            
            if(this.reencherir.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),categorieChoisie));
                        this.reencherir.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.reencherir.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
                else{
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow()));
                        this.reencherir.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.reencherir.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                            List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                    this.vue.getSession
                        ().getConBdD(), this.vue.getSession
                        ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                        ().getConBdD(), this.vue.getSession
                        ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText(),categorieChoisie));
                            this.reencherir.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                            vlEncherePerdante.getChildren().add(this.reencherir.getvEncherePerdante());
                        } catch (SQLException ex) {
                            vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                        }
                }
                else{
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText()));
                        this.reencherir.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.reencherir.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                } 
            }
                
            vlEncherePerdante.setAlignment(Pos.CENTER);
            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            this.reencherir.getGpEnchere().add(vlEncherePerdante,0,1);


            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
            if(this.reencherir.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),categorieChoisie));
                        this.reencherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                        vlEnchere.getChildren().add(this.reencherir.getvEnchereGagnante());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
                else{
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow()));
                        this.reencherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                        vlEnchere.getChildren().add(this.reencherir.getvEnchereGagnante());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText(),categorieChoisie));
                        this.reencherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                        vlEnchere.getChildren().add(this.reencherir.getvEnchereGagnante());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
                else{
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText()));
                        this.reencherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                        vlEnchere.getChildren().add(this.reencherir.getvEnchereGagnante());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                }
            }
            vlEnchere.setAlignment(Pos.CENTER);
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
            this.reencherir.getGpEnchere().add(vlEnchere,2,1);
        }
        
        if(etat==13){
            List<String> a = new ArrayList<>();
            for (String s: this.encheresTerminees.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères terminées",20));
            
            if(this.encheresTerminees.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetEncheriPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.encheresTerminees.setvEnchere(new ObjetTable3(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheresTerminees.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                    List<Objet> datas = BdD.objetEncheriPerime(
                            this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                    this.encheresTerminees.setvEnchere(new ObjetTable3(this.vue,datas));
                    vlEnchere.getChildren().add(this.encheresTerminees.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }                        
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetEncheriPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText(),categorieChoisie);
                        this.encheresTerminees.setvEnchere(new ObjetTable3(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheresTerminees.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                    List<Objet> datas = BdD.objetEncheriPerime(
                            this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText());
                    this.encheresTerminees.setvEnchere(new ObjetTable3(this.vue,datas));
                    vlEnchere.getChildren().add(this.encheresTerminees.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }                        
                }
            }
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
        vlEnchere.setAlignment(Pos.CENTER);
        
        
        VBox vlEnchereGagnante = new VBox();
        vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnées",20));
        if(this.encheresTerminees.getRecherche()==null){
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    this.encheresTerminees.setvEnchereGagnante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheresTerminees.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow()));
                    this.encheresTerminees.setvEnchereGagnante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheresTerminees.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        else{
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText(),categorieChoisie));
                    this.encheresTerminees.setvEnchereGagnante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheresTerminees.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText()));
                    this.encheresTerminees.setvEnchereGagnante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheresTerminees.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
        vlEnchereGagnante.setAlignment(Pos.CENTER);
                
        VBox vlEncherePerdante = new VBox();
        vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdues",20));
        if(this.encheresTerminees.getRecherche()==null){
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),categorieChoisie));
                    this.encheresTerminees.setvEncherePerdante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheresTerminees.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
            else{
                try {
                List<Objet> objetsEncheris = BdD.objetEncheriPerdantPerime(
                        this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow()));
                    this.encheresTerminees.setvEncherePerdante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheresTerminees.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        }
        else{
            if(categorieChoisie.getId()!=0){
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdantPerime(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText(),categorieChoisie));
                        this.encheresTerminees.setvEncherePerdante(new ObjetTable3(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.encheresTerminees.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
            }
            else{
                try {
                List<Objet> objetsEncheris = BdD.objetEncheriPerdantPerime(
                        this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText()));
                    this.encheresTerminees.setvEncherePerdante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheresTerminees.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            }
        } 
        JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
        vlEncherePerdante.setAlignment(Pos.CENTER);

        this.encheresTerminees.add(vlEnchere,0,1);
        this.encheresTerminees.add(vlEnchereGagnante,1,1);
        this.encheresTerminees.add(vlEncherePerdante,2,1);
        }
        
        
        
        if(etat==20){
            List<String> a = new ArrayList<>();
            for (String s: this.ventes.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlObjetsEnVente = new VBox();
            vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
            
            if(this.ventes.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsEnVente(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.ventes.setvObjetsEnVente(new ObjetTable(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventes.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsEnVente(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.ventes.setvObjetsEnVente(new ObjetTable(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventes.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsEnVente(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText(),categorieChoisie);
                        this.ventes.setvObjetsEnVente(new ObjetTable(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventes.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsEnVente(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText());
                        this.ventes.setvObjetsEnVente(new ObjetTable(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventes.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            } 
            JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
            vlObjetsEnVente.setAlignment(Pos.CENTER);
            this.ventes.add(vlObjetsEnVente,0,1);
            
            VBox vlObjetsVendus = new VBox();
            vlObjetsVendus.getChildren().add(new BigLabel("Vos objets enchéris",20));
            if(this.ventes.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.ventes.setvObjetsVendus(new ObjetTable(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventes.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.ventes.setvObjetsVendus(new ObjetTable(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventes.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText(),categorieChoisie);
                        this.ventes.setvObjetsVendus(new ObjetTable(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventes.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText());
                        this.ventes.setvObjetsVendus(new ObjetTable(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventes.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }       
                        
            JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
            vlObjetsVendus.setAlignment(Pos.CENTER);
            this.ventes.add(vlObjetsVendus,1,1);
            
            VBox vlObjetsPasVendus = new VBox();
            vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non encheris",20));
            if(this.ventes.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsPasVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.ventes.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventes.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsPasVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.ventes.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventes.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsPasVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText(),categorieChoisie);
                        this.ventes.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventes.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsPasVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText());
                        this.ventes.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventes.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
            vlObjetsPasVendus.setAlignment(Pos.CENTER);
            this.ventes.add(vlObjetsPasVendus,2,1);
        }
        
        if(etat==22){
            List<String> a = new ArrayList<>();
            for (String s: this.ventesTerminees.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlObjetsEnVente = new VBox();
            vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets mis en vente",20));
            
            if(this.ventesTerminees.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsEnVentePerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.ventesTerminees.setvObjetsEnVente(new ObjetTable3(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventesTerminees.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsEnVentePerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.ventesTerminees.setvObjetsEnVente(new ObjetTable3(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventesTerminees.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsEnVentePerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText(),categorieChoisie);
                        this.ventesTerminees.setvObjetsEnVente(new ObjetTable3(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventesTerminees.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsEnVentePerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText());
                        this.ventesTerminees.setvObjetsEnVente(new ObjetTable3(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventesTerminees.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            } 
            JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
            vlObjetsEnVente.setAlignment(Pos.CENTER);
            this.ventesTerminees.add(vlObjetsEnVente,0,1);
            
            VBox vlObjetsVendus = new VBox();
            vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
            if(this.ventesTerminees.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.ventesTerminees.setvObjetsVendus(new ObjetTable3(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventesTerminees.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.ventesTerminees.setvObjetsVendus(new ObjetTable3(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventesTerminees.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText(),categorieChoisie);
                        this.ventesTerminees.setvObjetsVendus(new ObjetTable3(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventesTerminees.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText());
                        this.ventesTerminees.setvObjetsVendus(new ObjetTable3(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventesTerminees.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }       
                        
            JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
            vlObjetsVendus.setAlignment(Pos.CENTER);
            this.ventesTerminees.add(vlObjetsVendus,1,1);
            
            VBox vlObjetsPasVendus = new VBox();
            vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
            if(this.ventesTerminees.getRecherche()==null){
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsPasVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),categorieChoisie);
                        this.ventesTerminees.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventesTerminees.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsPasVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow());
                        this.ventesTerminees.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventesTerminees.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            else{
                if(categorieChoisie.getId()!=0){
                    try {
                        List<Objet> datas = BdD.objetsPasVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText(),categorieChoisie);
                        this.ventesTerminees.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventesTerminees.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
                else{
                    try {
                        List<Objet> datas = BdD.objetsPasVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText());
                        this.ventesTerminees.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventesTerminees.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                }
            }
            JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
            vlObjetsPasVendus.setAlignment(Pos.CENTER);
            this.ventesTerminees.add(vlObjetsPasVendus,2,1);
        }
        
    }
    
    public void vendre(){
        Long datetime = System.currentTimeMillis();
        Timestamp debut = new Timestamp(datetime);
        String titre=this.vendre.getTitre().getText();
        String description=this.vendre.getDescription().getText();
        Timestamp fin=Timestamp.valueOf(this.vendre.getDateFin().getValue().atStartOfDay());
        int prixBase=Integer.parseInt(this.vendre.getPrixBase().getText());
        List<String> a = new ArrayList<>();
            for (String s: this.vendre.getCategorie().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
        try{
            BdD.ajouterObjet(this.vue.getSession().getConBdD(), titre,description, debut, fin, prixBase, categorieChoisie.getId(), this.vue.getSession().getCurUser().orElseThrow().getId());
        }catch (SQLException ex) {
             JavaFXUtils.showErrorInAlert("Problème rencontré, veuillez réessayer");
        }
        this.vendre.reInit();
    }
    
    public void recherche(){
        if(etat==10){
            if(this.encheres.getCategories().getSelectionModel().getSelectedItem()==null || this.encheres.getCategories().getSelectionModel().getSelectedItem().equals("0: All") ){
                VBox vlEnchere = new VBox();
                vlEnchere.getChildren().add(new BigLabel("Vos enchères",20));
                    try {
                        List<Objet> datas = BdD.objetEncheri(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheres.getRecherche().getText());
                        this.encheres.setvEnchere(new ObjetTable(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheres.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
            vlEnchere.setAlignment(Pos.CENTER);


            VBox vlEnchereGagnante = new VBox();
            vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText()));
                    this.encheres.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheres.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
            vlEnchereGagnante.setAlignment(Pos.CENTER);

            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText()));
                    this.encheres.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheres.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }

            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            vlEncherePerdante.setAlignment(Pos.CENTER);

            this.encheres.add(vlEnchere,0,1);
            this.encheres.add(vlEnchereGagnante,1,1);
            this.encheres.add(vlEncherePerdante,2,1);

            }
            
            else{
                List<String> a = new ArrayList<>();
                for (String s: this.encheres.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                    a.add(s);
                }
                Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
                VBox vlEnchere = new VBox();
                vlEnchere.getChildren().add(new BigLabel("Vos enchères",20));
                    try {
                        List<Objet> datas = BdD.objetEncheri(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheres.getRecherche().getText(),categorieChoisie);
                        this.encheres.setvEnchere(new ObjetTable(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheres.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
            vlEnchere.setAlignment(Pos.CENTER);


            VBox vlEnchereGagnante = new VBox();
            vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText(),categorieChoisie));
                    this.encheres.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheres.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
            vlEnchereGagnante.setAlignment(Pos.CENTER);

            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheres.getRecherche().getText(),categorieChoisie));
                    this.encheres.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheres.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }

            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            vlEncherePerdante.setAlignment(Pos.CENTER);

            this.encheres.add(vlEnchere,0,1);
            this.encheres.add(vlEnchereGagnante,1,1);
            this.encheres.add(vlEncherePerdante,2,1);
            }
        }
        
        if(etat==11){
            if(this.encherir.getCategories().getSelectionModel().getSelectedItem()==null || this.encherir.getCategories().getSelectionModel().getSelectedItem().equals("0: All")){
                VBox vlPasEnchere = new VBox();
                Label lPasEnchere = new Label("Objets en vente");
                lPasEnchere.setStyle("-fx-font-size: 20");
                vlPasEnchere.getChildren().add(lPasEnchere);

                try {
                    List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                 this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encherir.getRecherche().getText());
                    this.encherir.setvPasEnchere(new ObjetTable(this.vue,objetsPasEncheris));
                    vlPasEnchere.getChildren().add(this.encherir.getvPasEnchere());
                    } catch (SQLException ex) {
                        vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
            JavaFXUtils.addSimpleBorder(vlPasEnchere, Color.BLUE, 2);
            vlPasEnchere.setAlignment(Pos.CENTER);


            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encherir.getRecherche().getText()));
                    this.encherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchere.getChildren().add(this.encherir.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);           
            vlEnchere.setAlignment(Pos.CENTER);
            this.encherir.getGpEnchere().add(vlPasEnchere,0,1);
            this.encherir.getGpEnchere().add(vlEnchere,2,1);
            }
            
            else{
                List<String> a = new ArrayList<>();
                for (String s: this.encherir.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                    a.add(s);
                }
                Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
                VBox vlPasEnchere = new VBox();
                Label lPasEnchere = new Label("Objets en vente");
                lPasEnchere.setStyle("-fx-font-size: 20");
                vlPasEnchere.getChildren().add(lPasEnchere);

                try {
                    List<Objet> objetsPasEncheris = BdD.objetPasEncheri(
                 this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encherir.getRecherche().getText(),categorieChoisie);
                    this.encherir.setvPasEnchere(new ObjetTable(this.vue,objetsPasEncheris));
                    vlPasEnchere.getChildren().add(this.encherir.getvPasEnchere());
                    } catch (SQLException ex) {
                        vlPasEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
            JavaFXUtils.addSimpleBorder(vlPasEnchere, Color.BLUE, 2);
            vlPasEnchere.setAlignment(Pos.CENTER);


            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encherir.getRecherche().getText(),categorieChoisie));
                    this.encherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                    vlEnchere.getChildren().add(this.encherir.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);           
            vlEnchere.setAlignment(Pos.CENTER);
            this.encherir.getGpEnchere().add(vlPasEnchere,0,1);
            this.encherir.getGpEnchere().add(vlEnchere,2,1);
            }
        }
        
        if(etat==12){
            if(this.reencherir.getCategories().getSelectionModel().getSelectedItem()==null || this.reencherir.getCategories().getSelectionModel().getSelectedItem().equals("0: All")){
                VBox vlEncherePerdante = new VBox();
                vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText()));
                        this.reencherir.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.reencherir.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }

                vlEncherePerdante.setAlignment(Pos.CENTER);
                JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
                this.encherir.getGpEnchere().add(vlEncherePerdante,0,1);


                VBox vlEnchere = new VBox();
                vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText()));
                        this.reencherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                        vlEnchere.getChildren().add(this.reencherir.getvEnchereGagnante());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                vlEnchere.setAlignment(Pos.CENTER);
                JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
                this.reencherir.getGpEnchere().add(vlEnchere,2,1);
            }
            else{
                List<String> a = new ArrayList<>();
                for (String s: this.reencherir.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                    a.add(s);
                }
                Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
                VBox vlEncherePerdante = new VBox();
                vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriPerdant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText(),categorieChoisie));
                        this.reencherir.setvEncherePerdante(new ObjetTable(this.vue,objetsEncheris));
                        vlEncherePerdante.getChildren().add(this.reencherir.getvEncherePerdante());
                    } catch (SQLException ex) {
                        vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                    }

                vlEncherePerdante.setAlignment(Pos.CENTER);
                JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
                this.encherir.getGpEnchere().add(vlEncherePerdante,0,1);


                VBox vlEnchere = new VBox();
                vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
                    try {
                        List<Objet> objetsEncheris = BdD.objetEncheriGagnant(
                                this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),BdD.objetEncheri(this.vue.getSession
                    ().getConBdD(), this.vue.getSession
                    ().getCurUser().orElseThrow(),this.reencherir.getRecherche().getText(),categorieChoisie));
                        this.reencherir.setvEnchereGagnante(new ObjetTable(this.vue,objetsEncheris));
                        vlEnchere.getChildren().add(this.reencherir.getvEnchereGagnante());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD",20));
                    }
                vlEnchere.setAlignment(Pos.CENTER);
                JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
                this.reencherir.getGpEnchere().add(vlEnchere,2,1);
            }
        }
        
        if(etat==13){
            if(this.encheresTerminees.getCategories().getSelectionModel().getSelectedItem()==null || this.encheresTerminees.getCategories().getSelectionModel().getSelectedItem().equals("0: All") ){
                VBox vlEnchere = new VBox();
                vlEnchere.getChildren().add(new BigLabel("Vos enchères terminées",20));
                    try {
                        List<Objet> datas = BdD.objetEncheriPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText());
                        this.encheresTerminees.setvEnchere(new ObjetTable3(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheresTerminees.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
            vlEnchere.setAlignment(Pos.CENTER);


            VBox vlEnchereGagnante = new VBox();
            vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnées",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText()));
                    this.encheresTerminees.setvEnchereGagnante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheresTerminees.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
            vlEnchereGagnante.setAlignment(Pos.CENTER);

            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdues",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText()));
                    this.encheresTerminees.setvEncherePerdante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheresTerminees.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }

            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            vlEncherePerdante.setAlignment(Pos.CENTER);

            this.encheresTerminees.add(vlEnchere,0,1);
            this.encheresTerminees.add(vlEnchereGagnante,1,1);
            this.encheresTerminees.add(vlEncherePerdante,2,1);

            }
            
            else{
                List<String> a = new ArrayList<>();
                for (String s: this.encheresTerminees.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                    a.add(s);
                }
                Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
                VBox vlEnchere = new VBox();
                vlEnchere.getChildren().add(new BigLabel("Vos enchères terminées",20));
                    try {
                        List<Objet> datas = BdD.objetEncheriPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText(),categorieChoisie);
                        this.encheresTerminees.setvEnchere(new ObjetTable3(this.vue,datas));
                        vlEnchere.getChildren().add(this.encheresTerminees.getvEnchere());
                    } catch (SQLException ex) {
                        vlEnchere.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
            vlEnchere.setAlignment(Pos.CENTER);


            VBox vlEnchereGagnante = new VBox();
            vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnées",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriGagnantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText(),categorieChoisie));
                    this.encheresTerminees.setvEnchereGagnante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEnchereGagnante.getChildren().add(this.encheresTerminees.getvEnchereGagnante());
                } catch (SQLException ex) {
                    vlEnchereGagnante.getChildren().add(new BigLabel("Probleme BdD",20));
                }
            JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
            vlEnchereGagnante.setAlignment(Pos.CENTER);

            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdues",20));
                try {
                    List<Objet> objetsEncheris = BdD.objetEncheriPerdantPerime(
                            this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),BdD.objetEncheriPerime(this.vue.getSession
                ().getConBdD(), this.vue.getSession
                ().getCurUser().orElseThrow(),this.encheresTerminees.getRecherche().getText(),categorieChoisie));
                    this.encheresTerminees.setvEncherePerdante(new ObjetTable3(this.vue,objetsEncheris));
                    vlEncherePerdante.getChildren().add(this.encheresTerminees.getvEncherePerdante());
                } catch (SQLException ex) {
                    vlEncherePerdante.getChildren().add(new BigLabel("Probleme BdD",20));
                }

            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            vlEncherePerdante.setAlignment(Pos.CENTER);

            this.encheresTerminees.add(vlEnchere,0,1);
            this.encheresTerminees.add(vlEnchereGagnante,1,1);
            this.encheresTerminees.add(vlEncherePerdante,2,1);
            }
        }
               
        if(etat==20){
            if(this.ventes.getCategories().getSelectionModel().getSelectedItem()==null || this.ventes.getCategories().getSelectionModel().getSelectedItem().equals("0: All")){
                VBox vlObjetsEnVente = new VBox();
                vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
                    try {
                        List<Objet> datas = BdD.objetsEnVente(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText());
                        this.ventes.setvObjetsEnVente(new ObjetTable(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventes.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }

                JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
                vlObjetsEnVente.setAlignment(Pos.CENTER);
                this.ventes.add(vlObjetsEnVente,0,1);

                VBox vlObjetsVendus = new VBox();
                vlObjetsVendus.getChildren().add(new BigLabel("Vos objets enchéris",20));
                    try {
                        List<Objet> datas = BdD.objetsVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText());
                        this.ventes.setvObjetsVendus(new ObjetTable(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventes.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }


                JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
                vlObjetsVendus.setAlignment(Pos.CENTER);
                this.ventes.add(vlObjetsVendus,1,1);

                VBox vlObjetsPasVendus = new VBox();
                vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non encheris",20));
                    try {
                        List<Objet> datas = BdD.objetsPasVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText());
                        this.ventes.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventes.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
                vlObjetsPasVendus.setAlignment(Pos.CENTER);
                this.ventes.add(vlObjetsPasVendus,2,1);
            }
            else{
                 List<String> a = new ArrayList<>();
                for (String s: this.ventes.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                    a.add(s);
                }
                Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
                                VBox vlObjetsEnVente = new VBox();
                vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
                    try {
                        List<Objet> datas = BdD.objetsEnVente(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText(),categorieChoisie);
                        this.ventes.setvObjetsEnVente(new ObjetTable(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventes.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }

                JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
                vlObjetsEnVente.setAlignment(Pos.CENTER);
                this.ventes.add(vlObjetsEnVente,0,1);

                VBox vlObjetsVendus = new VBox();
                vlObjetsVendus.getChildren().add(new BigLabel("Vos objets enchéris",20));
                    try {
                        List<Objet> datas = BdD.objetsVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText(),categorieChoisie);
                        this.ventes.setvObjetsVendus(new ObjetTable(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventes.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }


                JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
                vlObjetsVendus.setAlignment(Pos.CENTER);
                this.ventes.add(vlObjetsVendus,1,1);

                VBox vlObjetsPasVendus = new VBox();
                vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non encheris",20));
                    try {
                        List<Objet> datas = BdD.objetsPasVendus(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventes.getRecherche().getText(),categorieChoisie);
                        this.ventes.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventes.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
                vlObjetsPasVendus.setAlignment(Pos.CENTER);
                this.ventes.add(vlObjetsPasVendus,2,1);
            }
        }
        
        if(etat==22){
            if(this.ventesTerminees.getCategories().getSelectionModel().getSelectedItem()==null || this.ventesTerminees.getCategories().getSelectionModel().getSelectedItem().equals("0: All")){
                VBox vlObjetsEnVente = new VBox();
                vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets mis en vente",20));
                    try {
                        List<Objet> datas = BdD.objetsEnVentePerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText());
                        this.ventesTerminees.setvObjetsEnVente(new ObjetTable3(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventesTerminees.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }

                JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
                vlObjetsEnVente.setAlignment(Pos.CENTER);
                this.ventesTerminees.add(vlObjetsEnVente,0,1);

                VBox vlObjetsVendus = new VBox();
                vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
                    try {
                        List<Objet> datas = BdD.objetsVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText());
                        this.ventesTerminees.setvObjetsVendus(new ObjetTable3(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventesTerminees.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }


                JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
                vlObjetsVendus.setAlignment(Pos.CENTER);
                this.ventesTerminees.add(vlObjetsVendus,1,1);

                VBox vlObjetsPasVendus = new VBox();
                vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
                    try {
                        List<Objet> datas = BdD.objetsPasVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText());
                        this.ventesTerminees.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventesTerminees.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
                vlObjetsPasVendus.setAlignment(Pos.CENTER);
                this.ventesTerminees.add(vlObjetsPasVendus,2,1);
            }
            else{
                 List<String> a = new ArrayList<>();
                for (String s: this.ventesTerminees.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                    a.add(s);
                }
                Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
                                VBox vlObjetsEnVente = new VBox();
                vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets mis en vente",20));
                    try {
                        List<Objet> datas = BdD.objetsEnVentePerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText(),categorieChoisie);
                        this.ventesTerminees.setvObjetsEnVente(new ObjetTable3(this.vue,datas));
                        vlObjetsEnVente.getChildren().add(this.ventesTerminees.getvObjetsEnVente());
                    } catch (SQLException ex) {
                        vlObjetsEnVente.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }

                JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
                vlObjetsEnVente.setAlignment(Pos.CENTER);
                this.ventesTerminees.add(vlObjetsEnVente,0,1);

                VBox vlObjetsVendus = new VBox();
                vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
                    try {
                        List<Objet> datas = BdD.objetsVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText(),categorieChoisie);
                        this.ventesTerminees.setvObjetsVendus(new ObjetTable3(this.vue,datas));
                        vlObjetsVendus.getChildren().add(this.ventesTerminees.getvObjetsVendus());
                    } catch (SQLException ex) {
                        vlObjetsVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }


                JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
                vlObjetsVendus.setAlignment(Pos.CENTER);
                this.ventesTerminees.add(vlObjetsVendus,1,1);

                VBox vlObjetsPasVendus = new VBox();
                vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
                    try {
                        List<Objet> datas = BdD.objetsPasVendusPerime(
                                this.vue.getSession().getConBdD(), this.vue.getSession().getCurUser().orElseThrow(),this.ventesTerminees.getRecherche().getText(),categorieChoisie);
                        this.ventesTerminees.setvObjetsNonVendus(new ObjetTable2(this.vue,datas));
                        vlObjetsPasVendus.getChildren().add(this.ventesTerminees.getvObjetsNonVendus());
                    } catch (SQLException ex) {
                        vlObjetsPasVendus.getChildren().add(new BigLabel("Probleme BdD : "+ex.getLocalizedMessage(),20));
                    }
                JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
                vlObjetsPasVendus.setAlignment(Pos.CENTER);
                this.ventesTerminees.add(vlObjetsPasVendus,2,1);
            }
        }
    }
    
        
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setEncherir(Encherir encherir) {
        this.encherir = encherir;
    }

    public void setEncheres(PanneauShowEnchere encheres) {
        this.encheres = encheres;
    }

    public void setReencherir(Reencherir reencherir) {
        this.reencherir = reencherir;
    }

    public void setVentes(PanneauShowVente ventes) {
        this.ventes = ventes;
    }

    public void setVendre(Vendre vendre) {
        this.vendre = vendre;
    }

    public void setEncheresTerminees(PanneauShowEnchereTerminees encheresTerminees) {
        this.encheresTerminees = encheresTerminees;
    }

    public void setVentesTerminees(PanneauShowVenteTerminees ventesTerminees) {
        this.ventesTerminees = ventesTerminees;
    }
    
    
    
    
    
    
    
    
    
}



