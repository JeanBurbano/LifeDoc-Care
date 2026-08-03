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
import model.Rol;
import model.RolDao;
import model.UsuarioDao;

import view.RegistroPersonalInterfaz;

public class RegistroPersonalController extends RegistroUsuariosController {

    private static final int ID_ROL_MEDICO = 3;
    private static final int ID_ROL_OPERARIO = 4;

    private RegistroPersonalInterfaz rpI;
    private UsuarioDao usuarioDao;
    private MedicoDao medicoDao;
    private OperarioDao operarioDao;
    private RolDao rolDao;

    private List<Rol> roles = new ArrayList<>();

    public RegistroPersonalController(RegistroPersonalInterfaz rpI) {
        super(rpI);
        this.rpI = rpI;
        this.usuarioDao = new UsuarioDao();
        this.medicoDao = new MedicoDao();
        this.operarioDao = new OperarioDao();
        this.rolDao = new RolDao();

        cargarComboRoles();
    }

    private void cargarComboRoles() {
        rpI.campoRol.removeAllItems();
        roles = rolDao.listar();
        roles.forEach(rol -> rpI.campoRol.addItem(rol.getNombreRol()));
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
        int idTipoIdentificacion = rpI.campoTipoId.getSelectedIndex() + 3;
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
        String rolSeleccionado = String.valueOf(rpI.campoRol.getSelectedItem());

        Validador validador = validarFormularioRegistro(idTipoIdentificacion, numeroIdentificacion,
                primerNombre, segundoNombre, primerApellido, segundoApellido, sexoBiologico,
                correo, telefono, contrasena, sisben, fechaNacimiento);

        validador.validar(rpI.campoRol.getSelectedIndex() == -1,
                "Debe seleccionar un rol valido para el personal\n");

        if (validador.tieneErrores()) {
            mostrarError(validador.obtenerErrores());
            return;
        }

        int idRol = calcularIdRol(rolSeleccionado);
        byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);

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
                edad,
                sisben);

        if (idUsuarioGenerado == -1) {
            mostrarError("No se pudo completar el registro del personal Verifique los datos introducidos");
            return;
        }

        if (!vincularConTablaEspecifica(idRol, idUsuarioGenerado)) {
            mostrarError("Error al guardar los datos especificos del personal");
            return;
        }

        JOptionPane.showMessageDialog(rpI, "Personal registrado exitosamente", "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE);
        this.rpI.dispose();
    }

    private int calcularIdRol(String rolSeleccionado) {
        boolean esMedico = rolSeleccionado.equalsIgnoreCase("Medico") || rolSeleccionado.equalsIgnoreCase("Medico");
        return esMedico ? ID_ROL_MEDICO : ID_ROL_OPERARIO;
    }

    private boolean vincularConTablaEspecifica(int idRol, int idUsuarioGenerado) {
        if (idRol == ID_ROL_MEDICO) {
            Medico m = new Medico();
            m.setId_medico(idUsuarioGenerado);
            return medicoDao.setAgregar(m) > 0;
        }
        if (idRol == ID_ROL_OPERARIO) {
            Operario op = new Operario();
            op.setId_usuario(idUsuarioGenerado);
            return operarioDao.setAgregar(op) > 0;
        }
        return false;
    }
    
}
