
package fr.insa.winkler.projet;

import java.sql.Timestamp;
import java.sql.Connection;


/**
 *
 * @author marie et théo
 * 
 * classe objet
 */
public class Objet {
    
    private int id;
    private String titre;
    private int prixBase;
    String description;
    Timestamp debut;
    Timestamp fin;
    String categorie;
    int proposePar;
    private int prixActuel;
    
    public Objet(Connection con,int id, String titre, int prixBase, String description, Timestamp debut, Timestamp fin, String categorie, int proposePar) {
        this.id = id;
        this.titre = titre;
        this.prixBase = prixBase;
        this.description = description;
        this.debut = debut;
        this.fin = fin;
        this.categorie = categorie;
        this.proposePar = proposePar;
        try{
            this.prixActuel=BdD.dernierEnchereSurObjet(con,this.getId());
        }catch(Exception e){
            this.prixActuel= this.prixBase;
        }
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

    public String getCategorie() {
        return categorie;
    }
    
    public int getProposePar() {
        return proposePar;
    }
    
    public int getPrixActuel() {
        return prixActuel;
    }
    
    
    public String toString() {
        String horloge = this.getFin().toString().substring(11,16);
        String calendrier = (this.getFin().getDay()+1) + "/" + (this.getFin().getMonth()+1) + "/" + (this.getFin().getYear()+1900);
        //System.out.print(horloge+" le "+calendrier);
        
        String res="";
        res=res+"                             "+this.getTitre().toUpperCase()+"\n"+"\n";
        res=res+"Description: "+this.getDescription()+"\n";
        res=res+"Catégorie: "+this.getCategorie()+"\n";
        res=res+"Prixe de base: "+this.getPrixBase()+"\n";
        res=res+"Prix actuel : "+this.getPrixActuel()+"\n";
        res=res+"Fin de l'enchère a " + horloge + " le " + calendrier + "\n";
        return res;
    }
        
       
    
    public String toString2() {
        String res="";
        res=res+"                             "+this.getTitre().toUpperCase()+"\n"+"\n";
        res=res+"Description: "+this.getDescription()+"\n";
        res=res+"Catégorie: "+this.getCategorie()+"\n";
        res=res+"Prixe de base: "+this.getPrixBase()+"\n";
        res=res+"Prix final : "+this.getPrixActuel()+"\n";
        res=res+"Fin de l'enchère : "+this.getFin()+"\n";
        return res;
    }
    
    public void print(Connection con){
        System.out.println("____________Objet____________");
        System.out.println(this.getTitre());
        System.out.println(this.getDescription());
        System.out.println("id : "+this.getId());
        System.out.println("prix de base : "+this.getPrixBase());
        System.out.println("debut enchere : "+this.getDebut());
        System.out.println("fin enchere : "+this.getFin());
        System.out.println("categorie : "+this.getCategorie());
        System.out.println("propose par : "+this.getProposePar());
        System.out.println("prix actuel : "+this.getPrixActuel());
    }
}
