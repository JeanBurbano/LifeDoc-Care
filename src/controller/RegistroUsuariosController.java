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
        } else if (e.getSource() == rI.btnVolverA) {
            volverALogin();
        }
    }

    private void registrar() {
        int idTipoIdentificacion = rI.campoTipoId.getSelectedIndex();
        String numeroIdentificacion = rI.campoNumeroID.getText().trim();
        String primerNombre = rI.campoPrimerNombre.getText().trim();
        String segundoNombre = rI.campoSegundoNombre.getText().trim();
        String primerApellido = rI.campoPrimerApellido.getText().trim();
        String segundoApellido = rI.campoSegundoApellido.getText().trim();
        String sexoBiologico = String.valueOf(rI.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = rI.datePickerNacimiento.getDate();
        String correo = rI.campoCorreo.getText().trim();
        String telefono = rI.campoTelefono.getText().trim();
        String contrasena = new String(rI.campoContraseña.getPassword());

        if (idTipoIdentificacion == 0) {
            mostrarError("Selecciona el tipo de documento");
            return;
        }
        if (!MetodosPublicos.validarNumero(numeroIdentificacion)
                || !MetodosPublicos.validarTamano(numeroIdentificacion, 8, 10)) {
            mostrarError("Campo id debe contener 8 o 10 caracteres");
            return;
        }
        if (primerNombre.isEmpty()) {
            mostrarError("El primer nombre es obligatorio");
            return;
        }
        if (primerApellido.isEmpty()) {
            mostrarError("El primer apellido es obligatorio");
            return;
        }
        if (sexoBiologico.isEmpty()) {
            mostrarError("Selecciona el sexo biológico");
            return;
        }
        if (fechaNacimiento == null) {
            mostrarError("Selecciona la fecha de nacimiento");
            return;
        }
        int edadCalculada = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edadCalculada < 0 || edadCalculada > 120) {
            mostrarError("La fecha de nacimiento no es válida");
            return;
        }
        if (!MetodosPublicos.validarFormatoCorreoGmail(correo)) {
            mostrarError("El correo debe tener un formato válido de Gmail (ejemplo@gmail.com)");
            return;
        }
        if (!telefono.isEmpty() && (!MetodosPublicos.validarNumero(telefono) || telefono.length() != 10)) {
            mostrarError("El número de teléfono debe tener 10 dígitos");
            return;
        }
        if (!MetodosPublicos.validarContrasena(contrasena)) {
            mostrarError("La contraseña debe incluir mayúscula, minúscula, número y un carácter especial ($ @ # % & * - _ ! ?)");
            return;
        }
        if (rI.campoSisben.getSelectedIndex() == 0) {
            mostrarError("Selecciona el grupo Sisbén (o 'No aplica' si no estás registrado)");
            return;
        }
        String sisben = String.valueOf(rI.campoSisben.getSelectedItem());

        if (usuarioDao.validarCampoIdBs(numeroIdentificacion, "usuario", "numero_identificacion")) {
            mostrarError("Ya existe un usuario registrado con ese número de identificación");
            return;
        }

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
                (byte) edadCalculada,
                sisben
        );

        if (idUsuarioGenerado != -1) {
            JOptionPane.showMessageDialog(rI, "Cuenta creada correctamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            volverALogin();
        } else {
            mostrarError("No se pudo completar el registro. Intenta de nuevo");
        }
    }

    private void volverALogin() {
        this.rI.dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(rI, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }
}
