/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.DisciplinaDAO;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Disciplina;
import model.DisciplinaPK;

/**
 *
 * @author linkf
 */
@FacesConverter(forClass = Disciplina.class)
public class DisciplinaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        //Obtém uma instância do DAO
        String[] split = string.split("#");
        DisciplinaDAO dao = CDI.current().select(DisciplinaDAO.class).get();
        //Busca o objeto (pessoa) no banco de dados pela chave primária
        System.out.println(string);

        Disciplina disciplina = dao.buscarPorSiglaECurso(split[0], split[1]);
        System.out.println(disciplina.toString());
        return disciplina;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println(o instanceof Disciplina);
        System.out.println(o instanceof DisciplinaPK);
        Disciplina d = (Disciplina) o;
        return String.valueOf(d.getDisciplinaPK().getSigla() + "#" + d.getDisciplinaPK().getCursoId());
    }

}
