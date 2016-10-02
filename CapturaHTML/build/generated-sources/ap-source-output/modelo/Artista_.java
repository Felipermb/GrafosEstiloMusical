package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Estilomusical;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-10-02T02:10:44")
@StaticMetamodel(Artista.class)
public class Artista_ { 

    public static volatile ListAttribute<Artista, Estilomusical> estilomusicalList;
    public static volatile SingularAttribute<Artista, Integer> idArtista;
    public static volatile SingularAttribute<Artista, String> nome;

}