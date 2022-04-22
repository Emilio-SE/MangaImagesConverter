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
//Importaciones Clases
import propiedades.InformacionGenerales;
import propiedades.Constantes;
//Otras Importaciones
import java.io.IOException;
import java.util.Queue;

public class GenerarPDF{
    //Declaración de Clases
    InformacionGenerales informacion;
    Constantes constantes;
    //Otras Declaraciones
    Queue <String> direccionesImagenes;
    //Variables Globales
    public static final PdfNumber VERTICAL = new PdfNumber(0);
    public static final PdfNumber HORIZONTAL = new PdfNumber(90);
    
    public GenerarPDF(InformacionGenerales informacionGeneral, Queue <String> direccionesImagenes){
        this.informacion = informacionGeneral;
        this.constantes = new Constantes();
        this.direccionesImagenes = direccionesImagenes;
    }
    
    public int crearPDF() throws IOException {
         
        PdfWriter titulo = new PdfWriter(informacion.getRutaGuardarDocumento() + informacion.getTituloPDF() + ".pdf");
        PdfDocument pdf = new PdfDocument(titulo);
        
        OrientacionPaginaEvento eventHandler = new OrientacionPaginaEvento();
        
        // -----------Metadatos-----------
        if(informacion.getTipoHoja() == 1){
            pdf.setDefaultPageSize(PageSize.A4);
        }else{
            pdf.setDefaultPageSize(PageSize.LETTER);
        }
        
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, eventHandler);
        Document documento = new Document(pdf);
        
        float[] margenes = informacion.getMargenesFloat();
        
        documento.setTopMargin( margenes[constantes.MARGEN_SUPERIOR] );
        documento.setRightMargin( margenes[constantes.MARGEN_DERECHO] );
        documento.setBottomMargin( margenes[constantes.MARGEN_INFERIOR] );
        documento.setLeftMargin( margenes[constantes.MARGEN_IZQUIERDO] );
        
        PdfDocumentInfo metadatos = pdf.getDocumentInfo();
        
        metadatos.setTitle(informacion.getTituloPDF());
        metadatos.setAuthor(informacion.getAutorPDF());
        
        // -----------Cola sin imagenes-----------
        int cantidadImagenes = direccionesImagenes.size();
        int erroresImagenes = 0;
        if(cantidadImagenes == 0){
            direccionesImagenes.add(System.getProperty("user.dir") + "\\src\\Multimedia\\ERROR-NO-IMAGE.jpg");
            cantidadImagenes = 1;
        }
        
        // -----------Colocar imagenes en PDF-----------
        float largoHoja = 0;
        float altoHoja = 0;
        
        for (int i = 0; i < cantidadImagenes; i++) {
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
                    eventHandler.setOrientation(VERTICAL);
                    imagen.setHeight(altoHoja);
                    imagen.setWidth(largoHoja);
                    documento.add(imagen);
                }else{
                    eventHandler.setOrientation(HORIZONTAL);
                    imagen.setRotationAngle(Math.toRadians(90));

                    imagen.setHeight(largoHoja);
                    imagen.setWidth(altoHoja);
                    documento.add(imagen);
                }
            }catch(Exception e){
                erroresImagenes++;
                System.out.println(e);
            }
            
        }

        documento.close();
        cantidadImagenes -= erroresImagenes;
        return cantidadImagenes;
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
    
}
