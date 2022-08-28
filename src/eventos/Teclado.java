
package eventos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import acciones.AccionesTextFields;
import acciones.AccionesJList;
import acciones.AccionesImagenes;
import acciones.AccionesArchivos;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Teclado implements KeyListener{
    
    private String fuente;
    private DefaultListModel modelo;
    private JList listado;
    private JTextField tfMargenes;
    private JPanel panelImagen;
    private JLabel lblImagen;
    private AccionesTextFields accionesTxtFields;
    private AccionesJList accionesJList;
    private AccionesArchivos accionesArchivos;
    private AccionesImagenes accionesImagenes;
    
    public Teclado(DefaultListModel modelo, JList listado, AccionesJList accionesJList, JTextField tfMargenes, String fuente){
        instancias(modelo, listado, accionesJList, fuente);
        this.tfMargenes = tfMargenes;
        this.accionesTxtFields = new AccionesTextFields();
    }
    
    public Teclado(DefaultListModel modelo, JList listado, AccionesJList accionesJList, JPanel panelImagen, JLabel lblImagen, String fuente){
        instancias(modelo, listado, accionesJList, fuente);
        accionesImagenes = new AccionesImagenes();
        this.panelImagen = panelImagen;
        this.lblImagen = lblImagen;

    }
    
    private void instancias(DefaultListModel modelo, JList listado, AccionesJList accionesJList, String fuente){
        this.fuente = fuente;
        this.modelo = modelo;
        this.listado = listado;
        this.accionesJList = accionesJList;
        accionesArchivos = new AccionesArchivos();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == tfMargenes){
            int teclaPresionada = (int) e.getKeyChar();
            accionesTxtFields.verificarTeclaIngresada(e, teclaPresionada);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getSource() != tfMargenes){
            if (e.getKeyCode()==KeyEvent.VK_DELETE){
                accionesJList.eliminarElementoDeJList(modelo, listado);
            
                if(fuente.equals("ConfirmarOrden")){
                    accionesImagenes.mostrarImagenEnCambioDeFoco(listado, modelo, panelImagen, lblImagen);
                }
            
            }

            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                accionesArchivos.abrirRutaEnComputadora(listado, modelo);
            }

            if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
                accionesJList.deshacerJList(modelo);
            }

            if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
                accionesJList.rehacerJList(modelo);
            }
        }
        
        

    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
