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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Inject
    private LoginController loginController;

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
    private RecuperacaoParalela recuperacaoParalelaAux;
    private List<RecuperacaoParalela> recuperacoesParalelas;
    private List<RecuperacaoParalela> allRecuperacoesParalelas;

    private String obs;

    @PostConstruct
    public void fillRecuperacaoParalelaList() {
        if (loginController.getServidorAutenticado() != null) {
            recuperacoesParalelas = recuperacaoDAO.buscarPorDocente(loginController.getServidorAutenticado().getProntuario());
            if (loginController.getServidorAutenticado().getTipo().contains("FCC")
                    || loginController.getServidorAutenticado().getTipo().contains("DAE")
                    || loginController.getServidorAutenticado().getTipo().contains("TAE")) {
                allRecuperacoesParalelas = recuperacaoDAO.buscarTodas();
            }
            recuperacaoParalela.setServidorCollection(new ArrayList<>());
            recuperacaoParalela.getServidorCollection().add(loginController.getServidorAutenticado());
        } else if (loginController.getEstudanteAutenticado() != null) {
            recuperacoesParalelas = recuperacaoDAO.buscarPorEstudante(loginController.getEstudanteAutenticado().getProntuario());
            System.out.println();
        }
        //recuperacoesParalelas = recuperacaoDAO.buscarPorEstudante("PE3012905");
        setMinMaxTime();
        setMinMaxDate();
        System.out.println();
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
        curso = null;
        turma = null;
        aula = new Aula();
        aulas = new ArrayList<>();
        recuperacaoParalela = new RecuperacaoParalela();
        estudantesTurma = new ArrayList<>();
        recuperacaoParalela.setDisciplina(new Disciplina());
        if (loginController != null) {
            recuperacaoParalela.setServidorCollection(new ArrayList<>());
            recuperacaoParalela.getServidorCollection().add(loginController.getServidorAutenticado());
        }
    }

    public String cadastrarRecuperacao(String path) {

        if (estudantesRP.isEmpty()) {
            Util.addMessageError("Selecione ao menos um estudante na Recupera????o Paralela");

            PrimeFaces.current().ajax().update("form:messages");
            return "";
        }

        if (aulas.isEmpty()) {
            Util.addMessageError("Adicione ao menos uma aula na Recupera????o Paralela");

            PrimeFaces.current().ajax().update("form:messages");
            return "";
        }

        try {
            Date date = new Date();

            recuperacaoParalela.setId(recuperacaoParalela.getDisciplina().getNome() + " " + recuperacaoParalela.getBimestre() + " " + turma.getIdturma());
            recuperacaoParalela.setAulaCollection(new ArrayList<Aula>());
            recuperacaoParalela.setRecuperacaoParalelaHasEstudanteCollection(new ArrayList<RecuperacaoParalelaHasEstudante>());

            recuperacaoParalela.setDataProposta(date);
            recuperacaoParalela.setAnoLetivo(new GregorianCalendar().get(Calendar.YEAR));
            recuperacaoParalela.setQuantidadeAlunos(estudantesRP.size());
            recuperacaoParalela.setQuantidadeAulas(aulas.size());
            recuperacaoParalela.setStatus("Pendente");

            for (Estudante e : estudantesRP) {
                RecuperacaoParalelaHasEstudante rphe = new RecuperacaoParalelaHasEstudante();
                rphe.setEstudante(e);
                rphe.setNotaPos(0.0f);
                rphe.setRecuperacaoParalela(recuperacaoParalela);
                rphe.setRecuperacaoParalelaHasEstudantePK(new RecuperacaoParalelaHasEstudantePK(recuperacaoParalela.getId(), e.getProntuario()));
                recuperacaoParalela.getRecuperacaoParalelaHasEstudanteCollection().add(rphe);
            }

            for (Aula a : aulas) {
                a.setRecuperacaoParalelaId(recuperacaoParalela);
                recuperacaoParalela.getAulaCollection().add(a);
            }

            recuperacaoDAO.create(recuperacaoParalela);
            
            // enviar email ao fcc
//            try {
//                // enviar e-mail
//                String curso = this.curso.getDescricao();
//                List<String> emails = new ArrayList<>();
//                if (curso.toLowerCase().contains("inf")) {
//                    Servidor s = servidorDAO.buscarPorTipo("FCC INF");
//                    emails.add(s.getEmail());
//                } else if (curso.toLowerCase().contains("mec")) {
//                    Servidor s = servidorDAO.buscarPorTipo("FCC MEC");
//                    emails.add(s.getEmail());
//                }
//                util.JavaMail.email(emails, "Novo cadastro de Recupera????o Paralela", recuperacaoParalela.getDisciplina().getDisciplinaPK().getSigla(), recuperacaoParalela.getDisciplina().getNome(), "A recupera????o paralela foi cadastrada e aguarda sua avalia????o.");
//                Util.addMessageInformation("Um email foi enviado ao Coordenador");
//            } catch (EJBException e) {
//                System.out.println(e);
//                Util.addMessageError("Erro ao enviar o email para o coordenador do curso. Contate o administrador");
//                PrimeFaces.current().ajax().update("form:messages");
//            }
            fillRecuperacaoParalelaList();
            Util.addMessageInformation("A recupera????o paralela foi enviada para an??lise");

            PrimeFaces.current().ajax().update("form:messages");
            openNew();

            return "/" + path + "/rp/consultar/index.xhtml?faces-redirect=true";
//            return "";
        } catch (Exception e) {
            System.out.println(e);
            Util.addMessageError("Erro ao cadastrar a recupera????o paralela.");

            PrimeFaces.current().ajax().update("form:messages");
            return "";
        }
    }

    public void editarRecuperacao(RecuperacaoParalela rp, String message, boolean email) {
        rp.getRecuperacaoParalelaHasEstudanteCollection().isEmpty();
        rp.getServidorCollection().isEmpty();
        rp.getAulaCollection().isEmpty();

        if (rp.getStatus().equals("Corre????o")) {
            rp.setStatus("Pendente");
        }

        recuperacaoDAO.update(rp);

        if (email) {
            //System.out.println("Enviar email para os alunos");
            emailNotaPosRp(rp);
        }

        Util.addMessageInformation(message);

        PrimeFaces.current().ajax().update("form:messages");
        recuperacaoParalela = new RecuperacaoParalela();
    }

    public void removerRecuperacao() {
        try {
            recuperacaoDAO.remove(recuperacaoParalela);
            fillRecuperacaoParalelaList();
            recuperacaoParalela = new RecuperacaoParalela();
        } catch (EJBException e) {
            return;
        }
    }

    public void adicionarAula() {
//        Estudante e = estudantesRP.get(estudantesRP.size());
//        for (RecuperacaoParalela rp : recuperacoesParalelas) {
//            for (Aula a: rp.getAulaCollection())
//            if (e.) {
//                Util.addMessageInformation("O aluno " + e.getNome() + " j?? est?? cadastrado em uma RP no mesmo hor??rio");
//                PrimeFaces.current().ajax().update("form:messages");
//            }
//        }

        if (aula.getHorarioFim().before(aula.getHorario())) {
            Util.addMessageError("Hor??rio de t??rmino n??o pode ser inferior ao hor??rio de in??cio");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-aulas");
        } else {
            aula.setRecuperacaoParalelaId(recuperacaoParalela);
            aula.setIdaula(Integer.parseInt(String.valueOf(new Date().getTime()).substring(8)));
            aulas.add(aula);
            aula = new Aula();

            Util.addMessageInformation("Aula Adicionada");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-aulas");
        }
    }

    public void removerAula() {
        aulas.remove(aula);
        aula = new Aula();

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

    public void onTurmaChange() {
        if (turma != null) {
            buscarEstudantesTurma();
        } else {
            estudantesTurma = new ArrayList<>();
            estudantesRP = new ArrayList<>();
        }
    }

    public void setMinMaxTime() {
        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 7);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        minTime = tmp.getTime();

//        tmp = Calendar.getInstance().getInstance();
//        tmp.set(Calendar.HOUR_OF_DAY, 16);
//        tmp.set(Calendar.MINUTE, 0);
//        tmp.set(Calendar.SECOND, 0);
//        tmp.set(Calendar.MILLISECOND, 0);
//        maxTime = tmp.getTime();
    }

    public void setMinMaxDate() {
        minDate = new Date();
        maxDate = new Date(minDate.getTime() + (365 * 24 * 60 * 60 * 1000));
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void deferirRpFcc() {
        try {
            recuperacaoParalela.setStatus("Deferida - FCC");

            if (recuperacaoParalela.getObservacoes() != null) {
                recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "\n");
            }
            recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "Aprovada FCC em " + getDateTime());
            if (obs != null) {
                recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + " - " + obs);
                obs = null;
            }
            recuperacaoDAO.update(recuperacaoParalela);

            // enviar email dae - descomentar
