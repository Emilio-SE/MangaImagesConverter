package acciones;

//Importacion elementos awt y Swing
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
//Otras importaciones
import java.io.File;
import propiedades.Constantes;

public class AccionesExploradorArchivos {
    private Constantes constante;
    public AccionesExploradorArchivos(){
        constante = new Constantes();
    }
    
    public String[] abrirExploradorArchivos(String AbrirEn){
       
        JFileChooser explorador = new JFileChooser();
        
        explorador.setDialogTitle("Seleccione el archivo");
         
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg, jpeg, png, gif Files", constante.EXTENSIONES_PERMITIDAS);
        
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

            return AccionesGenerales.ordenarListado( AccionesConversiones.FileAListString(listadoArchivos) );
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
    
    public String rutaActual(){
        return System.getProperty("user.dir") + "\\";
    }
        
}
