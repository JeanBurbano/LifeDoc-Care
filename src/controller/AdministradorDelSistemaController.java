package controller;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import model.UsuarioDao;
import view.AdministradorDelSistemaInterfaz;

public class AdministradorDelSistemaController extends PacienteController {

    AdministradorDelSistemaInterfaz adminSistem;
    private final UsuarioDao usuarioDao = new UsuarioDao();

    public AdministradorDelSistemaController(AdministradorDelSistemaInterfaz adminSistem) {
        super(adminSistem);
        adminSistem.btnRol.addActionListener(this);
        adminSistem.btnHabilitar.addActionListener(this);
        adminSistem.btnDesabilitar.addActionListener(this);
        adminSistem.btnLimpiar.addActionListener(this);
    }

    private void habilitarUsuarioSeleccionado() {
        int fila = adminSistem.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(adminSistem, "Debe seleccionar al menos un usuario.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idUsuario = (int) adminSistem.mDefaultTableModel.getValueAt(fila, 0);
        if (adminSistem.getUsuario().getIdUsuario() != idUsuario) {
            boolean actualizado = usuarioDao.habilitarUsuario(idUsuario);
            if (actualizado) {
                JOptionPane.showMessageDialog(adminSistem, "Usuario habilitado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(adminSistem, "El usuario ya se encuentra habilitado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(adminSistem, "No se puede inhabilitar a si mismo.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deshabilitarUsuarioSeleccionado() {
        int fila = adminSistem.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(adminSistem, "Debe seleccionar al menos un usuario.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idUsuario = (int) adminSistem.mDefaultTableModel.getValueAt(fila, 0);
        if (adminSistem.getUsuario().getIdUsuario() != idUsuario) {
            boolean actualizado = usuarioDao.deshabilitarUsuario(idUsuario);
            if (actualizado) {
                JOptionPane.showMessageDialog(adminSistem, "Usuario deshabilitado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(adminSistem, "El usuario ya se encuentra deshabilitado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(adminSistem, "No se puede deshabilitar a si mismo.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.adminSistem = (AdministradorDelSistemaInterfaz) pacienteI;

        if (e.getSource() == adminSistem.btnRol) {
            adminSistem.vistaUsuarios();
            adminSistem.cargarUsuarios(usuarioDao.listar());
            adminSistem.btnRol.setEnabled(false);
            adminSistem.habilitarBotonesMenu(adminSistem.btnRol);
        } else if (e.getSource() == adminSistem.btnHabilitar) {
            habilitarUsuarioSeleccionado();
        } else if (e.getSource() == adminSistem.btnDesabilitar) {
            deshabilitarUsuarioSeleccionado();
        } else if (e.getSource() == adminSistem.btnLimpiar) {
            adminSistem.mDefaultTableModel.setRowCount(0); //Solo vacía la vista no toca base de datos
        }
    }
}
