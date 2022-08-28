package acciones;

import java.awt.Desktop;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class AccionesArchivos {
    
    public boolean existeArchivo(String ruta){
        File archivo = new File(ruta);
        
        return archivo.exists();
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
                abrirArchivo(rutaImagen.toString());
            }
        }
    }
    
    public void abrirArchivo(String ruta){
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
        
}
