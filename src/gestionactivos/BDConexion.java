/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.modelo.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Collections.list;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author USER
 */
public class BDConexion {
    
  
    
    String driver = "org.postgresql.Driver";
    String url ="jdbc:postgresql://localhost:5432/activosfijos";
    String usr = "postgres";
    String pwd = "root";
    static Connection con = null;
    final Object lock = new Object();

    // Singleton
    protected static final BDConexion _INSTANCE = new BDConexion();
    public static BDConexion getInstance() { return _INSTANCE; }

    protected BDConexion() {
        try {
            if ( con == null ) {
                System.setProperty("postgresql.database.forceDatabaseLock", "true");
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection( url, usr, pwd );
            }
        }
        catch ( Exception e ) {
           
           
                e.printStackTrace();
                System.exit(0);
            
        }

        if ( con != null )
            System.out.println("Database connected");
        else
            System.out.println("Database NOT connected");
    }
  
     public boolean isConnected() {
        if ( con != null )
            return true;
        return false;
    }

    public void disconnect() {
        try {
            // Close the active connection
            if ( isConnected() ) {
                con.commit();
                con.close();
            }

            // Shutdown Derby - the exception contains the shutdown message
            try {
                con = DriverManager.getConnection( "jdbc:postgresql:;shutdown=true", usr, pwd );
            }
            catch ( SQLException sqle ) {
                System.out.println(sqle.getMessage());
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    
  // consultas a continuacion
    

    public ObservableList<String> getRubros() {
       ObservableList<String> rubros = FXCollections.observableArrayList();
         Statement stmt= null;
         String query ="select nombrerubro from rubro ORDER BY nombrerubro";
         
         try {
           stmt=con.createStatement();
           ResultSet rs=stmt.executeQuery(query);
           while(rs.next()){
               rubros.add(rs.getString("nombrerubro"));
               //System.out.println(rs.getString("nombrerubro"));
           }  
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
return rubros;
    }
    
    public  ObservableList<String> getUbicacion(){
    ObservableList<String> ubicaciones = FXCollections.observableArrayList();
         Statement stmt= null;
         String query ="select nombrelugar from ubicacion";
         try {
           stmt=con.createStatement();
           ResultSet rs=stmt.executeQuery(query);
           while(rs.next()){
               ubicaciones.add(rs.getString("nombrelugar"));
               //System.out.println(rs.getString("nombrerubro"));
           }  
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
return ubicaciones;
    } 
    
    
    
  public String generarCodigo(String rubro){
      String idrubro,idactivo,ultimo = null,respuesta = null,primero = null;
      Statement stmt0,stmt;
      int codConver,contador;
      String query0="SELECT idrubro FROM rubro WHERE nombrerubro='"+rubro+"';";
     
      try{
      stmt0=con.createStatement();
      ResultSet rs0=stmt0.executeQuery(query0);
      while(rs0.next())
      {
          idrubro=rs0.getString("idrubro");
         try{
      String query="SELECT idactivo FROM activo WHERE idrubro='"+idrubro+"' ORDER BY idactivo DESC LIMIT 1; ";
      stmt=con.createStatement();
      ResultSet rs=stmt.executeQuery(query);
      if(rs.next())
      {   idactivo=rs.getString("idactivo");
        //separando CSAR0XA---XXX
          primero=idactivo.substring(0,7);
          ultimo=idactivo.substring(7,idactivo.length()); 
          codConver= Integer.parseInt(ultimo);
          codConver++;
          respuesta=Integer.toString(codConver); 
          switch(respuesta.length())
          {
              case 1:
                  respuesta=primero+"00"+respuesta;
               break;
              case 2:
                   respuesta=primero+"0"+respuesta;
                break;
          }
          
         
      }// fin del if, entonces no hay activo aun registrado para ese rubro, por lo tanto se genera el primer codigo
      else {respuesta=idrubro+"A001";}
      } catch ( Exception e ) {
            e.printStackTrace();
      }
    
      }
      } catch ( Exception e ) {
            e.printStackTrace();
      }
      return respuesta;
      
      
  }
  
  ObservableList<String> getIdactivo() {
      ObservableList<String> idactivos = FXCollections.observableArrayList();
         Statement stmt= null;
         String query ="select idactivo from activo;";
         try {
           stmt=con.createStatement();
           ResultSet rs=stmt.executeQuery(query);
           while(rs.next()){
               idactivos.add(rs.getString("idactivo"));
               //System.out.println(idactivos);
              
           }
           //System.out.println(idactivos);
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
return idactivos; 
        }

}

    


    

