/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.DirectoraController.respue;
import static gestionactivos.GestionActivos.primaryStage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AdminController extends GestionActivos implements Initializable{

       GestionActivos Principal;
      @FXML private ImageView ingresoActivo;
      @FXML private ImageView solicitarActivo;
      @FXML private ImageView reparacionActivo;
      @FXML private ImageView bajaActivo;  
      @FXML private ImageView reportes;
      @FXML private ImageView mantenimientoRubros;
      @FXML private  Label lblSolicitudes;
    List lista= new ArrayList<>();
    String imagenSeleccionada="";
    
    private ScheduledService<Void> backgroundTH;
    Boolean continuar=true;
    BDConexion bd= BDConexion.getInstance();
    final String estado="ACEPTADA";
    static String respue="";
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Iniciando el hilo 
        hilo();
          ContextMenu context = new ContextMenu();
   ingresoActivo.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           capturarClick(ingresoActivo);
           
       }
   });
 
   solicitarActivo.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           capturarClick(solicitarActivo);
           
       }
   });
   
      reparacionActivo.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           capturarClick(reparacionActivo);
           
       }
   });
       bajaActivo.setOnMouseClicked(new EventHandler() {

       @Override
       public void handle(Event event) {
           capturarClick(bajaActivo);
           
       }
   });
     
     
 final ContextMenu cm= new ContextMenu();
       MenuItem item1= new MenuItem("Modificar Rubro");
       MenuItem item2= new MenuItem("Agregar Nuevo Rubro");
       MenuItem item6= new MenuItem("Agregar Ubicación");
       MenuItem item7= new MenuItem("Control de Inventario");
       cm.getItems().addAll(item1,item2,item6,item7);
     
       mantenimientoRubros.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.PRIMARY)  
                cm.show(mantenimientoRubros,e.getScreenX(),e.getScreenY());
        }
});
       item1.setOnAction(new EventHandler() {

              @Override
              public void handle(Event event) {
                  imagenSeleccionada="modificarRubro";
                  llamarInterfaces();
              }
          });
       item2.setOnAction(new EventHandler() {

              @Override
              public void handle(Event event) {
              imagenSeleccionada="ingresarRubro";
              llamarInterfaces();
              }
          });
       item6.setOnAction(new EventHandler(){

              @Override
              public void handle(Event event) {
                imagenSeleccionada="agregarUbicación";
                  llamarInterfaces();              }
          });
        item7.setOnAction(new EventHandler(){

              @Override
              public void handle(Event event) {
                imagenSeleccionada="controlInventario";
                  llamarInterfaces();              }
          });
final ContextMenu cm2= new ContextMenu();
       MenuItem item3= new MenuItem("Reporte de Activos Ingresados");
       MenuItem item4= new MenuItem("Reporte de Activos en Reparacion");
       MenuItem item5= new MenuItem("Reporte de Activos dados de Baja");
       cm2.getItems().addAll(item3,item4,item5);
         
       reportes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.PRIMARY)  
                cm2.show(reportes,e.getScreenX(),e.getScreenY());
        }
});
       item3.setOnAction(new EventHandler(){

              @Override
              public void handle(Event event) {
                imagenSeleccionada="reporteIngreso";
                  llamarInterfaces();              }
          });
       
       item4.setOnAction(new EventHandler(){

              @Override
              public void handle(Event event) {
                imagenSeleccionada="reporteReparacionAdmin";
                  llamarInterfaces();              }
          });
       
       item5.setOnAction(new EventHandler(){

              @Override
              public void handle(Event event) {
                imagenSeleccionada="reporteBajaAdmin";
                  llamarInterfaces();              }
          });
        
 /*mantenimientoRubros.setOnMouseClicked(new EventHandler() {

              @Override
              public void handle(Event event) {
              capturarClick(mantenimientoRubros);
              }
          });*/
    }
    
    
    public void setPrincipal(GestionActivos Principal)
    {
        this.Principal=Principal;
    }

