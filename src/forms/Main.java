
package forms;

import propiedades.InformacionGenerales;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Emilio Sánchez
 */
public class Main {


    public static void main(String[] args) {
        InformacionGenerales informacion = new InformacionGenerales();
        FormPrincipal principal = new FormPrincipal(informacion);
        
        principal.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}
