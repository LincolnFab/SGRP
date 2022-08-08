/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TurmaDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Curso;
import model.Turma;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class TurmaController implements Serializable {

    @Inject
    private TurmaDAO turmaDAO;

    private Turma turma;
    private List<Turma> turmas;

    @PostConstruct
    public void fillTurmaList() {
        turmas = turmaDAO.buscarTodos();
    }

    public TurmaController() {
        openNew();
    }

    public void openNew() {
        turma = new Turma();
        turma.setCursoId(new Curso());
    }

    public void cadastrarTurma() {
        turmaDAO.create(turma);
        fillTurmaList();
        Util.addMessageInformation("Turma Cadastrada");

        PrimeFaces.current().executeScript("PF('createTurmaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-turmas");
    }

    public void editarTurma() {
        turmaDAO.update(turma);
        Util.addMessageInformation("Turma Editada");

        PrimeFaces.current().executeScript("PF('editTurmaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-turmas");
    }

    public void removerTurma() {
        try {
            turmaDAO.remove(turma);
            turmaDAO.remove(turma);
            fillTurmaList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover esta turma");
            return;
        }
        Util.addMessageInformation("Turma Removida");

        PrimeFaces.current().executeScript("PF('editTurmaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-turmas");
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }

}
