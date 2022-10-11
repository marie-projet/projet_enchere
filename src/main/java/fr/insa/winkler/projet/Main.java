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

import java.sql.Connection;
import fr.insa.winkler.utils.Console;
/**
*
* @author Théo et Marie
*/
public class Main {

   public static void register() {
       try ( Connection con = BdD.defautConnect()) {
           System.out.println("connecté !!!");

           String nom = Console.entreeString("nom : ");
           String email = Console.entreeString("email : ");
           String pass = Console.entreeString("password : ");
           String passTest = Console.entreeString("verify password : ");

           while(!pass.equals(passTest)){
               System.out.println("Your first and second password are not the same.");
               pass = Console.entreeString("password : ");
               passTest = Console.entreeString("verify password : ");
           }

           String codepostal = Console.entreeString("codepostal : ");

           int id = BdD.newUser(con, nom, email, pass, codepostal);

       } catch (Exception ex) {
           throw new Error(ex);
       }   
   }

   public static void main(String[] args) {
       System.out.println("Hello World!");
       register();

   }
}


