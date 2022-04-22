package propiedades;

import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Propiedades {
    
    private final String PROPIEDADES;
    private String  Titulo;
    private String  Autor;
    private String  Portada;
    private String  Guardar_En;
    private String Margenes;
    private int Formato_Hoja;
    private int Unidades;
    private String  Seleccionar_Archivo_En;
    private String  Seleccionar_Carpeta_En;
    private Constantes constantes;
    
    public Propiedades() {
        this.PROPIEDADES = System.getProperty("user.dir") + "\\src\\config\\Configuracion.properties";
        constantes = new Constantes();
    }
    
    public void sobreescribirPropiedades(String titulo, String autor, String portada, String guardarEn, String formatoHoja, String margenes, String unidades, String selectArchivo, String selectCarpeta){
        
        try {
            OutputStream salida = new FileOutputStream(PROPIEDADES);
            
            Properties propiedades = new Properties();
            
            propiedades.setProperty("Titulo", titulo);
            propiedades.setProperty("Autor", autor);
            propiedades.setProperty("Portada", portada);
            propiedades.setProperty("GuardarEn", guardarEn);
            propiedades.setProperty("FormatoHoja", formatoHoja);
            propiedades.setProperty("Margenes", margenes);
            propiedades.setProperty("Unidades", unidades);
            propiedades.setProperty("SeleccionarArchivoEn", selectArchivo);
            propiedades.setProperty("SeleccionarCarpetaEn", selectCarpeta);
            
            propiedades.store(salida, null);
            
            JOptionPane.showMessageDialog(null, "Datos Actualizados.\n\nReinicie el programa para aplicar los cambios.", "", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (FileNotFoundException ex) {
            propiedadesEnError();
            JOptionPane.showMessageDialog(null, "<html>No se ha encontrado el archivo \"Configuracion.properties\" en la ruta especificada.<br><br><html>" + ex.getMessage(), "Archivo de configuración no encontrado", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            propiedadesEnError();
            JOptionPane.showMessageDialog(null, "<html>Ha ocurrido un error de E/S<br><br></html>" + ex.getMessage(), "Error de E/S", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void consultarPropiedades(){
        try {
            InputStream archivo = new FileInputStream(PROPIEDADES);
            Properties propiedades = new Properties();
            
            propiedades.load(archivo);
            
            this.Titulo = propiedades.getProperty("Titulo");
            this.Autor = propiedades.getProperty("Autor");
            this.Portada = propiedades.getProperty("Portada");
            this.Guardar_En = propiedades.getProperty("GuardarEn");
            this.Formato_Hoja = Integer.parseInt( propiedades.getProperty("FormatoHoja") );
            this.Margenes = propiedades.getProperty("Margenes");
            this.Unidades = Integer.parseInt( propiedades.getProperty("Unidades") );
            this.Seleccionar_Archivo_En = propiedades.getProperty("SeleccionarArchivoEn");
            this.Seleccionar_Carpeta_En = propiedades.getProperty("SeleccionarCarpetaEn");
            
        } catch (FileNotFoundException ex) {
            propiedadesEnError();
            JOptionPane.showMessageDialog(null, "<html>No se ha encontrado el archivo \"Configuracion.properties\" en la ruta especificada.<br><br><html>" + ex.getMessage(), "Archivo de configuración no encontrado", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            propiedadesEnError();
            JOptionPane.showMessageDialog(null, "<html>Ha ocurrido un error de E/S<br><br></html>" + ex.getMessage(), "Error de E/S", JOptionPane.ERROR_MESSAGE);
        }
    }   

    private void propiedadesEnError(){
        this.Titulo = "";
        this.Autor = "MIC";
        this.Portada = "";
        this.Guardar_En = "";
        this.Formato_Hoja = constantes.TAMANIO_A4;
        this.Margenes = "36, 36, 36, 36";
        this.Unidades = constantes.UNIDAD_PTS;
        this.Seleccionar_Archivo_En = ".";
        this.Seleccionar_Carpeta_En = ".";
        this.Formato_Hoja = 1;
        this.Unidades = 3;
    }
    
    public String getTitulo() {
        return Titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public String getPortada() {
        return Portada;
    }

    public String getGuardar_En() {
        return Guardar_En;
    }

    public int getUnidades() {
        
        if(Unidades == constantes.UNIDAD_CM){
            return constantes.UNIDAD_CM;
        }else if(Unidades == constantes.UNIDAD_PUL){
            return constantes.UNIDAD_PUL;
        }else{
            return constantes.UNIDAD_PTS;
        }

    }
    
    public int getFormato_Hoja() {
        if(Formato_Hoja == constantes.TAMANIO_CARTA){
            return constantes.TAMANIO_CARTA;
        }else{
            return constantes.TAMANIO_A4;
        }
    }

    public String getMargenes() {
        return Margenes;
    }

    public String getSeleccionar_Archivo_En() {
        return Seleccionar_Archivo_En;
    }

    public String getSeleccionar_Carpeta_En() {
        return Seleccionar_Carpeta_En;
    }
    
}
