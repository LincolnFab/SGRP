/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CursoDAO;
import dao.TurmaDAO;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Curso;
import model.Turma;
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
public class TurmaController implements Serializable {

    @Inject
    private TurmaDAO turmaDAO;
    @Inject
    private CursoDAO cursoDAO;

    private Turma turma;
    private List<Turma> turmas;
    private UploadedFile file;
    
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
    
    public void importarTurma() {
        if (file != null) {
            List<Turma> planilha = new ArrayList<Turma>();
            List<Turma> turmaBanco = new ArrayList<Turma>();
            List<Curso> cursos = new ArrayList<Curso>();
            
            turmaBanco = turmaDAO.buscarTodos();
            cursos = cursoDAO.buscarTodos();
            
            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.turmaExcelData(newFile, cursos);

            for (Turma t : planilha) {
                if (turmaBanco.contains(t)) {
                    turmaDAO.update(t);
                } else {
                    turmaDAO.create(t);
                }
            }

            PrimeFaces.current().executeScript("PF('importarTurma').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-turmas");
            fillTurmaList();
            util.Util.addMessageInformation("Turma(s) Cadastrada(s)");
        } else {
            System.out.println("FILE NULL");
        }
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    

}
