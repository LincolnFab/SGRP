/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ServidorDAO;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Servidor;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;
import util.UploadFileToFile;
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
    private UploadedFile file;

    private boolean loading = false;

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

    public void importarServidores() {
        loading = true;
        List<Servidor> planilha = new ArrayList<Servidor>();
        List<Servidor> servidoresBanco = new ArrayList<Servidor>();
        if (file != null) {
            try {
                servidoresBanco = servidorDAO.buscarTodos();

                File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
                planilha = util.ReadExcel.servidorReadExcel(newFile);

                for (Servidor s : planilha) {
                    if (servidoresBanco.contains(s)) {
                        s.setSenha(servidoresBanco.get(servidoresBanco.indexOf(s)).getSenha());
                        servidorDAO.update(s);
                    } else {
                        servidorDAO.create(s);
                    }
                }

                loading = false;
                util.Util.addMessageInformation("Servidor(es) Importado(s)");

                PrimeFaces.current().executeScript("PF('importarServidor').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
                fillServidorList();
            } catch (Exception e) {
                loading = false;
                util.Util.addMessageError("Erro ao Importar");

                PrimeFaces.current().ajax().update("form:messages");
            }

        } else {
            loading = false;
            util.Util.addMessageWarning("Selecione um arquivo .xls ou .xlsx");

            PrimeFaces.current().ajax().update("form:messages");
            System.out.println("FILE NULL");
        }
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

}
