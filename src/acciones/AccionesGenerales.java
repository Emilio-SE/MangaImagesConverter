package acciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author emili
 */
public class AccionesGenerales {
    public String nombreAleatorio(){
        String nombreDocumento = "MIC ";
        int numeroAleatorioDistintivo1 = (int)( Math.random() * 100000000 + 1 );
        
        DateTimeFormatter fechaActual = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        
        nombreDocumento += fechaActual.format(LocalDateTime.now()) + " " + numeroAleatorioDistintivo1;
        nombreDocumento = nombreDocumento.replace("/", "-").replace(":", "-").replace(" ", "_");
        
        return nombreDocumento;
    }
    
    public static String[] ordenarListado( List<String> rutas ){
          
        Collections.sort(rutas, new Comparator<String>() {
            @Override
            public int compare(String cad1, String cad2) {

                String cad1ParteStr = cad1.replaceAll("\\d", "");
                String cad2ParteStr = cad2.replaceAll("\\d", "");

                if(cad1ParteStr.equalsIgnoreCase(cad2ParteStr))
                {
                    int op = (int) (extraeNum(cad1) - extraeNum(cad2));
                    return op;
                }
                return cad1.compareTo(cad2);
            }


            long extraeNum(String str) {
                String num = str.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Long.parseLong(num);
            }

        });
         
        return rutas.toArray(new String[0]);
          
    }
}
