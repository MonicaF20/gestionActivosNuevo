/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    EntityManagerFactory emf=Persistence.createEntityManagerFactory("SolicitarBajaDocente");
         EntityManager em=emf.createEntityManager();
    /**
     * Initializes the controller class.
     */
     @FXML
        public void solicitarBaja(ActionEvent event) throws IOException {
         LocalDate localDate=datePickerSolicitudBaja.getValue();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //datePickerSolicitudBaja=ConvertidorFecha.ConvertidorFecha(datePickerSolicitudBaja);
       
//        datePickerSolicitudBaja.valueProperty().addListener(new ChangeListener<LocalDate>(){
//
//           @Override
//           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
//              
//           }
//       });
    }//fin initialize    
    
    
}
