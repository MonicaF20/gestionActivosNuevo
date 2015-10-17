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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MariaJose
 */
public class MenuLateralController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button ingreso;
    @FXML
    private Button reporteIngreso;
    @FXML Button modificarRubro;
    @FXML Button agregarRubro;
    @FXML Label lblRegresar;
    @FXML Button solicitudesPeticion;
    @FXML Button reportePeticiones;
    @FXML Button solicitudesReparacion;
    @FXML Button reporteReparacion;
    @FXML  Button solicitudesBaja;
    @FXML Button reporteBaja;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ingreso.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(ingreso);
           
       }
   });
  reporteIngreso.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           itemSelected(reporteIngreso);
           
       }
   });
  modificarRubro.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(modificarRubro);
            }
        });
  agregarRubro.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(agregarRubro);
            }
        });
 lblRegresar.setOnMouseClicked(new EventHandler(){

            @Override
            public void handle(Event event) {
                try {
                    regresarMenu();
                } catch (IOException ex) {
                    Logger.getLogger(MenuLateralController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    solicitudesPeticion.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(solicitudesPeticion);
            }
        });
    
    reportePeticiones.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(reportePeticiones);
            }
        });
    solicitudesReparacion.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(solicitudesReparacion);
            }
        });
    reporteReparacion.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(reporteReparacion);
            }
        });
     solicitudesBaja.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(solicitudesBaja);
            }
        });
     reporteBaja.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                itemSelected(reporteBaja);
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
loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
}catch(Exception e){}
switch(selected.getText())
{
    case "Ingresar Nuevo Activo":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/FXMLDocument.fxml"));
             
             primaryStage.setTitle("Ingreso de un Nuevo Activo");
             
            }catch(Exception e){System.out.print(e);}
        break;
     case "Generar Reporte:Ingresos":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteIngreso.fxml"));
             primaryStage.setTitle("Reportes de los Activos Ingresados");
             
            }catch(Exception e){System.out.print(e);}
        break;
     case "Modificar Rubro":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/modificarRubro.fxml"));
             primaryStage.setTitle("Modificar Rubro Existente");
             
            }catch(Exception e){System.out.print(e);}
        break; 
     case "Agregar Nuevo Rubro":
         try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/ingresarRubro.fxml"));
             primaryStage.setTitle("Ingresar Un Nuevo Rubro");
             
            }catch(Exception e){System.out.print(e);}
        break; 
     case "Ver Solicitudes de Peticion":
     try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesAsignacionAdmin.fxml"));
             primaryStage.setTitle("Ver Solicitudes de Petición");
             
            }catch(Exception e){System.out.print(e);}
        break;
     case "Generar Reporte : Peticiones":
          try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportePeticionAdmin.fxml"));
             primaryStage.setTitle("Reporte de Solicitudes de Petición de Activos");
             
            }catch(Exception e){System.out.print(e);}
        break;
    case "Ver Solcitudes Reparacion":
          try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesReparacionAdmin.fxml"));
             primaryStage.setTitle("Solicitudes de Reparación");
            }catch(Exception e){System.out.print(e);}
        break;
        case "Generar Reporte: Reparacion":
          try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesReparacionAdmin.fxml"));
             primaryStage.setTitle("Reportes de Reparacion Reparacion");
            }catch(Exception e){System.out.print(e);}
        break;
        case "Ver Solicitudes de Baja":
          try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesBajaAdmin.fxml"));
             primaryStage.setTitle("Solicitudes de Baja de Activo");
            }catch(Exception e){System.out.print(e);}
        break;
         case "Generar Reporte: Baja":
          try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesBajaAdmin.fxml"));
             primaryStage.setTitle("Reportes de Baja de Activo");
            }catch(Exception e){System.out.print(e);}
        break;
         
}

   GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3);
        

   
}
    public void regresarMenu() throws IOException{
   AnchorPane loader= null;
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuAdmin.fxml"));
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   
    }
}

