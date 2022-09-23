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
    @NamedQuery(name = "RecuperacaoParalela.findByQuantidadeAlunos", query = "SELECT r FROM RecuperacaoParalela r WHERE r.quantidadeAlunos = :quantidadeAlunos"),
    @NamedQuery(name = "RecuperacaoParalela.findByQuantidadeAulas", query = "SELECT r FROM RecuperacaoParalela r WHERE r.quantidadeAulas = :quantidadeAulas"),
    @NamedQuery(name = "RecuperacaoParalela.findByAnoLetivo", query = "SELECT r FROM RecuperacaoParalela r WHERE r.anoLetivo = :anoLetivo"),
    @NamedQuery(name = "RecuperacaoParalela.findByBimestre", query = "SELECT r FROM RecuperacaoParalela r WHERE r.bimestre = :bimestre"),
    @NamedQuery(name = "RecuperacaoParalela.findByAtividadesPropostas", query = "SELECT r FROM RecuperacaoParalela r WHERE r.atividadesPropostas = :atividadesPropostas"),
    @NamedQuery(name = "RecuperacaoParalela.findByStatus", query = "SELECT r FROM RecuperacaoParalela r WHERE r.status = :status")})
public class RecuperacaoParalela implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_proposta")
    @Temporal(TemporalType.DATE)
    private Date dataProposta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "justificativa")
    private String justificativa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "objetivo_geral")
    private String objetivoGeral;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "procedimentos_avaliativos")
    private String procedimentosAvaliativos;
    @Column(name = "quantidade_alunos")
    private Integer quantidadeAlunos;
    @Column(name = "quantidade_aulas")
    private Integer quantidadeAulas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ano_letivo")
    private int anoLetivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bimestre")
    private int bimestre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "atividades_propostas")
    private String atividadesPropostas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "status")
    private String status;
    @JoinTable(name = "recuperacao_paralela_has_servidor", joinColumns = {
        @JoinColumn(name = "recuperacao_paralela_idrecuperacao_paralela", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "servidor_prontuario", referencedColumnName = "prontuario")})
    @ManyToMany
    private Collection<Servidor> servidorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recuperacaoParalelaId")
    private Collection<Aula> aulaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recuperacaoParalela")
    private Collection<RecuperacaoParalelaHasEstudante> recuperacaoParalelaHasEstudanteCollection;
    @JoinColumns({
        @JoinColumn(name = "disciplina_sigla", referencedColumnName = "sigla"),
        @JoinColumn(name = "disciplina_curso_id", referencedColumnName = "curso_id")})
    @ManyToOne(optional = false)
    private Disciplina disciplina;

    public RecuperacaoParalela() {
    }

    public RecuperacaoParalela(String id) {
        this.id = id;
    }

    public RecuperacaoParalela(String id, Date dataProposta, String justificativa, String objetivoGeral, String procedimentosAvaliativos, int anoLetivo, int bimestre, String atividadesPropostas, String status) {
        this.id = id;
        this.dataProposta = dataProposta;
        this.justificativa = justificativa;
        this.objetivoGeral = objetivoGeral;
        this.procedimentosAvaliativos = procedimentosAvaliativos;
        this.anoLetivo = anoLetivo;
        this.bimestre = bimestre;
        this.atividadesPropostas = atividadesPropostas;
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

    public String getAtividadesPropostas() {
        return atividadesPropostas;
    }

    public void setAtividadesPropostas(String atividadesPropostas) {
        this.atividadesPropostas = atividadesPropostas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return "\n\nRecuperacaoParalela: " + 
                "\nid.......................................: " + id + 
                "\ndataProposta.............................: " + dataProposta + 
                "\njustificativa............................: " + justificativa + 
                "\nobjetivoGeral............................: " + objetivoGeral + 
                "\nprocedimentosAvaliativos.................: " + procedimentosAvaliativos + 
                "\nquantidadeAlunos.........................: " + quantidadeAlunos + 
                "\nquantidadeAulas..........................: " + quantidadeAulas + 
                "\nanoLetivo................................: " + anoLetivo + 
                "\nbimestre.................................: " + bimestre + 
                "\natividadesPropostas......................: " + atividadesPropostas + 
                "\nstatus...................................: " + status + 
                "\nservidorCollection.......................: " + servidorCollection + 
                "\naulaCollection...........................: " + aulaCollection + 
                "\nrecuperacaoParalelaHasEstudanteCollection: " + recuperacaoParalelaHasEstudanteCollection +
                "\ndisciplina...............................: " + disciplina + "\n\n";
    }

}
