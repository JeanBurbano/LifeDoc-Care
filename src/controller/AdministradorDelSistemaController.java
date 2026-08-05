package controller;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.MetodosPublicos;
import model.UsuarioPublico;
import model.UsuarioPublicoDao;
import view.AdministradorDelSistemaInterfaz;
import view.UsuarioListadoInterfaz;

public class AdministradorDelSistemaController extends PacienteController {

    AdministradorDelSistemaInterfaz adminSistem;
    private final UsuarioPublicoDao usuarioDao = new UsuarioPublicoDao();

    public AdministradorDelSistemaController(AdministradorDelSistemaInterfaz adminSistem) {
        super(adminSistem);
        this.adminSistem.btnRol.addActionListener(this);
        this.adminSistem.btnHabilitar.addActionListener(this);
        this.adminSistem.btnDesabilitar.addActionListener(this);
        this.adminSistem.btnLimpiar.addActionListener(this);
        this.adminSistem.btnUsuarioTargeta.addActionListener(this);
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
                boolean actualizado = (usuarioDao.setHabilitar(idUsuario) > 0);
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
                boolean actualizado = (usuarioDao.setEliminar(idUsuario) > 0);
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
        adminSistem.cargarUsuarios(new UsuarioPublicoDao().listar());
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

    protected void procesoBtnUsuario() {
        Thread hiloVistaTargeta = new Thread(() -> {
            UsuarioListadoInterfaz util = new UsuarioListadoInterfaz("Usuarios Del sistema");
            List<UsuarioPublico> lista = new ArrayList<>(new UsuarioPublicoDao().listar());
            for (UsuarioPublico usu : lista) {
                util.agregarTarjetaUsuario(usu.getFotoPerfil(), usu.getRol(), usu.getTipoIdentificacion(), usu.getNumeroIdentificacion(),
                        usu.getPrimerNombre(), usu.getPrimerApellido(), usu.getCorreo(), usu.getFechaNacimiento(), usu.getSexoBiologico(),
                        usu.getNumeroCelular(), usu.getEdad(), usu.getSisben(), usu.isEstado());
            }
            MetodosPublicos.abrirVentanaDisPoseOnClose(util);
        });
        hiloVistaTargeta.start();
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
        if (e.getSource() == adminSistem.btnUsuarioTargeta) {
            procesoBtnUsuario();
        }
    }
}
