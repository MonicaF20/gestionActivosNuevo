/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    BDConexion db = BDConexion.getInstance();

    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GestionActivosPU");
    static EntityManager em = emf.createEntityManager();
    static EntityManager em2 = emf.createEntityManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> list = FXCollections.observableArrayList();

        list = db.getUbicacion();

        cmb_Ubicacion.setItems(list);
        AutoCompleteComboBoxListener combobox = new AutoCompleteComboBoxListener<>(cmb_Ubicacion);

        cmb_Ubicacion.setEditable(false);

    }

}
