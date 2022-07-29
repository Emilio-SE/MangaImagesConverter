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
import java.util.Stack;

public class AccionesComponentes{
    
    private String abrirCarpetasEn;
    private String abrirArchivosEn;
    private AccionesExploradorArchivos explorador;
    private Stack<instanciasJList>instanciaAnteriorJList;
    private Stack<instanciasJList>instanciaSiguienteJList;
    
    
            
    public AccionesComponentes(String abrirCarpetasEn, String abrirArchivosEn){
        explorador = new AccionesExploradorArchivos();
        this.abrirCarpetasEn = abrirCarpetasEn;
        this.abrirArchivosEn = abrirArchivosEn;
        instanciaAnteriorJList = new Stack<instanciasJList>();
        instanciaSiguienteJList = new Stack<instanciasJList>();
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
        rutasCarpetas = Arrays.asList( explorador.abrirExploradorCarpetas(abrirCarpetasEn) );
        
        AccionesGenerales.ordenarListado( rutasCarpetas );
        
        if( !rutasCarpetas.get(0).equals("") ){ 
                
                int contadorElementosAgregados = 0;
                int cantidadElementosArray = rutasCarpetas.size();
                
                for(String rutaCarpeta : rutasCarpetas){
                    contadorElementosAgregados++;
                    
                    modelo.addElement(rutaCarpeta);
                    
                    if(contadorElementosAgregados == cantidadElementosArray || contadorElementosAgregados == 1){
                        actualizarInstanciaJList("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), cantidadElementosArray);
                    }else{
                        actualizarInstanciaJList("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), -1);
                    }
                }

        }

        return modelo;
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
                    actualizarInstanciaJList("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), cantidadElementosArray);
                }else{
                    actualizarInstanciaJList("agregar", rutaCarpeta, modelo.indexOf(rutaCarpeta), -1);
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
    
    public DefaultListModel agregarArchivo(DefaultListModel modelo){
        String rutas[] = explorador.abrirExploradorArchivos(abrirArchivosEn);
        
        if(!rutas[0].equals("")){
            
            int contadorElementosAgregados = 0;
            int cantidadElementosArray = rutas.length;
            
            for(String ruta : rutas){
                contadorElementosAgregados++;
                modelo.addElement(ruta);
                if(contadorElementosAgregados == cantidadElementosArray || contadorElementosAgregados == 1){
                    actualizarInstanciaJList("agregar", ruta, modelo.indexOf(ruta), cantidadElementosArray);
                }else{
                    actualizarInstanciaJList("agregar", ruta, modelo.indexOf(ruta), -1);
                }
            }
            
        }

        return modelo;
    }
    
    public boolean eliminarElementoList(DefaultListModel modelo, JList lista){
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
                        actualizarInstanciaJList("eliminar", rutaCarpeta, indiceAEliminar, cantidadElementosArray);
                    }else{
                        actualizarInstanciaJList("eliminar", rutaCarpeta, indiceAEliminar, -1);
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

    public DefaultListModel subirElementoList(DefaultListModel modelo, JList lista){
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
                
                actualizarInstanciaJList("sube", rutaActual, indiceSuperior, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                actualizarInstanciaJList("baja", rutaSuperior, indiceElementoSeleccionado, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                
            }else if(indiceElementoSeleccionado == indiceLimiteBloque){ //Para elementos que no sean indice cero, pero esten dentro del bloque cero.
                indicesSeleccionados[indice] = indiceElementoSeleccionado;
                indiceLimiteBloque++;
            }
            indice++;
        }
        
        lista.setSelectedIndices( indicesSeleccionados );
        
        return modelo;
    }

    public DefaultListModel bajarElementoList(DefaultListModel modelo, JList lista){
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
                
                actualizarInstanciaJList("baja", rutaActual, indiceInferior, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                actualizarInstanciaJList("sube", rutaAbajo, indiceElementoSeleccionado, cantidadElementosSeleccionados - cantidadInicialEnBloque);
                
            }else if(indiceElementoSeleccionado == indiceLimiteBloque){ //Para elementos que no sean indice cero, pero esten dentro del bloque cero.
                indicesSeleccionados[indice] = indiceElementoSeleccionado;
                indiceLimiteBloque--;
                contadorElementosEnBloque++;
            }

        }
        lista.setSelectedIndices( indicesSeleccionados );

        return modelo;
    }
    
    public void limpiarJList(DefaultListModel modelo, JList lista){
        
        int cantidadElementos = modelo.size();
        int ultimoIndice = cantidadElementos - 1;
        String rutaCarpeta;
        
        for(int indiceElementoAEliminar = ultimoIndice; indiceElementoAEliminar >=0; indiceElementoAEliminar--){
            
            rutaCarpeta = modelo.remove( indiceElementoAEliminar ).toString();
            
            if(indiceElementoAEliminar == 0 || indiceElementoAEliminar == ultimoIndice){
                actualizarInstanciaJList("limpiar", rutaCarpeta, indiceElementoAEliminar, cantidadElementos);
            }else{
                actualizarInstanciaJList("limpiar", rutaCarpeta, indiceElementoAEliminar, -1);
            }            
                
        }
        
    }
    
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

    public void verificarTeclaIngresada(KeyEvent evt, int teclaPresionada){        
        if ( (teclaPresionada < KeyEvent.VK_0 || teclaPresionada > KeyEvent.VK_9) && ( teclaPresionada != KeyEvent.VK_COMMA ) && ( teclaPresionada != KeyEvent.VK_BACK_SPACE ) && ( teclaPresionada != KeyEvent.VK_SPACE )) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }
    
    public void deshacerJList(DefaultListModel modelo){
        
        if(!instanciaAnteriorJList.isEmpty()){
            int cantidadElementosModificados = 0;
            instanciasJList instanciaAnterior = instanciaAnteriorJList.pop();
            instanciaSiguienteJList.add(instanciaAnterior);

            switch ( instanciaAnterior.getAccion() ) {
                case "agregar":
                    
                    modelo.remove(instanciaAnterior.getIndice());

                    cantidadElementosModificados = instanciaAnterior.getElementosAfectados();
                    for(int i = 0; i < (cantidadElementosModificados - 1); i++){
                        instanciaAnterior = instanciaAnteriorJList.pop();
                        instanciaSiguienteJList.add(instanciaAnterior);
                        modelo.remove(instanciaAnterior.getIndice());
                    }

                    break;
                case "eliminar":
                    
                    modelo.add(instanciaAnterior.getIndice(), instanciaAnterior.getDireccion());

                    cantidadElementosModificados = instanciaAnterior.getElementosAfectados();
                    for(int i = 0; i < (cantidadElementosModificados - 1); i++){
                        instanciaAnterior = instanciaAnteriorJList.pop();
                        instanciaSiguienteJList.add(instanciaAnterior);
                        modelo.add(instanciaAnterior.getIndice(), instanciaAnterior.getDireccion()); 
                    }

                    break;
                case "sube":
                    
                    cambioAInstanciaAnterior(modelo, instanciaAnterior);
                    
                    break;
                case "baja":    
                    
                    cambioAInstanciaAnterior(modelo, instanciaAnterior);

                    break;
                case "limpiar":
                    
                    modelo.addElement(instanciaAnterior.getDireccion()); 

                    cantidadElementosModificados = instanciaAnterior.getElementosAfectados();
                    for(int i = 0; i < (cantidadElementosModificados - 1); i++){
                        instanciaAnterior = instanciaAnteriorJList.pop();
                        instanciaSiguienteJList.add(instanciaAnterior);
                        modelo.addElement(instanciaAnterior.getDireccion()); 
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
        
    }
    
    public void rehacerJList(DefaultListModel modelo){
        
        if(!instanciaSiguienteJList.isEmpty()){
            instanciasJList instanciaSiguiente = instanciaSiguienteJList.pop();
            instanciaAnteriorJList.add(instanciaSiguiente);
            int cantidadElementosModificados;

            switch (instanciaSiguiente.getAccion()) {
                case "agregar":
                    modelo.add(instanciaSiguiente.getIndice(), instanciaSiguiente.getDireccion()); 

                    cantidadElementosModificados = instanciaSiguiente.getElementosAfectados();
                    for(int i = 0; i < (cantidadElementosModificados - 1); i++){
                        instanciaSiguiente = instanciaSiguienteJList.pop();
                        instanciaAnteriorJList.add(instanciaSiguiente);
                        modelo.add(instanciaSiguiente.getIndice(), instanciaSiguiente.getDireccion()); 
                    }
                    
                    break;
                case "eliminar":
                    modelo.remove(instanciaSiguiente.getIndice());
                    
                    cantidadElementosModificados = instanciaSiguiente.getElementosAfectados();
                    for(int i = 0; i < (cantidadElementosModificados - 1); i++){
                        instanciaSiguiente = instanciaSiguienteJList.pop();
                        instanciaAnteriorJList.add(instanciaSiguiente);
                        modelo.remove(instanciaSiguiente.getIndice());
                    }
                    
                    break;
                case "sube":
                    cambioAInstanciaSiguiente(modelo, instanciaSiguiente);
                    break;
                case "baja":    
                    cambioAInstanciaSiguiente(modelo, instanciaSiguiente);
                    break;
                case "limpiar":
                    modelo.remove(instanciaSiguiente.getIndice()); 

                    cantidadElementosModificados = instanciaSiguiente.getElementosAfectados();
                    for(int i = 0; i < (cantidadElementosModificados - 1); i++){
                        instanciaSiguiente = instanciaSiguienteJList.pop();
                        instanciaAnteriorJList.add(instanciaSiguiente);
                        modelo.remove(instanciaSiguiente.getIndice());
                    }
                    break;
                default:
                    throw new AssertionError();
            }
        }
        
    }
    
    private void cambioAInstanciaAnterior(DefaultListModel modelo, instanciasJList instanciaAnterior){
        instanciasJList instanciaAnteriorAnterior = instanciaAnteriorJList.pop();
        instanciaSiguienteJList.add(instanciaAnteriorAnterior);

        int cantidadElementosModificados = instanciaAnterior.getElementosAfectados();

        cambioDePosicionElementosJList(modelo, instanciaAnterior.getDireccion(), instanciaAnteriorAnterior.getDireccion(), instanciaAnterior.getIndice(), instanciaAnteriorAnterior.getIndice());

        for(int i = 0; i < (cantidadElementosModificados - 1); i++){

                instanciaAnterior = instanciaAnteriorJList.pop();
                instanciaSiguienteJList.add(instanciaAnterior);
                instanciaAnteriorAnterior = instanciaAnteriorJList.pop();
                instanciaSiguienteJList.add(instanciaAnteriorAnterior);

                cambioDePosicionElementosJList(modelo, instanciaAnterior.getDireccion(), instanciaAnteriorAnterior.getDireccion(), instanciaAnterior.getIndice(), instanciaAnteriorAnterior.getIndice());

        }
    }
    
    private void cambioAInstanciaSiguiente(DefaultListModel modelo, instanciasJList instanciaSiguiente){
        
        instanciasJList instanciaSiguienteSiguiente = instanciaSiguienteJList.pop();
        instanciaAnteriorJList.add(instanciaSiguienteSiguiente);

        int cantidadElementosModificados = instanciaSiguiente.getElementosAfectados();

        cambioDePosicionElementosJList(modelo, instanciaSiguiente.getDireccion(), instanciaSiguienteSiguiente.getDireccion(),instanciaSiguienteSiguiente.getIndice(), instanciaSiguiente.getIndice());
        
        for(int i = 0; i < (cantidadElementosModificados - 1); i++){

                instanciaSiguiente = instanciaSiguienteJList.pop();
                instanciaAnteriorJList.add(instanciaSiguiente);
                instanciaSiguienteSiguiente = instanciaSiguienteJList.pop();
                instanciaAnteriorJList.add(instanciaSiguienteSiguiente);

                cambioDePosicionElementosJList(modelo, instanciaSiguiente.getDireccion(), instanciaSiguienteSiguiente.getDireccion(),instanciaSiguienteSiguiente.getIndice(), instanciaSiguiente.getIndice());
        }
    }
    
    private void cambioDePosicionElementosJList(DefaultListModel modelo, String elementoA, String ElementoB, int indiceA, int indiceB){
        modelo.setElementAt(elementoA, indiceB);
        modelo.setElementAt(ElementoB, indiceA);
    }
    
    public void actualizarInstanciaJList(String accion, String direccion, int indice, int cantElementosAfectados){
        instanciaAnteriorJList.add( new instanciasJList(accion, direccion, indice, cantElementosAfectados) );
        
        if(!instanciaSiguienteJList.isEmpty()){
            instanciaSiguienteJList.clear();
        }
    }
    
    /*
    private void mostrarA(){
        System.out.println("---------");
                for(int i = 0; i < instanciaAnteriorJList.size(); i++){
                    System.out.println("A "+instanciaAnteriorJList.get(i).getAccion());
                    System.out.println("A "+instanciaAnteriorJList.get(i).getDireccion());
                    System.out.println("A "+instanciaAnteriorJList.get(i).getIndice());
                    System.out.println("A "+instanciaAnteriorJList.get(i).getElementosAfectados());
                }
    }
    
    private void mostrarB(){
        System.out.println("---------");
                for(int i = 0; i < instanciaSiguienteJList.size(); i++){
                    System.out.println("B "+instanciaSiguienteJList.get(i).getAccion());
                    System.out.println("B "+instanciaSiguienteJList.get(i).getDireccion());
                    System.out.println("B "+instanciaSiguienteJList.get(i).getIndice());
                    System.out.println("B "+instanciaSiguienteJList.get(i).getElementosAfectados());
                }
    }
    */
}
