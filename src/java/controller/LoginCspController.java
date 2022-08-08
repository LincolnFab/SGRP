/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class LoginCspController implements Serializable {

    private String login;
    private String senha;
    
    public LoginCspController (){
        
    }

    public String autenticador() {
        if (this.login.equals(this.senha)) {
            return "csp/home?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Inválido", "Usuário e senha incorretos"));
            return null;
        }
    }

    public String logout(){
        return "/login_csp?faces-redirect=true";
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
