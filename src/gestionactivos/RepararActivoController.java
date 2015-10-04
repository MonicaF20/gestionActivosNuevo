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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Andrea
 */
public class RepararActivoController implements Initializable {

    @FXML
    private Label codigoLabel;
    @FXML
    private Label codigo;
    @FXML
    private TextField nombre;
    @FXML
    private Button ingresarBoton;
    @FXML
    private Button cancelarBoton;
    @FXML
    private DatePicker fecha;
    @FXML
    private Button buscarBoton;
    @FXML
    private ComboBox<String> idactivo;
    
BDConexion db= BDConexion.getInstance();


  static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
    static EntityManager em=emf.createEntityManager();
     static EntityManager em2=emf.createEntityManager();
    
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
   ObservableList<String> list2 = FXCollections.observableArrayList();
   list2=db.getIdactivo();
    
   idactivo.setItems(list2);
   AutoCompleteComboBoxListener combobox= new AutoCompleteComboBoxListener<>(idactivo);
     
     
   
    }    
    

     

 @FXML
     public void buscar(){
         
       // String id_activo = (String) idactivo.getValue();
    //Activo activo= new Activo();
    
       //String activo1 = "select * from activo where idactivo = :idactivo";
    //String nom= activo1.getNombreactivo;
    
    
     
     }
     
   @FXML
     public void ingresarActivoDañado(){
    
}
}