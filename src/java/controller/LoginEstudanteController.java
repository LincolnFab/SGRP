/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EstudanteDAO;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import model.Estudante;

/**
 *
 * @author linkf
 */
@Named
@RequestScoped
public class LoginEstudanteController implements Serializable {

    @Inject
    private EstudanteDAO estudanteDAO;
    
    private String login;
    private String senha;
    private Estudante estudanteAutenticado;
    
    
    public LoginEstudanteController (){
        //this.login = "aluno123";
        //this.login = "";
        //this.senha = "";
    }

    public String autenticador() {
        if (login != null) {
            estudanteAutenticado = estudanteDAO.buscarPorProntuario(login);

            if (estudanteAutenticado != null && estudanteAutenticado.getProntuario().equals(login) && estudanteAutenticado.getSenha().equals(senha)) {
                return "estudante/home?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Inválido", "Usuário e senha incorretos"));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Inválido", "Usuário e senha incorretos"));
            return null;
        }
    }

    public String logout(){
        return "/login_estudante?faces-redirect=true";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