public void llamarInterfaces(){
Parent loader = null;
AnchorPane loader2 = null;
AnchorPane loader3 = null;

Scene newScene; //haciendo referencia al scene de la pagina principal
newScene = GestionActivos.scene;

Stage mainWindow; //Haciendo referencia al primaryStage
mainWindow = GestionActivos.primaryStage;

mainWindow.setScene(newScene);     


//cargando el menulateral

try{
loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
}catch(Exception e){}
try{
loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
}catch(Exception e){System.out.println(e);}
switch(imagenSeleccionada)
{
    case "ingresoActivo":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/FXMLDocument.fxml"));
             
             primaryStage.setTitle("Ingreso de un Nuevo Activo");
             
            }catch(Exception e){}
        break;
    case "modificarRubro":
        try{
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/modificarRubro.fxml"));
            primaryStage.setTitle("Modificar Rubro Existente");
        }catch(Exception e){System.out.println(e);}
    break;
    case "ingresarRubro":
        try{
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/ingresarRubro.fxml"));
            primaryStage.setTitle("Agregar Nuevo Rubro");
        }catch(Exception e){}
    break;
    case "reparacionActivo":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesReparacionAdmin.fxml"));
            primaryStage.setTitle("Solicitudes de Reparacion de Activos");
        }catch(Exception e){}
    break;
        case "solicitarActivo":
        try{
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesAsignacionAdmin.fxml"));
            primaryStage.setTitle("Solicitudes de Asignación de Activos");
        }catch(Exception e){ System.out.println(e);}
    break;
        case "bajaActivo":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesBajaAdmin.fxml"));
            primaryStage.setTitle("Solicitudes de Baja de Activos");
        }catch(Exception e){}
    break;
    case "reporteIngreso":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteIngreso.fxml"));
            primaryStage.setTitle("Reporte de Activos Ingresados");
        }catch(Exception e){}
    break;
        case "reporteBajaAdmin":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesBajaAdmin.fxml"));
            primaryStage.setTitle("Reporte de Activos dados de Baja");
        }catch(Exception e){}
        break;
        case "reporteReparacionAdmin":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesReparacionAdmin.fxml"));
            primaryStage.setTitle("Reporte de Activos en Reparacion");
        }catch(Exception e){}
        break;
      case "agregarUbicación":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/agregarUbicacion.fxml"));
            primaryStage.setTitle("Agregar Ubicación");
        }catch(Exception e){}
    break;
           case "controlInventario":
        try{
            
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/controlInventario.fxml"));
            primaryStage.setTitle("Control Inventario");
        }catch(Exception e){}
    break;
   /* //******* Mónica Baja activo **********
    case "bajaActivo":
        try{
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/bajaActivo.fxml"));
             
             primaryStage.setTitle("Baja de un Activo");
        }catch(Exception e){
        break;*/
        }


   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(loader2);
   GestionActivos.rootPane.setRight(loader3);
   
}

    public void capturarClick(ImageView iv) {
       imagenSeleccionada=iv.getId();
       System.out.println(imagenSeleccionada);
       llamarInterfaces();
       
    }
    
            private void hilo(){
        
         /*backgroundTH = new Service<Void>()*/
        backgroundTH  = new ScheduledService<Void>(){
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>(){
            @Override 
            protected Void call()throws Exception{
                
                while(continuar){ 
                  respue= bd.solicitudespendientes(estado);
                  updateMessage("Solicitudes Pendientes : "+" "+respue);//seteando el label 
                  //System.out.println("número: "+respue);
                  
                }//while 
                
            return null;
            }
            };
        }
        
       }; //fin service
       
         backgroundTH.setOnSucceeded(new EventHandler <WorkerStateEvent>(){
             @Override
             public void handle(WorkerStateEvent event) {
                 System.out.println("Done!");
             }
         });
         lblSolicitudes.textProperty().bind(backgroundTH.messageProperty());//para recargar el label 
         backgroundTH.restart();
         backgroundTH.setPeriod(Duration.seconds(10));
        
        
        }//fin hilo 


    

}

    
    
   
