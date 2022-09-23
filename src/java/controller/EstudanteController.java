/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EstudanteDAO;
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
import model.Estudante;
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
public class EstudanteController implements Serializable {

    @Inject
    private EstudanteDAO estudanteDAO;
    @Inject
    private TurmaDAO turmaDAO;

    private Estudante estudante;
    private List<Estudante> estudantes;
    private UploadedFile file;

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
    
    public void importarEstudantes() {
        if (file != null) {
            List<Estudante> planilha = new ArrayList<Estudante>();
            List<Estudante> estudantesBanco = new ArrayList<Estudante>();
            List<Turma> turmas = new ArrayList<Turma>();
            
            estudantesBanco = estudanteDAO.buscarTodos();
            turmas = turmaDAO.buscarTodos();
        
            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.estudanteExcelData(newFile, turmas);
        
            for (Estudante e : planilha) {
                if (estudantesBanco.contains(e)) {
                    e.setSenha(estudantesBanco.get(estudantesBanco.indexOf(e)).getSenha());
                    estudanteDAO.update(e);
                } else {
                    estudanteDAO.create(e);
                }
            }

            PrimeFaces.current().executeScript("PF('importarEstudante').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-estudantes");
            fillEstudanteList();
            util.Util.addMessageInformation("Estudante(s) Cadastrado(s)");
        } else {
            System.out.println("FILE NULL");
        }
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
}
