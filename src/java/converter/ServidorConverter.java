/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.ServidorDAO;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Servidor;

/**
 *
 * @author linkf
 */
@FacesConverter(forClass = Servidor.class, value = "servidorConverter")
public class ServidorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        ServidorDAO dao = CDI.current().select(ServidorDAO.class).get();
        Servidor servidor = dao.buscarPorProntuario(string);
        
        return servidor;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Servidor s = (Servidor) o;
        return String.valueOf(s.getProntuario());
    }

}
