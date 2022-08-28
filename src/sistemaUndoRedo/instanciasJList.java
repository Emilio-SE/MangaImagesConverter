package sistemaUndoRedo;

public class InstanciasJList {
    
    private String accion;
    private String direccion;
    private int indice;
    private int elementosAfectados;
    
    public InstanciasJList(String accion, String direccion, int indice, int elementosAfectados){
        this.accion = accion;
        this.direccion = direccion;
        this.indice = indice;
        this.elementosAfectados = elementosAfectados;
    }
    
    public String getAccion(){
        return this.accion;
    }
    
    public String getDireccion(){
        return this.direccion;
    }
    
    public int getIndice(){
        return this.indice;
    }
    
    public int getElementosAfectados(){
        return this.elementosAfectados;
    }
    
}
