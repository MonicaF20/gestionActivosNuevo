/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import gestionactivos.modelo.*;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
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
   @FXML private Label codigo;
   @FXML private Label codigoLabel;
   @FXML private ComboBox<String> rubro;
   @FXML  private TextField nombre;
   @FXML private TextArea descripcion;
   @FXML  private ComboBox ubicacion;
   @FXML private TextField ubi;
   @FXML private Button ingresarBoton;
   @FXML private Button cancelarBoton;
   @FXML private DatePicker fecha;
   @FXML private ImageView imageViewCargar;
   BDConexion db= BDConexion.getInstance();
   Activo activo= new Activo();
   Ubicacion location= new Ubicacion();
   
  //variables para la generacion de QR
  private static final String FORMATO_IMAGEN = "png"; 
  private static String RUTA_IMAGEN = System.getProperty("user.home"); 
  private static String RUTA_IMAGEN2 = System.getProperty("user.home"); 
  private static final int ancho = 500; 
  private static final int alto = 500; 
  private static final int ancho2 = 500; 
  private static final int alto2 = 70; 
  private static String datos = "";
  File imgFile=null;
   
   @FXML
   public void ingresarActivo(ActionEvent event) throws IOException {
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
      if(ubicacion.getValue().toString().startsWith("Bodega"))
      { activo.setEstadogeneral("DISPONIBLE");}
      else {activo.setEstadogeneral("EN USO");}
      if(imgFile!= null)
      {activo.setImagenactivo(this.convertirImagen(imgFile));}
     // activo.setCodigoqr(this.convertirImagen());
      em2.getTransaction().begin();
      em2.persist(activo);
      em2.getTransaction().commit();
      em2.close();//cerrar el objeto del entitymanager
      emf.close();//cerrar la conexion a la base de datos
      Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Activo Ingresado Exitosamente");
        alert.setHeaderText(null);
        alert.setContentText("Codigo Generado: "+activo.getIdactivo());
        
        ButtonType buttonTypeOne = new ButtonType("IMPRIMIR CODIGO");
        ButtonType buttonTypeAceptar = new ButtonType("Aceptar", ButtonData.YES);
        alert.getButtonTypes().setAll(buttonTypeOne,buttonTypeAceptar);
        Optional<ButtonType> result= alert.showAndWait();
        
        
        if (result.get()==buttonTypeOne){
        //imprimir la etiqueta
            System.out.println("Imprimir imagen");  
           crearPNG();
        }
        generarQR();
        refresh(); 
        RUTA_IMAGEN=System.getProperty("user.home"); 
        RUTA_IMAGEN2=System.getProperty("user.home");
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

    private void generarQR()   {
    BitMatrix bm = null;
        Writer writer= new QRCodeWriter();
        datos="Codigo de Activo: "+activo.getIdactivo()+"\n"
                +"Rubro de Activo: "+activo.getIdrubro().getIdrubro()+"\n"
                + "Nombre de Activo: "+activo.getNombreactivo()+"\n"
                + "Descripcion de Activo: "+activo.getDescripcionactivo()+" \n"
                + "Ubicacion de Activo: "+ubicacion.getValue()+"\n"
                + "Estado de Activo: "+activo.getEstadogeneral();
           
               
                
        try {
            bm=writer.encode(datos, BarcodeFormat.QR_CODE, ancho, alto);
        } catch (WriterException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    BufferedImage image = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB); 
       for (int y = 0; y < ancho; y++) { 
       for (int x = 0; x < alto; x++) { 
           int grayValue = (bm.get(x, y) ? 1 : 0) & 0xff; 
           image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF)); 
       } 
    } 
       image= invertirColores(image,ancho,alto);
       File dir = new File(RUTA_IMAGEN, "CodigosQR-SISGE");
        if (!dir.exists() && !dir.mkdirs()) {
        try {
            throw new IOException("Unable to create " + dir.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       RUTA_IMAGEN=dir+ "/"+activo.getIdactivo()+".png";
    FileOutputStream qrCode = null; 
        try {
            qrCode = new FileOutputStream(RUTA_IMAGEN);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try { 
            ImageIO.write(image, FORMATO_IMAGEN, qrCode);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            qrCode.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
     Desktop d = Desktop.getDesktop(); 
        try {
            d.open(new File(RUTA_IMAGEN));
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
       private static  BufferedImage invertirColores(BufferedImage imagen,int anchoX, int altoX) { 
        for (int x = 0; x < anchoX; x++) { 
            for (int y = 0; y < altoX; y++) { 
                int rgb = imagen.getRGB(x, y); 
                if (rgb == -16777216) { 
                    imagen.setRGB(x, y, -1); 
                } else { 
                    imagen.setRGB(x, y, -16777216); 
                } 
            } 
        } 
        return imagen; 
    } 
    
    public void crearPNG() {
        FileOutputStream labels= null;
 try {  
     //creando un directorio para guardar las etiquetas
     File dir= new File(RUTA_IMAGEN2,"Etiquetas-SISGE");
     if (!dir.exists() && !dir.mkdirs()) {
         try {
             throw new IOException("Unable to create " + dir.getAbsolutePath());
         } catch (IOException ex) {
             Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     //generando la imagen 
     BufferedImage image = new BufferedImage(ancho2, alto2, BufferedImage.TYPE_INT_RGB);
     Graphics g = image.getGraphics();
     g.setFont(new Font("TimesRoman", Font.BOLD, 55));
     g.drawString(activo.getIdactivo(),50,50);
 
     RUTA_IMAGEN2=dir+"/"+activo.getIdactivo()+".png";
     labels = new FileOutputStream(RUTA_IMAGEN2);
     image= invertirColores(image,ancho2, alto2);
            try {  
                ImageIO.write(image, "png", labels);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                labels.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Desktop desktop= Desktop.getDesktop();
            try {
                desktop.open(new File(RUTA_IMAGEN2));
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
 } catch (FileNotFoundException ex) {  
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex); 
 } finally {
            try {
                labels.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public byte[] convertirImagen(File fileImg) throws IOException{
    
           
          /*  FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileImg);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum); 
                    System.out.println("read " + readNum + " bytes,");
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //bytes is the ByteArray we need
            byte[] bytes = bos.toByteArray();
            return bytes;*/
     

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
