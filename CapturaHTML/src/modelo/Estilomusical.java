/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kleyson
 */
@Entity
@Table(name = "estilomusical", catalog = "graforelacional", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estilomusical.findAll", query = "SELECT e FROM Estilomusical e"),
    @NamedQuery(name = "Estilomusical.findByIdEstiloMusical", query = "SELECT e FROM Estilomusical e WHERE e.idEstiloMusical = :idEstiloMusical"),
    @NamedQuery(name = "Estilomusical.findByNome", query = "SELECT e FROM Estilomusical e WHERE e.nome = :nome")})
public class Estilomusical implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEstiloMusical")
    private Integer idEstiloMusical;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @ManyToMany(mappedBy = "estilomusicalList", fetch = FetchType.LAZY)
    private List<Artista> artistaList;

    public Estilomusical() {
    }

    public Estilomusical(Integer idEstiloMusical) {
        this.idEstiloMusical = idEstiloMusical;
    }

    public Estilomusical(Integer idEstiloMusical, String nome) {
        this.idEstiloMusical = idEstiloMusical;
        this.nome = nome;
    }

    public Integer getIdEstiloMusical() {
        return idEstiloMusical;
    }

    public void setIdEstiloMusical(Integer idEstiloMusical) {
        this.idEstiloMusical = idEstiloMusical;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Artista> getArtistaList() {
        return artistaList;
    }

    public void setArtistaList(List<Artista> artistaList) {
        this.artistaList = artistaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstiloMusical != null ? idEstiloMusical.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estilomusical)) {
            return false;
        }
        Estilomusical other = (Estilomusical) object;
        if ((this.idEstiloMusical == null && other.idEstiloMusical != null) || (this.idEstiloMusical != null && !this.idEstiloMusical.equals(other.idEstiloMusical))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "capturahtml.Estilomusical[ idEstiloMusical=" + idEstiloMusical + " ]";
    }
    
}
