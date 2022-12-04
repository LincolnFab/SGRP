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
            Util.addMessageError("Erro ao cadastrar disciplina. Verifique se a disciplina já existe.");
            PrimeFaces.current().ajax().update("form:messages");
        }

        PrimeFaces.current().executeScript("PF('createDisciplinaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
    }

    public void editarDisciplina() {
        try {
            disciplinaDAO.update(disciplina);
            Util.addMessageInformation("Disciplina Editada");
        } catch (Exception e) {
            Util.addMessageInformation("Erro ao editar disciplina");
        }

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
            int error = 0;
            int update = 0;
            int create = 0;

            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.disciplinaExcelData(newFile, cursos);

            for (Disciplina d : planilha) {
                if (disciplinaBanco.contains(d)) {
                    try {
                        disciplinaDAO.update(d);
                        update += 1;
                    } catch (Exception e) {
                        error += 1;
                    }
                } else {
                    try {
                        disciplinaDAO.create(d);
                        create += 1;
                    } catch (Exception e) {
                        error += 1;
                    }
                }
            }

            PrimeFaces.current().executeScript("PF('importarDisciplina').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
            fillCursoList();

            util.Util.addMessageWarning(update + " registro(s) atualizado(s)");
            util.Util.addMessageWarning(create + " registro(s) inserido(s)");
            util.Util.addMessageWarning(error + " registro(s) não foram importado(s)");
        } else {
            PrimeFaces.current().executeScript("PF('importarDisciplina').hide()");
            fillCursoList();
            PrimeFaces.current().ajax().update("form:messages", "form:dt-disciplinas");
            util.Util.addMessageError("Arquivo inválido");
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
