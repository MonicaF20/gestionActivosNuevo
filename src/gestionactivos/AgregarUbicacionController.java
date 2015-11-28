/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Rubro;
import gestionactivos.modelo.Ubicacion;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
public class AgregarUbicacionController implements Initializable {
     Alert alert =new Alert(Alert.AlertType.ERROR);
    @FXML private TextField txtUbicacion;
    List<Ubicacion> listaUbicacion=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void ingresarUbicacion(ActionEvent event){
    
       if(txtUbicacion.getText().length()>0 )
    {   EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
        EntityManager em= emf.createEntityManager();
         listaUbicacion= em.createNamedQuery("Ubicacion.findByNombrelugar",Ubicacion.class).setParameter("nombrelugar",txtUbicacion.getText()).getResultList();
       
            if(listaUbicacion.isEmpty())
            {
                //si esta vacia entonces No existe y se puede Ingresar
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Acción");
                alert.setHeaderText(null);
                alert.setContentText("¿Esta Seguro que desea agregar la siguiente Ubicacion?\n\n"
                        + "Nombre de Ubicacion: "+txtUbicacion.getText());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                Ubicacion ubicacion = new Ubicacion();
                ubicacion.setNombrelugar(txtUbicacion.getText());
                em.clear();
                em.getTransaction().begin();
                em.persist(ubicacion);
                em.getTransaction().commit();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Ingreso Correcto");
                alert.setHeaderText(null);
                alert.setContentText("Una  nueva Ubicación ha sido agregado correctamente");
                alert.showAndWait();
               
                refresh();
            }
            }
            else{
            //notificar que el rubro ya existe
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Error: Ubicacion ya Existe");
                alert.setHeaderText(null);
                alert.setContentText("Ubicacion ya Existe, no puede volverla a ingresar");
                alert.showAndWait();
            }
      
                }
    else
    {
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Campos Obligatorios no pueden quedar vacios");
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/agregarUbicacion.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
}
