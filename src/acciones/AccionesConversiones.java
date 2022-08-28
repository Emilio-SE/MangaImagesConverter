package acciones;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class AccionesConversiones {
    
    public float[] cmApts(String margenesTexto){
        
        float[] margenesPts = new float[4];
        float[] margenesCm = margenesTxtAFloat(margenesTexto);
        
        for (int i = 0; i < 4; i++) {
            margenesPts[i] = margenesCm[i] * 28.346f;
        }
        
        return margenesPts;
    }
    
    public float[] pulApts(String margenesTexto){
        
        float[] margenesPts = new float[4];
        float[] margenesPul = margenesTxtAFloat(margenesTexto);
        
        for (int i = 0; i < 4; i++) {
            margenesPts[i] = margenesPul[i] * 72f;
        }
        
        return margenesPts;
    }
    
    public float[] margenesTxtAFloat(String margenesTexto){

        String margenesSeparados[] = margenesTexto.split(",");
        
        float[] margenes = new float[4];
        
        for (int i = 0; i < 4; i++) {
            
            try{
                margenes[i] = Float.parseFloat(margenesSeparados[i]) ;
            }catch(ArrayIndexOutOfBoundsException e){
                margenes[i] = 0;
            }catch(NumberFormatException e){
                margenes[i] = 0;
            }

        }        
        
        return margenes;
    }
    
    public void copiarColas(Queue<String> colaDestino, Queue<String> colaOrigen){
        Iterator<String> auxColaOrigen = colaOrigen.iterator();
        
        if(!colaDestino.isEmpty()){
            colaDestino.remove();
        }
        
        while(auxColaOrigen.hasNext()){
            colaDestino.add(auxColaOrigen.next());
        }
        
    }
    
    public static List<String> FileAListString(File[] listadoArchivos){
        
        List<String> rutas = new ArrayList<>();
        
        for (File ruta : listadoArchivos) {
            rutas.add(ruta.getAbsolutePath());
        }
        
        return rutas;
        
    }
    
    
}
