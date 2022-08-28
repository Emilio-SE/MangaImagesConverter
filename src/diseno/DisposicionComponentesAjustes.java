package diseno;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class DisposicionComponentesAjustes {
    private GridBagConstraints gbc;
    private JFrame frame;
    
    public DisposicionComponentesAjustes(GridBagConstraints gbc, JFrame frame){
        this.gbc = gbc;
        this.frame = frame;
    }
    
    public void valoresPredeterminados(){
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 1;
    }
    
    public void disposicionFila1(JLabel lblApartadoValores){
        gbc.gridy = 0;
        
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        frame.add(lblApartadoValores, gbc);
    }
    
    public void disposicionFila2(JLabel lblTitulo, JTextField tfTitulo){
        gbc.gridy = 1;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        frame.add(lblTitulo, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfTitulo, gbc);
    }
    
    public void disposicionFila3(JLabel lblAutor, JTextField tfAutor){
        gbc.gridy = 2;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        frame.add(lblAutor, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfAutor, gbc);
    }
    
    public void disposicionFila4(JLabel lblPortada, JTextField tfPortada, JButton btnBuscaPortada){
        gbc.gridy = 3;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        frame.add(lblPortada, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfPortada, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(btnBuscaPortada, gbc);
    }
    
    public void disposicionFila5(JLabel lblGuardar, JTextField tfGuardar, JButton btnBuscarGuardar){
        gbc.gridy = 4;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        frame.add(lblGuardar, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfGuardar, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        frame.add(btnBuscarGuardar, gbc);
    }
    
    public void disposicionFila6(JLabel lblFormatoHoja, JRadioButton rbtnA4, JRadioButton rbtnCarta){
        gbc.gridy = 5;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        frame.add(lblFormatoHoja, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(rbtnA4, gbc);
        
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(rbtnCarta, gbc);
    }
    
    public void disposicionFila7(JLabel lblMargenes, JTextField tfMargenes, JRadioButton rbtnCm, JRadioButton rbtnPul, JRadioButton rbtnPts){
        gbc.gridy = 6;
        
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(lblMargenes, gbc);
        
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfMargenes, gbc);
        
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(rbtnCm, gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(rbtnPul, gbc);
        
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(rbtnPts, gbc);
    }
    
    public void disposicionFila8(JLabel lblSeleccionarArchivo, JTextField tfSeleccionarArchivo, JButton btnSeleccionarArchivo){
        gbc.gridy = 7;
        
        gbc.gridwidth = 1;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(lblSeleccionarArchivo, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfSeleccionarArchivo, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(btnSeleccionarArchivo, gbc);
    }
    
    public void disposicionFila9(JLabel lblSeleccionarCarpeta, JTextField tfSeleccionarCarpeta, JButton btnSeleccionarCarpeta){
        gbc.gridy = 8;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        frame.add(lblSeleccionarCarpeta, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfSeleccionarCarpeta, gbc);
        
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(btnSeleccionarCarpeta, gbc);
    }
    
    public void disposicionFila10(JButton btnCancelar, JButton btnGuardar){
        gbc.gridy = 9;

        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnCancelar, gbc);
        
        gbc.gridx = 1;
        frame.add(btnGuardar, gbc);
    }
}
