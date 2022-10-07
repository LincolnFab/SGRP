/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DisciplinaDAO;
import dao.EstudanteDAO;
import dao.RecuperacaoDAO;
import dao.ServidorDAO;
import dao.TurmaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Aula;
import model.Curso;
import model.Disciplina;
import model.Estudante;
import model.RecuperacaoParalela;
import model.RecuperacaoParalelaHasEstudante;
import model.RecuperacaoParalelaHasEstudantePK;
import model.Servidor;
import model.Turma;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class RecuperacaoController implements Serializable {

    @Inject
    private RecuperacaoDAO recuperacaoDAO;

    @Inject
    private EstudanteDAO estudanteDAO;

    @Inject
    private DisciplinaDAO disciplinaDAO;

    @Inject
    private TurmaDAO turmaDAO;
    
    @Inject
    private ServidorDAO servidorDAO;

    private Date minTime;
    private Date maxTime;
    private Date minDate;
    private Date maxDate;

    private Turma turma;
    private Curso curso;
    private Aula aula;
    private Disciplina disciplina;

    private List<Turma> turmasCurso;
    private List<Disciplina> disciplinasCurso;
    private List<Estudante> estudantesTurma;
    private List<Estudante> estudantesRP;
    private List<Aula> aulas;

    private RecuperacaoParalela recuperacaoParalela;
    private List<RecuperacaoParalela> recuperacoesParalelas;
    
    private EmailController emailController;

    @PostConstruct
    public void fillRecuperacaoParalelaList() {
        recuperacoesParalelas = recuperacaoDAO.buscarTodas();

        setMinMaxTime();
        setMinMaxDate();
    }

    public RecuperacaoController() {
        openNew();
    }

    public void buscarDisciplinasCurso() {
        disciplinasCurso = disciplinaDAO.buscarPorCurso(curso.getId());
    }

    public void buscarTurmasCurso() {
        turmasCurso = turmaDAO.buscarPorCurso(curso.getId());
    }

    public void buscarEstudantesTurma() {
        estudantesTurma = estudanteDAO.buscarPorTurma(turma);
    }

    public boolean hasSelectedStudents() {
        return estudantesRP != null && !estudantesRP.isEmpty();
    }

    public String getAddButtonMessage() {
        if (hasSelectedStudents()) {
            int size = estudantesRP.size();
            return size > 1 ? size + " Estudantes selecionados" : "1 Estudante Selecionado";
        }

        return "Selecione os Estudantes";
    }

    public void adicionarStudentsRP() {
        estudantesRP.forEach((e) -> {
            System.out.println(e.toString());
        });
    }

    public void removerStudentsRP() {

    }

    public String navegar(int index) {
//        System.out.println(recuperacaoParalela.toString());

//        for (Servidor s : recuperacaoParalela.getServidorCollection()) {
//            System.out.println(s.toString());
//        }
//        System.out.println(turma.toString());
        Util.addMessageInformation("Continuar");

//        for(Servidor s: servidores) {
//            System.out.println(s.toString());
//        }
        PrimeFaces.current().ajax().update("form:messages");
        System.out.println(index);
        switch (index) {
            case 0:
                buscarEstudantesTurma();
                return "/docente/rp/cadastrar/alunos.xhtml?i=1faces-redirect=true";
            case 1:
                return "/docente/rp/cadastrar/aulas.xhtml?i=2faces-redirect=true";
            case -1:
                return "/docente/rp/cadastrar/alunos.xhtml?i=0faces-redirect=true";
            case -2:
                return "/docente/rp/cadastrar/aulas.xhtml?i=2faces-redirect=true";
        }
        return null;
    }

    public void openNew() {
        turma = null;
        aula = new Aula();
        aulas = new ArrayList<>();
        recuperacaoParalela = new RecuperacaoParalela();
        recuperacaoParalela.setDisciplina(new Disciplina());
    }

    public void cadastrarRecuperacao() {
        Date date = new Date();
        recuperacaoParalela.setId("trhtr");
        recuperacaoParalela.setAulaCollection(new ArrayList<Aula>());
        recuperacaoParalela.setRecuperacaoParalelaHasEstudanteCollection(new ArrayList<RecuperacaoParalelaHasEstudante>());

        recuperacaoParalela.setDataProposta(date);
        recuperacaoParalela.setAnoLetivo(new GregorianCalendar().get(Calendar.YEAR));
        recuperacaoParalela.setQuantidadeAlunos(estudantesRP.size());
        recuperacaoParalela.setQuantidadeAulas(aulas.size());
        recuperacaoParalela.setStatus("pendente");

        for (Estudante e : estudantesRP) {
            RecuperacaoParalelaHasEstudante rphe = new RecuperacaoParalelaHasEstudante();
            rphe.setEstudante(e);
            rphe.setRecuperacaoParalela(recuperacaoParalela);
            rphe.setRecuperacaoParalelaHasEstudantePK(new RecuperacaoParalelaHasEstudantePK(recuperacaoParalela.getId(), e.getProntuario()));
            recuperacaoParalela.getRecuperacaoParalelaHasEstudanteCollection().add(rphe);
        }

        for (Aula a : aulas) {
            a.setRecuperacaoParalelaId(recuperacaoParalela);
            recuperacaoParalela.getAulaCollection().add(a);
        }

        try {
            System.out.println(recuperacaoParalela.toString());
            recuperacaoDAO.create(recuperacaoParalela);
            
            // enviar e-mail
            String curso = recuperacaoParalela.getDisciplina().getCurso().getDescricao();
            System.out.println("CURSO...." + curso);
            if (curso.toLowerCase().contains("inf")) {
                Servidor s = servidorDAO.buscarPorTipo("FCC INF");
                util.JavaMail.emailFccDae(s.getEmail());
            } else if (curso.toLowerCase().contains("mec")) {
                Servidor s = servidorDAO.buscarPorTipo("FCC MEC");
                util.JavaMail.emailFccDae(s.getEmail());
            }
            
            fillRecuperacaoParalelaList();
            Util.addMessageInformation("A recuperação paralela foi enviada para análise");
            Util.addMessageInformation("Um email foi enviado ao Coordenador");

            PrimeFaces.current().ajax().update("form:messages");

        } catch (Exception e) {
            Util.addMessageError("Erro ao cadastrar a recuperação paralela");

            PrimeFaces.current().ajax().update("form:messages");
        }
    }

    public void editarRecuperacao() {
        recuperacaoDAO.update(recuperacaoParalela);
        Util.addMessageInformation("Registro Editado");

        PrimeFaces.current().ajax().update("form:messages");
    }

    public void removerRecuperacao() {
        try {
            recuperacaoDAO.remove(recuperacaoParalela);
            fillRecuperacaoParalelaList();
        } catch (EJBException e) {
            return;
        }
    }

    public void adicionarAula() {
        aula.setRecuperacaoParalelaId(recuperacaoParalela);
        aulas.add(aula);

        Util.addMessageInformation("Aula Adicionada");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-aulas");
    }

    public void removerAula() {
        aulas.remove(aula);

        Util.addMessageInformation("Aula Removida");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-aulas");
    }

    public void onCursoChange() {
        if (curso != null) {
            buscarTurmasCurso();
            buscarDisciplinasCurso();
        } else {
            turmasCurso = new ArrayList<>();
            disciplinasCurso = new ArrayList<>();
        }
    }

    public void setMinMaxTime() {
        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 7);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        minTime = tmp.getTime();

        tmp = Calendar.getInstance().getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 16);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        maxTime = tmp.getTime();
    }

    public void setMinMaxDate() {
        minDate = new Date();
        maxDate = new Date(minDate.getTime() + (365 * 24 * 60 * 60 * 1000));
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public RecuperacaoParalela getRecuperacaoParalela() {
        return recuperacaoParalela;
    }

    public void setRecuperacaoParalela(RecuperacaoParalela recuperacaoParalela) {
        this.recuperacaoParalela = recuperacaoParalela;
    }

    public List<RecuperacaoParalela> getRecuperacoesParalelas() {
        return recuperacoesParalelas;
    }

    public void setRecuperacoesParalelas(List<RecuperacaoParalela> recuperacoesParalelas) {
        this.recuperacoesParalelas = recuperacoesParalelas;
    }

    public List<Estudante> getEstudantesTurma() {
        return estudantesTurma;
    }

    public void setEstudantesTurma(List<Estudante> estudantesTurma) {
        this.estudantesTurma = estudantesTurma;
    }

    public List<Estudante> getEstudantesRP() {
        return estudantesRP;
    }

    public void setEstudantesRP(List<Estudante> estudantesRP) {
        this.estudantesRP = estudantesRP;
    }

    public List<Disciplina> getDisciplinasCurso() {
        return disciplinasCurso;
    }

    public void setDisciplinasCurso(List<Disciplina> disciplinasCurso) {
        this.disciplinasCurso = disciplinasCurso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public List<Turma> getTurmasCurso() {
        return turmasCurso;
    }

    public void setTurmasCurso(List<Turma> turmasCurso) {
        this.turmasCurso = turmasCurso;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

}
