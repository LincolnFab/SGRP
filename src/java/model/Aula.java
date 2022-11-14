/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lincoln
 */
@Entity
@Table(name = "aula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aula.findAll", query = "SELECT a FROM Aula a"),
    @NamedQuery(name = "Aula.findByIdaula", query = "SELECT a FROM Aula a WHERE a.idaula = :idaula"),
    @NamedQuery(name = "Aula.findByDia", query = "SELECT a FROM Aula a WHERE a.dia = :dia"),
    @NamedQuery(name = "Aula.findByHorario", query = "SELECT a FROM Aula a WHERE a.horario = :horario"),
    @NamedQuery(name = "Aula.findByHorarioFim", query = "SELECT a FROM Aula a WHERE a.horarioFim = :horarioFim")})
public class Aula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaula", nullable = false)
    private Integer idaula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dia", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horario", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horario_fim", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date horarioFim;
    @JoinColumn(name = "recuperacao_paralela_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private RecuperacaoParalela recuperacaoParalelaId;
    @JoinColumn(name = "sala_de_aula_idSala", referencedColumnName = "idSala", nullable = false)
    @ManyToOne(optional = false)
    private SalaDeAula saladeaulaidSala;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aula", fetch=FetchType.EAGER)
    private Collection<Frequencia> frequenciaCollection;

    public Aula() {
    }

    public Aula(Integer idaula) {
        this.idaula = idaula;
    }

    public Aula(Integer idaula, Date dia, Date horario, Date horarioFim) {
        this.idaula = idaula;
        this.dia = dia;
        this.horario = horario;
        this.horarioFim = horarioFim;
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

    public Date getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Date horarioFim) {
        this.horarioFim = horarioFim;
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

    @XmlTransient
    public Collection<Frequencia> getFrequenciaCollection() {
        return frequenciaCollection;
    }

    public void setFrequenciaCollection(Collection<Frequencia> frequenciaCollection) {
        this.frequenciaCollection = frequenciaCollection;
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
