/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.GestionActivos.primaryStage;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class MenulateralDirectoraController implements Initializable {

    @FXML
    private Button actingresado;
    @FXML
    private Button actreparacion;
    @FXML
    private Button actbaja;
    @FXML
    private Button solpeticion;
    @FXML
    private Button solreparacion;
    @FXML
    private Button solbaja;
    @FXML
    private Button replanificacion;
//     @FXML private Button verplanificacion;
    @FXML
    private Label lblRegresar;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actingresado.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(actingresado);

            }

        });

        actbaja.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(actbaja);

            }

        });

        //Marcela agrego esto 
        replanificacion.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(replanificacion);

            }

        });

//        verplanificacion.setOnMouseClicked(new EventHandler() {
//        @Override
//        public void handle(Event event) {
//           itemSelected(verplanificacion);
//           
//       }
//        
//        });
        lblRegresar.setOnMouseClicked(new EventHandler() {

            @Override
            public void handle(Event event) {
                try {
                    regresarMenu();
                } catch (IOException ex) {
                    Logger.getLogger(MenulateralDirectoraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        actreparacion.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(actreparacion);

            }

        });
        solpeticion.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(solpeticion);

            }

        });
        solreparacion.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(solreparacion);

            }
        });
        solbaja.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                itemSelected(solbaja);

            }
        });
    }

    public void itemSelected(Button seleccion) {
//    AnchorPane loader=null;
        Parent loader = null;
        AnchorPane loader2 = null;
        AnchorPane loader3 = null;

        Scene newScene; //referencia al scene de la pagina princiapal
        newScene = GestionActivos.scene;
        //referencia al primaryStage
        Stage mainWindow = GestionActivos.primaryStage;

        mainWindow.setScene(newScene);
        //para cargar los menus Laterales 
        try {
            loader2 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
            loader3 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
        }
        String prueba = seleccion.getText();
        System.out.println("Esto captura: " + prueba);
        switch (seleccion.getText()) {

            case "Activos Ingresados":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteIngreso.fxml"));
                    primaryStage.setTitle("Reporte de Ingreso de Activos");
                } catch (Exception e) {
                }
                break;
            case "Activos de Baja":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesBajaAdmin.fxml"));
                    primaryStage.setTitle("Reporte de Baja de Activos");
                } catch (Exception e) {
                }
                break;

            //Vista de Marce
            case "Realizar Planificación":

                //**********************************************************************************
                //COMENTAR ESTO PARA PRUEBA
                Calendar fecha = new GregorianCalendar();
                int año = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH);

                if (mes != 1 || mes != 2) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                        alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No es inicio de año " + año);

                    alert.showAndWait();

                } //***********************
//                else { //COMETAR ESTE ELSE
                    try {
                        
                        Parent loader0 = null;
                        loader0 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/PlanificacionInicio.fxml"));

                        //Para que aparesca el scroll en la pantalla
                        ScrollPane scrollPane = new ScrollPane();
                        scrollPane.setFitToWidth(false);
                        scrollPane.setFitToHeight(true);
                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        scrollPane.setContent(loader0);

                        loader = scrollPane;
                        primaryStage.setTitle("Realizar Planificacion Inicio de Año");
                    } catch (Exception e) {
                    }
//COMENTAR ESTA "}"
//                }
                //*************************************************************************
                
                //SIN VERIFICAR SI ES INICIO DE AÑO                 
//                try {
//                        
//                        Parent loader0 = null;
//                        loader0 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/PlanificacionInicio.fxml"));
//
//                        //Para que aparesca el scroll en la pantalla
//                        ScrollPane scrollPane = new ScrollPane();
//                        scrollPane.setFitToWidth(false);
//                        scrollPane.setFitToHeight(true);
//                        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                        scrollPane.setContent(loader0);
//
//                        loader = scrollPane;
//                        primaryStage.setTitle("Realizar Planificacion Inicio de Año");
//                    } catch (Exception e) {
//                    }
//                
                //**********************************************************************

                break;

            case "Activos en Reparación":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesReparacionAdmin.fxml"));
                    primaryStage.setTitle("Activos en Reparación");
                } catch (Exception e) {
                }
                break;
            case "Peteción de Activos":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesPeticionDirectora.fxml"));
                    primaryStage.setTitle("Solicitudes de Petición");
                } catch (Exception e) {
                }
                break;
            case "Reparación de Activos":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesReparacionDirectora.fxml"));
                    primaryStage.setTitle("Reparacion de Activos");
                } catch (Exception e) {
                }
                break;
            case "Baja de Activos":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesBajaDirectora.fxml"));
                    primaryStage.setTitle("Solicitudes de Baja");
                } catch (Exception e) {
                }
                break;

        }//fin switch
        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);

    }

    public void regresarMenu() throws IOException {
        AnchorPane loader = null;
        loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuDirectora.fxml"));
        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(null);
        GestionActivos.rootPane.setRight(null);

    }

}