//            try {
//                Servidor s = servidorDAO.buscarPorTipo("DAE");
//                List<String> emails = new ArrayList<>();
//                emails.add(s.getEmail());
//                util.JavaMail.email(emails, "Novo cadastro de Recupera????o Paralela", recuperacaoParalela.getDisciplina().getDisciplinaPK().getSigla(), recuperacaoParalela.getDisciplina().getNome(), "A recupera????o paralela foi cadastrada e aguarda sua avalia????o.");
//                Util.addMessageInformation("Um email foi enviado ao DAE");
//            } catch (EJBException e) {
//                //System.out.println(e);
//                Util.addMessageError("Erro ao enviar o email para o DAE. Contate o administrador");
//                PrimeFaces.current().ajax().update("form:messages");
//            }
            PrimeFaces.current().executeScript("PF('autRP').hide()");
            fillRecuperacaoParalelaList();
            Util.addMessageInformation("Recuperacao paralela deferida");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
            recuperacaoParalela = new RecuperacaoParalela();
        } catch (EJBException e) {
            PrimeFaces.current().executeScript("PF('autRP').hide()");
            Util.addMessageError("N??o foi poss??vel deferir a recupera????o paralela");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
        }
    }

    public void deferirRpDae() {
        try {
            recuperacaoParalela.setStatus("Aprovada");
            if (recuperacaoParalela.getObservacoes() != null) {
                recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "\n");
            }
            recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "Aprovada DAE em " + getDateTime());
            if (obs != null) {
                recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + " - " + obs);
                obs = null;
            }

            recuperacaoDAO.update(recuperacaoParalela);
            
            // enviar emails - descomentar
