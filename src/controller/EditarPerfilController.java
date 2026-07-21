package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import model.UsuarioDao;
import view.EditarPerfilInterfaz;

public class EditarPerfilController implements ActionListener {

    private static final String MENSAJE_EXITO = "Se cambio correctamente el ";
    private static final String MENSAJE_ERROR = "Se ha producido un error vuelve a intentar mas tarde";
    private final EditarPerfilInterfaz edI;
    private String nombre, correo, telefono;
    private final int id;
    private boolean estado;
    private final UsuarioDao usuDao;

    private void agregarListenerBotones() {
        this.edI.editar.addActionListener(this);
        this.edI.guardar.addActionListener(this);
        this.edI.volver.addActionListener(this);
    }

    private void inicializador() {
        nombre = edI.fielNombre.getText();
        correo = edI.fieldCorreo.getText();
        telefono = edI.fieldTelefono.getText();
    }

    public EditarPerfilController(EditarPerfilInterfaz edI, int id) {
        this.edI = edI;
        this.id = id;
        inicializador();
        agregarListenerBotones();
        this.estado = false;
        this.usuDao = new UsuarioDao();
    }

    public void estadoDeCosas(boolean miestado) {
        this.edI.fielNombre.setEditable(miestado);
        this.edI.fieldCorreo.setEditable(miestado);
        this.edI.fieldTelefono.setEditable(miestado);
        this.edI.guardar.setEnabled(miestado);
    }

    private void actualizarCampos(String campo, String valor, String campo1) {
        if (usuDao.actualizarCampoUsuario(id, campo, valor)) {
            JOptionPane.showMessageDialog(edI, MENSAJE_EXITO + campo1);
            estado = true;
        } else {
            JOptionPane.showMessageDialog(edI, MENSAJE_ERROR);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edI.editar) {
            if (edI.guardar.isEnabled()) {
                estadoDeCosas(false);
            } else {
                estadoDeCosas(true);
            }
        }
        if (e.getSource() == edI.guardar) {
            this.edI.guardar.setEnabled(false);
            String nombre = edI.fielNombre.getText(), correo = edI.fieldCorreo.getText(),
                    telefono = edI.fieldTelefono.getText();
            boolean validador = (!nombre.isEmpty() && !correo.isEmpty() && !telefono.isEmpty());
            if (validador) {
                boolean validador2 = nombre.equals(this.nombre)
                        && correo.equals(this.correo)
                        && telefono.equals(this.telefono);
                if (validador2) {
                    JOptionPane.showMessageDialog(edI, "Por lo menos uno de los campos deve de ser diferente");
                } else {
                    if (!nombre.equals(this.nombre)) {
                        if (!nombre.matches("\\p{L}+")) {
                            JOptionPane.showMessageDialog(edI, "El campo nombre no es valido");
                        } else if (nombre.length() > 2) {
                            actualizarCampos("primer_nombre", nombre, "nombre");
                        } else {
                            JOptionPane.showMessageDialog(edI, "El nombre deve de de ser mayor a 2 caracteres");
                        }
                    }
                    if (!correo.equals(this.correo)) {
                        if (MetodosPublicos.validarFormatoCorreoGmail(correo)) {
                            actualizarCampos("correo_electronico", correo, "Correo Elctronico");
                        } else {
                            JOptionPane.showMessageDialog(edI, "El formato del correo no es valido");
                        }
                    }
                    if (!telefono.equals(this.telefono)) {
                        if (!MetodosPublicos.validarNumero(telefono)) {
                            JOptionPane.showMessageDialog(edI, "Solo se permiten numeros en el campo telefono");
                        } else if (telefono.length() != 10) {
                            JOptionPane.showMessageDialog(edI, "El campo telefono solo tiene permitido 10 digitos");
                        } else if (String.valueOf(telefono.charAt(0)).equals("3")) {
                            JOptionPane.showMessageDialog(edI, "El numero no es colombiano");
                        } else {
                            actualizarCampos("numero_celular", telefono, "Telefono");
                        }
                    }
                    if (estado) {
                        this.edI.dispose();
                    }
                }
            } else {
                String mensaje = null;
                if (nombre.isEmpty()) {
                    mensaje = "El campo nombre no puede quedar vacio.";
                }
                if (correo.isEmpty()) {
                    mensaje = (mensaje == null) ? "El campo correo no puede quedar vacio."
                            : mensaje + "\nEl campo correo no puede quedar vacio.";
                }
                if (telefono.isEmpty()) {
                    mensaje = (mensaje == null) ? "El campo telefono no puede quedar vacio."
                            : mensaje + "\nEl campo telefono no puede quedar vacio.";
                }
                JOptionPane.showMessageDialog(edI, mensaje);
            }
            this.edI.guardar.setEnabled(true);
        }

        if (e.getSource() == edI.volver) {
            edI.dispose();
        }
    }
}
