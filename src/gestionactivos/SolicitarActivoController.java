/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Solicitud;
import gestionactivos.modelo.Ubicacion;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Marce
 */
public class SolicitarActivoController implements Initializable {

    @FXML
    private TextArea descripcion;

  

    @FXML
    private DatePicker fecha;

    @FXML
    private Label codigo;

    @FXML
    private ComboBox<String> ubicacionSolicDoc;

    @FXML
    private Label codigoLabel;

    @FXML
    private Button ingresarBoton;

    @FXML
    private Button cancelarBoton;

    @FXML
    private TextField nombreActivo;

    
    @FXML
    private TextField cantidad;
    
     @FXML
    private TextField nombreSolicitante;
     
    @FXML
    private Label labelcodigo1;

   


    
    BDConexion db= BDConexion.getInstance();    
    Solicitud solicitud = new Solicitud();    
    Ubicacion location= new Ubicacion();
    //codigo que antepondra al correlativo de las solicitudes de activo
    String codigoletras = "SAC";
    
    
 static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
    static EntityManager em=emf.createEntityManager();
     static EntityManager em2=emf.createEntityManager();
     
     
     
     @FXML
   public void ingresarSolicitudActivo(ActionEvent event){
      if(nombreActivo.getText().isEmpty() || descripcion.getText().isEmpty() || cantidad.getText().isEmpty() || nombreSolicitante.getText().isEmpty() || ubicacionSolicDoc.getValue().isEmpty() || (fecha.getValue().toString().isEmpty())){
      
               JOptionPane.showMessageDialog(null, "Error. Debe ingresar todos los campos");}
      else{
       
     EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
     EntityManager em=emf.createEntityManager();
      
    EntityManagerFactory emf2= Persistence.createEntityManagerFactory("GestionActivosPU");
     EntityManager em2=emf2.createEntityManager();
   
       LocalDate localDate=fecha.getValue();
       Instant instant=Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
       Date date= Date.from(instant);
       try{
       //Obteniedo el id de la ubicacion del nuevo activo
       em.getTransaction().begin(); 
      
       
       
       Query query=em.createNativeQuery("SELECT u.idubicacion FROM  Ubicacion u where u.nombrelugar=?",Ubicacion.class).setParameter(1, ubicacionSolicDoc.getValue().toString());
      Ubicacion results=(Ubicacion) query.getSingleResult();
      
     /* Query query2=em.createNativeQuery("SELECT r.idrubro FROM Rubro r  WHERE r.nombrerubro=?",Rubro.class).setParameter(1,rubro.getValue().toString());
      Rubro  results2= (Rubro)query2.getSingleResult();*/
              
     em.getTransaction().commit();
     em.close();
       
   //if(nombreSolicitant.getText().length()>0){
        //solicitud.setIdsolicitud();
       em2.getTransaction().begin();
      solicitud.setNombreactivo(nombreActivo.getText());
      solicitud.setFecharegistrasoli(date);
      solicitud.setNombresolicitante(nombreSolicitante.getText());     
      solicitud.setDescripcionsolicitud(descripcion.getText());
      solicitud.setCantidadsolicitada(Integer.getInteger(cantidad.getText()));
      solicitud.setEstadosolicitud("PENDIENTE");
      solicitud.setTiposolicitud(codigoletras);
      solicitud.setIdubicacion(results);
      solicitud.setIdsolicitud(labelcodigo1.getText());
      labelcodigo1.setVisible(true);
       //codigo.setVisible(true);
     // String codigoGenerado= db.generarCodigoSolictudActivo(codigoletras);
      em2.persist(solicitud);
      em2.getTransaction().commit();
      em2.close();//cerrar el objeto del entitymanager
      emf.close();//cerrar la conexion a la base de datos
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Solicitud Ingresada Exitosamente");
        alert.setHeaderText(null);
        alert.setContentText("Codigo Generado: "+solicitud.getIdsolicitud());

        alert.showAndWait();
            refresh();
   
    //alert diciendo
   
     // System.out.println("Id de la Ubicacion "+results.getIdubicacion());
     
     //Seteando un objeto activo para luego ingresarlo a la base
     
        
   
     }
     catch (RuntimeException e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText(null);
alert.setContentText("Error al ingresar la nueva solicitud");

alert.showAndWait();
     System.out.println(e);}
      
   }
     
   }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       
   
   
   //LLENADO DE COMBOBOX DE UBICACION
   
   ObservableList<String> list3 = FXCollections.observableArrayList();
   list3=db.getUbicacion();
    
   ubicacionSolicDoc.setItems(list3);
   
   
   
   
   
   cancelarBoton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event event) {
                
               JOptionPane.showMessageDialog(null, "Solicitud Cancelada");
                try {
                    regresarMenu();
                } catch (IOException ex) {
                    Logger.getLogger(MenuLateralDocenteController.class.getName()).log(Level.SEVERE, null, ex);
                }
 }
        });
   
   
   String codigo=db.generarCodigoSolictudActivo(codigoletras);
   labelcodigo1.setText(codigo);
   
    }    
    
    
    
     public void refresh(){
        Parent loader = null;
        AnchorPane loader2 = null;
        AnchorPane loader3 = null;

Scene newScene; //haciendo referencia al scene de la pagina principal
newScene = GestionActivos.scene;
Stage mainWindow; //Haciendo referencia al primaryStage
mainWindow = GestionActivos.primaryStage;
mainWindow.setScene(newScene);     

//cargando el menulateral
try {
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/SolicitarActivo.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateraldocente.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
    
    
    
    //METODO REGRESA MENU
   public void regresarMenu() throws IOException{
   AnchorPane loader= null;
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menudocente.fxml"));
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   
    }  
}