//            try {
//                // enviar email docente respons??vel, csp e alunos
//                List<String> emails = new ArrayList<>();
//
//                // Docentes
//                recuperacaoParalela.getServidorCollection().forEach(((servidor) -> {
//                    emails.add(servidor.getEmail());
//                }));
//
//                //CSP - descomentar
//                //emails.add("csp.pep@ifsp.edu.br");
//                // Alunos
//                recuperacaoParalela.getRecuperacaoParalelaHasEstudanteCollection().forEach((estudante) -> {
//                    if (estudante.getEstudante().getEmailAluno() != null) {
//                        emails.add(estudante.getEstudante().getEmailAluno());
//                    }
//                    if (estudante.getEstudante().getEmailPessoal() != null) {
//                        emails.add(estudante.getEstudante().getEmailPessoal());
//                    }
//                    if (estudante.getEstudante().getEmailResponsavel() != null) {
//                        emails.add(estudante.getEstudante().getEmailResponsavel());
//                    }
//                });
//
//                util.JavaMail.email(emails, "Recupera????o Paralela", recuperacaoParalela.getDisciplina().getDisciplinaPK().getSigla(), recuperacaoParalela.getDisciplina().getNome(), "Cadastro a??provado da recupera????o paralela.");
//                Util.addMessageInformation("Email enviado para o(a) respons??vel pela recupera????o paralela");
//            } catch (EJBException e) {
//                //System.out.println(e);
//                Util.addMessageError("Erro ao enviar o email para o(a) respons??vel pela recupera????o paralela. Contate o administrador.");
//                PrimeFaces.current().ajax().update("form:messages");
//            }
            PrimeFaces.current().executeScript("PF('autRP').hide()");
            fillRecuperacaoParalelaList();
            Util.addMessageInformation("Recupera????o paralela deferida");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
            recuperacaoParalela = new RecuperacaoParalela();
        } catch (EJBException e) {
            PrimeFaces.current().executeScript("PF('autRP').hide()");
            Util.addMessageError("N??o foi poss??vel deferir a recupera????o paralela");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
        }
    }

    public void indeferirRp() {
        try {
            recuperacaoParalela.setStatus("Corre????o");

            if (recuperacaoParalela.getObservacoes() != null) {
                recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "\n");
            }
            recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "Indeferido em " + getDateTime());
            if (obs != null) {
                recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + " - " + obs);
                obs = null;
            }
            recuperacaoDAO.update(recuperacaoParalela);
            
            // enviar email docente - descomentar 
