/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.*;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 *
 * @author USER
 */
public class FXMLDocumentController implements Initializable {
    /*Para hacer referencia a los objetos del formulario se antepone la etiqueta @FXML
    y se le da el nombre al objeto segun se le puso  en el id
    */
    GestionActivos ga;
   @FXML
   private Label codigo;
    @FXML
   private Label codigoLabel;
   @FXML
   private ComboBox<String> rubro;
   @FXML
   private TextField nombre;
   @FXML
   private TextArea descripcion;
   @FXML
   private ComboBox ubicacion;
    @FXML
   private TextField ubi;
   @FXML
   private Button ingresarBoton;
   @FXML
   private Button cancelarBoton;
   @FXML 
   private DatePicker fecha;
   
   BDConexion db= BDConexion.getInstance();
   Activo activo= new Activo();
   Ubicacion location= new Ubicacion();
   
  
   
   @FXML
   public void ingresarActivo(ActionEvent event){
       EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
     EntityManager em=emf.createEntityManager();
      
    EntityManagerFactory emf2= Persistence.createEntityManagerFactory("GestionActivosPU");
     EntityManager em2=emf2.createEntityManager();
   
       LocalDate localDate=fecha.getValue();
       Instant instant=Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
       Date date= Date.from(instant);
       try{
       //Obteniedo el id de la ubicacion del nuevo activo
       em.getTransaction().begin(); 
      Query query=em.createNativeQuery("SELECT u.idubicacion FROM  Ubicacion u where u.nombrelugar=?",Ubicacion.class).setParameter(1, ubicacion.getValue().toString());
      Ubicacion results=(Ubicacion) query.getSingleResult();
      
      Query query2=em.createNativeQuery("SELECT r.idrubro FROM Rubro r  WHERE r.nombrerubro=?",Rubro.class).setParameter(1,rubro.getValue().toString());
      Rubro  results2= (Rubro)query2.getSingleResult();
              
     em.getTransaction().commit();
     em.close();
       
   
    
     // System.out.println("Id de la Ubicacion "+results.getIdubicacion());
     
     //Seteando un objeto activo para luego ingresarlo a la base
     
      activo.setFechaingres(date);
      activo.setIdactivo(codigo.getText());
      activo.setNombreactivo(nombre.getText());
      activo.setDescripcionactivo(descripcion.getText());
      activo.setIdubicacion(results);
      activo.setIdrubro(results2);
      System.out.println(results);
      System.out.println(results2);
      activo.setEstadogeneral("DISPONIBLE");
      em2.getTransaction().begin();
      em2.persist(activo);
      em2.getTransaction().commit();
      em2.close();//cerrar el objeto del entitymanager
      emf.close();//cerrar la conexion a la base de datos
      Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Activo Ingresado Exitosamente");
        alert.setHeaderText(null);
        alert.setContentText("Codigo Generado: "+activo.getIdactivo());

        alert.showAndWait();
        refresh();
   
     }
     catch (RuntimeException e) {
    Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText(null);
alert.setContentText("Error en el Ingreso de Nuevo Activo");

alert.showAndWait();
     System.out.println(e);}
      
   }
   
   
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
     
      
      //set datepicker con la fecha actual 
    String pattern="dd-MM-yyyy"; 
    fecha.setValue(LocalDate.now());
    fecha.setPromptText(pattern.toLowerCase());
    fecha.setConverter(new StringConverter<LocalDate>()
    {
 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
          @Override
          public String toString(LocalDate object) {
              if(object != null)
              {
                  return dateFormatter.format(object);
              }else
              {
                  return "";
              }
          }

          @Override
          public LocalDate fromString(String string) {

          if (string != null && !string.isEmpty()) {
             return LocalDate.parse(string, dateFormatter);
         } else {
             return null;
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/FXMLDocument.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuLateral.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }
   
    @FXML
    public void cargarRubros(){
            
        //Llenando el combobox del rubro con datos de la base

      ObservableList<String> list = FXCollections.observableArrayList();
      list=db.getRubros();
     rubro.setItems(list);
     rubro.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
     {

          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               // generando el codigo del nuevo activo
    String codigoGenerado= db.generarCodigo(rubro.getValue());
    codigo.setText(codigoGenerado);
    codigoLabel.setVisible(true);
    codigo.setVisible(true);
   
          }
     
     }
     
     
     );
    }
    @FXML
     public void cargarUbicaciones(){
          //Autocomplete Ubicacion
   ObservableList<String> list2 = FXCollections.observableArrayList();
   list2=db.getUbicacion();
    
   ubicacion.setItems(list2);
   AutoCompleteComboBoxListener combobox= new AutoCompleteComboBoxListener<>(ubicacion);
     
     }
        
        
    
}
