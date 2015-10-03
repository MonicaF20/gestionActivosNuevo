/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Rubro;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Valery
 */
public class IngresarRubroController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private Label lblCodigoGenerado; 
    @FXML private Label lblCodRubro;
    @FXML private TextField txtNombreRubro;
    @FXML private TextArea txtDescripRubro;
    @FXML private Button btnIngresarRubro;
    Alert alert =new Alert(AlertType.ERROR);
    EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
    EntityManager em= emf.createEntityManager();
    Rubro rubro= new Rubro();
    List<Rubro> listasRubros=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void ingresarRubro(){
    if(txtNombreRubro.getText().length()>0 && txtDescripRubro.getText().length()>0)
    {
        listasRubros= em.createNamedQuery("Rubro.findByNombrerubro",Rubro.class).setParameter("nombrerubro",txtNombreRubro.getText().toUpperCase()).getResultList();
        System.out.println(listasRubros);
            if(listasRubros.isEmpty())
            {
                //si esta vacia entonces No existe y se puede Ingresar
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Acción");
                alert.setHeaderText(null);
                alert.setContentText("¿Esta Seguro que desea agregar el siguiente Rubro?\n\n"
                        + "Nombre de Rubro: "+txtNombreRubro.getText().toUpperCase()+"\n"
                        + " Descripcion: "+txtDescripRubro.getText()+"\n\n");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                generarCodigoRubro();
                rubro.setNombrerubro(txtNombreRubro.getText().toUpperCase());
                rubro.setDescripcionrubro(txtDescripRubro.getText());
                em.getTransaction().begin();
                em.persist(rubro);
                em.getTransaction().commit();
                alert.setAlertType(AlertType.INFORMATION);
                alert.setTitle("Ingreso Correcto");
                alert.setHeaderText(null);
                alert.setContentText("Un Nuevo Rubro ha sido agregado correctamente con codigo de Rubro= "+rubro.getIdrubro());
                alert.showAndWait();
                lblCodigoGenerado.setVisible(true);
                lblCodRubro.setText(rubro.getIdrubro());
                lblCodRubro.setVisible(true);
                btnIngresarRubro.setDisable(true);
            }
            }
            else{
            //notificar que el rubro ya existe
                alert.setAlertType(AlertType.INFORMATION);
                alert.setTitle("Error: Rubro Existente");
                alert.setHeaderText(null);
                alert.setContentText("Rubro ya Existe, ingrese otro Nombre de Rubro");
                alert.showAndWait();
            }
      
                }
    else
    {
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("Campos Obligatorios no pueden quedar vacios");
    alert.showAndWait();
    }
    }
    
    public void generarCodigoRubro(){
    EntityManager em2= emf.createEntityManager();
    List maxCod=em2.createNativeQuery("select max(idrubro) from rubro;").getResultList();
   
   String codigoSplit=maxCod.get(0).toString().substring(4,6);
  
    int numeroActivo= Integer.parseInt(codigoSplit)+1;
    
    if(Integer.toString(numeroActivo).length()==1)
    {rubro.setIdrubro("CSAR0"+numeroActivo);}
    else
    {
    rubro.setIdrubro("CSAR"+numeroActivo);
    }

    }
}
