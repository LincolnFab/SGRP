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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "disciplina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disciplina.findAll", query = "SELECT d FROM Disciplina d"),
    @NamedQuery(name = "Disciplina.findBySigla", query = "SELECT d FROM Disciplina d WHERE d.disciplinaPK.sigla = :sigla"),
    @NamedQuery(name = "Disciplina.findByNome", query = "SELECT d FROM Disciplina d WHERE d.nome = :nome"),
    @NamedQuery(name = "Disciplina.findByCursoId", query = "SELECT d FROM Disciplina d WHERE d.disciplinaPK.cursoId = :cursoId")})
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DisciplinaPK disciplinaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nome")
    private String nome;
    @JoinColumn(name = "curso_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Collection<RecuperacaoParalela> recuperacaoParalelaCollection;

    public Disciplina() {
    }

    public Disciplina(DisciplinaPK disciplinaPK) {
        this.disciplinaPK = disciplinaPK;
    }

    public Disciplina(DisciplinaPK disciplinaPK, String nome) {
        this.disciplinaPK = disciplinaPK;
        this.nome = nome;
    }

    public Disciplina(String sigla, String cursoId) {
        this.disciplinaPK = new DisciplinaPK(sigla, cursoId);
    }

    public DisciplinaPK getDisciplinaPK() {
        return disciplinaPK;
    }

    public void setDisciplinaPK(DisciplinaPK disciplinaPK) {
        this.disciplinaPK = disciplinaPK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @XmlTransient
    public Collection<RecuperacaoParalela> getRecuperacaoParalelaCollection() {
        return recuperacaoParalelaCollection;
    }

    public void setRecuperacaoParalelaCollection(Collection<RecuperacaoParalela> recuperacaoParalelaCollection) {
        this.recuperacaoParalelaCollection = recuperacaoParalelaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disciplinaPK != null ? disciplinaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.disciplinaPK == null && other.disciplinaPK != null) || (this.disciplinaPK != null && !this.disciplinaPK.equals(other.disciplinaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Disciplina[ disciplinaPK=" + disciplinaPK + " ]";
    }
    
}
