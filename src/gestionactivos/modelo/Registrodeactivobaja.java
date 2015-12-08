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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Monica
 */
@Entity
@Table(name = "registrodeactivobaja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registrodeactivobaja.findAll", query = "SELECT r FROM Registrodeactivobaja r"),
    @NamedQuery(name = "Registrodeactivobaja.findByIdbaja", query = "SELECT r FROM Registrodeactivobaja r WHERE r.idbaja = :idbaja"),
    @NamedQuery(name = "Registrodeactivobaja.findByCausabaja", query = "SELECT r FROM Registrodeactivobaja r WHERE r.causabaja = :causabaja"),
    @NamedQuery(name = "Registrodeactivobaja.findByFechabaja", query = "SELECT r FROM Registrodeactivobaja r WHERE r.fechabaja = :fechabaja")})
public class Registrodeactivobaja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbaja")
    private Integer idbaja;
    @Column(name = "causabaja")
    private String causabaja;
    @Column(name = "fechabaja")
    @Temporal(TemporalType.DATE)
    private Date fechabaja;
    @Column(name = "idsolicitud")
    private String idsolicitud;

    public Registrodeactivobaja() {
    }

    public Registrodeactivobaja(Integer idbaja) {
        this.idbaja = idbaja;
    }

    public Integer getIdbaja() {
        return idbaja;
    }

    public void setIdbaja(Integer idbaja) {
        this.idbaja = idbaja;
    }

    public String getCausabaja() {
        return causabaja;
    }

    public void setCausabaja(String causabaja) {
        this.causabaja = causabaja;
    }

    public Date getFechabaja() {
        return fechabaja;
    }

    public void setFechabaja(Date fechabaja) {
        this.fechabaja = fechabaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbaja != null ? idbaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registrodeactivobaja)) {
            return false;
        }
        Registrodeactivobaja other = (Registrodeactivobaja) object;
        if ((this.idbaja == null && other.idbaja != null) || (this.idbaja != null && !this.idbaja.equals(other.idbaja))) {
            return false;
        }
        return true;
    }

    public String getIdsolicitud() {
        return idsolicitud;
    }

    public void setIdsolicitud(String idsolicitud) {
        this.idsolicitud = idsolicitud;
    }
    
    @Override
    public String toString() {
        return "gestionactivos.modelo.Registrodeactivobaja[ idbaja=" + idbaja + " ]";
    }
    
}
