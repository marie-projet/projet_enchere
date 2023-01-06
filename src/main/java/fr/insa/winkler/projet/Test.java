package fr.insa.winkler.projet;
import java.sql.Timestamp;
/**
 *
 * @author marie
 */
public class Test {
    
    public static void main(String[] args) {
        System.out.println("coucou");
        
        Timestamp tp = new Timestamp(System.currentTimeMillis());
        System.out.println(tp);
    }
    
}
