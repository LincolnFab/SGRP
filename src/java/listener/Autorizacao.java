package listener;

import controller.LoginController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import static jdk.internal.joptsimple.internal.Messages.message;
import model.Servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marilena Oshima
 */
public class Autorizacao implements PhaseListener {

    @Inject
    private LoginController loginController;

    public boolean autorizacaoAcesso(String request) {
        if (loginController.getEstudanteAutenticado() != null) {
            if (request.equals("/estudante/home.xhtml")
                    || request.equals("/estudante/recuperacoes.xhtml")) {
                return true;
            }
        }
        if (loginController.getServidorAutenticado().getTipo().equals("TAE")) {
            if (request.equals("/csp/home.xhtml")
                    || request.equals("/csp/consultar/estudantes.xhtml")
                    || request.equals("/csp/consultar/recuperacoes.xhtml")
                    || request.equals("/csp/gerenciamento/cursos.xhtml")
                    || request.equals("/csp/gerenciamento/disciplinas.xhtml")
                    || request.equals("/csp/gerenciamento/estudantes.xhtml")
                    || request.equals("/csp/gerenciamento/salas.xhtml")
                    || request.equals("/csp/gerenciamento/servidores.xhtml")
                    || request.equals("/csp/gerenciamento/turmas.xhtml")
                    || request.equals("/csp/relatorio/disciplinas.xhtml")
                    || request.equals("/csp/relatorio/estudantes.xhtml")) {
                return true;
            }
        }
        if (loginController.getServidorAutenticado().getTipo().equals("DAE")) {
            if (request.equals("/dae/home.xhtml")
                    || request.equals("/dae/rp/cadastrar/index.xhtml")
                    || request.equals("/dae/rp/consultar/index.xhtml")
                    || request.equals("/dae/rp/consultar/recuperacoes.xhtml")) {
                return true;
            }
        }
        if (loginController.getServidorAutenticado().getTipo().contains("FCC")) {
            if (request.equals("/fcc/home.xhtml")
                    || request.equals("/fcc/rp/cadastrar/index.xhtml")
                    || request.equals("/fcc/rp/consultar/index.xhtml")
                    || request.equals("/fcc/rp/consultar/recuperacoes.xhtml")) {
                return true;
            }
        }
        if (loginController.getServidorAutenticado().getTipo().equals("PROFESSOR")) {
            if (request.equals("/docente/home.xhtml")
                    || request.equals("/docente/rp/cadastrar/index.xhtml")
                    || request.equals("/docente/rp/consultar/index.xhtml")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPhase(PhaseEvent pe) {
        FacesContext facesContext = pe.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        //System.out.println("current page...." + currentPage);

        HttpServletRequest request = (HttpServletRequest) pe.getFacesContext().getExternalContext().getRequest();
        if (!request.getServletPath().equals("/login.xhtml")) {
            if (loginController.getServidorAutenticado() == null && loginController.getEstudanteAutenticado() == null) {
                try {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acesso negado", "Você deve realizar login antes de acessar a página"));
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .getFlash().setKeepMessages(true);
                    pe.getFacesContext().getExternalContext().redirect("/SGRP/login.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(Autorizacao.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                boolean temAcesso = autorizacaoAcesso(request.getServletPath());
                if (!temAcesso) {

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Acesso negado", "Você não tem autorização para acessar a página"));
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .getFlash().setKeepMessages(true);
                    String path;
                    if (loginController.getEstudanteAutenticado() != null) {
                        path = "estudante";
                    } else {
                        if (loginController.getServidorAutenticado().getTipo().equals("TAE")) {
                            path = "csp";
                        } else if (loginController.getServidorAutenticado().getTipo().equals("PROFESSOR")) {
                            path = "docente";
                        } else if (loginController.getServidorAutenticado().getTipo().contains("FCC")) {
                            path = "fcc";
                        } else if (loginController.getServidorAutenticado().getTipo().equals("DAE")) {
                            path = "dae";
                        } else {
                            path = null;
                        }
                    }
                    try {
                        pe.getFacesContext().getExternalContext().redirect("/SGRP/" + path + "/home.xhtml");
                    } catch (IOException ex) {
                        Logger.getLogger(Autorizacao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent pe
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
