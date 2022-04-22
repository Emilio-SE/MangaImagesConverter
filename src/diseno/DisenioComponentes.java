package diseno;

import java.awt.Cursor;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class DisenioComponentes {
    
    public void disenioEtiquetas(JLabel lbl){
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        lbl.setFont( new Font( "Arial", Font.BOLD, 13 ) );
    }
    
    public void disenioTitulos(JLabel lbl){
        lbl.setFont( new Font( "Arial", Font.BOLD, 14 ) );
    }
    
    public void disenioBotones(JButton btn, String toolTip){
        btn.setBackground(new Color(242, 244, 244));
        btn.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(toolTip);
    }
    
    public void disenioCuadrosDeTexto(JTextField tf){
        tf.setPreferredSize(new Dimension(50, 25));
        tf.setBackground(new Color(253, 253, 253));
        tf.setFont(new Font( "Arial", Font.ROMAN_BASELINE, 13 ));
        tf.setBorder(BorderFactory.createLineBorder(new Color(160,160,160)));
    }
    
    public void disenioList(JList lst){
        lst.setPreferredSize(new Dimension(50, 270));
        lst.setBackground(new Color(253, 253, 253));
        lst.setBorder(BorderFactory.createLineBorder(new Color(160,160,160)));
        lst.setFont(new Font( "Arial", Font.BOLD, 12));
    }
    
    public void disenioRadioButton(JRadioButton rbtn){
        rbtn.setBackground(new Color(236, 246, 247));
        rbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rbtn.setFont(new Font( "Arial", Font.CENTER_BASELINE, 13 ));
    }
    
    public void activarBtnActualizarMeta(JButton btnActualizar){
        //btnActualizar.setBackground(new Color(224, 164, 179));
        btnActualizar.setBackground(new Color(229, 177, 190));
        btnActualizar.setText("Actualizar Metadatos");
        btnActualizar.setToolTipText("Clic para actualizar los metadatos");
        btnActualizar.setEnabled(true);
    }
    
    public void desactivarBtnActualizarMeta(JButton btnActualizar){
        btnActualizar.setBackground(new Color(220, 243, 195));
        btnActualizar.setText("Metadatos Actualizados");
        btnActualizar.setToolTipText("¡Los metadatos están actualizados!");
        btnActualizar.setEnabled(false);
    }
    
    public void actualizandoBtnActualizarMeta(JButton btnActualizar){
        btnActualizar.setBackground(new Color(191, 229, 177));
    }
    
}
