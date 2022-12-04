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

    private boolean loading = true;

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
        try {
            servidorDAO.create(servidor);
            fillServidorList();
            Util.addMessageInformation("Servidor Cadastrado");
        } catch (Exception e) {
            Util.addMessageError("Erro ao cadastrar servidor. Verifique se o servidor já existe.");
        }

        PrimeFaces.current().executeScript("PF('createServidorDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
    }

    public void editarServidor() {
        try {
            servidorDAO.update(servidor);
            Util.addMessageInformation("Servidor Editado");
        } catch (Exception e) {
            Util.addMessageError("Erro ao editar servidor");
        }

        PrimeFaces.current().executeScript("PF('editServidorDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
    }

    public void editarServidor(Servidor servidor) {
        servidorDAO.update(servidor);
        Util.addMessageInformation("Servidor Editado");

        PrimeFaces.current().executeScript("PF('editServidorDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
    }

    public void removerServidor() {
        try {
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
        PrimeFaces.current().ajax().update("loading");
        List<Servidor> planilha = new ArrayList<Servidor>();
        List<Servidor> servidoresBanco = new ArrayList<Servidor>();
        if (file != null) {

            servidoresBanco = servidorDAO.buscarTodos();
            int error = 0;
            int update = 0;
            int create = 0;

            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.servidorReadExcel(newFile);

            for (Servidor s : planilha) {
                if (servidoresBanco.contains(s)) {
                    try {
                        s.setSenha(servidoresBanco.get(servidoresBanco.indexOf(s)).getSenha());
                        servidorDAO.update(s);
                        update += 1;
                    } catch (Exception e) {
                        error += 1;
                    }
                } else {
                    try {
                        servidorDAO.create(s);
                        create += 1;
                    } catch (Exception e) {
                        error += 1;
                    }
                }
            }

            PrimeFaces.current().executeScript("PF('importarServidor').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
            fillServidorList();

            util.Util.addMessageWarning(update + " registro(s) atualizado(s)");
            util.Util.addMessageWarning(create + " registro(s) inserido(s)");
            util.Util.addMessageWarning(error + " registro(s) não foram importado(s)");

        } else {
            PrimeFaces.current().executeScript("PF('importarServidor').hide()");
            fillServidorList();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-servidores");
            util.Util.addMessageError("Arquivo inválido");
        }
        PrimeFaces.current().ajax().update("loading");
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
