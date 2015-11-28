
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import gestionactivos.modelo.Solicitud;
import gestionactivos.modelo.Ubicacion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;



/**
 * FXML Controller class
 *
 * @author Valery
 */
public class SolicitudesReparacionDirectoraController implements Initializable {
     @FXML
    private TextArea descripcion;
     
     @FXML
    private Button Cancelarsoli;

    @FXML
    private ComboBox<String> idsolicitud;

    @FXML
    private TextField fecha;

    @FXML
    private Label codigo;

    @FXML
    private TextField ubicacion;


    @FXML
    private TextField nombre;

    @FXML
    private TextField nombre_soli;

    BDConexion db = BDConexion.getInstance();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em = emf.createEntityManager();
    
    Solicitud solicitud = new Solicitud();
    Ubicacion ubicacion1 = new Ubicacion();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2 = db.getSolicitudReparacionActivo();

        idsolicitud.setItems(list2);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(idsolicitud);
        
        idsolicitud.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                String valor = idsolicitud.getValue();
         
    solicitud = (Solicitud) em.createNamedQuery("Solicitud.findByIdsolicitud", Solicitud.class).setParameter("idsolicitud",valor).getSingleResult();
            ubicacion1 = solicitud.getIdubicacion(); //tipo UBICACION
        
        
        Ubicacion ubicacion_n= (Ubicacion) em.createNamedQuery("Ubicacion.findByIdubicacion", Ubicacion.class).setParameter("idubicacion", ubicacion1.getIdubicacion()).getSingleResult();
 
        nombre_soli.setText(solicitud.getNombresolicitante());
        nombre.setText(solicitud.getNombreactivo() );
        ubicacion.setText(ubicacion_n.getNombrelugar());
        descripcion.setText(solicitud.getDescripcionsolicitud());
        fecha.setText(solicitud.getFecharegistrasoli().toString());
        
              
            }
        });
        
          Cancelarsoli.setOnAction ( new EventHandler(){
        

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
        @FXML
    void aceptarSoli(ActionEvent event) {
        //tivo) em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo", valor3).getSingleResult();
            em.getTransaction().begin();
            solicitud.setEstadosolicitud("ACEPTADA");  
            em.getTransaction().commit();
   Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Solicitud Aprobada");
        alert.setContentText("Solicitud Aprobada");
        alert.setHeaderText(null);
        alert.showAndWait();
        refresh();
    }
     
    @FXML
    void RechazarSoli(ActionEvent event) {

        em.getTransaction().begin();
            solicitud.setEstadosolicitud("RECHAZADA");  
            em.getTransaction().commit();
            
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Solicitud Aprobada Exitosamente");
        alert.setContentText("Solicitud Rechazada");
        
        alert.setHeaderText(null);

        alert.showAndWait();
            refresh();
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesReparacionDirectora.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
    
   public void regresarMenu() throws IOException{
   AnchorPane loader= null;
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuDirectora.fxml"));
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   
    }  
    
    
   

                
}
