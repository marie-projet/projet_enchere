/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.winkler.projet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Théo
 */
public class Categorie {
    
    private int id;
    private String nom;
    
    
    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
     
    public int getId(){
        return this.id;
    }
    public String getNom(){
        return this.nom;
    }
    public static String getNom(int i){
        return predef(i).getNom();
    }
    
    public void print(){
        System.out.println("__________Categorie_________");
        System.out.println(this.getNom());
        System.out.println("id : "+this.getId());
    }
    
    public static Categorie predef(int num){
        switch(num){
            case 0:
                return All();
            case 1:
                return jeux();
            case 2:
                return collections();
            case 3:
                return vetementsEtAccessoires();
            case 4:
                return artEtAntiquites();
            case 5:
                return bijouxEtMontres();
            case 6:
                return livres();
            case 7:
                return ceramiqueEtVerres();
            case 8:
                return electromenager();
            case 9:
                return instrumentsDeMusique();
            case 10:
                return musique();
            case 11:
                return articlesPourLaMaison();
            case 12:
                return articlesPourLeJardin();
            default:
                return autre();
        }
    }
    
    public static Categorie All(){
        return new Categorie(0, "All");
    }
    
    public static Categorie jeux(){
        return new Categorie(1, "Jeux");
    }
    
    public static Categorie collections(){
        return new Categorie(2, "Collections");
    }
    
    public static Categorie vetementsEtAccessoires(){
        return new Categorie(3, "Vetements et accessoires");
    }
    
    public static Categorie artEtAntiquites(){
        return new Categorie(4, "Art et antiquites");
    }
    
    public static Categorie bijouxEtMontres(){
        return new Categorie(5, "Bijoux et montres");
    }
    
    public static Categorie livres(){
        return new Categorie(6, "Livres");
    }
    
    public static Categorie ceramiqueEtVerres(){
        return new Categorie(7, "Ceramique et verres");
    }
    
    public static Categorie electromenager(){
        return new Categorie(8, "Electromenager");
    }
    
    public static Categorie instrumentsDeMusique(){
        return new Categorie(9, "Instruments de musique");
    }
    
    public static Categorie musique(){
        return new Categorie(10, "Musique");
    }
    
    public static Categorie articlesPourLaMaison(){
        return new Categorie(11, "Articles pour la maison");
    }
    
    public static Categorie articlesPourLeJardin(){
        return new Categorie(12, "Articles pour le jardin");
    }
    
    public static Categorie autre(){
        return new Categorie(13, "Autre");
    }
    
    public static List<Categorie> ListCategorie(){
        List<Categorie> categories=new ArrayList<Categorie>();
        for(int i=0; i<14; i++){
            categories.add(predef(i));
        }
        return categories;
    }
        public String toString(){
        return this.getId()+": "+this.getNom();
    }
      
}
