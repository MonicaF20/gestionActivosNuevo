/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos.modelo;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MariaJose
 */
@Entity
@Table(name = "activo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activo.findAll", query = "SELECT a FROM Activo a"),
    @NamedQuery(name = "Activo.findByIdactivo", query = "SELECT a FROM Activo a WHERE a.idactivo = :idactivo"),
    @NamedQuery(name = "Activo.findByDescripcionactivo", query = "SELECT a FROM Activo a WHERE a.descripcionactivo = :descripcionactivo"),
    @NamedQuery(name = "Activo.findByFechaingres", query = "SELECT a FROM Activo a WHERE a.fechaingres = :fechaingres"),
    @NamedQuery(name = "Activo.findByEstadogeneral", query = "SELECT a FROM Activo a WHERE a.estadogeneral = :estadogeneral"),
    @NamedQuery(name = "Activo.findByNombreactivo", query = "SELECT a FROM Activo a WHERE a.nombreactivo = :nombreactivo"),
    @NamedQuery(name = "Activo.findAllNombres", query = "SELECT distinct a FROM Activo a"),
    @NamedQuery(name = "Activo.findByUbicacion", query = "SELECT a FROM Activo a WHERE a.idubicacion = :ubicacion"),
    @NamedQuery(name = "Activo.findByIdRubro", query = "SELECT a FROM Activo a WHERE a.idrubro = :idrubro")})
    
public class Activo implements Serializable {
    @Lob
    @Column(name = "imagenactivo")
    private byte[] imagenactivo;
    @Lob
    @Column(name = "codigoqr")
    private byte[] codigoqr;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idactivo")
    private String idactivo;
    @Column(name = "descripcionactivo")
    private String descripcionactivo;
    @Column(name = "fechaingres")
    @Temporal(TemporalType.DATE)
    private Date fechaingres;
    @Column(name = "estadogeneral")
    private String estadogeneral;
    @Column(name = "nombreactivo")
    private String nombreactivo;
    @OneToMany(mappedBy = "idactivo")
    private Collection<Rubro> rubroCollection;
    @OneToMany(mappedBy = "idactivo")
    private Collection<Ubicacion> ubicacionCollection;
    @JoinColumn(name = "idrubro", referencedColumnName = "idrubro")
    @ManyToOne
    private Rubro idrubro;
    @JoinColumn(name = "idubicacion", referencedColumnName = "idubicacion")
    @ManyToOne
    private Ubicacion idubicacion;

    public Activo() {
    }

    public Activo(String idactivo) {
        this.idactivo = idactivo;
    }

    public String getIdactivo() {
        return idactivo;
    }

    public void setIdactivo(String idactivo) {
        this.idactivo = idactivo;
    }

    public String getDescripcionactivo() {
        return descripcionactivo;
    }

    public void setDescripcionactivo(String descripcionactivo) {
        this.descripcionactivo = descripcionactivo;
    }

    public Date getFechaingres() {
        return fechaingres;
    }

    public void setFechaingres(Date fechaingres) {
        this.fechaingres = fechaingres;
    }

    public String getEstadogeneral() {
        return estadogeneral;
    }

    public void setEstadogeneral(String estadogeneral) {
        this.estadogeneral = estadogeneral;
    }

    public String getNombreactivo() {
        return nombreactivo;
    }

    public void setNombreactivo(String nombreactivo) {
        this.nombreactivo = nombreactivo;
    }

    @XmlTransient
    public Collection<Rubro> getRubroCollection() {
        return rubroCollection;
    }

    public void setRubroCollection(Collection<Rubro> rubroCollection) {
        this.rubroCollection = rubroCollection;
    }

    @XmlTransient
    public Collection<Ubicacion> getUbicacionCollection() {
        return ubicacionCollection;
    }

    public void setUbicacionCollection(Collection<Ubicacion> ubicacionCollection) {
        this.ubicacionCollection = ubicacionCollection;
    }

    public Rubro getIdrubro() {
        return idrubro;
    }

    public void setIdrubro(Rubro idrubro) {
        this.idrubro = idrubro;
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
        hash += (idactivo != null ? idactivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activo)) {
            return false;
        }
        Activo other = (Activo) object;
        if ((this.idactivo == null && other.idactivo != null) || (this.idactivo != null && !this.idactivo.equals(other.idactivo))) {
            return false;
        }
        return true;
    }
public byte[] getImagenactivo() {
        return imagenactivo;
    }

    public void setImagenactivo(byte[] imagenactivo) {
        this.imagenactivo = imagenactivo;
    }

    public byte[] getCodigoqr() {
        return codigoqr;
    }

    public void setCodigoqr(byte[] codigoqr) {
        this.codigoqr = codigoqr;
    }
    
    @Override
    public String toString() {
        return "gestionactivos.modelo.Activo[ idactivo=" + idactivo + " ]";
    }

   

    
}
