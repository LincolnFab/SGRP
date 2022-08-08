/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ServidorDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Servidor;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class ServidorController implements Serializable {

    @Inject
    private ServidorDAO servidorDAO;

    private Servidor servidor;
    private List<Servidor> servidores;

    @PostConstruct
    public void fillServidorList() {
        servidores = servidorDAO.buscarTodos();
    }

    public ServidorController() {
        openNew();
    }

    public void openNew() {
        servidor = new Servidor();
    }

    public void cadastrarServidor() {
        servidorDAO.create(servidor);
        fillServidorList(); 
        Util.addMessageInformation("Servidor Cadastrado");

        PrimeFaces.current().executeScript("PF('createServidorDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
    }

    public void editarServidor() {
        servidorDAO.update(servidor);
        Util.addMessageInformation("Servidor Editado");

        PrimeFaces.current().executeScript("PF('editServidorDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
    }

    public void removerServidor() {
        try {
            servidorDAO.remove(servidor);
            servidorDAO.remove(servidor);
            fillServidorList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover este servidor");
            return;
        }
        Util.addMessageInformation("Servidor Removido");

        PrimeFaces.current().executeScript("PF('editServidorDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public List<Servidor> getServidores() {
        return servidores;
    }

    public void setServidores(List<Servidor> servidores) {
        this.servidores = servidores;
    }

}
