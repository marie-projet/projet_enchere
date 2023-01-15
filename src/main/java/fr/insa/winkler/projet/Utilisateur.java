
package fr.insa.winkler.projet;

/**
 *
 * @author marie et théo
 * 
 * classe utilisateur
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
