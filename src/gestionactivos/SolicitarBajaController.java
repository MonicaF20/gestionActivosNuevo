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
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author Monica
 */
public class SolicitarBajaController implements Initializable {
   @FXML
   private TextField txtCodigoActivo;
   @FXML   
   private TextField txtNombreActivo;
   @FXML   
   private TextArea txtDetalleDanio;
   @FXML   
   private DatePicker datePickerSolicitudBaja;
   @FXML
   private ComboBox<String> cbUbicacionActivo;
   @FXML
   private Button btnEnviarSol;
   @FXML
   private Button btnCancelarSol; 
   @FXML
   private Label lblCodigoSol;
   
   Solicitud Sol = new Solicitud();
   BDConexion db= BDConexion.getInstance(); 
   EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
   EntityManager em=emf.createEntityManager();
   EntityManagerFactory emf1= Persistence.createEntityManagerFactory("GestionActivosPU");
   EntityManager em1=emf.createEntityManager();
   String codigoLe="SBA";
    /**
     * Initializes the controller class.
     */
   @FXML
   public void enviarSolicitudBaja(ActionEvent event) throws IOException{
     LocalDate localD=datePickerSolicitudBaja.getValue();
     Instant instant=Instant.from(localD.atStartOfDay(ZoneId.systemDefault()));
     Date fecha= Date.from(instant);
       try{
       //Obteniedo el id de la ubicacion del nuevo activo
       em.getTransaction().begin(); 
      Query query=em.createNativeQuery("SELECT u.idubicacion FROM  Ubicacion u where u.nombrelugar=?",Ubicacion.class).setParameter(1, cbUbicacionActivo.getValue().toString());
      Ubicacion ubi=(Ubicacion)query.getSingleResult();
      em.getTransaction().commit();
      em.close();
      
      em1.getTransaction().begin();
      Sol.setFecharegistrasoli(fecha);
      Sol.setDescripcionsolicitud(txtDetalleDanio.getText());
      Sol.setIdubicacion(ubi);
      Sol.setEstadosolicitud("PENDIENTE");
      Sol.setTiposolicitud(codigoLe);
      Sol.setIdsolicitud(lblCodigoSol.getText());
      //lblCodigoSol.setVisible(true);
      em1.persist(Sol);
      em1.getTransaction().commit();
      em1.close();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Solicitud Ingresada Exitosamente");
        alert.setHeaderText(null);
        alert.setContentText("Codigo Generado: "+Sol.getIdsolicitud());

        alert.showAndWait();
        refresh();
   }
       catch(RuntimeException e){
       
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error Dialog");
           alert.setHeaderText(null);
           alert.setContentText("Error al ingresar la nueva solicitud");

           alert.showAndWait();
           System.out.println(e);
   }
   
   }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        datePickerSolicitudBaja=ConvertidorFecha.ConvertidorFecha(datePickerSolicitudBaja);
        ObservableList<String> listaUbi = FXCollections.observableArrayList();
         listaUbi=db.getUbicacion();
         cbUbicacionActivo.setItems(listaUbi);
         String code=db.generarCodigoSolictudActivo(codigoLe);
         lblCodigoSol.setText(code);
         
         
         btnCancelarSol.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "Solicitud Cancelada");
                try {
                    regresarMenu();
                } catch (IOException ex) {
                    Logger.getLogger(MenuLateralDocenteController.class.getName()).log(Level.SEVERE, null, ex);
                }
               
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

//cargando el menulateral
try {
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/SolicitarBaja.fxml"));
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
