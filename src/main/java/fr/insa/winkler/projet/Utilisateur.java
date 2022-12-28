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
public class Utilisateur {
    
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String pass;
    private String codePostal;
    
    public Utilisateur(int id, String nom, String prenom, String email, String pass, String codePostal) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pass = pass;
        this.codePostal = codePostal;
    }
    public Utilisateur(int id, String nom, String email, String pass, String codePostal) {
        this(id, nom, null, email, pass, codePostal);
    }
    public Utilisateur() { // en cas de problème
        this(0, null, null,null, null);
    }
    
    public static Utilisateur admin(){
        return new Utilisateur(0,"admin",null,"admin","00000");
    }
    
    public int getId(){
        return this.id;
    }
    public String getNom(){
        return this.nom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPass(){
        return this.pass;
    }
    public String getCodePostal(){
        return this.codePostal;
    }
    
    public void print(){
        System.out.println("_________Utilisateur_________");
        if (this.getPrenom() == null){
            System.out.println(this.getNom());
        } else {
            System.out.println(this.getNom()+" "+this.getPrenom());
        }
        System.out.println("id : "+this.getId());
        System.out.println("email : "+this.getEmail());
        System.out.println("pass : "+this.getPass());
        System.out.println("code postal : "+this.getCodePostal());
    }
}
