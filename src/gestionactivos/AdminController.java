/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.GestionActivos.primaryStage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    List lista= new ArrayList<>();
    String imagenSeleccionada="";
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
       cm.getItems().addAll(item1,item2);
     
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
        }catch(Exception e){System.out.println(e);}
    break;
    case "ingresarRubro":
        try{
            loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/ingresarRubro.fxml"));
        }catch(Exception e){}
    break;
    //******* Mónica Baja activo **********
    case "bajaActivo":
        try{
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/bajaActivo.fxml"));
             
             primaryStage.setTitle("Baja de un Activo");
        }catch(Exception e){
        
        }
    
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

   public void cargarSubMenu(){
       final ContextMenu cm= new ContextMenu();
       MenuItem item1= new MenuItem("Modificar Rubro");
       MenuItem item2= new MenuItem("Agregar Nuevo Rubro");
       cm.getItems().addAll(item1,item2);
       reportes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override 
        public void handle(MouseEvent e) {
            if (e.getButton() == MouseButton.PRIMARY)  
                cm.show(reportes,e.getScreenX(),e.getScreenY());
        }
});
   }

    

}

    
    
   
