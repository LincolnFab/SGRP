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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @NamedQuery(name = "SalaDeAula.findByDescricao", query = "SELECT s FROM SalaDeAula s WHERE s.descricao = :descricao")})
public class SalaDeAula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSala")
    private Integer idSala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saladeaulaidSala")
    private Collection<Aula> aulaCollection;

    public SalaDeAula() {
    }

    public SalaDeAula(Integer idSala) {
        this.idSala = idSala;
    }

    public SalaDeAula(Integer idSala, String descricao) {
        this.idSala = idSala;
        this.descricao = descricao;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
