/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.CursoDAO;
import dao.TurmaDAO;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Curso;
import model.Turma;

/**
 *
 * @author linkf
 */
@FacesConverter(forClass = Turma.class)
public class TurmaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {

        //Obtém uma instância do DAO
        TurmaDAO dao = CDI.current().select(TurmaDAO.class).get();
        //Busca o objeto (pessoa) no banco de dados pela chave primária
        Turma turma = dao.buscarPorId(Integer.valueOf(string));
        return turma;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Turma t = (Turma) o;
        return String.valueOf(t.getIdturma());
    }

}
