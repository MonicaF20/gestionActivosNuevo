/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Solicitud;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class EstadoSolBajaController implements Initializable {
    @FXML
    private ComboBox cbIdSol;
    @FXML
    private Label lblRespuesta;
    @FXML
    private Button btnConsulta;
    
    BDConexion db= BDConexion.getInstance(); 
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em=emf.createEntityManager();
    Solicitud solicitud= new Solicitud();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //para el combo box
        ObservableList<String> listaId = FXCollections.observableArrayList();
        listaId=db.getSolicitudesBajaTodas();
        cbIdSol.setItems(listaId);
        
           cbIdSol.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 em.getTransaction().begin();
                Query query=em.createNativeQuery("SELECT * FROM  Solicitud s where s.idsolicitud=?",Solicitud.class).setParameter(1, cbIdSol.getValue().toString());
                solicitud = (Solicitud)query.getSingleResult();
                em.getTransaction().commit();
                //em.close();
                 
            }
        
        
        });  //listener 
        
        btnConsulta.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                String estado=solicitud.getEstadosolicitud();
                System.out.println(estado);
                switch(estado)
                {
                    case "PENDIENTE":
                            lblRespuesta.setText("Su solicitud aún no ha sido procesada");
                        break;
                    case "ACEPTADA":
                        lblRespuesta.setText("Su solicitud ha sido Aceptada");
                        break;
                    case "RECHAZADA":
                        lblRespuesta.setText("Su solicitud ha sido Rechazada");
                        break;
                
                }
                lblRespuesta.setVisible(true);   
                
            
             }
        });  //event handler
        
    }    
    public void refresh() {
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
            loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/estadoSolBaja.fxml"));
            loader2 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateraldocente.fxml"));
            loader3 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
        }
        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);
    }
    
    
}
