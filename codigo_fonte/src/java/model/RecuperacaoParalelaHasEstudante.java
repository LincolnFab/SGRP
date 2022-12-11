/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
 * @author linkf
 */
@Entity
@Table(name = "recuperacao_paralela_has_estudante")
@XmlRootElement
@Cacheable(false)
@NamedQueries({
    @NamedQuery(name = "RecuperacaoParalelaHasEstudante.findAll", query = "SELECT r FROM RecuperacaoParalelaHasEstudante r"),
    @NamedQuery(name = "RecuperacaoParalelaHasEstudante.findByRecuperacaoParalelaId", query = "SELECT r FROM RecuperacaoParalelaHasEstudante r WHERE r.recuperacaoParalelaHasEstudantePK.recuperacaoParalelaId = :recuperacaoParalelaId"),
    @NamedQuery(name = "RecuperacaoParalelaHasEstudante.findByEstudanteProntuario", query = "SELECT r FROM RecuperacaoParalelaHasEstudante r WHERE r.recuperacaoParalelaHasEstudantePK.estudanteProntuario = :estudanteProntuario"),
    @NamedQuery(name = "RecuperacaoParalelaHasEstudante.findByNotaPos", query = "SELECT r FROM RecuperacaoParalelaHasEstudante r WHERE r.notaPos = :notaPos"),
    @NamedQuery(name = "RecuperacaoParalelaHasEstudante.findByNotaPre", query = "SELECT r FROM RecuperacaoParalelaHasEstudante r WHERE r.notaPre = :notaPre")})
public class RecuperacaoParalelaHasEstudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecuperacaoParalelaHasEstudantePK recuperacaoParalelaHasEstudantePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nota_pos", precision = 12, scale = 0)
    private Float notaPos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nota_pre", nullable = false)
    private float notaPre;
    @JoinColumn(name = "estudante_prontuario", referencedColumnName = "prontuario", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estudante estudante;
    @JoinColumn(name = "recuperacao_paralela_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private RecuperacaoParalela recuperacaoParalela;

    public RecuperacaoParalelaHasEstudante() {
    }

    public RecuperacaoParalelaHasEstudante(RecuperacaoParalelaHasEstudantePK recuperacaoParalelaHasEstudantePK) {
        this.recuperacaoParalelaHasEstudantePK = recuperacaoParalelaHasEstudantePK;
    }

    public RecuperacaoParalelaHasEstudante(RecuperacaoParalelaHasEstudantePK recuperacaoParalelaHasEstudantePK, float notaPre) {
        this.recuperacaoParalelaHasEstudantePK = recuperacaoParalelaHasEstudantePK;
        this.notaPre = notaPre;
    }

    public RecuperacaoParalelaHasEstudante(String recuperacaoParalelaId, String estudanteProntuario) {
        this.recuperacaoParalelaHasEstudantePK = new RecuperacaoParalelaHasEstudantePK(recuperacaoParalelaId, estudanteProntuario);
    }

    public RecuperacaoParalelaHasEstudantePK getRecuperacaoParalelaHasEstudantePK() {
        return recuperacaoParalelaHasEstudantePK;
    }

    public void setRecuperacaoParalelaHasEstudantePK(RecuperacaoParalelaHasEstudantePK recuperacaoParalelaHasEstudantePK) {
        this.recuperacaoParalelaHasEstudantePK = recuperacaoParalelaHasEstudantePK;
    }

    public Float getNotaPos() {
        return notaPos;
    }

    public void setNotaPos(Float notaPos) {
        this.notaPos = notaPos;
    }

    public float getNotaPre() {
        return notaPre;
    }

    public void setNotaPre(float notaPre) {
        this.notaPre = notaPre;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public RecuperacaoParalela getRecuperacaoParalela() {
        return recuperacaoParalela;
    }

    public void setRecuperacaoParalela(RecuperacaoParalela recuperacaoParalela) {
        this.recuperacaoParalela = recuperacaoParalela;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recuperacaoParalelaHasEstudantePK != null ? recuperacaoParalelaHasEstudantePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecuperacaoParalelaHasEstudante)) {
            return false;
        }
        RecuperacaoParalelaHasEstudante other = (RecuperacaoParalelaHasEstudante) object;
        if ((this.recuperacaoParalelaHasEstudantePK == null && other.recuperacaoParalelaHasEstudantePK != null) || (this.recuperacaoParalelaHasEstudantePK != null && !this.recuperacaoParalelaHasEstudantePK.equals(other.recuperacaoParalelaHasEstudantePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RecuperacaoParalelaHasEstudante[ recuperacaoParalelaHasEstudantePK=" + recuperacaoParalelaHasEstudantePK + " ]";
    }
    
}