//            try {
//                // enviar email docente
//                List<String> emailDocentes = new ArrayList<>();
//                recuperacaoParalela.getServidorCollection().forEach(((servidor) -> {
//                    emailDocentes.add(servidor.getEmail());
//                }));
//                util.JavaMail.email(emailDocentes, "Corre????o da recupera????o paralela", recuperacaoParalela.getDisciplina().getDisciplinaPK().getSigla(), recuperacaoParalela.getDisciplina().getNome(), "Foi solicitado corre????o da recupera????o paralela.");
//                Util.addMessageInformation("Um email foi enviado para o(a) respons??vel pela recupera????o paralela");
//            } catch (EJBException e) {
//                Util.addMessageError("Erro ao enviar o email para o(a) respons??vel pela recupera????o paralela. Contate o administrador.");
//                PrimeFaces.current().ajax().update("form:messages");
//            }
            PrimeFaces.current().executeScript("PF('indefRP').hide()");
            fillRecuperacaoParalelaList();
            Util.addMessageInformation("Recupera????o paralela indeferida");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
            recuperacaoParalela = new RecuperacaoParalela();
        } catch (EJBException e) {
            PrimeFaces.current().executeScript("PF('indefRP').hide()");
            Util.addMessageError("N??o foi poss??vel indeferir a recupera????o paralela");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
        }
    }

    public void emailNotaPosRp(RecuperacaoParalela rp) {
        List<String> emails = new ArrayList<>();

        // enviar email aos alunos - descomentar
//        try {
//            // Alunos
//            rp.getRecuperacaoParalelaHasEstudanteCollection().forEach((estudante) -> {
//                if (estudante.getEstudante().getEmailAluno() != null) {
//                    emails.add(estudante.getEstudante().getEmailAluno());
//                }
//                if (estudante.getEstudante().getEmailPessoal() != null) {
//                    emails.add(estudante.getEstudante().getEmailPessoal());
//                }
//                if (estudante.getEstudante().getEmailResponsavel() != null) {
//                    emails.add(estudante.getEstudante().getEmailResponsavel());
//                }
//            });
//            util.JavaMail.email(emails, "Recupera????o Paralela", recuperacaoParalela.getDisciplina().getDisciplinaPK().getSigla(), recuperacaoParalela.getDisciplina().getNome(), "A nota da recupera????o paralela foi cadastrada.");
//            //Util.addMessageInformation("Email enviado para o(s) estudante(s)");
//        } catch (EJBException e) {
//            Util.addMessageError("Erro ao enviar email para o(s) estudante(s). Contate o administrador.");
//            PrimeFaces.current().ajax().update("form:messages");
//        }

    }

    public void cancelarRP() {
        recuperacaoParalela.setStatus("Cancelada");
        if (recuperacaoParalela.getObservacoes() != null) {
            recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "\n");
        }
        recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "Cancelada em: " + getDateTime());

        try {
            recuperacaoDAO.update(recuperacaoParalela);
            PrimeFaces.current().executeScript("PF('cancelarRP').hide()");
            Util.addMessageInformation("Recupera????o paralela cancelada");
            fillRecuperacaoParalelaList();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
            recuperacaoParalela = new RecuperacaoParalela();
        } catch (EJBException e) {
            PrimeFaces.current().executeScript("PF('cancelarRP').hide()");
            Util.addMessageError("N??o foi poss??vel indeferir a recupera????o paralela");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
        }
    }

    public void finalizarRp() {
        recuperacaoParalela.setStatus("Finalizada");

        if (recuperacaoParalela.getObservacoes() != null) {
            recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "\n");
        }
        recuperacaoParalela.setObservacoes(recuperacaoParalela.getObservacoes() + "Finalizada em " + getDateTime());
        List<String> emails = new ArrayList<>();
        
        try {
            recuperacaoDAO.update(recuperacaoParalela);

            // enviar email csp - descomentar
            //emails.add("csp.pep@ifsp.edu.br");
//            try {
//                util.JavaMail.email(emails, "Recupera????o Paralela", recuperacaoParalela.getDisciplina().getDisciplinaPK().getSigla(), recuperacaoParalela.getDisciplina().getNome(), "A recupera????o paralela foi finalizada.");
//            } catch (EJBException e) {
//                //Util.addMessageError("Erro ao enviar o email para o(a) respons??vel pela recupera????o paralela. Contate o administrador.");
//                //PrimeFaces.current().ajax().update("form:messages");
//            }
            PrimeFaces.current().executeScript("PF('finalizarRP').hide()");
            Util.addMessageInformation("Recupera????o paralela finalizada");
            fillRecuperacaoParalelaList();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
            recuperacaoParalela = new RecuperacaoParalela();
        } catch (EJBException e) {
            PrimeFaces.current().executeScript("PF('finalizarRP').hide()");
            Util.addMessageError("N??o foi poss??vel finalizar a recupera????o paralela");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-rp");
        }
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

    public RecuperacaoParalela getRecuperacaoParalelaAux() {
        return recuperacaoParalelaAux;
    }

    public void setRecuperacaoParalelaAux(RecuperacaoParalela recuperacaoParalelaAux) {
        this.recuperacaoParalelaAux = recuperacaoParalelaAux;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public List<RecuperacaoParalela> getAllRecuperacoesParalelas() {
        return allRecuperacoesParalelas;
    }

    public void setAllRecuperacoesParalelas(List<RecuperacaoParalela> allRecuperacoesParalelas) {
        this.allRecuperacoesParalelas = allRecuperacoesParalelas;
    }

}
