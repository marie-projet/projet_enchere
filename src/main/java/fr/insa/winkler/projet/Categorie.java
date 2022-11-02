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
    
    public void print(){
        System.out.println("__________Categorie_________");
        System.out.println(this.getNom());
        System.out.println("id : "+this.getId());
    }
}
