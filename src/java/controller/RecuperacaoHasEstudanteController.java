/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RecuperacaoHasEstudanteDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.RecuperacaoParalelaHasEstudante;

/**
 *
 * @author Marilena Oshima
 */
@Named
@SessionScoped
public class RecuperacaoHasEstudanteController implements Serializable {

    @Inject
    private RecuperacaoHasEstudanteDAO recuperacaoHasEstudanteDAO;

    private List<RecuperacaoParalelaHasEstudante> recuperacoesHasEstudantes;

    private RecuperacaoParalelaHasEstudante recuperacaoParalelaHasEstudante;

    @PostConstruct
    public void fillRecuperacaoParalelaList() {
        recuperacoesHasEstudantes = recuperacaoHasEstudanteDAO.buscarTodos();

    }

    public List<RecuperacaoParalelaHasEstudante> getRecuperacoesHasEstudantes() {
        return recuperacoesHasEstudantes;
    }

    public void setRecuperacoesHasEstudantes(List<RecuperacaoParalelaHasEstudante> recuperacoesHasEstudantes) {
        this.recuperacoesHasEstudantes = recuperacoesHasEstudantes;
    }

    public RecuperacaoParalelaHasEstudante getRecuperacaoParalelaHasEstudante() {
        return recuperacaoParalelaHasEstudante;
    }

    public void setRecuperacaoParalelaHasEstudante(RecuperacaoParalelaHasEstudante recuperacaoParalelaHasEstudante) {
        this.recuperacaoParalelaHasEstudante = recuperacaoParalelaHasEstudante;
    }

}
