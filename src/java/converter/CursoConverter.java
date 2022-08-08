/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.CursoDAO;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Curso;

/**
 *
 * @author linkf
 */
@FacesConverter(forClass = Curso.class)
public class CursoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
//        System.out.println("getAsObject: " + string);

        //Obtém uma instância do DAO
        CursoDAO dao = CDI.current().select(CursoDAO.class).get();
        //Busca o objeto (pessoa) no banco de dados pela chave primária
        Curso curso = dao.buscarPorId(Integer.valueOf(string));
        return curso;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        Curso c = (Curso) t;
        return String.valueOf(c.getId());
    }
}
