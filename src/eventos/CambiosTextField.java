package eventos;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import diseno.DisenioComponentes;

public class CambiosTextField implements DocumentListener{
    
    private JButton btnActualizar;
    private DisenioComponentes disenio;
    
    public CambiosTextField(JButton btnActualizar){
        this.btnActualizar = btnActualizar;
        this.disenio = new DisenioComponentes();
    }
    
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
