/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class GestionActivos extends Application implements Runnable {
   public static Stage primaryStage;
   public static BorderPane rootPane;
    private AnchorPane ventanaDos;
     private AnchorPane header;
  public static Scene scene;
  BDConexion bd= BDConexion.getInstance();
    public final String  estado="PENDIENTE";
    String respuesta="";
    Thread t=new Thread(this);
    private boolean continuar =true;
    public void lanzarhilo(){
    t.start(); 
    
    }
   
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        GestionActivos.primaryStage=primaryStage;
        cargarPrincipal();
       //(new Thread(new GestionActivos() )).start();
      lanzarhilo();

    }
    public  void cargarPrincipal(){
           try{
            //FXMLLoader loader= new FXMLLoader(GestionActivos.class.getResource("/gestionactivos/vistas/principal.fxml"));
           FXMLLoader loader= new FXMLLoader(GestionActivos.class.getResource("/gestionactivos/vistas/inicio.fxml"));
            rootPane=(BorderPane)loader.load();
            scene=new Scene(rootPane);
           // primaryStage.setTitle("Sistema para la Gestion de Activos Fijos");
            primaryStage.setScene(scene);
            //primaryStage.setFullScreen(true);
            InicioController controller = loader.getController();
            controller.setPrincipal(this);
            rootPane.setCenter(addCenter(0));
            primaryStage.show();
          
            }catch(IOException e){
                }
          
        }

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);        
    }
    
   
   
    
      public AnchorPane addCenter(int interfaz) {
    AnchorPane anchor = new AnchorPane();
    FXMLLoader loader;
    switch(interfaz)
    {
        case 0:
            try{
             loader= new FXMLLoader(GestionActivos.class.getResource("/gestionactivos/vistas/logeo.fxml"));
             anchor= (AnchorPane)loader.load();
             primaryStage.setTitle("Sistema para la Gestion de Activos Fijos");
             
            }catch(Exception e){}
    //codigo para la excepcion
            
            break;
        case 1:
            try{
             loader= new FXMLLoader(GestionActivos.class.getResource("/gestionactivos/vistas/menu.fxml"));
             anchor= (AnchorPane)loader.load();
             primaryStage.setTitle("Sistema para la Gestion de Activos Fijos");
             
            }catch(Exception e){}
    //codigo para la excepcion
            
            break;
        case 2:
             anchor.getStylesheets().add("/gestionactivos/css/form.css");
             anchor.getStyleClass().add("center");
        try{
        loader= new FXMLLoader(GestionActivos.class.getResource("/gestionactivos/vistas/FXMLDocument.fxml"));
        anchor = (AnchorPane)loader.load();
        anchor.setPrefSize(anchor.getPrefWidth(), anchor.getPrefHeight());
        primaryStage.setTitle("Ingreso de Nuevo Activo");
        FXMLDocumentController controller= loader.getController();
         
    }catch(Exception e){}
    //codigo para la excepcion
  
            
            
            
            break;
    }
  
   
    
    return anchor;
      }

  
      public void addAnchorCenter(){}
    
      
    
    public void run() {
          while(continuar){
          respuesta= bd.solicitudespendientes(estado);
          System.out.println("número: "+respuesta);
          try {
			t.sleep(5000);//milisegundos
		} catch (InterruptedException ex) {
			t.currentThread().interrupt();
                        Thread.currentThread().interrupt();
		}
          if(rootPane.isVisible()==false){
            continuar=false;
          }
          }
         
          //esperar();
//        BDConexion bd= BDConexion.getInstance();
//        if(bd.isConnected()){
//            respuesta= bd.solicitudespendientes(estado);
//        System.out.println("número: "+respuesta);
//        esperar();
//        }
        
    }
     
    
 
}
