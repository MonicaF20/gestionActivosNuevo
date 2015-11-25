/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import static gestionactivos.Bandeja.respuesta;
import static gestionactivos.GestionActivos.primaryStage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static gestionactivos.GestionActivos.respuesta;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class DirectoraController extends GestionActivos implements Initializable/*,Runnable*/ {

    GestionActivos Principal;
    @FXML
    private ImageView reportes;
    @FXML
    private ImageView solicitudes;
    @FXML
    private ImageView planificacion;
    @FXML
    private  Label lblSolicitudes;
    List lista = new ArrayList<>();
    String seleccion = "";
    
    //private Service<Void> backgroundTH;
    private ScheduledService<Void> backgroundTH;
    Boolean continuar=true;
    BDConexion bd= BDConexion.getInstance();
    final String estado="PENDIENTE";
    static String respue="";
   
    //Thread t = new Thread (this);
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // contexto= new ContextMenu();
/*
       
         solicitudes.setOnMouseClicked(new EventHandler(){
         @Override directora
         public void handle(Event event){
         capturarClick(solicitudes);
         }
         });*/
        
//        th.start();
          
          hilo();
        
        
       
        
        planificacion.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                capturarClick(planificacion);
            }
        });
        //opciones del submenu  de Reportes
        final ContextMenu contexto = new ContextMenu();
        MenuItem item1 = new MenuItem("Activos Ingresados");
        MenuItem item2 = new MenuItem("Activos en Reparación");
        MenuItem item3 = new MenuItem("Activos de Baja");
        contexto.getItems().addAll(item1, item2, item3);
        reportes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    contexto.show(reportes, e.getScreenX(), e.getScreenY());
                }
            }
        }); //termina el manejador del evento 
        item1.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                seleccion = "reporteIngreso";
                llamarInterfaces();
            }
        });
        item2.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                seleccion = "reporteRepa";
                llamarInterfaces();
            }
        });
        item3.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                seleccion = "reporteBaja";
                llamarInterfaces();
            }
        });

        //Opciones de Submenu de Solicitudes 
        final ContextMenu cm = new ContextMenu();
        MenuItem sol1 = new MenuItem("Petición de Activos");
        MenuItem sol2 = new MenuItem("Reparación de Activos");
        MenuItem sol3 = new MenuItem("Baja de Activos");
        cm.getItems().addAll(sol1, sol2, sol3);
        solicitudes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY) {
                    cm.show(solicitudes, e.getScreenX(), e.getScreenY());
                }
            }
        }); //termina el manejador del evento

        sol1.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                seleccion = "solicitudpeticion";
                llamarInterfaces();
            }
        });
        sol2.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                seleccion = "solicitudreparacion";
                llamarInterfaces();
            }
        });
        sol3.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                seleccion = "solicitudbaja";
                llamarInterfaces();
            }
        });

    }

    public void setPrincipal(GestionActivos Principal) {
        this.Principal = Principal;
    }

    public void capturarClick(ImageView imageV) {
        seleccion = imageV.getId();
        System.out.println("Esto selecciono: " + seleccion);

        llamarInterfaces();
    }

    public void llamarInterfaces() {
        Parent loader = null;
        AnchorPane loader2 = null;
        AnchorPane loader3 = null;

        Scene newScene; //haciendo referencia al scene de la pagina principal
        newScene = GestionActivos.scene;

        Stage mainWindow; //Haciendo referencia al primaryStage
        mainWindow = GestionActivos.primaryStage;

        mainWindow.setScene(newScene);

//+++++++++++++++++++++++++++++++++   hacer un menú lateral para la Directora 
//cargando el menulateral
        try {
            loader2 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menulateralDirectora.fxml"));
        } catch (Exception e) {
        }
        try {
            loader3 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/lateralDerecho.fxml"));
        } catch (Exception e) {
            System.out.println(e);
        }
        switch (seleccion) {
            case "reporteIngreso":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteIngreso.fxml"));
                    //th.interrupt(); //prueba con nuevo hilo ******
                    continuar=false;
                    primaryStage.setTitle("Reporte Ingreso de Activos");

                } catch (Exception e) {
                }
                break;
            case "reporteRepa":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reportesReparacionAdmin.fxml"));

                    primaryStage.setTitle("Reporte Reparacion de Activos ");

                } catch (Exception e) {
                }
                break;
            case "reporteBaja":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/reporteBaja.fxml"));

                    primaryStage.setTitle("Reporte Baja de Activos");

                } catch (Exception e) {
                }
                break;

            //Vista de Marcela diredi    
            case "planificacion":

                //**********************************************************************************
                //COMENTAR ESTO PARA PRUEBA
                Calendar fecha = new GregorianCalendar();
                int año = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH);
                
                //COMPRUEBA QUE SEA ENERO O FEBRO DEL AÑO PRESENTE
                if (mes != 1 || mes != 2) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No es inicio de año " + año);

                    alert.showAndWait();

                    try {
                        loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuDirectora.fxml"));
                        loader2 = null;
                        loader3 = null;

                        primaryStage.setTitle("Sistema para la Gestion de Activos Fijos");

                    } catch (Exception e) {
                    }

                } else {
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

                }

 //************************************************************************************************************************************************
                //SIN VERIFICAR SI ES INICIO DE AÑO   
