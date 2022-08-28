package sistemaUndoRedo;

import java.util.Stack;
import javax.swing.DefaultListModel;

public class EstadosColas {
    
    private Stack<InstanciasJList>pilaAccionesPrevias;
    private Stack<InstanciasJList>pilaAccionesSiguientes;
    
    public EstadosColas(){
        pilaAccionesPrevias = new Stack<InstanciasJList>();
        pilaAccionesSiguientes = new Stack<InstanciasJList>();
    }
    
    protected void volverAgregarElementosJList(DefaultListModel modelo, InstanciasJList accion, Stack<InstanciasJList> pilaAccionesA, Stack<InstanciasJList> pilaAccionesB, boolean esAddElement){
        
        if( esAddElement ){
            modelo.addElement( accion.getDireccion() ); 
        }else{
            modelo.add( accion.getIndice(), accion.getDireccion() );
        }
        
        int cantidadElementosModificados = accion.getElementosAfectados();
        
        for(int i = 0; i < (cantidadElementosModificados - 1); i++){
            accion = pilaAccionesA.pop();
            pilaAccionesB.add( accion );
            
            if( esAddElement ){
                modelo.addElement( accion.getDireccion() ); 
            }else{
                modelo.add( accion.getIndice(), accion.getDireccion() );
            }  
        }
        
    }
    
    protected void volverEliminarElementosJList(DefaultListModel modelo, InstanciasJList accion, Stack<InstanciasJList> pilaAccionesA, Stack<InstanciasJList> pilaAccionesB){
        modelo.remove( accion.getIndice() );

        int cantidadElementosModificados = accion.getElementosAfectados();
        
        for(int i = 0; i < (cantidadElementosModificados - 1); i++){
            accion = pilaAccionesA.pop();
            pilaAccionesB.add( accion );
            modelo.remove( accion.getIndice() );
        }
    }
    
    protected void reintercambioElementosJList(DefaultListModel modelo, InstanciasJList accion, Stack<InstanciasJList> pilaAccionesA, Stack<InstanciasJList> pilaAccionesB, String procedencia){
        InstanciasJList instanciaAnteriorAnterior = pilaAccionesA.pop();
        pilaAccionesB.add( instanciaAnteriorAnterior );

        int cantidadElementosModificados = accion.getElementosAfectados();

        if( procedencia.equals("D") ){
            cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), accion.getIndice(), instanciaAnteriorAnterior.getIndice());
        }else{
            cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), instanciaAnteriorAnterior.getIndice(), accion.getIndice());
        }

        for(int i = 0; i < (cantidadElementosModificados - 1); i++){

                accion = pilaAccionesA.pop();
                pilaAccionesB.add( accion );
                instanciaAnteriorAnterior = pilaAccionesA.pop();
                pilaAccionesB.add( instanciaAnteriorAnterior );
                
                if( procedencia.equals("D") ){
                    cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), accion.getIndice(), instanciaAnteriorAnterior.getIndice());
                }else{
                    cambioDePosicionElementosJList(modelo, accion.getDireccion(), instanciaAnteriorAnterior.getDireccion(), instanciaAnteriorAnterior.getIndice(), accion.getIndice());
                }
                
        }
    }
    
    protected void cambioDePosicionElementosJList(DefaultListModel modelo, String elementoA, String elementoB, int indiceA, int indiceB){
        modelo.setElementAt(elementoA, indiceB);
        modelo.setElementAt(elementoB, indiceA);
    }
    
    protected void agregarNuevaAccionAPila(String accion, String direccion, int indiceDireccion, int cantElementosAfectados){
        pilaAccionesPrevias.add( new InstanciasJList(accion, direccion, indiceDireccion, cantElementosAfectados) );
        
        if(!pilaAccionesSiguientes.isEmpty()){
            pilaAccionesSiguientes.clear();
        }
    }

    protected Stack<InstanciasJList> getPilaAccionesPrevias() {
        return pilaAccionesPrevias;
    }

    protected Stack<InstanciasJList> getPilaAccionesSiguientes() {
        return pilaAccionesSiguientes;
    }

    
}
