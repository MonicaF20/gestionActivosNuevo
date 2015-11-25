/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos;

/**
 *
 * @author Monica
 */
public class Bandeja implements Runnable {
    Boolean continuar=true;
    BDConexion bd= BDConexion.getInstance();
    String estado="PENDIENTE";
    String respuesta="";
    Thread t;
    
    @Override
    public void run(){
       while(continuar){ 
       respuesta= bd.solicitudespendientes(estado);
        System.out.println("número: "+respuesta);
        esperar();
       }
        
    };
    private void esperar(){
      try {
			t.sleep( 5000);//milisegundos
		} catch (InterruptedException ex) {
			t.currentThread().interrupt();
		}
    }
    
}
