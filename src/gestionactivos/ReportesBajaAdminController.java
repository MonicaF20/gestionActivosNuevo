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
import gestionactivos.Clases.ActivoRB;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Solicitud;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.transform.TransformerException;

/**
 * FXML Controller class
 *
 
 */
public class ReportesBajaAdminController implements Initializable {
    @FXML private TextField txtAnio;
    @FXML private TableView tableReporteBaja;
    @FXML private TableColumn tableColRubro;
    @FXML private TableColumn tableColActivo;
    @FXML private TableColumn tableColNombre;
    @FXML private TableColumn tableColCausaBaja;
    @FXML private Button btnImprimir;
    @FXML private Label lblTituloReporte; 
    
    
   BDConexion db=BDConexion.getInstance();
   Solicitud sol = new Solicitud();
   EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
   EntityManager em=emf.createEntityManager();
   Calendar fecha = Calendar.getInstance();
   Activo activo =new Activo();
    List<ActivoRB> listActivosRB = new ArrayList<ActivoRB>();
//    List<Activo> listActivos = new ArrayList<Activo>();
//    List<Activo> listaDelAño = new ArrayList<Activo>();
    ObservableList<ActivoRB> listaActivosBaja;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtAnio.setText(Integer.toString(fecha.get(Calendar.YEAR)));
        
        em.getTransaction().begin();
        //listActivos=em.createNamedQuery("Activo.findByEstadogeneral",Activo.class).setParameter("estadogeneral","NO DISPONIBLE").getResultList();
        listActivosRB=db.reporteBaja();
        em.getTransaction().commit();
        em.close();
//        for(int i=0; i<listActivos.size();i++){
//           int anio=convertirFechaString(listActivos.get(i).getFechaingres());
//           int anioActual=Integer.parseInt(txtAnio.getText());
//           if(anio==anioActual){
//               //Activos de baja de este año 
//               listaDelAño.add(listActivos.get(i));
//           }
//        }
        listaActivosBaja=FXCollections.observableArrayList(listActivosRB);
        for(int j=0; j<listActivosRB.size();j++){
          tableReporteBaja.setItems(listaActivosBaja);
          tableColActivo.setCellValueFactory(new PropertyValueFactory<>("idactivo"));
          tableColNombre.setCellValueFactory(new PropertyValueFactory<>("nombreactivio"));
          tableColRubro.setCellValueFactory(new PropertyValueFactory<>("idrubro"));
          tableColCausaBaja.setCellValueFactory(new PropertyValueFactory<>("descripcionsol"));
          
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
//           tableColRubro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {
//  
//        @Override
//        public ObservableValue<String> call(TableColumn.CellDataFeatures<Activo, String> param) {
//            SimpleStringProperty sp = new SimpleStringProperty();
//            sp.unbind();
//            sp.bind(new StringBinding() {
//                {
//                param.getValue().getIdrubro();
//                }
//                @Override
//                protected String computeValue() {
//                       return param.getValue().getIdrubro().getIdrubro();
//					}
//                
//            });
//            return sp;
//        }
//		});
        }//for
        
    }    
     public void generarPDF() throws DocumentException, TransformerException, IOException{
        Document document= new Document();
    
       try {
           FileOutputStream fichero= new FileOutputStream("reporteBaja.pdf");
           PdfWriter writer=PdfWriter.getInstance(document, fichero);
           document.open();
          //encabezado
           Image image= Image.getInstance("logo.jpg");
           image.setAlignment(Element.ALIGN_CENTER);
           document.add(image);
           document.addAuthor("Author: SISGE: SISTEMA PARA LA GESTION DE ACTIVOS");
           document.addCreator("Creadores: Grupo 16-DSI215");
           document.addTitle("SISGE: SISTEMA PARA LA GESTION DE ACTIVOS\n REPORTE DE BAJA DE ACTIVOS FIJOS");
         
           document.add(new Paragraph("CENTRO ESCOLAR CATOLICO SAN ANTONIO",FontFactory.getFont(FontFactory.TIMES_BOLD,18,Font.BOLD,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("SISGE-SISTEMA PARA LA GESTION DE ACTIVOS FIJO",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("GENERACION DE REPORTES",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.DARK_GRAY)));
           document.add(new Paragraph("***************************************************************************************************************"));
           document.add(new Paragraph("\n"));
           document.add(new Paragraph(lblTituloReporte.getText(),FontFactory.getFont(FontFactory.TIMES_BOLD,14,BaseColor.BLUE)));
           document.add(new Paragraph("\n\n\n"));
           PdfPTable table= new PdfPTable(4);
           
          // Encabezados
           table.addCell(tableColRubro.getText());
           table.addCell(tableColActivo.getText());
           table.addCell(tableColNombre.getText());
           table.addCell(tableColCausaBaja.getText());
           
           
           
           for(int i=0;i<listActivosRB.size();i++)
           {
               table.addCell(listActivosRB.get(i).getIdrubro());
               table.addCell(listActivosRB.get(i).getIdactivo());
               table.addCell(listActivosRB.get(i).getNombreactivio());
               table.addCell(listActivosRB.get(i).getDescripcionsol());
               
               
           }
           document.add(table);
           document.add(new Paragraph("\n\n\n"));
           //document.add(new Paragraph(lblCuenta.getText()));
           document.add(new Paragraph("\n\n\n"));
           document.add(new Paragraph("Generado: "+ new Date().toString()));
           document.close();
           try {
         File path = new File ("reporteBaja.pdf");
         Desktop.getDesktop().open(path);
}catch (IOException ex) {
     ex.printStackTrace();
}
       } catch (FileNotFoundException ex) {
           Logger.getLogger(ReporteIngresoController.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    
    }
    
    
//    public int convertirFechaString(Date date){
//  
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        String reportDate = df.format(date);
//        int retorna=0;
//     
//        retorna=Integer.parseInt(reportDate.substring(0,4)); 
//        //System.out.println("año:" +retorna);
//         return  retorna;
//      
//     }
    
}
