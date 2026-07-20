package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import view.EditarPerfilInterfaz;

public class EditarPerfilController implements ActionListener {

    private EditarPerfilInterfaz edI;
    private String nombre, correo, telefono;
    private int id;
    private boolean estado;

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
    }

    public void estadoDeCosas(boolean miestado) {
        this.edI.fielNombre.setEditable(miestado);
        this.edI.fieldCorreo.setEditable(miestado);
        this.edI.fieldTelefono.setEditable(miestado);
        this.edI.guardar.setEnabled(miestado);
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
                        if () {
                        } else if (nombre.length() > 0) {
                            JOptionPane.showMessageDialog(edI, "se cambio el nombre correctamente");
                            this.estado = (estado == false) ? true : false;
                        } else {
                            JOptionPane.showMessageDialog(edI, "El nombre deve de de ser mayor a 2 caracteres");
                        }
                    }

                    if (!correo.equals(this.correo)) {
                        if (MetodosPublicos.validarFormatoCorreoGmail(correo)) {
                            JOptionPane.showMessageDialog(edI, "se cambio el correo correctamente");
                            this.estado = (estado == false) ? true : false;
                        } else {
                            JOptionPane.showMessageDialog(edI, "El formato no es valido");
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
                            JOptionPane.showMessageDialog(edI, "Se cambio el telefono correctamente");
                            this.estado = (estado == false) ? true : false;
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
