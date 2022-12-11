/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lincoln
 */
@Entity
@Table(name = "frequencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Frequencia.findAll", query = "SELECT f FROM Frequencia f"),
    @NamedQuery(name = "Frequencia.findByAulaIdaula", query = "SELECT f FROM Frequencia f WHERE f.frequenciaPK.aulaIdaula = :aulaIdaula"),
    @NamedQuery(name = "Frequencia.findByEstudanteProntuario", query = "SELECT f FROM Frequencia f WHERE f.frequenciaPK.estudanteProntuario = :estudanteProntuario"),
    @NamedQuery(name = "Frequencia.findByFrequencia", query = "SELECT f FROM Frequencia f WHERE f.frequencia = :frequencia")})
public class Frequencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FrequenciaPK frequenciaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "frequencia", nullable = false)
    private boolean frequencia;
    @JoinColumn(name = "aula_idaula", referencedColumnName = "idaula", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Aula aula;
    @JoinColumn(name = "estudante_prontuario", referencedColumnName = "prontuario", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estudante estudante;

    public Frequencia() {
    }

    public Frequencia(FrequenciaPK frequenciaPK) {
        this.frequenciaPK = frequenciaPK;
    }

    public Frequencia(FrequenciaPK frequenciaPK, boolean frequencia) {
        this.frequenciaPK = frequenciaPK;
        this.frequencia = frequencia;
    }

    public Frequencia(int aulaIdaula, String estudanteProntuario) {
        this.frequenciaPK = new FrequenciaPK(aulaIdaula, estudanteProntuario);
    }

    public FrequenciaPK getFrequenciaPK() {
        return frequenciaPK;
    }

    public void setFrequenciaPK(FrequenciaPK frequenciaPK) {
        this.frequenciaPK = frequenciaPK;
    }

    public boolean getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(boolean frequencia) {
        this.frequencia = frequencia;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (frequenciaPK != null ? frequenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Frequencia)) {
            return false;
        }
        Frequencia other = (Frequencia) object;
        if ((this.frequenciaPK == null && other.frequenciaPK != null) || (this.frequenciaPK != null && !this.frequenciaPK.equals(other.frequenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Frequencia[ frequenciaPK=" + frequenciaPK + " ]";
    }
    
}
