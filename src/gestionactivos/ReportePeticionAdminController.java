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
import gestionactivos.modelo.Solicitud;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class ReportePeticionAdminController implements Initializable {
    
    @FXML
    private Button btnImprimir;

    @FXML
    private TableColumn tableColNom;

    @FXML
    private TableColumn tableColSolicitud;

    @FXML
    private TableColumn tableColFecha;

    @FXML
    private Label lblCuenta;

    @FXML
    private Label lblNota;

    @FXML
    private TableColumn tableColSolicitante;

    @FXML
    private TableColumn tableColDescripcion;

    @FXML
    private Label lblTituloReporte;

    @FXML
    private TableView tableReporteReparacion;

   /* @FXML
    private TableColumn tableColActivo;*/

    @FXML
    private Label anio;
    
   BDConexion db= BDConexion.getInstance();
   EntityManagerFactory emf4= Persistence.createEntityManagerFactory("GestionActivosPU");
   EntityManager em4= emf4.createEntityManager();
    int modalidad,rowIndex;
    List<Solicitud> listasolicitudes = new ArrayList<Solicitud>();

    ObservableList<Solicitud> listaSolicitud;
    Calendar fecha = Calendar.getInstance();
String cuenta="Cantidad de Registros: ";
    byte[] imagenMostrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         anio.setText(Integer.toString(fecha.get(Calendar.YEAR)));
     anio.setVisible(true);
    EntityManagerFactory emf1= Persistence.createEntityManagerFactory("GestionActivosPU");
   EntityManager em1=emf1.createEntityManager();
        em1.getTransaction().begin();
        
        listasolicitudes=db.reportePeticionSolicitudes();
        em1.getTransaction().commit();
        em1.close();

        listaSolicitud=FXCollections.observableArrayList(listasolicitudes);
        
        lblCuenta.setText(cuenta+listaSolicitud.size());
   for(int i=0;i<listasolicitudes.size();i++)
    {
     
  
        
   tableReporteReparacion.setItems(listaSolicitud);
 
   
   
    //tableColActivo.setCellValueFactory(new PropertyValueFactory<>("idactivo"));
    tableColNom.setCellValueFactory(new PropertyValueFactory<>("nombreactivo"));
    tableColDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionsolicitud"));
    tableColFecha.setCellValueFactory(new PropertyValueFactory<>("fecharegistrasoli"));
    tableColSolicitud.setCellValueFactory(new PropertyValueFactory<>("idsolicitud"));
    tableColSolicitante.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));
    }
   
   
       /*tableReporteReparacion.setOnMouseClicked(new EventHandler<MouseEvent>() {
       
           @Override
           public void handle(MouseEvent event) {
               lblNota.setText("");
               if(event.getClickCount()==2)
               {rowIndex= tableReporteReparacion.getSelectionModel().getSelectedIndex();
               imagenMostrar=listasolicitudes.get(rowIndex).getImagenSolicitud();
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
       });*/
        // TODO
    }    
    
    
    public void generarPDF(ActionEvent event) throws DocumentException, TransformerException, IOException{
        Document document= new Document();
    
       try {
           FileOutputStream fichero= new FileOutputStream("reporteSolicitudesSolicitadas.pdf");
           PdfWriter writer=PdfWriter.getInstance(document, fichero);
           document.open();
          //encabezado
           Image image= Image.getInstance("logo.jpg");
           image.setAlignment(Element.ALIGN_CENTER);
           document.add(image);
           document.addAuthor("Author: SISGE: SISTEMA PARA LA GESTION DE ACTIVOS");
           document.addCreator("Creadores: Grupo 16-DSI215");
           document.addTitle("SISGE: SISTEMA PARA LA GESTION DE ACTIVOS\n REPORTE DE ACTIVOS FIJOS SOLICITADOS");
         
           document.add(new Paragraph("CENTRO ESCOLAR CATOLICO SAN ANTONIO",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("SISGE-SISTEMA PARA LA GESTION DE ACTIVOS FIJO",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("GENERACION DE REPORTES",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("***************************************************************************************************************"));
           document.add(new Paragraph("\n"));
           document.add(new Paragraph(lblTituloReporte.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,14,BaseColor.BLUE)));
           document.add(new Paragraph("\n\n\n"));
           PdfPTable table= new PdfPTable(5);
           
          // Encabezados
           
           table.addCell(tableColSolicitud.getText());
           table.addCell(tableColNom.getText());
          // table.addCell(tableColActivo.getText());
           table.addCell(tableColSolicitante.getText());
           table.addCell(tableColDescripcion.getText());
           table.addCell(tableColFecha.getText());
          //table.addCell(tableColUbicacion.getText());
              
           
           for(int i=0;i<listasolicitudes.size();i++)
           {
               table.addCell(listasolicitudes.get(i).getIdsolicitud()) ;
               table.addCell(listasolicitudes.get(i).getNombreactivo());
               //table.addCell(listasolicitudes.get(i).getIdactivo());
               table.addCell(listasolicitudes.get(i).getNombresolicitante());
               table.addCell(listasolicitudes.get(i).getDescripcionsolicitud().toString());
               table.addCell(listasolicitudes.get(i).getFecharegistrasoli().toString());
               
               //table.addCell(listasolicitudes.get(i).getIdubicacion().getNombrelugar());
               
               
               
              
               
               
           }
           document.add(table);
           document.add(new Paragraph("\n\n\n"));
           document.add(new Paragraph(lblCuenta.getText()));
           document.add(new Paragraph("\n\n\n"));
           document.add(new Paragraph("Generado: "+ new Date().toString()));
           document.close();
           try {
         File path = new File ("reporteSolicitudesSolicitadas.pdf");
         Desktop.getDesktop().open(path);
}catch (IOException ex) {
     ex.printStackTrace();
}
       } catch (FileNotFoundException ex) {
           Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    
    }
    
    private void ConvertirImagen(byte[] bytes) throws IOException {
	
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
    
}
