/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.Clases.ActivoC;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Ubicacion;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Marce
 */
public class InsertActPlanController implements Initializable {

    //Declaracion de Labels
    @FXML
    private Label lbUbicacion;
    @FXML
    private Label lbCategoria;
    @FXML
    private Label lbNueva;
    @FXML
    private Label lbActivo;
    @FXML
    private Label lbActual;
    @FXML
    private Label lbAccion;

    @FXML
    private TextField txtUbicacion;

    //Declaracion de Botones
    @FXML
    private Button btn_Aceptar;
    @FXML
    private Button btnCancelar;

    //Declaracion de ComboBox
    @FXML
    private ComboBox<String> cmbActivo;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private ComboBox<String> cmbUbi;

    ObservableList<String> list1;
    BDConexion db = BDConexion.getInstance();
    Alert alert = new Alert(Alert.AlertType.ERROR);

    private ActivoC seleccion = new ActivoC();

    @FXML
    void Aceptar(ActionEvent event) {

        BDConexion db = BDConexion.getInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
        EntityManager em = emf.createEntityManager();

        String opc = lbAccion.getText();
        System.out.println("La accion: " + opc);

        switch (opc) {
            case "Insertar":
                // Actualizar ubiciacion al Insertar
                if (txtUbicacion.getText() != null && cmbCategoria.getValue() != null && cmbActivo.getValue() != null) {

                    String ubiValor = txtUbicacion.getText();
                    System.out.println("Valor Ubicacion: " + ubiValor);
                    Ubicacion idrubro = db.getIdUbicacion(ubiValor);
                    System.out.println("ID Ubicacion: " + idrubro);

                    String valor2 = cmbActivo.getValue();
                    String[] idActSelec = valor2.split(":");
                    System.out.println("IdActivo: " + idActSelec[0]);

                    Activo actOb = new Activo();

                    actOb = (Activo) em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo", idActSelec[0]).getSingleResult();

                    em.getTransaction().begin();
                    actOb.setIdubicacion(idrubro);
                    actOb.setEstadogeneral("En uso");

                    em.getTransaction().commit();

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Insercion Exitosa");
                    alert.setHeaderText(null);
                    alert.setContentText("Se inserto Correctamente");
                    alert.showAndWait();

                    // Cerrar ventana
                    Stage stage = (Stage) btn_Aceptar.getScene().getWindow();
                    // do what you have to do
                    stage.close();
//                    refresh();
                } else {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Campos Obligatorios no pueden estar vacios");

                    alert.showAndWait();

                }

                break;
            case "Modificar":

                System.out.println("Entro Modificar");

                if (cmbUbi.getValue() != null) {

                    String ubiValor = cmbUbi.getValue();
                    System.out.println("Valor Ubicacion: " + ubiValor);
                    Ubicacion idrubro = db.getIdUbicacion(ubiValor);
                    System.out.println("ID Ubicacion: " + idrubro);

                    final ActivoC actTable = getTablaSeleccionada();
//                    posicionEnTabla = data.indexOf(actTable);
                    String valor3 = actTable.getCodigo();
                    System.out.println("IdActivo: " + valor3);

                    Activo actOb = new Activo();

                    actOb = (Activo) em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo", valor3).getSingleResult();

                    if (ubiValor.indexOf("Bodega") != -1) {
                        em.getTransaction().begin();
                        actOb.setIdubicacion(idrubro);
                        actOb.setEstadogeneral("DISPONIBLE");
                        em.getTransaction().commit();

                    } else {
                        em.getTransaction().begin();
                        actOb.setIdubicacion(idrubro);
                        actOb.setEstadogeneral("En uso");
                        em.getTransaction().commit();
                    }

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Insercion Exitosa");
                    alert.setHeaderText(null);
                    alert.setContentText("Se inserto Correctamente");
                    alert.showAndWait();

                    // Cerrar ventana
                    Stage stage = (Stage) btn_Aceptar.getScene().getWindow();
                    // do what you have to do
                    stage.close();

//                    refresh();
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Campos Obligatorios no pueden estar vacios");

                    alert.showAndWait();
                }
                break;
        }

    }

    @FXML
    void Cancelar(ActionEvent event) {

        //Cierra toda la app
//        Platform.exit();
        // get a handle to the stage
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        // do what you have to do
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    public void setCampos(String valor) {

        txtUbicacion.setText(valor);
        txtUbicacion.setEditable(false);

        //Llenado los ComboBox
        ObservableList<String> list0 = FXCollections.observableArrayList();
        list0 = db.getUbicacion();
        cmbUbi.setItems(list0);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(cmbUbi);
        cmbUbi.setEditable(false);

        list1 = FXCollections.observableArrayList();
        list1 = db.getRubros();
        System.out.println("lista" + list1);
        cmbCategoria.setItems(list1);
        AutoCompleteComboBoxListener combobox1 = new AutoCompleteComboBoxListener<>(cmbCategoria);
        cmbCategoria.setEditable(false);

        cmbActivo.setEditable(false);
        cmbCategoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Seleccionado los activo con respecto a la categoria

                ObservableList<String> list2 = FXCollections.observableArrayList();
                String catSelec = cmbCategoria.getValue();
                System.out.println("Categoria seleccionada:" + catSelec);

                String id = db.getIdRubro(catSelec);
                System.out.println("Cod seleccionada:" + id);

                list2 = db.getNomActivo(id);

                System.out.println("lista2:" + list2);

                cmbActivo.setItems(list2);
                AutoCompleteComboBoxListener combobox2 = new AutoCompleteComboBoxListener<>(cmbActivo);
                cmbActivo.setEditable(false);

            }

        });
    }

    public void setAccion(String accion) {
        lbAccion.setText(accion);

        switch (lbAccion.getText()) {
            case "Insertar":

                //Label
                lbActual.setVisible(false);
                lbNueva.setVisible(false);
                //ComboBox
                cmbUbi.setVisible(false);
                break;
            case "Modificar":

                //Labels
                lbActual.setVisible(true);
                lbNueva.setVisible(true);
                lbCategoria.setVisible(false);
                lbActivo.setVisible(false);
                //ComboBox
                cmbCategoria.setVisible(false);
                cmbActivo.setVisible(false);
                break;
        }
    }

    public void setActSeleccionado(ActivoC activoC) {
        seleccion = activoC;
    }

    public ActivoC getTablaSeleccionada() {
        if (seleccion != null) {
            return seleccion;
        }

        return null;
    }

}
