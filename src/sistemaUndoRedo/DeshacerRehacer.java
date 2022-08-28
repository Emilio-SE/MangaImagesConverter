package sistemaUndoRedo;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class DeshacerRehacer extends EstadosColas{

    public void deshacerJList(DefaultListModel modelo){
        
        if(!getPilaAccionesPrevias().isEmpty()){

            try{
                
                InstanciasJList accionAnterior = getPilaAccionesPrevias().pop();
                getPilaAccionesSiguientes().add(accionAnterior);
            
                switch ( accionAnterior.getAccion() ) {
                    case "agregar":
                        volverEliminarElementosJList(modelo, accionAnterior, getPilaAccionesPrevias(), getPilaAccionesSiguientes());
                        break;
                    case "eliminar":
                        volverAgregarElementosJList(modelo, accionAnterior, getPilaAccionesPrevias(), getPilaAccionesSiguientes(), false);
                        break;
                    case "sube":
                        reintercambioElementosJList(modelo, accionAnterior, getPilaAccionesPrevias(), getPilaAccionesSiguientes(), "D");
                        break;
                    case "baja":
                        reintercambioElementosJList(modelo, accionAnterior, getPilaAccionesPrevias(), getPilaAccionesSiguientes(), "D");
                        break;
                    case "limpiar":
                        volverAgregarElementosJList(modelo, accionAnterior, getPilaAccionesPrevias(), getPilaAccionesSiguientes(), true);
                        break;
                    default:
                        throw new AssertionError();
                }
                
            }catch(Exception e){
                
                if(!getPilaAccionesPrevias().isEmpty()){
                    getPilaAccionesPrevias().clear();
                }
                if(!getPilaAccionesSiguientes().isEmpty()){
                    getPilaAccionesSiguientes().clear();
                }
                
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el proceso Undo/Redo. Las pilas se han limpiado.\n\n" + e.getMessage(), "Error en el sistema Undo/Redo", JOptionPane.ERROR_MESSAGE);
                
            }
            
        }
        
    }
    
    public void rehacerJList(DefaultListModel modelo){
        
        if(!getPilaAccionesSiguientes().isEmpty()){
            
            try{
                
                InstanciasJList accionSiguiente = getPilaAccionesSiguientes().pop();
                getPilaAccionesPrevias().add(accionSiguiente);
            
                switch ( accionSiguiente.getAccion() ) {
                    case "agregar":
                        volverAgregarElementosJList(modelo, accionSiguiente, getPilaAccionesSiguientes(), getPilaAccionesPrevias(), false);
                        break;
                    case "eliminar":
                        volverEliminarElementosJList(modelo, accionSiguiente, getPilaAccionesSiguientes(), getPilaAccionesPrevias());
                        break;
                    case "sube":
                        reintercambioElementosJList(modelo, accionSiguiente, getPilaAccionesSiguientes(), getPilaAccionesPrevias(), "R");
                        break;
                    case "baja":    
                        reintercambioElementosJList(modelo, accionSiguiente, getPilaAccionesSiguientes(), getPilaAccionesPrevias(), "R");
                        break;
                    case "limpiar":
                        volverEliminarElementosJList(modelo, accionSiguiente, getPilaAccionesSiguientes(), getPilaAccionesPrevias());
                        break;
                    default:
                        throw new AssertionError();
                }
                
            }catch(Exception e){
                if(!getPilaAccionesPrevias().isEmpty()){
                    getPilaAccionesPrevias().clear();
                }
                if(!getPilaAccionesSiguientes().isEmpty()){
                    getPilaAccionesSiguientes().clear();
                }
                
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error en el proceso Undo/Redo. Las pilas se han limpiado.\n\n" + e.getMessage(), "Error en el sistema Undo/Redo", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }
    
}
