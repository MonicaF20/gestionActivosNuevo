/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Solicitud;
import gestionactivos.modelo.Ubicacion;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class SolicitudesReparacionAdminController implements Initializable {
    @FXML
    private TextArea descripcion;

    @FXML
    private TextField nombre_soli;

    @FXML
    private TextField fecha;

    @FXML
    private Label codigo;

    @FXML
    private TextField ubicacion;

    @FXML
    private Label codigoLabel;

    @FXML
    private TextField nom_activo;

    @FXML
    private ComboBox<String> idsolicitud;

    @FXML
    private Label lbl_aprobada;
    
    
     BDConexion db = BDConexion.getInstance();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em = emf.createEntityManager();
    
    Solicitud solicitud = new Solicitud();
    Ubicacion ubicacion1 = new Ubicacion();
     Activo activo = new Activo();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2 = db.getSolicitudReparacionActivo() ;//getSolicitudReparacionActivoAdmin

        idsolicitud.setItems(list2);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(idsolicitud);
        
        idsolicitud.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                String valor = idsolicitud.getValue();
         
            solicitud = (Solicitud) em.createNamedQuery("Solicitud.findByIdsolicitud", Solicitud.class).setParameter("idsolicitud",valor).getSingleResult();
            ubicacion1 = solicitud.getIdubicacion(); //tipo UBICACION
        
        
        Ubicacion ubicacion_n= (Ubicacion) em.createNamedQuery("Ubicacion.findByIdubicacion", Ubicacion.class).setParameter("idubicacion", ubicacion1.getIdubicacion()).getSingleResult();
 
        nom_activo.setText(solicitud.getNombreactivo());
        nombre_soli.setText(solicitud.getNombresolicitante());
        ubicacion.setText(ubicacion_n.getNombrelugar());
        descripcion.setText(solicitud.getDescripcionsolicitud());
        fecha.setText(solicitud.getFecharegistrasoli().toString());
        lbl_aprobada.setVisible(true);
        
              
            }
        });
    }
    
    @FXML
    void iniciaReparacion(ActionEvent event) {
         actualiza();
         
        em.getTransaction().begin();
            solicitud.setEstadosolicitud("REALIZADA");  
            em.getTransaction().commit();
            
           
       
   Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Solicitud Aprobada");
        alert.setContentText("Reparacion iniciada con exito");
        alert.setHeaderText(null);
        alert.showAndWait();
        refresh();
       
      
    }
    public void actualiza(){
         String valor = solicitud.getIdactivo();
        activo = (Activo) em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo", valor).getSingleResult();
        em.getTransaction().begin();
        activo.setEstadogeneral("EN REPARACION"); 
            em.getTransaction().commit();
        
    }
    
    
    public void refresh(){
//        
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesReparacionAdmin.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }   
    }    
    

