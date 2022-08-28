package forms;

//Importación de componentes Swing y Awt
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
//Importación de Clases
import diseno.*;
import acciones.*;
import eventos.CambiosTextField;
import eventos.Mouse;
import eventos.Teclado;
import eventos.ComponentesFormPrincipal;
import propiedades.Constantes;
import propiedades.Metadatos;
//Otras Importaciones
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.util.List;
import java.io.File;

public class FormPrincipal extends JFrame{

    //Declaración Componentes.
    private JLabel lblApartadoMeta;
    private JLabel lblTitulo;
    private JLabel lblAutor;
    private JLabel lblPortada;
    private JLabel lblApartadoAjustes;
    private JLabel lblFormatoHoja;
    private JLabel lblGuardarDoc;
    private JLabel lblApartadoSeleccion;
    private JLabel lblMargenes;
    private JLabel lblPieDePagina;
    private JTextField tfTitulo;
    private JTextField tfAutor;
    private JTextField tfMargenes;
    private JTextField tfPortada;
    private JTextField tfRutaGuardarDoc;
    private JList lstCarpetas;
    private JButton btnPortada;
    private JButton btnGuardarDoc;
    private JButton btnSubir;
    private JButton btnBajar;
    private JButton btnLimpiar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnAjustesPredeterminados;
    private JButton btnCargarCarpetas;
    private JButton btnCargarImagenes;
    private JButton btnActualizar;
    private JButton btnInfo;
    private JRadioButton rbtnA4;
    private JRadioButton rbtnCarta;
    private JRadioButton rbtnCm;
    private JRadioButton rbtnPts;
    private JRadioButton rbtnPul;
    private ButtonGroup tipoHoja;
    private ButtonGroup tipoMargenes;
    //Declaración clases.
    private DisenioComponentes disenio;
    private Metadatos informacion;
    private Constantes constantes;

    private AccionesJList accionesJList;


    //Variables Globales.
    private DefaultListModel modelo;

    public FormPrincipal() {
        this.informacion = Metadatos.getInstancia();        
        instancias();
        valoresPredeterminados();
        disenioForm();
        eventos();
    }

    private void instancias(){
        //Instacias de componentes.
        lblApartadoMeta = new JLabel("- METADATOS -");
        lblTitulo = new JLabel("Título:");
        lblAutor = new JLabel("Autor:");
        lblPortada = new JLabel("Portada:");
        lblFormatoHoja = new JLabel("Formato de Hoja:");
        lblApartadoAjustes = new JLabel("- AJUSTES -");
        lblGuardarDoc = new JLabel("Guardar PDF en:");
        lblApartadoSeleccion = new JLabel("- SELECCIÓN DE CARPETAS -");
        lblMargenes = new JLabel("Margenes:");
        lblPieDePagina = new JLabel("Ideado, diseñado y desarrollado por Emilio SE.");
        tfTitulo = new JTextField();
        tfAutor = new JTextField();
        tfMargenes = new JTextField();
        tfPortada = new JTextField();
        tfRutaGuardarDoc = new JTextField();
        btnAjustesPredeterminados = new JButton("Cambiar Ajustes Predeterminados");
        btnPortada = new JButton("Seleccionar");
        btnGuardarDoc = new JButton("Cambiar Ruta");
        btnSubir = new JButton("Subir");
        btnBajar = new JButton("Bajar");
        btnLimpiar = new JButton("Limpiar");
        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");
        btnCargarCarpetas = new JButton("Cargar Carpetas");
        btnCargarImagenes = new JButton("Agrega solo Imágenes");
        btnActualizar = new JButton("Actualizar Metadatos.");
        btnInfo = new JButton("Más Info");
        tipoHoja = new ButtonGroup();
        tipoMargenes = new ButtonGroup();
        rbtnA4 = new JRadioButton("A4");
        rbtnCarta = new JRadioButton("Carta");
        rbtnCm = new JRadioButton("cm");
        rbtnPul = new JRadioButton("pul");
        rbtnPts = new JRadioButton("pts");
        modelo = new DefaultListModel();
        lstCarpetas = new RedimensionamientoJList(modelo, new Dimension(50, 270));
        //Instancias de Clases.
        disenio = new DisenioComponentes();
        accionesJList = new AccionesJList( informacion.getRutaAbrirCarpetaEn(), informacion.getRutaAbrirArchivoEn() );
        constantes = new Constantes();
    }

