package fr.insa.winkler.projet;

import fr.insa.winkler.gui.JavaFXUtils;
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
    
    /**
     * méthode générale de connexion à la base de données
     * @param host String
     * @param port int
     * @param database String
     * @param user String
     * @param pass String
     * @return Connection
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
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
    
    /**
     * méthode de connexion par défault à la base de données
     * @return Connection
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static Connection defautConnect()
            throws ClassNotFoundException, SQLException {
        return connectGeneralPostGres("localhost", 5432,
                "postgres", "postgres", "pass");
    }
    
    /**
     * cree les tables et contraintes de la base de données 
     * @param con Connection
     * @throws SQLException 
     */
    public static void creeSchema(Connection con)
            throws SQLException {
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

            // on definit les liens entre les clés externes et les clés primaires
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
            // on confirme (commit) la transaction puisque tout s'est bien passe
            con.commit();
        } catch (SQLException ex) {
            // on annule la transaction
            con.rollback();
            // on renvoie l'exeption pour qu'elle puisse éventuellement
            // être gérée (message à l'utilisateur...)
            throw ex;
        } finally {
            // on retourne dans le mode par défaut de gestion des transaction :
            con.setAutoCommit(true);
        }
    }

    /**
     * supprime les contraintes et les table de la BDD
     * @param con Connection
     * @throws SQLException 
     */
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
    
    
    /**
     * supprime et recree les tables de la BDD
     * @param con Connection
     * @throws SQLException 
     */
    public static void recreeTout(Connection con) throws SQLException {
        deleteSchema(con);
        creeSchema(con);
    }
    
    
    /**
     * cree la BDD exemple (BDD initiale)
     * @param con Connection
     * @throws SQLException 
     */
    public static void creeExemple( Connection con) throws SQLException{
        try ( Statement st = con.createStatement()) {
            st.executeUpdate(
            """
            INSERT INTO utilisateur (nom,prenom,email,pass,codepostal) 
            values 
            ('admin','admin','admin','admin','00000'),
            ('Toto',null,'toto@mail.fr','pass1','67084'),
            ('Morane','Bob','bob@mail.fr','felicidad','FR-75007'),
            ('Marley','Bob','bob@mail.com','gg','JAM-JMAAW14'),
            ('L''Eponge','Bob','bob@fond.ocean','pass2',null),
            ('Winkler','Marie','winkler.marie@icloud.com','pass','67110'),
            ('Silvagni','Théo','tsilvagni01@gmail.com','oscar','67116'),
            ('Musk','Elon','elon.musk@tesla.com','UeQka07-kMvva','90011'),
            ('Bezos','Jeff','ceo@amazon.com','thune','79821'),
            ('Bern','Stephane','steph.bern@aol.com','ohhhlala','75003'),
            ('Plaza','Stephane','stephane@plaza-immobilier.fr','zizou','75011')
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
            ('Chaise à bascule','Chaise à bascule de mon arrière-grand-mère. En excellent état, parfaitement conservé d avant la première guerre.','2022-09-01 10:00:00.0','2022-09-02 11:00:00.0','5000','11','2'),
            ('Lit','Superbe sommier. Matelas non inclus. Bois chêne massif et lattes incassables. Il en à vu des pirouettes!','2022-01-01 09:00:00.0','2022-09-04 10:00:00.0','20000','11','4'),
            ('Blouson','Veste en cuir noir, il est trop beau! M en séparer me brise le cœur. Malheureusement le büch ne rentre plus...','2022-01-01 09:00:00.0',' 2022-09-02 11:00:00.0','30000','3','3'),
            ('Ordinateur Apple','Macbook en très bon état, très peu utilisé.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','5000','8','2'),
            ('Collier','Collier exceptionnel en perles de l Atlantique. Réalisé par la maison Garnier.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','200','5','2'),
            ('Vase ancien','Vase en porcelaine. Motifs variés.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','5000','4','2'), 
            ('Meuble télé','Meuble en marbre ayant appartenu à Elvis Presley. Attention meuble massif, extrêmement lourd.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','2000','11','2'), 
            ('Ancienne Voiture','Renault 21 de collection.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','10000','13','2'), 
            ('Tableau inconnu','Tableau du 17e siècle. Artiste inconnu.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','50000','4','3'), 
            ('Robe de mariage','Robe de mariage blanche avec dentelle.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','4000','3','2'), 
            ('Encyclopédie','Encyclopédie du 20e siècle.','2023-01-01 10:00:00.0','2023-09-02 11:00:00.0','100','6','2'),
            ('La Cène','Copie du tableau de la renaissance de Léonard de Vinci. Taille réelle.','2022-04-12 10:00:00.0','2023-01-01 23:59:00.0','25000','4','9'), 
            ('La naissance de Vénus','Copie conforme du célèbre tableau du 14e siècle de Sandro Botticelli. Ira parfaitement dans votre salon dans le 18e.','2022-01-01 16:00:00.0','2023-01-01 11:00:00.0','100000','4','9'), 
            ('Voiture de retour vers le future','DeLorean DMC-12 du célèbre film. La voiture est vendue dans le but d exposition, elle n est pas homologuée pour la route. Originale de 1985. Une affaire en or.','2022-12-01 01:30:00.0','2023-05-01 12:00:00.0','152000','2','9'), 
            ('Bague en or','Bague perlée trèfles, 24 carats pour femme. Moyen modèle. maison Van Cleef.','2023-01-01 01:00:00.0','2023-06-01 01:00:00.0','130000','5','2'), 
            ('Armoire en pin','Armoire ancienne avec moulure en pin. 135x60x210.','2023-01-15 09:28:00.0','2023-07-01 01:00:00.0','7000','11','2'),
            ('Barbecue charbon','Barbecue charbon de bois Weber Kettle, diamètre 57cm.','2023-01-09 07:04:00.0','2023-05-01 01:00:00.0','100','12','3'),
            ('Veste chaude en fourrure','Veste noire unicolore avec manche longue pour femme. Fourrure artificielle évidemment.','2022-10-10 18:20:00.0',' 2023-03-20 13:00:00.0','3700','3','5'),
            ('Bible en allemand','Livre manuscrit datant du 15e siècle. Pièce exceptionnelle du monastère de Leipzig.','2023-01-01 08:00:00.0','2023-09-02 02:00:00.0','2000','6','1'),
            ('Tableau carte du monde','Tableau planisphère pour faire genre en soirée.','2022-01-01 13:00:00.0','2022-03-02 13:00:00.0','5980','4','3')
            """);
            
            st.executeUpdate(
            """
            INSERT INTO enchere (de,sur,quand,montant) values
            ('10','13','2022-02-06 10:09:00.0','100500'),
            ('7','13','2022-05-02 11:10:00.0','110000'),
            ('6','13','2022-05-13 02:03:00.0','200000'),
            ('7','10','2022-02-04 09:26:00.0','26000'),
            ('8','10','2022-06-13 10:34:00.0','27000'),
            ('10','10','2022-08-23 01:09:00.0','30000'),
            ('8','10','2022-12-01 04:54:00.0','50000'),
            ('7','14','2023-01-02 07:50:00.0','500000'),
            ('8','14','2023-01-10 23:10:00.0','1000000'),
            ('4','1','2022-09-01 12:00:00.0','5500'),
            ('3','1','2022-09-01 13:00:00.0','6000'),
            ('4','1','2022-09-02 09:10:00.0','7000'),
            ('4','3','2022-09-02 11:00:00.0','30000'),
            ('3','6','2023-01-02 12:02:00.0','5500'),
            ('2','7','2023-01-02 02:35:00.0','3000'),
            ('4','9','2023-01-02 03:38:00.0','55000'),
            ('4','7','2023-01-02 04:09:00.0','2999'),
            ('4','1','2023-01-02 07:10:00.0','6000'),
            ('1','20','2022-01-02 10:11:00.0','5990'),
            ('2','20','2022-02-03 13:12:00.0','6300')
            
            """);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    
    /**
     * renvoie la liste des utilisateurs de la BDD
     * @param con Connection
     * @return List<Utilisateur>
     * @throws SQLException 
     */
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

    
    /**
     * affiche la liste des utilisateurs de la BDD dans la console
     * @param list List<Utilisateur>
     * @throws SQLException 
     */
    public static void afficheListUtilisateur(List<Utilisateur> list)throws SQLException {
        for(Utilisateur utilisateur: list){
            utilisateur.print();
        }
    }
    
    
    /**
     * renvoie la liste des catégories de la BDD
     * @param con Connection
     * @return List<Categorie>
     * @throws SQLException 
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
    
    /**
     * affiche la liste des catégories de la BDD dans la console
     * @param list List<Categorie>
     * @throws SQLException 
     */
    public static void afficheListCategorie(List<Categorie> list)throws SQLException {
        for(Categorie categorie: list){
            categorie.print();
        }
    }
    
    
    /**
     * renvoie la liste des objets de la BDD
     * @param con Connection
     * @return List<Objet>
     * @throws SQLException 
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
    
    
    /**
     * renvoie la liste des objets dont la date de fin n'est pas encore dépassée
     * @param con Connection
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> listObjetFrais(Connection con) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    
    
    /**
     * renvoie la liste des objets dont la date de fin est dépassée
     * @param con Connection
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> listObjetPerime(Connection con) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    /**
     * affiche une liste d'objets dans la console
     * @param con Connection
     * @param list List<Objet>
     * @throws SQLException 
     */
    public static void afficheListObjet(Connection con, List<Objet> list)throws SQLException {
        for(Objet objet: list){
            objet.print(con);
        }
    }
    
    
    /**
     * renvoie la liste des objets proposés par un utilisateur
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Objet>
     * @throws SQLException 
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
    
    
    /**
     * affiche toutes les enchères dans la console
     * @param con Connection
     * @throws SQLException 
     */
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
    
    
    /**
     * affiches les enchères effectuées sur un objet dans la console
     * @param con Connection
     * @param identifiant int (identifiant objet)
     * @throws SQLException 
     */
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
    

    /**
     * renvoie la liste des objets en vente surlesquels l'utilisateur a enchéri au moins une fois 
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheri(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    int proposePar = rs.getInt(8);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets qui ne sont plus en vente surlesquels l'utilisateur avait enchéri au moins une fois 
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriPerime(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    int proposePar = rs.getInt(8);
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    
    
    /**
     * renvoie la liste des objets en vente dont la description/le titre 
     * contient un mot surlesquels l'utilisateur a enchéri au moins une fois 
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheri(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select distinct on (objet.id) objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.proposepar, objet.categorie from enchere
        join objet on objet.id = enchere.sur
        where enchere.de=?
        and (objet.description like ? or objet.titre like ?)
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    int proposePar = rs.getInt(7);
                    String idcategorie = rs.getString(8);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets plus en vente dont la description/le titre 
     * contient un mot surlesquels l'utilisateur a enchéri au moins une fois 
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriPerime(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select distinct on (objet.id) objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.proposepar, objet.categorie from enchere
        join objet on objet.id = enchere.sur
        where enchere.de=?
        and (objet.description like ? or objet.titre like ?)
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    int proposePar = rs.getInt(7);
                    String idcategorie = rs.getString(8);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    /**
     * renvoie la liste d'objets en vente correspondant à une catégorie
     * surlesquels l'utilisateur a enchéri au moins une fois
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param categorie Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheri(Connection con, Utilisateur utilisateur, Categorie categorie) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie.getNom(), proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste d'objets plus en vente correspondant à une catégorie
     * surlesquels l'utilisateur a enchéri au moins une fois
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param categorie Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriPerime(Connection con, Utilisateur utilisateur, Categorie categorie) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie.getNom(), proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets en vente correspondant à une catégorie et dont 
     * le titre/description contient un mot surlesquels l'utilisateur a fait au moins une enchère
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @return
     * @throws SQLException 
     */
    public static List<Objet> objetEncheri(Connection con, Utilisateur utilisateur, String mot,Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    List<Objet> res = new ArrayList<>();
    try ( PreparedStatement st = con.prepareStatement(
    """
    select distinct on (objet.id) objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.proposepar, objet.categorie from enchere
    join objet on objet.id = enchere.sur
    where enchere.de=?
    and (objet.description like ? or objet.titre like ?) and objet.categorie=?

    """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    int proposePar = rs.getInt(7);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), proposePar));
                    }
                }
                return res;
            }
        }
    }
    
    /**
     * renvoie la liste des objets plus en vente correspondant à une catégorie et dont 
     * le titre/description contient un mot surlesquels l'utilisateur a fait au moins une enchère
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @return
     * @throws SQLException 
     */        
    public static List<Objet> objetEncheriPerime(Connection con, Utilisateur utilisateur, String mot,Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    List<Objet> res = new ArrayList<>();
    try ( PreparedStatement st = con.prepareStatement(
    """
    select distinct on (objet.id) objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.proposepar, objet.categorie from enchere
    join objet on objet.id = enchere.sur
    where enchere.de=?
    and (objet.description like ? or objet.titre like ?) and objet.categorie=?

    """)) {
        st.setInt(1,utilisateur.getId());
        st.setString(2,"%" + mot + "%");
        st.setString(3,"%" + mot + "%");
        st.setInt(4,cat.getId());
        try ( ResultSet rs = st.executeQuery()){
            while (rs.next()) {
                int id = rs.getInt(1);
                String titre = rs.getString(2);
                String description = rs.getString(3);
                Timestamp debut = rs.getTimestamp(4);
                Timestamp fin = rs.getTimestamp(5);
                int prixBase = rs.getInt(6);
                int proposePar = rs.getInt(7);
                if(fin.before(now)){
                    res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), proposePar));
                }
            }
            return res;
            }
        }
    }
    
    
    
    /**
     * renvoie la liste des objets en vente pour lesquels l'utilisateur a l'enchère la plus élevée
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param list List<Objet>
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriGagnant(Connection con, Utilisateur utilisateur, List<Objet> list) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            if(dernierEnchereSurObjet(con,objet.getId()) == dernierEnchereSurObjetDunUtilisateur(con,objet.getId(),utilisateur) && objet.getFin().after(now)){
                res.add(objet);
            }
        }
        return res;
    }
    
    
    /**
     * renvoie la liste des objets plus en vente pour lesquels l'utilisateur a l'enchère la plus élevée
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param list List<Objet>
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriGagnantPerime(Connection con, Utilisateur utilisateur, List<Objet> list) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            
            if(dernierEnchereSurObjet(con,objet.getId()) == dernierEnchereSurObjetDunUtilisateur(con,objet.getId(),utilisateur) && objet.getFin().before(now)){
                res.add(objet);
            }
        }
        return res;
    }
    

    /**
     * renvoie la liste des objets en vente pour lesquels l'utilisateur a enchéri 
     * au moins une fois mais un autre utilisateur a fait une enchère supérieure
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param list List<Objet>
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriPerdant(Connection con, Utilisateur utilisateur, List<Objet> list) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            if(dernierEnchereSurObjet(con,objet.getId()) > dernierEnchereSurObjetDunUtilisateur(con,objet.getId(),utilisateur) && objet.getFin().after(now)){
                res.add(objet);
            }
        }
        return res;
    }
    
    
    /**
     * renvoie la liste des objets plus en vente pour lesquels l'utilisateur a enchéri 
     * au moins une fois mais un autre utilisateur a fait une enchère supérieure
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param list List<Objet>
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetEncheriPerdantPerime(Connection con, Utilisateur utilisateur, List<Objet> list) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        for(Objet objet: list){
            if(dernierEnchereSurObjet(con,objet.getId()) > dernierEnchereSurObjetDunUtilisateur(con,objet.getId(),utilisateur) && objet.getFin().before(now)){
                System.out.print("&");
                res.add(objet);
            }
        }
        return res;
    }
    
    /**
     * renvoie la liste des objets correspondant à une catégorie
     * @param con Connection
     * @param categorie Categorie
     * @param list List<Objet>
     * @return
     * @throws SQLException 
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
    
    
    /**
     * renvoie la liste des objets en vente surlesquels un utilisateur n'a pas fait d'enchère
     * @param con
     * @param utilisateur
     * @return Lis<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetPasEncheri(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.id not in (select enchere.sur from enchere where enchere.de=?) and objet.proposepar!=?
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }     
                }
                return res;
            }
            
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        return res;
    }
    
    
    /**
     * renvoie la liste des objets en vente correspondant à une catégorie 
     * surlesquels un utilisateur n'a pas fait d'enchère
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetPasEncheri(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    List<Objet> res = new ArrayList<>();
    try ( PreparedStatement st = con.prepareStatement(
    """
    select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
    where objet.id not in (select enchere.sur from enchere where enchere.de=?) and objet.categorie=? and objet.proposepar!=?

        """)) {
            st.setInt(1,utilisateur.getId());
            st.setInt(2,cat.getId());
            st.setInt(3,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    

    /**
     * renvoie la liste des objets en vente dont le titre/description contient un mot
     * surlesquels un utilisateur n'a pas fait d'enchère
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetPasEncheri(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.id not in (select enchere.sur from enchere where enchere.de=?) and (objet.description like ? or objet.titre like ?) and objet.proposepar!=?
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,utilisateur.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String categorie = rs.getString(7);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets en vente correspondant à une catégorie 
     * dont le titre/description contient un mot
     * et surlesquels un utilisateur n'a pas fait d'enchère
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetPasEncheri(Connection con, Utilisateur utilisateur, String mot,Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.id not in (select enchere.sur from enchere where enchere.de=?) and (objet.description like ? or objet.titre like ?) and objet.proposepar!=? and objet.categorie=? 
        
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,utilisateur.getId());
            st.setInt(5,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVente(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur dont le titre/la description contient un mot
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVente(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? 
        and (objet.description like ? or objet.titre like ?)
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur correspondant à une catégorie
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVente(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur correspondant à une catégorie
     * et dont le titre/description contient un mot
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @returnList<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVente(Connection con, Utilisateur utilisateur, String mot, Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    List<Objet> res = new ArrayList<>();
    try ( PreparedStatement st = con.prepareStatement(
    """
    select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
    where objet.proposepar=? 
    and (objet.description like ? or objet.titre like ?) and objet.categorie=?
    """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur dont la date de fin est dépassée
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVentePerime(Connection con, Utilisateur utilisateur) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur dont la date de fin est dépassée
     * et dont le titre/la description contient un mot
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVentePerime(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? 
        and (objet.description like ? or objet.titre like ?)
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur dont la date de fin est dépassée 
     * correspondant à une catégorie 
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @returnList<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVentePerime(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur dont la date de fin est dépassée 
     * correspondant à une catégorie et dont le titre/description contient un mot
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @returnList<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsEnVentePerime(Connection con, Utilisateur utilisateur, String mot, Categorie cat) throws SQLException {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    List<Objet> res = new ArrayList<>();
    try ( PreparedStatement st = con.prepareStatement(
    """
    select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
    where objet.proposepar=? 
    and (objet.description like ? or objet.titre like ?) and objet.categorie=?
    """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List>Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsPasVendus(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * correspondant à une catégorie
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsPasVendus(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * dont le titre/la description contient un mot
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */    
    public static List<Objet> objetsPasVendus(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id not in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?)
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * dont le titre/la description contient un mot et correspondant à une catégorie
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsPasVendus(Connection con, Utilisateur utilisateur, String mot, Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id not in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?) and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * et dont la date de fin est dépassée
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List>Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsPasVendusPerime(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * correspondant à une catégorie et dont la date de fin est dépassée
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsPasVendusPerime(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * dont le titre/la description contient un mot et dont la date de fin est dépassée
     * @param con Conneciton
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */  
    public static List<Objet> objetsPasVendusPerime(Connection con, Utilisateur utilisateur, String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id not in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?)
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets mis en vente par un utilisateur surlesquels personne n'a fait d'enchère
     * dont le titre/la description contient un mot et correspondant à une catégorie
     * et dont la date de fin est dépassée
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsPasVendusPerime(Connection con, Utilisateur utilisateur, String mot, Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id not in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?) and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * rencoie la liste des objets en vente d'un utilisateur pour lesquels
     * une enchère a été faite
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendus(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    /**
     * renvoie la liste des objets en vente correspondant à une catégorie d'un utilisateur
     * pour lesquels une enchère a été faite
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendus(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets en vente dont le titre/la description contient un mot d'un utilisateur
     * pour lesquels une enchère a été faite
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendus(Connection con, Utilisateur utilisateur,String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?)
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets en vente dont le titre/la description contient un mot 
     * et correspondant à une catégorie, d'un utilisateur pour lesquels une enchère a été faite
     * @param con Connection
     * @param utilisateur utilisateur
     * @param mot String 
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendus(Connection con, Utilisateur utilisateur,String mot,Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase from objet
        where objet.proposepar=? and objet.id in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?) and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.after(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets vendus par un utilisateur
     * @param con Connection
     * @param utilisateur Utilisateur
     * @return List<Obejt>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendusPerime(Connection con, Utilisateur utilisateur) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets correspondant à une catégorie vendus par un utilisateur
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendusPerime(Connection con, Utilisateur utilisateur, Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
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
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets dont le titre/la description contient un mot vendus par un utilisateur
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendusPerime(Connection con, Utilisateur utilisateur,String mot) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase, objet.categorie from objet
        where objet.proposepar=? and objet.id in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?)
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    String idcategorie = rs.getString(7);
                    Categorie cat=Categorie.predef(Integer.parseInt(idcategorie));
                    String categorie=cat.getNom();
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, categorie, utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    /**
     * renvoie la liste des objets correspondant à une catégorie 
     * et dont le titre/la description contient un mot, vendus par un utilisateur
     * @param con Connection
     * @param utilisateur Utilisateur
     * @param mot String
     * @param cat Categorie
     * @return List<Objet>
     * @throws SQLException 
     */
    public static List<Objet> objetsVendusPerime(Connection con, Utilisateur utilisateur,String mot,Categorie cat) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Objet> res = new ArrayList<>();
        try ( PreparedStatement st = con.prepareStatement(
        """
        select objet.id, objet.titre, objet.description, objet.debut, objet.fin, objet.prixbase from objet
        where objet.proposepar=? and objet.id in (select enchere.sur from enchere) and (objet.description like ? or objet.titre like ?) and objet.categorie=?
        """)) {
            st.setInt(1,utilisateur.getId());
            st.setString(2,"%" + mot + "%");
            st.setString(3,"%" + mot + "%");
            st.setInt(4,cat.getId());
            try ( ResultSet rs = st.executeQuery()){
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String titre = rs.getString(2);
                    String description = rs.getString(3);
                    Timestamp debut = rs.getTimestamp(4);
                    Timestamp fin = rs.getTimestamp(5);
                    int prixBase = rs.getInt(6);
                    if(fin.before(now)){
                        res.add(new Objet(con,id, titre, prixBase, description, debut, fin, cat.getNom(), utilisateur.getId()));
                    }
                }
                return res;
            }
        }
    }
    
    
    
    
   
    
    /**
     * permet de rechercher un utilisateur par son nom et d'afficher son identifiant et don mdp dans la console
     * @param con Connection
     * @param nom String
     * @throws SQLException 
     */
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
    
    
    /**
     * permet d'ajouter un utilisateur à la BDD
     * @param con Connection
     * @param nom String
     * @param prenom String
     * @param email String
     * @param pass String
     * @param codePostal String
     * @return Utilisateur
     * @throws SQLException 
     */
    public static Utilisateur ajouterUtilisateur(Connection con, String nom, String prenom, String email, String pass, String codePostal) throws SQLException {
        con.setAutoCommit(false);
        nom = stringCorrecter(nom);
        prenom = stringCorrecter(prenom);
        
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
    
    
    /**
     * permet d'ajouter un utilisateur sans prénom ni code postal
     * @param con Connection
     * @param nom String
     * @param email String
     * @param pass String
     * @return Utilisateur
     * @throws SQLException 
     */
    public static Utilisateur ajouterUtilisateur(Connection con, String nom, String email, String pass, String codepostal) throws SQLException {
        return ajouterUtilisateur(con, nom, null, email, pass, codepostal);
    }

    
    /**
     * permet d'ajouter un utilisateur en demandant les informations dans la console
     * @param con Connection
     * @return Utilisateur
     * @throws SQLException 
     */
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
    

    
   /**
    * permet d'ajouter une catégorie
    * @param con Connection
    * @param nom String
    * @throws SQLException 
    */
   public static void ajouterCategorie(Connection con, String nom) throws SQLException {
       con.setAutoCommit(false);
       try ( PreparedStatement pst = con.prepareStatement(
               """
               insert into categorie (nom) values (?)
               """)) {

           pst.setString(1, nom);
           pst.executeUpdate();
           con.commit();
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }
   
   
   /**
    * permet d'ajouter une catégorie depuis la console
    * @param con Connection
    * @throws SQLException 
    */
   public static void ajouterCategorie(Connection con) throws SQLException {
       // lors de la creation du PreparedStatement, il faut que je précise
       // que je veux qu'il conserve les clés générées
        String nom = Console.entreeString("nom : ");
        ajouterCategorie(con,nom);
    }
   
   
   /**
    * permet de demander une date depuis la console
    * @param con Connection
    * @return String
    * @throws SQLException 
    */
    public static String demanderDate (Connection con) throws SQLException{
        String jour =Console.entreeString("Jour de fin de vente sour la forme annee-mois-jour:");
        String heure=Console.entreeString("Heure de fin de vente sour la forme heure:minute:seconde");
        String date =jour+' '+heure;
        return date;
   }
   
    
   /**
    * permet de choisir une catégorie parmi la liste depuis la console
    * @param con Connection
    * @return int (identifiant catégorie)
    * @throws SQLException 
    */
   public static int choisirCategorie(Connection con) throws SQLException{
       afficheListCategorie(listCategorie(con));
       return Console.entreeEntier("Entrez l'identifiant de la categorie de votre objet");
   }
   
   
   /**
    * permet de choisir un objet parmi la liste depuis la console
    * @param con Conneciton
    * @return int (identifiant objet)
    * @throws SQLException 
    */
   public static int choisirObjet(Connection con) throws SQLException{
       afficheListObjet(con,listObjet(con));
       return Console.entreeEntier("Entrez l'identifiant de l'objet de votre choix");
   }
   
   /**
    * corrige un text si nécessaire pour ne pas entrainer d'erreur
    * @param txt String
    * @return String
    */
   public static String stringCorrecter(String str){
       char restab[] = new char [str.length()];
        if(str.contains("'")){
            for(int i = 0; i < str.length(); i++){
                if(str.charAt(i) == 39){
                    restab[i]=32;
                }else{
                    restab[i]=str.charAt(i);
                }
            }
            return new String(restab);
        }else{
            return str;
        }
   }
   
   /**
    * permet d'ajouter un objet
    * @param con Connection
    * @param titre String
    * @param description String
    * @param debut Timestamp
    * @param fin Timestamp
    * @param prixbase int
    * @param categorie int (identifiant categorie)
    * @param proposepar in (identifiant utilisateur)
    * @throws SQLException 
    */
    public static void ajouterObjet(Connection con, String titre, String description, Timestamp debut, Timestamp fin, int prixbase, int categorie, int proposepar) throws SQLException {
       con.setAutoCommit(false);
       titre = stringCorrecter(titre);
       description = stringCorrecter(description);
       
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
           con.commit();
           } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }

    }
   
    
    /**
     * permet d'ajouter un objet depuis la console
     * @param con Connection
     * @param utilisateur Utilisateur
     * @throws SQLException 
     */
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
    /**
     * renvoie le montant maximal des enchères sur un objet 
     * ou son prix de base si aucune enchère n'a été faite
     * @param con Connection
     * @param sur int (identifiant objet)
     * @return int (montant)
     * @throws SQLException 
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
    
    
    /**
     * renvoie le montant maximal des enchères faites par un utilisateur sur un objet 
     * ou son prix de base si aucune enchère n'a été faite
     * @param con Connection
     * @param sur int (identifiant objet)
     * @param utilisateur Utilisateur
     * @return int (montant)
     * @throws SQLException 
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
    
    
    /**
     * permet d'ajouter une enchère
     * @param con Connection
     * @param de int (identifiant utilisateur)
     * @param sur int (identifiant objet)
     * @param quand Timestamp
     * @param montant int 
     * @throws SQLException 
     */
    public static void ajouterEnchere(Connection con, int de, int sur, Timestamp quand, int montant) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        con.setAutoCommit(false);
        try ( PreparedStatement st = con.prepareStatement(
                "select fin from objet "
                + "where objet.id = ?")) {
            st.setInt(1, sur);

            try ( ResultSet rs = st.executeQuery()) {
                rs.next();
                Timestamp fin = rs.getTimestamp("fin");
                
                if(fin.after(now)){
                    
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
                            con.commit();
                        }
                }else{
                    con.rollback();
                    JavaFXUtils.showErrorInAlert("Montant inférieur à l'enchère précédente");
                    throw new Error("Montant inférieur à l'enchère précédente");
                }
                    
                }else{
                    JavaFXUtils.showErrorInAlert("Date depassée");
                    throw new Error("Date depassée");
                }               
            }
        }finally{
            con.setAutoCommit(true);
        }
    }
   
    
    /**
     * permet d'ajouter une enchère depuis la console
     * @param con Connection
     * @param utilisateur Utilisateur
     * @throws SQLException 
     */
    public static void ajouterEnchere(Connection con, Utilisateur utilisateur) throws SQLException {
        
        int de=utilisateur.getId();
        int sur=choisirObjet(con);
        afficheEnchereObjet(con,sur);
        Long datetime = System.currentTimeMillis();
        Timestamp quand = new Timestamp(datetime);
        int montant = Console.entreeEntier("montant de votre enchère: ");
        ajouterEnchere(con,de,sur,quand,montant);
    }
    
    
    /**
     * permet d'ajouter une enchère en fixant debut au moment présent
     * @param con Conenction
     * @param utilisateur Utilisateur
     * @param montant int
     * @param sur int (identifiant objet)
     * @throws SQLException 
     */
    public static void ajouterEnchere(Connection con, Utilisateur utilisateur, int montant,int sur) throws SQLException {       
        int de=utilisateur.getId();
        Long datetime = System.currentTimeMillis();
        Timestamp quand = new Timestamp(datetime);
        ajouterEnchere(con,de,sur,quand,montant);
    }
    
    /**
     * permet à un utilisateur de se connecter
     * @param con Connection
     * @param email String
     * @param pass String
     * @return Utilisateur
     * @throws SQLException 
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
                    return Optional.of(new Utilisateur(res.getInt("id"), res.getString("nom"), res.getString("prenom"), email, pass, res.getString("codepostal")));
                } else {
                    return Optional.empty();
                }
            }
        }
    }
   
    
    /**
     * permet à un utilisateur de se connecter depuis la console
     * @param con Connection
     * @return Utilisateur
     * @throws SQLException 
     */
    public static Optional<Utilisateur> connexionUtilisateur(Connection con) throws SQLException {
       String email = Console.entreeString("email : ");
       String pass = Console.entreeString("pass : ");
       return connexionUtilisateur(con, email, pass);
    }

    
   /**
    * affiche le bilan des enchères d'un utilisateur dans la console
    * @param con Connection
    * @param utilisateur Utilisateur
    * @throws SQLException 
    */
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
   
    
    /**
     * menu textuel permettant de simuler le site de ventes aux enchères dans la console
     * @param con Connection
     * @throws SQLException 
     */
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
    
    
    /**
     * menu textuel permettant de simuler le site de ventes aux enchères dans la console
     * avec option administrateur
     * @param con Connection
     * @throws SQLException 
     */
    public static void menuV2(Connection con) throws SQLException {
        int rep = 0;
        Utilisateur utilActif = new Utilisateur();
        while (true) {
            System.out.println("__________MENU_BASE__________");
            System.out.println("[1] Se connecter");
            System.out.println("[2] Créer un nouveau compte");
            System.out.println("[0] Quitter");
            
            rep = Console.entreeEntier("Votre choix : ");
            
            if (rep == 1){ // Se connecter
                utilActif = connexionUtilisateur(con).get();
            } else if (rep == 2){ // Créer un nouveau compte
                utilActif = ajouterUtilisateur(con);
            } else { // Quitter
                break;
            }
            rep = -1;
            if (utilActif.getId()==0){ // menu admin
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
                    //afficheListObjet(con,objetsEnVente(con,utilActif,"lit"));
                    afficheListObjet(con,objetEncheri(con,utilActif,"e"));
                    
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
            //afficheListCategorie(listCategorie(con));
            //afficheListObjet(con,listObjet(con));
            menuV2(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
