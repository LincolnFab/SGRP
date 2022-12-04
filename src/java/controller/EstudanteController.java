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
        try {
            estudanteDAO.create(estudante);
            fillEstudanteList();
            Util.addMessageInformation("Estudante Cadastrado");
        } catch (Exception e) {
            Util.addMessageError("Erro ao cadastrar estudante. Verifique se o estudante já existe.");
        }
        
        PrimeFaces.current().executeScript("PF('createEstudanteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estudantes");
    }
    
    public void editarEstudante() {
        try {
            estudanteDAO.update(estudante);
            Util.addMessageInformation("Estudante Editado");
            
        } catch (Exception e) {
            Util.addMessageError("Erro ao editar estudante");
        }
        
        PrimeFaces.current().executeScript("PF('editEstudanteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-estudantes");
    }
    
    public void editarEstudante(Estudante estudante) {
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
            int error = 0;
            int update = 0;
            int create = 0;
            
            File newFile = UploadFileToFile.uploadedFileToFileConverter(file);
            planilha = util.ReadExcel.estudanteExcelData(newFile, turmas);
            
            for (Estudante e : planilha) {
                if (estudantesBanco.contains(e)) {
                    try {
                        e.setSenha(estudantesBanco.get(estudantesBanco.indexOf(e)).getSenha());
                        estudanteDAO.update(e);
                        update += 1;
                    } catch (Exception ex) {
                        error += 1;
                    }
                } else {
                    try {
                        estudanteDAO.create(e);
                        create += 1;
                    } catch (Exception ex) {
                        error += 1;
                    }
                }
            }
            PrimeFaces.current().executeScript("PF('importarEstudante').hide()");
            PrimeFaces.current().ajax().update("form:fileupload", "form:messages", "form:dt-estudantes");
            fillEstudanteList();
            util.Util.addMessageWarning(update + " registro(s) atualizado(s)");
            util.Util.addMessageWarning(create + " registro(s) inserido(s)");
            util.Util.addMessageWarning(error + " registro(s) não foram importado(s)");
            
        } else {
            PrimeFaces.current().executeScript("PF('importarEstudante').hide()");
            PrimeFaces.current().ajax().update("form:fileupload", "form:messages", "form:dt-estudantes");
            fillEstudanteList();
            util.Util.addMessageError("Arquivo inválido");
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
