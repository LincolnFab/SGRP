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
@Table(name = "estudante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudante.findAll", query = "SELECT e FROM Estudante e"),
    @NamedQuery(name = "Estudante.findByProntuario", query = "SELECT e FROM Estudante e WHERE e.prontuario = :prontuario"),
    @NamedQuery(name = "Estudante.findByNome", query = "SELECT e FROM Estudante e WHERE e.nome = :nome"),
    @NamedQuery(name = "Estudante.findBySenha", query = "SELECT e FROM Estudante e WHERE e.senha = :senha"),
    @NamedQuery(name = "Estudante.findByEmailPessoal", query = "SELECT e FROM Estudante e WHERE e.emailPessoal = :emailPessoal"),
    @NamedQuery(name = "Estudante.findByEmailAluno", query = "SELECT e FROM Estudante e WHERE e.emailAluno = :emailAluno"),
    @NamedQuery(name = "Estudante.findByTurma", query = "SELECT e FROM Estudante e WHERE e.turmaIdturma = :turma"),
    @NamedQuery(name = "Estudante.findByEmailResponsavel", query = "SELECT e FROM Estudante e WHERE e.emailResponsavel = :emailResponsavel")})
public class Estudante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "prontuario")
    private String prontuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 240)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "senha")
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email_pessoal")
    private String emailPessoal;
    @Size(max = 45)
    @Column(name = "email_aluno")
    private String emailAluno;
    @Size(max = 45)
    @Column(name = "email_responsavel")
    private String emailResponsavel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudante")
    private Collection<RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection;
    @JoinColumn(name = "turma_idturma", referencedColumnName = "idturma")
    @ManyToOne(optional = false)
    private Turma turmaIdturma;

    public Estudante() {
    }

    public Estudante(String prontuario) {
        this.prontuario = prontuario;
    }

    public Estudante(String prontuario, String nome, String senha, String emailPessoal) {
        this.prontuario = prontuario;
        this.nome = nome;
        this.senha = senha;
        this.emailPessoal = emailPessoal;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }

    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

    @XmlTransient
    public Collection<RecuperacaoParalelaHasEstudante> getRecuperacaoParalelaHasEstudanteCollection() {
        return recuperacaoParalelaHasEstudanteCollection;
    }

    public void setRecuperacaoParalelaHasEstudanteCollection(Collection<RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection) {
        this.recuperacaoParalelaHasEstudanteCollection = recuperacaoParalelaHasEstudanteCollection;
    }

    public Turma getTurmaIdturma() {
        return turmaIdturma;
    }

    public void setTurmaIdturma(Turma turmaIdturma) {
        this.turmaIdturma = turmaIdturma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prontuario != null ? prontuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudante)) {
            return false;
        }
        Estudante other = (Estudante) object;
        if ((this.prontuario == null && other.prontuario != null) || (this.prontuario != null && !this.prontuario.equals(other.prontuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Estudante[ prontuario=" + prontuario + " ]";
    }
    
}
