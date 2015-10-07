/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;
import static gestionactivos.GestionActivos.primaryStage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class MenulateralDirectoraController implements Initializable {
     @FXML private Button actingresado;
     @FXML private Button actreparacion;
     @FXML private Button actbaja;
     @FXML private Button solpeticion;
     @FXML private Button solreparacion;
     @FXML private Button solbaja;
     @FXML private Button replanificacion;
     @FXML private Button verplanificacion;
     
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actingresado.setOnMouseClicked(new EventHandler() {
        @Override
        public void handle(Event event) {
           itemSelected(actingresado);
           
       }
        
        });
        
        actbaja.setOnMouseClicked(new EventHandler() {
        @Override
        public void handle(Event event) {
           itemSelected(actbaja);
           
       }
        
        });
        
        //Marcela agrego esto 
        replanificacion.setOnMouseClicked(new EventHandler() {
        @Override
        public void handle(Event event) {
           itemSelected(replanificacion);
           
       }
        
        });
        
        verplanificacion.setOnMouseClicked(new EventHandler() {
        @Override
        public void handle(Event event) {
           itemSelected(verplanificacion);
           
       }
        
        });
      
      
    }
    
    public void itemSelected(Button seleccion){
    AnchorPane loader=null;
    AnchorPane loader2=null;
    AnchorPane loader3=null;
    
    Scene newScene; //referencia al scene de la pagina princiapal
    newScene= GestionActivos.scene;
    //referencia al primaryStage
    Stage mainWindow=GestionActivos.primaryStage;
    
    mainWindow.setScene(newScene);
    //para cargar los menus Laterales 
        try {
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
        }
        String prueba=seleccion.getText();
        System.out.println("Esto captura: "+prueba); 
        switch(seleccion.getText()){
               
            case "Activos Ingresados":
                try{
                loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteIngreso.fxml"));
                primaryStage.setTitle("Reporte de Ingreso de Activos");
                }catch(Exception e) {}
            break;
            case "Activos de Baja":
                try{
                loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteBaja.fxml"));
                primaryStage.setTitle("Reporte de Baja de Activos");
                }catch(Exception e) {}
            break;
                
            //Vista de Marce
            case "Realizar Planificación":
                try{
                loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/PlanificacionInicio.fxml"));
                primaryStage.setTitle("Realizar Planificacion Inicio de Año");
                }catch(Exception e) {}
            break;
                
            case "Ver Planificación":
                try{
                loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/ActivosInicio.fxml"));
                primaryStage.setTitle("Planificacion Inicio de Año");
                }catch(Exception e) {}
            break;
        
        }//fin switch
  GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    
    
    }
    
}
