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
import java.util.LinkedList;
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
        String[] rutas = explorador.abrirExploradorCarpetas(abrirCarpetasEn);
        int tamLista = modelo.getSize();
        if(!rutas[0].equals("")){ 
            for(int indice = 0; indice < rutas.length; indice++){
                if( (tamLista + indice) > 15){
                    JOptionPane.showMessageDialog(null, "Limite de 16 carpetas");
                    break;
                }else{
                    modelo.addElement(rutas[indice]);
                }
            }
        }

        return modelo;
    }
    
    public DefaultListModel agregarCarpetasRecursivamente(DefaultListModel modelo){
        String[] rutas = explorador.abrirExploradorCarpetas(abrirCarpetasEn);
        Queue<String> carpetas = new LinkedList<String>();
        
        
        int tamLista = modelo.getSize();
        
        if(!rutas[0].equals("")){ 
            
            for(int indice = 0; indice < rutas.length; indice++){
                
                if( (tamLista + indice) > 15){
                    JOptionPane.showMessageDialog(null, "Limite de 16 carpetas");
                    
                    break;
                }else{
                    
                    modelo.addElement(rutas[indice]);
                    carpetas.add(rutas[indice]);
                }
            }
            
            buscarSubCarpetas(carpetas, modelo);
            
            return modelo;
        }else{
            
            return modelo;
        }
        
    }
    
    private DefaultListModel buscarSubCarpetas(Queue <String> directoriosRaiz, DefaultListModel modelo){
        
        if(!directoriosRaiz.isEmpty()){
            String carpetaRaiz = directoriosRaiz.remove();

            File directorioRaiz = new File(carpetaRaiz);
            
            File[] archivosCarpetaRaíz = directorioRaiz.listFiles();
            
            for(File archivo : archivosCarpetaRaíz){
                /*if(archivo.isDirectory() && modelo.getSize() < 16){
                    directoriosRaiz.add(archivo.getAbsolutePath() + "\\");
                    modelo.addElement(archivo.getAbsolutePath() + "\\");
                    buscarSubCarpetas(directoriosRaiz, modelo);
                }*/
                
                if(archivo.isDirectory() ){
                    
                    if(modelo.getSize() < 16){
                        directoriosRaiz.add(archivo.getAbsolutePath() + "\\");
                        modelo.addElement(archivo.getAbsolutePath() + "\\");
                        buscarSubCarpetas(directoriosRaiz, modelo);
                        //System.out.println(archivo.getAbsolutePath());
                    }else{
                        JOptionPane.showMessageDialog(null, "No se han podido agregar las carpetas a partir de: \n" + archivo.getAbsolutePath() + "\nLimite de 16 carpetas.\n" );
                        break;
                    }

                }
                
            }
            
        }else{
            return modelo;
        }
            
        return modelo;
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
        //String[] direcciones = new String[cantidadCarpetas];
        
        for (int indice = 0; indice < cantidadCarpetas; indice++) {
            
            if(explorador.existeArchivo(modelo.getElementAt(indice).toString())){
                direcciones.add(modelo.getElementAt(indice).toString());
            }else{
                //JOptionPane.showMessageDialog(null, "La dirección " + );
            }

        }
        
        return direcciones; 
    }
    /*
    public DefaultListModel colocarImagenes(DefaultListModel modelo, Queue<String> direccionesCarpetas){
        
        int cantidadCarpetas = direccionesCarpetas.size();
        String[] listado;
        String nombreImagen;
        String direccionImagen;
        String direccionCarpeta;
        String[] extensionesPermitidas = explorador.getExtensionesPermitidas();
        
        //Recaba los nombres de las imagenes por carpeta.
        for (int indiceCarpeta = 0; indiceCarpeta < cantidadCarpetas; indiceCarpeta++) {
            //Obtiene el contenido de la carpeta.
            direccionCarpeta = direccionesCarpetas.remove();
            File carpeta = new File( direccionCarpeta );
            
            //Enlista el contenido de la carpeta.
            listado = carpeta.list();
            //Filtra el contenido de la carpeta dejando solo las extensiones validas.
            for (int indiceImagen = 0; indiceImagen < listado.length; indiceImagen++) {
                //Selecciona la imagen, una por una.
                nombreImagen = listado[indiceImagen];
                //Verifica que contengan la extensión.
                int i = 0;
                while(i < extensionesPermitidas.length){
                    
                    //Compara las terminaciones.
                    String extensionMin = "." + extensionesPermitidas[i].toLowerCase();
                    String extensionMayu = "." + extensionesPermitidas[i].toUpperCase();
                    
                    if(nombreImagen.endsWith( extensionMin ) || nombreImagen.endsWith( extensionMayu )){
                        //Coloca la dirección completa.
                        direccionImagen =  direccionCarpeta + nombreImagen;
                        //La agrega al modelo.
                        modelo.addElement(direccionImagen);
                        break;//Termina y continua con el siguiente.
                    }
                    
                    i++;//Continua con la siguiente extensión.
                }
                
            }

        }
        
        return modelo;
        
    }*/
    /////////////////////////////////////////////////////////////////////////////////////
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
            listado = carpeta.list();
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
