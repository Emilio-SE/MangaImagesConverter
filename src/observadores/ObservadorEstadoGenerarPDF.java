package observadores;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;

public class ObservadorEstadoGenerarPDF implements Observer{
    
    private JButton btnGenerarPDF;
    
    public ObservadorEstadoGenerarPDF(JButton btnGenerarPDF){
        this.btnGenerarPDF = btnGenerarPDF;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        int[] datos = (int[]) arg;
        
        haTerminado(datos[0], datos[1]);

    }
    
    private void haTerminado(int elementosConvertidos, int cantidadElementos){
        if(elementosConvertidos < cantidadElementos){
            btnGenerarPDF.setEnabled(false);
            btnGenerarPDF.setText("Se han convertido " + elementosConvertidos + " de " + cantidadElementos + " imágenes.");
            
        }else if(elementosConvertidos == cantidadElementos){
            btnGenerarPDF.setEnabled(true);
            btnGenerarPDF.setText("Generar PDF");
        }
    }
    
}
