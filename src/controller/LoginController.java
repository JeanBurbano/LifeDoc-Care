package controller;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.MetodosPublicos;
import model.UsuarioDao;
import model.Paciente;
import view.AdministradorDelSistemaInterfaz;
import view.Login;
import view.PacienteInterfaz;
import view.RecuperacionContrasenaInterfaz;

public class LoginController implements ActionListener {

    RecuperacionContrasenaInterfaz rc;
    Login lg;
    UsuarioDao usuDao;
    private Paciente usu;
    private byte c;

    public LoginController(Login lg, RecuperacionContrasenaInterfaz recuperarC) {
        this.lg = lg;
        this.rc = recuperarC;
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
        this.lg.titulo2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rc.setVisible(true);
                rc.setDefaultCloseOperation(EXIT_ON_CLOSE);
                rc.setExtendedState(MAXIMIZED_BOTH);
                RecuperarContrasenaController cRc = new RecuperarContrasenaController(rc);
            }
        });
        this.c = 0;
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

            if (validar && c < 6) {
                this.usuDao = new UsuarioDao();
                this.usu = usuDao.login(id, contrasena);
                contrasena = null;
                if (usu != null && usu.getEstado()) {
                    this.lg.dispose();
                    switch (usu.getId_rol()) {
                        case 1:
                            AdministradorDelSistemaInterfaz adminSistem = new AdministradorDelSistemaInterfaz(usu.getPrimerNombre(), "Paciente", "fotosPerfil/fotoDefecto.png");
                            adminSistem.setVisible(true);
                            adminSistem.setDefaultCloseOperation(EXIT_ON_CLOSE);
                            adminSistem.setExtendedState(MAXIMIZED_BOTH);
                            AdministradorDelSistemaController cp = new AdministradorDelSistemaController(adminSistem);
                            break;
                        case 5:
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
                    if (usu == null && !usuDao.validarCampoIdBs(id, "usuario", "numero_identificacion")) {
                        JOptionPane.showMessageDialog(lg, "El usuario no existe");
                    } else if (usu == null && usuDao.validarCampoIdBs(id, "usuario", "numero_identificacion")) {
                        JOptionPane.showMessageDialog(lg, "La contrasena es incorrecta");
                    } else {
                        if (!usu.getEstado()) {
                            JOptionPane.showMessageDialog(lg, "El usuario esta inabilitado");
                        } else {
                            JOptionPane.showMessageDialog(lg, "La contrasena es incorrecta");
                        }
                    }
                }
                this.usu = null;
                this.usuDao = null;
                id = null;
            } else if (c == 5) {
                JOptionPane.showMessageDialog(lg, "Los has intentado muchas veces por favor espera 5 minutos");
                this.lg.bIngresar.setEnabled(false);
                this.lg.bRegistar.setEnabled(false);
                try {
                    // Espera 5 minutos (5 * 60 * 1000 milisegundos)
                    Thread.sleep(5 * 60 * 1000);
                    System.out.println("Espera finalizada.");
                } catch (InterruptedException ex) {
                    // Manejar la interrupción del hilo
                    Thread.currentThread().interrupt();
                }
                this.lg.bIngresar.setEnabled(true);
                this.lg.bRegistar.setEnabled(true);
                c = 0;
            } else {
                this.c++;
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
