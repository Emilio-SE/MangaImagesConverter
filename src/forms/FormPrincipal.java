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
import java.awt.Insets;
import java.awt.Color;
//--Importacion Eventos
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//Importación de Clases
import diseno.DisenioComponentes;
import acciones.*;
import propiedades.Constantes;
import propiedades.InformacionGenerales;
//Otras Importaciones
import java.util.Queue;
import java.util.LinkedList;

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
    private InformacionGenerales informacion;
    private Constantes constantes;
    private AccionesExploradorArchivos explorador;
    private EventosComponentes eventosComponentes;
    private AccionesComponentes accionesComponentes;
    private AccionesGenerales accionesGenerales;
    //Variables Globales.
    private DefaultListModel modelo;
    private String tituloPDF;
    private String autorPDF;
    private String margenes;
    private String rutaPortada;
    private String rutaGuardarDocumento;
    private Queue<String> direccionesCarpetas;

    public FormPrincipal(InformacionGenerales informarcion) {
        this.informacion = informarcion;        
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
        tfRutaGuardarDoc = new JTextField(rutaGuardarDocumento);
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
        lstCarpetas = new JList(modelo);
        //Instancias de Clases.
        eventosComponentes = new EventosComponentes();
        disenio = new DisenioComponentes();
        accionesComponentes = new AccionesComponentes( informacion.getRutaAbrirCarpetaEn(), informacion.getRutaAbrirArchivoEn() );
        explorador = new AccionesExploradorArchivos();
        accionesGenerales = new AccionesGenerales();
        constantes = new Constantes();
    }

    private void valoresPredeterminados(){
        //Valores de Ventana.
        this.setTitle("MANGA IMAGES CONVERTER (Ver. 2.1)");
        this.setResizable(false);
        this.setSize(600, 750);
        getContentPane().setBackground(new Color(236, 246, 247));
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        //Variables de Metadatos.
        tituloPDF = informacion.getTituloPDF();
        autorPDF = informacion.getAutorPDF();
        rutaPortada = informacion.getRutaPortada();
        margenes = informacion.getMargenesString();
        rutaGuardarDocumento = informacion.getRutaGuardarDocumento();
        //Asignación Valores de Campos.
        tfTitulo.setText(tituloPDF);
        tfAutor.setText(autorPDF);
        tfPortada.setText(rutaPortada);
        tfMargenes.setText(margenes);
        tfRutaGuardarDoc.setText(rutaGuardarDocumento);
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

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 1;

        //Fila 0
        gbc.gridy = 0;
        
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblApartadoMeta, gbc);

        //Fila 1
        gbc.gridy = 1;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(lblTitulo, gbc);

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfTitulo, gbc);

        //Fila 2
        gbc.gridy = 2;

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(lblAutor, gbc);

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfAutor, gbc);

        //Fila 3
        gbc.gridy = 3;

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(lblPortada, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfPortada, gbc);

        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnPortada, gbc);

        //Fila 4
        gbc.gridy = 4;
        
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblApartadoAjustes, gbc);
        
        //Fila 5
        gbc.gridy = 5;

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridwidth = 1;
        add(lblFormatoHoja, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(rbtnA4, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(rbtnCarta, gbc);

        //Fila 6
        gbc.gridy = 6;
        
        gbc.gridx = 0;
        add(lblMargenes, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfMargenes, gbc);
        
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(rbtnCm, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        add(rbtnPul, gbc);
        
        gbc.anchor = GridBagConstraints.LINE_END;
        add(rbtnPts, gbc);
        
        //Fila 7
        gbc.gridy = 7;

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblGuardarDoc, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfRutaGuardarDoc, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnGuardarDoc, gbc);

        //Fila 8
        gbc.gridy = 8;
        
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAjustesPredeterminados, gbc);
        
        //Fila 9
        gbc.gridy = 9;

        gbc.insets = new Insets(10, 0, 10, 0);
        add(lblApartadoSeleccion, gbc);

        //Fila 10
        gbc.gridy = 10;
        
        gbc.insets = new Insets(0, 30, 0, 0);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(btnSubir, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnBajar);
        add(btnBajar, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnAgregar, gbc);
        
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(btnEliminar, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnLimpiar, gbc);
        
        //Fila 11
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.fill = GridBagConstraints.BOTH;
        add(lstCarpetas, gbc);
        
        //Fila 12
        gbc.gridy = 12;
        gbc.gridwidth = 1;

        gbc.insets = new Insets(0, 30, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(btnCargarCarpetas, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCargarImagenes, gbc);

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnActualizar, gbc);

        //Fila 13
        gbc.gridy = 13;

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblPieDePagina, gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(btnInfo, gbc);
        
        this.setVisible(true);

    }

    private void eventos(){
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
        tfTitulo.getDocument().addDocumentListener(new cambiosTextField());
        tfAutor.getDocument().addDocumentListener(new cambiosTextField());
        tfPortada.getDocument().addDocumentListener(new cambiosTextField());
        tfRutaGuardarDoc.getDocument().addDocumentListener(new cambiosTextField());
        tfMargenes.getDocument().addDocumentListener(new cambiosTextField());
        tfMargenes.addKeyListener(new textoIngresado());
    }

    private void actualizarMetadatos(){
        tituloPDF = tfTitulo.getText();
        autorPDF = tfAutor.getText();
        margenes = tfMargenes.getText();
        rutaPortada = tfPortada.getText();
        rutaGuardarDocumento = tfRutaGuardarDoc.getText();

        if( !tituloPDF.equals("") && !informacion.getTituloPDF().equals( tituloPDF ) ){
            informacion.setTituloPDF( tituloPDF );
        }

        if(!autorPDF.equals("") && !informacion.getAutorPDF().equals( autorPDF ) ){
            informacion.setAutorPDF(autorPDF);
        }

        if( !informacion.getRutaPortada().equals(rutaPortada) ){
            if(rutaPortada.equals("") || explorador.existeArchivo( rutaPortada )){
                informacion.setRutaPortada( rutaPortada );
            }else{
                rutaPortada = "";
                informacion.setRutaPortada( rutaPortada );
            }
        }

        if( !margenes.equals("")){
            if(rbtnCm.isSelected()){
                informacion.setMargenesFloat( accionesGenerales.cmApts(margenes) );
            }else if(rbtnPul.isSelected()){
                informacion.setMargenesFloat( accionesGenerales.pulApts(margenes) );
            }else{
                informacion.setMargenesFloat( accionesGenerales.margenesTxtAFloat(margenes) );
            }
        }
        
        if(explorador.existeArchivo( rutaGuardarDocumento )){
            informacion.setRutaGuardarDocumento( rutaGuardarDocumento );
        }
        
        disenio.desactivarBtnActualizarMeta(btnActualizar);
        
    }
    
    public class textoIngresado implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            int teclaPresionada = (int) e.getKeyChar();
            accionesComponentes.verificarTeclaIngresada(e, teclaPresionada);
        }
        
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
        
    }
    
    public class cambiosTextField implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            disenio.activarBtnActualizarMeta(btnActualizar);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            disenio.activarBtnActualizarMeta(btnActualizar);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {}
        
    }
    
    public class EventosComponentes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            //RADIO BUTTON A4
            if(e.getSource() == rbtnA4){
                informacion.setTipoHoja(constantes.TAMANIO_A4);
            }
            
            //RADIO BUTTON CARTA
            if(e.getSource() == rbtnCarta){
                informacion.setTipoHoja(constantes.TAMANIO_CARTA);
            }
            
            //RADIO BUTTON CM / PUL / PTS
            if(e.getSource() == rbtnCm || e.getSource() == rbtnPul || e.getSource() == rbtnPts){
                disenio.activarBtnActualizarMeta(btnActualizar);
            }
            
            //BOTÓN PORTADA
            if (e.getSource() == btnPortada ) {
                accionesComponentes.colocarRutaArchivo(tfPortada);
            }

            //BOTÓN AJUSTES PREDETERMINADOS
            if(e.getSource() == btnAjustesPredeterminados){
                actualizarMetadatos();
                Ajustes ajustes = new Ajustes(informacion);
            }
            
            //BOTÓN GUARDAR DOC
            if(e.getSource() == btnGuardarDoc){
                accionesComponentes.colocarRutaCarpeta(tfRutaGuardarDoc);
            }

            //BOTÓN SUBIR
            if(e.getSource() == btnSubir){
                accionesComponentes.subirElementoList(modelo, lstCarpetas);
            }
            
            //BOTÓN BAJAR
            if(e.getSource() == btnBajar){
                accionesComponentes.bajarElementoList(modelo, lstCarpetas);
            }
            
            //BOTÓN ELIMINAR
            if(e.getSource() == btnEliminar){
                accionesComponentes.eliminarCarpeta(modelo, lstCarpetas);
            }
            
            //BOTÓN LIMPIAR
            if(e.getSource() == btnLimpiar){
                modelo.clear();
            }

            //BOTÓN AGREGAR
            if (e.getSource() == btnAgregar ) {
                if(modelo.getSize() < 16){
                    
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea agregar subcarpetas?", "Subcarpetas", JOptionPane.YES_NO_OPTION);
                    
                    if(respuesta == 0){
                        accionesComponentes.agregarCarpetasRecursivamente(modelo);
                    }else if(respuesta == 1){
                        accionesComponentes.agregarCarpetas(modelo);
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Limite de 16 carpetas");
                }
           }

            //BOTÓN CARGAR
            if(e.getSource() == btnCargarCarpetas){
                
                if(!modelo.isEmpty()){

                    actualizarMetadatos();
                    
                    //Comparten misma dirección en memoria.
                    direccionesCarpetas = accionesComponentes.obtenerDireccionesLista(modelo);
                    
                    if(!direccionesCarpetas.isEmpty()){
                        ConfirmarOrden confirmarOrden = new ConfirmarOrden(informacion, direccionesCarpetas);
                    }else{
                        JOptionPane.showMessageDialog(null, "La o las carpetas no se han podido cargar, asegurese que todos los directorios sean existentes.", "Error al cargar carpetas", JOptionPane.WARNING_MESSAGE);
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "¡Ánimate! Agregra una carpeta :D", ":3", JOptionPane.INFORMATION_MESSAGE);
                }

            }

            //BOTÓN CARGAR IMAGENES
            if(e.getSource() == btnCargarImagenes){                
                actualizarMetadatos();
                direccionesCarpetas = new LinkedList<String>();
                ConfirmarOrden confirmarOrden = new ConfirmarOrden(informacion, direccionesCarpetas);

            }

            //BOTÓN Actualizar
            if(e.getSource() == btnActualizar){
                actualizarMetadatos();
                disenio.desactivarBtnActualizarMeta(btnActualizar);
            }
            
            if(e.getSource() == btnInfo){
                
            }
            
        }

    }
    
}