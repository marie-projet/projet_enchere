package fr.insa.winkler.projet;

import fr.insa.winkler.utils.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author marie
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
            ('Meuble'),
            ('Habit')
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
                      
    /**
    public static void afficheTousLesUtilisateurs(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            try ( ResultSet rs = st.executeQuery(
                    "select id,nom,pass from utilisateur")) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String nom = rs.getString(2);
                    String motDePasse = rs.getString("pass");
                    System.out.println(id + " : " + nom + "(" + motDePasse
                            + ")");
                }
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
                    System.out.println(id + " : " + nom + "(" + motDePasse
                            + ")");
                }
            }
        }
    }

    // lors de la création d'un utilisateur, l'identificateur est automatiquement
    // créé par le SGBD.
    // on va souvent avoir besoin de cet identificateur dans le programme,
    // par exemple pour gérer des liens "aime" entre utilisateur
    // vous trouverez ci-dessous la façon de récupérer les identificateurs
    // créés : ils se présentent comme un ResultSet particulier.
    public static int createUtilisateur(Connection con,
            String nom, String pass)
            throws SQLException {
        // lors de la creation du PreparedStatement, il faut que je précise
        // que je veux qu'il conserve les clés générées
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into utilisateur (nom,pass) values (?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, nom);
            pst.setString(2, pass);
            pst.executeUpdate();

            // je peux alors récupérer les clés créées comme un result set :
            try ( ResultSet rid = pst.getGeneratedKeys()) {
                // et comme ici je suis sur qu'il y a une et une seule clé, je
                // fait un simple next 
                rid.next();
                // puis je récupère la valeur de la clé créé qui est dans la
                // première colonne du ResultSet
                int id = rid.getInt(1);
                return id;
            }
        }
    }

    public static void menu(Connection con) {
        int rep = -1;
        while (rep != 0) {
            System.out.println("Menu BdD Aime");
            System.out.println("=============");
            System.out.println("1) créer/recréer la BdD initiale");
            System.out.println("2) liste des utilisateurs");
            System.out.println("3) cherche par nom");
            System.out.println("4) ajouter un utilisateur");
            System.out.println("5) ajouter un lien 'Aime'");
            System.out.println("6) ajouter n utilisateurs aléatoires");
            System.out.println("0) quitter");
            rep = Console.entreeEntier("Votre choix : ");
            try {
                if (rep == 1) {
                    recreeTout(con);

                } else if (rep == 2) {
                    afficheTousLesUtilisateurs(con);

                } else if (rep == 3) {
                    String cherche = Console.entreeString("nom cherché :");
                    trouveParNom(con, cherche);
                } else if (rep == 4) {
                    String n = Console.entreeString("nom : ");
                    String p = Console.entreeString("pass : ");
                    int id = createUtilisateur(con, n, p);
                    System.out.println("utilisateur " + id + " créé");
                }
//else if (rep == 5) {
//                    demandeNouvelAime(con);
//                } else if (rep == 6) {
//                    System.out.println("création d'utilisateurs 'aléatoires'");
//                    int combien = Console.entreeEntier("combien d'utilisateur : ");
//                    for (int i = 0; i < combien; i++) {
//                        boolean exist = true;
//                        while (exist) {
//                            String nom = "U" + ((int) (Math.random() * 10000));
//                            if (!nomUtilisateurExiste(con, nom)) {
//                                exist = false;
//                                createUtilisateur(con, nom, "P" + ((int) (Math.random() * 10000)));
//                            }
//                        }
//
//                    }
//                }
            } catch (SQLException ex) {
                throw new Error(ex);
            }
        }
        
    }
    //___________________________________new_user___________________________________

   public static int newUser(Connection con, String nom, String email, String pass, String codepostal) throws SQLException {
       return newUser(con, nom, "", email, pass, codepostal);
   }

   public static int newUser(Connection con, String nom, String prenom, String email, String pass, String codepostal) throws SQLException {
       // lors de la creation du PreparedStatement, il faut que je précise
       // que je veux qu'il conserve les clés générées
       try ( PreparedStatement pst = con.prepareStatement(
               """
               insert into utilisateur (nom,prenom,email,pass,codepostal) values (?,?,?,?,?)
               """, PreparedStatement.RETURN_GENERATED_KEYS)) {

           pst.setString(1, nom);
           pst.setString(2, prenom);
           pst.setString(3, email);
           pst.setString(4, pass);
           pst.setString(5, codepostal);

           pst.executeUpdate();

           // je peux alors récupérer les clés créées comme un result set :
           try ( ResultSet rid = pst.getGeneratedKeys()) {
               // et comme ici je suis sur qu'il y a une et une seule clé, je
               // fait un simple next 
               rid.next();
               // puis je récupère la valeur de la clé créé qui est dans la
               // première colonne du ResultSet
               int id = rid.getInt(1);
               return id;
           }
       }
   }
   */
    public static void main(String[] args) {
        try ( Connection con = defautConnect()) {
            System.out.println("connecté !!!");
            //menu(con);
            //creeSchema(con);
            //deleteSchema(con);
            recreeTout(con);
            creeExemple(con);
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

}
