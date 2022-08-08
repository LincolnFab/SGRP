/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author linkf
 */
@Entity
@Table(name = "aula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aula.findAll", query = "SELECT a FROM Aula a"),
    @NamedQuery(name = "Aula.findByIdaula", query = "SELECT a FROM Aula a WHERE a.idaula = :idaula"),
    @NamedQuery(name = "Aula.findByDia", query = "SELECT a FROM Aula a WHERE a.dia = :dia"),
    @NamedQuery(name = "Aula.findByHorario", query = "SELECT a FROM Aula a WHERE a.horario = :horario"),
    @NamedQuery(name = "Aula.findByFrequencia", query = "SELECT a FROM Aula a WHERE a.frequencia = :frequencia")})
public class Aula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaula")
    private Integer idaula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia")
    @Temporal(TemporalType.DATE)
    private Date dia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horario")
    @Temporal(TemporalType.TIME)
    private Date horario;
    @Column(name = "frequencia")
    private Short frequencia;
    @JoinColumn(name = "recuperacao_paralela_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RecuperacaoParalela recuperacaoParalelaId;
    @JoinColumn(name = "sala_de_aula_idSala", referencedColumnName = "idSala")
    @ManyToOne(optional = false)
    private SalaDeAula saladeaulaidSala;

    public Aula() {
    }

    public Aula(Integer idaula) {
        this.idaula = idaula;
    }

    public Aula(Integer idaula, Date dia, Date horario) {
        this.idaula = idaula;
        this.dia = dia;
        this.horario = horario;
    }

    public Integer getIdaula() {
        return idaula;
    }

    public void setIdaula(Integer idaula) {
        this.idaula = idaula;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Short getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Short frequencia) {
        this.frequencia = frequencia;
    }

    public RecuperacaoParalela getRecuperacaoParalelaId() {
        return recuperacaoParalelaId;
    }

    public void setRecuperacaoParalelaId(RecuperacaoParalela recuperacaoParalelaId) {
        this.recuperacaoParalelaId = recuperacaoParalelaId;
    }

    public SalaDeAula getSaladeaulaidSala() {
        return saladeaulaidSala;
    }

    public void setSaladeaulaidSala(SalaDeAula saladeaulaidSala) {
        this.saladeaulaidSala = saladeaulaidSala;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaula != null ? idaula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aula)) {
            return false;
        }
        Aula other = (Aula) object;
        if ((this.idaula == null && other.idaula != null) || (this.idaula != null && !this.idaula.equals(other.idaula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Aula[ idaula=" + idaula + " ]";
    }
    
}
