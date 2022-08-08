/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CursoDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Curso;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class CursoController implements Serializable {

    @Inject
    private CursoDAO cursoDAO;

    private Curso curso;
    private List<Curso> cursos;

    @PostConstruct
    public void fillCursoList() {
        cursos = cursoDAO.buscarTodos();
    }

    public CursoController() {
        openNew();
    }

    public void openNew() {
        curso = new Curso();
    }

    public void cadastrarCurso() {
        cursoDAO.create(curso);
        fillCursoList();
        Util.addMessageInformation("Curso Cadastrado");

        PrimeFaces.current().executeScript("PF('createCursoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cursos");
    }

    public void editarCurso() {
        cursoDAO.update(curso);
        Util.addMessageInformation("Curso Editado");

        PrimeFaces.current().executeScript("PF('editCursoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cursos");
    }

    public void removerCurso() {
        try {
            cursoDAO.remove(curso);
            fillCursoList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover este curso");
            return;
        }
        Util.addMessageInformation("Curso Removido");

        PrimeFaces.current().executeScript("PF('editCursoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cursos");
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

}
