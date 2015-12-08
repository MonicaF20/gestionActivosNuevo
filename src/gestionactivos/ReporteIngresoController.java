/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.svg.graphic.Circle;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Rubro;
import java.awt.Desktop;
import java.awt.Font;
import static java.awt.SystemColor.desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.transform.TransformerException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
/**
 * FXML Controller class
 *
 * @author Valery
 */
public class ReporteIngresoController implements Initializable {

   @FXML  private Label lblTituloReporte;
   @FXML private ComboBox<String> cmbOpcReporte;
   @FXML private TableView tableReporteIngreso;
   @FXML private Button btnImprimir;
   @FXML  private TableColumn tableColRubro;
   @FXML  private TableColumn tableColActivo;
   @FXML  private TableColumn tableColNombre;
   @FXML  private TableColumn tableColDescrip;
   @FXML private DatePicker dtPickerReporte;
   @FXML private ComboBox<String> cmbBuscarRubros;
   @FXML private ComboBox<String> cmbMeses;
   @FXML private TableColumn tableColEstado;
   @FXML private TableColumn tableColFecha;
   @FXML private TextField txtAnio;
   @FXML private ComboBox cmbNombre;
    @FXML private Label lblCuenta;
   @FXML private Label lblNota;
    EntityManagerFactory emf4= Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em4= emf4.createEntityManager();
    List<Activo> nombres=FXCollections.observableArrayList();
    List<String> nombresAux=FXCollections.observableArrayList();
   ObservableList<Activo> listaActivo;
   Calendar fecha = Calendar.getInstance();
   Calendar fecha2 = Calendar.getInstance();
   int modalidad =0,count, rowIndex;
   Date date= new Date();
   SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy");
   Activo activo= new Activo();
   BDConexion db= BDConexion.getInstance();
   List<Activo> results;
   List<String> listaRubros= FXCollections.observableArrayList();
   List<Activo> mesAux;
    SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
    String cuenta="Cantidad de Registros: ";
    byte[] imagenMostrar;
    private ObservableList<String> opciones= FXCollections.observableArrayList("Reporte Diario","Reporte Mensual",
                                                                                "Reporte Anual",
                                                                                "Por Rubro",
                                                                                "Por Activo"); 
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       dtPickerReporte=ConvertidorFecha.ConvertidorFecha(dtPickerReporte);
      cmbOpcReporte.setItems(opciones);
      cmbOpcReporte.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<String>() {
      
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
              modalidad= cmbOpcReporte.getSelectionModel().getSelectedIndex();   
              cargarDatos(modalidad);
          System.out.println(modalidad);}
      });
    ObservableList<String> list2 = FXCollections.observableArrayList();
            list2=db.getRubros();
            cmbBuscarRubros.setItems(list2);
   
            cmbBuscarRubros.setValue(list2.get(0));
            
    ObservableList<String> meses= FXCollections.observableArrayList();
    String[] elements= {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto",
    "Septiembre","Octubre","Noviembre","Diciembre"};
           meses.addAll(elements);
           cmbMeses.setItems(meses);
           cmbMeses.setValue(elements[fecha.get(Calendar.MONTH)]);
            
     cmbMeses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            
            
            cargarDatos(1);
           }
       });

    cmbMeses.setOnMouseClicked(new EventHandler() {

           @Override
           public void handle(Event event) {
            limpiarTabla();
           }
       });
    
    txtAnio.setText(Integer.toString(fecha2.get(Calendar.YEAR)));
    txtAnio.textProperty().addListener(new ChangeListener<String>() {

          
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
              limpiarTabla();
              if(newValue.length()==4)
              {
              cargarDatos(2);}
           }
 
    } );
    
    em4.getTransaction().begin();
    TypedQuery<Activo> query4=em4.createNamedQuery("Activo.findAllNombres",Activo.class);
    nombres= query4.getResultList();
            em4.close();
            for(int i=0;i<nombres.size();i++)
            {
                nombresAux.add(nombres.get(i).getNombreactivo());
            }
            ObservableList<String> nombresO=FXCollections.observableArrayList(nombresAux);
            cmbNombre.setItems(nombresO);
    
   cmbNombre.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               lblNota.setText("");
               cargarDatos(4);
           }
       });
              
   tableReporteIngreso.setOnMouseClicked(new EventHandler<MouseEvent>() {
       
           @Override
           public void handle(MouseEvent event) {
               lblNota.setText("");
               if(event.getClickCount()==2)
               {rowIndex= tableReporteIngreso.getSelectionModel().getSelectedIndex();
               imagenMostrar=results.get(rowIndex).getImagenactivo();
               if(imagenMostrar==null){
            String nota=" IMAGEN DE ACTIVO NO DISPONIBLE";
            lblNota.setText(nota);}else{
               try {
                    lblNota.setText("");
                   ConvertirImagen(imagenMostrar);
               } catch (IOException ex) {
                   Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
               }
               }
               }
           }
       });
   

