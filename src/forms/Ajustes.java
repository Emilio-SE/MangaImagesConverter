package forms;

//Importación de componentes awt y Swing
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
//Importacion de Eventos
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Importación clases
import diseno.DisenioComponentes;
import propiedades.Propiedades;
import propiedades.Constantes;
import acciones.AccionesComponentes;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import propiedades.InformacionGenerales;

public class Ajustes extends JFrame {
    JLabel lblApartadoAjustesDocumento;
    JLabel lblFormatoHoja;
    JLabel lblMargenes;
    JLabel lblApartadoValores;
    JLabel lblTitulo;
    JLabel lblAutor;
    JLabel lblPortada;
    JLabel lblGuardar;
    JLabel lblSeleccionarArchivo;
    JLabel lblSeleccionarCarpeta;
    JButton btnBuscaPortada;
    JButton btnBuscarGuardar;
    JButton btnSeleccionarArchivo;
    JButton btnSeleccionarCarpeta;
    JButton btnGuardar;
    JButton btnCancelar;
    JTextField tfMargenes;
    JTextField tfTitulo;
    JTextField tfAutor;
    JTextField tfPortada;
    JTextField tfGuardar;
    JTextField tfSeleccionarArchivo;
    JTextField tfSeleccionarCarpeta;
    JRadioButton rbtnA4;
    JRadioButton rbtnCarta;
    JRadioButton rbtnCm;
    ButtonGroup tipoHoja;
    JRadioButton rbtnPulgadas;
    JRadioButton rbtnPuntos;
    ButtonGroup tipoMargenes;
    DisenioComponentes disenio;
    private Propiedades propiedades;
    private Constantes constantes;
    private EventosComponentes eventosComponentes;
    private AccionesComponentes accionesComponentes;
    private InformacionGenerales informacion;
    
    public Ajustes(InformacionGenerales informarcion){
        this.informacion = informarcion;
        instancias();
        valoresPredeterminados();
        disenioForm();
        eventos();
    }
    
    private void instancias(){
        //Instanciación de componentes awt y Swing
        lblApartadoAjustesDocumento = new JLabel("- Ajustes del Documento Actual -");
        lblFormatoHoja = new JLabel("Formato de Hoja");
        lblMargenes = new JLabel("Margenes");
        lblApartadoValores = new JLabel("- Valores Predeterminados -");
        lblTitulo = new JLabel("Título");
        lblAutor = new JLabel("Autor");
        lblPortada = new JLabel("Portada");
        lblGuardar = new JLabel("Guardar");
        lblSeleccionarArchivo = new JLabel("Seleccionar Archivo En: ");
        lblSeleccionarCarpeta = new JLabel("Seleccionar Carpeta En: ");
        btnBuscaPortada = new JButton("Seleccionar");
        btnBuscarGuardar = new JButton("Seleccionar");
        btnSeleccionarArchivo = new JButton("Seleccionar");
        btnSeleccionarCarpeta = new JButton("Seleccionar");
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        tfMargenes = new JTextField();
        tfTitulo = new JTextField();
        tfAutor = new JTextField();
        tfPortada = new JTextField();
        tfGuardar = new JTextField();
        tfSeleccionarArchivo = new JTextField();
        tfSeleccionarCarpeta = new JTextField();
        rbtnA4 = new JRadioButton("A4");
        rbtnCarta = new JRadioButton("Carta");
        tipoHoja = new ButtonGroup();
        rbtnCm = new JRadioButton("cm");
        rbtnPulgadas = new JRadioButton("pul");
        rbtnPuntos = new JRadioButton("pts");
        tipoMargenes = new ButtonGroup();
        //Instanciación de clases
        disenio = new DisenioComponentes();
        propiedades = new Propiedades();
        constantes = new Constantes();
        eventosComponentes = new EventosComponentes();
        accionesComponentes = new AccionesComponentes(informacion.getRutaAbrirCarpetaEn(), informacion.getRutaAbrirArchivoEn());
    }
    
