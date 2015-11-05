/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    private ComboBox<String> rubroSolicDoc;

    @FXML
    private DatePicker fecha;

    @FXML
    private Label codigo;

    @FXML
    private ComboBox<String> ubicacion;

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

   


    
    BDConexion db= BDConexion.getInstance();
 static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
    static EntityManager em=emf.createEntityManager();
     static EntityManager em2=emf.createEntityManager();
     
     
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //LLENADO DE COMBOBOX DE RUBROS
       //LLENADO DE COMBOBOX DE RUBROS
         ObservableList<String> list2 = FXCollections.observableArrayList();
   list2=db.getRubros();
    
   rubroSolicDoc.setItems(list2);
   
   
   //LLENADO DE COMBOBOX DE UBICACION
   
   ObservableList<String> list3 = FXCollections.observableArrayList();
   list3=db.getUbicacion();
    
   ubicacion.setItems(list3);
   
   
   
   
   
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
