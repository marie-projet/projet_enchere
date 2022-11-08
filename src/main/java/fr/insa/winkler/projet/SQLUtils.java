
package fr.insa.winkler.projet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;

/**
 *
 * @author francois
 */
public class SQLUtils {

    public static class ResultSetAsList {

        private final List<String> entetes;
        private final List<List<Object>> valeurs;

        public ResultSetAsList(List<String> entetes, List<List<Object>> valeurs) {
            this.entetes = entetes;
            this.valeurs = valeurs;
        }

    }

    public static ResultSetAsList asLists(Connection con, PreparedStatement pst) throws SQLException {
        try ( ResultSet rs = pst.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            List<String> entetes = new ArrayList<>(meta.getColumnCount());
            for (int c = 1; c <= meta.getColumnCount(); c++) {
                entetes.add(meta.getColumnName(c));
            }
            List<List<Object>> datas = new ArrayList<>(meta.getColumnCount());
            while (rs.next()) {
                List<Object> ligne = new ArrayList<>();
                for (int c = 1; c <= meta.getColumnCount(); c++) {
                    ligne.add(rs.getObject(c));
                }
                datas.add(ligne);
            }
            return new ResultSetAsList(entetes, datas);
        }
    }
    
        /**
     * retourne une représentation d'un 'ResultSet' sous forme d'une table HTML.
     * repris de fr.insa.beuvron.cours.m3.database.ResultSetUtils.
     * <p>
     * remarquez que la table affichée est assez basique mais que la méthode est
     * trés générale : elle peut afficher n'importe quel ResultSet.
     * </p>
     *
     * @param rs le ResultSet
     * @return 
     * @throws java.lang.Exception 
     */
    public static String formatResultSetAsHTMLTable(ResultSet rs) throws SQLException {
        StringBuilder res = new StringBuilder();
        // il est également possible d'avoir des information sur la 'structure'
        // du Resultset : nombre de colonnes, nom des colonnes ...
        // toutes ces informations sont contenues dans le 'metadata' associé
        // au ResultSet
        String headerStyle = "STYLE=\"border-top: 3px solid #000000;"
                + " border-bottom: 3px solid #000000;"
                + " border-left: 1px solid #000000;"
                + " border-right: 1px solid #000000\""
                + " ALIGN=LEFT";
        String normalStyle = "STYLE=\"border-bottom: 1px solid #000000;"
                + " border-left: 1px solid #000000;"
                + " border-right: 1px solid #000000\""
                + "ALIGN=LEFT";
        ResultSetMetaData metadata = rs.getMetaData();
        int nombreColonnes = metadata.getColumnCount();
        res.append("<TABLE CELLSPACING=0 COLS=" + nombreColonnes + " BORDER=1>\n");
        res.append("<TBODY>\n");
        res.append("<TR>\n");
        for (int i = 1; i <= nombreColonnes; i++) {
            res.append("  <TD " + headerStyle + ">" + metadata.getColumnName(i) + "</TD>\n");
        }
        res.append("</TR>\n");
        while (rs.next()) {
            res.append("<TR>\n");
            for (int i = 1; i <= nombreColonnes; i++) {
                res.append("  <TD " + normalStyle + ">"
                        + StringEscapeUtils.escapeHtml4("" + rs.getObject(i)) + "</TD>\n");
            }
            res.append("</TR>\n");
        }
        res.append("</TBODY>\n");
        res.append("</TABLE>\n");
        return res.toString();
    }



}
