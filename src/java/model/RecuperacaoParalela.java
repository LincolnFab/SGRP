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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author linkf
 */
@Entity
@Table(name = "recuperacao_paralela")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecuperacaoParalela.findAll", query = "SELECT r FROM RecuperacaoParalela r"),
    @NamedQuery(name = "RecuperacaoParalela.findById", query = "SELECT r FROM RecuperacaoParalela r WHERE r.id = :id"),
    @NamedQuery(name = "RecuperacaoParalela.findByDataProposta", query = "SELECT r FROM RecuperacaoParalela r WHERE r.dataProposta = :dataProposta"),
    @NamedQuery(name = "RecuperacaoParalela.findByJustificativa", query = "SELECT r FROM RecuperacaoParalela r WHERE r.justificativa = :justificativa"),
    @NamedQuery(name = "RecuperacaoParalela.findByObjetivoGeral", query = "SELECT r FROM RecuperacaoParalela r WHERE r.objetivoGeral = :objetivoGeral"),
    @NamedQuery(name = "RecuperacaoParalela.findByProcedimentosAvaliativos", query = "SELECT r FROM RecuperacaoParalela r WHERE r.procedimentosAvaliativos = :procedimentosAvaliativos"),
    @NamedQuery(name = "RecuperacaoParalela.findByAtividadesPropostas", query = "SELECT r FROM RecuperacaoParalela r WHERE r.atividadesPropostas = :atividadesPropostas"),
    @NamedQuery(name = "RecuperacaoParalela.findByAnoLetivo", query = "SELECT r FROM RecuperacaoParalela r WHERE r.anoLetivo = :anoLetivo"),
    @NamedQuery(name = "RecuperacaoParalela.findByBimestre", query = "SELECT r FROM RecuperacaoParalela r WHERE r.bimestre = :bimestre"),
    @NamedQuery(name = "RecuperacaoParalela.findByStatus", query = "SELECT r FROM RecuperacaoParalela r WHERE r.status = :status"),
    @NamedQuery(name = "RecuperacaoParalela.findByQuantidadeAlunos", query = "SELECT r FROM RecuperacaoParalela r WHERE r.quantidadeAlunos = :quantidadeAlunos"),
    @NamedQuery(name = "RecuperacaoParalela.findByQuantidadeAulas", query = "SELECT r FROM RecuperacaoParalela r WHERE r.quantidadeAulas = :quantidadeAulas"),
    @NamedQuery(name = "RecuperacaoParalela.findByObservacoes", query = "SELECT r FROM RecuperacaoParalela r WHERE r.observacoes = :observacoes")})
public class RecuperacaoParalela implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "id", nullable = false, length = 11)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_proposta", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataProposta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "justificativa", nullable = false, length = 1500)
    private String justificativa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "objetivo_geral", nullable = false, length = 1500)
    private String objetivoGeral;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "procedimentos_avaliativos", nullable = false, length = 1500)
    private String procedimentosAvaliativos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1500)
    @Column(name = "atividades_propostas", nullable = false, length = 1500)
    private String atividadesPropostas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ano_letivo", nullable = false)
    private int anoLetivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bimestre", nullable = false)
    private int bimestre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "status", nullable = false, length = 14)
    private String status;
    @Column(name = "quantidade_alunos")
    private Integer quantidadeAlunos;
    @Column(name = "quantidade_aulas")
    private Integer quantidadeAulas;
    @Size(max = 1500)
    @Column(name = "observacoes", length = 1500)
    private String observacoes;
    @JoinTable(name = "recuperacao_paralela_has_servidor", joinColumns = {
        @JoinColumn(name = "recuperacao_paralela_idrecuperacao_paralela", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "servidor_prontuario", referencedColumnName = "prontuario", nullable = false)})
    @ManyToMany
    private Collection<Servidor> servidorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recuperacaoParalelaId")
    private Collection<Aula> aulaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recuperacaoParalela")
    private Collection<RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection;
    @JoinColumns({
        @JoinColumn(name = "disciplina_sigla", referencedColumnName = "sigla", nullable = false),
        @JoinColumn(name = "disciplina_curso_id", referencedColumnName = "curso_id", nullable = false)})
    @ManyToOne(optional = false)
    private Disciplina disciplina;

    public RecuperacaoParalela() {
    }

    public RecuperacaoParalela(String id) {
        this.id = id;
    }

    public RecuperacaoParalela(String id, Date dataProposta, String justificativa, String objetivoGeral, String procedimentosAvaliativos, String atividadesPropostas, int anoLetivo, int bimestre, String status) {
        this.id = id;
        this.dataProposta = dataProposta;
        this.justificativa = justificativa;
        this.objetivoGeral = objetivoGeral;
        this.procedimentosAvaliativos = procedimentosAvaliativos;
        this.atividadesPropostas = atividadesPropostas;
        this.anoLetivo = anoLetivo;
        this.bimestre = bimestre;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDataProposta() {
        return dataProposta;
    }

    public void setDataProposta(Date dataProposta) {
        this.dataProposta = dataProposta;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public String getObjetivoGeral() {
        return objetivoGeral;
    }

    public void setObjetivoGeral(String objetivoGeral) {
        this.objetivoGeral = objetivoGeral;
    }

    public String getProcedimentosAvaliativos() {
        return procedimentosAvaliativos;
    }

    public void setProcedimentosAvaliativos(String procedimentosAvaliativos) {
        this.procedimentosAvaliativos = procedimentosAvaliativos;
    }

    public String getAtividadesPropostas() {
        return atividadesPropostas;
    }

    public void setAtividadesPropostas(String atividadesPropostas) {
        this.atividadesPropostas = atividadesPropostas;
    }

    public int getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public int getBimestre() {
        return bimestre;
    }

    public void setBimestre(int bimestre) {
        this.bimestre = bimestre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public void setQuantidadeAlunos(Integer quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }

    public Integer getQuantidadeAulas() {
        return quantidadeAulas;
    }

    public void setQuantidadeAulas(Integer quantidadeAulas) {
        this.quantidadeAulas = quantidadeAulas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @XmlTransient
    public Collection<Servidor> getServidorCollection() {
        return servidorCollection;
    }

    public void setServidorCollection(Collection<Servidor> servidorCollection) {
        this.servidorCollection = servidorCollection;
    }

    @XmlTransient
    public Collection<Aula> getAulaCollection() {
        return aulaCollection;
    }

    public void setAulaCollection(Collection<Aula> aulaCollection) {
        this.aulaCollection = aulaCollection;
    }

    @XmlTransient
    public Collection<RecuperacaoParalelaHasEstudante> getRecuperacaoParalelaHasEstudanteCollection() {
        return recuperacaoParalelaHasEstudanteCollection;
    }

    public void setRecuperacaoParalelaHasEstudanteCollection(Collection<RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection) {
        this.recuperacaoParalelaHasEstudanteCollection = recuperacaoParalelaHasEstudanteCollection;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecuperacaoParalela)) {
            return false;
        }
        RecuperacaoParalela other = (RecuperacaoParalela) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RecuperacaoParalela[ id=" + id + " ]";
    }
    
}
