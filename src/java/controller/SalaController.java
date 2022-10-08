/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SalaDAO;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.SalaDeAula;
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
public class SalaController implements Serializable {

    @Inject
    private SalaDAO salaDAO;

    private SalaDeAula salaDeAula;
    private List<SalaDeAula> salasDeAula;
    private UploadedFile file;

    @PostConstruct
    public void fillSalaList() {
        salasDeAula = salaDAO.buscarTodos();
    }

    public SalaController() {
        openNew();
    }

    public void openNew() {
        salaDeAula = new SalaDeAula();
    }

    public void cadastrarSala() {
        try {
            salaDAO.create(salaDeAula);
            fillSalaList();
            Util.addMessageInformation("Sala Cadastrada");
        } catch (EJBException e) {
            Util.addMessageError("Erro ao cadastrar sala (verifique se a sala já existe)");
        }

        PrimeFaces.current().executeScript("PF('createSalaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-salas");
    }

    public void editarSala() {
        try {
            salaDAO.update(salaDeAula);
            Util.addMessageInformation("Sala Editada");
        } catch (EJBException e) {
            Util.addMessageError("Erro ao editar sala");
        }

        PrimeFaces.current().executeScript("PF('editSalaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-salas");
    }

    public void removerSala() {
        try {
            salaDAO.remove(salaDeAula);
            fillSalaList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover este sala");
            return;
        }
        Util.addMessageInformation("Sala Removida");

        PrimeFaces.current().executeScript("PF('editSalaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-salas");
    }

    public void importarSala() {

        if (file != null) {
            List<SalaDeAula> planilha = new ArrayList<SalaDeAula>();
            List<SalaDeAula> salasBanco = new ArrayList<SalaDeAula>();

            salasBanco = salaDAO.buscarTodos();
            int error = 0;
            int update = 0;
            int create = 0;
            
            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.salaDeAulaReadExcel(newFile);

            for (SalaDeAula s : planilha) {
                if (salasBanco.contains(s)) {
                    try{
                        salaDAO.update(s);
                        update += 1;
                    } catch (Exception e) {
                        error += 1;
                    }
                } else {
                    try {
                        salaDAO.create(s);
                        create += 1;
                    } catch(Exception e) {
                        error += 1;
                    }
                }
            }

            PrimeFaces.current().executeScript("PF('importarSala').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-salas");
            fillSalaList();

            util.Util.addMessageWarning(update + " registro(s) atualizado(s)");
            util.Util.addMessageWarning(create + " registro(s) inserido(s)");
            util.Util.addMessageWarning(error + " registro(s) não foram importado(s)");
            
        } else {
            PrimeFaces.current().executeScript("PF('importarTurma').hide()");
            fillSalaList();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-turmas");
            util.Util.addMessageError("Arquivo inválido");
        }
    }

    public SalaDAO getSalaDAO() {
        return salaDAO;
    }

    public void setSalaDAO(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public SalaDeAula getSalaDeAula() {
        return salaDeAula;
    }

    public void setSalaDeAula(SalaDeAula salaDeAula) {
        this.salaDeAula = salaDeAula;
    }

    public List<SalaDeAula> getSalasDeAula() {
        return salasDeAula;
    }

    public void setSalasDeAula(List<SalaDeAula> salasDeAula) {
        this.salasDeAula = salasDeAula;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
