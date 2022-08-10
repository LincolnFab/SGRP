/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EstudanteDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Estudante;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class EstudanteController implements Serializable {

    @Inject
    private EstudanteDAO estudanteDAO;

    private Estudante estudante;
    private List<Estudante> estudantes;

    @PostConstruct
    public void fillEstudanteList() {
        estudantes = estudanteDAO.buscarTodos();
    }

    public EstudanteController() {
        openNew();
    }

    public void openNew() {
        estudante = new Estudante();
    }

    public void cadastrarEstudante() {
        estudanteDAO.create(estudante);
        fillEstudanteList();
        Util.addMessageInformation("Estudante Cadastrado");

        PrimeFaces.current().executeScript("PF('createEstudanteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estudantes");
    }

    public void editarEstudante() {
        estudanteDAO.update(estudante);
        Util.addMessageInformation("Estudante Editado");

        PrimeFaces.current().executeScript("PF('editEstudanteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estudantes");
    }

    public void removerEstudante() {
        try {
            estudanteDAO.remove(estudante);
            fillEstudanteList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover este estudante");
            return;
        }
        Util.addMessageInformation("Estudante Removido");

        PrimeFaces.current().executeScript("PF('editEstudanteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estudantes");
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public List<Estudante> getEstudantes() {
        return estudantes;
    }

    public void setEstudantes(List<Estudante> estudantes) {
        this.estudantes = estudantes;
    }
}
