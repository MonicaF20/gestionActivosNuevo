/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MariaJose
 */
public class LateralDerechoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lblUserLogeado;
    @FXML 
    private Label lblCerrarSesion; 
    
    GestionActivos ga;
    InicioController ic;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     //System.out.println(InicioController.logeado);
        lblUserLogeado.setText(InicioController.logeado);
    }    
    
    public void cerrarSesion() throws IOException, Exception {  
        
    //redirigiendo a la ventana principal 
        AnchorPane loader= null;
        Scene newScene= GestionActivos.scene;
       Stage mainWindow=GestionActivos.primaryStage;
        mainWindow.setScene(newScene);
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/logeo.fxml"));
        
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   InicioController.logeado="";
   mainWindow.setTitle("Sistema para la Gestion de Activos Fijos");
//   primaryStage.setTitle("Reporte Baja de Activos");

    
    }
    
}
