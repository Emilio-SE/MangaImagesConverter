package acciones;

import java.awt.Image;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import propiedades.InformacionGenerales;

public class AccionesImagenes {
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
    
    public void actualizarBufferImagenes(DefaultListModel modelo, InformacionGenerales informacion){
        int cantDireccionesTemporales = informacion.getDireccionesImagenes().size();
        
        if(cantDireccionesTemporales > 0){
            for (int i = 0; i < cantDireccionesTemporales; i++) {
                informacion.getDireccionesImagenes().remove();
            }
        }
        
        if(!informacion.getRutaPortada().equals("")){
            informacion.setDireccionesImagenes(informacion.getRutaPortada());
        }

        for (int i = 0; i < modelo.getSize(); i++) {    
            informacion.setDireccionesImagenes( modelo.getElementAt(i).toString() );
        }
    }
}
