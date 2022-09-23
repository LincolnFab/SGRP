/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.SalaDAO;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.SalaDeAula;

/**
 *
 * @author linkf
 */
@FacesConverter(forClass = SalaDeAula.class)
public class SalaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        SalaDAO dao = CDI.current().select(SalaDAO.class).get();
        SalaDeAula sala = dao.buscarPorId(Integer.parseInt(string));
        return sala;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object t) {
        SalaDeAula s = (SalaDeAula) t;
        return String.valueOf(s.getIdSala());
    }
}
