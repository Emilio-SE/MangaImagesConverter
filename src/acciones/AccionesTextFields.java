package acciones;

//Importación componentes awt y Swing
import java.awt.Toolkit;
import javax.swing.JTextField;
//Importación de eventos
import java.awt.event.KeyEvent;
import propiedades.Metadatos;

public class AccionesTextFields{
    
    private AccionesExploradorArchivos explorador;
    private Metadatos informacion;
   
    public AccionesTextFields(){
        explorador = new AccionesExploradorArchivos();
        informacion = Metadatos.getInstancia();

        
    }
    
    public void colocarRutaCarpeta(JTextField campo){
        String[] rutas = explorador.abrirExploradorCarpetas(informacion.getRutaAbrirCarpetaEn());
        
        if(!rutas[0].equals("")){
            campo.setText(rutas[0]);
        }
    }
    
    public void colocarRutaArchivo(JTextField campo){
        String ruta[] = explorador.abrirExploradorArchivos(informacion.getRutaAbrirArchivoEn());
        
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
