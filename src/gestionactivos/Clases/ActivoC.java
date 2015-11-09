/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos.Clases;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Marce
 */
public class ActivoC {
    
    public SimpleStringProperty ubicacion = new SimpleStringProperty();
    public SimpleStringProperty rubro = new SimpleStringProperty();
    public SimpleStringProperty codigo = new SimpleStringProperty();
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleStringProperty estado = new SimpleStringProperty();
    public SimpleStringProperty fechaIngreso = new SimpleStringProperty();
    
    
    public String getUbicacion (){
        return ubicacion.get();
    }
    
    public String getRubro(){
        return rubro.get();
    }
    
    public String getCodigo(){
        return codigo.get();
    }
    public String getNombre(){
        return nombre.get();
    }
    
    public String getEstado(){
        return estado.get();
    }
    
    public String getFechaIngreso(){
        return fechaIngreso.get();
    }

       
    
}
