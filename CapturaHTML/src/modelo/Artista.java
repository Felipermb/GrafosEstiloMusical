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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "artista", catalog = "grafoestilomusical", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artista.findAll", query = "SELECT a FROM Artista a"),
    @NamedQuery(name = "Artista.findByIdArtista", query = "SELECT a FROM Artista a WHERE a.idArtista = :idArtista"),
    @NamedQuery(name = "Artista.findByNome", query = "SELECT a FROM Artista a WHERE a.nome = :nome")})
public class Artista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArtista")
    private Integer idArtista;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @JoinTable(name = "estilomusical_has_artista", joinColumns = {
        @JoinColumn(name = "Artista_idArtista", referencedColumnName = "idArtista")}, inverseJoinColumns = {
        @JoinColumn(name = "EstiloMusical_idEstiloMusical", referencedColumnName = "idEstiloMusical")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Estilomusical> estilomusicalList;

    public Artista() {
    }

    public Artista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public Artista(Integer idArtista, String nome) {
        this.idArtista = idArtista;
        this.nome = nome;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public List<Estilomusical> getEstilomusicalList() {
        return estilomusicalList;
    }

    public void setEstilomusicalList(List<Estilomusical> estilomusicalList) {
        this.estilomusicalList = estilomusicalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArtista != null ? idArtista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artista)) {
            return false;
        }
        Artista other = (Artista) object;
        if ((this.idArtista == null && other.idArtista != null) || (this.idArtista != null && !this.idArtista.equals(other.idArtista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Artista[ idArtista=" + idArtista + " ]";
    }
    
}
