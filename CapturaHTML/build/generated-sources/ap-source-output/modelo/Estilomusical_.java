package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Artista;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-02T02:10:44")
@StaticMetamodel(Estilomusical.class)
public class Estilomusical_ { 

    public static volatile SingularAttribute<Estilomusical, Integer> idEstiloMusical;
    public static volatile SingularAttribute<Estilomusical, String> nome;
    public static volatile ListAttribute<Estilomusical, Artista> artistaList;

}