package propiedades;

//Importación clases
import acciones.AccionesExploradorArchivos;
import acciones.AccionesConversiones;
import acciones.AccionesGenerales;
import java.util.Arrays;

public class Metadatos {
    
    //Declaración de Clases
    private AccionesGenerales accionesGenerales;
    private ConsultarMetadatos propiedades;
    private AccionesExploradorArchivos procesos;
    private AccionesConversiones conversiones;
    //Variables Globales
    private static Metadatos informacionGeneral;
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
    
    private Metadatos(){

        instancias();
        propiedades.consultarPropiedades();
        valoresPredeterminados();
    }
    
    public static Metadatos getInstancia(){
        
        if(informacionGeneral == null){
            informacionGeneral = new Metadatos();
        }
        
        return informacionGeneral;
    }

    private void instancias(){
        //Instancias de Clases
        procesos = new AccionesExploradorArchivos();
        propiedades = new ConsultarMetadatos();
        accionesGenerales = new AccionesGenerales();
        conversiones = new AccionesConversiones();
    }
    
    private void valoresPredeterminados(){
        
        String auxString;
        
        auxString = propiedades.getTitulo();
        if(!auxString.equals("")){
            this.tituloPDF = auxString;
        }else{
            this.tituloPDF = accionesGenerales.nombreAleatorio();
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
        
        this.margenesFloat = conversiones.margenesTxtAFloat( propiedades.getMargenes() );
        
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

    public void setTipoHoja(int tipoHoja) {
        this.tipoHoja = tipoHoja;
    }

    public void setUnidadMedida(int unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
}