    private void valoresPredeterminados(){
        //Valores de Ventana.
        this.setTitle("MANGA IMAGES CONVERTER (Ver. 2.2.2)");
        this.setResizable(false);
        this.setSize(600, 750);
        getContentPane().setBackground(new Color(236, 246, 247));
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        //Asignación Valores de Campos.
        tfTitulo.setText( informacion.getTituloPDF() );
        tfAutor.setText( informacion.getAutorPDF() );
        tfPortada.setText( informacion.getRutaPortada() );
        tfMargenes.setText( informacion.getMargenesString() );
        tfRutaGuardarDoc.setText( informacion.getRutaGuardarDocumento() );
        //Visibilidad y Selección
        if(informacion.getTipoHoja() == constantes.TAMANIO_CARTA){
            rbtnCarta.setSelected(true);
        }else{
            rbtnA4.setSelected(true);
        }
        
        if(informacion.getUnidadMedida() == constantes.UNIDAD_CM){
            rbtnCm.setSelected(true);
        }else if(informacion.getUnidadMedida() == constantes.UNIDAD_PUL){
            rbtnPul.setSelected(true);
        }else{
            rbtnPts.setSelected(true);
        }
        
        btnActualizar.setEnabled(false);
        
        //Otros.
        tipoHoja.add(rbtnA4);
        tipoHoja.add(rbtnCarta);
        tipoMargenes.add(rbtnCm);
        tipoMargenes.add(rbtnPul);
        tipoMargenes.add(rbtnPts);
    }

    private void disenioForm() {

        // -----------Diseño Componentes-----------
        disenio.disenioEtiquetas(lblTitulo);
        disenio.disenioEtiquetas(lblAutor);
        disenio.disenioEtiquetas(lblPortada);
        disenio.disenioEtiquetas(lblFormatoHoja);
        disenio.disenioEtiquetas(lblGuardarDoc);
        disenio.disenioEtiquetas(lblMargenes);
        disenio.disenioEtiquetas(lblPieDePagina);
        
        disenio.disenioTitulos(lblApartadoMeta);
        disenio.disenioTitulos(lblApartadoAjustes);
        disenio.disenioTitulos(lblApartadoSeleccion);
        
        disenio.disenioBotones(btnInfo, "Más información.");
        disenio.disenioBotones(btnPortada, "Selecciona una imagen de portada.");
        disenio.disenioBotones(btnAjustesPredeterminados, "Cambia los ajustes predeterminados del programa");
        disenio.disenioBotones(btnGuardarDoc, "Selecciona la ruta donde guardar el documento.");
        disenio.disenioBotones(btnSubir, "Sube de posición la carpeta seleccionada.");
        disenio.disenioBotones(btnBajar, "Baja de posición la carpeta seleccionada.");
        disenio.disenioBotones(btnEliminar, "Elimina la carpeta seleccionada.");
        disenio.disenioBotones(btnLimpiar, "Eliminar la lista completa.");
        disenio.disenioBotones(btnAgregar, "Agrega una nueva carpeta.");
        disenio.disenioBotones(btnCargarCarpetas, "Carga las carpetas y muestra las imagenes.");
        disenio.disenioBotones(btnCargarImagenes, "Carga imagenes sin necesidad de seleccionar carpetas.");
        disenio.disenioBotones(btnActualizar, "¡Actualiza los metadatos!");
        disenio.desactivarBtnActualizarMeta(btnActualizar);
        
        disenio.disenioCuadrosDeTexto(tfTitulo);
        disenio.disenioCuadrosDeTexto(tfAutor);
        disenio.disenioCuadrosDeTexto(tfPortada);
        disenio.disenioCuadrosDeTexto(tfMargenes);
        disenio.disenioCuadrosDeTexto(tfRutaGuardarDoc);
        
        disenio.disenioList(lstCarpetas);
        
        disenio.disenioRadioButton(rbtnA4);
        disenio.disenioRadioButton(rbtnCarta);
        disenio.disenioRadioButton(rbtnCm);
        disenio.disenioRadioButton(rbtnPul);
        disenio.disenioRadioButton(rbtnPts);
        
        tfMargenes.setToolTipText("<html>Margenes: Superior, Derecho, Inferior, Izquierdo <br><br> Cada margen debe ir separado por una coma.</html>  ");
        
        // -----------Disposición Componentes-----------
        GridBagConstraints gbc = new GridBagConstraints();

        DisposicionComponentesFormPrincipal dc = new DisposicionComponentesFormPrincipal(gbc, this);
        
        dc.valoresPredeterminados();
        dc.disposicionFila1(lblApartadoMeta);
        dc.disposicionFila2(lblTitulo, tfTitulo);
        dc.disposicionFila3(lblAutor, tfAutor);
        dc.disposicionFila4(lblPortada, tfPortada, btnPortada);
        dc.disposicionFila5(lblApartadoAjustes);
        dc.disposicionFila6(lblFormatoHoja, rbtnA4, rbtnCarta);
        dc.disposicionFila7(lblMargenes, tfMargenes, rbtnCm, rbtnPul, rbtnPts);
        dc.disposicionFila8(lblGuardarDoc, tfRutaGuardarDoc, btnGuardarDoc);
        dc.disposicionFila9(btnAjustesPredeterminados);
        dc.disposicionFila10(lblApartadoSeleccion);
        dc.disposicionFila11(btnSubir, btnBajar, btnAgregar, btnEliminar, btnLimpiar);
        dc.disposicionFila12(lstCarpetas);
        dc.disposicionFila13(btnCargarCarpetas, btnCargarImagenes, btnActualizar);
        dc.disposicionFila14(lblPieDePagina, btnInfo);
        
        this.setVisible(true);

    }
    
