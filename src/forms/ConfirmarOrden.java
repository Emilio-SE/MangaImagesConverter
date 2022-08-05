package forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.JOptionPane;
//Importación Eventos
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//Importación de Clases
import propiedades.InformacionGenerales;
import diseno.DisenioComponentes;
import acciones.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import observadores.ObservadorEstadoGenerarPDF;
//Otras Importaciones
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.SwingUtilities;

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
    private JButton btnCargarCarpetas;
    private JButton btnCargarImagenes;
    private JList lstImagenes;
    //Declaración clases.
    private InformacionGenerales informacion;
    private DisenioComponentes disenio;
    private AccionesExploradorArchivos explorador;
    private AccionesGenerales accionesGenerales;
    private AccionesJList accionesJList;
    private AccionesImagenes accionesImagenes;
    private GenerarPDF generarPDF;
    //Declaración Hilos
    private Thread hiloGenerarPDF;
    //Variables Globales.
    private DefaultListModel modelo;
    private Queue<String> direccionesCarpetas;
    
    public ConfirmarOrden(InformacionGenerales informarcion, Queue<String>direccionesCarpetas, JButton btnCargarCarpetas, JButton btnCargarImagenes){
        this.informacion = informarcion;
        this.btnCargarCarpetas = btnCargarCarpetas;
        this.btnCargarImagenes = btnCargarImagenes;
        instancias();
        accionesGenerales.copiarColas(this.direccionesCarpetas, direccionesCarpetas);
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
        accionesJList = new AccionesJList( informacion.getRutaAbrirCarpetaEn(), informacion.getRutaAbrirArchivoEn() );
        generarPDF = new GenerarPDF(informacion);
        explorador = new AccionesExploradorArchivos();
        disenio = new DisenioComponentes();
        accionesGenerales = new AccionesGenerales();
        accionesImagenes = new AccionesImagenes();
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
        accionesJList.colocarImagenes(modelo, direccionesCarpetas);
        btnCancelar.setEnabled(false);
        btnCargarCarpetas.setEnabled(false);
        btnCargarImagenes.setEnabled(false);
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
        panelImagen.add(lblImagen);
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
        btnAgregar.addKeyListener(new EventosTeclado());
        btnSubirPosicionImagen.addKeyListener(new EventosTeclado());
        btnBajarPosicionImagen.addKeyListener(new EventosTeclado());
        btnEliminar.addKeyListener(new EventosTeclado());
        btnLimpiar.addKeyListener(new EventosTeclado());
        this.addWindowListener(new EventoCerrarForm());
        
        lstImagenes.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> rutas = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    accionesJList.agregarArchivo(modelo, rutas);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado "  + ex.getMessage(), "Error inesperado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }
    
    public class EventosMouse implements MouseListener{  
        @Override
        public void mousePressed(MouseEvent e) {
            
            if (e.getClickCount() == 1) {
                accionesImagenes.mostrarImagenEnCambioDeFoco(lstImagenes, modelo, panelImagen, lblImagen);
            }
            
            if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)){
                explorador.abrirRutaEnComputadora(lstImagenes, modelo);
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
                accionesJList.eliminarElementoJList(modelo, lstImagenes);
                accionesImagenes.mostrarImagenEnCambioDeFoco(lstImagenes, modelo, panelImagen, lblImagen);
            }
            
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                explorador.abrirRutaEnComputadora(lstImagenes, modelo);
            }
            
            if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z){
                accionesJList.deshacerJList(modelo);
            }
            
            if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
                accionesJList.rehacerJList(modelo);
            }
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_UP){
                accionesImagenes.mostrarImagenEnCambioDeFoco(lstImagenes, modelo, panelImagen, lblImagen);; 
            }
        }

    }
    
    public class EventosComponentes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //BOTÓN SUBIR
            if(e.getSource() == btnSubirPosicionImagen){
                accionesJList.subirElementoJList(modelo, lstImagenes);
            }
            
            //BOTÓN BAJAR
            if(e.getSource() == btnBajarPosicionImagen){
                accionesJList.bajarElementoJList(modelo, lstImagenes);
            }
            
            //BOTÓN AGREGAR
            if (e.getSource() == btnAgregar ) {
                accionesJList.agregarArchivo(modelo);
            }

            //BOTÓN ELIMINAR
            if(e.getSource() == btnEliminar){
                accionesJList.eliminarElementoJList(modelo, lstImagenes);
                accionesImagenes.mostrarImagenEnCambioDeFoco(lstImagenes, modelo, panelImagen, lblImagen);
            }
            
            if(e.getSource() == btnLimpiar){
                accionesJList.limpiarJList(modelo, lstImagenes);
            }
            
            //BOTÓN CANCELAR
            if(e.getSource() == btnCancelar){
                generarPDF.cancelarEjecucion();
            }
            
            //BOTÓN GENERAR
            if(e.getSource() == btnGenerarPDF){
                Queue <String> direccionesImagenes = new LinkedList<String>();
                accionesImagenes.actualizarBufferImagenes(modelo, informacion);
                accionesGenerales.copiarColas(direccionesImagenes, informacion.getDireccionesImagenes());
                
                generarPDF.setDireccionesImagenes(direccionesImagenes);
                ObservadorEstadoGenerarPDF observadorGenerarPDF = new ObservadorEstadoGenerarPDF(btnGenerarPDF, btnCancelar);
                generarPDF.addObserver(observadorGenerarPDF);
                
                hiloGenerarPDF = new Thread(generarPDF);
                
                try {                    
                    if(explorador.existeArchivo(informacion.getRutaGuardarDocumento() + informacion.getTituloPDF() + ".pdf")){
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
     
    }
    
    public class EventoCerrarForm implements WindowListener{

        @Override
        public void windowClosing(WindowEvent e) {
        
            int respuesta = JOptionPane.showConfirmDialog(null, "Al salir perderá todo cambio realizado en esta ventana. \n\n¿Desea salir? ", "Salir", JOptionPane.YES_NO_OPTION);
                
            if(respuesta == JOptionPane.YES_OPTION){
                generarPDF.cancelarEjecucion();
                btnCargarCarpetas.setEnabled(true);
                btnCargarImagenes.setEnabled(true);
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