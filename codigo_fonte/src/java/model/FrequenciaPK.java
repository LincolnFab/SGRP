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
 * @author Lincoln
 */
@Embeddable
public class FrequenciaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "aula_idaula", nullable = false)
    private int aulaIdaula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "estudante_prontuario", nullable = false, length = 9)
    private String estudanteProntuario;

    public FrequenciaPK() {
    }

    public FrequenciaPK(int aulaIdaula, String estudanteProntuario) {
        this.aulaIdaula = aulaIdaula;
        this.estudanteProntuario = estudanteProntuario;
    }

    public int getAulaIdaula() {
        return aulaIdaula;
    }

    public void setAulaIdaula(int aulaIdaula) {
        this.aulaIdaula = aulaIdaula;
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
        hash += (int) aulaIdaula;
        hash += (estudanteProntuario != null ? estudanteProntuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FrequenciaPK)) {
            return false;
        }
        FrequenciaPK other = (FrequenciaPK) object;
        if (this.aulaIdaula != other.aulaIdaula) {
            return false;
        }
        if ((this.estudanteProntuario == null && other.estudanteProntuario != null) || (this.estudanteProntuario != null && !this.estudanteProntuario.equals(other.estudanteProntuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FrequenciaPK[ aulaIdaula=" + aulaIdaula + ", estudanteProntuario=" + estudanteProntuario + " ]";
    }
    
}
