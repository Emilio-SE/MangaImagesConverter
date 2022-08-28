
package acciones;

import diseno.DisenioComponentes;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import propiedades.Metadatos;

public class AccionesMetadatos {
    
    private Metadatos informacion;
    private AccionesArchivos accionesArchivos;
    private AccionesConversiones conversiones;
    private DisenioComponentes disenio;
    
    public AccionesMetadatos(){
        informacion = Metadatos.getInstancia();
        accionesArchivos = new AccionesArchivos();
        conversiones = new AccionesConversiones();
        disenio = new DisenioComponentes();
    }
    
    public void actualizarMetadatos(JTextField tfTitulo, JTextField tfAutor, JTextField tfMargenes, JTextField tfPortada, JTextField tfRutaGuardarDoc, JButton btnActualizar, JRadioButton rbtnCm, JRadioButton rbtnPul){
        String tituloPDF = tfTitulo.getText();
        String autorPDF = tfAutor.getText();
        String margenes = tfMargenes.getText();
        String rutaPortada = tfPortada.getText();
        String rutaGuardarDocumento = tfRutaGuardarDoc.getText();

        if( !tituloPDF.equals("") && !informacion.getTituloPDF().equals( tituloPDF ) ){
            informacion.setTituloPDF( tituloPDF );
        }

        if(!autorPDF.equals("") && !informacion.getAutorPDF().equals( autorPDF ) ){
            informacion.setAutorPDF(autorPDF);
        }

        if( !informacion.getRutaPortada().equals(rutaPortada) ){
            if(rutaPortada.equals("") || accionesArchivos.existeArchivo( rutaPortada )){
                informacion.setRutaPortada( rutaPortada );
            }else{
                rutaPortada = "";
                informacion.setRutaPortada( rutaPortada );
            }
        }

        if( !margenes.equals("")){
            if(rbtnCm.isSelected()){
                informacion.setMargenesFloat( conversiones.cmApts(margenes) );
            }else if(rbtnPul.isSelected()){
                informacion.setMargenesFloat( conversiones.pulApts(margenes) );
            }else{
                informacion.setMargenesFloat( conversiones.margenesTxtAFloat(margenes) );
            }
        }
        
        if(!rutaGuardarDocumento.endsWith("\\") && !rutaGuardarDocumento.endsWith("/")){
                rutaGuardarDocumento += "\\";
        }

        if(accionesArchivos.existeArchivo( rutaGuardarDocumento )){
            informacion.setRutaGuardarDocumento( rutaGuardarDocumento );
        }
        
        disenio.desactivarBtnActualizarMeta(btnActualizar);
        
    }
}
