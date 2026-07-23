/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Medico;
import model.MedicoDao;
import model.MetodosPublicos;
import model.Operario;
import model.OperarioDao;
import model.PersonalCentro;
import model.PersonalCentroDao;
import model.Rol;
import model.RolDao;

import view.RegistroPersonalInterfaz;

/**
 *
 * @author lunaa
 */
public class RegistroPersonalController extends RegistroUsuariosController {

    RegistroPersonalInterfaz vista;
    RolDao rolDao;
    MedicoDao medicoDao;
    OperarioDao operarioDao;
    PersonalCentroDao personalDao;

    private List<Rol> roles = new ArrayList<>();

    public RegistroPersonalController(RegistroPersonalInterfaz vista) {
        super(vista);

        if (this.vista != null) {
        this.vista.btnRegistrarse.removeActionListener(this);
        this.vista.btnVolverA.removeActionListener(this);

        this.vista.btnVolverA.addActionListener(this);
        this.vista.btnRegistrarse.addActionListener(this);
        
        cargarRoles();
    }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrarse) {
            registrar();
        } else if (e.getSource() == vista.btnVolverA) {
            volverALogin();
        }
    }

    private void cargarRoles() {
        roles = new RolDao().listar();
        vista.campoRol.removeAllItems();
        for (Rol r : roles) {
            vista.campoRol.addItem(r.getNombreRol());
        }
    }

    private void registrar() {
        int idTipoIdentificacion = vista.campoTipoId.getSelectedIndex();
        String numeroIdentificacion = vista.campoNumeroID.getText().trim();
        String primerNombre = vista.campoPrimerNombre.getText().trim();
        String segundoNombre = vista.campoSegundoNombre.getText().trim();
        String primerApellido = vista.campoPrimerApellido.getText().trim();
        String segundoApellido = vista.campoSegundoApellido.getText().trim();
        String sexoBiologico = String.valueOf(vista.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = vista.datePickerNacimiento.getDate();
        String correo = vista.campoCorreo.getText().trim();
        String telefono = vista.campoTelefono.getText().trim();
        String contrasena = new String(vista.campoContraseña.getPassword());

        if (!validarCampos(idTipoIdentificacion, numeroIdentificacion, primerNombre,
                primerApellido, sexoBiologico, fechaNacimiento, correo, telefono, contrasena)) {
            return;
        }

        if (personalDao.validarCampoIdBs(numeroIdentificacion, "usuario", "numero_identificacion")) {
            JOptionPane.showMessageDialog(null, "El número de identificación ingresado ya está registrado en el sistema");
            return;
        }

        PersonalCentro personal = new PersonalCentro();
        personal.setIdTipoIdentificacion(idTipoIdentificacion);
        personal.setNumeroIdentificacion(numeroIdentificacion);
        personal.setPrimerNombre(primerNombre);
        personal.setSegundoNombre(segundoNombre);
        personal.setPrimerApellido(primerApellido);
        personal.setSegundoApellido(segundoApellido);
        personal.setCorreoElectronico(correo);
        personal.setContrasena(contrasena);
        personal.setFechaNacimiento(fechaNacimiento);
        personal.setSexoBiologico(sexoBiologico);
        personal.setNumeroCelular(telefono);
        personal.setSisben(" ");
        personal.setEstado(true);

        if (personal.getEdad() < 18) {
            JOptionPane.showMessageDialog(null, " El personal del centro médico debe ser mayor de edad (18+ años)");
            return;
        }

        int indiceSeleccionado = vista.campoRol.getSelectedIndex() - 1; // Restamos 1 por el ítem "Seleccione un rol..."
        Rol rolSeleccionado = roles.get(indiceSeleccionado);
        personal.setIdRol(rolSeleccionado.getIdRol());

        int idUsuarioGenerado = personalDao.setAgregar(personal);

        if (idUsuarioGenerado != -1) {
            boolean registroEspecificoExitoso = false;
            String nombreRol = rolSeleccionado.getNombreRol();

            // Verifica qué rol se seleccionó para vincular la tabla correspondiente
            if (nombreRol.equalsIgnoreCase("Medico") || nombreRol.equalsIgnoreCase("Médico")) {
                Medico medico = new Medico();
                medico.setIdUsuario(idUsuarioGenerado); // Vincula la FK del usuario recién creado
                registroEspecificoExitoso = (medicoDao.setAgregar(medico) > 0);

            } else if (nombreRol.equalsIgnoreCase("Operario")) {
                Operario operario = new Operario();
                operario.setIdUsuario(idUsuarioGenerado);
                registroEspecificoExitoso = (operarioDao.setAgregar(operario) > 0);

            } else {
                // En caso de que exista un rol que no requiera tabla hija específica (ej. Administrador)
                registroEspecificoExitoso = true;
            }

            // 6. CONFIRMACIÓN AL USUARIO
            if (registroEspecificoExitoso) {
                JOptionPane.showMessageDialog(vista,
                        "El " + nombreRol + " ha sido registrado exitosamente en el sistema.",
                        "Registro Exitoso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                volverALogin();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar registrar el usuario en la base de datos.");
        }
    }

    private void volverALogin() {
        vista.dispose();
    }
    
    private boolean validarCampos(
            int idTipoIdentificacion, 
            String numeroIdentificacion, 
            String primerNombre, 
            String primerApellido, 
            String sexoBiologico, 
            LocalDate fechaNacimiento, 
            String correo, 
            String telefono, 
            String contrasena) {

        if (idTipoIdentificacion <= 0) {
            JOptionPane.showMessageDialog(null, "Selecciona el tipo de documento");
            return false;
        }
        if (!MetodosPublicos.validarNumero(numeroIdentificacion)
                || !MetodosPublicos.validarTamano(numeroIdentificacion, 8, 10)) {
            JOptionPane.showMessageDialog(null, "Campo id debe contener 8 o 10 caracteres");
            return false;
        }
        if (primerNombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El primer nombre es obligatorio");
            return false;
        }
        if (primerApellido.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El primer apellido es obligatorio");
            return false;
        }
        if (sexoBiologico.isEmpty() || vista.comboSexo.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(null, "Selecciona el sexo biológico");
            return false;
        }
        if (fechaNacimiento == null) {
            JOptionPane.showMessageDialog(null, "Selecciona la fecha de nacimiento");
            return false;
        }
        if (!MetodosPublicos.validarFormatoCorreoGmail(correo)) {
            JOptionPane.showMessageDialog(null, "El correo debe tener un formato válido de Gmail (ejemplo@gmail.com)");
            return false;
        }
        if (!telefono.isEmpty() && (!MetodosPublicos.validarNumero(telefono) || telefono.length() != 10)) {
            JOptionPane.showMessageDialog(null, "El número de teléfono debe tener 10 dígitos");
            return false;
        }
        if (!MetodosPublicos.validarContrasena(contrasena)) {
            JOptionPane.showMessageDialog(null, "La contraseña debe incluir mayúscula, minúscula, número y un carácter especial ($ @ # % & * - _ ! ?)");
            return false;
        }

        return true;
    }

}