    private void eventos(){
        ComponentesFormPrincipal eventosComponentes = new ComponentesFormPrincipal(modelo, lstCarpetas, accionesJList);
        CambiosTextField cambiosTextField = new CambiosTextField(btnActualizar);
        Teclado eventosTeclado = new Teclado(modelo, lstCarpetas, accionesJList, tfMargenes, "FormPrincipal");
        Mouse eventosMouse = new Mouse(modelo, lstCarpetas, "FormPrincipal");
        
        eventosComponentes.setBtnActualizar(btnActualizar);
        eventosComponentes.setBtnCargarCarpetas(btnCargarCarpetas);
        eventosComponentes.setBtnCargarImagenes(btnCargarImagenes);
        eventosComponentes.setRbtnCm(rbtnCm);
        eventosComponentes.setRbtnPts(rbtnPts);
        eventosComponentes.setRbtnPul(rbtnPul);
        eventosComponentes.setTfAutor(tfAutor);
        eventosComponentes.setTfMargenes(tfMargenes);
        eventosComponentes.setTfRutaGuardarDoc(tfRutaGuardarDoc);
        eventosComponentes.setTfPortada(tfPortada);
        eventosComponentes.setTfTitulo(tfTitulo);
        
        btnPortada.addActionListener(eventosComponentes);
        btnAjustesPredeterminados.addActionListener(eventosComponentes);
        btnGuardarDoc.addActionListener(eventosComponentes);
        btnSubir.addActionListener(eventosComponentes);
        btnBajar.addActionListener(eventosComponentes);
        btnEliminar.addActionListener(eventosComponentes);
        btnLimpiar.addActionListener(eventosComponentes);
        btnAgregar.addActionListener(eventosComponentes);
        btnCargarCarpetas.addActionListener(eventosComponentes);
        btnCargarImagenes.addActionListener(eventosComponentes);
        btnActualizar.addActionListener(eventosComponentes);
        btnInfo.addActionListener(eventosComponentes);
        rbtnA4.addActionListener(eventosComponentes);
        rbtnCarta.addActionListener(eventosComponentes);
        rbtnCm.addActionListener(eventosComponentes);
        rbtnPts.addActionListener(eventosComponentes);
        rbtnPul.addActionListener(eventosComponentes);
        tfTitulo.getDocument().addDocumentListener(cambiosTextField);
        tfAutor.getDocument().addDocumentListener(cambiosTextField);
        tfPortada.getDocument().addDocumentListener(cambiosTextField);
        tfRutaGuardarDoc.getDocument().addDocumentListener(cambiosTextField);
        tfMargenes.getDocument().addDocumentListener(cambiosTextField);
        tfMargenes.addKeyListener(eventosTeclado);
        lstCarpetas.addMouseListener(eventosMouse);
        lstCarpetas.addKeyListener(eventosTeclado);
        btnAgregar.addKeyListener(eventosTeclado);
        btnBajar.addKeyListener(eventosTeclado);
        btnSubir.addKeyListener(eventosTeclado);
        btnEliminar.addKeyListener(eventosTeclado);
        btnLimpiar.addKeyListener(eventosTeclado);
        
        lstCarpetas.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> rutas = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    accionesJList.agregarCarpetasAJList(modelo, rutas);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado "  + ex.getMessage(), "Error inesperado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }

}