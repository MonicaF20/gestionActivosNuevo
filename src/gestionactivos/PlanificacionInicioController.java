/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.BDConexion.con;
import gestionactivos.Clases.ActivoC;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Ubicacion;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Marce
 */
public class PlanificacionInicioController implements Initializable {

    @FXML
    private Button btn_Buscar;
    @FXML
    private Button btn_Eliminar;
    @FXML
    private Button btn_Insertar;
    @FXML
    private Button btn_Modificar;
    @FXML
    private Button btn_Aceptar;

    // Declaramos los ComboBOX
    @FXML
    private ComboBox<String> cmb_Grado;
    @FXML
    private ComboBox<String> cmbCategoria;
    @FXML
    private ComboBox<String> cmbUbi;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private ComboBox<String> cmbActivo;

    @FXML
    private Label lbAccion;
    @FXML
    private Label lbActivo;
    @FXML
    private Label lbCategoria;
    @FXML
    private Label lbUbicacion;
    @FXML
    private Label lbEstado;
    @FXML
    private Label lbNueva;
    @FXML
    private Label lbActual;
    @FXML
    private TextField txtUbicacion;

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
    private TableView<ActivoC> tb_ActPlan;

    ObservableList<ActivoC> activos;

    private ObservableList<ActivoC> data;

    ObservableList<String> list1;

    private int posicionEnTabla;

    BDConexion db = BDConexion.getInstance();

    Alert alert = new Alert(Alert.AlertType.ERROR);

    /**
     * Método para inicializar la tabla
     */
    private void inicializarTabla() {
        cl_Estado.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("estado"));
        cl_Nombre.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("nombre"));
        cl_Fecha.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("fechaIngreso"));
        cl_Codigo.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("codigo"));
        cl_Rubro.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("rubro"));
        cl_Ubicacion.setCellValueFactory(new PropertyValueFactory<ActivoC, String>("ubicacion"));

