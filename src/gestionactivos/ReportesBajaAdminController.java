/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.Clases.ActivoRB;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Solicitud;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
