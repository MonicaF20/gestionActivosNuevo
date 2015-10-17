/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Rubro;
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
import javafx.scene.control.Alert.AlertType;
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
public class ModificarRubroController implements Initializable {

 @FXML private ComboBox<String> cmbRubrosModificar;
 @FXML private Label lblCodRubro;
 @FXML private TextField txtNombreRubro;
 @FXML private TextArea txtDescripRubro;
 
BDConexion db= BDConexion.getInstance();
EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
EntityManager em= emf.createEntityManager();

Rubro rubro= new Rubro();

Alert alert=new Alert(AlertType.ERROR);


 @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> rubros= FXCollections.observableArrayList();
        rubros=db.getRubros();
        cmbRubrosModificar.setItems(rubros);
      AutoCompleteComboBoxListener cmb= new AutoCompleteComboBoxListener(cmbRubrosModificar);
      
      cmbRubrosModificar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rubro=(Rubro) em.createNamedQuery("Rubro.findByNombrerubro",Rubro.class).setParameter("nombrerubro",cmbRubrosModificar.getValue()).getSingleResult();
                lblCodRubro.setText(rubro.getIdrubro());
                txtNombreRubro.setText(rubro.getNombrerubro());
                txtDescripRubro.setText(rubro.getDescripcionrubro());
            }
        });
    }    
   
    public void actualizarDatos(ActionEvent event){
    if(txtNombreRubro.getText().length()>0 && txtDescripRubro.getText().length()>0)
    {
        em.getTransaction().begin();
        rubro.setNombrerubro(txtNombreRubro.getText());
        rubro.setDescripcionrubro(txtDescripRubro.getText());
        em.getTransaction().commit();
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle("Actualización Exitosa");
        alert.setHeaderText(null);
        alert.setContentText("Actualizacion de Rubro Realizada Correctamente");
        alert.showAndWait();
        refresh();
    }else
    {alert.setTitle("Error");
       alert.setHeaderText(null);
       alert.setContentText("Campos Obligatorios no pueden estar vacios");

        alert.showAndWait();
    
    
    }
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/modificarRubro.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
}
