/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos.Clases;

/**
 *
 * @author Monica
 */
public class ActivoRB {
    private String idactivo;
    private String idrubro;
    private String nombreactivio;
    private String descripcionsol;
    private byte[] imagensolicitud;
    private String fechabaja;

    public String getIdactivo() {
        return idactivo;
    }

    public void setIdactivo(String idactivo) {
        this.idactivo = idactivo;
    }

    public String getIdrubro() {
        return idrubro;
    }

    public void setIdrubro(String idrubro) {
        this.idrubro = idrubro;
    }

    public String getNombreactivio() {
        return nombreactivio;
    }

    public void setNombreactivio(String nombreactivio) {
        this.nombreactivio = nombreactivio;
    }

    public String getDescripcionsol() {
        return descripcionsol;
    }

    public void setDescripcionsol(String descripcionsol) {
        this.descripcionsol = descripcionsol;
    }

    public byte[] getImagensolicitud() {
        return imagensolicitud;
    }
    
    public void setImagensolicitud(byte[] imagensolicitud) {
        this.imagensolicitud = imagensolicitud;
    }
    
    public String getFechabaja() {
        return fechabaja;
    }
    
    public void setFechabaja(String fechabaja) {
        this.fechabaja = fechabaja;
    }
    
    }
