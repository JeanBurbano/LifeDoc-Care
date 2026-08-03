package view;

import java.time.LocalDate;
import javax.swing.ImageIcon;
import model.MetodosPublicos;

public class UsaVista {
    
    public static void main(String[] args) {
        ImageIcon fotoIcon = new ImageIcon("fotosPerfil/fotoDefecto.png");
        UsuarioListadoInterfaz util = new UsuarioListadoInterfaz("ddd");
        util.agregarTarjetaUsuario(fotoIcon, "Médico", "Cedula de Ciudadania", "1020304050",
        "Camila", "Torres", "camila@gmail.com", LocalDate.now(), "Femenino",
        "3001234567", (byte)17, "A1", true);
        MetodosPublicos.abrirVentana(util);
    }
}