    private void valoresPredeterminados(){
        //Valores ventana
        this.setResizable(false);
        this.setSize(600, 400);
        getContentPane().setBackground(new Color(236, 246, 247));
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        
        tipoHoja.add(rbtnA4);
        tipoHoja.add(rbtnCarta);
        tipoMargenes.add(rbtnCm);
        tipoMargenes.add(rbtnPulgadas);
        tipoMargenes.add(rbtnPuntos);
        //System.out.println(propiedades.getAutor());
        propiedades.consultarPropiedades();
        tfTitulo.setText(propiedades.getTitulo());
        tfAutor.setText(propiedades.getAutor());
        tfPortada.setText(propiedades.getPortada());
        tfGuardar.setText(propiedades.getGuardar_En());
        tfMargenes.setText(propiedades.getMargenes());
        tfSeleccionarArchivo.setText(propiedades.getSeleccionar_Archivo_En());
        tfSeleccionarCarpeta.setText(propiedades.getSeleccionar_Carpeta_En());
        
        if(propiedades.getFormato_Hoja() == constantes.TAMANIO_A4){
            rbtnA4.setSelected(true);
        }else{
            rbtnCarta.setSelected(true);
        }
        
        if(propiedades.getUnidades() == constantes.UNIDAD_CM){
            rbtnCm.setSelected(true);
        }else if(propiedades.getUnidades() == constantes.UNIDAD_PUL){
            rbtnPulgadas.setSelected(true);
        }else{
            rbtnPuntos.setSelected(true);
        }
        
    }

    private void disenioForm(){
        
        disenio.disenioTitulos(lblApartadoValores);
        disenio.disenioEtiquetas(lblFormatoHoja);
        disenio.disenioEtiquetas(lblMargenes);
        disenio.disenioEtiquetas(lblTitulo);
        disenio.disenioEtiquetas(lblAutor);
        disenio.disenioEtiquetas(lblPortada);
        disenio.disenioEtiquetas(lblGuardar);
        disenio.disenioEtiquetas(lblSeleccionarArchivo);
        disenio.disenioEtiquetas(lblSeleccionarCarpeta);
        
        disenio.disenioCuadrosDeTexto(tfMargenes);
        disenio.disenioCuadrosDeTexto(tfTitulo);
        disenio.disenioCuadrosDeTexto(tfAutor);
        disenio.disenioCuadrosDeTexto(tfPortada);
        disenio.disenioCuadrosDeTexto(tfGuardar);
        disenio.disenioCuadrosDeTexto(tfSeleccionarArchivo);
        disenio.disenioCuadrosDeTexto(tfSeleccionarCarpeta);
        
        disenio.disenioBotones(btnBuscaPortada, "");
        disenio.disenioBotones(btnBuscarGuardar, "");
        disenio.disenioBotones(btnSeleccionarArchivo, "");
        disenio.disenioBotones(btnSeleccionarCarpeta, "");
        disenio.disenioBotones(btnCancelar, "");
        disenio.disenioBotones(btnGuardar, "");
        
        disenio.disenioRadioButton(rbtnA4);
        disenio.disenioRadioButton(rbtnCarta);
        disenio.disenioRadioButton(rbtnCm);
        disenio.disenioRadioButton(rbtnPulgadas);
        disenio.disenioRadioButton(rbtnPuntos);
        
        // -----------Disposición Componentes-----------
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 1;
        
        //Fila 0
        gbc.gridy = 0;
        
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(lblApartadoValores, gbc);
        
        //Fila 1
        gbc.gridy = 1;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnBuscaPortada, gbc);
        
