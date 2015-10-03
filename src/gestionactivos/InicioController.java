/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.Usuario;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author MariaJose
 */
public class InicioController extends GestionActivos  implements Initializable {

    /**
     * Initializes the controller class.
     */
   
    
    GestionActivos Principal;
    
   static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("GestionActivosPU");
   static EntityManager em=emf.createEntityManager();

    static String logeado;
          @FXML 
    private TextField usuario;
      @FXML 
      private PasswordField contra;
       @FXML 
      private Label mensajeError;
   @FXML 
      private Button iniciarSesion;
   @FXML 
      private AnchorPane anchorLogeo;
   
       ObservableList menuAdmin= FXCollections.observableArrayList("Ingreso de un Nuevo Activo");
    
     @FXML
    public void setPrincipal(GestionActivos Principal)
    {
        this.Principal=Principal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    }
    
   
    
    
    public void llamarInterfaces(String usuario) throws IOException{
    System.out.print(usuario);
    Parent loader = null; 
    
    Scene newScene; //haciendo referencia al scene de la pagina principal
    newScene = GestionActivos.scene;

    Stage mainWindow; //Haciendo referencia al primaryStage
    mainWindow = GestionActivos.primaryStage;

    mainWindow.setScene(newScene); 
    switch(usuario){
        //llamar todas las opciones disponibles segun el usuario
        case "admin":
            loader = (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menuAdmin.fxml"));
        
            break;
         case "docente":
            loader = (AnchorPane)FXMLLoader.load(getClass().getResource("/gestionactivos/vistas/menudocente.fxml"));
        
            break;
    }
    GestionActivos.rootPane.setCenter(loader);
}
    
    
   
    
  @FXML         
  public  void validarUsuario(){
  
  String usuarioTF=usuario.getText();
  String contraTF=contra.getText();
      try{ //validacion de usuario
em.getTransaction().begin();
Query query=em.createNativeQuery("SELECT * FROM Usuario u WHERE u.nombreusuario ='"+usuarioTF+"' AND u.claveusuario='"+contraTF+"'",Usuario.class);
List results = (List)query.getResultList();
em.getTransaction().commit();

if(results.isEmpty())
{
    mensajeError.setText("ERROR: USUARIO INVALIDO");
    contra.setText("");
    usuario.setOnKeyTyped(new EventHandler<KeyEvent>(){

        @Override
        public void handle(KeyEvent event) {
            mensajeError.setText("");
        }
    });
    
    
    
}else
{
    
    try {
        //em.close();
        logeado=usuarioTF;
        llamarInterfaces(usuarioTF);
    } catch (IOException ex) {
        Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
    
    } 
}

        
    }catch(RuntimeException e){ System.out.println("Caught "+e); } 
  
  
} 

    
public void isEnter(KeyEvent event){
if(event.getCode().equals(KeyCode.ENTER)){
    validarUsuario();
}

}  

  
    
   
    
   
    
}

 


            
            
            
            
    
    

  