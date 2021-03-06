/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Rubro;
import gestionactivos.modelo.Solicitud;
import gestionactivos.modelo.Ubicacion;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import static java.sql.JDBCType.NULL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

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
    private TextArea descripcion;
    @FXML
    private TextField ubicacion;
    @FXML
    private TextField rubro;
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
    
    @FXML
    private Label soli;
  
    @FXML
    private Label num_soli;
    
    @FXML
    private TextField nom_solicitante;

     @FXML
    private ImageView imageViewCargar;
     
     
    BDConexion db = BDConexion.getInstance();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em = emf.createEntityManager();

    Activo activo = new Activo();
    Solicitud solicitud = new Solicitud();
    String codigoletras = "SAR";
        Rubro rubro1 = new Rubro();
        Ubicacion ubicacion1 = new Ubicacion();
File imgFile=null;
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
       fecha=ConvertidorFecha.ConvertidorFecha(fecha);

        ObservableList<String> list2 = FXCollections.observableArrayList();
        list2 = db.getIdactivo();

        idactivo.setItems(list2);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(idactivo);
        
        String codigo=db.generarCodigoSolictudActivo(codigoletras);
   num_soli.setText(codigo);
   
      
  cancelarBoton.setOnAction ( new EventHandler(){
        

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
  imageViewCargar.setOnMouseClicked(new EventHandler(){

        @Override
        public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Obtener la imagen seleccionada
        imgFile = fileChooser.showOpenDialog(GestionActivos.primaryStage);

        // Mostar la imagen
        if (imgFile != null) {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            imageViewCargar.setImage(image);
        }        }
    });

        

    }

    @FXML
    public void buscar() {
        BDConexion db = BDConexion.getInstance();
    EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em2 = emf2.createEntityManager();
    
    
     
    
       
       
        String valor = idactivo.getValue();
        
        System.out.printf("Boton buscar" + idactivo.getValue());
        
        activo = (Activo) em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo", valor).getSingleResult();
      
        ubicacion1 = activo.getIdubicacion(); //tipo UBICACION
        rubro1=activo.getIdrubro(); //tipo RUBRO
    
        Rubro rubro_n= (Rubro) em.createNamedQuery("Rubro.findByIdrubro", Rubro.class).setParameter("idrubro", rubro1.getIdrubro()).getSingleResult();
        
        
        Ubicacion ubicacion_n= (Ubicacion) em.createNamedQuery("Ubicacion.findByIdubicacion", Ubicacion.class).setParameter("idubicacion", ubicacion1.getIdubicacion()).getSingleResult();
      
        em2.getTransaction().begin();
        nombre.setText(activo.getNombreactivo());
        rubro.setText(rubro_n.getNombrerubro());
        ubicacion.setText(ubicacion_n.getNombrelugar());
        
        
        
        String codigo=db.generarCodigoSolictudActivo(codigoletras);
   num_soli.setText(codigo);
  
    }

    @FXML
    public void ingresarActivoDañado() throws IOException {
        
        if(nombre.getText().isEmpty() || rubro.getText().isEmpty()||ubicacion.getText().isEmpty()||descripcion.getText().isEmpty() || fecha.getValue().toString().isEmpty() || nom_solicitante.getText().isEmpty()){
         
             Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText(null);
alert.setContentText("Debe llenar todos los campos");

alert.showAndWait();

        }
        else{
        
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
     EntityManager em=emf.createEntityManager();
      
    EntityManagerFactory emf2= Persistence.createEntityManagerFactory("GestionActivosPU");
     EntityManager em2=emf2.createEntityManager();
   
       LocalDate localDate=fecha.getValue();
       Instant instant=Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
       Date date= Date.from(instant);
       try{
     
       em2.getTransaction().begin();
      solicitud.setFecharegistrasoli(date);     
      solicitud.setDescripcionsolicitud(descripcion.getText());
      solicitud.setEstadosolicitud("PENDIENTE");
      solicitud.setTiposolicitud(codigoletras);
      solicitud.setIdubicacion(ubicacion1);
      solicitud.setIdsolicitud(num_soli.getText());
      solicitud.setNombreactivo(activo.getNombreactivo());
      solicitud.setIdactivo(activo.getIdactivo());
      solicitud.setNombresolicitante(nom_solicitante.getText());
      num_soli.setVisible(true);
      soli.setVisible(true);
      if(imgFile!= null)
      {
          System.out.println(imgFile);
          System.out.println(this.convertirImagen(imgFile));
          solicitud.setImagenSolicitud(this.convertirImagen(imgFile));
          System.out.println(solicitud.getImagenSolicitud());
      }
      //em2.getTransaction().begin();
       //codigo.setVisible(true);
     // String codigoGenerado= db.generarCodigoSolictudActivo(codigoletras);
      em2.persist(solicitud);
      em2.getTransaction().commit();
      em2.close();//cerrar el objeto del entitymanager
      emf.close();//cerrar la conexion a la base de datos
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Solicitud Ingresada Exitosamente");
        alert.setHeaderText(null);
        alert.setContentText("Codigo Generado: "+solicitud.getIdsolicitud());

        alert.showAndWait();
            refresh();
   
    //alert diciendo
   
     // System.out.println("Id de la Ubicacion "+results.getIdubicacion());
     
     //Seteando un objeto activo para luego ingresarlo a la base
     
        
   
     }
     catch (RuntimeException e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText(null);
alert.setContentText("Error al ingresar la nueva solicitud");

alert.showAndWait();
     System.out.println(e);}
      
   }
     
        
    }  
    public void regresarMenu() throws IOException{
   AnchorPane loader= null;
    loader=(AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menudocente.fxml"));
   GestionActivos.rootPane.setCenter(loader);
   GestionActivos.rootPane.setLeft(null);
   GestionActivos.rootPane.setRight(null);
   
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
            loader= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/RepararActivo.fxml"));
            loader2= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateraldocente.fxml"));
            loader3= (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));   //// LATERAL DERECHO ES PARA TODOS???????
        } catch (Exception e) {
    }
 GestionActivos.rootPane.setCenter(loader);
  GestionActivos.rootPane.setLeft(loader2);
  GestionActivos.rootPane.setRight(loader3); 
    }

public byte[] convertirImagen(File fileImg) throws IOException{
    
        byte[] imageInByte;
        BufferedImage originalImage = ImageIO.read(fileImg);

        // convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();

      
return imageInByte;
    
    }
    
    }
    


