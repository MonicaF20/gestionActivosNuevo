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
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Rubro;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.paint.Color;

import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.transform.TransformerException;

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

   
 
   int modalidad =0,count;
   Date date= new Date();
   SimpleDateFormat smf = new SimpleDateFormat("dd-MM-yyyy");
   Activo activo= new Activo();
   BDConexion db= BDConexion.getInstance();
   List<Activo> results;
   List<String> listaRubros= FXCollections.observableArrayList();
    
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
              modalidad=cmbOpcReporte.getSelectionModel().getSelectedIndex();   
              cargarDatos(modalidad);
          System.out.println(modalidad);}
      });
    ObservableList<String> list2 = FXCollections.observableArrayList();
            list2=db.getRubros();
            cmbBuscarRubros.setItems(list2);
   
            cmbBuscarRubros.setValue(list2.get(0));
            
    this.cargarDatos(0);
 
    }    
    
    public SimpleStringProperty cargarDatos(int modalidad){

   
    switch(modalidad){
        //modalidad 0 es la por defecto: REPORTES DIARIOS
        case 0:
           EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
 EntityManager em= emf.createEntityManager();
      
             em.getTransaction().begin();
             cmbBuscarRubros.setVisible(false);
           dtPickerReporte.setVisible(true);
            
           TypedQuery<Activo> query= em.createNamedQuery("Activo.findByFechaingres",Activo.class).setParameter("fechaingres",date);
           results= query.getResultList();
            em.getTransaction().commit();
            System.out.println(results);
            em.close();
            emf.close();
          
        break;
            //modalidad 3: generar reportes por rubro
       
        case 3:
            lblTituloReporte.setText("REPORTE DE INGRESO DE ACTIVOS SEGUN RUBRO \n"+ cmbBuscarRubros.getValue());
             modalidad=cmbOpcReporte.getSelectionModel().getSelectedIndex();
             EntityManagerFactory emf3= Persistence.createEntityManagerFactory("GestionActivosPU");
             EntityManager em3= emf3.createEntityManager();
             dtPickerReporte.setVisible(false);
             cmbBuscarRubros.setVisible(true);
             
            
            AutoCompleteComboBoxListener combobox= new AutoCompleteComboBoxListener<>(cmbBuscarRubros);
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
            System.out.println(results);
         break;
    }
    for(int i=0;i<results.toArray().length;i++)
    {
        
 
    ObservableList<Activo> listaActivo= FXCollections.observableArrayList(results);
    
   tableReporteIngreso.setItems(listaActivo);
    tableColActivo.setCellValueFactory(new PropertyValueFactory<>("idactivo"));
    tableColNombre.setCellValueFactory(new PropertyValueFactory<>("nombreactivo"));
    tableColDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcionactivo"));
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
}
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
           PdfPTable table= new PdfPTable(4);
           
          // Encabezados
           table.addCell(tableColRubro.getText());
           table.addCell(tableColActivo.getText());
           table.addCell(tableColNombre.getText());
           table.addCell(tableColDescrip.getText());
         
           
           for(int i=0;i<results.size();i++)
           {
               table.addCell(results.get(i).getIdrubro().getIdrubro());
               table.addCell(results.get(i).getIdactivo().toString());
               table.addCell(results.get(i).getNombreactivo().toString());
               table.addCell(results.get(i).getDescripcionactivo().toString());
           }
           document.add(table);
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
}