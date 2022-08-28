package eventos;

import diseno.DisenioComponentes;
import forms.Ajustes;
import forms.ConfirmarOrden;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import propiedades.Constantes;
import propiedades.Metadatos;
import acciones.AccionesTextFields;
import acciones.AccionesJList;
import acciones.AccionesMetadatos;
import java.util.Queue;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;

public class ComponentesFormPrincipal implements ActionListener{
    private JButton btnCargarCarpetas;//
    private JButton btnCargarImagenes;//
    private JButton btnActualizar;//
    private JRadioButton rbtnCm;//
    private JRadioButton rbtnPts;//
    private JRadioButton rbtnPul;//
    private JTextField tfTitulo;//
    private JTextField tfAutor;//
    private JTextField tfMargenes;//
    private JTextField tfPortada;//
    private JTextField tfRutaGuardarDoc;//
    private Queue<String> direccionesCarpetas;
    private DefaultListModel modelo;
    private JList lstCarpetas;
    private Metadatos informacion;
    private Constantes constantes;
    private DisenioComponentes disenio;
    private AccionesTextFields accionesTxtFields;
    private AccionesJList accionesJList;
    private AccionesMetadatos metadatos;
    
    public ComponentesFormPrincipal(DefaultListModel modelo, JList lstCarpetas, AccionesJList accionesJList){
        informacion = Metadatos.getInstancia();
        constantes = new Constantes();
        disenio = new DisenioComponentes();
        metadatos = new AccionesMetadatos();
        accionesTxtFields = new AccionesTextFields();
        this.accionesJList = accionesJList;
        this.modelo = modelo;
        this.lstCarpetas = lstCarpetas;
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("A4")){
            informacion.setTipoHoja(constantes.TAMANIO_A4);
        }

        if(e.getActionCommand().equals("Carta")){
            informacion.setTipoHoja(constantes.TAMANIO_CARTA);
        }

        if(e.getSource() == rbtnCm || e.getSource() == rbtnPul || e.getSource() == rbtnPts){
            disenio.activarBtnActualizarMeta(btnActualizar);
        }

        if (e.getActionCommand().equals("Seleccionar") ) {
            accionesTxtFields.colocarRutaArchivo(tfPortada);
        }

        if(e.getActionCommand().equals("Cambiar Ajustes Predeterminados")){
            metadatos.actualizarMetadatos(tfTitulo, tfAutor, tfMargenes, tfPortada, tfRutaGuardarDoc, btnActualizar, rbtnCm, rbtnPul);
            Ajustes ajustes = new Ajustes();
        }

        if(e.getActionCommand().equals("Cambiar Ruta")){
            accionesTxtFields.colocarRutaCarpeta(tfRutaGuardarDoc);
        }

        if(e.getActionCommand().equals("Subir")){
            accionesJList.subirElementoEnJList(modelo, lstCarpetas);
        }

        if(e.getActionCommand().equals("Bajar")){
            accionesJList.bajarElementoEnJList(modelo, lstCarpetas);
        }

        if(e.getActionCommand().equals("Eliminar")){
            accionesJList.eliminarElementoDeJList(modelo, lstCarpetas);
        }

        if(e.getActionCommand().equals("Limpiar")){
            accionesJList.limpiarElementosDeJList(modelo, lstCarpetas);
        }

        if (e.getActionCommand().equals("Agregar")) {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar subcarpetas?", "Subcarpetas", JOptionPane.YES_NO_OPTION);

            if(respuesta == JOptionPane.YES_OPTION){
                accionesJList.agregarCarpetasRecursivamenteAJList(modelo);
            }else if(respuesta == 1){
                accionesJList.agregarCarpetasAJList(modelo);
            }

       }

        if(e.getSource() == btnCargarCarpetas){

            if(!modelo.isEmpty()){

                metadatos.actualizarMetadatos(tfTitulo, tfAutor, tfMargenes, tfPortada, tfRutaGuardarDoc, btnActualizar, rbtnCm, rbtnPul);

                //Comparten misma dirección en memoria.
                direccionesCarpetas = accionesJList.obtenerRutasDeJLista(modelo);

                if(!direccionesCarpetas.isEmpty()){
                    ConfirmarOrden confirmarOrden = new ConfirmarOrden(direccionesCarpetas, btnCargarCarpetas, btnCargarImagenes);
                }else{
                    JOptionPane.showMessageDialog(null, "La o las carpetas no se han podido cargar, asegurese que todos los directorios sean existentes.", "Error al cargar carpetas", JOptionPane.ERROR_MESSAGE);
                }

            }else{
                JOptionPane.showMessageDialog(null, "¡Ánimate! Agregra una carpeta :D", ":3", JOptionPane.INFORMATION_MESSAGE);
            }

        }

        if(e.getSource() == btnCargarImagenes){                
            metadatos.actualizarMetadatos(tfTitulo, tfAutor, tfMargenes, tfPortada, tfRutaGuardarDoc, btnActualizar, rbtnCm, rbtnPul);
            direccionesCarpetas = new LinkedList<String>();
            ConfirmarOrden confirmarOrden = new ConfirmarOrden(direccionesCarpetas, btnCargarCarpetas, btnCargarImagenes);

        }

        if(e.getSource() == btnActualizar){
            metadatos.actualizarMetadatos(tfTitulo, tfAutor, tfMargenes, tfPortada, tfRutaGuardarDoc, btnActualizar, rbtnCm, rbtnPul);
            disenio.desactivarBtnActualizarMeta(btnActualizar);
        }

    }
    
    //-------------- SETTERS -------------- 

    public void setBtnCargarCarpetas(JButton btnCargarCarpetas) {
        this.btnCargarCarpetas = btnCargarCarpetas;
    }

    public void setBtnCargarImagenes(JButton btnCargarImagenes) {
        this.btnCargarImagenes = btnCargarImagenes;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public void setRbtnCm(JRadioButton rbtnCm) {
        this.rbtnCm = rbtnCm;
    }

    public void setRbtnPts(JRadioButton rbtnPts) {
        this.rbtnPts = rbtnPts;
    }

    public void setRbtnPul(JRadioButton rbtnPul) {
        this.rbtnPul = rbtnPul;
    }

    public void setTfPortada(JTextField tfPortada) {
        this.tfPortada = tfPortada;
    }

    public void setTfRutaGuardarDoc(JTextField tfRutaGuardarDoc) {
        this.tfRutaGuardarDoc = tfRutaGuardarDoc;
    }

    public void setTfTitulo(JTextField tfTitulo) {
        this.tfTitulo = tfTitulo;
    }

    public void setTfAutor(JTextField tfAutor) {
        this.tfAutor = tfAutor;
    }

    public void setTfMargenes(JTextField tfMargenes) {
        this.tfMargenes = tfMargenes;
    }
    
    
    
}
