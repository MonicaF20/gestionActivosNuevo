/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

import gestionactivos.Clases.ActivoRB;
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
import gestionactivos.modelo.Ubicacion;

/**
 *
 * @author USER
 */
public class BDConexion {

    String driver = "org.postgresql.Driver";
    String url = "jdbc:postgresql://localhost:5432/activosfijos";
    String usr = "postgres";
    String pwd = "root";
    static Connection con = null;
    final Object lock = new Object();

    // Singleton
    protected static final BDConexion _INSTANCE = new BDConexion();

    public static BDConexion getInstance() {
        return _INSTANCE;
    }

    protected BDConexion() {
        try {
            if (con == null) {
                System.setProperty("postgresql.database.forceDatabaseLock", "true");
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(url, usr, pwd);
            }
        } catch (Exception e) {

            e.printStackTrace();
            System.exit(0);

        }

        if (con != null) {
            System.out.println("Database connected");
        } else {
            System.out.println("Database NOT connected");
        }
    }

    public boolean isConnected() {
        if (con != null) {
            return true;
        }
        return false;
    }

    public void disconnect() {
        try {
            // Close the active connection
            if (isConnected()) {
                con.commit();
                con.close();
            }

            // Shutdown Derby - the exception contains the shutdown message
            try {
                con = DriverManager.getConnection("jdbc:postgresql:;shutdown=true", usr, pwd);
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // consultas a continuacion
    public ObservableList<String> getRubros() {
        ObservableList<String> rubros = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select nombrerubro from rubro ORDER BY nombrerubro";

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                rubros.add(rs.getString("nombrerubro"));
                //System.out.println(rs.getString("nombrerubro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rubros;
    }

    public ObservableList<String> getUbicacion() {
        ObservableList<String> ubicaciones = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select nombrelugar from ubicacion";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ubicaciones.add(rs.getString("nombrelugar"));
                //System.out.println(rs.getString("nombrerubro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ubicaciones;
    }

    public String generarCodigo(String rubro) {
        String idrubro, idactivo, ultimo = null, respuesta = null, primero = null;
        Statement stmt0, stmt;
        int codConver, contador;
        String query0 = "SELECT idrubro FROM rubro WHERE nombrerubro='" + rubro + "';";

        try {
            stmt0 = con.createStatement();
            ResultSet rs0 = stmt0.executeQuery(query0);
            while (rs0.next()) {
                idrubro = rs0.getString("idrubro");
                try {
                    String query = "SELECT idactivo FROM activo WHERE idrubro='" + idrubro + "' ORDER BY idactivo DESC LIMIT 1; ";
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        idactivo = rs.getString("idactivo");
                        //separando CSAR0XA---XXX
                        primero = idactivo.substring(0, 7);
                        ultimo = idactivo.substring(7, idactivo.length());
                        codConver = Integer.parseInt(ultimo);
                        codConver++;
                        respuesta = Integer.toString(codConver);
                        switch (respuesta.length()) {
                            case 1:
                                respuesta = primero + "00" + respuesta;
                                break;
                            case 2:
                                respuesta = primero + "0" + respuesta;
                                break;
                        }

                    }// fin del if, entonces no hay activo aun registrado para ese rubro, por lo tanto se genera el primer codigo
                    else {
                        respuesta = idrubro + "A001";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;

    }
    
    
    
    
    public String generarCodigoSolictudActivo(String codigoletras){
      String idsolicitud,ultimo = null,respuesta = null,primero = null;
      Statement stmt0,stmt;
      int codConver,contador;
      String query="SELECT idsolicitud FROM solicitud WHERE tiposolicitud='"+codigoletras+"' ORDER BY idsolicitud DESC LIMIT 1;;";
     
      try{
      stmt=con.createStatement();
      ResultSet rs=stmt.executeQuery(query);
      if(rs.next())
      {   idsolicitud=rs.getString("idsolicitud");
        //separando SAC---XXX
          primero=idsolicitud.substring(0,3);
          //System.out.println("muestra  las sigals:" +primero);
          ultimo=idsolicitud.substring(3,idsolicitud.length()); 
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
      else {respuesta=codigoletras+"001";}
      } catch ( Exception e ) {
            e.printStackTrace();
      }
    
      
         /*catch ( Exception e ) {
            e.printStackTrace();
      }*/
      return respuesta;
      
      
}

    ObservableList<String> getIdactivo() {
        ObservableList<String> idactivos = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select idactivo from activo;";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idactivos.add(rs.getString("idactivo"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idactivos;
    }
    
    
    
    ObservableList<String> getSolicitudPeticionActivo() {
        ObservableList<String> idsolicitudes = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select idsolicitud from solicitud where tiposolicitud='SAC' and estadosolicitud='PENDIENTE'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idsolicitudes.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idsolicitudes;
    }
   
    ObservableList<String> getSolicitudReparacionActivo() {
        ObservableList<String> idsolicitudes = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select idsolicitud from solicitud where tiposolicitud='SAR' and estadosolicitud='PENDIENTE'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idsolicitudes.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idsolicitudes;
    }

     ObservableList<String> getSolicitudReparacionActivoAdmin() {
        ObservableList<String> idsolicitudes = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select idsolicitud from solicitud where tiposolicitud='SAR' and estadosolicitud='ACEPTADA'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idsolicitudes.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idsolicitudes;
    }
     List<Solicitud> reporteReparacion() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.imagensolicitud,s.nombreactivo,s.idactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='ACEPTADA' and s.tiposolicitud='SAR';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
      List<Solicitud> reporteReparacion1() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.imagensolicitud,s.nombreactivo,s.idactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='REALIZADA' and s.tiposolicitud='SAR';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
      
      List<Solicitud> reporteReparacion2() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.imagensolicitud,s.nombreactivo,s.idactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='RECHAZADA' and s.tiposolicitud='SAR';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
      List<Solicitud> reporteReparacion3() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.imagensolicitud,s.nombreactivo,s.idactivo,s.idsolicitud  from solicitud s  where s.estadosolicitud='PENDIENTE' and s.tiposolicitud='SAR';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 System.out.println(rs.getString("imagensolicitud"));
                 solicitud.setImagenSolicitud(rs.getBytes("imagensolicitud"));
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
     
     
     ObservableList<String> getSolicitudPeticionActivoAdmin() {
        ObservableList<String> idsolicitudes = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select idsolicitud from solicitud where tiposolicitud='SAC' and estadosolicitud='ACEPTADA'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idsolicitudes.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idsolicitudes;
    }
     
     
     
    /// Marcela 
    String getIdRubro(String rubro) {
        Statement stmt = null;
        String resul = "";
//         ObservableList<String> resultado = FXCollections.observableArrayList();
        System.out.println("Valor recibido:" + rubro);
        String query = "Select idrubro \n"
                + "from rubro \n"
                + "where nombrerubro = '" + rubro + "'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                resul = rs.getString("idrubro");
                System.out.println("Encontrado:" + resul);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resul;
    }

    ObservableList<String> getNomActivo(String idRubro) {
        ObservableList<String> activos = FXCollections.observableArrayList();
        Statement stmt = null;

        String query = "Select idactivo, nombreactivo\n"
                + "from activo\n"
                + "where idrubro = '" + idRubro + "' and estadogeneral = 'DISPONIBLE' \n"
                + "and to_char(fechaingres,'YYYY') < to_char(now(),'YYYY')";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                activos.add(rs.getString("idactivo")+":"+rs.getString("nombreactivo"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activos;
    }
    
    

    Ubicacion getIdUbicacion(String nomUbicacion) {
        Statement stmt = null;
        Ubicacion resul = new Ubicacion();
        String idubicacion;
//         ObservableList<String> resultado = FXCollections.observableArrayList();
//        System.out.println("Valor recibido:"+rubro);
        String query = "Select idubicacion\n"
                + "from ubicacion\n"
                + "where nombrelugar = '"+nomUbicacion+"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idubicacion = rs.getString("idubicacion");
                resul.setIdubicacion(Integer.parseInt(idubicacion));
//                System.out.println("Encontrado:"+resul);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resul;
    }
    
    //Consultas para Baja de solicitud 
    String solicitudespendientes(String estado){
        Statement stmt = null;
        String resul = null;
        String query ="Select count(estadosolicitud) as contar from solicitud where estadosolicitud='"+estado+"';";
        try{
            stmt=con.createStatement();
            ResultSet rs= stmt.executeQuery(query);
            while(rs.next()){
            resul= rs.getString("contar");
                //System.out.println("resultado"+resul);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       return resul;
    }
   String getNomUbicacion(int idUbicacion) {
        Statement stmt = null;
        String resul="";
        String idubicacion = String.valueOf(idUbicacion);
//         ObservableList<String> resultado = FXCollections.observableArrayList();
//        System.out.println("Valor recibido:"+rubro);
        String query = "Select nombrelugar\n"
                + "from ubicacion\n"
                + "where  idubicacion = '"+idUbicacion+"'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                resul = rs.getString("nombrelugar");
//                resul.setIdubicacion(Integer.parseInt(idubicacion));
//                System.out.println("Encontrado:"+resul);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resul;        
}
   
     ObservableList<String> getSolicitudesBaja() {
        ObservableList<String> solicitudBaja = FXCollections.observableArrayList();
        Statement stmt = null;

        String query = "select idsolicitud from solicitud where tiposolicitud='SBA' and estadosolicitud='PENDIENTE'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                solicitudBaja.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudBaja;
    }
   
      ObservableList<String> getSolicitudesBajaTodas() {
        ObservableList<String> solicitudBaja = FXCollections.observableArrayList();
        Statement stmt = null;

        String query = "select idsolicitud from solicitud where tiposolicitud='SBA'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                solicitudBaja.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudBaja;
    }
      
      
      ObservableList<String> getSolicitudReparacionActivoAll() {
        ObservableList<String> solicitudBaja = FXCollections.observableArrayList();
        Statement stmt = null;

        String query = "select idsolicitud from solicitud where tiposolicitud='SAR'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                solicitudBaja.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudBaja;
    }
      
      
      ObservableList<String> getSolicitudPeticionActivoAll() {
        ObservableList<String> solicitudBaja = FXCollections.observableArrayList();
        Statement stmt = null;

        String query = "select idsolicitud from solicitud where tiposolicitud='SAC'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                solicitudBaja.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudBaja;
    }
      
      
      ObservableList<String> getSolicitudesBajaAcept() {
        ObservableList<String> solicitudBaja = FXCollections.observableArrayList();
        Statement stmt = null;

        String query = "select idsolicitud from solicitud where tiposolicitud='SBA' and estadosolicitud='ACEPTADA'";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                solicitudBaja.add(rs.getString("idsolicitud"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudBaja;
    }
      List<ActivoRB> reporteBaja() {
        List<ActivoRB> solicitudBaja = new ArrayList<ActivoRB>();
        
        Statement stmt = null;

        String query = "select a.idactivo ,a.nombreactivo,a.idrubro,s.descripcionsolicitud,s.imagensolicitud,rb.fechabaja from activo a join solicitud s on s.idactivo=a.idactivo join registrodeactivobaja rb on s.idsolicitud=rb.idsolicitud where a.estadogeneral='NO DISPONIBLE' and s.estadosolicitud='EJECUTADA';";
                
                //"select a.idactivo ,a.nombreactivo,a.idrubro,s.descripcionsolicitud,s.fecharegistrasoli,s.imagensolicitud from activo a join solicitud s on s.idactivo=a.idactivo where a.estadogeneral='NO DISPONIBLE' and s.estadosolicitud='EJECUTADA';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ActivoRB activo= new ActivoRB();
                activo.setIdactivo(rs.getString("idactivo"));
                activo.setIdrubro(rs.getString("idrubro"));
                activo.setNombreactivio(rs.getString("nombreactivo"));
                activo.setDescripcionsol(rs.getString("descripcionsolicitud"));
                activo.setImagensolicitud(rs.getBytes("imagensolicitud"));
                activo.setFechabaja(rs.getString("fechabaja"));
                solicitudBaja.add(activo);
                //System.out.println(idactivos);

            }
            //System.out.println("numero de activos"+solicitudBaja.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudBaja;
    }
      
      String solicitudespendientesAdmin(String estado){
        Statement stmt = null;
        String resul = null;
        String query ="Select count(estadosolicitud) as contar from solicitud where estadosolicitud='"+estado+"' or estadosolicitud='RECHAZADA' ;";
        try{
            stmt=con.createStatement();
            ResultSet rs= stmt.executeQuery(query);
            while(rs.next()){
            resul= rs.getString("contar");
                //System.out.println("resultado"+resul);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
       return resul;
    }

      List<Solicitud> reportePeticionSolicitudes() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.nombreactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='ASIGNADO' and s.tiposolicitud='SAC';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                 // solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 //solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
      
      
      List<Solicitud> reportePeticionSolicitudesAceptada() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.nombreactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='ACEPTADA' and s.tiposolicitud='SAC';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                 // solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 //solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
      
      
      
      
      List<Solicitud> reportePeticionSolicitudesRechazada() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.nombreactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='RECHAZADA' and s.tiposolicitud='SAC';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                 // solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 //solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
      
      
      List<Solicitud> reportePeticionSolicitudesPendiente() {
        List<Solicitud> solicitudrepa = new ArrayList<Solicitud>();
        
        Statement stmt = null;

        String query = "select s.descripcionsolicitud,s.fecharegistrasoli,s.nombresolicitante,s.nombreactivo,s.idsolicitud from solicitud s  where s.estadosolicitud='PENDIENTE' and s.tiposolicitud='SAC';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solicitud solicitud= new Solicitud();
                
                 // solicitud.setIdactivo(rs.getString("idactivo"));
                 solicitud.setDescripcionsolicitud(rs.getString("descripcionsolicitud"));
                 solicitud.setFecharegistrasoli(rs.getDate("fecharegistrasoli") );
                 solicitud.setNombresolicitante(rs.getString("nombresolicitante"));
                 solicitud.setIdsolicitud(rs.getString("idsolicitud"));
                 solicitud.setNombreactivo(rs.getString("nombreactivo"));
                 //solicitud.setImagenSolicitud(rs.getBytes("imagenSolicitud"));
                 
                
                solicitudrepa.add(solicitud);
                //System.out.println(idactivos);

            }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudrepa;
    }
         ObservableList<String> getIdactivoUso() {
        ObservableList<String> idactivos = FXCollections.observableArrayList();
        Statement stmt = null;
        String query = "select idactivo from activo where estadogeneral='EN USO';";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                idactivos.add(rs.getString("idactivo"));
                //System.out.println(idactivos);

            }
            //System.out.println(idactivos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idactivos;
    }   
}
