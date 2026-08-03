package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import model.UsuarioDao;
import view.EditarPerfilInterfaz;

public class EditarPerfilController implements ActionListener {

    //Variables static final 
    private static final String MENSAJE_EXITO = "Se cambio correctamente el ";
    private static final String MENSAJE_ERROR = "Se ha producido un error vuelve a intentar mas tarde";
    //Variables de instancia
    private final EditarPerfilInterfaz edI;
    private String nombre, correo, telefono;
    private final int id;
    private boolean estado;
    private final UsuarioDao usuDao;
    private String nombreModi;

    private void agregarListenerBotones() {
        this.edI.editar.addActionListener(this);
        this.edI.guardar.addActionListener(this);
        this.edI.volver.addActionListener(this);
    }

    private void inicializador() {
        nombre = edI.fielNombre.getText();
        correo = edI.fieldCorreo.getText();
        telefono = edI.fieldTelefono.getText();
        MetodosPublicos.soloLetras(edI.fielNombre, 30);
        MetodosPublicos.soloNumeros(edI.fieldTelefono, 10);
    }

    public EditarPerfilController(EditarPerfilInterfaz edI, int id,String nombreModi) {
        this.edI = edI;
        this.id = id;
        inicializador();
        agregarListenerBotones();
        this.estado = false;
        this.usuDao = new UsuarioDao();
        this.nombreModi=nombreModi;
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
            nombreModi = nombre;
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
            return;
        }
        if (e.getSource() == edI.guardar) {
            this.edI.guardar.setEnabled(false);
            String nombre = edI.fielNombre.getText().toLowerCase(), correo = edI.fieldCorreo.getText().trim(),
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
                        if (nombre.length() > 2) {
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
                        if (telefono.length() < 10) {
                            JOptionPane.showMessageDialog(edI, "El campo telefono solo tiene permitido 10 digitos");
                        } else if (telefono.charAt(0) != '3') {
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
                Validador vali = new Validador();
                vali.validar(nombre.isEmpty(), "El campo nombre no puede quedar vacio.\n");
                vali.validar(correo.isEmpty(), "El campo correo no puede quedar vacio.\n");
                vali.validar(telefono.isEmpty(), "El campo telefono no puede quedar vacio.");
                JOptionPane.showMessageDialog(edI, vali.obtenerErrores());
            }
            this.edI.guardar.setEnabled(true);
            return;
        }

        if (e.getSource() == edI.volver) {
            edI.dispose();
            return;
        }
    }
}
