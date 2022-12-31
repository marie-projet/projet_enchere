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
import fr.insa.winkler.gui.vues.PanneauShowEnchere;
import fr.insa.winkler.gui.vues.PanneauShowVente;
import fr.insa.winkler.gui.vues.Reencherir;
import fr.insa.winkler.projet.BdD;
import fr.insa.winkler.projet.Categorie;
import fr.insa.winkler.projet.Objet;
import java.sql.SQLException;
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
    private Encherir encherir;
    private Reencherir reencherir;
    private PanneauShowVente ventes;
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
                    System.out.println(obj.toString());
                    JavaFXUtils.showInfoObjet(obj.toString());
                }
                this.encheres.getvEnchereGagnante().getSelectionModel().clearSelection();
            }
            if(this.encheres.getvEncherePerdante() != null){
                List<Objet>objetSelctionne=this.encheres.getvEncherePerdante().getSelectedObjects();
                for (Objet obj:objetSelctionne){
                    System.out.println(obj.toString());
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
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.BLUE, 2);
        vlEnchere.setAlignment(Pos.CENTER);
        this.encheres.add(vlEnchere,0,1);
        
        VBox vlEnchereGagnante = new VBox();
        vlEnchereGagnante.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
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
        JavaFXUtils.addSimpleBorder(vlEnchereGagnante, Color.GREEN, 2);
        vlEnchereGagnante.setAlignment(Pos.CENTER);
        this.encheres.add(vlEnchereGagnante,1,1);
        
        VBox vlEncherePerdante = new VBox();
        vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
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
        JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
        vlEncherePerdante.setAlignment(Pos.CENTER);
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
        JavaFXUtils.addSimpleBorder(vlPasEnchere, Color.BLUE, 2);
        vlPasEnchere.setAlignment(Pos.CENTER);
        this.encherir.getGpEnchere().setAlignment(Pos.CENTER);
       
        
     
        VBox vlEnchere = new VBox();
        vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
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
        JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);           
        vlEnchere.setAlignment(Pos.CENTER);
        this.encherir.getGpEnchere().add(vlEnchere,2,1);
        this.encherir.getChildren().add(this.encherir.getGpEnchere());
        }
        
        if(etat==12){
            List<String> a = new ArrayList<>();
            for (String s: this.reencherir.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            
            VBox vlEncherePerdante = new VBox();
            vlEncherePerdante.getChildren().add(new BigLabel("Vos enchères perdantes",20));
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
            vlEncherePerdante.setAlignment(Pos.CENTER);
            JavaFXUtils.addSimpleBorder(vlEncherePerdante, Color.RED, 2);
            this.reencherir.getGpEnchere().add(vlEncherePerdante,0,1);


            VBox vlEnchere = new VBox();
            vlEnchere.getChildren().add(new BigLabel("Vos enchères gagnantes",20));
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
            vlEnchere.setAlignment(Pos.CENTER);
            JavaFXUtils.addSimpleBorder(vlEnchere, Color.GREEN, 2);
            this.reencherir.getGpEnchere().add(vlEnchere,2,1);
            this.reencherir.getChildren().add(this.reencherir.getGpEnchere());
        }
        if(etat==20){
            List<String> a = new ArrayList<>();
            for (String s: this.ventes.getCategories().getSelectionModel().getSelectedItem().split(":")) {
                a.add(s);
            }
            Categorie categorieChoisie=Categorie.predef(Integer.parseInt(a.get(0)));
            VBox vlObjetsEnVente = new VBox();
            vlObjetsEnVente.getChildren().add(new BigLabel("Vos objets en vente",20));
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
            JavaFXUtils.addSimpleBorder(vlObjetsEnVente, Color.BLUE, 2);
            vlObjetsEnVente.setAlignment(Pos.CENTER);
            this.ventes.add(vlObjetsEnVente,0,1);
            
            VBox vlObjetsVendus = new VBox();
            vlObjetsVendus.getChildren().add(new BigLabel("Vos objets vendus",20));
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
            JavaFXUtils.addSimpleBorder(vlObjetsVendus, Color.GREEN, 2);
            vlObjetsVendus.setAlignment(Pos.CENTER);
            this.ventes.add(vlObjetsVendus,1,1);
            
            VBox vlObjetsPasVendus = new VBox();
            vlObjetsPasVendus.getChildren().add(new BigLabel("Vos objets non vendus",20));
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
            JavaFXUtils.addSimpleBorder(vlObjetsPasVendus, Color.RED, 2);
            vlObjetsPasVendus.setAlignment(Pos.CENTER);
            this.ventes.add(vlObjetsPasVendus,2,1);
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
    
    
    
    
    
    
    
}



