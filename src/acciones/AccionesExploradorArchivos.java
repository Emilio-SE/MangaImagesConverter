package acciones;

//Importacion elementos awt y Swing
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
//Otras importaciones
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccionesExploradorArchivos {
    private String[] extensionesPermitidas;
    
    public AccionesExploradorArchivos(){
        extensionesPermitidas = new String[] {"jpg", "jpeg", "png", "gif"};
    }
    
    public String[] abrirExploradorArchivos(String AbrirEn){
        
        String[] ruta;
        
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
            ruta = new String[listadoArchivos.length];
            
            for (int indice = 0; indice < listadoArchivos.length; indice++) {
                ruta[indice] = listadoArchivos[indice].getAbsolutePath();
            }
            
            return ruta;
        }else{
            return ruta = new String[]{""};
        }
         
    }
    
    public String[] abrirExploradorCarpetas(String AbrirEn){
        
        String[] ruta;
        
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
            ruta = new String[listadoCarpetas.length];
            
            for (int indice = 0; indice < listadoCarpetas.length; indice++) {
                ruta[indice] = listadoCarpetas[indice].getAbsolutePath() + "\\";
            }
            
            return ruta;

//ruta = fichero.getAbsolutePath() + "\\";
        }else{
            return ruta = new String[]{""};
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
    
}
