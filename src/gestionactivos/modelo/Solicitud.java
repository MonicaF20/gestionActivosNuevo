/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PINTO
 */
@Entity
@Table(name = "solicitud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT s FROM Solicitud s"),
    @NamedQuery(name = "Solicitud.findByFecharegistrasoli", query = "SELECT s FROM Solicitud s WHERE s.fecharegistrasoli = :fecharegistrasoli"),
    @NamedQuery(name = "Solicitud.findByEstadosolicitud", query = "SELECT s FROM Solicitud s WHERE s.estadosolicitud = :estadosolicitud"),
    @NamedQuery(name = "Solicitud.findByDescripcionsolicitud", query = "SELECT s FROM Solicitud s WHERE s.descripcionsolicitud = :descripcionsolicitud"),
    @NamedQuery(name = "Solicitud.findByCantidadsolicitada", query = "SELECT s FROM Solicitud s WHERE s.cantidadsolicitada = :cantidadsolicitada"),
    @NamedQuery(name = "Solicitud.findByTiposolicitud", query = "SELECT s FROM Solicitud s WHERE s.tiposolicitud = :tiposolicitud"),
    @NamedQuery(name = "Solicitud.findByNombresolicitante", query = "SELECT s FROM Solicitud s WHERE s.nombresolicitante = :nombresolicitante"),
    @NamedQuery(name = "Solicitud.findByNombreactivo", query = "SELECT s FROM Solicitud s WHERE s.nombreactivo = :nombreactivo"),
    @NamedQuery(name = "Solicitud.findByIdsolicitud", query = "SELECT s FROM Solicitud s WHERE s.idsolicitud = :idsolicitud")})
public class Solicitud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "fecharegistrasoli")
    @Temporal(TemporalType.DATE)
    private Date fecharegistrasoli;
    @Column(name = "estadosolicitud")
    private String estadosolicitud;
    @Column(name = "descripcionsolicitud")
    private String descripcionsolicitud;
    @Column(name = "cantidadsolicitada")
    private Integer cantidadsolicitada;
    @Column(name = "tiposolicitud")
    private String tiposolicitud;
    @Lob
    //@Column(name = "imagenSolicitud")
    //private byte[] imagenSolicitud;
    
    
    @Column(name = "nombresolicitante")
    private String nombresolicitante;
    
    @Column(name = "nombreactivo")
    private String nombreactivo;
    @Id
    @Basic(optional = false)
    @Column(name = "idsolicitud")
    private String idsolicitud;

    
    @JoinColumn(name = "idubicacion", referencedColumnName = "idubicacion")
    @ManyToOne
    private Ubicacion idubicacion;
    public Solicitud() {
    }

    public Solicitud(String idsolicitud) {
        this.idsolicitud = idsolicitud;
    }

    public Solicitud(String idsolicitud, Date fecharegistrasoli) {
        this.idsolicitud = idsolicitud;
        this.fecharegistrasoli = fecharegistrasoli;
    }

    public Date getFecharegistrasoli() {
        return fecharegistrasoli;
    }

    public void setFecharegistrasoli(Date fecharegistrasoli) {
        this.fecharegistrasoli = fecharegistrasoli;
    }

    public String getEstadosolicitud() {
        return estadosolicitud;
    }

    public void setEstadosolicitud(String estadosolicitud) {
        this.estadosolicitud = estadosolicitud;
    }

    public String getDescripcionsolicitud() {
        return descripcionsolicitud;
    }

    public void setDescripcionsolicitud(String descripcionsolicitud) {
        this.descripcionsolicitud = descripcionsolicitud;
    }

    public Integer getCantidadsolicitada() {
        return cantidadsolicitada;
    }

    public void setCantidadsolicitada(Integer cantidadsolicitada) {
        this.cantidadsolicitada = cantidadsolicitada;
    }

    public String getTiposolicitud() {
        return tiposolicitud;
    }

    public void setTiposolicitud(String tiposolicitud) {
        this.tiposolicitud = tiposolicitud;
    }

   /* public byte[] getImagenSolicitud() {
        return imagenSolicitud;
    }

    public void setImagenSolicitud(byte[] imagenSolicitud) {
        this.imagenSolicitud = imagenSolicitud;
    }*/

    public String getNombresolicitante() {
        return nombresolicitante;
    }

    public void setNombresolicitante(String nombresolicitante) {
        this.nombresolicitante = nombresolicitante;
    }

    public String getNombreactivo() {
        return nombreactivo;
    }

    public void setNombreactivo(String nombreactivo) {
        this.nombreactivo = nombreactivo;
    }

    public String getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(String idsolicitud) {
        this.idsolicitud = idsolicitud;
    }
    
    
    public Ubicacion getIdubicacion() {
        return idubicacion;
    }

    public void setIdubicacion(Ubicacion idubicacion) {
        this.idubicacion = idubicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsolicitud != null ? idsolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitud)) {
            return false;
        }
        Solicitud other = (Solicitud) object;
        if ((this.idsolicitud == null && other.idsolicitud != null) || (this.idsolicitud != null && !this.idsolicitud.equals(other.idsolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gestionactivos.modelo.Solicitud[ idsolicitud=" + idsolicitud + " ]";
    }
    
}
