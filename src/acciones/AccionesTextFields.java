package acciones;

//Importación componentes awt y Swing
import java.awt.Toolkit;
import javax.swing.JTextField;
//Importación de eventos
import java.awt.event.KeyEvent;

public class AccionesTextFields{
    
    private String abrirCarpetasEn;
    private String abrirArchivosEn;
    private AccionesExploradorArchivos explorador;
   
    public AccionesTextFields(String abrirCarpetasEn, String abrirArchivosEn){
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
    
    public void verificarTeclaIngresada(KeyEvent evt, int teclaPresionada){        
        if ( (teclaPresionada < KeyEvent.VK_0 || teclaPresionada > KeyEvent.VK_9) && ( teclaPresionada != KeyEvent.VK_COMMA ) && ( teclaPresionada != KeyEvent.VK_BACK_SPACE ) && ( teclaPresionada != KeyEvent.VK_SPACE )) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
    }
 
}
