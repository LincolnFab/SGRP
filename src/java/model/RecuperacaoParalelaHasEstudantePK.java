/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author linkf
 */
@Embeddable
public class RecuperacaoParalelaHasEstudantePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "recuperacao_paralela_id", nullable = false, length = 70)
    private String recuperacaoParalelaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "estudante_prontuario", nullable = false, length = 9)
    private String estudanteProntuario;

    public RecuperacaoParalelaHasEstudantePK() {
    }

    public RecuperacaoParalelaHasEstudantePK(String recuperacaoParalelaId, String estudanteProntuario) {
        this.recuperacaoParalelaId = recuperacaoParalelaId;
        this.estudanteProntuario = estudanteProntuario;
    }

    public String getRecuperacaoParalelaId() {
        return recuperacaoParalelaId;
    }

    public void setRecuperacaoParalelaId(String recuperacaoParalelaId) {
        this.recuperacaoParalelaId = recuperacaoParalelaId;
    }

    public String getEstudanteProntuario() {
        return estudanteProntuario;
    }

    public void setEstudanteProntuario(String estudanteProntuario) {
        this.estudanteProntuario = estudanteProntuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recuperacaoParalelaId != null ? recuperacaoParalelaId.hashCode() : 0);
        hash += (estudanteProntuario != null ? estudanteProntuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecuperacaoParalelaHasEstudantePK)) {
            return false;
        }
        RecuperacaoParalelaHasEstudantePK other = (RecuperacaoParalelaHasEstudantePK) object;
        if ((this.recuperacaoParalelaId == null && other.recuperacaoParalelaId != null) || (this.recuperacaoParalelaId != null && !this.recuperacaoParalelaId.equals(other.recuperacaoParalelaId))) {
            return false;
        }
        if ((this.estudanteProntuario == null && other.estudanteProntuario != null) || (this.estudanteProntuario != null && !this.estudanteProntuario.equals(other.estudanteProntuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RecuperacaoParalelaHasEstudantePK[ recuperacaoParalelaId=" + recuperacaoParalelaId + ", estudanteProntuario=" + estudanteProntuario + " ]";
    }
    
}
