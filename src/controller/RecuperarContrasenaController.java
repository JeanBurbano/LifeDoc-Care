package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.EnvioCorreos;
import model.MetodosPublicos;
import model.UsuarioDao;
import view.RecuperacionContrasenaInterfaz;

public class RecuperarContrasenaController implements ActionListener {

    RecuperacionContrasenaInterfaz p;
    private byte validador;
    private EnvioCorreos envioCorreos;
    private UsuarioDao usuDao;

    private void agregarActionListener() {
        this.p.btnCorreo.addActionListener(this);
        this.p.btnSms.addActionListener(this);
        this.p.btnContinuar.addActionListener(this);
        this.p.flecha.addActionListener(this);
    }

    public RecuperarContrasenaController(RecuperacionContrasenaInterfaz p) {
        this.usuDao = new UsuarioDao();
        this.validador = 0;
        this.p = p;
        this.p.vistaMetodoDerecuperacion();
        agregarActionListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.p.btnCorreo) {
            this.p.vistaRecuperacion("Correo");
            this.validador = 1;
        }
        if (e.getSource() == this.p.btnSms) {
            this.p.vistaRecuperacion("SMS");
            this.validador = 2;
        }
        if (e.getSource() == this.p.btnContinuar) {
            if (validador == 1) {
                String correo = p.field.getText().trim();
                if (correo.isEmpty()) {
                    JOptionPane.showMessageDialog(p, "El campo correo electronico es obligatorio");
                } else if (!MetodosPublicos.validarFormatoCorreoGmail(correo)) {
                    JOptionPane.showMessageDialog(p, "El correo debe tener el formato .....@gmail.com");
                } else if (!usuDao.validarCampoIdBs(correo, "usuario", "correo_electronico")) {
                    JOptionPane.showMessageDialog(p, "El corre no esta disponible");
                } else {
                    this.envioCorreos = new EnvioCorreos(p);
                    boolean enviado = envioCorreos.enviarCorreoRecuperacion();
                    if (enviado) {
                        this.p.codigoAutenticacion("Correo", correo);
                        this.p.field.setText("");
                        this.validador = 3;
                    } else {
                        JOptionPane.showMessageDialog(p, "No se pudo enviar el correo, intente nuevamente");
                    }
                }
            } else if (validador == 2) {
                if (!p.field.getText().isEmpty() && (MetodosPublicos.validarNumero(p.field.getText()) && p.field.getText().length() == 10)) {
                    this.p.codigoAutenticacion("SMS", this.p.field.getText());
                    this.p.field.setText("");
                    this.validador = 3;
                } else {
                    JOptionPane.showMessageDialog(p, "Ingrese un numero de celular valido (10 digitos)");
                }
            } else if (validador == 3) {
                String codigoIngresado = p.field.getText().trim();
                if (codigoIngresado.isEmpty()) {
                    JOptionPane.showMessageDialog(p, "El campo codigo de verificacion es obligatorio");
                } else if (envioCorreos == null || envioCorreos.getNumeroAzar() == null) {
                    JOptionPane.showMessageDialog(p, "El codigo ha expirado, solicite uno nuevo");
                    this.p.vistaMetodoDerecuperacion();
                    this.validador = 0;
                } else if (codigoIngresado.equals(String.valueOf(envioCorreos.getNumeroAzar()))) {
                    this.p.vistaCambiarContrasena();
                    this.p.field.setText("");
                    envioCorreos.eliminarValorNumeroAzar();
                    this.validador = 4;
                } else {
                    JOptionPane.showMessageDialog(p, "El codigo ingresado es incorrecto");
                }
            } else if (validador == 4) {
                if ((!p.contrasena.getText().isEmpty() && !String.valueOf(p.contrasenaVeri.getPassword()).isEmpty())
                        && (MetodosPublicos.validarContrasena(p.contrasena.getText())
                        && MetodosPublicos.validarTamano(p.contrasena.getText(), 8))
                        && String.valueOf(p.contrasenaVeri.getPassword()).equals(p.contrasena.getText())) {
                    this.p.field.setText("");
                    this.p.dispose();
                    this.validador = 0;
                } else if (p.contrasena.getText().isEmpty() && String.valueOf(p.contrasenaVeri.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(p, "Los campos son obligatorios");
                } else {
                    if (p.contrasena.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(p, "El campo de constrasena esta vacio");
                    }
                    if (String.valueOf(p.contrasenaVeri.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(p, "Confirma la contrasena por favor");
                    }
                    if (!MetodosPublicos.validarContrasena(p.contrasena.getText())) {
                        JOptionPane.showMessageDialog(p, "La contrasena debe de cumplir con estos parametros\n"
                                + "Minimo 8 caracteres\n"
                                + "1 Mayuscula,\n"
                                + "1 Minuscula\n"
                                + "1 Numero\n"
                                + "1 Simbolos permitidos @, #, $, %, &, *, -, _, !, ?");
                    }
                    if (!p.contrasena.getText().isEmpty() && !String.valueOf(p.contrasenaVeri.getPassword()).isEmpty()
                            && !String.valueOf(p.contrasenaVeri.getPassword()).equals(p.contrasena.getText())) {
                        JOptionPane.showMessageDialog(p, "Las constrasenas no coinciden");
                    }
                    if (!MetodosPublicos.validarTamano(p.contrasena.getText(), 8) || !MetodosPublicos.validarTamano(String.valueOf(p.contrasenaVeri.getPassword()), 8)) {
                        JOptionPane.showMessageDialog(p, "El campo contrasena debe de contener como minimo 8 caracteres");
                    }
                }
            }
        }
        if (e.getSource() == this.p.flecha) {
            if (validador == 0) {
                this.p.dispose();
            } else if (validador == 1 || validador == 2) {
                this.p.vistaMetodoDerecuperacion();
                this.validador = 0;
            } else if (validador == 3 || validador == 4) {
                JOptionPane.showMessageDialog(p, "Se cancelara todo el proceso de recuperacion");
                if (envioCorreos != null) {
                    envioCorreos.eliminarValorNumeroAzar();
                }
                this.p.vistaMetodoDerecuperacion();
                this.validador = 0;
            }
        }
    }
}
