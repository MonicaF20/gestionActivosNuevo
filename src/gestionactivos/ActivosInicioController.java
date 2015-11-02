/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.BDConexion.con;
import gestionactivos.Clases.ActivoC;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Marce
 */
public class ActivosInicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btn_Buscar;

    @FXML
    private ComboBox<String> cmb_Ubicacion;

    // Declaramos la tabla y las columnas
    @FXML
    private TableView<ActivoC> tb_ActInicio;
    @FXML
    private TableColumn cl_Estado;
    @FXML
    private TableColumn cl_ubicacion;
    @FXML
    private TableColumn cl_nombre;
    @FXML
    private TableColumn cl_rubro;
    @FXML
    private TableColumn cl_fecha;
    @FXML
    private TableColumn cl_codigo;

    ObservableList<ActivoC> activos;

    private ObservableList<ActivoC> data;

    private int posicionActivoEnTabla;

    BDConexion db = BDConexion.getInstance();
    
    Alert alert = new Alert(Alert.AlertType.ERROR);

    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    static EntityManager em = emf.createEntityManager();
    static EntityManager em2 = emf.createEntityManager();

    /**
     * Método para inicializar la tabla
     */
    private void inicializarTabla() {
        cl_Estado.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("estado"));
        cl_nombre.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("nombre"));
        cl_fecha.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("fechaIngreso"));
        cl_codigo.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("codigo"));
        cl_rubro.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("rubro"));
        cl_ubicacion.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("ubicacion"));

        activos = FXCollections.observableArrayList();
        tb_ActInicio.setItems(activos);

//        data = FXCollections.observableArrayList();
//        tb_ActInicio.setItems(data);
    }

//    -------------------------------------------
    @FXML
    void btn_buscar(ActionEvent event) {

        String valor = cmb_Ubicacion.getValue();
        System.out.println("Valor selecionado:" + valor);

        data = FXCollections.observableArrayList();
        if (valor != null) {
            try {

                String SQL = null;
                if (valor == "Todo") {
                    SQL = "Select to_char(a.fechaingres,'DD/MM/YYYY') as fecha ,a.idactivo as idActivo,a.idrubro as idRubro,\n"
                            + "a.nombreactivo as nombre,a.estadogeneral as estado, b.nombrelugar as ubicacion\n"
                            + "from activo as a, ubicacion as b\n"
                            + "where a.idubicacion = b.idubicacion\n"
                            + "and to_char(a.fechaingres,'YYYY') < to_char(now(),'YYYY');";

                } else {

                    SQL = "Select to_char(a.fechaingres,'DD/MM/YYYY') as fecha ,a.idactivo as idActivo,a.idrubro as idRubro,\n"
                            + "a.nombreactivo as nombre,a.estadogeneral as estado, b.nombrelugar as ubicacion\n"
                            + "from activo as a, ubicacion as b\n"
                            + "where a.idubicacion = b.idubicacion\n"
                            + "and b.nombrelugar= '" + valor + "' \n"
                            + "and to_char(a.fechaingres,'YYYY') < to_char(now(),'YYYY');";

                }

                ResultSet rs = con.createStatement().executeQuery(SQL);
                while (rs.next()) {
                    ActivoC act = new ActivoC();
//            Usermaster cm = new Usermaster();
                    act.estado.set(rs.getString("estado"));
                    act.nombre.set(rs.getString("nombre"));
                    act.fechaIngreso.set(rs.getString("fecha"));
                    act.codigo.set(rs.getString("idactivo"));
                    act.rubro.set(rs.getString("idrubro"));
                    act.ubicacion.set(rs.getString("ubicacion"));

                    data.add(act);
                }
                tb_ActInicio.setItems(data);
//        tableview.setItems(data);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
        }
        else {

            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una UBICACION");

            alert.showAndWait();

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> list = FXCollections.observableArrayList();

        list = db.getUbicacion();
        list.add("Todo");

        cmb_Ubicacion.setItems(list);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(cmb_Ubicacion);

        cmb_Ubicacion.setEditable(false);

        // Inicializamos la tabla
        this.inicializarTabla();

        // Inicializamos la tabla con algunos datos 
        data = FXCollections.observableArrayList();
        try {
            String SQL = "Select to_char(a.fechaingres,'DD/MM/YYYY') as fecha ,a.idactivo as idActivo,a.idrubro as idRubro,\n"
                    + "a.nombreactivo as nombre,a.estadogeneral as estado, b.nombrelugar as ubicacion\n"
                    + "from activo as a, ubicacion as b\n"
                    + "where a.idubicacion = b.idubicacion\n"
                    + "and to_char(a.fechaingres,'YYYY') < to_char(now(),'YYYY');";
            //Mi consulta         
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while (rs.next()) {
                ActivoC act = new ActivoC();
//            Usermaster cm = new Usermaster();
                act.estado.set(rs.getString("estado"));
                act.nombre.set(rs.getString("nombre"));
                act.fechaIngreso.set(rs.getString("fecha"));
                act.codigo.set(rs.getString("idactivo"));
                act.rubro.set(rs.getString("idrubro"));
                act.ubicacion.set(rs.getString("ubicacion"));

                data.add(act);
            }
            tb_ActInicio.setItems(data);
//        tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

}
