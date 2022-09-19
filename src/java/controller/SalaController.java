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
        salaDAO.create(salaDeAula);
        fillSalaList();
        Util.addMessageInformation("Sala Cadastrada");

        PrimeFaces.current().executeScript("PF('createSalaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-salas");
    }

    public void editarSala() {
        salaDAO.update(salaDeAula);
        Util.addMessageInformation("Sala Editada");

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
    
    public void importarServidores() {
        
        if (file != null) {
            List<SalaDeAula> planilha = new ArrayList<SalaDeAula>();
            List<SalaDeAula> salasBanco = new ArrayList<SalaDeAula>();

            salasBanco = salaDAO.buscarTodos();

            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.salaDeAulaReadExcel(newFile);
            
            for (SalaDeAula s : planilha) {
                if (salasBanco.contains(s)) {
                 
                    salaDAO.update(s);
                } else {
                    salaDAO.create(s);
                }
            }

            PrimeFaces.current().executeScript("PF('importarSala').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-salas");
            fillSalaList();
            
            util.Util.addMessageInformation("Salas de Aula Cadastrada(s)");
        } else {
            System.out.println("FILE NULL");
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
