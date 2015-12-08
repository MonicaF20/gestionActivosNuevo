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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class ReporteBajaController implements Initializable {

    @FXML
    private Button btnImprimir;
    @FXML
    private TableColumn tableColCodigoS;
    @FXML
    private TableColumn tableColNombreS;
    @FXML
    private TableColumn tableColDescripcion;
    @FXML
    private TableColumn tableColNombre;
    @FXML
    private TableColumn tableColCodigoA;
    @FXML
    private TableColumn tableColFechaIn;
    @FXML
    private TableView tableReporteDirectora;
    @FXML
    private ComboBox<String> cbOpciones;
    @FXML
    private Label lblTituloReporte;
    @FXML
    private Label lblNota;

    BDConexion db = BDConexion.getInstance();
    Solicitud solb = new Solicitud();
    int modalidad, rowIndex;
    List<Solicitud> listasol = new ArrayList<Solicitud>();
    ObservableList<Solicitud> listaSolicitud;
    byte[] imagenMostrar;

    private ObservableList<String> opciones = FXCollections.observableArrayList("Aceptadas", "Bajas realizadas", "Rechazadas", "Pendientes");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbOpciones.setItems(opciones);
        cbOpciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                modalidad = cbOpciones.getSelectionModel().getSelectedIndex();
                cargarDatos(modalidad);
                System.out.println(modalidad);
            }
        });
       
        btnImprimir.setOnAction(new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                  try {
                      generarPDF();
                  } catch (DocumentException ex) {
                      Logger.getLogger(ReportesBajaAdminController.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (TransformerException ex) {
                      Logger.getLogger(ReportesBajaAdminController.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (IOException ex) {
                      Logger.getLogger(ReportesBajaAdminController.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
          });
        tableReporteDirectora.setOnMouseClicked(new EventHandler<MouseEvent>() {
       
           @Override
           public void handle(MouseEvent event) {
               
               if(event.getClickCount()==2)
               {
               rowIndex= tableReporteDirectora.getSelectionModel().getSelectedIndex();
               imagenMostrar=listasol.get(rowIndex).getImagenSolicitud();
               if(imagenMostrar==null){
               String nota=" IMAGEN DE ACTIVO NO DISPONIBLE";
               lblNota.setText(nota);
               }
               else{
               try {
                   lblNota.setText("");
                   ConvertirImagen(imagenMostrar);
                 } catch (IOException ex) {
                   Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
               }
               }//else
               }//if
           }//mouse event 
       });
        
    }

    public SimpleStringProperty cargarDatos(int modalidad) {

        switch (modalidad) {

            //modalidad 0 es la por defecto: REPORTES DIARIOS
            case 0:
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();

                listasol = em.createNamedQuery("Solicitud.findByEstadoAndTipo", Solicitud.class).setParameter("estadosolicitud", "ACEPTADA")
                        .setParameter("tiposolicitud", "SBA").getResultList();
                if (listasol.isEmpty()) {
                    System.out.println("entro");
                    if(tableReporteDirectora.getItems().isEmpty()){
                         sinDatos();
                    }
                    else {     
                       tableReporteDirectora.getItems().removeAll(listaSolicitud);
                    }
                }
                em.getTransaction().commit();
                em.close();

                listaSolicitud = FXCollections.observableArrayList(listasol);

                break;

            case 1:
                EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("GestionActivosPU");
                EntityManager em1 = emf1.createEntityManager();
                em1.getTransaction().begin();

                listasol = em1.createNamedQuery("Solicitud.findByEstadoAndTipo", Solicitud.class).setParameter("estadosolicitud", "EJECUTADA")
                        .setParameter("tiposolicitud", "SBA").getResultList();
                if (listasol.isEmpty()) {
                    System.out.println("entro");
                    if(tableReporteDirectora.getItems().isEmpty()){
                         sinDatos();
                    }
                    else {     
                       tableReporteDirectora.getItems().removeAll(listaSolicitud);
                    }

                }
                em1.getTransaction().commit();
                em1.close();

                listaSolicitud = FXCollections.observableArrayList(listasol);

                break;
            case 2:
                EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("GestionActivosPU");
                EntityManager em2 = emf2.createEntityManager();
                em2.getTransaction().begin();

                listasol = em2.createNamedQuery("Solicitud.findByEstadoAndTipo", Solicitud.class).setParameter("estadosolicitud", "RECHAZADA")
                        .setParameter("tiposolicitud", "SBA").getResultList();
                if (listasol.isEmpty()) {
                    System.out.println("entro");
                   if(tableReporteDirectora.getItems().isEmpty()){
                         sinDatos();
                    }
                    else {     
                       tableReporteDirectora.getItems().removeAll(listaSolicitud);
                    }

                }
                em2.getTransaction().commit();
                em2.close();

                listaSolicitud = FXCollections.observableArrayList(listasol);

                break;
            case 3:
                EntityManagerFactory emf3 = Persistence.createEntityManagerFactory("GestionActivosPU");
                EntityManager em3 = emf3.createEntityManager();
                em3.getTransaction().begin();

                listasol = em3.createNamedQuery("Solicitud.findByEstadoAndTipo", Solicitud.class).setParameter("estadosolicitud", "PENDIENTE")
                        .setParameter("tiposolicitud", "SBA").getResultList();
                if (listasol.isEmpty()) {

                    if(tableReporteDirectora.getItems().isEmpty()){
                         sinDatos();
                    }
                    else {     
                       tableReporteDirectora.getItems().removeAll(listaSolicitud);
                    }

                }
                em3.getTransaction().commit();
                em3.close();

                listaSolicitud = FXCollections.observableArrayList(listasol);

                break;
//        }
        }

        for (int i = 0; i < listasol.size(); i++) {

            tableReporteDirectora.setItems(listaSolicitud);

            tableColCodigoA.setCellValueFactory(new PropertyValueFactory<>("idactivo"));
            tableColNombre.setCellValueFactory(new PropertyValueFactory<>("nombreactivo"));
            tableColDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionsolicitud"));
            tableColFechaIn.setCellValueFactory(new PropertyValueFactory<>("fecharegistrasoli"));
            tableColCodigoS.setCellValueFactory(new PropertyValueFactory<>("idsolicitud"));
            tableColNombreS.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));

        }

        return null;
    }

    public void generarPDF() throws DocumentException, TransformerException, IOException {
        Document document = new Document();

        try {
            FileOutputStream fichero = new FileOutputStream("reporteBaja.pdf");
            PdfWriter writer = PdfWriter.getInstance(document, fichero);
            document.open();
            //encabezado
            Image image = Image.getInstance("logo.jpg");
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);
            document.addAuthor("Author: SISGE: SISTEMA PARA LA GESTION DE ACTIVOS");
            document.addCreator("Creadores: Grupo 16-DSI215");
            document.addTitle("SISGE: SISTEMA PARA LA GESTION DE ACTIVOS\n REPORTE DE BAJA DE ACTIVOS FIJOS");

            document.add(new Paragraph("CENTRO ESCOLAR CATOLICO SAN ANTONIO", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD, BaseColor.DARK_GRAY)));
            document.add(new Paragraph("SISGE-SISTEMA PARA LA GESTION DE ACTIVOS FIJO", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.DARK_GRAY)));
            document.add(new Paragraph("GENERACION DE REPORTES", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, BaseColor.DARK_GRAY)));
            document.add(new Paragraph("***************************************************************************************************************"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(lblTituloReporte.getText(), FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLUE)));
            document.add(new Paragraph("\n\n\n"));
            PdfPTable table = new PdfPTable(6);

            // Encabezados
            table.addCell(tableColCodigoS.getText());
            table.addCell(tableColCodigoA.getText());
            table.addCell(tableColNombre.getText());
            table.addCell(tableColNombreS.getText());
            table.addCell(tableColFechaIn.getText());
            table.addCell(tableColDescripcion.getText());

            for (int i = 0; i < listasol.size(); i++) {
                table.addCell(listasol.get(i).getIdsolicitud());
                table.addCell(listasol.get(i).getIdactivo());
                table.addCell(listasol.get(i).getNombreactivo());
                table.addCell(listasol.get(i).getNombresolicitante());
                table.addCell(listasol.get(i).getFecharegistrasoli().toString());
                table.addCell(listasol.get(i).getDescripcionsolicitud());

            }
            document.add(table);
            document.add(new Paragraph("\n\n\n"));
            //document.add(new Paragraph(lblCuenta.getText()));
            document.add(new Paragraph("\n\n\n"));
            document.add(new Paragraph("Generado: " + new Date().toString()));
            document.close();
            try {
                File path = new File("reporteBaja.pdf");
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//generar pdf 
    
    private void ConvertirImagen(byte[] bytes) throws IOException {
	// convert byte array back to BufferedImage
       /*  ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
         BufferedImage bufferedImage = ImageIO.read(bis);
         WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
         FileOutputStream labels= null;
         try {  */
        //creando un directorio para guardar las etiquetas
        String ruta = System.getProperty("user.home");
        File dir = new File(System.getProperty(ruta, "ActivosImagenes"));
        if (!dir.exists() && !dir.mkdirs()) {
            try {
                throw new IOException("Unable to create " + dir.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ruta = dir + ".png";

        if (bytes != null) {
            try {

                InputStream in = new ByteArrayInputStream(bytes);
                BufferedImage bImageFromConvert = ImageIO.read(in);
                ImageIO.write(bImageFromConvert, "png", new File(ruta));

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        if (bytes != null) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(ruta));

        }
    }//CONVERTIR IMAGEN 
    
    private void sinDatos(){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("No se encontraron datos");
                        alert.showAndWait();
     
    }
}
