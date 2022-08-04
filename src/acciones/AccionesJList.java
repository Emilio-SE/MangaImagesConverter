package acciones;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class AccionesJList {
    
    private String abrirCarpetasEn;
    private String abrirArchivosEn;
    private AccionesExploradorArchivos explorador;
    private Stack<instanciasJList>accionesPreviasJList;
    private Stack<instanciasJList>accionesSiguientesJList;
    
    public AccionesJList(String abrirCarpetasEn, String abrirArchivosEn){
        explorador = new AccionesExploradorArchivos();
        this.abrirCarpetasEn = abrirCarpetasEn;
        this.abrirArchivosEn = abrirArchivosEn;
        accionesPreviasJList = new Stack<instanciasJList>();
        accionesSiguientesJList = new Stack<instanciasJList>();
        
    }
    
    public DefaultListModel agregarArchivo(DefaultListModel modelo){
        String rutas[] = explorador.abrirExploradorArchivos(abrirArchivosEn);
        colocarArchivosEnModelo(modelo, rutas);
        return modelo;
    }
    
    public DefaultListModel agregarArchivo(DefaultListModel modelo, List<File> rutas){
        
        List<String> listadoRutas = new ArrayList();
        
        for(File ruta : rutas){
            if(ruta.isFile()){
                if( explorador.esArchivoValido(ruta.toString()) ){
                    listadoRutas.add( ruta.toString() );
                }
            }
        }
        if(listadoRutas.isEmpty()){
            listadoRutas.add("");
        }
        
        String[] listadoString = new String [listadoRutas.size()];
        listadoString = AccionesGenerales.ordenarListado(listadoRutas);
        
        colocarArchivosEnModelo(modelo, listadoString);
        
        return modelo;
    }
    
    private void colocarArchivosEnModelo(DefaultListModel modelo, String[] rutasCarpetas){
        if(!rutasCarpetas[0].equals("")){
            
            int contadorElementosAgregados = 0;
            int cantidadElementosArray = rutasCarpetas.length;
            
            for(String ruta : rutasCarpetas){
                contadorElementosAgregados++;
                modelo.addElement(ruta);
                if(contadorElementosAgregados == cantidadElementosArray || contadorElementosAgregados == 1){
                    agregarNuevaAccionAPila("agregar", ruta, modelo.indexOf(ruta), cantidadElementosArray);
                }else{
                    agregarNuevaAccionAPila("agregar", ruta, modelo.indexOf(ruta), -1);
                }
            }
            
        }
    }
    
    public DefaultListModel agregarCarpetas(DefaultListModel modelo){
        List<String> rutasCarpetas = new ArrayList<>();
        rutasCarpetas = Arrays.asList( explorador.abrirExploradorCarpetas(abrirCarpetasEn) );
        
        AccionesGenerales.ordenarListado( rutasCarpetas );
        colcocarCarpetasEnModelo(modelo, rutasCarpetas);

        return modelo;
    }
    
    public DefaultListModel agregarCarpetas(DefaultListModel modelo, List<File> rutas){
        List<File> rutasArchivos = new ArrayList<>();
        List<String> rutasCarpetas = new ArrayList<>();
        rutasArchivos = rutas;
        
        for(File ruta : rutasArchivos){
            if(ruta.isDirectory()){
                rutasCarpetas.add( ruta.toString() );
            }
        }
        
        if(rutasCarpetas.isEmpty()){
            rutasCarpetas.add("");
        }
        
        AccionesGenerales.ordenarListado( rutasCarpetas );
        colcocarCarpetasEnModelo(modelo, rutasCarpetas);

        return modelo;
    }
    
    private void colcocarCarpetasEnModelo(DefaultListModel modelo, List<String> rutasCarpetas){
        if( !rutasCarpetas.get(0).equals("") ){ 
                
                int contadorElementosAgregados = 0;
                int cantidadElementosArray = rutasCarpetas.size();
                
                for(String rutaCarpeta : rutasCarpetas){
                    contadorElementosAgregados++;
                    
                    modelo.addElement(rutaCarpeta);
                    
                    if(contadorElementosAgregados == cantidadElementosArray || contadorElementosAgregados == 1){
                        agregarNuevaAccionAPila("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), cantidadElementosArray);
                    }else{
                        agregarNuevaAccionAPila("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), -1);
                    }
                }

        }
    }
    
    public DefaultListModel agregarCarpetasRecursivamente(DefaultListModel modelo){
        String[] rutasCarpetas = explorador.abrirExploradorCarpetas( abrirCarpetasEn );
        List<String> rutasSubCarpetas = new ArrayList<>();
        Queue<String> rutasCarpetasRaices = new LinkedList<String>();
        
        if( !rutasCarpetas[0].equals("") ){ 
            
            for(String rutaCarpeta : rutasCarpetas){
                    rutasSubCarpetas.add( rutaCarpeta );
                    rutasCarpetasRaices.add( rutaCarpeta );
            }
            
            buscarSubCarpetas(rutasCarpetasRaices, rutasSubCarpetas);
            AccionesGenerales.ordenarListado( rutasSubCarpetas );
            
            String rutaCarpeta;
            int contadorElementosAgregados = 0;
            int cantidadElementosArray = rutasSubCarpetas.size();
            
            while(!rutasSubCarpetas.isEmpty()){
                
                contadorElementosAgregados++;
                
                rutaCarpeta = rutasSubCarpetas.remove(0);
                modelo.addElement( rutaCarpeta ); 
                
                if(contadorElementosAgregados == cantidadElementosArray || contadorElementosAgregados == 1){
                    agregarNuevaAccionAPila("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), cantidadElementosArray);
                }else{
                    agregarNuevaAccionAPila("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), -1);
                }
                
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
    
    public boolean eliminarElementoJList(DefaultListModel modelo, JList lista){
        int[] indicesElementosSeleccionados = lista.getSelectedIndices();
        int cantidadElementosArray = indicesElementosSeleccionados.length;
        int elementosEliminados = 0, indiceAEliminar;
        String rutaCarpeta;
        boolean bool = true;
        
        for(int indiceElementoSeleccionado : indicesElementosSeleccionados){
            
            if(indiceElementoSeleccionado != -1){
                
                try {
                    
                    indiceAEliminar = indiceElementoSeleccionado - elementosEliminados;
                    rutaCarpeta = modelo.remove(indiceAEliminar).toString();
                    elementosEliminados++;
                    
                    if(elementosEliminados == cantidadElementosArray || elementosEliminados == 1){
                        agregarNuevaAccionAPila("eliminar", rutaCarpeta, indiceAEliminar, cantidadElementosArray);
                    }else{
                        agregarNuevaAccionAPila("eliminar", rutaCarpeta, indiceAEliminar, -1);
                    }
                    
                } catch (IndexOutOfBoundsException e) {
                    bool = false;
                }
                
            }else{
                bool = false;
            }

        } 
        
        if(modelo.getSize() > 0){
            int foco = indicesElementosSeleccionados[indicesElementosSeleccionados.length - 1] - elementosEliminados;
        
            if(foco < modelo.getSize() - 1){
                lista.setSelectedIndex(foco + 1);
            }else if(foco == modelo.getSize() - 1){
                lista.setSelectedIndex(foco);
            }
        }
        
        return bool;
    }

    public void limpiarJList(DefaultListModel modelo, JList lista){
        
        int cantidadElementos = modelo.size();
        int ultimoIndice = cantidadElementos - 1;
        String rutaCarpeta;
        
        for(int indiceElementoAEliminar = ultimoIndice; indiceElementoAEliminar >=0; indiceElementoAEliminar--){
            
            rutaCarpeta = modelo.remove( indiceElementoAEliminar ).toString();
            
            if(indiceElementoAEliminar == 0 || indiceElementoAEliminar == ultimoIndice){
                agregarNuevaAccionAPila("limpiar", rutaCarpeta, indiceElementoAEliminar, cantidadElementos);
            }else{
                agregarNuevaAccionAPila("limpiar", rutaCarpeta, indiceElementoAEliminar, -1);
            }            
                
        }
        
    }
    
    public DefaultListModel subirElementoJList(DefaultListModel modelo, JList lista){
        int[] indicesElementosSeleccionados = lista.getSelectedIndices();
        int cantidadElementosSeleccionados = indicesElementosSeleccionados.length;
        int[] indicesSeleccionados = new int[cantidadElementosSeleccionados];
        int indice = 0, cantidadInicialEnBloque = 0, indiceLimiteBloque = 0, indiceSuperior;
        String rutaSuperior, rutaActual;
        boolean esPrimerElementoModificado = true;
        
        for(int indiceElementoSeleccionado : indicesElementosSeleccionados){
            if(indiceElementoSeleccionado != -1 && indiceElementoSeleccionado == 0){ //Inicia en el indice cero
                
                indicesSeleccionados[indice] = indiceElementoSeleccionado;
                indiceLimiteBloque++;
                
            }else if(indiceElementoSeleccionado != indiceLimiteBloque){ //Inicia en indice cero pero tiene indices seleccionados fuera del bloque cero
                
                indiceSuperior = indiceElementoSeleccionado - 1;

                rutaSuperior = modelo.getElementAt( indiceSuperior ).toString();
                rutaActual = modelo.getElementAt( indiceElementoSeleccionado ).toString();

                cambioDePosicionElementosJList(modelo, rutaSuperior, rutaActual, indiceSuperior, indiceElementoSeleccionado);

                indicesSeleccionados[indice] = indiceSuperior;

                if(esPrimerElementoModificado){
                    cantidadInicialEnBloque = indiceLimiteBloque;
                    esPrimerElementoModificado = false;
                }
                
                int posibleIndiceLimiteBloque = indiceSuperior - 1;
                
                if( posibleIndiceLimiteBloque == indiceLimiteBloque){ //crea un nuevo bloque cero y/o ingresa un indice dentro del bloque cero.
                    indiceLimiteBloque++;
                }
                
                agregarNuevaAccionAPila("sube", rutaActual, indiceSuperior, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                agregarNuevaAccionAPila("baja", rutaSuperior, indiceElementoSeleccionado, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                
            }else if(indiceElementoSeleccionado == indiceLimiteBloque){ //Para elementos que no sean indice cero, pero esten dentro del bloque cero.
                indicesSeleccionados[indice] = indiceElementoSeleccionado;
                indiceLimiteBloque++;
            }
            indice++;
        }
        
        lista.setSelectedIndices( indicesSeleccionados );
        
        return modelo;
    }

    public DefaultListModel bajarElementoJList(DefaultListModel modelo, JList lista){
        int[] indicesElementosSeleccionados = lista.getSelectedIndices();
        int cantidadElementosSeleccionados = indicesElementosSeleccionados.length;
        int[] indicesSeleccionados = new int[cantidadElementosSeleccionados];
        int ultimoElemento = modelo.getSize() - 1, contadorElementosEnBloque=0, cantidadInicialEnBloque = 0;
        int indiceElementoSeleccionado, indiceInferior, indiceLimiteBloque=ultimoElemento;
        String rutaAbajo, rutaActual;
        boolean esPrimerElementoModificado = true;
        
        for(int indice = indicesSeleccionados.length - 1; indice >= 0; indice--){
            indiceElementoSeleccionado = indicesElementosSeleccionados[indice];
            
            if(indiceElementoSeleccionado != -1 && indiceElementoSeleccionado == ultimoElemento){
                indicesSeleccionados[indice] = indiceElementoSeleccionado;
                indiceLimiteBloque--;
                contadorElementosEnBloque++;
            }else if(indiceElementoSeleccionado != indiceLimiteBloque){
            
                indiceInferior = indiceElementoSeleccionado + 1;
                
                rutaAbajo = modelo.getElementAt( indiceInferior ).toString();
                rutaActual = modelo.getElementAt( indiceElementoSeleccionado ).toString();
                
                cambioDePosicionElementosJList(modelo, rutaActual, rutaAbajo, indiceElementoSeleccionado, indiceInferior);
                
                indicesSeleccionados[indice] = indiceInferior;

                if(esPrimerElementoModificado){
                    cantidadInicialEnBloque = contadorElementosEnBloque;
                    esPrimerElementoModificado = false;
                }
                
                if( (indiceInferior+1) == indiceLimiteBloque){ //crea un nuevo bloque cero y/o ingresa un indice dentro del bloque cero.
                    indiceLimiteBloque--;
                    contadorElementosEnBloque++;
                }
                
                agregarNuevaAccionAPila("baja", rutaActual, indiceInferior, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                agregarNuevaAccionAPila("sube", rutaAbajo, indiceElementoSeleccionado, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                
            }else if(indiceElementoSeleccionado == indiceLimiteBloque){ //Para elementos que no sean indice cero, pero esten dentro del bloque cero.
                indicesSeleccionados[indice] = indiceElementoSeleccionado;
                indiceLimiteBloque--;
                contadorElementosEnBloque++;
            }

        }
        lista.setSelectedIndices( indicesSeleccionados );

        return modelo;
    }
    
    public void deshacerJList(DefaultListModel modelo){
        
        if(!accionesPreviasJList.isEmpty()){

            try{
                
                instanciasJList accionAnterior = accionesPreviasJList.pop();
                accionesSiguientesJList.add(accionAnterior);
            
                switch ( accionAnterior.getAccion() ) {
                    case "agregar":
                        eliminarElementosJList(modelo, accionAnterior, accionesPreviasJList, accionesSiguientesJList);
                        break;
                    case "eliminar":
                        agregarElementosJList(modelo, accionAnterior, accionesPreviasJList, accionesSiguientesJList, false);
                        break;
                    case "sube":
                        intercambioElementosJList(modelo, accionAnterior, accionesPreviasJList, accionesSiguientesJList, "D");
                        break;
                    case "baja":
                        intercambioElementosJList(modelo, accionAnterior, accionesPreviasJList, accionesSiguientesJList, "D");
                        break;
                    case "limpiar":
                        agregarElementosJList(modelo, accionAnterior, accionesPreviasJList, accionesSiguientesJList, true);
                        break;
                    default:
                        throw new AssertionError();
                }
                
            }catch(Exception e){
                
                if(!accionesPreviasJList.isEmpty()){
                    accionesPreviasJList.clear();
                }
                if(!accionesSiguientesJList.isEmpty()){
                    accionesSiguientesJList.clear();
                }
                
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el proceso Undo/Redo. Las pilas se han limpiado.\n\n" + e.getMessage(), "Error en el sistema Undo/Redo", JOptionPane.ERROR_MESSAGE);
                
            }
            
        }
        
    }
    
    public void rehacerJList(DefaultListModel modelo){
        
        if(!accionesSiguientesJList.isEmpty()){
            
            try{
                
                instanciasJList accionSiguiente = accionesSiguientesJList.pop();
                accionesPreviasJList.add(accionSiguiente);
            
                switch ( accionSiguiente.getAccion() ) {
                    case "agregar":
                        agregarElementosJList(modelo, accionSiguiente, accionesSiguientesJList, accionesPreviasJList, false);
                        break;
                    case "eliminar":
                        eliminarElementosJList(modelo, accionSiguiente, accionesSiguientesJList, accionesPreviasJList);
                        break;
                    case "sube":
                        intercambioElementosJList(modelo, accionSiguiente, accionesSiguientesJList, accionesPreviasJList, "R");
                        break;
                    case "baja":    
                        intercambioElementosJList(modelo, accionSiguiente, accionesSiguientesJList, accionesPreviasJList, "R");
                        break;
                    case "limpiar":
                        eliminarElementosJList(modelo, accionSiguiente, accionesSiguientesJList, accionesPreviasJList);
                        break;
                    default:
                        throw new AssertionError();
                }
                
            }catch(Exception e){
                if(!accionesPreviasJList.isEmpty()){
                    accionesPreviasJList.clear();
                }
                if(!accionesSiguientesJList.isEmpty()){
                    accionesSiguientesJList.clear();
                }
                
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el proceso Undo/Redo. Las pilas se han limpiado.\n\n" + e.getMessage(), "Error en el sistema Undo/Redo", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }
    
    private void agregarElementosJList(DefaultListModel modelo, instanciasJList accion, Stack<instanciasJList> pilaAccionesA, Stack<instanciasJList> pilaAccionesB, boolean esAddElement){
        
        if( esAddElement ){
            modelo.addElement( accion.getDireccion() ); 
        }else{
            modelo.add( accion.getIndice(), accion.getDireccion() );
        }
        
        int cantidadElementosModificados = accion.getElementosAfectados();
        
        for(int i = 0; i < (cantidadElementosModificados - 1); i++){
            accion = pilaAccionesA.pop();
            pilaAccionesB.add( accion );
            
            if( esAddElement ){
                modelo.addElement( accion.getDireccion() ); 
            }else{
                modelo.add( accion.getIndice(), accion.getDireccion() );
            }  
        }
        
    }
    
    private void eliminarElementosJList(DefaultListModel modelo, instanciasJList accion, Stack<instanciasJList> pilaAccionesA, Stack<instanciasJList> pilaAccionesB){
        modelo.remove( accion.getIndice() );

        int cantidadElementosModificados = accion.getElementosAfectados();
        
        for(int i = 0; i < (cantidadElementosModificados - 1); i++){
            accion = pilaAccionesA.pop();
            pilaAccionesB.add( accion );
            modelo.remove( accion.getIndice() );
        }
    }
    
    private void intercambioElementosJList(DefaultListModel modelo, instanciasJList accion, Stack<instanciasJList> pilaAccionesA, Stack<instanciasJList> pilaAccionesB, String procedencia){
        instanciasJList instanciaAnteriorAnterior = pilaAccionesA.pop();
        pilaAccionesB.add( instanciaAnteriorAnterior );

        int cantidadElementosModificados = accion.getElementosAfectados();

        if( procedencia.equals("D") ){
            cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), accion.getIndice(), instanciaAnteriorAnterior.getIndice());
        }else{
            cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), instanciaAnteriorAnterior.getIndice(), accion.getIndice());
        }

        for(int i = 0; i < (cantidadElementosModificados - 1); i++){

                accion = pilaAccionesA.pop();
                pilaAccionesB.add( accion );
                instanciaAnteriorAnterior = pilaAccionesA.pop();
                pilaAccionesB.add( instanciaAnteriorAnterior );
                
                if( procedencia.equals("D") ){
                    cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), accion.getIndice(), instanciaAnteriorAnterior.getIndice());
                }else{
                    cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), instanciaAnteriorAnterior.getIndice(), accion.getIndice());
                }
                
        }
    }
    
    private void cambioDePosicionElementosJList(DefaultListModel modelo, String elementoA, String elementoB, int indiceA, int indiceB){
        modelo.setElementAt(elementoA, indiceB);
        modelo.setElementAt(elementoB, indiceA);
    }
    
    public void agregarNuevaAccionAPila(String accion, String direccion, int indiceDireccion, int cantElementosAfectados){
        accionesPreviasJList.add( new instanciasJList(accion, direccion, indiceDireccion, cantElementosAfectados) );
        
        if(!accionesSiguientesJList.isEmpty()){
            accionesSiguientesJList.clear();
        }
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
    
    public Queue<String> obtenerDireccionesJLista(DefaultListModel modelo){
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
}
