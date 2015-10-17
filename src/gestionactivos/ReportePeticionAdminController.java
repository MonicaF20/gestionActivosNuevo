/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class ReportePeticionAdminController implements Initializable {
     @FXML private ComboBox<String> cmbOpcReporte;
 private ObservableList<String> opciones= FXCollections.observableArrayList("Reporte Mensual",
                                                                                "Reporte Anual",
                                                                                "Por Rubro", "Por Activo"); 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbOpcReporte.setItems(opciones);
    
    }    
    
}
