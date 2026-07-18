package view;

import java.awt.Color;
import model.MetodosPublicos;

public class EditarPerfilInterfaz extends PacienteInterfaz {

    public EditarPerfilInterfaz(String nombreInterfaz) {
        super(nombreInterfaz);
        super.encabezado.setOpaque(true);
        super.encabezado.setBackground(COLOR_VERDE_ACENTO);
        super.cuerpo1.setOpaque(true);
        super.cuerpo1.setBackground(Color.RED);
        super.cuerpo2.setOpaque(true);
        super.cuerpo2.setBackground(Color.YELLOW);
        MetodosPublicos.refrescarVentana(encabezado);
        MetodosPublicos.refrescarVentana(cuerpo1);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }
}
