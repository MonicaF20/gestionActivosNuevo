/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.Clases.Objetos;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Ubicacion;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.event.Event;
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
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class ControlInventarioController implements Initializable {

   
   SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
   int rowIndex;
   @FXML Label ubiActual;
    @FXML
    private Button btn_Eliminar;
    @FXML
    private Button btn_Insertar;
    @FXML
    private Button btn_Modificar;

    // Declaramos los ComboBOX
    @FXML
    private ComboBox<String> cmb_Grado;
@FXML
    private ComboBox<String> cmbNueva;
    // Declaramos la tabla y las columnas
    @FXML
    private TableColumn cl_Codigo;
    @FXML
    private TableColumn cl_Rubro;
    @FXML
    private TableColumn cl_Nombre;
    @FXML
    private TableColumn cl_Estado;
    @FXML
    private TableColumn cl_Ubicacion;
    @FXML
    private TableColumn cl_Fecha;

 @FXML
    private TableView<Activo> tb_ActPlan;
    @FXML private Pane paneOculto;
    private ObservableList<Objetos> elementos;

    //Declarcion de Objetos que se utilizaran 
    ObservableList<Activo> activos;

    private ObservableList<Activo> data;

    private int posicionEnTabla;

    BDConexion db = BDConexion.getInstance();

    Alert alert = new Alert(Alert.AlertType.ERROR);
    Ubicacion results;
    Ubicacion results3;
    List<Activo> results2;
   ObservableList<Activo> datos;
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        
       
        ObservableList<String> list = FXCollections.observableArrayList();

        list = db.getUbicacion();
       

        cmb_Grado.setItems(list);
        cmb_Grado.setEditable(false);
        cmb_Grado.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               cargarDatos(cmb_Grado.getSelectionModel().selectedItemProperty().getValue());
               System.out.print(cmb_Grado.getSelectionModel().selectedItemProperty().getValue());
               
           
           }
       });
        
        
        tb_ActPlan.setOnMouseClicked(new EventHandler<MouseEvent>() {
       
           @Override
           public void handle(MouseEvent event) {
               if(event.getClickCount()==2)
               {rowIndex= tb_ActPlan.getSelectionModel().getSelectedIndex();
               paneOculto.setVisible(true);
                  
               ubiActual.setText(datos.get(rowIndex).getIdubicacion().getNombrelugar());
               
             }else{
               
               }
               }
          
       });
  cmbNueva.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                ObservableList<String> list2 = FXCollections.observableArrayList();
                list2=db.getUbicacion();
    
                cmbNueva.setItems(list2); 
            }
        });
      

    }
    
    
    
    
     public void cargarDatos(String modalidad){
         paneOculto.setVisible(false);
        EntityManagerFactory emf1= Persistence.createEntityManagerFactory("GestionActivosPU");
          EntityManager em1= emf1.createEntityManager();
          em1.getTransaction().begin();  
          TypedQuery<Ubicacion> query1= em1.createNamedQuery("Ubicacion.findByNombrelugar",Ubicacion.class).setParameter("nombrelugar", modalidad);
          results= query1.getSingleResult();
         System.out.println("ID DEL LUGAR " +results.getIdubicacion());
        
          EntityManager em2= emf1.createEntityManager();
         TypedQuery<Activo> query2= em2.createNamedQuery("Activo.findByUbicacion",Activo.class).setParameter("ubicacion",results);
          results2= query2.getResultList();
         if(results2.size()==0)
         { 
             limpiarTabla();
             }else
         {datos= FXCollections.observableArrayList(results2);
      
for (int i=0;i<datos.size();i++)
{
        tb_ActPlan.setItems(datos);
          System.out.println("ADENTRO");
      
      cl_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombreactivo"));
     cl_Codigo.setCellValueFactory(new PropertyValueFactory<>("idactivo"));
      cl_Rubro.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {
  
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Activo, String> param) {
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
    
      
      
      //cl_Ubicacion.setCellValueFactory(new PropertyValueFactory<>("idubicacion"));
      cl_Ubicacion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {
  
        @Override
        public ObservableValue<String> call(TableColumn.CellDataFeatures<Activo, String> param) {
            SimpleStringProperty sp = new SimpleStringProperty();
            sp.unbind();
            sp.bind(new StringBinding() {
                {
                param.getValue().getIdubicacion();
                }
                @Override
                protected String computeValue() {
                       return param.getValue().getIdubicacion().getNombrelugar();
					}
                
            });
            return sp;
        }
		});
      
      
      cl_Fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Activo,String>, ObservableValue<String>>() {

       

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Activo, String> param) {
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
      
}
     }
     }
      
     
     
      public void limpiarTabla(){
        if(datos!=null)
        {  tb_ActPlan.getItems().removeAll(datos);}
           
     
     }
     
     public void cancelar(){
     paneOculto.setVisible(false);
     
     }
     
     public void actualizar(){
         
     String lugarNuevo= cmbNueva.getValue();
      if(cmbNueva.getValue()==null)
      {}else{
       //convertir nombre ubicacion a idubicacion
       EntityManagerFactory emf1= Persistence.createEntityManagerFactory("GestionActivosPU");
       EntityManager em1= emf1.createEntityManager();
       EntityManager em= emf1.createEntityManager();
          
       TypedQuery<Ubicacion> query1= em.createNamedQuery("Ubicacion.findByNombrelugar",Ubicacion.class).setParameter("nombrelugar", cmbNueva.getValue());
       results3= query1.getSingleResult();
       em.close();
         TypedQuery<Activo> query2= em1.createNamedQuery("Activo.findByIdactivo",Activo.class).setParameter("idactivo", datos.get(rowIndex).getIdactivo());
         Activo activo =query2.getSingleResult();
         activo.setIdubicacion(results3);
         
      
        em1.getTransaction().begin();
        
        em1.getTransaction().commit();
        em1.close();
        cargarDatos(cmb_Grado.getValue());
     }
     }
    }
    
    
    

