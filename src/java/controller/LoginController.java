/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EstudanteDAO;
import dao.ServidorDAO;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Estudante;
import model.Servidor;
import org.primefaces.PrimeFaces;
import util.Util;

/**
 *
 * @author linkf
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

    private String login;
    private String senha;
    private String novaSenha;
    private String tipoUsuario;
    private Servidor servidorAutenticado;
    private Estudante estudanteAutenticado;

    @Inject
    private ServidorDAO servidorDAO;

    @Inject
    private EstudanteDAO estudanteDAO;

    public LoginController() {
        this.login = "";
        this.senha = "";
    }

    public String autenticador() {
        //login = login.toUpperCase();

        if (servidorAutenticador()) {
            if (servidorAutenticado.getSenha().equals(senha)) {
                System.out.println(servidorAutenticado.toString());
                if (servidorAutenticado.getTipo().equals("PROFESSOR")) {
                    return "docente/home?faces-redirect=true";
                }
                if (servidorAutenticado.getTipo().equals("DAE")) {
                    return "dae/home?faces-redirect=true";
                }
                if (servidorAutenticado.getTipo().equals("TAE")) {
                    return "csp/home?faces-redirect=true";
                }
                if (servidorAutenticado.getTipo().contains("FCC")) {
                    return "fcc/home?faces-redirect=true";
                }
            } else {
                Util.addMessageError("Senha inválida");
                this.login = "";
                this.senha = "";
            }
        } else if (estudanteAutenticador()) {
            if (estudanteAutenticado.getSenha().equals(senha)) {
                return "estudante/home?faces-redirect=true";
            } else {
                System.out.println("senha errada.....");
                Util.addMessageError("Senha inválida");
                this.login = "";
                this.senha = "";
            }
        } else {
            Util.addMessageError("Login inválido");
        }

        return "login";
        //return null;
    }

    public void alterarSenha() {

    }

    public boolean servidorAutenticador() {
        try {
            servidorAutenticado = servidorDAO.buscarPorProntuario(login);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean estudanteAutenticador() {
        try {
            estudanteAutenticado = estudanteDAO.buscarPorProntuario(login);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String logout() {
        this.login = "";
        this.senha = "";
        estudanteAutenticado = null;
        servidorAutenticado = null;

        return "/login?faces-redirect=true";
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

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Servidor getServidorAutenticado() {
        return servidorAutenticado;
    }

    public void setServidorAutenticado(Servidor servidorAutenticado) {
        this.servidorAutenticado = servidorAutenticado;
    }

    public Estudante getEstudanteAutenticado() {
        return estudanteAutenticado;
    }

    public void setEstudanteAutenticado(Estudante estudanteAutenticado) {
        this.estudanteAutenticado = estudanteAutenticado;
    }

}
