/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

/**
 *
 * @author Valery
 */
public class ConvertidorFecha {
  public static DatePicker ConvertidorFecha(DatePicker dtPicker){
    String pattern="dd-MM-yyyy"; 
    dtPicker.setValue(LocalDate.now());
    dtPicker.setPromptText(pattern.toLowerCase());
   dtPicker.setConverter(new StringConverter<LocalDate>()
    {
 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
          @Override
          public String toString(LocalDate object) {
              if(object != null)
              {
                  return dateFormatter.format(object);
              }else
              {
                  return "";
              }
          }

          @Override
          public LocalDate fromString(String string) {

          if (string != null && !string.isEmpty()) {
             return LocalDate.parse(string, dateFormatter);
         } else {
             return null;
         }
     
          
          }
      });
   
     return dtPicker;
        
    }    
    }
    

