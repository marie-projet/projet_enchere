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

import java.sql.Timestamp;

/**
 *
 * @author Théo
 */
public class Objet {
    
    private int id;
    private String titre;
    private int prixBase;
    String description;
    Timestamp debut;
    Timestamp fin;
    Categorie categorie;
    Utilisateur proposePar;
    
    public Objet(int id, String titre, int prixBase, String description, Timestamp debut, Timestamp fin, Categorie categorie, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.prixBase = prixBase;
        this.debut = debut;
        this.fin = fin;
        this.categorie = categorie;
        this.proposePar = utilisateur;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getPrixBase() {
        return prixBase;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDebut() {
        return debut;
    }

    public Timestamp getFin() {
        return fin;
    }

    public Categorie getCategorie() {
        return categorie;
    }
    
    public Utilisateur getProposePar() {
        return proposePar;
    }
    
    public void print(){
        System.out.println("____________Objet____________");
        System.out.println(this.getTitre());
        System.out.println(this.getDescription());
        System.out.println("id : "+this.getId());
        System.out.println("prix de base : "+this.getPrixBase());
        System.out.println("debut enchere : "+this.getDebut());
        System.out.println("fin enchere : "+this.getFin());
        System.out.println("categorie : "+this.getCategorie().getNom());
        System.out.println("propose par : "+this.getProposePar().getNom());
    }
}
