package eventos;

import acciones.AccionesArchivos;
import acciones.AccionesConversiones;
import acciones.AccionesImagenes;
import acciones.AccionesJList;
import acciones.GenerarPDF;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import observadores.ObservadorEstadoGenerarPDF;
import propiedades.Metadatos;

public class ComponentesConfirmarOrden implements ActionListener{

    private JButton btnGenerarPDF;
    private JButton btnCancelar;
    private JPanel panelImagen;
    private JLabel lblImagen;
    private JList lstImagenes;
    private DefaultListModel modelo;
    private Thread hiloGenerarPDF;
    private GenerarPDF generarPDF;
    private AccionesJList accionesJList;
    private AccionesImagenes accionesImagenes;
    private AccionesConversiones accionesConversiones;
    private AccionesArchivos accionesArchivos;
    private Metadatos informacion;

    public ComponentesConfirmarOrden(DefaultListModel modelo, JList lstImagenes, AccionesJList accionesJList, GenerarPDF generarPDF) {
        accionesImagenes = new AccionesImagenes();
        accionesConversiones = new AccionesConversiones();
        accionesArchivos = new AccionesArchivos();
        informacion = Metadatos.getInstancia();
        this.modelo = modelo;
        this.lstImagenes = lstImagenes;
        this.accionesJList = accionesJList;        
        this.generarPDF = generarPDF;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("Subir")){
            accionesJList.subirElementoEnJList(modelo, lstImagenes);
        }

        if(e.getActionCommand().equals("Bajar")){
            accionesJList.bajarElementoEnJList(modelo, lstImagenes);
        }

        if (e.getActionCommand().equals("Agregar") ) {
            accionesJList.agregarArchivosAJList(modelo);
        }

        if(e.getActionCommand().equals("Eliminar")){
            accionesJList.eliminarElementoDeJList(modelo, lstImagenes);
            accionesImagenes.mostrarImagenEnCambioDeFoco(lstImagenes, modelo, panelImagen, lblImagen);
        }

        if(e.getActionCommand().equals("Limpiar")){
            accionesJList.limpiarElementosDeJList(modelo, lstImagenes);
        }

        if(e.getSource() == btnCancelar){
            generarPDF.cancelarEjecucion();
        }

        if(e.getSource() == btnGenerarPDF){
            Queue <String> direccionesImagenes = new LinkedList<String>();
            accionesImagenes.actualizarBufferImagenes(modelo);
            accionesConversiones.copiarColas(direccionesImagenes, accionesImagenes.getDireccionesImagenes());

            generarPDF.setDireccionesImagenes(direccionesImagenes);
            ObservadorEstadoGenerarPDF observadorGenerarPDF = new ObservadorEstadoGenerarPDF(btnGenerarPDF, btnCancelar);
            generarPDF.addObserver(observadorGenerarPDF);

            hiloGenerarPDF = new Thread(generarPDF);

            try {                    
                if(accionesArchivos.existeArchivo(informacion.getRutaGuardarDocumento() + informacion.getTituloPDF() + ".pdf")){
                    int respuesta = JOptionPane.showConfirmDialog(null, "El archivo \"" + informacion.getTituloPDF() + "\" Ya existe en la ruta especificada\n" + "¿Desea sobreescribirlo?", "Archivo Existente", JOptionPane.YES_NO_OPTION);

                    if(respuesta == 0){
                        hiloGenerarPDF.start();
                    }                        
                }else{
                    hiloGenerarPDF.start(); 
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR INESPERADO", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public void setBtnGenerarPDF(JButton btnGenerarPDF) {
        this.btnGenerarPDF = btnGenerarPDF;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public void setPanelImagen(JPanel panelImagen) {
        this.panelImagen = panelImagen;
    }

    public void setLblImagen(JLabel lblImagen) {
        this.lblImagen = lblImagen;
    }
    
    
    
}
