/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ServidorDAO;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Servidor;
import util.Util;

/**
 *
 * @author Marilena Oshima
 */
@Named
@RequestScoped
public class EmailController {
    
    @Inject
    ServidorDAO servidorDAO;
    
    
    public void enviarEmail() {
        // Lista de e-mails
        System.out.println("tentando enviar email");
        ArrayList<String> to = new ArrayList<>();
        to.add("marilenaoshima@gmail.com");
        to.add("marilena.oshima@aluno.ifsp.edu.br");
        
        // Assunto
        String subject = "Recuperação paralela";
        
        // Corpo do e-mail
        String htmlBody = 
                "<html>"
                + "<h1>Recuperação Paralela</h1>"
                + "<p>Cadastrado na recuperação paralela.</p>"
                + "<a href=\"https://www.w3schools.com\">Acesse: pep2.ifsp.edu.br/rp</a>"
                + "<h5>mensagem automática. não responda</h5>"
                + "</html>";
        
        try {
            util.JavaMail.sendEmail(to, subject, htmlBody);
            Util.addMessageInformation("E-mail enviado com sucesso.");
        } catch(Exception e) {
            Util.addMessageError("Não foi possível enviar o e-mail.");
        }
        
    }
    
    public void emailDaeFcc() {
        ArrayList<String> to = new ArrayList<>();
        Servidor s;
        
        String subject = "Recuperação paralela";
        
        String htmlBody = 
                "<html>"
                + "<h2>Sistema de Gerenciamento de Recuperação Paralela</h2>"
                + "<p>Uma recuperação paralela foi cadastrada e aguarda sua avaliação.</p>"
                + "<p> Entre no sistema para avaliar."
                + "<a href=\"https://www.w3schools.com\">Acesse: pep2.ifsp.edu.br/rp</a>"
                + "<h5>Mensagem automática. Não responda.</h5>"
                + "</html>";
        try {
            s = servidorDAO.buscarPorTipo("DAE");
            to.add(s.getEmail());
            s = servidorDAO.buscarPorTipo("FCC");
            to.add(s.getEmail());
            util.JavaMail.sendEmail(to, subject, htmlBody);
        } catch(Exception e) {
            System.out.println("Não foi possível enviar o e-mail.");
        }
        
    }
}
