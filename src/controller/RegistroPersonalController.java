/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.Period;
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
import model.UsuarioDao;

import view.RegistroPersonalInterfaz;

/**
 *
 * @author lunaa
 */
public class RegistroPersonalController extends RegistroUsuariosController {

    private RegistroPersonalInterfaz rpI;
    private UsuarioDao usuarioDao;
    private MedicoDao medicoDao;
    private OperarioDao operarioDao;
    private RolDao rolDao;

    private List<Rol> roles = new ArrayList<>();

    public RegistroPersonalController(RegistroPersonalInterfaz rpI) {
        super(rpI); // Hereda los actionListeners del R.usuarios
        this.rpI = rpI;
        this.usuarioDao = new UsuarioDao();
        this.medicoDao = new MedicoDao();
        this.operarioDao = new OperarioDao();
        this.rolDao = new RolDao();

        cargarComboRoles();
    }


    private void cargarComboRoles() {
        rpI.campoRol.removeAllItems();
        List<Rol> listaRoles = rolDao.listar();
        for (Rol r : listaRoles) {
            rpI.campoRol.addItem(r.getNombreRol());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rpI.btnRegistrarse) {
            registrarPersonal();
        } else if (e.getSource() == rpI.btnVolverA) {
            super.actionPerformed(e);
        }
    }

    private void registrarPersonal() {
        // 1. Validar el campo único/nuevo del personal (Rol)
        if (rpI.campoRol.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(rpI, "Selecciona un rol válido para el personal", "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String rolSeleccionado = String.valueOf(rpI.campoRol.getSelectedItem());
        
        // Asignamos el ID de rol correspondiente (3 = Médico, 4 = Operario)
        int idRol = (rolSeleccionado.equalsIgnoreCase("Medico") || rolSeleccionado.equalsIgnoreCase("Médico")) ? 3 : 4;
        
        // 2. Para registrar el usuario base aprovechando la transacción de UsuarioDao
        int idTipoIdentificacion = rpI.campoTipoId.getSelectedIndex() + 3; // 'Cedula Ciudadania' en BD es 3
        String numeroIdentificacion = rpI.campoNumeroID.getText().trim();
        String primerNombre = rpI.campoPrimerNombre.getText().trim();
        String segundoNombre = rpI.campoSegundoNombre.getText().trim();
        String primerApellido = rpI.campoPrimerApellido.getText().trim();
        String segundoApellido = rpI.campoSegundoApellido.getText().trim();
        String sexoBiologico = String.valueOf(rpI.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = rpI.datePickerNacimiento.getDate();
        String correo = rpI.campoCorreo.getText().trim();
        String telefono = rpI.campoTelefono.getText().trim();
        String contrasena = new String(rpI.campoContraseña.getPassword());
        String sisben = String.valueOf(rpI.campoSisben.getSelectedItem());

        int edadCalculada = (fechaNacimiento != null) ? Period.between(fechaNacimiento, LocalDate.now()).getYears() : 0;

        // Registrar el usuario pasando idRol dinámico (3 o 4)
        int idUsuarioGenerado = usuarioDao.registrarUsuario(
                idRol,
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

        // 3. Vincular con la tabla específica (Medico u Operario) si el usuario fue creado exitosamente
        if (idUsuarioGenerado != -1) {
            int resultado = 0;

            if (idRol == 3) {
                Medico m = new Medico();
                m.setId_medico(idUsuarioGenerado);
                resultado = medicoDao.setAgregar(m);
            } else if (idRol == 4) {
                Operario op = new Operario();
                op.setId_usuario(idUsuarioGenerado);
                resultado = operarioDao.setAgregar(op);
            }

            if (resultado > 0) {
                JOptionPane.showMessageDialog(rpI, "Personal registrado exitosamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                this.rpI.dispose();
            } else {
                JOptionPane.showMessageDialog(rpI, "Error al guardar los datos específicos del personal", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rpI, "No se pudo completar el registro del personal. Verifique los datos introducidos.", "Error de registro", JOptionPane.WARNING_MESSAGE);
        }
    }
    

}
