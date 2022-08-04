package observadores;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;

public class ObservadorEstadoGenerarPDF implements Observer{
    
    private JButton btnGenerarPDF;
    private JButton btnCancelar;
    
    public ObservadorEstadoGenerarPDF(JButton btnGenerarPDF, JButton btnCancelar){
        this.btnGenerarPDF = btnGenerarPDF;
        this.btnCancelar = btnCancelar;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        int[] datos = (int[]) arg;
        
        haTerminado(datos[0], datos[1], datos[2]);

    }
    
    private void haTerminado(int elementosConvertidos, int cantidadElementos, int haFinalizado){
        if(elementosConvertidos < cantidadElementos && haFinalizado == 0){
            btnGenerarPDF.setEnabled(false);
            btnCancelar.setEnabled(true);
            btnGenerarPDF.setText("Se han convertido " + elementosConvertidos + " de " + cantidadElementos + " imágenes.");
            
        }else if(elementosConvertidos == cantidadElementos || haFinalizado == 1){
            btnGenerarPDF.setEnabled(true);
            btnCancelar.setEnabled(false);
            btnGenerarPDF.setText("Generar PDF");
        }
    }
    
}
