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
//Importacion de Eventos
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Importación clases
import diseno.DisenioComponentes;
import propiedades.ConsultarMetadatos;
import propiedades.Constantes;
import acciones.AccionesTextFields;
import diseno.DisposicionComponentesAjustes;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

public class Ajustes extends JFrame {
    private JLabel lblApartadoAjustesDocumento;
    private JLabel lblFormatoHoja;
    private JLabel lblMargenes;
    private JLabel lblApartadoValores;
    private JLabel lblTitulo;
    private JLabel lblAutor;
    private JLabel lblPortada;
    private JLabel lblGuardar;
    private JLabel lblSeleccionarArchivo;
    private JLabel lblSeleccionarCarpeta;
    private JButton btnBuscaPortada;
    private JButton btnBuscarGuardar;
    private JButton btnSeleccionarArchivo;
    private JButton btnSeleccionarCarpeta;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextField tfMargenes;
    private JTextField tfTitulo;
    private JTextField tfAutor;
    private JTextField tfPortada;
    private JTextField tfGuardar;
    private JTextField tfSeleccionarArchivo;
    private JTextField tfSeleccionarCarpeta;
    private JRadioButton rbtnA4;
    private JRadioButton rbtnCarta;
    private JRadioButton rbtnCm;
    private ButtonGroup tipoHoja;
    private JRadioButton rbtnPulgadas;
    private JRadioButton rbtnPuntos;
    private ButtonGroup tipoMargenes;
    private DisenioComponentes disenio;
    private ConsultarMetadatos propiedades;
    private Constantes constantes;
    private EventosComponentes eventosComponentes;
    private AccionesTextFields accionesTxtFields;
    
    public Ajustes(){
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
        propiedades = new ConsultarMetadatos();
        constantes = new Constantes();
        eventosComponentes = new EventosComponentes();
        accionesTxtFields = new AccionesTextFields();
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

        DisposicionComponentesAjustes dc = new DisposicionComponentesAjustes(gbc, this);
        
        dc.valoresPredeterminados();
        dc.disposicionFila1(lblApartadoValores);
        dc.disposicionFila2(lblTitulo, tfTitulo);
        dc.disposicionFila3(lblAutor, tfAutor);
        dc.disposicionFila4(lblPortada, tfPortada, btnBuscaPortada);
        dc.disposicionFila5(lblGuardar, tfGuardar, btnBuscarGuardar);
        dc.disposicionFila6(lblFormatoHoja, rbtnA4, rbtnCarta);
        dc.disposicionFila7(lblMargenes, tfMargenes, rbtnCm, rbtnPulgadas, rbtnPuntos);
        dc.disposicionFila8(lblSeleccionarArchivo, tfSeleccionarArchivo, btnSeleccionarArchivo);
        dc.disposicionFila9(lblSeleccionarCarpeta, tfSeleccionarCarpeta, btnSeleccionarCarpeta);
        dc.disposicionFila10(btnCancelar, btnGuardar);
        
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
            
            if(e.getSource() == btnBuscaPortada){
                accionesTxtFields.colocarRutaArchivo(tfPortada);
            }
            
            if(e.getSource() == btnBuscarGuardar){
                accionesTxtFields.colocarRutaCarpeta(tfGuardar);
            }
            
            if(e.getSource() == btnSeleccionarArchivo){
                accionesTxtFields.colocarRutaCarpeta(tfSeleccionarArchivo);
            }
            
            if(e.getSource() == btnSeleccionarCarpeta){
                accionesTxtFields.colocarRutaCarpeta(tfSeleccionarCarpeta);
            }

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
            accionesTxtFields.verificarTeclaIngresada(e, teclaPresionada);
        }
        
        @Override
        public void keyPressed(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
        
    }
    
    
    
}