//                try {
//                    Parent loader0 = null;
//
//                    loader0 = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/PlanificacionInicio.fxml"));
//
//                    //Para que aparesca el scroll en la pantalla
//                    ScrollPane scrollPane = new ScrollPane();
//                    scrollPane.setFitToWidth(false);
//                    scrollPane.setFitToHeight(true);
//                    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
////                    scrollPane.setPrefSize(500, 1000);
//                    scrollPane.setContent(loader0);
//
//                    loader = scrollPane;
////                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/PlanificacionInicio.fxml"));
//
//                    primaryStage.setTitle("Realizar Planificacion Inicio de Año");
//                } catch (Exception e) {
//                }
                
                
                break;

            case "solicitudreparacion":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesReparacionDirectora.fxml"));

                    primaryStage.setTitle("Solicitudes de Reparación de Activos");

                } catch (Exception e) {
                }
                break;
            case "solicitudpeticion":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesPeticionDirectora.fxml"));

                    primaryStage.setTitle("Solicitud de Peticion de Activos");

                } catch (Exception e) {
                }
                break;
            case "solicitudbaja":
                try {
                    loader = (AnchorPane) FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/solicitudesBajaDirectora.fxml"));

                    primaryStage.setTitle("Solicitud de Baja de Activos");

                } catch (Exception e) {
                }
                break;
        }

        GestionActivos.rootPane.setCenter(loader);
        GestionActivos.rootPane.setLeft(loader2);
        GestionActivos.rootPane.setRight(loader3);

    }

    
//    Thread th =new Thread(new Task(){
//        
//    @Override
//    protected Object call() throws Exception{
//    while(continuar){ 
//       respue= bd.solicitudespendientes(estado);
//       
//        System.out.println("número: "+respue);
//        lblSolicitudes.setText(respue);
//       
//        try {
//			th.sleep( 5000);//milisegundos
//		} catch (InterruptedException ex) {
//			th.currentThread().interrupt();
//		}
//        
//         }
//      return true;
//    }
//    });
        private void hilo(){
        
         /*backgroundTH = new Service<Void>()*/
        backgroundTH  = new ScheduledService<Void>(){
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>(){
            @Override 
            protected Void call()throws Exception{
                
                while(continuar){ 
                  respue= bd.solicitudespendientes(estado);
                  updateMessage("Solicitudes Pendientes : "+" "+respue);//seteando el label 
                  System.out.println("número: "+respue);
                  
                }//while 
                
            return null;
            }
            };
        }
        
       }; //fin service
       
         backgroundTH.setOnSucceeded(new EventHandler <WorkerStateEvent>(){
             @Override
             public void handle(WorkerStateEvent event) {
                 System.out.println("Done!");
             }
         });
         lblSolicitudes.textProperty().bind(backgroundTH.messageProperty());//para recargar el label 
         backgroundTH.restart();
         backgroundTH.setPeriod(Duration.seconds(10));
        
        
        }//fin hilo 

      
        
        

    
    
}
