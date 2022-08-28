package acciones;

//Importaciones iText7
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import java.io.FileNotFoundException;
//Importaciones Clases
import propiedades.Metadatos;
import propiedades.Constantes;
//Otras Importaciones
import java.io.IOException;
import java.util.Observable;
import java.util.Queue;
import javax.swing.JOptionPane;

public class GenerarPDF extends Observable implements Runnable{
    //Declaración de Clases
    Metadatos informacion;
    Constantes constantes;
    //Otras Declaraciones
    Queue <String> direccionesImagenes;
    //Variables Globales
    private static final PdfNumber VERTICAL = new PdfNumber(0);
    private static final PdfNumber HORIZONTAL = new PdfNumber(90);
    private boolean cancelar = false;
    private boolean estaCreandoPDF = false;
    
    public GenerarPDF(){
        this.informacion = Metadatos.getInstancia();
        this.constantes = new Constantes();
    }
    
    @Override
    public void run() {
        try {
            crearPDF();
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error: " + ex.getMessage());
        }
    }

    private int crearPDF() throws IOException {
        
        OrientacionPaginaEvento orientacion = new OrientacionPaginaEvento();
        estaCreandoPDF = true;
        Document documento = colocarMetadatos(orientacion);
        
        int cantidadImagenes = direccionesImagenes.size();

        cantidadImagenes = noImagenesAgregadas(cantidadImagenes); // -----------Cola sin imagenes-----------
        
        cantidadImagenes = colocarImagenesEnPDF(documento, cantidadImagenes, orientacion);

        documento.close();
        estaCreandoPDF = false;
        
        JOptionPane.showMessageDialog(null, cantidadImagenes + " imagenes se han convertido al archivo PDF :D" + "\nEl documento ha sido generado en: " + informacion.getRutaGuardarDocumento());
        
        return cantidadImagenes;
    }
    
    private Document colocarMetadatos (OrientacionPaginaEvento orientacion) throws FileNotFoundException{
        PdfWriter titulo = new PdfWriter(informacion.getRutaGuardarDocumento() + informacion.getTituloPDF() + ".pdf");
        PdfDocument pdf = new PdfDocument(titulo);
        
        if(informacion.getTipoHoja() == 1){
            pdf.setDefaultPageSize(PageSize.A4);
        }else{
            pdf.setDefaultPageSize(PageSize.LETTER);
        }
        
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, orientacion);
                
        float[] margenes = informacion.getMargenesFloat();
        
        Document documento = new Document(pdf);
        
        documento.setTopMargin( margenes[constantes.MARGEN_SUPERIOR] );
        documento.setRightMargin( margenes[constantes.MARGEN_DERECHO] );
        documento.setBottomMargin( margenes[constantes.MARGEN_INFERIOR] );
        documento.setLeftMargin( margenes[constantes.MARGEN_IZQUIERDO] );
        
        PdfDocumentInfo metadatos = pdf.getDocumentInfo();
        
        metadatos.setTitle(informacion.getTituloPDF());
        metadatos.setAuthor(informacion.getAutorPDF());
        metadatos.setCreator("Manga Images Converter");
        return documento;
    }
    
    private int noImagenesAgregadas(int cantidadImagenes){

        if(cantidadImagenes == 0){
            direccionesImagenes.add(System.getProperty("user.dir") + "\\src\\Multimedia\\ERROR-NO-IMAGE.jpg");
            cantidadImagenes = 1;
        }
        
        return cantidadImagenes;
        
    }
    
    private int colocarImagenesEnPDF(Document documento, int cantidadImagenes, OrientacionPaginaEvento orientacion){
        float largoHoja = 0;
        float altoHoja = 0;
        int erroresImagenes = 0, imagenesAgregadas = 0;
        int[] datos = new int[3];
        
        for (int numImagen = 0; numImagen < cantidadImagenes; numImagen++) {
            //El catch espera por si se ha pasado algún archivo que no sea imagen y ocasiona un error.
            try{
                
                Image imagen = new Image(ImageDataFactory.create( direccionesImagenes.remove() ));
            
                if(informacion.getTipoHoja() == constantes.TAMANIO_A4){
                    largoHoja = PageSize.A4.getWidth() - documento.getLeftMargin() - documento.getRightMargin();
                    altoHoja = PageSize.A4.getHeight() - documento.getTopMargin() - documento.getBottomMargin();
                }else if(informacion.getTipoHoja() == constantes.TAMANIO_CARTA){
                    largoHoja = PageSize.LETTER.getWidth() - documento.getLeftMargin() - documento.getRightMargin();
                    altoHoja = PageSize.LETTER.getHeight() - documento.getTopMargin() - documento.getBottomMargin();
                }


                if(imagen.getImageWidth() < imagen.getImageHeight()){
                    orientacion.setOrientation(VERTICAL);
                    imagen.setHeight(altoHoja);
                    imagen.setWidth(largoHoja);
                    documento.add(imagen);
                }else{
                    orientacion.setOrientation(HORIZONTAL);
                    imagen.setRotationAngle(Math.toRadians(90));

                    imagen.setHeight(largoHoja);
                    imagen.setWidth(altoHoja);
                    documento.add(imagen);
                }
                
                imagenesAgregadas = numImagen + 1;
                
                datos[0] = imagenesAgregadas;
                datos[1] = cantidadImagenes;
                
                
                if(cancelar){
                    datos[2] = 1;
                    setChanged();
                    notifyObservers(datos);
                    break;
                }else{
                    datos[2] = 0;
                    setChanged();
                    notifyObservers(datos);
                }
                
            }catch(Exception e){
                erroresImagenes++;
                System.out.println(e);
            }
            
        }
        
        if(erroresImagenes > 0){
            JOptionPane.showMessageDialog(null, erroresImagenes + " imagenes no han podido ser agregadas.", "Error al cargar imagenes", JOptionPane.WARNING_MESSAGE);
        }
        
        cancelar=false;
        return imagenesAgregadas -= erroresImagenes;
    }

    private static class OrientacionPaginaEvento implements IEventHandler {
        private PdfNumber orientacion = VERTICAL;

        public void setOrientation(PdfNumber orientacion) {
            this.orientacion = orientacion;
        }

        @Override
        public void handleEvent(Event eventoActual) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) eventoActual;
            docEvent.getPage().put(PdfName.Rotate, orientacion);
        }
    }
    
    public void setDireccionesImagenes(Queue <String> direccionesImagenes){
        this.direccionesImagenes = direccionesImagenes;
    }
    
    public void cancelarEjecucion(){
        this.cancelar = true;
    }
    
}
