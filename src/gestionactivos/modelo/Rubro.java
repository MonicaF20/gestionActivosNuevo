/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionactivos.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MariaJose
 */
@Entity
@Table(name = "rubro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r"),
    @NamedQuery(name = "Rubro.findByIdrubro", query = "SELECT r FROM Rubro r WHERE r.idrubro = :idrubro"),
    @NamedQuery(name = "Rubro.findByNombrerubro", query = "SELECT r FROM Rubro r WHERE r.nombrerubro = :nombrerubro"),
    @NamedQuery(name = "Rubro.findByDescripcionrubro", query = "SELECT r FROM Rubro r WHERE r.descripcionrubro = :descripcionrubro")})
public class Rubro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idrubro")
    private String idrubro;
    @Column(name = "nombrerubro")
    private String nombrerubro;
    @Column(name = "descripcionrubro")
    private String descripcionrubro;
    @JoinColumn(name = "idactivo", referencedColumnName = "idactivo")
    @ManyToOne
    private Activo idactivo;
    @OneToMany(mappedBy = "idrubro")
    private Collection<Activo> activoCollection;

    public Rubro() {
    }

    public Rubro(String idrubro) {
        this.idrubro = idrubro;
    }

    public String getIdrubro() {
        return idrubro;
    }

    public void setIdrubro(String idrubro) {
        this.idrubro = idrubro;
    }

    public String getNombrerubro() {
        return nombrerubro;
    }

    public void setNombrerubro(String nombrerubro) {
        this.nombrerubro = nombrerubro;
    }

    public String getDescripcionrubro() {
        return descripcionrubro;
    }

    public void setDescripcionrubro(String descripcionrubro) {
        this.descripcionrubro = descripcionrubro;
    }

    public Activo getIdactivo() {
        return idactivo;
    }

    public void setIdactivo(Activo idactivo) {
        this.idactivo = idactivo;
    }

    @XmlTransient
    public Collection<Activo> getActivoCollection() {
        return activoCollection;
    }

    public void setActivoCollection(Collection<Activo> activoCollection) {
        this.activoCollection = activoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrubro != null ? idrubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubro)) {
            return false;
        }
        Rubro other = (Rubro) object;
        if ((this.idrubro == null && other.idrubro != null) || (this.idrubro != null && !this.idrubro.equals(other.idrubro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gestionactivos.modelo.Rubro[ idrubro=" + idrubro + " ]";
    }
    
}
