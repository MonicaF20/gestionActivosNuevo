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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andrea
 */
public class DocenteController implements Initializable {
    
    GestionActivos Principal;
  @FXML
      private ImageView solicitarActivo;
       @FXML
      private ImageView reparacionActivo;
       @FXML
       List lista= new ArrayList<>();
    String imagenSeleccionada="";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
             }
    
    public void setPrincipal(GestionActivos Principal)
    {
        this.Principal=Principal;
    }

public void llamarInterfaces(String seleccionado){
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
loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateraldocente.fxml"));
}catch(Exception e){}
try{
loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
}catch(Exception e){System.out.println(e);}
switch(imagenSeleccionada)
{
    case "reparacionActivo":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/RepararActivo.fxml"));
             
             primaryStage.setTitle("Generar repararacion de  Activo");
             
            }catch(Exception e){}
        break;
        case "socilitarActivo":
        try{
             loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/SolicitarActivo.fxml"));
             
             primaryStage.setTitle("Solicitar Activo");
             
            }catch(Exception e){}
        break;
    
                }
GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(loader2);
   GestionActivos.rootPane.setRight(loader3);
                }


public void capturarClick(ImageView iv) {
       imagenSeleccionada=iv.getId();
       System.out.println("Seleccion: "+imagenSeleccionada);
       llamarInterfaces(imagenSeleccionada);
       
    }  
    
    }  
