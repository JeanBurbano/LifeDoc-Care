package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.EnvioCorreos;
import model.Hashed;
import model.MetodosPublicos;
import model.UsuDao;
import view.RecuperacionContrasenaInterfaz;

public class RecuperarContrasenaController implements ActionListener {

    RecuperacionContrasenaInterfaz p;
    private String correo;
    private String numeroCelular;
    private byte validador;
    private EnvioCorreos envioCorreos;
    private UsuDao usuDao;

    private void agregarActionListener() {
        this.p.btnCorreo.addActionListener(this);
        this.p.btnSms.addActionListener(this);
        this.p.btnContinuar.addActionListener(this);
        this.p.flecha.addActionListener(this);
    }

    public RecuperarContrasenaController(RecuperacionContrasenaInterfaz p) {
        this.usuDao = new UsuDao();
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
                String correoIngresado = p.field.getText().trim();
                if (correoIngresado.isEmpty()) {
                    JOptionPane.showMessageDialog(p, "El campo correo electronico es obligatorio");
                } else if (!MetodosPublicos.validarFormatoCorreoGmail(correoIngresado)) {
                    JOptionPane.showMessageDialog(p, "El correo debe tener el formato .....@gmail.com");
                } else if (!usuDao.validarCampoIdBs(correoIngresado, "usuario", "correo_electronico")) {
                    JOptionPane.showMessageDialog(p, "El corre no esta disponible");
                } else {
                    this.envioCorreos = new EnvioCorreos(p);
                    boolean enviado = envioCorreos.enviarCorreoRecuperacion();
                    if (enviado) {
                        this.correo = correoIngresado;
                        this.p.codigoAutenticacion("Correo", correoIngresado);
                        this.p.field.setText("");
                        this.validador = 3;
                    } else {
                        JOptionPane.showMessageDialog(p, "No se pudo enviar el correo, intente nuevamente");
                    }
                }
            } else if (validador == 2) {
                String numeroIngresado = p.field.getText().trim();
                if (numeroIngresado.isEmpty() || !MetodosPublicos.validarNumero(numeroIngresado) || numeroIngresado.length() != 10) {
                    JOptionPane.showMessageDialog(p, "Ingrese un numero de celular valido (10 digitos)");
                } else if (!usuDao.validarCampoIdBs(numeroIngresado, "usuario", "numero_celular")) {
                    JOptionPane.showMessageDialog(p, "El numero no esta disponible");
                } else {
                    this.numeroCelular = numeroIngresado;
                    this.p.codigoAutenticacion("SMS", numeroIngresado);
                    this.p.field.setText("");
                    this.validador = 3;
                }
            } else if (validador == 3) {
                String codigoIngresado = p.field.getText().trim();
                if (codigoIngresado.isEmpty()) {
                    JOptionPane.showMessageDialog(p, "El campo codigo de verificacion es obligatorio");
                } else if (envioCorreos == null || envioCorreos.codigoExpirado()) {
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

                    String contrasenaHasheada = Hashed.hashPassword(p.contrasena.getText());
                    boolean actualizado = actualizarContrasenaUsuario(contrasenaHasheada);

                    if (actualizado) {
                        JOptionPane.showMessageDialog(p, "Contraseña actualizada correctamente");
                        this.correo = null;
                        this.numeroCelular = null;
                        this.p.field.setText("");
                        this.p.dispose();
                        this.validador = 0;
                    } else {
                        JOptionPane.showMessageDialog(p, "No se pudo actualizar la contraseña, intente nuevamente");
                    }
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
                this.correo = null;
                this.numeroCelular = null;
                this.p.vistaMetodoDerecuperacion();
                this.validador = 0;
            }
        }
    }

    private boolean actualizarContrasenaUsuario(String contrasenaHasheada) {
        if (correo != null && !correo.isEmpty()) {
            return usuDao.actualizarContrasenaPorCorreo(correo, contrasenaHasheada);
        }
        if (numeroCelular != null && !numeroCelular.isEmpty()) {
            return usuDao.actualizarContrasenaPorNumeroCelular(numeroCelular, contrasenaHasheada);
        }
        return false;
    }
}