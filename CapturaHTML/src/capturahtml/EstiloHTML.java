package capturahtml;

import dao.EstilomusicalJpaController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import modelo.Estilomusical;

public class EstiloHTML {

    public void ler(File file, Estilomusical estiloentidade, EstilomusicalJpaController estilodao) {
        
        //PADRÃO 
        //<li><a href="/estilos/alternativo/">Alternativo</a></li>
        // < 60
        // > 62
        boolean tag = false, abre_tag = false, fecha_tag = true;
        int TAG = 2;
        String palavra;
        int a = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int caractere;
            try {
                while ((caractere = br.read()) != -1) {
                    if (TAG == 4) {
                        //Capturar palavra
                        palavra = "";
                        palavra += (char) caractere;
                        while ((caractere = br.read()) != 60) {
                            palavra += (char) caractere;
                        }
                        a++;
                        System.out.println(a + " " + palavra);
                        
                        estiloentidade.setNome(palavra);
                        estilodao.create(estiloentidade);
                        
                        abre_tag = true;
                        fecha_tag = false;
                        TAG = 0;
                        //continue;
                    } 
                    
                    switch (caractere) {
                        case 60:
                            //abre tag <
                            if (fecha_tag) {
                                //System.out.print(" < ");
                                abre_tag = true;
                                fecha_tag = false;
                            } else {
                                //System.out.println("Erro 1");
                            }

                            break;
                        case 62:
                            //fecha tag >
                            if (abre_tag) {
                                //System.out.print(" > ");
                                TAG++;

                                abre_tag = false;
                                fecha_tag = true;
                            } else {
                                //System.out.println("Erro 2");
                            }

                            break;
                        default:
                            //System.out.print("x");
                            break;
                    }
                }
            } catch (IOException | NumberFormatException ex) {
                System.err.println("Falha " + ex.getMessage());
            }
        } catch (Exception x) {
            System.out.println(x.getMessage() + " erro");
            System.out.println(x.getLocalizedMessage());
        }
    }
    
    //SOBRECARGA DE MÉTODO
    public void ler(File file) {
        
        //PADRÃO 
        //<li><a href="/estilos/alternativo/">Alternativo</a></li>
        // < 60
        // > 62
        boolean tag = false, abre_tag = false, fecha_tag = true;
        int TAG = 2;
        String palavra;
        int a = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            int caractere;
            try {
                while ((caractere = br.read()) != -1) {
                    if (TAG == 4) {
                        //Capturar palavra
                        palavra = "";
                        palavra += (char) caractere;
                        while ((caractere = br.read()) != 60) {
                            palavra += (char) caractere;
                        }
                        a++;
                        System.out.println(a + " " + palavra);
                        abre_tag = true;
                        fecha_tag = false;
                        TAG = 0;
                        //continue;
                    } 
                    
                    switch (caractere) {
                        case 60:
                            //abre tag <
                            if (fecha_tag) {
                                //System.out.print(" < ");
                                abre_tag = true;
                                fecha_tag = false;
                            } else {
                                //System.out.println("Erro 1");
                            }

                            break;
                        case 62:
                            //fecha tag >
                            if (abre_tag) {
                                //System.out.print(" > ");
                                TAG++;

                                abre_tag = false;
                                fecha_tag = true;
                            } else {
                                //System.out.println("Erro 2");
                            }

                            break;
                        default:
                            //System.out.print("x");
                            break;
                    }
                }
            } catch (IOException | NumberFormatException ex) {
                System.err.println("Falha " + ex.getMessage());
            }
        } catch (Exception x) {
            System.out.println(x.getMessage() + " erro");
            System.out.println(x.getLocalizedMessage());
        }
    }
}
