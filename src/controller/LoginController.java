package controller;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.MetodosPublicos;
import model.UsuarioDao;
import model.Paciente;
import view.AdministradorDelSistemaInterfaz;
import view.Login;
import view.MedicoInterfaz;
import view.OperarioInterfaz;
import view.PacienteInterfaz;
import view.RecuperacionContrasenaInterfaz;

public class LoginController implements ActionListener {

    private static final byte MAX_INTENTOS = 5;
    private static final int MINUTOS_BLOQUEO = 5;

    RecuperacionContrasenaInterfaz rc;
    Login lg;
    UsuarioDao usuDao = new UsuarioDao();
    private Paciente usu;
    private byte c;
    private boolean bloqueado;

    public LoginController(Login lg, RecuperacionContrasenaInterfaz rc) {
        lg.lblOjo.addMouseListener(new MouseAdapter() {
            boolean isVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                isVisible = !isVisible;
                if (isVisible) {
                    lg.cambioEstado((byte) 1); // Mostrar texto
                    lg.lblOjo.setIcon(lg.iconOjoAbierto);
                } else {
                    lg.cambioEstado((byte) 2); // Ocultar texto
                    lg.lblOjo.setIcon(lg.iconOjoCerrado);
                }
            }
        });
        lg.titulo2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lg.titulo2.isEnabled()) {
                    rc.setVisible(true);
                    rc.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    rc.setExtendedState(MAXIMIZED_BOTH);
                    RecuperarContrasenaController cRc = new RecuperarContrasenaController(rc);
                } else {
                    JOptionPane.showMessageDialog(lg, "Por Favor Intenta Mas Tarde");
                }
            }
        });
        this.lg = lg;
        this.rc = rc;
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
        this.c = 0;
        this.bloqueado = false;
    }

    private void estadoDeCosas(boolean estado) {
        this.lg.bIngresar.setEnabled(estado);
        this.lg.bRegistar.setEnabled(estado);
        this.lg.titulo2.setEnabled(estado);
    }

    private boolean estadito(String id, String contrasena) {
        return (MetodosPublicos.validarTamano(id, 8, 10)
                && MetodosPublicos.validarNumero(id))
                && (MetodosPublicos.validarTamano(contrasena, 8)
                && MetodosPublicos.validarContrasena(contrasena));
    }

    private void creaUsuSegunRol(byte id_rol) {
        switch (id_rol) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:

        }
    }

    //Metodo reutilizable abre la interfaz correspondiente segun el rol del usuario que inicio sesion
    private void abrirInterfazSegunRol(Paciente usuario) {
        switch (usuario.getId_rol()) {
            case 1:
                AdministradorDelSistemaInterfaz adminSistem = new AdministradorDelSistemaInterfaz("Administrador del sistema", usuario);
                adminSistem.setVisible(true);
                adminSistem.setDefaultCloseOperation(EXIT_ON_CLOSE);
                adminSistem.setExtendedState(MAXIMIZED_BOTH);
                AdministradorDelSistemaController cp = new AdministradorDelSistemaController(adminSistem);
                break;
            case 3:
                MedicoInterfaz i = new MedicoInterfaz("Medico", usuario);
                i.setVisible(true);
                i.setDefaultCloseOperation(EXIT_ON_CLOSE);
                i.setExtendedState(MAXIMIZED_BOTH);
                MedicoController mc = new MedicoController(i);
                break;
            case 4:
                OperarioInterfaz opI = new OperarioInterfaz("Operario", usuario);
                opI.setVisible(true);
                opI.setDefaultCloseOperation(EXIT_ON_CLOSE);
                opI.setExtendedState(MAXIMIZED_BOTH);
                OperarioController controller = new OperarioController(opI);
                break;
            case 5:
                PacienteInterfaz p = new PacienteInterfaz("Paciente", usuario);
                p.setVisible(true);
                p.setDefaultCloseOperation(EXIT_ON_CLOSE);
                p.setExtendedState(MAXIMIZED_BOTH);
                PacienteController clg = new PacienteController(p);
                break;
            default:
                System.out.println("se produjo un error rol no valido");
                break;
        }
    }

    //Metodo reutilizable bloquea el formulario 5 minutos sin congelar la interfaz
    private void bloquearFormularioTemporalmente() {
        this.bloqueado = true;
        JOptionPane.showMessageDialog(lg, "Los has intentado muchas veces por favor espera " + MINUTOS_BLOQUEO + " minutos");
        estadoDeCosas(false);
        Timer temporizador = new Timer(MINUTOS_BLOQUEO * 60 * 1000, ev -> {
            this.bloqueado = false;
            this.c = 0;
            estadoDeCosas(true);
        });
        temporizador.setRepeats(false);
        temporizador.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.lg.bIngresar) {

            if (this.bloqueado) {
                JOptionPane.showMessageDialog(lg, "Los has intentado muchas veces por favor espera " + MINUTOS_BLOQUEO + " minutos");
                return;//Rompe si ya fue bloqueado
            }

            estadoDeCosas(false);
            String id = this.lg.getId();
            String contrasena = this.lg.getPassword();
            if (estadito(id, contrasena) && c < MAX_INTENTOS) {
                estadoDeCosas(true);
                this.usu = usuDao.login(id, contrasena);
                contrasena = null;
                if (usu != null && usu.getEstado()) {
                    this.c = 0;
                    this.lg.dispose();
                    MetodosPublicos.reproducirSonido("senora-su-hijo-esta-viendo-p0rr0.wav");
                    abrirInterfazSegunRol(usu);
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
                    this.c++;
                }
                estadoDeCosas(true);
                this.usu = null;
                id = null;
            } else if (c >= MAX_INTENTOS) {
                bloquearFormularioTemporalmente();
            } else {
                estadoDeCosas(true);
                this.c++;
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "Campo id es obligatorio");
                } else {
                    if (!MetodosPublicos.validarTamano(id, 8, 10)) {
                        JOptionPane.showMessageDialog(lg, "Campo id debe contener 8 o 10 caracteres");
                    }
                    if (!MetodosPublicos.validarNumero(id)) {
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
