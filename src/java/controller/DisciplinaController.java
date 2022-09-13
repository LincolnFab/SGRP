/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DisciplinaDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Curso;
import model.Disciplina;
import model.DisciplinaPK;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class DisciplinaController implements Serializable {

    @Inject
    private DisciplinaDAO disciplinaDAO;

    private Disciplina disciplina;
    private List<Disciplina> disciplinas;

    @PostConstruct
    public void fillCursoList() {
        for (Disciplina d : disciplinaDAO.buscarTodos()) {
            System.out.println(d.toString());
        }
        disciplinas = disciplinaDAO.buscarTodos();
    }

    public DisciplinaController() {
        openNew();
    }

    public void openNew() {
        disciplina = new Disciplina();
        disciplina.setCurso(new Curso());
        disciplina.setDisciplinaPK(new DisciplinaPK());
    }

    public void cadastrarDisciplina() {
        try {
            disciplina.getDisciplinaPK().setCursoId(disciplina.getCurso().getId());
            disciplinaDAO.create(disciplina);
            fillCursoList();
            Util.addMessageInformation("Disciplina Cadastrada");
        } catch (EJBException ex) {
            Util.addMessageError("Sigla de disciplina já existe");
            PrimeFaces.current().ajax().update("form:messages");
        }

        PrimeFaces.current().executeScript("PF('createDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }

    public void editarDisciplina() {
        disciplinaDAO.update(disciplina);
        Util.addMessageInformation("Disciplina Editada");

        PrimeFaces.current().executeScript("PF('editDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }

    public void removerDisciplina() {
        try {
            disciplinaDAO.remove(disciplina);
            fillCursoList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover esta disciplina");
            return;
        }
        Util.addMessageInformation("Disciplina Removido");

        PrimeFaces.current().executeScript("PF('editDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
