/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Marce
 */
public class ActivosInicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button btn_Buscar;

    @FXML
    private ComboBox<?> cmb_Ubicacion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
