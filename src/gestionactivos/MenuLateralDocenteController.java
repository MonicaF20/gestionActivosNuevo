/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.GestionActivos.primaryStage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MariaJose
 */
public class MenuLateralDocenteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button solicitudesPeticion;
    @FXML
    private Button reporteReparacion;
    
     @FXML
    private Accordion acordion;

    @FXML
    private Button solicitudesReparacion;

    @FXML
    private TitledPane baja;

    @FXML
    private TitledPane reparacion;

    @FXML
    private Button reporteBaja;

    @FXML
    private TitledPane peticiones;

    @FXML
    private Button reportePeticiones;

    @FXML
    private Button solicitudesBaja;
    @FXML Label lblRegresar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        solicitudesPeticion.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(solicitudesPeticion);
           
       }
   });
           solicitudesBaja.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(solicitudesBaja);
           
       }
   });
             solicitudesReparacion.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(solicitudesReparacion);
           
       }
   });
  reporteBaja.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(reporteBaja);
           
       }
   });
  
  
  reportePeticiones.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(reportePeticiones);
           
       }
   });
  
  
  lblRegresar.setOnMouseClicked(new EventHandler(){

            @Override
            public void handle(Event event) {
                try {
                    regresarMenu();
                } catch (IOException ex) {
                    Logger.getLogger(MenuLateralDocenteController.class.getName()).log(Level.SEVERE, null, ex);
                }
 }
        });
  
  reporteReparacion.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(reporteReparacion);
           
       }
   });
  
    }    

    public void itemSelected(Button selected) {
       AnchorPane loader= null;
       AnchorPane loader2=null;
        AnchorPane loader3=null;
Scene newScene; //haciendo referencia al scene de la pagina principal
newScene = GestionActivos.scene;

Stage mainWindow; //Haciendo referencia al primaryStage
mainWindow = GestionActivos.primaryStage;

mainWindow.setScene(newScene);     
//cargando el menulateral

try{
loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateraldocente.fxml"));
loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
}catch(Exception e){}
System.out.println("Seleccion: "+selected.getText());
switch(selected.getText())
{
    case "Generar Solicitud de Reparacion":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/RepararActivo.fxml"));
             
             primaryStage.setTitle("Solicitud de Reparar Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
    case "Generar Solicitud de Peticion":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/SolicitarActivo.fxml"));
             
             primaryStage.setTitle("Solicitud de Peticion de Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
        case "Generar Solicitud de baja":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/SolicitarBaja.fxml"));
             
             primaryStage.setTitle("Solicitud de Baja de Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
        case "Estado de Solicitudes de Peticion":
             try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/estadoSolPeticion.fxml"));
             
             primaryStage.setTitle("Solicitud de Baja de Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
            case "Estado de Solcitudes Reparacion":
             try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/estadoSolReparacion.fxml"));
             
             primaryStage.setTitle("Solicitud de Baja de Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
        case "Estado de Solicitudes de Baja":
             try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/estadoSolBaja.fxml"));
             
             primaryStage.setTitle("Solicitud de Baja de Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
}

   GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
        

   
}
    public void regresarMenu() throws IOException{
   AnchorPane loader= null;
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menudocente.fxml"));
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   
    }
    }
    