//        activos = FXCollections.observableArrayList();
//        tb_ActInicio.setItems(activos);
        data = FXCollections.observableArrayList();
        tb_ActPlan.setItems(data);
    }

    @FXML
    void buscar(ActionEvent event) {

        String valor = cmb_Grado.getValue();
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
                tb_ActPlan.setItems(data);
//        tableview.setItems(data);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
        } else {

            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una UBICACION");

            alert.showAndWait();

        }

    }

    @FXML
    void insertAct(ActionEvent event) {

        btn_Insertar.setDisable(true);
        btn_Modificar.setDisable(true);
        btn_Eliminar.setDisable(true);

        btn_Aceptar.setVisible(true);

        lbAccion.setVisible(true);
        lbActivo.setVisible(true);
        lbCategoria.setVisible(true);
        lbEstado.setDisable(false);
        lbUbicacion.setVisible(true);

//        txtUbicacion.setVisible(true);
        cmbCategoria.setVisible(true);
        cmbEstado.setDisable(false);
        cmbUbi.setVisible(true);
        cmbActivo.setVisible(true);

        String valor = cmb_Grado.getValue();
        System.out.println("Valor selecionado:" + valor);

        txtUbicacion.setText(valor);
        txtUbicacion.setEditable(false);

        lbAccion.setText(btn_Insertar.getText());

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

    @FXML
    void Modificar(ActionEvent event) {

        btn_Insertar.setDisable(true);
        btn_Modificar.setDisable(true);
        btn_Eliminar.setDisable(true);

        lbAccion.setVisible(true);
//        lbActivo.setDisable(true);
//        lbCategoria.setDisable(true);
//        lbEstado.setDisable(true);
        lbUbicacion.setVisible(true);

        lbActual.setVisible(true);
        lbNueva.setVisible(true);

        txtUbicacion.setVisible(true);
        txtUbicacion.setEditable(false);

//        cmbCategoria.setDisable(false);
//        cmbEstado.setDisable(false);
        cmbUbi.setVisible(true);
//        cmbActivo.setDisable(false);

        btn_Aceptar.setVisible(true);

        lbAccion.setText(btn_Modificar.getText());

        ObservableList<String> list = FXCollections.observableArrayList();

        list = db.getUbicacion();

        cmbUbi.setItems(list);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(cmbUbi);

        cmbUbi.setEditable(false);

        String valor = cmbUbi.getValue();
        System.out.println("Valor selecionado Ubi:" + valor);
//
//        txtUbicacion.setText(valor);
//        txtUbicacion.setEditable(false);

//        ActivoC activo = new ActivoC();
//        activo.ubicacion.set(cmbUbi.getValue());
    }

    @FXML
    void Eliminar(ActionEvent event) {

//        btn_Insertar.setDisable(true);
//        btn_Modificar.setDisable(true);
//        btn_Eliminar.setDisable(true);
//
//        btn_Aceptar.setVisible(true);
//        lbAccion.setVisible(true);
//        lbAccion.setText(btn_Eliminar.getText());
        BDConexion db = BDConexion.getInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
        EntityManager em = emf.createEntityManager();

        final ActivoC activo = getTablaSeleccionada();
        posicionEnTabla = data.indexOf(activo);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText("Esta seguro desea remover el ACTIVO:" + activo.getNombre() + "\n de la UBICACION: " + activo.getUbicacion());

        ButtonType buttonTypeOne = new ButtonType("CANCELAR");
        ButtonType buttonTypeAceptar = new ButtonType("ACEPTAR", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeAceptar);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeAceptar) {
            //cambiar ubicacion a bodega y poner estado=DISPONIBLE
//            final ActivoC actTable = getTablaSeleccionada();
//                    posicionEnTabla = data.indexOf(actTable);
            Ubicacion idrubro = new Ubicacion();
            idrubro.setIdubicacion(23);

            String valor3 = activo.getCodigo();
            System.out.println("IdActivo: " + valor3);

            Activo actOb = new Activo();

            actOb = (Activo) em.createNamedQuery("Activo.findByIdactivo", Activo.class).setParameter("idactivo", valor3).getSingleResult();
            em.getTransaction().begin();
            actOb.setIdubicacion(idrubro);
            actOb.setEstadogeneral("DISPONIBLE");
            em.getTransaction().commit();

            refresh();
        }

    }

    @FXML
    void Aceptar(ActionEvent event) {

        BDConexion db = BDConexion.getInstance();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
        EntityManager em = emf.createEntityManager();

        //***********************
        lbAccion.setVisible(false);
        lbActivo.setVisible(false);
        lbCategoria.setVisible(false);
        lbEstado.setVisible(false);
        lbUbicacion.setVisible(false);

        lbActual.setVisible(false);
        lbNueva.setVisible(false);

        txtUbicacion.setVisible(false);

        cmbCategoria.setVisible(false);
        cmbEstado.setVisible(false);
        cmbUbi.setVisible(false);
        cmbActivo.setVisible(false);

        btn_Aceptar.setVisible(false);

        txtUbicacion.setText("");

        btn_Insertar.setDisable(false);
        btn_Eliminar.setDisable(true);
        btn_Modificar.setDisable(true);

        //***********************
//        Activo actOb = new Activo();
        String opc = lbAccion.getText();
        System.out.println("La accion: " + opc);

        switch (opc) {
            case "Insertar":
                // Actalizar ubiciacion al Insertar
                if (cmbUbi.getValue() != null && cmbCategoria.getValue() != null && cmbActivo.getValue() != null) {

                    String ubiValor = cmbUbi.getValue();
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
                    refresh();
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
                    refresh();
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Campos Obligatorios no pueden estar vacios");

                    alert.showAndWait();
                }
                break;
        }

    }
    /**
     * Listener de la tabla personas
     */
    private final ListChangeListener<ActivoC> selectorTabla
            = new ListChangeListener<ActivoC>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends ActivoC> c) {
                    ponerActivoSeleccionado();
                }
            };

    /**
     * PARA SELECCIONAR UNA CELDA DE LA TABLA "tablaPersonas"
     */
    public ActivoC getTablaSeleccionada() {
        if (tb_ActPlan != null) {
            List<ActivoC> tabla = tb_ActPlan.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final ActivoC competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    /**
     * Método para poner en los textFields la tupla que selccionemos
     */
    private void ponerActivoSeleccionado() {
        final ActivoC activo = getTablaSeleccionada();
        posicionEnTabla = data.indexOf(activo);

        if (activo != null) {

            // Pongo los textFields con los datos correspondientes
            txtUbicacion.setText(activo.getUbicacion());

            // Pongo los botones en su estado correspondiente
            btn_Modificar.setDisable(false);
            btn_Eliminar.setDisable(false);
            btn_Insertar.setDisable(false);
            btn_Aceptar.setVisible(false);
            
            txtUbicacion.setVisible(true);
            
            lbUbicacion.setVisible(true);
            lbCategoria.setVisible(false);
            lbActivo.setVisible(false);
            lbAccion.setVisible(false);
            lbActual.setVisible(false);
            lbNueva.setVisible(false);
            
            cmbUbi.setVisible(false);
            cmbCategoria.setVisible(false);
            cmbActivo.setVisible(false);
            

        }
    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        //***********************
        lbAccion.setVisible(false);
        lbActivo.setVisible(false);
        lbCategoria.setVisible(false);
        lbEstado.setVisible(false);
        lbUbicacion.setVisible(false);

        lbActual.setVisible(false);
        lbNueva.setVisible(false);

        txtUbicacion.setVisible(false);

        cmbCategoria.setVisible(false);
        cmbEstado.setVisible(false);
        cmbUbi.setVisible(false);
        cmbActivo.setVisible(false);

        btn_Aceptar.setVisible(false);
        btn_Eliminar.setDisable(true);
        btn_Modificar.setDisable(true);

        //***********************
        ObservableList<String> list = FXCollections.observableArrayList();

        list = db.getUbicacion();
        list.add("Todo");

        cmb_Grado.setItems(list);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(cmb_Grado);

        cmb_Grado.setEditable(false);

        // Inicializamos la tabla
        this.inicializarTabla();

        // Seleccionar las tuplas de la tabla de las personas
        final ObservableList<ActivoC> tablaPersonaSel = tb_ActPlan.getSelectionModel().getSelectedItems();
        tablaPersonaSel.addListener(selectorTabla);

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
            tb_ActPlan.setItems(data);
//        tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }

    public void refresh() {
        Parent loader = null;
        AnchorPane loader2 = null;
        AnchorPane loader3 = null;

        Scene newScene; //haciendo referencia al scene de la pagina principal
        newScene = GestionActivos.scene;
        Stage mainWindow; //Haciendo referencia al primaryStage
        mainWindow = GestionActivos.primaryStage;
        mainWindow.setScene(newScene);

//cargando el menulateral
        try {
            loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/PlanificacionInicio.fxml"));
            loader2 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
            loader3 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
        }
        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);
    }

}
