/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.winkler.gui.vues;

import javafx.scene.control.Label;

/**
 *
 * @author francois
 */
public class BigLabel extends Label{
    
    public BigLabel(String txt,int fontSize) {
        super(txt);
        this.setStyle("-fx-font-size: "+fontSize);
        
    }
    
}
