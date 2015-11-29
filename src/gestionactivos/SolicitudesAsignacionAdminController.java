/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Rubro;
import gestionactivos.modelo.Solicitud;
import gestionactivos.modelo.Ubicacion;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
 * @author Valery
 */
public class SolicitudesAsignacionAdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
     @FXML
    private TextArea descripcion;

    @FXML
    private TextField fecha;

    @FXML
    private Label codigo;

    @FXML
    private TextField ubicacion;

    @FXML
    private Label codigoLabel;

    @FXML
    private TextField nombreSolic;

    @FXML
    private Button ingresarBoton;

    @FXML
    private TextField nombreActivo;
    
    @FXML
    private ComboBox<String> codSolic;
    
    @FXML
    private Button Cancelar;
    
    
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
EntityManager em= emf.createEntityManager();


    BDConexion db= BDConexion.getInstance();
  Solicitud solicitud = new Solicitud();
  Solicitud solicitud1 = new Solicitud();
  Ubicacion ubicacion1 = new Ubicacion();
  Activo activo = new Activo();  
  
 
  
  @FXML
   public void AsignarActivo(ActionEvent event){
       
        
   solicitud1 =(Solicitud) em.createNamedQuery("Solicitud.findByIdsolicitud",Solicitud.class).setParameter("idsolicitud",codSolic.getValue()).getSingleResult();
  // activo = (Activo) em.createNamedQuery("Activo.findByNombreactivo", Activo.class).setParameter("nombreactivo",solicitud1.getNombreactivo()).getSingleResult();     
   
   
   Query query2=em.createNativeQuery("SELECT a.idactivo FROM activo a  WHERE a.nombreactivo=? AND estadogeneral='DISPONIBLE'",Activo.class).setParameter(1,solicitud1.getNombreactivo());
      Activo  results2= (Activo)query2.getSingleResult();
    
      
      if (results2.getNombreactivo().isEmpty())
      {
      
         JOptionPane.showMessageDialog(null, "jajajsjdaksdassle");
      }
       System.out.println("results2"+results2);
      if(solicitud1.getNombreactivo() == results2.getNombreactivo())
      {
         // Clase();
          System.out.println("igualdad");
          
          em.getTransaction().begin();               
         solicitud1.setEstadosolicitud("NO ASIGNADO");
         em.getTransaction().commit();
         
         JOptionPane.showMessageDialog(null, "No Existe Activo Disponible");
        
      }else{ //if(solicitud1.getNombreactivo()!= results2.getNombreactivo()){
          em.getTransaction().begin();               
         solicitud1.setEstadosolicitud("ASIGNADO");
         em.getTransaction().commit();
      JOptionPane.showMessageDialog(null, "Activo Asignado");
      }
      
           refresh();
   }
  
 
   
    @Override      
    public void initialize(URL url, ResourceBundle rb) {
                  // TODO
        
        ObservableList<String> solicitudes= FXCollections.observableArrayList();
        solicitudes =db.getSolicitudPeticionActivoAdmin();
        codSolic.setItems(solicitudes);
      AutoCompleteComboBoxListener cmb= new AutoCompleteComboBoxListener(codSolic);
      
      
        
        codSolic.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               
               solicitud =(Solicitud) em.createNamedQuery("Solicitud.findByIdsolicitud",Solicitud.class).setParameter("idsolicitud",codSolic.getValue()).getSingleResult();
               ubicacion1 = solicitud.getIdubicacion();
               
               Ubicacion ubi =(Ubicacion) em.createNamedQuery("Ubicacion.findByIdubicacion",Ubicacion.class).setParameter("idubicacion",ubicacion1.getIdubicacion()).getSingleResult();
              ubicacion.setText(ubi.getNombrelugar());
              
               nombreSolic.setText(solicitud.getNombresolicitante());
               nombreActivo.setText(solicitud.getNombreactivo());
              
              fecha.setText(solicitud.getFecharegistrasoli().toString());
              descripcion.setText(solicitud.getDescripcionsolicitud());
              
               
            }
        });
        
       Cancelar.setOnAction(new EventHandler(){

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
    }   
    
        
         //METODO REGRESA MENU
   public void regresarMenu() throws IOException{
   AnchorPane loader= null;
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuAdmin.fxml"));
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   
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

try {
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesAsignacionAdmin.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
    }    
    
