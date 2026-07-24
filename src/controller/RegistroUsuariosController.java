package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import model.UsuarioDao;
import view.RegistroUsuariosInterfaz;

public class RegistroUsuariosController implements ActionListener {

    private RegistroUsuariosInterfaz rI;
    private UsuarioDao usuarioDao;

    private static final int ID_ROL_PACIENTE = 5;

    public RegistroUsuariosController(RegistroUsuariosInterfaz rI) {
        this.rI = rI;
        this.usuarioDao = new UsuarioDao();

        this.rI.btnRegistrarse.addActionListener(this);
        this.rI.btnVolverA.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rI.btnRegistrarse) {
            registrar();
            return;
        }

        if (e.getSource() == rI.btnVolverA) {
            volverALogin();
            return;
        }
    }

    private void registrar() {
        int idTipoIdentificacion = rI.campoTipoId.getSelectedIndex();
        String numeroIdentificacion = rI.campoNumeroID.getText().trim();
        String primerNombre = rI.campoPrimerNombre.getText().trim();
        String primerApellido = rI.campoPrimerApellido.getText().trim();
        String sexoBiologico = String.valueOf(rI.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = rI.datePickerNacimiento.getDate();
        String correo = rI.campoCorreo.getText().trim();
        String telefono = rI.campoTelefono.getText().trim();
        String contrasena = new String(rI.campoContraseña.getPassword());
        String grupoSisben = String.valueOf(rI.campoSisben.getSelectedItem());

        boolean validador = ((idTipoIdentificacion > -1)
                && (!numeroIdentificacion.isEmpty() && !primerNombre.isEmpty()
                && !primerApellido.isEmpty() && !sexoBiologico.isEmpty() && !correo.isEmpty()
                && !contrasena.isEmpty() && !grupoSisben.isEmpty())
                && fechaNacimiento != null);

        if (validador) {
            // validariamos que si tiene segundo nombre le asignamos si no le asignamos null
            String segundoNombre = rI.campoSegundoNombre.getText().trim();
            String segundoApellido = rI.campoSegundoApellido.getText().trim();
            byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);

            // validador2 reglas finas de formato (letras, edad vs documento, correo, telefono, contraseña)
            String mensaje2 = "";

            if (!MetodosPublicos.validarSoloLetras(primerNombre)) {
                mensaje2 += "El primer nombre solo debe contener letras\n";
            }
            if (!segundoNombre.isEmpty() && !MetodosPublicos.validarSoloLetras(segundoNombre)) {
                mensaje2 += "El segundo nombre solo debe contener letras\n";
            }
            if (!MetodosPublicos.validarSoloLetras(primerApellido)) {
                mensaje2 += "El primer apellido solo debe contener letras\n";
            }
            if (!segundoApellido.isEmpty() && !MetodosPublicos.validarSoloLetras(segundoApellido)) {
                mensaje2 += "El segundo apellido solo debe contener letras\n";
            }
            if (!MetodosPublicos.validarNumero(numeroIdentificacion)
                    || !MetodosPublicos.validarTamano(numeroIdentificacion, 8, 10)) {
                mensaje2 += "El numero de identificacion debe tener entre 8 y 10 digitos\n";
            }

            if (edad > -1 && edad < 117) {
                // Edad vs tipo de documento
                final int TI_REGISTRO_CIVIL = 1;    // ajusta al indice real de tu combo
                final int TI_TARJETA_IDENTIDAD = 2; // ajusta al indice real de tu combo
                final int TI_CEDULA = 3;            // ajusta al indice real de tu combo

                if (edad < 7 && idTipoIdentificacion != TI_REGISTRO_CIVIL) {
                    mensaje2 += "Para menores de 7 años el documento debe ser Registro Civil\n";
                } else if (edad >= 7 && edad < 18 && idTipoIdentificacion != TI_TARJETA_IDENTIDAD) {
                    mensaje2 += "Para menores de edad (7 a 17 años) el documento debe ser Tarjeta de Identidad\n";
                } else if (idTipoIdentificacion != TI_CEDULA) {
                    mensaje2 += "Para mayores de edad el documento debe ser Cedula de Ciudadania\n";
                }
            } else {
                mensaje2 += "No se permiten personas con tal capacidad de edad\n";
            }

            if (!MetodosPublicos.validarFormatoCorreoGmail(correo)) {
                mensaje2 += "El correo debe tener un formato valido de Gmail (ejemplo@gmail.com)\n";
            }
            if (!telefono.isEmpty()) {
                if (!MetodosPublicos.validarNumero(telefono)) {
                    mensaje2 += "El numero de telefono debe contener solo numeros\n";
                } else if (telefono.length() != 10) {
                    mensaje2 += "El numero de telefono debe tener 10 digitos\n";
                } else if (!String.valueOf(telefono.charAt(0)).equals("3")) {
                    mensaje2 += "El numero de telefono debe ser colombiano\n";
                }
            }

            if (!MetodosPublicos.validarContrasena(contrasena)) {
                mensaje2 += "La contraseña debe incluir mayuscula, minuscula, numero y caracter especial ($ @ # % & * - _ ! ?)\n";
            }
            if (rI.campoSisben.getSelectedIndex() == 0) {
                mensaje2 += "Selecciona el grupo Sisben (o 'No aplica' si no estas registrado)\n";
            }

            boolean validador2 = mensaje2.isEmpty();

            if (!validador2) {
                mostrarError(mensaje2);
                return;
            }

            if (usuarioDao.validarCampoIdBs(numeroIdentificacion, "usuario", "numero_identificacion")) {
                mostrarError("Ya existe un usuario registrado con ese numero de identificacion");
                return;
            }

            if (usuarioDao.validarCampoIdBs(correo, "usuario", "correo_electronico")) {
                mostrarError("Ya existe un usuario registrado con ese Correo electronico");
                return;
            }

            String sisben = String.valueOf(rI.campoSisben.getSelectedItem());
            int idUsuarioGenerado = usuarioDao.registrarUsuario(
                    ID_ROL_PACIENTE,
                    idTipoIdentificacion,
                    numeroIdentificacion,
                    primerNombre,
                    segundoNombre,
                    primerApellido,
                    segundoApellido,
                    correo,
                    contrasena,
                    fechaNacimiento,
                    sexoBiologico,
                    telefono,
                    edad,
                    sisben
            );

            if (idUsuarioGenerado != -1) {
                JOptionPane.showMessageDialog(rI, "Cuenta creada correctamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                volverALogin();
            } else {
                mostrarError("No se pudo completar el registro. Intenta de nuevo");
            }

        } else {
            String mensaje = "";
            if (idTipoIdentificacion < 0) {
                mensaje += "Debe seleccionar un tipo de documento\n";
            }
            if (numeroIdentificacion.isEmpty()) {
                mensaje += "El campo numero de identificacion esta vacio\n";
            }
            if (primerNombre.isEmpty()) {
                mensaje += "El campo primer nombre esta vacio\n";
            }
            if (primerApellido.isEmpty()) {
                mensaje += "El campo primer apellido esta vacio\n";
            }
            if (sexoBiologico.isEmpty() || sexoBiologico.equals("null")) {
                mensaje += "Debe seleccionar el sexo biologico\n";
            }
            if (correo.isEmpty()) {
                mensaje += "El campo correo esta vacio\n";
            }
            if (contrasena.isEmpty()) {
                mensaje += "El campo contraseña esta vacio\n";
            }
            if (grupoSisben.isEmpty() || grupoSisben.equals("null")) {
                mensaje += "Debe seleccionar el grupo Sisben\n";
            }
            if (fechaNacimiento == null) {
                mensaje += "Debe seleccionar la fecha de nacimiento\n";
            }
            mostrarError(mensaje);
        }
    }

    private void volverALogin() {
        this.rI.dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(rI, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }
}