dtPickerReporte.valueProperty().addListener(new ChangeListener<LocalDate>(){

           @Override
           public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
               limpiarTabla();
               cargarDatos(0);
           }
       });

   
              this.cargarDatos(0);
    }
    
 public SimpleStringProperty cargarDatos(int modalidad){
   //   DateFormat df = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss");
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    switch(modalidad){
     
        //modalidad 0 es la por defecto: REPORTES DIARIOS
        case 0:
            String fechaString=dtPickerReporte.getValue().toString();
           System.out.print("entro a modalidad 0");
           System.out.print(dtPickerReporte.getValue());
           System.out.println(date);
      {
          try {
              date= df.parse(fechaString);
          } catch (ParseException ex) {
              Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
           EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
           EntityManager em= emf.createEntityManager();
           txtAnio.setVisible(false);
           cmbBuscarRubros.setVisible(false);
          txtAnio.setVisible(false);
          cmbNombre.setVisible(false);
          cmbMeses.setVisible(false);
           dtPickerReporte.setVisible(true);  
           
         
           em.getTransaction().begin();
           TypedQuery<Activo> query= em.createNamedQuery("Activo.findByFechaingres",Activo.class).setParameter("fechaingres", date);
           results= query.getResultList();
            em.getTransaction().commit();
            
            em.close();
            emf.close();
            listaActivo= FXCollections.observableArrayList(results);
        break;
            //reporte mensual
         case 1:
          
          mesAux=new ArrayList<Activo>();
          lblTituloReporte.setText("REPORTE DE INGRESO DE ACTIVOS SEGUN MES DE "+cmbMeses.getValue().toUpperCase());
          cmbBuscarRubros.setVisible(false);
          dtPickerReporte.setVisible(false);
          txtAnio.setVisible(false);
          cmbNombre.setVisible(false);
          cmbMeses.setVisible(true);
          EntityManagerFactory emf1= Persistence.createEntityManagerFactory("GestionActivosPU");
          EntityManager em1= emf1.createEntityManager();
          em1.getTransaction().begin();  
          TypedQuery<Activo> query1= em1.createNamedQuery("Activo.findAll",Activo.class);
          results= query1.getResultList();
          em1.getTransaction().commit();
          em1.close();
          emf1.close();
          //System.out.println(results.size());
            for (int i=0; i<results.size();i++)
            {//procedo a separar los activos del mes seleccionado
        
               int mes= convertirFechaString(results.get(i).getFechaingres(),1);//obtengo el mes de cada objeto activo de la base
               int mesCmb=cmbMeses.getSelectionModel().getSelectedIndex()+1;
                //AHORA a dividir la lista results en una que solo tengo los activos del mes recien calculado
                if(mes==mesCmb){
                //SI COINCIDEN ENTONCES SON DEL MES SELECCIONADO EN EL COMBOBOX, 
                    //PROCEDE AGREGAR A LA LISTA AUXILIAR PARA LUEGO DESPLEGAR EN TABLA
                       mesAux.add(results.get(i));
                }
            }
         results=mesAux;
          listaActivo= FXCollections.observableArrayList(results);
            
        break;
         case 2: // busquedas anuales
          mesAux=new ArrayList<Activo>();
          lblTituloReporte.setText("REPORTE DE INGRESO DE ACTIVOS PARA AÑO "+txtAnio.getText());
          cmbBuscarRubros.setVisible(false);
          dtPickerReporte.setVisible(true);
          cmbMeses.setVisible(false);
          cmbNombre.setVisible(false);
          txtAnio.setVisible(true);
          
          EntityManagerFactory emf2= Persistence.createEntityManagerFactory("GestionActivosPU");
          EntityManager em2= emf2.createEntityManager();
          em2.getTransaction().begin();  
          TypedQuery<Activo> query2= em2.createNamedQuery("Activo.findAll",Activo.class);
          results= query2.getResultList();
          em2.close();
          emf2.close();
          for (int i=0; i<results.size();i++)
            {//procedo a separar los activos por el año que esta en el datepicker
        
               int anio= convertirFechaString(results.get(i).getFechaingres(),2);//obtengo el año de cada objeto activo de la base
               int anioDP=Integer.parseInt(txtAnio.getText());
               System.out.println("Año del Date picker: "+anioDP);
                //AHORA a dividir la lista results en una que solo tengo los activos del mes recien calculado
                if(anio==anioDP){
                //SI COINCIDEN ENTONCES SON DEL MES SELECCIONADO EN EL COMBOBOX, 
                    //PROCEDE AGREGAR A LA LISTA AUXILIAR PARA LUEGO DESPLEGAR EN TABLA
                       mesAux.add(results.get(i));
                }
            }
       
          listaActivo= FXCollections.observableArrayList(mesAux);
         break;
            //modalidad 3: generar reportes por rubro
            
        case 3:
            lblTituloReporte.setText("REPORTE DE INGRESO DE ACTIVOS SEGUN RUBRO \n"+ cmbBuscarRubros.getValue());
             modalidad=cmbOpcReporte.getSelectionModel().getSelectedIndex();
             EntityManagerFactory emf3= Persistence.createEntityManagerFactory("GestionActivosPU");
             EntityManager em3= emf3.createEntityManager();
            dtPickerReporte.setVisible(false);
            cmbMeses.setVisible(false);
            txtAnio.setVisible(false);
            cmbNombre.setVisible(false);
            cmbBuscarRubros.setVisible(true);
            
            System.out.println(cmbBuscarRubros.getValue());
            em3.getTransaction().begin();
            Query query3=em3.createNativeQuery("SELECT r.idrubro FROM Rubro r  WHERE r.nombrerubro=?",Rubro.class).setParameter(1,cmbBuscarRubros.getValue());
            Rubro  results3= (Rubro)query3.getSingleResult();
            em3.getTransaction().commit();
            em3.clear();
            em3.getTransaction().begin();
            System.out.println(results3);
            TypedQuery<Activo> query31=em3.createNamedQuery("Activo.findByIdRubro",Activo.class).setParameter("idrubro",results3);
             results= query31.getResultList();
            em3.getTransaction().commit();
            if(results.isEmpty()){limpiarTabla();}
            System.out.println(results);
            listaActivo= FXCollections.observableArrayList(results);
         break;
            
        case 4: //por nombre de activo
           
            lblTituloReporte.setText("REPORTE DE INGRESO DE ACTIVOS: ");
             modalidad=cmbOpcReporte.getSelectionModel().getSelectedIndex();
            EntityManager em41= emf4.createEntityManager();
            dtPickerReporte.setVisible(false);
            cmbMeses.setVisible(false);
            txtAnio.setVisible(false);
            cmbBuscarRubros.setVisible(false);
            cmbNombre.setVisible(true);
            
            TypedQuery<Activo> query41=em41.createNamedQuery("Activo.findByNombreactivo",Activo.class).setParameter("nombreactivo",cmbNombre.getValue());
            results= query41.getResultList();
            if(results.isEmpty()){limpiarTabla();}
            System.out.println(results);
            em41.close();
            listaActivo= FXCollections.observableArrayList(results);
            
            break;
    }
    
   lblCuenta.setText(cuenta+listaActivo.size());
   for(int i=0;i<results.size();i++)
    {
      
  
  
   tableReporteIngreso.setItems(listaActivo);
 
   
   
    tableColActivo.setCellValueFactory(new PropertyValueFactory<>("idactivo"));
    tableColNombre.setCellValueFactory(new PropertyValueFactory<>("nombreactivo"));
    tableColDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcionactivo"));
   
    tableColEstado.setCellValueFactory(new PropertyValueFactory<>("estadogeneral"));
    tableColRubro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {
  
        @Override
        public ObservableValue<String> call(CellDataFeatures<Activo, String> param) {
            SimpleStringProperty sp = new SimpleStringProperty();
            sp.unbind();
            sp.bind(new StringBinding() {
                {
                param.getValue().getIdrubro();
                }
                @Override
                protected String computeValue() {
                       return param.getValue().getIdrubro().getIdrubro();
					}
                
            });
            return sp;
        }
		});
    
     tableColFecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {
  
        @Override
        public ObservableValue<String> call(CellDataFeatures<Activo, String> param) {
            SimpleStringProperty sp = new SimpleStringProperty();
            sp.unbind();
            sp.bind(new StringBinding() {
                {
                param.getValue().getIdrubro();
                }
                @Override
                protected String computeValue() {
                       return param.getValue().getIdrubro().getIdrubro();
					}
                
            });
            return sp;
        }
		});
    
    
    tableColFecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {

       @Override
     public ObservableValue<String> call(CellDataFeatures<Activo, String> param) {
          SimpleStringProperty sp = new SimpleStringProperty();
           sp.unbind();
            sp.bind(new StringBinding() {
                {
              // param.getValue().getFechaingres();
                }
                @Override
                protected String computeValue() {
                    ;
                       return formatter.format(param.getValue().getFechaingres());
					}
                
            });
            return sp;
       }
   });
    

    
    }//fin del if
        
       return null;
    
    }
    
    
    
    public void count(ActionEvent event)
    {
        
        cargarDatos(modalidad);
    }
    
    public void generarPDF(ActionEvent event) throws DocumentException, TransformerException, IOException{
        Document document= new Document();
    
       try {
           FileOutputStream fichero= new FileOutputStream("reporteIngreso.pdf");
           PdfWriter writer=PdfWriter.getInstance(document, fichero);
           document.open();
          //encabezado
           Image image= Image.getInstance("logo.jpg");
           image.setAlignment(Element.ALIGN_CENTER);
           document.add(image);
           document.addAuthor("Author: SISGE: SISTEMA PARA LA GESTION DE ACTIVOS");
           document.addCreator("Creadores: Grupo 16-DSI215");
           document.addTitle("SISGE: SISTEMA PARA LA GESTION DE ACTIVOS\n REPORTE DE INGRESOS DE ACTIVOS FIJOS");
         
           document.add(new Paragraph("CENTRO ESCOLAR CATOLICO SAN ANTONIO",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("SISGE-SISTEMA PARA LA GESTION DE ACTIVOS FIJO",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("GENERACION DE REPORTES",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("***************************************************************************************************************"));
           document.add(new Paragraph("\n"));
           document.add(new Paragraph(lblTituloReporte.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,14,BaseColor.BLUE)));
           document.add(new Paragraph("\n\n\n"));
           PdfPTable table= new PdfPTable(6);
           
          // Encabezados
           table.addCell(tableColRubro.getText());
           table.addCell(tableColActivo.getText());
           table.addCell(tableColNombre.getText());
           table.addCell(tableColDescrip.getText());
           table.addCell(tableColFecha.getText());
           table.addCell(tableColEstado.getText());
           
           
           for(int i=0;i<results.size();i++)
           {
               table.addCell(results.get(i).getIdrubro().getIdrubro());
               table.addCell(results.get(i).getIdactivo().toString());
               table.addCell(results.get(i).getNombreactivo().toString());
               table.addCell(results.get(i).getDescripcionactivo().toString());
               table.addCell(formatter.format(results.get(i).getFechaingres()));
               table.addCell(results.get(i).getEstadogeneral());
               
           }
           document.add(table);
           document.add(new Paragraph("\n\n\n"));
           document.add(new Paragraph(lblCuenta.getText()));
           document.add(new Paragraph("\n\n\n"));
           document.add(new Paragraph("Generado: "+ new Date().toString()));
           document.close();
           try {
         File path = new File ("reporteIngreso.pdf");
         Desktop.getDesktop().open(path);
}catch (IOException ex) {
     ex.printStackTrace();
}
       } catch (FileNotFoundException ex) {
           Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    
    }


   
     private void ConvertirImagen(byte[] bytes) throws IOException {
	// convert byte array back to BufferedImage
       /*  ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	 BufferedImage bufferedImage = ImageIO.read(bis);
        WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
         FileOutputStream labels= null;
 try {  */
     //creando un directorio para guardar las etiquetas
         String ruta=System.getProperty("user.home");
        File dir= new File(System.getProperty(ruta,"ActivosImagenes"));
     if (!dir.exists() && !dir.mkdirs()) {
         try {
             throw new IOException("Unable to create " + dir.getAbsolutePath());
         } catch (IOException ex) {
             Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     ruta=dir+".png";
    
         if(bytes!= null){
             try{
                 
         InputStream in = new ByteArrayInputStream(bytes);
	 BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "png", new File(ruta));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
         }
      
         if(bytes!= null){
          Desktop desktop= Desktop.getDesktop();
            desktop.open(new File(ruta));
         
         }
 }

     public void limpiarTabla(){
        // System.out.println("Numero de filas: "+ tableReporteIngreso.getItems().size());
            lblNota.setText("");
             tableReporteIngreso.getItems().removeAll(listaActivo);
             lblCuenta.setText(cuenta+0);
     
     }
     
     public int convertirFechaString(Date date,int caso){
  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = df.format(date);
        int retorna=0;
     // System.out.println("Report Date: " + reportDate);
      //System.out.println("Report Date: " + reportDate.substring(5, 7));
    //  System.out.println("Report Date: " + Integer.parseInt(reportDate.substring(5, 7)));
       switch(caso)
       {
           case 1: //retorne el mes
               retorna=Integer.parseInt(reportDate.substring(5, 7));
           break;
           case 2://retorne el año
              retorna=Integer.parseInt(reportDate.substring(0,4)); 
               //System.out.println("año:" +retorna);
       }
      return  retorna;
      
     }

    public void mostrarImagen(int index){
  //  System.out.println("Row Index "+ index);
       
    }
}
