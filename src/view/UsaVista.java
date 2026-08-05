package view;

import java.time.LocalDate;
import model.MetodosPublicos;

public class UsaVista {

    public static void main(String[] args) {
        UsuarioListadoInterfaz util = new UsuarioListadoInterfaz();

        util.agregarTarjetaUsuario("fotosPerfil/fotoDefecto.png", "Médico", "Cedula de Ciudadania", "1020304050",
                "Camila", "Torres", "camila@gmail.com", LocalDate.now(), "Femenino",
                "3001234567", (byte) 17, "A1", true);

        util.agregarTarjetaUsuario("fotosPerfil/fotoDefecto.png", "Enfermero", "Cedula de Ciudadania", "1020304051",
                "Luis", "Gómez", "luis@gmail.com", LocalDate.now(), "Masculino",
                "3007654321", (byte) 30, "B2", true);

        util.agregarTarjetaUsuario("fotosPerfil/fotoDefecto.png", "Administrador", "Cedula de Ciudadania", "1020304052",
                "María", "Pérez", "maria@gmail.com", LocalDate.now(), "Femenino",
                "3009876543", (byte) 25, "C3", false);
        MetodosPublicos.abrirVentana(util);
    }

}