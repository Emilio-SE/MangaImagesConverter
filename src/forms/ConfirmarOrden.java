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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//Importación de Clases
import propiedades.Metadatos;
import diseno.DisenioComponentes;
import acciones.*;
import eventos.ComponentesConfirmarOrden;
import eventos.Mouse;
import eventos.Teclado;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import sistemaUndoRedo.*;
//Otras Importaciones
import java.util.LinkedList;
import java.util.List;
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
    private JButton btnCargarCarpetas;
    private JButton btnCargarImagenes;
    private JList lstImagenes;
    //Declaración clases.
    private Metadatos informacion;
    private DisenioComponentes disenio;
    private AccionesExploradorArchivos explorador;
    private AccionesConversiones accionesGenerales;
    private AccionesJList accionesJList;
    private AccionesImagenes accionesImagenes;
    private GenerarPDF generarPDF;
    
    //Variables Globales.
    private DefaultListModel modelo;
    private Queue<String> direccionesCarpetas;
    
    public ConfirmarOrden(Queue<String>direccionesCarpetas, JButton btnCargarCarpetas, JButton btnCargarImagenes){
        this.informacion = Metadatos.getInstancia();
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
        btnSubirPosicionImagen = new JButton("Subir");
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
        generarPDF = new GenerarPDF();
        explorador = new AccionesExploradorArchivos();
        disenio = new DisenioComponentes();
        accionesGenerales = new AccionesConversiones();
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
        accionesJList.colocarRutasImagenesEnJList(modelo, direccionesCarpetas);
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
        ComponentesConfirmarOrden eventosComponentes = new ComponentesConfirmarOrden(modelo, lstImagenes, accionesJList, generarPDF);
        Mouse eventosMouse = new Mouse(modelo, lstImagenes, "ConfirmarOrden");
        Teclado eventosTeclado = new Teclado(modelo, lstImagenes, accionesJList, panelImagen, lblImagen, "ConfirmarOrden");
        
        eventosMouse.setLblImagen(lblImagen);
        eventosMouse.setPanelImagen(panelImagen);
        
        eventosComponentes.setBtnCancelar(btnCancelar);
        eventosComponentes.setBtnGenerarPDF(btnGenerarPDF);
        eventosComponentes.setLblImagen(lblImagen);
        eventosComponentes.setPanelImagen(panelImagen);
        
        btnSubirPosicionImagen.addActionListener(eventosComponentes);
        btnBajarPosicionImagen.addActionListener(eventosComponentes);
        btnAgregar.addActionListener(eventosComponentes);
        btnEliminar.addActionListener(eventosComponentes);
        btnLimpiar.addActionListener(eventosComponentes);
        btnGenerarPDF.addActionListener(eventosComponentes);
        btnCancelar.addActionListener(eventosComponentes);
        lstImagenes.addMouseListener(eventosMouse);
        lstImagenes.addKeyListener(eventosTeclado);
        btnAgregar.addKeyListener(eventosTeclado);
        btnSubirPosicionImagen.addKeyListener(eventosTeclado);
        btnBajarPosicionImagen.addKeyListener(eventosTeclado);
        btnEliminar.addKeyListener(eventosTeclado);
        btnLimpiar.addKeyListener(eventosTeclado);
        this.addWindowListener(new EventoCerrarForm());
        
        lstImagenes.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> rutas = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    accionesJList.agregarArchivosAJList(modelo, rutas);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado "  + ex.getMessage(), "Error inesperado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
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