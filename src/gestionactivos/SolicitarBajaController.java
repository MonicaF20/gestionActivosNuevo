/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class SolicitarBajaController implements Initializable {
    @FXML
    private TextField txtNombreActivo;
    @FXML
    private TextField txtCodigoActivo;
    @FXML
    private TextArea txtDetalleDanio;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEnviar; 
    @FXML
    private DatePicker datePickerSolicitudBaja;
    Date date= new Date();
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Calendar fecha=Calendar.getInstance();
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       datePickerSolicitudBaja=ConvertidorFecha.ConvertidorFecha(datePickerSolicitudBaja);
       
        datePickerSolicitudBaja.valueProperty().addListener(new ChangeListener<LocalDate>(){

           @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
              
           }
       });

        

    }//fin initialize    
    
    
}
