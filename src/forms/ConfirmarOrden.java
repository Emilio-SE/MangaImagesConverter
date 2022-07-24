package forms;

import propiedades.InformacionGenerales;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
//Importación Eventos
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//Importación de Clases
import diseno.DisenioComponentes;
import acciones.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//Otras Importaciones
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ConfirmarOrden extends JFrame{
    //Declaración Componentes.
    private JPanel panelBotones;
    private JScrollPane panelCentral;
    private JPanel panelPiePagina;
    private JPanel panelImagen;
    private JLabel lblImagen;
    private JButton btnSubirPosicionImagen;
    private JButton btnBajarPosicionImagen;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnGenerarPDF;
    private JButton btnCancelar;
    private JList lstImagenes;
    //Declaración clases.
    InformacionGenerales informacion;
    private DisenioComponentes disenio;
    private AccionesComponentes accionesComponentes;
    private AccionesExploradorArchivos explorador;
    private AccionesGenerales accionesGenerales;
    //Variables Globales.
    private DefaultListModel modelo;
    private Queue<String> direccionesCarpetas;
    private int actualizarForm = 0;
    
    public ConfirmarOrden(InformacionGenerales informarcion, Queue<String>direccionesCarpetas){
        this.informacion = informarcion;
        instancias();
        accionesGenerales.copiarColas(this.direccionesCarpetas, direccionesCarpetas);
        
        //colocarImagen("");
        valoresPredeterminados();
        disenioForm();
        eventos();
    }
    
    private void instancias(){
        //Instacias de componentes.
        panelBotones = new JPanel();
        panelPiePagina = new JPanel();
        panelImagen = new JPanel();
        btnSubirPosicionImagen = new JButton("Subir ");
        btnBajarPosicionImagen = new JButton("Bajar");
        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnGenerarPDF = new JButton("Generar PDF");
        btnCancelar = new JButton("Cancelar");
        modelo = new DefaultListModel();
        lstImagenes = new JList(modelo);
        lblImagen = new JLabel();
        //Instancias clases
        accionesComponentes = new AccionesComponentes( informacion.getRutaAbrirCarpetaEn(), informacion.getRutaAbrirArchivoEn() );
        explorador = new AccionesExploradorArchivos();
        disenio = new DisenioComponentes();
        accionesGenerales = new AccionesGenerales();
        //Instancias variables globales
        direccionesCarpetas = new LinkedList<String>();
    }
    
    private void valoresPredeterminados(){
        //Valores de Ventana.
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("CONFIRMAR");
        this.setResizable(false);
        //this.setUndecorated(true);
        this.setSize(1000, 400);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        //Otros
        panelCentral = new JScrollPane(lstImagenes);
        accionesComponentes.colocarImagenes(modelo, direccionesCarpetas);
        //Valores contenedores
        getContentPane().add(panelBotones, BorderLayout.NORTH);
        getContentPane().add(panelCentral, BorderLayout.CENTER);
        getContentPane().add(panelPiePagina, BorderLayout.SOUTH);
        getContentPane().add(panelImagen, BorderLayout.EAST);
        //Layouts
        panelBotones.setLayout(new FlowLayout());
        panelCentral.setLayout(new ScrollPaneLayout());
        panelPiePagina.setLayout(new FlowLayout());
        panelImagen.setLayout(new FlowLayout());
        //¡NO QUITAR EL SIGUIENTE AGREGADO DE LABEL!
        panelImagen.add(lblImagen);
        /*
            Se agrega primero el label "lblImagen" al panel que se encuentra en el lateral
            derecho, esto permite que posteriormente se agregue la imagen a dicho label y
            posteriormente se actualice el form, de tal manera que permita visualizar
            correctamente la imagen en el form, de otra manera, si se quita este agregado
            no será posible visualizar la imagen hasta el segundo clic, y si se quita el 
            actualizado del form <<vea línea 177 a fecha de este escrito>> directamente no
            será pósible visualizar la imagen.        
        */
    }
    
    private void disenioForm(){
        // ------------------Diseño Paneles-----------------
        panelBotones.setBackground(new Color(236, 246, 247));
        //panelCentral.setBackground(new Color(236, 246, 247));
        panelImagen.setBackground(new Color(236, 246, 247));
        panelPiePagina.setBackground(new Color(236, 246, 247));
        
        // -----------Diseño Componentes-----------
        disenio.disenioBotones(btnSubirPosicionImagen, "Sube de posición la imagen seleccionada.");
        disenio.disenioBotones(btnBajarPosicionImagen, "Baja de posición la imagen seleccionada.");
        disenio.disenioBotones(btnAgregar, "Agrega una nueva imagen.");
        disenio.disenioBotones(btnEliminar, "Elimina la carpeta seleccionada.");
        disenio.disenioBotones(btnLimpiar, "Eliminar la lista completa.");
        disenio.disenioBotones(btnCancelar, "Cancelar.");
        disenio.disenioBotones(btnGenerarPDF, "¡Genera tu documento PDF!");
        
        // -----------Disposición Componentes-----------
        panelBotones.add(btnSubirPosicionImagen);
        panelBotones.add(btnBajarPosicionImagen);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelPiePagina.add(btnCancelar);
        panelPiePagina.add(btnGenerarPDF);

        this.setVisible(true);
    }

    private void eventos(){
        btnSubirPosicionImagen.addActionListener(new EventosComponentes());
        btnBajarPosicionImagen.addActionListener(new EventosComponentes());
        btnAgregar.addActionListener(new EventosComponentes());
        btnEliminar.addActionListener(new EventosComponentes());
        btnLimpiar.addActionListener(new EventosComponentes());
        btnGenerarPDF.addActionListener(new EventosComponentes());
        btnCancelar.addActionListener(new  EventosComponentes());
        lstImagenes.addMouseListener(new EventosMouse());
        lstImagenes.addKeyListener(new EventosTeclado());
        this.addWindowListener(new EventoCerrarForm());
    }
    
    private void colocarImagen(String rutaImagen){            
        try {
            ImageIcon icon = new ImageIcon(rutaImagen);
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(290, 310, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            lblImagen.setIcon(icon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado " + e.getMessage(), "ERROR", JOptionPane.ERROR);
        }
        
        if(actualizarForm < 1){
            this.invalidate();
            this.validate();
            actualizarForm++;
        }
    }
    
    private void mostrarImagenEnCambioDeFoco(){
        int indice = lstImagenes.getSelectedIndex();

        if (indice != -1) {
            Object rutaImagen = modelo.getElementAt(indice);
            colocarImagen(rutaImagen.toString());
            panelImagen.add(lblImagen);
        }
    }
    
    private void actualizarBufferImagenes(){
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
    
    public class EventosMouse implements MouseListener{  
        @Override
        public void mousePressed(MouseEvent e) {
            JList clic = (JList) e.getSource();
            
            if (e.getClickCount() == 1) {
                mostrarImagenEnCambioDeFoco();
            }
            
            if(e.getClickCount() == 2){
                int indice = clic.locationToIndex(e.getPoint());
                
                if (indice != -1) {
                    Object rutaImagen = clic.getModel().getElementAt(indice);
                    explorador.abrirArchvo(rutaImagen.toString());
                }

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
    }
    
    public class EventosTeclado implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_DELETE){
                accionesComponentes.eliminarElementoList(modelo, lstImagenes);
                mostrarImagenEnCambioDeFoco();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_UP){
                mostrarImagenEnCambioDeFoco(); 
            }
        }

    }
    
    public class EventosComponentes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //BOTÓN SUBIR
            if(e.getSource() == btnSubirPosicionImagen){
                accionesComponentes.subirElementoList(modelo, lstImagenes);
            }
            
            //BOTÓN BAJAR
            if(e.getSource() == btnBajarPosicionImagen){
                accionesComponentes.bajarElementoList(modelo, lstImagenes);
            }
            
            //BOTÓN AGREGAR
            if (e.getSource() == btnAgregar ) {
                accionesComponentes.agregarArchivo(modelo);
            }

            //BOTÓN ELIMINAR
            if(e.getSource() == btnEliminar){
                accionesComponentes.eliminarElementoList(modelo, lstImagenes);
            }
            
            if(e.getSource() == btnLimpiar){
                modelo.clear();
            }
            
            //BOTÓN CANCELAR
            if(e.getSource() == btnCancelar){
                int respuesta = JOptionPane.showConfirmDialog(null, "<html>Al salir perderá todo cambio realizado en esta ventana. <br><br>¿Desea continuar?</html>", "¿Desea salir?", JOptionPane.YES_NO_OPTION);
                
                if(respuesta == 0){
                    dispose();
                }
            }
            
            //BOTÓN GENERAR
            if(e.getSource() == btnGenerarPDF){
                Queue <String> direccionesImagenes = new LinkedList<String>();
                actualizarBufferImagenes();
                
                accionesGenerales.copiarColas(direccionesImagenes, informacion.getDireccionesImagenes());
                GenerarPDF pdf = new GenerarPDF(informacion, direccionesImagenes);

                try {                    
                    if(explorador.existeArchivo(informacion.getRutaGuardarDocumento() + informacion.getTituloPDF() + ".pdf")){
                        int respuesta = JOptionPane.showConfirmDialog(null, "El archivo \"" + informacion.getTituloPDF() + "\" Ya existe en la ruta especificada\n" + "¿Desea sobreescribirlo?", "Archivo Existente", JOptionPane.YES_NO_OPTION);
                        
                        if(respuesta == 0){
                            int cantidadConvertida = pdf.crearPDF();
                            JOptionPane.showMessageDialog(null, cantidadConvertida + " imagenes se han convertido al archivo PDF :D" + "\nEl documento ha sido generado en: " + informacion.getRutaGuardarDocumento());
                        }                        
                    }else{
                        int cantidadConvertida = pdf.crearPDF();
                        JOptionPane.showMessageDialog(null, cantidadConvertida + " imagenes se han convertido al archivo PDF :D" + "\nEl documento ha sido generado en: " + informacion.getRutaGuardarDocumento());
                    }
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR INESPERADO", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }
     
    }
    
    public class EventoCerrarForm implements WindowListener{

        @Override
        public void windowClosing(WindowEvent e) {
        
            int respuesta = JOptionPane.showConfirmDialog(null, "Al salir perderá todo cambio realizado en esta ventana. \n\n¿Desea salir? ", "Salir", JOptionPane.YES_NO_OPTION);
                
            if(respuesta == JOptionPane.YES_OPTION){
                dispose();
            }
            
        }
        
        @Override
        public void windowOpened(WindowEvent e) {}
        @Override
        public void windowClosed(WindowEvent e) {}
        @Override
        public void windowIconified(WindowEvent e) {}
        @Override
        public void windowDeiconified(WindowEvent e) {}
        @Override
        public void windowActivated(WindowEvent e) {}
        @Override
        public void windowDeactivated(WindowEvent e) {}
        
    }
    
}