/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.BDConexion.con;
import gestionactivos.Clases.ActivoC;
import gestionactivos.Clases.Objetos;
import static gestionactivos.GestionActivos.primaryStage;
import gestionactivos.modelo.Activo;
import gestionactivos.modelo.Ubicacion;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

    // Declaramos los ComboBOX
    @FXML
    private ComboBox<String> cmb_Grado;

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

    //Declaracion de la segunda tabla
    @FXML
    private TableColumn clActivo;

    @FXML
    private TableColumn clCantidad;

    @FXML
    private TableView<Objetos> tb_Activos;

    private ObservableList<Objetos> elementos;

    //Declarcion de Objetos que se utilizaran 
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

    private void inicilzarTabla2() {
        clActivo.setCellValueFactory(new PropertyValueFactory<Objetos, String>("nombre"));
        clCantidad.setCellValueFactory(new PropertyValueFactory<Objetos, Integer>("cantidad"));

        elementos = FXCollections.observableArrayList();
        tb_Activos.setItems(elementos);
    }

    @FXML
    void buscar(ActionEvent event) {

        String valor = cmb_Grado.getValue();
        System.out.println("Valor selecionado:" + valor);

        data = FXCollections.observableArrayList();
        elementos = FXCollections.observableArrayList();

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

                //Declarando los objetos visible o invisibles
                btn_Insertar.setDisable(false);
                btn_Eliminar.setDisable(true);
                btn_Modificar.setDisable(true);

                tb_ActPlan.setVisible(true);
                tb_Activos.setVisible(true);
                btn_Insertar.setVisible(true);
                btn_Eliminar.setVisible(true);
                btn_Modificar.setVisible(true);

                //*******************************************
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

        if (valor != null) {
            try {
                String SQL2 = null;

                SQL2 = "Select a.nombreactivo AS nombre, count(a.nombreactivo) AS cantidad\n"
                        + "from activo as a, ubicacion as b\n"
                        + "where a.idubicacion = b.idubicacion\n"
                        + "and b.nombrelugar= '" + valor + "'\n"
                        + "and to_char(a.fechaingres,'YYYY') < to_char(now(),'YYYY')\n"
                        + "GROUP BY a.nombreactivo;";

                ResultSet rs2 = con.createStatement().executeQuery(SQL2);
                while (rs2.next()) {
                    Objetos elemt = new Objetos();

                    elemt.nombre.set(rs2.getString("nombre"));
                    elemt.cantidad.set(Integer.parseInt(rs2.getString("cantidad")));
                    elementos.add(elemt);
                }
                tb_Activos.setItems(elementos);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }

        }

    }

    @FXML
    void insertAct(ActionEvent event) {

        btn_Insertar.setDisable(true);
        btn_Modificar.setDisable(true);
        btn_Eliminar.setDisable(true);

        String valor = cmb_Grado.getValue();
        System.out.println("Valor selecionado:" + valor);

        String accion = btn_Insertar.getText();

        if (cmb_Grado.getValue() != null) {
            ActivoC actTable = getTablaSeleccionada();

            llamarPantalla(valor, accion, actTable);

            refresh();

        } else {

            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Campos Obligatorios no pueden estar vacios");

            alert.showAndWait();

        }

        //Declarando los objetos visible o invisibles
        btn_Insertar.setDisable(false);
        btn_Eliminar.setDisable(true);
        btn_Modificar.setDisable(true);

        tb_ActPlan.setVisible(true);
        tb_Activos.setVisible(true);
        btn_Insertar.setVisible(true);
        btn_Eliminar.setVisible(true);
        btn_Modificar.setVisible(true);

        //*******************************************
    }

    @FXML
    void Modificar(ActionEvent event) {

        btn_Insertar.setDisable(true);
        btn_Modificar.setDisable(true);
        btn_Eliminar.setDisable(true);

        String valor = cmb_Grado.getValue();
        System.out.println("Valor selecionado Ubi:" + valor);

        String accion = btn_Modificar.getText();

        if (cmb_Grado.getValue() != null) {
            ActivoC actTable = getTablaSeleccionada();

            llamarPantalla(valor, accion, actTable);
            refresh();

        } else {

            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Campos Obligatorios no pueden estar vacios");

            alert.showAndWait();

        }

    }

    @FXML
    void Eliminar(ActionEvent event) {

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

    /**
     * Listener de la tabla personas
     */
    private final ListChangeListener<ActivoC> selectorTabla = new ListChangeListener<ActivoC>() {
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

            // Pongo los botones en su estado correspondiente
            btn_Modificar.setDisable(false);
            btn_Eliminar.setDisable(false);
            btn_Insertar.setDisable(false);

        }
    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        //***********************
        btn_Insertar.setVisible(false);
        btn_Eliminar.setVisible(false);
        btn_Modificar.setVisible(false);

        tb_ActPlan.setVisible(false);
        tb_Activos.setVisible(false);

        //***********************
        //VERIFICA SI SE INICIO DE AÑO
        //COMENTAR ESTO PARA MUESTRA
//        Calendar fecha = new GregorianCalendar();
//        int año = fecha.get(Calendar.YEAR);
//        int mes = fecha.get(Calendar.MONTH);
//
//        if (mes != 1 || mes != 2) {
//
//            btn_Buscar.setDisable(true);
//            cmb_Grado.setDisable(true);
//
//        }
        //***********************
        
        ObservableList<String> list = FXCollections.observableArrayList();

        list = db.getUbicacion();
        list.add("Todo");

        cmb_Grado.setItems(list);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(cmb_Grado);

        cmb_Grado.setEditable(false);

        // Inicializamos la tabla
        this.inicializarTabla();

        this.inicilzarTabla2();

        // Seleccionar las tuplas de la tabla de las personas
        final ObservableList<ActivoC> tablaPersonaSel = tb_ActPlan.getSelectionModel().getSelectedItems();
        tablaPersonaSel.addListener(selectorTabla);

        // Inicializamos la tabla con algunos datos 
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

        //Agregar scroll a panel del Centro
        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(true);
//        scrollPane.setVvalue(0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(loader);

//        scrollPane.vvalueProperty().addListener((ObservableValue<? extends Number> ov, 
//            Number old_val, Number new_val) -> {
//                System.out.println(-new_val.doubleValue());
//                
//                
//        }); 
        GestionActivos.rootPane.setCenter(scrollPane);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);

    }

    public void llamarPantalla(String ubicacion, String accion, ActivoC seleccion) {
        Stage stage = new Stage();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gestionactivos/vistas/InsertActPlan.fxml"));

            Parent root = (Parent) fxmlLoader.load();

            //PARA MANDAR PARAMETROS A LA OTRA PANTALLA
            InsertActPlanController controller = fxmlLoader.<InsertActPlanController>getController();

            controller.setCampos(ubicacion);
            controller.setAccion(accion);
            controller.setActSeleccionado(seleccion);
            //********************************************

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(accion);

//            stage.show();
            stage.showAndWait();

//            stage.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
