package propiedades;

//Importación clases
import acciones.AccionesExploradorArchivos;
import acciones.AccionesGenerales;
import java.util.Arrays;
//Otras importaciones
import java.util.Queue;
import java.util.LinkedList;

public class InformacionGenerales {
    
    //Declaración de Clases
    private AccionesGenerales accionesGenerales;
    private Propiedades propiedades;
    private AccionesExploradorArchivos procesos;
    //Otras Declaraciones
    private Queue <String> direccionesImagenes;
    //Variables Globales
    private String tituloPDF;
    private String autorPDF;
    private String rutaPortada;
    private String rutaGuardarDocumento;
    private String rutaAbrirCarpetaEn;
    private String rutaAbrirArchivoEn;
    private String[] direccionesCarpetas;
    private float[] margenesFloat;
    private String margenesString;
    private int tipoHoja;
    private int unidadMedida;
    
    public InformacionGenerales(){
        instancias();
        propiedades.consultarPropiedades();
        valoresPredeterminados();
    }

    private void instancias(){
        //Instancias de Clases
        procesos = new AccionesExploradorArchivos();
        propiedades = new Propiedades();
        accionesGenerales = new AccionesGenerales();
        //Otras Instancias
        direccionesImagenes = new LinkedList<String>();
    }
    
    private void valoresPredeterminados(){
        
        String auxString;
        
        auxString = propiedades.getTitulo();
        if(!auxString.equals("")){
            this.tituloPDF = auxString;
        }else{
            this.tituloPDF = procesos.nombreAleatorio();
        }
        
        auxString = propiedades.getAutor();
        if(!auxString.equals("")){
            this.autorPDF = auxString;
        }else{
            this.autorPDF = "MIC";
        }
        
        auxString = propiedades.getPortada();
        if(!auxString.equals("")){
            this.rutaPortada = auxString;
        }else{
            this.rutaPortada = "";
        }

        auxString = propiedades.getGuardar_En();
        if(!auxString.equals("")){
            this.rutaGuardarDocumento = auxString;
        }else{
            this.rutaGuardarDocumento = procesos.rutaActual();
        }
        
        this.tipoHoja = propiedades.getFormato_Hoja();
        
        this.unidadMedida = propiedades.getUnidades();
        
        this.margenesFloat = accionesGenerales.margenesTxtAFloat( propiedades.getMargenes() );
        
        this.margenesString = Arrays.toString( this.margenesFloat ).replace("[", "").replace("]","").replace("0.0", "0");
        
        auxString = propiedades.getSeleccionar_Carpeta_En();
        if(!auxString.equals("")){
            this.rutaAbrirCarpetaEn = auxString;
        }else{
            this.rutaAbrirCarpetaEn = ".";
        }
        
        auxString = propiedades.getSeleccionar_Archivo_En();
        if(!auxString.equals("")){
            this.rutaAbrirArchivoEn = auxString;
        }else{
            this.rutaAbrirArchivoEn = ".";
        }
        
    }
    
    public String getTituloPDF() {
        return tituloPDF;
    }
    
    public String getAutorPDF() {
        return autorPDF;
    }

    public String getRutaPortada() {
        return rutaPortada;
    }

    public String getRutaGuardarDocumento() {
        return rutaGuardarDocumento;
    }

    public float[] getMargenesFloat() {
        return margenesFloat;
    }

    public String getMargenesString() {
        return margenesString;
    }
    
    public String[] getDireccionesCarpetas() {
        return direccionesCarpetas;
    }

    public int getTipoHoja() {
        return tipoHoja;
    }

    public int getUnidadMedida() {
        return unidadMedida;
    }

    public Queue<String> getDireccionesImagenes() {
        return direccionesImagenes;
    }

    public String getRutaAbrirCarpetaEn() {
        return rutaAbrirCarpetaEn;
    }

    public String getRutaAbrirArchivoEn() {
        return rutaAbrirArchivoEn;
    }
    
    public void setTituloPDF(String tituloPDF) {
        this.tituloPDF = tituloPDF;
    }

    public void setAutorPDF(String autorPDF) {
        this.autorPDF = autorPDF;
    }

    public void setRutaPortada(String rutaPortada) {
        this.rutaPortada = rutaPortada;
    }

    public void setRutaGuardarDocumento(String rutaGuardarDocumento) {
        this.rutaGuardarDocumento = rutaGuardarDocumento;
    }

    public void setDireccionesCarpetas(String[] direccionesCarpetas) {
        this.direccionesCarpetas = direccionesCarpetas;
    }

    public void setMargenesFloat(float[] margenesFloat) {
        this.margenesFloat = margenesFloat;
    }

    /*public void setMargenesString(String margenesString) {
        this.margenesString = margenesString;
    }*/
    
    public void setTipoHoja(int tipoHoja) {
        this.tipoHoja = tipoHoja;
    }

    public void setUnidadMedida(int unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
    public void setDireccionesImagenes(String direccionImagen) {
        this.direccionesImagenes.add(direccionImagen);
    }
    
}
