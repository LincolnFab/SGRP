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
        CursoDAO dao = CDI.current().select(CursoDAO.class).get();
        Curso curso = dao.buscarPorId(string);
        return curso;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        Curso c = (Curso) t;
        return String.valueOf(c.getId());
    }
}
