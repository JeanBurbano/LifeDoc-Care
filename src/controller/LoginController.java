package controller;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.MetodosPublicos;
import model.UsuarioDao;
import model.Usuarios;
import view.Login;
import view.PacienteInterfaz;

public class LoginController implements ActionListener {

    Login lg;
    UsuarioDao usuDao;
    private Usuarios usu;

    public LoginController(Login lg) {
        this.lg = lg;
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.lg.bIngresar) {
            String id = this.lg.getId();
            String contrasena = this.lg.getPassword();
            final boolean validar = (MetodosPublicos.validarTamano(id, 8, 10)
                    && MetodosPublicos.validarid(id))
                    && (MetodosPublicos.validarTamano(contrasena, 8)
                    && MetodosPublicos.validarContrasena(contrasena));

            if (validar) {
                this.usuDao = new UsuarioDao();
                this.usu = usuDao.login(id, contrasena);
                contrasena = null;
                if (usu != null && usu.getEstado()) {
                    switch (usu.getId_rol()) {
                        case (byte) 5:
                            PacienteInterfaz p = new PacienteInterfaz(usu.getPrimerNombre(), "Paciente", usu.getFotoPerfil());
                            p.setVisible(true);
                            p.setDefaultCloseOperation(EXIT_ON_CLOSE);
                            p.setExtendedState(MAXIMIZED_BOTH);
                            PacienteController clg = new PacienteController(p);
                            break;
                        default:
                            System.out.println("se produjo un error rol no valido");
                            break;
                    }
                } else {
                    if (usuDao.validarCampoIdBs(id)) {
                        JOptionPane.showMessageDialog(lg, "La contrasena es incorrecta");
                    } else {
                        JOptionPane.showMessageDialog(lg, "El usuario no existe");
                    }
                }
                this.usu = null;
                this.usuDao = null;
                id = null;
            } else {
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "Campo id es obligatorio");
                } else {
                    if (!MetodosPublicos.validarTamano(id, 8, 10)) {
                        JOptionPane.showMessageDialog(lg, "Campo id debe contener 8 o 10 caracteres");
                    }
                    if (!MetodosPublicos.validarid(id)) {
                        JOptionPane.showMessageDialog(lg, "Campo id contiene caracteres invalidos");
                    }
                }
                if (contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "Campo Contrasena es obligatorio");
                } else {
                    if (!MetodosPublicos.validarTamano(contrasena, 8)) {
                        JOptionPane.showMessageDialog(lg, "El campo contrasena debe dcontener como minimo 8 caracteres");
                    }
                    if (!MetodosPublicos.validarContrasena(contrasena)) {
                        JOptionPane.showMessageDialog(lg, "La contrasena debe de cumplir con estos parametros\n"
                                + "Minimo 8 caracteres\n"
                                + "1 Mayuscula,\n"
                                + "1 Minuscula\n"
                                + "1 Numero\n"
                                + "1 Simbolos permitidos @, #, $, %, &, *, -, _, !, ?");
                    }
                }
            }
        }
    }
}
