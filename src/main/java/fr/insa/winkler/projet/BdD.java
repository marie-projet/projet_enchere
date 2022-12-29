package fr.insa.winkler.projet;

import fr.insa.winkler.utils.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author marie et théo
 */
public class BdD {

    public static Connection connectGeneralPostGres(String host,
            int port, String database,
            String user, String pass)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public static Connection defautConnect()
            throws ClassNotFoundException, SQLException {
        return connectGeneralPostGres("localhost", 5439,
                "postgres", "postgres", "pass");
    }

    public static void creeSchema(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement créé ou pas du tout
        // je vais donc gérer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    CREATE TABLE utilisateur
                    (
                        id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
                        nom character varying(50) NOT NULL,
                        prenom character varying(50),
                        email character varying(100) NOT NULL,
                        pass character varying(50) NOT NULL,
                        codepostal character varying(20),
                        CONSTRAINT utilisateur_pkey PRIMARY KEY (id),
                        CONSTRAINT u_utilisateur_email UNIQUE (email)
                    )
                    
                    """);
            st.executeUpdate(
                    """
                    CREATE TABLE categorie
                    (
                        id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
                        nom character varying(100)  NOT NULL,
                        CONSTRAINT categorie_pkey PRIMARY KEY (id)
                    )
            """);
            st.executeUpdate(
                    """
                    CREATE TABLE enchere
                    (
                        id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
                        de integer NOT NULL,
                        sur integer NOT NULL,
                        quand timestamp without time zone NOT NULL,
                        montant integer NOT NULL,
                        CONSTRAINT enchere_pkey PRIMARY KEY (id)

                    )
            """);
            st.executeUpdate(
                    """
                    CREATE TABLE objet
                    (
                        id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
                        titre character varying(500) NOT NULL,
                        description text ,
                        debut timestamp without time zone NOT NULL,
                        fin timestamp without time zone NOT NULL,
                        prixbase integer NOT NULL,
                        categorie integer NOT NULL,
                        proposepar integer NOT NULL,
                        CONSTRAINT objet_pkey PRIMARY KEY (id)
                    )
            """);

            // je defini les liens entre les clés externes et les clés primaires
            // correspondantes
            st.executeUpdate(
                    """
                    ALTER TABLE OBJET
                        ADD CONSTRAINT fk_objet_categorie FOREIGN KEY (categorie)
                        REFERENCES categorie (id) 
                    """);
            st.executeUpdate(
                    """        
                    ALTER TABLE OBJET
                        ADD CONSTRAINT fk_objet_utilisateur FOREIGN KEY (proposepar)
                        REFERENCES utilisateur (id)
                    """);
            st.executeUpdate(
                    """  
                    ALTER TABLE ENCHERE
                        ADD CONSTRAINT fk_enchere_objet FOREIGN KEY (sur)
                        REFERENCES objet (id) 
                    """);
            st.executeUpdate(
                    """ 
                    ALTER TABLE ENCHERE
                        ADD CONSTRAINT fk_enchere_utilisateur FOREIGN KEY (de)
                        REFERENCES utilisateur (id) 
                    """);
            
            // si j'arrive jusqu'ici, c'est que tout s'est bien passé
            // je confirme (commit) la transaction
            con.commit();
        } catch (SQLException ex) {
            // quelque chose s'est mal passé
            // j'annule la transaction
            con.rollback();
            // puis je renvoie l'exeption pour qu'elle puisse éventuellement
            // être gérée (message à l'utilisateur...)
            throw ex;
        } finally {
            // je retourne dans le mode par défaut de gestion des transaction :
            // chaque ordre au SGBD sera considéré comme une transaction indépendante
            con.setAutoCommit(true);
        }
    }

    // vous serez bien contents, en phase de développement de pouvoir
    // "repartir de zero" : il est parfois plus facile de tout supprimer
    // et de tout recréer que d'essayer de modifier le schema et les données
    public static void deleteSchema(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            // pour être sûr de pouvoir supprimer, il faut d'abord supprimer les liens
            // puis les tables
            // suppression des liens
            try {
                st.executeUpdate(
                        """
                    ALTER TABLE OBJET
                        DROP CONSTRAINT fk_objet_categorie
                            """);
                System.out.println("constraint fk_objet_categorie dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    ALTER TABLE OBJET
                        DROP CONSTRAINT fk_objet_utilisateur
                            """);
                System.out.println("constraint fk_objet_utilisateur dropped");
            } catch (SQLException ex) {
                //nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    ALTER TABLE ENCHERE
                        DROP CONSTRAINT fk_enchere_objet
                            """);
                System.out.println("constraint fk_enchere_objet dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate(
                        """
                    ALTER TABLE ENCHERE
                        DROP CONSTRAINT fk_enchere_utilisateur
                            """);
                System.out.println("constraint fk_enchere_utilisateur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }

            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate(
                        """
                    DROP TABLE utilisateur
                    """);
                System.out.println("table utilisateur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    DROP TABLE utilisateur
                    """);
                System.out.println("table utilisateur dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    DROP TABLE categorie
                    """);
                System.out.println("table categorie dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    DROP TABLE enchere
                    """);
                System.out.println("table enchere dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
            try {
                st.executeUpdate(
                        """
                    drop table objet
                    """);
                System.out.println("table objet dropped");
            } catch (SQLException ex) {
                // nothing to do : maybe the table was not created
            }
        }
    }

    public static void recreeTout(Connection con) throws SQLException {
        deleteSchema(con);
        creeSchema(con);
    }
    
    public static void creeExemple( Connection con) throws SQLException{
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
            """
            INSERT INTO utilisateur (nom,prenom,email,pass,codepostal) 
            values 
            ('Toto',null,'toto@mail.fr','pass1','67084'),
            ('Morane','Bob','bob@mail.fr','felicidad','FR-75007'),
            ('Marley','Bob','bob@mail.com','gg','JAM-JMAAW14'),
            ('L''Eponge','Bob','bob@fond.ocean','pass2',null)
            """); 
            
            st.executeUpdate(
            """
            INSERT INTO categorie (nom) values
            ('Jeux'),
            ('Collections'),
            ('Vetements et accessoires'),
            ('Art et antiquites'),
            ('Bijoux et montres'),
            ('Livres'),
            ('Ceramique et verres'),
            ('Electromenager'),
            ('Instruments de musique'),
            ('Musique'),
            ('Articles pour la maison'),
            ('Articles pour le jardin'),
            ('Autre')
            """);
            
            st.executeUpdate(
            """
            INSERT INTO objet (titre,description,debut,fin,prixbase,categorie,proposepar) 
            values 
            ('chaise','à bascule','2022-09-01 10:00:00.0','2022-09-02 11:00:00.0','5000','1','1'),
            ('lit','superbe lit bla bla','2022-09-02 09:00:00.0','2022-09-04 10:00:00.0','20000','1','3'),
            ('blouson','en cuir noir trop beau','2022-09-02 09:00:00.0',' 2022-09-02 11:00:00.0','30000','2','2')
            """);
            
            st.executeUpdate(
            """
            INSERT INTO enchere (de,sur,quand,montant) values
            ('3','1','2022-09-01 12:00:00.0','5500'),
            ('2','1','2022-09-01 13:00:00.0','6000'),
            ('3','1','2022-09-02 10:00:00.0','7000'),
            ('3','3','2022-09-02 10:00:00.0','30000')
            """);
        }
    }
        
    public static List<Utilisateur> listUtilisateur(Connection con) throws SQLException {
        List<Utilisateur> res = new ArrayList<Utilisateur>();
        try ( PreparedStatement pst = con.prepareStatement("select id,nom,prenom,email,pass,codepostal from utilisateur ")) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Utilisateur(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("pass"),rs.getString("codepostal")));
                }
                return res;
            }
        }
    }
    
    public static void afficheListUtilisateur(List<Utilisateur> list)throws SQLException {
        for(Utilisateur utilisateur: list){
            utilisateur.print();
        }
    }
    
    /*
    public static void afficheTousLesUtilisateurs(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet rs = st.executeQuery(
                    "select id,nom,prenom,email,pass from utilisateur")) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String nom = rs.getString(2);
                    String prenom = rs.getString(3);
                    String email= rs.getString(4);
                    String motDePasse = rs.getString("pass");
                    
                    System.out.println(id + " : " + nom +" "+ prenom +" "+email+"(" + motDePasse+ ")");
                }
            }
        }
    }
    */
    
    public static List<Categorie> listCategorie(Connection con) throws SQLException {
        List<Categorie> res = new ArrayList<>();
        try ( PreparedStatement pst = con.prepareStatement("select id,nom from categorie ")) {
            try ( ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    res.add(new Categorie(rs.getInt("id"),rs.getString("nom")));
                }
                return res;
            }
        }
    }
    
    public static void afficheListCategorie(List<Categorie> list)throws SQLException {
        for(Categorie categorie: list){
            categorie.print();
        }
    }
    
    /*
    public static void afficheToutesLesCategories(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet rs = st.executeQuery(
                    "select nom from categorie")) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String nom = rs.getString(2);
                    System.out.println(id + " : " + nom );
                }
            }
        }
    }
    
    
    //dernierEnchereSurObjet(con,rs.getInt("id"))
    */
    
    public static List<Objet> listObjet(Connection con) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( Statement st = con.createStatement()) {
            try ( ResultSet rs = st.executeQuery(
                    """
                    select id,titre,description,debut,fin,prixbase,categorie,proposepar from objet 
                    """)) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    int proposePar = rs.getInt(8);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                }
                return res;
            }
        }
    }
    
    public static void afficheListObjet(Connection con, List<Objet> list)throws SQLException {
        for(Objet objet: list){
            objet.print(con);
        }
    }
    
    /*
    Donne la liste des objets mis en enchère par un utilisateur.
    */
    
    public static List<Objet> listObjetDunUtilisateur(Connection con, Utilisateur utilisateur) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id,objet.titre,objet.description,objet.debut,objet.fin,objet.prixbase,objet.categorie from objet
        where objet.proposepar=?
        """)) {
            st.setInt(1,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
        public static void afficheToutesLesEncheres(Connection con) throws SQLException {
            try ( Statement st = con.createStatement()) {
                try ( ResultSet rs = st.executeQuery(
                """
                select enchere.id, utilisateur.nom, utilisateur.prenom, objet.titre, quand, montant from enchere
                join objet on enchere.sur=objet.id
                join utilisateur on utilisateur.id=enchere.de 
                order by enchere.id ASC
              """)) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String nom = rs.getString(2);
                    String prenom = rs.getString(3);
                    String objet = rs.getString(4);
                    Timestamp quand = rs.getTimestamp(5);
                    int montant = rs.getInt(6);
                    System.out.println(id + " : " + objet+ " - " +montant+ "€" + " - "+quand+" - " + nom + " "+prenom);
                      }
                  }
        }
    }
    
    public static void afficheEnchereObjet(Connection con, int identifiant) throws SQLException {
    try ( PreparedStatement st = con.prepareStatement(
        """
        select enchere.id, utilisateur.nom, utilisateur.prenom, objet.titre, quand, montant from enchere
        join objet on enchere.sur=objet.id
        join utilisateur on utilisateur.id=enchere.de 
        where enchere.sur=?
        order by enchere.id ASC
        """)) {
            st.setInt(1,identifiant);
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String nom = rs.getString(2);
                    String prenom = rs.getString(3);
                    String objet = rs.getString(4);
                    Timestamp quand = rs.getTimestamp(5);
                    int montant = rs.getInt(6);
                    System.out.println("Les enchères déjà effectuées sur l'objet:");
                    System.out.println(id + " : " + objet+ " - " +montant+ "€" + " - "+quand+" - " + nom + " "+prenom);
                }
            }
        }
    }
    
    /*
    Donne la liste des objets dont l'utilisateur a enchéri au moins une fois.
    */
    public static List<Objet> objetEncheri(Connection con, Utilisateur utilisateur) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select distinct on (objet.id) objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie, objet.proposepar from enchere
        join objet on objet.id = enchere.sur
        where enchere.de=?
        
        """)) {
            st.setInt(1,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    int proposePar = rs.getInt(8);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                }
                return res;
            }
        }
    }
    
    /*
    La même chose mais avec une catégorie précise.
    */
    public static List<Objet> objetEncheri(Connection con, Utilisateur utilisateur, Categorie categorie) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select distinct on (objet.id) objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.proposepar from enchere
        join objet on objet.id = enchere.sur
        where enchere.de=?
        and objet.categorie=?
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,categorie.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    int proposePar = rs.getInt(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie.getNom(), proposePar));
                }
                return res;
            }
        }
    }
    
    
    /*
    Donne la liste des objets dont l'utilisateur à l'enchère la plus élevé. Prend la liste de objetEncheri en entrée.
    */
    public static List<Objet> objetEncheriGagnant(Connection con, Utilisateur utilisateur, List<Objet> list) throws SQLException {
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            if(dernierEnchereSurObjet(con,objet.getId()) == dernierEnchereSurObjetDunUtilisateur(con,objet.getId(),utilisateur)){
                res.add(objet);
            }
        }
        return res;
    }
    
    /*
    Donne la liste des objets dont l'utilisateur à enchéri au moins une fois mais un autre utilisateur à enchéri plus que lui. Prend la liste de objetEncheri en entrée.
    */
    public static List<Objet> objetEncheriPerdant(Connection con, Utilisateur utilisateur, List<Objet> list) throws SQLException {
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            if(dernierEnchereSurObjet(con,objet.getId()) > dernierEnchereSurObjetDunUtilisateur(con,objet.getId(),utilisateur)){
                res.add(objet);
            }
        }
        return res;
    }
    /*
    Pas tiptop mais en haut ça marche pas.
    */
    public static List<Objet> objetParCategorie(Connection con, Categorie categorie, List<Objet> list) throws SQLException {
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            if(objet.getCategorie().equals(categorie.getNom())){
                res.add(objet);
            }
        }
        return res;
    }
    
    public static List<Objet> objetPasEncheri(Connection con, Utilisateur utilisateur) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.id not in (select enchere.sur from enchere where enchere.de=?)
        
        """)) {
            st.setInt(1,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
        public static List<Objet> objetPasEncheri(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.id not in (select enchere.sur from enchere where enchere.de=?) and objet.categorie=?
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
    public static List<Objet> objetsEnVente(Connection con, Utilisateur utilisateur) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=?  
        """)) {
            st.setInt(1,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
        public static List<Objet> objetsEnVente(Connection con, Utilisateur utilisateur,Categorie cat) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
    public static List<Objet> objetsPasVendus(Connection con, Utilisateur utilisateur) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id not in (select enchere.sur from enchere)
        """)) {
            st.setInt(1,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
        public static List<Objet> objetsPasVendus(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id not in (select enchere.sur from enchere) and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
    public static List<Objet> objetsVendus(Connection con, Utilisateur utilisateur) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id in (select enchere.sur from enchere)
        """)) {
            st.setInt(1,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }
    
    
    public static List<Objet> objetsVendus(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id in (select enchere.sur from enchere) and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                }
                return res;
            }
        }
    }

    public static void trouveParNom(Connection con, String nom) throws SQLException {
        try ( PreparedStatement st = con.prepareStatement(
                "select id,nom,pass from utilisateur "
                + "where nom = ?")) {
            st.setString(1, nom);

            try ( ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String motDePasse = rs.getString("pass");
                    System.out.println(id + " : " + nom + "(" + motDePasse+ ")");
                }
            }
        }
    }
    
    public static Utilisateur ajouterUtilisateur(Connection con, String nom, String prenom, String email, String pass, String codePostal) throws SQLException {
        con.setAutoCommit(false);
        try ( PreparedStatement chercheEmail = con.prepareStatement(
                "select id from utilisateur where email = ?")) {
            chercheEmail.setString(1, email);
            ResultSet testEmail = chercheEmail.executeQuery();
            if (testEmail.next()) {
                throw new Error("email deja utilsé");
            }
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                    insert into utilisateur (nom,prenom,email,pass,codepostal) values (?,?,?,?,?)
                    """,PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, email);
                pst.setString(4, pass);
                pst.setString(5, codePostal);
                pst.executeUpdate();
                con.commit();
                try ( ResultSet rid = pst.getGeneratedKeys()) {
                    rid.next();// une seule clé dunc un next suffit
                    int id = rid.getInt(1);
                    return new Utilisateur(id, nom, prenom, email, pass, codePostal);
                }
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
    
    
    public static Utilisateur ajouterUtilisateur(Connection con, String nom, String email, String pass, String codepostal) throws SQLException {
        return ajouterUtilisateur(con, nom, null, email, pass, codepostal);
    }

    public static Utilisateur ajouterUtilisateur(Connection con) throws SQLException {
        String nom = Console.entreeString("nom : ");
        String prenom = Console.entreeString("prénom : ");
        String email = Console.entreeString("email : ");
        String pass = Console.entreeString("password : ");
        String passTest = Console.entreeString("verify password : ");

           while(!pass.equals(passTest)){
               System.out.println("Your first and second password are not the same.");
               pass = Console.entreeString("password : ");
               passTest = Console.entreeString("verify password : ");
           }

           String codepostal = Console.entreeString("codepostal : ");
        return ajouterUtilisateur(con,nom,prenom,email,pass,codepostal);
    }
    
    /*_________________________________ON DECIDE DE NE PAS AVOIR ADMIN DANS LA BDD_________________________________
    public static void ajouterAdmin(Connection con) throws SQLException {
        con.setAutoCommit(false);
        try ( PreparedStatement pst = con.prepareStatement(
        """
            insert into utilisateur (nom,email,pass,codepostal) values (?,?,?,?)
            """,PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, "admin");
            pst.setString(2, "admin");
            pst.setString(3, "admin");
            pst.setString(4, "00000");
            pst.executeUpdate();
            con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        }
    }
    */
   
   public static void ajouterCategorie(Connection con, String nom) throws SQLException {
       try ( PreparedStatement pst = con.prepareStatement(
               """
               insert into categorie (nom) values (?)
               """)) {

           pst.setString(1, nom);
           //coucou marie
           pst.executeUpdate();

        }
    }
   
   public static void ajouterCategorie(Connection con) throws SQLException {
       // lors de la creation du PreparedStatement, il faut que je précise
       // que je veux qu'il conserve les clés générées
        String nom = Console.entreeString("nom : ");
        ajouterCategorie(con,nom);
    }
   
    public static String demanderDate (Connection con) throws SQLException{
        String jour =Console.entreeString("Jour de fin de vente sour la forme annee-mois-jour:");
        String heure=Console.entreeString("Heure de fin de vente sour la forme heure:minute:seconde");
        String date =jour+' '+heure;
        return date;
   }
   
   public static int choisirCategorie(Connection con) throws SQLException{
       afficheListCategorie(listCategorie(con));
       return Console.entreeEntier("Entrez l'identifiant de la categorie de votre objet");
   }
   
   public static int choisirObjet(Connection con) throws SQLException{
       afficheListObjet(con,listObjet(con));
       return Console.entreeEntier("Entrez l'identifiant de l'objet de votre choix");
   }
   
   public static void ajouterObjet(Connection con, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int categorie, int proposepar) throws SQLException {
       try ( PreparedStatement pst = con.prepareStatement(
               """
               insert into objet (titre, description, debut, fin, prixbase,categorie, proposepar) values (?,?,?,?,?,?,?)
               """)) {

           pst.setString(1, titre);
           pst.setString(2, description);
           pst.setTimestamp(3, debut);
           pst.setTimestamp(4, fin);
           pst.setInt(5, prixbase);
           pst.setInt(6, categorie);
           pst.setInt(7, proposepar);

           pst.executeUpdate();

        }
    }
   
   public static void ajouterObjet(Connection con, Utilisateur utilisateur) throws SQLException {
        String titre = Console.entreeString("titre : ");
        String description = Console.entreeString("description : ");
        Long datetime = System.currentTimeMillis();
        Timestamp debut = new Timestamp(datetime);
        Timestamp fin= Timestamp.valueOf(demanderDate(con));
        int prixbase = Console.entreeEntier("prix de base: ");
        int categorie= choisirCategorie(con);
        int proposepar=utilisateur.getId();
        ajouterObjet(con,titre,description,debut,fin,prixbase,categorie,proposepar);
    }
   
   
    /*
    Donne le montant le plus élevé enchéri sur l'objet "sur".
    */
    public static int dernierEnchereSurObjet(Connection con, int sur) throws SQLException {
        try ( PreparedStatement chercheMontant = con.prepareStatement(
         "select max(montant) as montant from enchere where enchere.sur=?")) {
            chercheMontant.setInt(1, sur);
            ResultSet rs = chercheMontant.executeQuery();
            if(rs.next() && rs.getInt("montant")!=0) {
                return rs.getInt("montant");    
            } else {
                try ( PreparedStatement cherchePrix = con.prepareStatement(
            "select prixbase from objet where objet.id=?")) {
                    cherchePrix.setInt(1, sur);
                    ResultSet rp = cherchePrix.executeQuery();
                    if(rp.next()) {
                        //int prix = rp.getInt(1);
                        return rp.getInt("prixbase"); 
                    } else {
                        throw new Error("objet invalide "+ sur);
                    }
                }
            }
        }
    }
    
    
    /*
    Donne le montant le plus élevé qu'un utilisateur à enchérit sur l'objet d'id "sur".
    */
    public static int dernierEnchereSurObjetDunUtilisateur(Connection con, int sur, Utilisateur utilisateur) throws SQLException {
        try ( PreparedStatement chercheMontant = con.prepareStatement(
         "select max(montant) as montant from enchere where enchere.sur=? and enchere.de=?")) {
            chercheMontant.setInt(1, sur);
            chercheMontant.setInt(2, utilisateur.getId());
            ResultSet rs = chercheMontant.executeQuery();
            if(rs.next()) {
                return rs.getInt("montant");
            } else {
                try ( PreparedStatement cherchePrix = con.prepareStatement(
            "select prixbase from objet where objet.id=?")) {
                    cherchePrix.setInt(1, sur);
                    ResultSet rp = cherchePrix.executeQuery();
                    if(rp.next()) {
                        //int prix = rp.getInt(1);
                        return rp.getInt("prixbase"); 
                    } else {
                        throw new Error("objet invalide "+ sur);
                    }
                }
            }
        }
    }
   
   public static void ajouterEnchere(Connection con, int de, int sur, Timestamp quand, int montant) throws SQLException {
        int prix=dernierEnchereSurObjet(con, sur);
        if (montant>prix){  
            try ( PreparedStatement pst = con.prepareStatement(
                    """
                    insert into enchere (de, sur, quand, montant) values (?,?,?,?)
                    """)) {

                pst.setInt(1, de);
                pst.setInt(2, sur);
                pst.setTimestamp(3, quand);
                pst.setInt(4, montant);

                pst.executeUpdate();

            } 
        }else {
            throw new Error("Montant inférieur à l'enchère précédente");
        }
    }
   
    public static void ajouterEnchere(Connection con, Utilisateur utilisateur) throws SQLException {
        int de=utilisateur.getId();
        int sur=choisirObjet(con);
        afficheEnchereObjet(con,sur);
        Long datetime = System.currentTimeMillis();
        Timestamp quand = new Timestamp(datetime);
        int montant = Console.entreeEntier("montant de votre enchère: ");
        ajouterEnchere(con,de,sur,quand,montant);
    }
    
 
   
    /*
   public static Utilisateur connexionUtilisateur(Connection con, String email, String pass) throws SQLException {
       //coucou téo
        try ( PreparedStatement pst = con.prepareStatement("select * from utilisateur where email = ? and pass = ?")) {
            pst.setString(1, email);
            pst.setString(2, pass);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return new Utilisateur(res.getInt("id"), res.getString("nom"), res.getString("prenom"), email, pass, res.getString("codepostal"));
            } else {
                return new Utilisateur();//en cas de problème
            }
        }
    }
   
   public static Utilisateur connexionUtilisateur(Connection con) throws SQLException {
       String email = Console.entreeString("email : ");
       String pass = Console.entreeString("pass : ");
       return connexionUtilisateur(con, email, pass);
    }
*/

    public static Optional<Utilisateur> connexionUtilisateur(Connection con, String email, String pass) throws SQLException {
        if(email.equals("admin") && pass.equals("admin")){
            return Optional.of(Utilisateur.admin());
        } else {
            try ( PreparedStatement pst = con.prepareStatement("select * from utilisateur where email = ? and pass = ?")) {
                pst.setString(1, email);
                pst.setString(2, pass);
                ResultSet res = pst.executeQuery();
                if (res.next()) {
                    System.out.println("l'identifiant est"+res.getInt("id"));
                    return Optional.of(new Utilisateur(res.getInt("id"), res.getString("nom"), res.getString("prenom"), email, pass, res.getString("codepostal")));
                } else {
                    return Optional.empty();
                }
            }
        }
    }
   
   public static Optional<Utilisateur> connexionUtilisateur(Connection con) throws SQLException {
       String email = Console.entreeString("email : ");
       String pass = Console.entreeString("pass : ");
       return connexionUtilisateur(con, email, pass);
    }

    
   
   
   public static void BilanUtilisateur(Connection con, Utilisateur utilisateur) throws SQLException {
       int identifiant=utilisateur.getId();
       try ( PreparedStatement chercheEncheres = con.prepareStatement(
         "select objet.titre, objet.categorie, objet.prixbase, objet.debut, objet.fin from enchere"
                 + "join objet on objet.id=enchere.sur"
                 + "where enchere.de=?")) {
            chercheEncheres.setInt(1, identifiant);
            ResultSet rs = chercheEncheres.executeQuery();
            if(rs.next()) {
        
            }
        }
    }
   
   
   public static void menu(Connection con) throws SQLException {
        int rep = -1;
        while (rep != 0) {
            System.out.println("Menu BdD Encheres");
            System.out.println("=============");
            System.out.println("1) Créer/recréer la BdD initiale");
            System.out.println("2) Créer la base de donée exemple");
            System.out.println("3) Liste des utilisateurs");
            System.out.println("4) Chercher un utilisateur par nom");
            System.out.println("5) Ajouter un utilisateur");
            System.out.println("6) Ajouter une categorie");
            System.out.println("7) Ajouter un objet");
            System.out.println("8) Encherir");
            System.out.println("0) quitter");
            rep = Console.entreeEntier("Votre choix : ");
            try {
                if (rep == 1) {
                    recreeTout(con);

                } else if (rep==2){
                    creeExemple(con);
                }else if (rep == 3) {
                    afficheListUtilisateur(listUtilisateur(con));

                } else if (rep == 4) {
                    String cherche = Console.entreeString("nom cherché :");
                    trouveParNom(con, cherche);
                } else if (rep == 5) {
                    ajouterUtilisateur(con).print();
                } else if (rep == 6) {
                   ajouterCategorie(con); 
                } else if (rep==7){
                    System.out.println("veuillez vous connecter");
                    Utilisateur utilisateur=connexionUtilisateur(con).get();
                    ajouterObjet(con,utilisateur);
                } else if (rep==8){
                    System.out.println("veuillez vous connecter");
                    Utilisateur utilisateur=connexionUtilisateur(con).get();
                    ajouterEnchere(con,utilisateur);
                }

            } catch (SQLException ex) {
                throw new Error(ex);
            }
        }
        
    }
   
    public static void menuV2(Connection con) throws SQLException {
        int rep = 0;
        Utilisateur utilActif = new Utilisateur();
        while (true) {
            System.out.println("__________MENU_BASE__________");
            
            System.out.println("[1] Se connecter");
            System.out.println("[2] Créer un nouveau compte");
            System.out.println("[0] Quitter");
            
            rep = Console.entreeEntier("Votre1 choix : ");
            
            if (rep == 1){ // Se connecter
                utilActif = connexionUtilisateur(con).get();
            } else if (rep == 2){ // Créer un nouveau compte
                utilActif = ajouterUtilisateur(con);
            } else { // Quitter
                break;
            }
            rep = -1;
            if (utilActif.getId() == 0){ // menu admin
                while (rep != 0) {
                    System.out.println("__________MENU_ADMIN__________");
                    
                    System.out.println("Bienvenu "+utilActif.getNom()+" !");
                    
                    System.out.println("[1] Créer/recréer la BdD initiale");
                    System.out.println("[2] Créer la base de donée exemple");
                    System.out.println("[3] Liste des utilisateurs");
                    System.out.println("[4] Chercher un utilisateur par nom");
                    System.out.println("[5] Ajouter un utilisateur");
                    System.out.println("[6] Ajouter une categorie");
                    System.out.println("[0] Se déconecter");

                    rep = Console.entreeEntier("Votre choix : ");

                    switch(rep){
                        case 1: // Créer/recréer la BdD initiale
                            recreeTout(con);
                            break;
                        case 2: // Créer la base de donée exemple
                            creeExemple(con);
                            break;
                        case 3: // Liste des utilisateurs
                            afficheListUtilisateur(listUtilisateur(con));
                            break;
                        case 4: // Chercher un utilisateur par nom
                            String cherche = Console.entreeString("Nom cherché :");
                            trouveParNom(con, cherche);
                            break;
                        case 5: // Ajouter un utilisateur
                            ajouterUtilisateur(con).print();
                            break;
                        case 6: // Ajouter une categorie
                            ajouterCategorie(con);
                            break;
                    }
                }
            } else { // menu utilisateur
                while (rep != 0) {
                    
                    //afficheListObjet(con,objetEncheri(con,utilActif,Categorie.predef(1)));
                    
                    System.out.println("_______MENU_UTILISATEUR_______");
                    
                    System.out.println("Bienvenu(e) M,Mme "+utilActif.getNom()+" !");
                    
                    System.out.println("[3] Liste des utilisateurs");
                    System.out.println("[4] Chercher un utilisateur par nom");
                    System.out.println("[6] Mes objets");
                    System.out.println("[7] Ajouter un objet");
                    System.out.println("[8] Encherir");
                    System.out.println("[9] Mes enchers");
                    System.out.println("[0] Se déconecter");

                    rep = Console.entreeEntier("Votre choix : ");

                    switch(rep){
                        case 3: // Liste des utilisateurs
                            afficheListUtilisateur(listUtilisateur(con));
                            break;
                        case 4: // Chercher un utilisateur par nom
                            String cherche = Console.entreeString("Nom cherché :");
                        trouveParNom(con, cherche);
                            break;
                        case 6: // Mes objets
                            afficheListObjet(con,listObjetDunUtilisateur(con,utilActif));
                            break;
                        case 7: // Ajouter un objet
                            ajouterObjet(con,utilActif);
                            break;
                        case 8: // Encherir
                            ajouterEnchere(con,utilActif);
                            break;
                        case 9: // Mes enchers
                            List<Objet> list = objetEncheri(con,utilActif);
                            System.out.println("Objets dont l'enchère est gagnante : ");
                            afficheListObjet(con,objetEncheriGagnant(con,utilActif,list));
                            System.out.println("Objets dont l'enchère est perdante : ");
                            afficheListObjet(con,objetEncheriPerdant(con,utilActif,list));
                            break;
                    }
                }
            }
        }
    }
   
    public static void main(String[] args) {
        try ( Connection con = defautConnect()) {
            System.out.println("connecté !");
            //afficheToutesLesEncheres(con);
            afficheListCategorie(listCategorie(con));
            afficheListObjet(con,listObjet(con));
            menu(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
