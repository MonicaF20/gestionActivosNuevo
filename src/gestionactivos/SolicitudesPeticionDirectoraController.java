/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Rubro;
import gestionactivos.modelo.Solicitud;
import gestionactivos.modelo.Ubicacion;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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




public class SolicitudesPeticionDirectoraController implements Initializable {

    /**
     * Initializes the controller class.
     */
   @FXML
    private ComboBox<String> codSolic;
   
   @FXML
    private TextField nombreSolic;
   
   @FXML
    private TextField nombreActivo;
   
   @FXML
    private TextField ubicacion;
    
   
    @FXML
    private TextField fecha;
    
    
    @FXML
    private TextArea descripcion;
    
      @FXML
    private Button aceptarBoton;
      
       @FXML
    private Button rechazarBoton;

    BDConexion db= BDConexion.getInstance();
EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
EntityManager em= emf.createEntityManager();
  Solicitud solicitud = new Solicitud();
  Ubicacion ubicacion1 = new Ubicacion();
  //Ubicacion ubi = new Ubicacion();

  @FXML
   public void AceptarSolicitud(ActionEvent event){
       
   em.getTransaction().begin();
   solicitud.setEstadosolicitud("ACEPTADA");
   em.getTransaction().commit();
       JOptionPane.showMessageDialog(null, "Solicitud Aceptada");
       
    refresh();
}
      
   @FXML
   public void CancelarSolicitud(ActionEvent event){
       em.getTransaction().begin();
   solicitud.setEstadosolicitud("RECHAZADA");
   em.getTransaction().commit();
       JOptionPane.showMessageDialog(null, "Solicitud Rechazada");
       refresh();
    //
}
   
  
  
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> solicitudes= FXCollections.observableArrayList();
        solicitudes =db.getSolicitudPeticionActivo();
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesPeticionDirectora.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
}



 