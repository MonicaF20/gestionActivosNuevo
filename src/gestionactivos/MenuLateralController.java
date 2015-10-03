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
     
        
}

   GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3);
        

   
}
    
    }
    

