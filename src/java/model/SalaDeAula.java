/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author linkf
 */
@Entity
@Table(name = "sala_de_aula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaDeAula.findAll", query = "SELECT s FROM SalaDeAula s"),
    @NamedQuery(name = "SalaDeAula.findByIdSala", query = "SELECT s FROM SalaDeAula s WHERE s.idSala = :idSala"),
    @NamedQuery(name = "SalaDeAula.findByTipo", query = "SELECT s FROM SalaDeAula s WHERE s.tipo = :tipo")})
public class SalaDeAula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "idSala", nullable = false, length = 5)
    private String idSala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saladeaulaidSala")
    private Collection<Aula> aulaCollection;

    public SalaDeAula() {
    }

    public SalaDeAula(String idSala) {
        this.idSala = idSala;
    }

    public SalaDeAula(String idSala, String tipo) {
        this.idSala = idSala;
        this.tipo = tipo;
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Aula> getAulaCollection() {
        return aulaCollection;
    }

    public void setAulaCollection(Collection<Aula> aulaCollection) {
        this.aulaCollection = aulaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSala != null ? idSala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalaDeAula)) {
            return false;
        }
        SalaDeAula other = (SalaDeAula) object;
        if ((this.idSala == null && other.idSala != null) || (this.idSala != null && !this.idSala.equals(other.idSala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.SalaDeAula[ idSala=" + idSala + " ]";
    }
    
}
