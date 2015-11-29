/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Solicitud;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class EstadoSolPeticionController implements Initializable {
     @FXML
    private Label estado;
 @FXML
    private ComboBox<String> idsolicitud;
 
 @FXML
    private Button procesar;

    Solicitud solicitud = new Solicitud();
     BDConexion db = BDConexion.getInstance();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em = emf.createEntityManager();
    /**
     * Initializes the controller class.
     * 
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2 = db.getSolicitudPeticionActivoAll();

        idsolicitud.setItems(list2);
    }    
    
    
    @FXML
    void procesarConsulta(ActionEvent event) {
        
        String valor = idsolicitud.getValue();
         
            solicitud = (Solicitud) em.createNamedQuery("Solicitud.findByIdsolicitud", Solicitud.class).setParameter("idsolicitud",valor).getSingleResult();
                String estado1 = solicitud.getEstadosolicitud();
                estado.setText(estado1);
    }
    
}
