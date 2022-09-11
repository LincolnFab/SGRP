/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Marilena Oshima
 */
@Named
@RequestScoped
public class EmailController {
    public void enviarEmail() {
        // Lista de e-mails
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
        
        util.JavaMail.sendEmail(to, subject, htmlBody);
    }
}
