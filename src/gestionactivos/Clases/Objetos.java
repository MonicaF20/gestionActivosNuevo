/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos.Clases;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Marce
 */
public class Objetos {
    
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleIntegerProperty cantidad = new SimpleIntegerProperty();
    
    public String getNombre(){
        return nombre.get();
    }
    
    public Integer getCantidad(){
        return cantidad.get();
    }
    
}
