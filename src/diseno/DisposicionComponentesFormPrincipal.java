package diseno;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DisposicionComponentesFormPrincipal {
    
    private GridBagConstraints gbc;
    private JFrame frame;
    
    public DisposicionComponentesFormPrincipal(GridBagConstraints gbc, JFrame frame){
        this.gbc = gbc;
        this.frame = frame;
    }
    
    public void valoresPredeterminados(){
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridheight = 1;
    }
    
    public void disposicionFila1(JLabel lblApartadoMeta){
        gbc.gridy = 0;
        
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(lblApartadoMeta, gbc);
    }
    
    public void disposicionFila2(JLabel lblTitulo, JTextField tfTitulo){
        gbc.gridy = 1;
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
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
    
    public void disposicionFila4(JLabel lblPortada, JTextField tfPortada, JButton btnPortada){
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
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(btnPortada, gbc);
    }
    
    public void disposicionFila5(JLabel lblApartadoAjustes){
        gbc.gridy = 4;
        
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(lblApartadoAjustes, gbc);
    }
    
    public void disposicionFila6(JLabel lblFormatoHoja, JRadioButton rbtnA4, JRadioButton rbtnCarta){
        gbc.gridy = 5;

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridwidth = 1;
        frame.add(lblFormatoHoja, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(rbtnA4, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(rbtnCarta, gbc);
    }
    
    public void disposicionFila7(JLabel lblMargenes, JTextField tfMargenes, JRadioButton rbtnCm, JRadioButton rbtnPul, JRadioButton rbtnPts){
        gbc.gridy = 6;
        
        gbc.gridx = 0;
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
    
    public void disposicionFila8(JLabel lblGuardarDoc, JTextField tfRutaGuardarDoc, JButton btnGuardarDoc){
        gbc.gridy = 7;

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(lblGuardarDoc, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(tfRutaGuardarDoc, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 60, 0, 30);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnGuardarDoc, gbc);
    }
    
    public void disposicionFila9(JButton btnAjustesPredeterminados){
        gbc.gridy = 8;
        
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnAjustesPredeterminados, gbc);
    }
    
    public void disposicionFila10(JLabel lblApartadoSeleccion){
        gbc.gridy = 9;

        gbc.insets = new Insets(10, 0, 10, 0);
        frame.add(lblApartadoSeleccion, gbc);
    }
    
    public void disposicionFila11(JButton btnSubir, JButton btnBajar, JButton btnAgregar, JButton btnEliminar, JButton btnLimpiar){
        gbc.gridy = 10;
        
        gbc.insets = new Insets(0, 30, 0, 0);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(btnSubir, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(btnBajar);
        frame.add(btnBajar, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnAgregar, gbc);
        
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(btnEliminar, gbc);
        
        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(btnLimpiar, gbc);
    }
    
    public void disposicionFila12(JList lstCarpetas){
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(new JScrollPane(lstCarpetas),gbc);
    }
    
    public void disposicionFila13(JButton btnCargarCarpetas, JButton btnCargarImagenes, JButton btnActualizar){
        gbc.gridy = 12;
        gbc.gridwidth = 1;

        gbc.insets = new Insets(0, 30, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        frame.add(btnCargarCarpetas, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(btnCargarImagenes, gbc);

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(btnActualizar, gbc);
    }
    
    public void disposicionFila14(JLabel lblPieDePagina, JButton btnInfo){
        gbc.gridy = 13;

        gbc.insets = new Insets(0, 0, 0, 30);
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(lblPieDePagina, gbc);
        
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        frame.add(btnInfo, gbc);
    }
    
}
