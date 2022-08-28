package eventos;

import acciones.AccionesArchivos;
import acciones.AccionesImagenes;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Mouse implements MouseListener{
    
    private String fuente;
    private DefaultListModel modelo;
    private JList listado;
    private JPanel panelImagen;
    private JLabel lblImagen;
    private AccionesArchivos accionesArchivos;
    private AccionesImagenes accionesImagenes;
    
    public Mouse(DefaultListModel modelo, JList listado, String fuente){
        this.fuente = fuente;
        this.modelo = modelo;
        this.listado = listado;
        accionesArchivos = new AccionesArchivos();
        accionesImagenes = new AccionesImagenes();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getClickCount() == 1 && fuente.equals("ConfirmarOrden")) {
            accionesImagenes.mostrarImagenEnCambioDeFoco(listado, modelo, panelImagen, lblImagen);
        }
        
        if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
            accionesArchivos.abrirRutaEnComputadora(listado, modelo);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    public void setPanelImagen(JPanel panelImagen) {
        this.panelImagen = panelImagen;
    }

    public void setLblImagen(JLabel lblImagen) {
        this.lblImagen = lblImagen;
    }
    
    

}
