package diseno;

import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.ListModel;

public class RedimensionamientoJList<E> extends JList<E> {
    
    private Dimension visibilidadDelPanel;

        public RedimensionamientoJList(ListModel<E> modelo, Dimension visibilidadDelPanel) {
            super(modelo);
            this.visibilidadDelPanel = visibilidadDelPanel;
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            if (visibilidadDelPanel != null) {
                return visibilidadDelPanel;
            }
            return super.getPreferredScrollableViewportSize();
        }
    
}