        //Fila 4
        gbc.gridy = 4;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(lblGuardar, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfGuardar, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        add(btnBuscarGuardar, gbc);
        
        //Fila 5
        gbc.gridy = 5;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(lblFormatoHoja, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(rbtnA4, gbc);
        
        gbc.anchor = GridBagConstraints.LINE_END;
        add(rbtnCarta, gbc);
        
        //Fila 6
        gbc.gridy = 6;
        
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
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
        add(rbtnPulgadas, gbc);
        
        gbc.anchor = GridBagConstraints.LINE_END;
        add(rbtnPuntos, gbc);
        
        //Fila 7
        gbc.gridy = 7;
        
        gbc.gridwidth = 1;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblSeleccionarArchivo, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfSeleccionarArchivo, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnSeleccionarArchivo, gbc);
        
        //Fila 8
        gbc.gridy = 8;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(lblSeleccionarCarpeta, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfSeleccionarCarpeta, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnSeleccionarCarpeta, gbc);
        
        //Fila 9
        gbc.gridy = 9;

        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCancelar, gbc);
        
        gbc.gridx = 1;
        add(btnGuardar, gbc);
        
        setVisible(true);
    }
    
    private void eventos() {
        btnBuscaPortada.addActionListener(eventosComponentes);
        btnBuscarGuardar.addActionListener(eventosComponentes);
        btnSeleccionarArchivo.addActionListener(eventosComponentes);
        btnSeleccionarCarpeta.addActionListener(eventosComponentes);
        btnGuardar.addActionListener(eventosComponentes);
        btnCancelar.addActionListener(eventosComponentes);
        tfMargenes.addKeyListener(new textoIngresado());    
    }
    
    public class EventosComponentes implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            //BOTÓN BUSCAR PORTADA
            if(e.getSource() == btnBuscaPortada){
                accionesComponentes.colocarRutaArchivo(tfPortada);
            }
            
            //BOTÓN GUARDAR
            if(e.getSource() == btnBuscarGuardar){
                accionesComponentes.colocarRutaCarpeta(tfGuardar);
            }
            
            //BOTÓN SELECCIONAR ARCHIVO EN
            if(e.getSource() == btnSeleccionarArchivo){
                accionesComponentes.colocarRutaCarpeta(tfSeleccionarArchivo);
            }
            
            //BOTÓN SELECCIONAR CARPETA EN
            if(e.getSource() == btnSeleccionarCarpeta){
                accionesComponentes.colocarRutaCarpeta(tfSeleccionarCarpeta);
            }

            //BOTÓN GUARDAR
            if(e.getSource() == btnGuardar){
                
                String formatoHoja, unidades;
                
                if(rbtnA4.isSelected()){
                    formatoHoja = Integer.toString(constantes.TAMANIO_A4);
                }else{
                    formatoHoja = Integer.toString(constantes.TAMANIO_CARTA);
                }
                
                if(rbtnCm.isSelected()){
                    unidades = Integer.toString(constantes.UNIDAD_CM);
                }else if(rbtnPulgadas.isSelected()){
                    unidades = Integer.toString(constantes.UNIDAD_PUL);
                }else{
                    unidades = Integer.toString(constantes.UNIDAD_PTS);
                }

                String margenes = tfMargenes.getText();
                try{
                    Double margen = Double.parseDouble( margenes );
                    
                    if(margen < 0){
                        margen = 0.0;
                    }
                    
                    margenes = margen + ", " + margen + ", " + margen + ", " + margen;
                }catch(NumberFormatException ex){}
                
                
                
                propiedades.sobreescribirPropiedades(tfTitulo.getText(), tfAutor.getText(), tfPortada.getText(), tfGuardar.getText(), formatoHoja, margenes, unidades, tfSeleccionarArchivo.getText(), tfSeleccionarCarpeta.getText());
            }
            
            //BOTÓN CANCELAR
            if(e.getSource() == btnCancelar){
                
                int respuesta = JOptionPane.showConfirmDialog(null, "Al salir perderá todo cambio realizado en esta ventana. \n\n¿Desea salir? ", "Salir", JOptionPane.YES_NO_OPTION);
                
                if(respuesta == 0){
                    dispose();
                }
                
            }  
                  
        }
        
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
    
    
    
}
