package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author aluno
 */
public class Util {

    public static void addMessageInformation(String message) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", message));
    }

    public static void addMessageWarning(String message) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", message));
    }

    public static void addMessageError(String message) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", message));
    }

}
