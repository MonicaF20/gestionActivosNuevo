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


public class SolicitudesBajaDirectoraController implements Initializable {
     BDConexion db= BDConexion.getInstance(); 
     @FXML
     private TextField txtNombreSol;
     @FXML
     private TextField txtCodigoActivoS;
     @FXML
     private TextField txtFechaSol;
     @FXML
     private TextField txtNombreActivo;
     @FXML
     private TextArea txtDescripcionSol;
     @FXML
     private ComboBox<String> cbIdSolicitud;
     @FXML
     private Button btnAceptarSol;
     @FXML
     private Button btnRechazarSol;
   Solicitud sol = new Solicitud();
   EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
   EntityManager em=emf.createEntityManager();
   Date date= new Date();
   SimpleDateFormat formatoF = new SimpleDateFormat("dd-MM-yyyy"); 
      
   
   
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //para el combo box
        ObservableList<String> listaId = FXCollections.observableArrayList();
        listaId=db.getSolicitudesBaja();
        cbIdSolicitud.setItems(listaId);
        cbIdSolicitud.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                em.getTransaction().begin();
                Query query=em.createNativeQuery("SELECT * FROM  Solicitud s where s.idsolicitud=?",Solicitud.class).setParameter(1, cbIdSolicitud.getValue().toString());
                 sol = (Solicitud)query.getSingleResult();
                em.getTransaction().commit();
                //em.close();
                System.out.println("nombre"+sol.getNombreactivo());
                txtNombreSol.setText(sol.getNombresolicitante());
                txtNombreActivo.setText(sol.getNombreactivo());
                txtCodigoActivoS.setText(sol.getIdactivo());
                date=sol.getFecharegistrasoli();
                String fecha=formatoF.format(date);
                txtFechaSol.setText(fecha);
                txtDescripcionSol.setText(sol.getDescripcionsolicitud());
                
                
            }
        
        
        });
         
        btnAceptarSol.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                em.getTransaction().begin();
                sol.setEstadosolicitud("ACEPTADA");
                em.getTransaction().commit();
                em.close();
                JOptionPane.showMessageDialog(null, "Solicitud Aceptada");
                refresh();
                 
            }
        });
         btnRechazarSol.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                 em.getTransaction().begin();
                sol.setEstadosolicitud("RECHAZADA");
                em.getTransaction().commit();
                em.close();
                JOptionPane.showMessageDialog(null, "RECHAZADA");
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
            loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesBajaDirectora.fxml"));
            loader2 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
            loader3 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
        }
        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);
    }
    
}
