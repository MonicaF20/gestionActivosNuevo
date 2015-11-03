/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Marce
 */
public class SolicitarActivoController implements Initializable {

    @FXML
    private TextArea descripcion;

    @FXML
    private ComboBox<String> rubroSolicDoc;

    @FXML
    private DatePicker fecha;

    @FXML
    private Label codigo;

    @FXML
    private ComboBox<String> ubicacionSolicDoc;

    @FXML
    private Label codigoLabel;

    @FXML
    private Button ingresarBoton;

    @FXML
    private Button cancelarBoton;

    @FXML
    private TextField nombre;


    
    BDConexion db= BDConexion.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //LLENADO DE COMBOBOX DE RUBROS
        ObservableList<String> list2 = FXCollections.observableArrayList();
   list2=db.getRubros();
    
   rubroSolicDoc.setItems(list2);
   
   
   
   //LLENADO DE COMBOBOX DE UBICACION
   
   ObservableList<String> list3 = FXCollections.observableArrayList();
   list3=db.getUbicacion();
    
   ubicacionSolicDoc.setItems(list3);
    }    
    
}
