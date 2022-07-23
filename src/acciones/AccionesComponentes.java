package acciones;

//Importación componentes awt y Swing
import java.awt.Toolkit;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
//Importación de eventos
import java.awt.event.KeyEvent;
//Otras importaciones
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AccionesComponentes{
    
    private String abrirCarpetasEn;
    private String abrirArchivosEn;
    private AccionesExploradorArchivos explorador;
            
    public AccionesComponentes(String abrirCarpetasEn, String abrirArchivosEn){
        explorador = new AccionesExploradorArchivos();
        this.abrirCarpetasEn = abrirCarpetasEn;
        this.abrirArchivosEn = abrirArchivosEn;
    }
    
    public void colocarRutaCarpeta(JTextField campo){
        String[] rutas = explorador.abrirExploradorCarpetas(abrirCarpetasEn);
        
        if(!rutas[0].equals("")){
            campo.setText(rutas[0]);
        }
    }
    
    public void colocarRutaArchivo(JTextField campo){
        String ruta[] = explorador.abrirExploradorArchivos(abrirArchivosEn);
        
        if(!ruta[0].equals("")){
            campo.setText(ruta[0]);
        }
    }
    
    public DefaultListModel agregarCarpetas(DefaultListModel modelo){
        List<String> rutasCarpetas = new ArrayList<>();
        rutasCarpetas = Arrays.asList(explorador.abrirExploradorCarpetas(abrirCarpetasEn));
        
        AccionesGenerales.ordenarListado(rutasCarpetas);
        
        if(!rutasCarpetas.isEmpty()){ 
            
            for(int indice = 0; indice < rutasCarpetas.size(); indice++){
                    modelo.addElement(rutasCarpetas.get(indice));
            }
        }

        return modelo;
    }
    
    public DefaultListModel agregarCarpetasRecursivamente(DefaultListModel modelo){
        String[] rutasCarpetas = explorador.abrirExploradorCarpetas(abrirCarpetasEn);
        List<String> rutasSubCarpetas = new ArrayList<>();
        Queue<String> rutasCarpetasRaices = new LinkedList<String>();
        
        if(!rutasCarpetas[0].equals("")){ 
            
            for(String rutaCarpeta : rutasCarpetas){
                
                    rutasSubCarpetas.add(rutaCarpeta);
                    rutasCarpetasRaices.add(rutaCarpeta);

            }
            
            buscarSubCarpetas(rutasCarpetasRaices, rutasSubCarpetas);
            
            AccionesGenerales.ordenarListado(rutasSubCarpetas);
            
            while(!rutasSubCarpetas.isEmpty()){
                modelo.addElement(rutasSubCarpetas.remove(0)); 
            }
            
            return modelo;
        }else{
            
            return modelo;
        }
        
    }
    
    private List<String> buscarSubCarpetas(Queue <String> directoriosRaices, List<String> subDirectorios){
        
        if(!directoriosRaices.isEmpty()){
            String carpetaRaiz = directoriosRaices.remove();

            File directorioRaiz = new File(carpetaRaiz);
            
            File[] archivosCarpetaRaíz = directorioRaiz.listFiles();
            
            for(File archivo : archivosCarpetaRaíz){
                
                if(archivo.isDirectory() ){
                    
                    directoriosRaices.add(archivo.getAbsolutePath() + "\\");
                    subDirectorios.add(archivo.getAbsolutePath() + "\\");
                    buscarSubCarpetas(directoriosRaices, subDirectorios);

                }
                
            }
            
        }else{
            return subDirectorios;
        }
            
        return subDirectorios;
    }
    
    public DefaultListModel agregarArchivo(DefaultListModel modelo){
        String rutas[] = explorador.abrirExploradorArchivos(abrirArchivosEn);
        
        if(!rutas[0].equals("")){
            /*for(int indice = 0; indice < rutas.length; indice++){
                modelo.addElement(rutas[indice]);
            }*/
            
            for(String ruta : rutas){
                modelo.addElement(ruta);
            }
            
        }

        return modelo;
    }
    
    public boolean eliminarCarpeta(DefaultListModel modelo, JList lista){
        int[] indiceElementosSeleccionados = lista.getSelectedIndices();
        int control = 0;
        boolean bool = true;
        
        for(int indiceElementoSeleccionado : indiceElementosSeleccionados){
            
            if(indiceElementoSeleccionado != -1){
                try {
                    modelo.remove(indiceElementoSeleccionado - control);
                    control++;
                } catch (IndexOutOfBoundsException e) {
                    bool = false;
                }
            }else{
                bool = false;
            }

        } 
        
        if(modelo.getSize() > 0){
            int foco = indiceElementosSeleccionados[indiceElementosSeleccionados.length - 1] - control;
        
            if(foco < modelo.getSize() - 1){
                lista.setSelectedIndex(foco + 1);
            }else if(foco == modelo.getSize() - 1){
                lista.setSelectedIndex(foco);
            }
        }
        
        return bool;
    }

    public DefaultListModel subirElementoList(DefaultListModel modelo, JList lista){
        
        String rutaCima, rutaActual;
        int[] indicesElementosSeleccionados = lista.getSelectedIndices();
        int[] selectedIndices = new int[indicesElementosSeleccionados.length];
        int i = 0, indiceSuperior;
        
        for(int indiceElementoSeleccionado : indicesElementosSeleccionados){
            if(indiceElementoSeleccionado != -1 && indiceElementoSeleccionado != 0){
                indiceSuperior = indiceElementoSeleccionado - 1;
                
                rutaCima = modelo.getElementAt( indiceSuperior ).toString();
                rutaActual = modelo.getElementAt( indiceElementoSeleccionado ).toString();
                modelo.setElementAt(rutaCima, indiceElementoSeleccionado);
                modelo.setElementAt(rutaActual, indiceSuperior);
                
                selectedIndices[i] = indiceSuperior;
                i++;
            }
        }
        
        lista.setSelectedIndices(selectedIndices);
        
        return modelo;
    }

    public DefaultListModel bajarElementoList(DefaultListModel modelo, JList lista){
        
        String rutaAbajo, rutaActual;
        int[] indicesElementosSeleccionados = lista.getSelectedIndices();
        int[] selectedIndices = new int[indicesElementosSeleccionados.length];
        int indice, indiceAbajo;
        
        for(int i = selectedIndices.length - 1; i >= 0; i--){
            indice = indicesElementosSeleccionados[i];
            
            if(indice != -1 && indice != (modelo.getSize() - 1)){
                indiceAbajo = indice + 1;
                
                rutaAbajo = modelo.getElementAt( indiceAbajo ).toString();
                rutaActual = modelo.getElementAt( indice ).toString();
                modelo.setElementAt(rutaAbajo, indice);
                modelo.setElementAt(rutaActual, indiceAbajo);
                
                selectedIndices[i] = indiceAbajo;
            }else if(indice == (modelo.getSize() - 1)){
                selectedIndices[i] = modelo.getSize() - 1;
            }
        }
        lista.setSelectedIndices(selectedIndices);

        return modelo;
    }
    //////////////////
    public Queue<String> obtenerDireccionesLista(DefaultListModel modelo){
        Queue <String> direcciones = new LinkedList<String>();
        int cantidadCarpetas = modelo.getSize();
        int rutasPerdidas = 0;
        
        for (int indice = 0; indice < cantidadCarpetas; indice++) {
            
            if(explorador.existeArchivo(modelo.getElementAt(indice).toString())){
                direcciones.add(modelo.getElementAt(indice).toString());
            }else{
                rutasPerdidas++;
            }

        }
        
        if(rutasPerdidas > 0){
            JOptionPane.showMessageDialog(null, rutasPerdidas + " carpetas no han podido ser cargadas.", "Error al cargar carpetas", JOptionPane.WARNING_MESSAGE);
        }
        
        return direcciones; 
    }

    public DefaultListModel colocarImagenes(DefaultListModel modelo, Queue<String> direccionesCarpetas){
        
        int cantidadCarpetas = direccionesCarpetas.size();
        String[] listado;
        String nombreImagen;
        String direccionImagen;
        String direccionCarpeta;
        
        
        //Recaba los nombres de las imagenes por carpeta.
        for (int indiceCarpeta = 0; indiceCarpeta < cantidadCarpetas; indiceCarpeta++) {
            //Obtiene el contenido de la carpeta.
            direccionCarpeta = direccionesCarpetas.remove();
            File carpeta = new File( direccionCarpeta );
            
            //Enlista el contenido de la carpeta.
            listado = AccionesGenerales.ordenarListado(Arrays.asList(carpeta.list()));
            //Filtra el contenido de la carpeta dejando solo las extensiones validas.
            for (int indiceImagen = 0; indiceImagen < listado.length; indiceImagen++) {

                nombreImagen = listado[indiceImagen];                

                if( esImagen(nombreImagen) ){
                    direccionImagen =  direccionCarpeta + nombreImagen;
                    modelo.addElement(direccionImagen);                    
                }

            } 
   
        }
        
        return modelo;
        
    }
    
    private boolean esImagen(String nombreImagen){
        String[] extensionesPermitidas = explorador.getExtensionesPermitidas();
        int i = 0;
        boolean confirmacion = false;
        
        while(i < extensionesPermitidas.length){
            if(nombreImagen.toLowerCase().endsWith( "." + extensionesPermitidas[i] )){
                confirmacion = true;
                break;
            }
            i++;
        }
        
        return confirmacion;
     }
    /////////////////////////////////////////////////////////////////////////////////////
    public void verificarTeclaIngresada(KeyEvent evt, int teclaPresionada){        
        if ( (teclaPresionada < KeyEvent.VK_0 || teclaPresionada > KeyEvent.VK_9) && ( teclaPresionada != KeyEvent.VK_COMMA ) && ( teclaPresionada != KeyEvent.VK_BACK_SPACE ) && ( teclaPresionada != KeyEvent.VK_SPACE )) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }
    
}
