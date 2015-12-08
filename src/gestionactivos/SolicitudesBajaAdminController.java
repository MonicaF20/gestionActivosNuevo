/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Registrodeactivobaja;
import gestionactivos.modelo.Solicitud;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
 * @author Monica
 */
public class SolicitudesBajaAdminController implements Initializable {
     @FXML
     private TextField txtNombreS;
     @FXML
     private TextField txtCodigoArt;
     @FXML
     private TextArea txtDescriS;
     @FXML
     private TextField txtFechaS;
     @FXML
     private Button btnBaja;
     @FXML
     private ComboBox<String> cbSolId;
     @FXML
     private Label lblSolAceptada;
     
    BDConexion db = BDConexion.getInstance();
    Solicitud sol = new Solicitud();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em = emf.createEntityManager();
    EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em2 = emf2.createEntityManager();
    Date date = new Date();
    Date fechaBaja= new Date();
    SimpleDateFormat formatoF = new SimpleDateFormat("dd-MM-yyyy");
    Activo activo = new Activo();
    Registrodeactivobaja registrobaja= new Registrodeactivobaja();
    Registrodeactivobaja registrobaux= new Registrodeactivobaja();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ObservableList<String> listaId = FXCollections.observableArrayList();
        listaId=db.getSolicitudesBajaAcept();
        if(listaId.isEmpty()){
           listaId.add("No hay Solicitudes");
        }
        cbSolId.setItems(listaId);
            cbSolId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                em.getTransaction().begin();
                Query query=em.createNativeQuery("SELECT * FROM  Solicitud s where s.idsolicitud=?",Solicitud.class).setParameter(1, cbSolId.getValue().toString());
                 sol = (Solicitud)query.getSingleResult();
                em.getTransaction().commit();
                //em.close();
                System.out.println("nombre"+sol.getNombreactivo());
                lblSolAceptada.setVisible(true);
                txtNombreS.setText(sol.getNombreactivo());
                txtCodigoArt.setText(sol.getIdactivo());
                date=sol.getFecharegistrasoli();
                String fecha=formatoF.format(date);
                txtFechaS.setText(fecha);
                txtDescriS.setText(sol.getDescripcionsolicitud());
               
                
            }
        
        
        });
            btnBaja.setOnAction(new EventHandler<ActionEvent>() {

             @Override
             public void handle(ActionEvent event) {
                 em.getTransaction().begin();
                 activo=em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo",sol.getIdactivo().toString()).getSingleResult();
                 //System.out.println(activo.getEstadogeneral().toString());
                 activo.setEstadogeneral("NO DISPONIBLE");
                 sol.setEstadosolicitud("EJECUTADA");
                 //registrobaja.set
                 
                 registrobaja.setFechabaja(fechaBaja);
                 registrobaja.setIdsolicitud(sol.getIdsolicitud());
                 em.getTransaction().commit();
                 em.close();
                em2.getTransaction().begin();
                em2.persist(registrobaja);
                em2.getTransaction().commit();
                em2.close();
                 JOptionPane.showMessageDialog(null, "Activo dado de Baja ");
                refresh();
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
            loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesBajaAdmin.fxml"));
            loader2 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
            loader3 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
        }
        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);
    }

    
    
}
