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
        List<String> subCarpetas = new ArrayList<>();
        subCarpetas = Arrays.asList(explorador.abrirExploradorCarpetas(abrirCarpetasEn));
        int tamLista = modelo.getSize();
        
        AccionesGenerales.ordenarListado(subCarpetas);
        
        if(!subCarpetas.isEmpty()){ 
            
            for(int indice = 0; indice < subCarpetas.size(); indice++){
                if( (tamLista + indice) > 15){
                    JOptionPane.showMessageDialog(null, "Limite de 16 carpetas");
                    break;
                }else{
                    modelo.addElement(subCarpetas.get(indice));
                }
            }
        }

        return modelo;
    }
    
    public DefaultListModel agregarCarpetasRecursivamente(DefaultListModel modelo){
        String[] rutas = explorador.abrirExploradorCarpetas(abrirCarpetasEn);
        List<String> subCarpetas = new ArrayList<>();
        Queue<String> raices = new LinkedList<String>();
        
        int tamLista = modelo.getSize();
        
        if(!rutas[0].equals("")){ 
            
            for(int indice = 0; indice < rutas.length; indice++){
                
                if( (subCarpetas.size() + tamLista + indice) > 15){
                    JOptionPane.showMessageDialog(null, "Limite de 16 carpetas");
                    
                    break;
                }else{
                    subCarpetas.add(rutas[indice]);
                    raices.add(rutas[indice]);
                }
            }
            
            buscarSubCarpetas(raices, subCarpetas, modelo.getSize());
            
            AccionesGenerales.ordenarListado(subCarpetas);
            
            while(modelo.getSize() < 16 && !subCarpetas.isEmpty()){
                modelo.addElement(subCarpetas.remove(0)); 
            }
            
            return modelo;
        }else{
            
            return modelo;
        }
        
    }
    
    private List<String> buscarSubCarpetas(Queue <String> directoriosRaiz, List<String> subCarpetas, int tamModelo){
        
        if(!directoriosRaiz.isEmpty()){
            String carpetaRaiz = directoriosRaiz.remove();

            File directorioRaiz = new File(carpetaRaiz);
            
            File[] archivosCarpetaRaíz = directorioRaiz.listFiles();
            
            for(File archivo : archivosCarpetaRaíz){
                
                if(archivo.isDirectory() ){
                    
                    directoriosRaiz.add(archivo.getAbsolutePath() + "\\");
                    subCarpetas.add(archivo.getAbsolutePath() + "\\");
                    buscarSubCarpetas(directoriosRaiz, subCarpetas, tamModelo);

                }
                
            }
            
        }else{
            return subCarpetas;
        }
            
        return subCarpetas;
    }
    
    public DefaultListModel agregarArchivo(DefaultListModel modelo){
        String rutas[] = explorador.abrirExploradorArchivos(abrirArchivosEn);
        
        if(!rutas[0].equals("")){
            for(int indice = 0; indice < rutas.length; indice++){
                modelo.addElement(rutas[indice]);
            }
        }

        return modelo;
    }
    
    public boolean eliminarCarpeta(DefaultListModel modelo, JList lista){
        int indiceElementoSeleccionado = lista.getSelectedIndex();
        
        if(indiceElementoSeleccionado != -1){
            modelo.remove(indiceElementoSeleccionado);
            return true;
        }else{
            return false;
        }
    }
    
    public DefaultListModel subirElementoList(DefaultListModel modelo, JList lista){
        
        String rutaCima;
        
        int indiceElementoSeleccionado = lista.getSelectedIndex();
        
        if(indiceElementoSeleccionado != -1 && indiceElementoSeleccionado != 0){
            //Obtengo el elemento superior.
            rutaCima = modelo.getElementAt( indiceElementoSeleccionado - 1 ).toString();
            //Elimino el elemento superior.
            modelo.remove(indiceElementoSeleccionado - 1);
            //El elemento superior es agregado en la posición actual de tal manera que queda debajo del antiguo elemento seleccionado.
            modelo.add(indiceElementoSeleccionado, rutaCima);
        }
        
        return modelo;
    }
    
    public DefaultListModel bajarElementoList(DefaultListModel modelo, JList lista){
        
        String rutaSeleccionado;
        
        int indiceElementoSeleccionado = lista.getSelectedIndex();
        
        if(indiceElementoSeleccionado != -1 && indiceElementoSeleccionado != (modelo.getSize() - 1)){
            //Obtengo el elemento seleccionado.
            rutaSeleccionado = modelo.getElementAt( indiceElementoSeleccionado ).toString();
            //Elimino el elemento seleccionado.
            modelo.remove(indiceElementoSeleccionado);
            //El elemento seleccionado es agregado en la posición siguiente de tal manera que queda debajo del antiguo elemento seleccionado.
            modelo.add(indiceElementoSeleccionado + 1, rutaSeleccionado);
        }
        
        return modelo;
    }
    //////////////////
    public Queue<String> obtenerDireccionesLista(DefaultListModel modelo){
        Queue <String> direcciones = new LinkedList<String>();
        int cantidadCarpetas = modelo.getSize();
        
        for (int indice = 0; indice < cantidadCarpetas; indice++) {
            
            if(explorador.existeArchivo(modelo.getElementAt(indice).toString())){
                direcciones.add(modelo.getElementAt(indice).toString());
            }

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
