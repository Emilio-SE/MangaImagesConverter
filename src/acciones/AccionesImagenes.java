package acciones;

import java.awt.Image;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import propiedades.Constantes;
import propiedades.Metadatos;

public class AccionesImagenes {
    
    private Metadatos informacion;
    private Constantes constantes;
    private static Queue <String> direccionesImagenes;
    
    public AccionesImagenes(){
        this.informacion = Metadatos.getInstancia();
        constantes = new Constantes();
        direccionesImagenes = new LinkedList<String>();
    }
    
    public void colocarImagen(JLabel lblImagen, String rutaImagen){            
        try {
            ImageIcon icon = new ImageIcon(rutaImagen);
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(290, 310, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            lblImagen.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado " + e.getMessage(), "ERROR", JOptionPane.ERROR);
        }
    }
    
    public void mostrarImagenEnCambioDeFoco(JList lstImagenes, DefaultListModel modelo, JPanel panelImagen, JLabel lblImagen) {
        int indice = lstImagenes.getSelectedIndex();

        if (indice != -1) {
            Object rutaImagen = modelo.getElementAt(indice);
            colocarImagen(lblImagen, rutaImagen.toString());
            panelImagen.add(lblImagen);
        }
    }
    
    public void actualizarBufferImagenes(DefaultListModel modelo){
        int cantDireccionesTemporales = getDireccionesImagenes().size();
        
        if(cantDireccionesTemporales > 0){
            for (int i = 0; i < cantDireccionesTemporales; i++) {
                getDireccionesImagenes().remove();
            }
        }
        
        if(!informacion.getRutaPortada().equals("")){
            setDireccionesImagenes(informacion.getRutaPortada());
        }

        for (int i = 0; i < modelo.getSize(); i++) {    
            setDireccionesImagenes( modelo.getElementAt(i).toString() );
        }
    }
    
    public boolean esImagen(String nombreImagen){
        String[] extensionesPermitidas = constantes.EXTENSIONES_PERMITIDAS;
        int i = 0;
        boolean confirmacion = false;
        
        while(i < extensionesPermitidas.length){
            if(nombreImagen.toLowerCase().endsWith( "." + extensionesPermitidas[i] )){
                confirmacion = true;
                break;
            }
            i++;
        }
        
        return confirmacion;
    }
    
    public Queue<String> getDireccionesImagenes() {
        return direccionesImagenes;
    }
    
    public void setDireccionesImagenes(String direccionImagen) {
        direccionesImagenes.add(direccionImagen);
    }
}
