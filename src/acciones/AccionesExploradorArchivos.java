package acciones;

//Importacion elementos awt y Swing
import java.awt.Desktop;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
//Otras importaciones
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class AccionesExploradorArchivos {
    private String[] extensionesPermitidas;
    
    public AccionesExploradorArchivos(){
        extensionesPermitidas = new String[] {"jpg", "jpeg", "png", "gif"};
    }
    
    public String[] abrirExploradorArchivos(String AbrirEn){
        
        //String[] rutas;
        
        JFileChooser explorador = new JFileChooser();
        
        explorador.setDialogTitle("Seleccione el archivo");
         
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg, jpeg, png, gif Files", this.extensionesPermitidas);
        
        explorador.addChoosableFileFilter(filtro);
        explorador.setFileSelectionMode(JFileChooser.FILES_ONLY);
        explorador.setAcceptAllFileFilterUsed(false);
        explorador.setMultiSelectionEnabled(true);
        
        if(!AbrirEn.equals("")){
            explorador.setCurrentDirectory(new File( AbrirEn ));
        }else{
            explorador.setCurrentDirectory(new File( rutaActual() ));
        }
        
        int ejecucion = explorador.showOpenDialog(new JFrame());
        
        if (ejecucion == JFileChooser.APPROVE_OPTION){
            File[] listadoArchivos = explorador.getSelectedFiles();

            return AccionesGenerales.ordenarListado( AccionesGenerales.FileAListString(listadoArchivos) );
        }else{
            return new String[]{""};
        }
         
    }
    
    public String[] abrirExploradorCarpetas(String AbrirEn){
        
        String[] rutas;
        
        JFileChooser explorador = new JFileChooser();
        
        explorador.setDialogTitle("Seleccione la carpeta.");
        
        explorador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        explorador.setAcceptAllFileFilterUsed(false);
        explorador.setMultiSelectionEnabled(true);
        
        if(!AbrirEn.equals("")){
            explorador.setCurrentDirectory(new File( AbrirEn ));
        }else{
            explorador.setCurrentDirectory(new File( rutaActual() ));
        }
        
        int ejecucion = explorador.showOpenDialog(new JFrame());
        
        if (ejecucion == JFileChooser.APPROVE_OPTION){
            
            File[] listadoCarpetas = explorador.getSelectedFiles();
            rutas = new String[listadoCarpetas.length];
            
            for (int indice = 0; indice < listadoCarpetas.length; indice++) {
                rutas[indice] = listadoCarpetas[indice].getAbsolutePath() + "\\";
            }
            
            return rutas;

        }else{
            return new String[]{""};
        }
         
        
    }
    
    public String nombreAleatorio(){
        String nombreDocumento = "MIC ";
        int numeroAleatorioDistintivo1 = (int)( Math.random() * 100000000 + 1 );
        
        DateTimeFormatter fechaActual = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        
        nombreDocumento += fechaActual.format(LocalDateTime.now()) + " " + numeroAleatorioDistintivo1;
        nombreDocumento = nombreDocumento.replace("/", "-").replace(":", "-").replace(" ", "_");
        
        return nombreDocumento;
    }
    
    public String rutaActual(){
        
        return System.getProperty("user.dir") + "\\";
    }
    
    public String[] getExtensionesPermitidas() {
        return extensionesPermitidas;
    }
    
    public boolean existeArchivo(String ruta){
        File archivo = new File(ruta);
        
        return archivo.exists();
    }
    
    public boolean esArchivoValido(String archivo){
        boolean esValido = false;
        
        for(int indice = 0; indice < extensionesPermitidas.length; indice++){
            if( archivo.toLowerCase().endsWith( "." + extensionesPermitidas[indice] ) ){
                esValido = true;
                break;
            }
        }
        
        return esValido;
    }
    
    public void abrirArchvo(String ruta){
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop escritorio = Desktop.getDesktop();
                File documento = new File(ruta);
                escritorio.open(documento);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar abrir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void abrirRutaEnComputadora(JList lstDirecciones, DefaultListModel modelo){
        int[] indices = lstDirecciones.getSelectedIndices();

        if(indices.length > 5){
            int respuesta = JOptionPane.showConfirmDialog(null,"Estas a punto de abrir más de 5 direcciones. \n\n¿Estás seguro de continuar?", "Advertencia",JOptionPane.YES_NO_OPTION);
            
            if(respuesta == JOptionPane.YES_OPTION){
                abrirRutas(indices, modelo);
            }
            
        }else{
            abrirRutas(indices, modelo);
        }

    }
    
    private void abrirRutas(int[] indices, DefaultListModel modelo){
        for(int indice : indices){
            if (indice != -1) {
                Object rutaImagen = modelo.getElementAt(indice);
                abrirArchvo(rutaImagen.toString());
            }
        }
    }
    
}
