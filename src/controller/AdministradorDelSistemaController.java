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
        byte n = (byte) JOptionPane.showConfirmDialog(pacienteI, "Estas seguro de esta accion");
        if (n == 0) {
            int idUsuario = (int) adminSistem.mDefaultTableModel.getValueAt(fila, 0);
            if (adminSistem.getUsuario().getIdUsuario() != idUsuario) {
                boolean actualizado = usuarioDao.habilitarUsuario(idUsuario);
                if (actualizado) {
                    JOptionPane.showMessageDialog(adminSistem, "Usuario habilitado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    procesoBtnRol();
                } else {
                    JOptionPane.showMessageDialog(adminSistem, "El usuario ya se encuentra habilitado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(adminSistem, "No se puede inhabilitar a si mismo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(pacienteI, "No se completo la opcion habilitar usuario");
        }
    }

    private void deshabilitarUsuarioSeleccionado() {
        int fila = adminSistem.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(adminSistem, "Debe seleccionar al menos un usuario.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        byte n = (byte) JOptionPane.showConfirmDialog(pacienteI, "Estas seguro de esta accion");
        if (n == 0) {
            int idUsuario = (int) adminSistem.mDefaultTableModel.getValueAt(fila, 0);
            if (adminSistem.getUsuario().getIdUsuario() != idUsuario) {
                boolean actualizado = usuarioDao.deshabilitarUsuario(idUsuario);
                if (actualizado) {
                    JOptionPane.showMessageDialog(adminSistem, "Usuario deshabilitado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    procesoBtnRol();
                } else {
                    JOptionPane.showMessageDialog(adminSistem, "El usuario ya se encuentra deshabilitado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(adminSistem, "No se puede deshabilitar a si mismo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(pacienteI, "No se completo la opcion habilitar usuario");
        }
    }

    private void procesoBtnRol() {
        adminSistem.vistaUsuarios();
        adminSistem.cargarUsuarios(usuarioDao.listar());
        adminSistem.btnRol.setEnabled(false);
        adminSistem.habilitarBotonesMenu(adminSistem.btnRol);
    }

    private void procesoBtnHanilitar() {
        this.adminSistem.btnHabilitar.setEnabled(false);
        habilitarUsuarioSeleccionado();
        this.adminSistem.btnHabilitar.setEnabled(true);
    }

    protected void procesoBtnDesabilitar() {
        this.adminSistem.btnDesabilitar.setEnabled(false);
        deshabilitarUsuarioSeleccionado();
        this.adminSistem.btnDesabilitar.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        this.adminSistem = (AdministradorDelSistemaInterfaz) pacienteI;

        if (e.getSource() == adminSistem.btnRol) {
            procesoBtnRol();
            return;
        }
        if (e.getSource() == adminSistem.btnHabilitar) {
            procesoBtnHanilitar();
            return;
        }
        if (e.getSource() == adminSistem.btnDesabilitar) {
            procesoBtnDesabilitar();
            return;
        }
        if (e.getSource() == adminSistem.btnLimpiar) {
            adminSistem.mDefaultTableModel.setRowCount(0); //Solo vacía la vista no toca base
            return;                                        //de datos pero igual le hacemos la broma a la instru paula
        }
    }
}
