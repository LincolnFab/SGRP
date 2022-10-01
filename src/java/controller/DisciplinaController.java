/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CursoDAO;
import dao.DisciplinaDAO;
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
import model.Disciplina;
import model.DisciplinaPK;
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
public class DisciplinaController implements Serializable {

    @Inject
    private DisciplinaDAO disciplinaDAO;
    @Inject
    private CursoDAO cursoDAO;

    private Disciplina disciplina;
    private List<Disciplina> disciplinas;
    private UploadedFile file;

    @PostConstruct
    public void fillCursoList() {
        /*for (Disciplina d : disciplinaDAO.buscarTodos()) {
            System.out.println(d.toString());
        }*/
        disciplinas = disciplinaDAO.buscarTodos();
    }

    public DisciplinaController() {
        openNew();
    }

    public void openNew() {
        disciplina = new Disciplina();
        disciplina.setCurso(new Curso());
        disciplina.setDisciplinaPK(new DisciplinaPK());
    }

    public void cadastrarDisciplina() {
        try {
            disciplina.getDisciplinaPK().setCursoId(disciplina.getCurso().getId());
            disciplinaDAO.create(disciplina);
            fillCursoList();
            Util.addMessageInformation("Disciplina Cadastrada");
        } catch (EJBException ex) {
            Util.addMessageError("Sigla de disciplina já existe");
            PrimeFaces.current().ajax().update("form:messages");
        }

        PrimeFaces.current().executeScript("PF('createDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }

    public void editarDisciplina() {
        disciplinaDAO.update(disciplina);
        Util.addMessageInformation("Disciplina Editada");

        PrimeFaces.current().executeScript("PF('editDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }

    public void removerDisciplina() {
        try {
            disciplinaDAO.remove(disciplina);
            fillCursoList();
        } catch (EJBException e) {
            Util.addMessageError("Não é possível remover esta disciplina");
            return;
        }
        Util.addMessageInformation("Disciplina Removido");

        PrimeFaces.current().executeScript("PF('editDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }
    
    public void importarDisciplina() {
        if (file != null) {
            List<Disciplina> planilha = new ArrayList<Disciplina>();
            List<Disciplina> disciplinaBanco = new ArrayList<Disciplina>();
            List<Curso> cursos = new ArrayList<Curso>();
            
            disciplinaBanco = disciplinaDAO.buscarTodos();
            cursos = cursoDAO.buscarTodos();
            
            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.disciplinaExcelData(newFile, cursos);
        
            System.out.println("planilha..." + planilha);
            for (Disciplina d : planilha) {
                if (disciplinaBanco.contains(d)) {
                    System.out.println("UPDATE");
                    System.out.println("D..." + d);
                    disciplinaDAO.update(d);
                } else {
                    System.out.println("CREATE");
                    System.out.println("D..." + d);
                    disciplinaDAO.create(d);
                }
            }
            this.file = null;
            PrimeFaces.current().executeScript("PF('importarDisciplina').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
            fillCursoList();
            util.Util.addMessageInformation("Disciplina(s) Cadastrado(s)");
        } else {
            System.out.println("FILE NULL");
        }
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
}
