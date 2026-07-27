package controller;

import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.Hashed;
import model.MetodosPublicos;
import model.UsuarioDao;
import model.Usuario;
import view.AdministradorCentroInterfaz;
import view.AdministradorDelSistemaInterfaz;
import view.Login;
import view.MedicoInterfaz;
import view.OperarioInterfaz;
import view.PacienteInterfaz;
import view.RecuperacionContrasenaInterfaz;
import view.RegistroUsuariosInterfaz;

public class LoginController implements ActionListener {

    //Variables static por que las van a compartir todas las intancias por ende su valor va aser el mismo y si cambia una cambia para todas
    private static final byte MAX_INTENTOS = 5;
    private static final int MINUTOS_BLOQUEO = 5;
    //Variables de instancia
    private RecuperacionContrasenaInterfaz rc;
    private RegistroUsuariosInterfaz ur;
    private Login lg;
    private UsuarioDao usuDao = new UsuarioDao();
    private Usuario usu;
    private byte c;
    private boolean bloqueado;

    //Este metodo me hace el proceso para el ojo que permite ver la contrasena
    private void ojodelLogin() {
        Thread hiloOjo = new Thread(() -> {
            lg.lblOjo.addMouseListener(new MouseAdapter() {
                boolean isVisible = false;

                @Override
                public void mouseClicked(MouseEvent e) {
                    isVisible = !isVisible;
                    if (isVisible) {
                        lg.cambioEstado((byte) 1); //Mostrar texto
                        lg.lblOjo.setIcon(lg.iconOjoAbierto);
                    } else {
                        lg.cambioEstado((byte) 2); //Ocultar texto
                        lg.lblOjo.setIcon(lg.iconOjoCerrado);
                    }
                }
            });
        });
        hiloOjo.start();
    }

    //Este metodo hace 2 cosas primero le indicamos que el JLabel va a tener un mouseClicked y segundo
    //me hace el proceso para abrir la vista de recuperacion de contrasena
    private void vistaRecuperarContrasena() {
        Thread hiloVistaRecu = new Thread(() -> {
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
        });
        hiloVistaRecu.start();
    }

    private void agregarActionListenerABotonesDeLogin() {
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
    }

    //Controlador
    public LoginController(Login lg, RecuperacionContrasenaInterfaz rc, RegistroUsuariosInterfaz ur) {
        this.lg = lg;
        ojodelLogin();
        agregarActionListenerABotonesDeLogin();
        this.rc = rc;
        vistaRecuperarContrasena();
        this.c = 0;
        this.bloqueado = false;
        this.ur = ur;
        RegistroUsuariosController.inisializarRegistroPersonas();
    }

    public void abrirVistaRegistro() {
        Thread hiloVistasRegistro = new Thread(() -> {
            RegistroUsuariosController cRu = new RegistroUsuariosController(ur);
            ur.setDefaultCloseOperation(EXIT_ON_CLOSE);
            ur.setExtendedState(MAXIMIZED_BOTH);
            ur.setVisible(true);
        });
        hiloVistasRegistro.start();
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

    //Este metodo tadavi no se si ponerlo en el abirInterfaz para abrir las vistas sin problemas
    private void verInterfaz(PacienteInterfaz p) {
        p.setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setExtendedState(MAXIMIZED_BOTH);
        p.setVisible(true);
    }

    //Metodo reutilizable abre la interfaz correspondiente segun el rol del usuario que inicio sesion
    private void abrirInterfazSegunRol(Usuario usuario) {
        switch (usuario.getIdRol()) {
            case 1:
//                AdministradorDelSistemaInterfaz adminSistem = new AdministradorDelSistemaInterfaz("Administrador del sistema", usuario);
//                AdministradorDelSistemaController cp = new AdministradorDelSistemaController(adminSistem);
//                verInterfaz(adminSistem);
                break;
            case 2:
//                AdministradorCentroInterfaz adminI = new AdministradorCentroInterfaz("Administrador del centro", usuario);
//                AdminCentroController adminC = new AdminCentroController(adminI);
//                verInterfaz(adminI);
                break;
            case 3:
//                MedicoInterfaz i = new MedicoInterfaz("Medico", usuario);
//                MedicoController mc = new MedicoController(i);
//                verInterfaz(i);
                break;
            case 4:
//                OperarioInterfaz opI = new OperarioInterfaz("Operario", usuario);
//                OperarioController controller = new OperarioController(opI);
//                verInterfaz(opI);
                break;
            case 5:
                PacienteInterfaz p = new PacienteInterfaz("Paciente", usuario);
                PacienteController clg = new PacienteController(p);
                verInterfaz(p);
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
        if (e.getSource() == lg.bIngresar) {
            estadoDeCosas(false);
            String id = lg.getId();
            String contrasena = lg.getPassword();
            if (c >= MAX_INTENTOS) {
                bloquearFormularioTemporalmente();
                this.lg.limpiar();
            } else if (estadito(id, contrasena)) {
                usu = RegistroUsuariosController.consultarPersona(id);
                if (usu != null) {
                    if (usu.isEstado() && Hashed.verifyPassword(contrasena, usu.getContrasena())) {
                        c = 0;
                        if (usu.getSexoBiologico().equals("Masculino")) {
                            MetodosPublicos.reproducirSonido("bienvenido.wav");
                        } else {
                            MetodosPublicos.reproducirSonido("bienvenida.wav");
                        }
                        abrirInterfazSegunRol(usu);
                    } else {
                        if (!Hashed.verifyPassword(contrasena, usu.getContrasena())) {
                            JOptionPane.showMessageDialog(lg, "contrasena incorrecta incorrecta");
                        } else {
                            if (!usu.isEstado()) {
                                JOptionPane.showMessageDialog(lg, "El usuario esta inabilitado");
                            } else {
                                JOptionPane.showMessageDialog(lg, "La contrasena es incorrecta");
                            }
                        }
                        c++;
                    }
                } else {
                    c++;
                    JOptionPane.showMessageDialog(lg, "El usuario no existe");
                }
                estadoDeCosas(true);
                usu = null;
                id = null;
                contrasena = null;
            } else {
                String mensaje = "";
                this.c++;
                if (id.isEmpty()) {
                    mensaje += "Campo id es obligatorio.\n";
                } else {
                    if (!MetodosPublicos.validarTamano(id, 8, 10)) {
                        mensaje += "Campo id debe contener 8 o 10 caracteres.\n";
                    }
                    if (!MetodosPublicos.validarNumero(id)) {
                        mensaje += "Campo id contiene caracteres invalidos.\n";
                    }
                }
                if (contrasena.isEmpty()) {
                    mensaje += "Campo Contrasena es obligatorio.\n";
                } else {
                    if (!MetodosPublicos.validarTamano(contrasena, 8)) {
                        mensaje += "El campo contrasena debe dcontener como minimo 8 caracteres.\n";
                    }
                    if (!MetodosPublicos.validarContrasena(contrasena)) {
                        mensaje += "La contrasena debe de cumplir con estos parametros\n"
                                + "1 Mayuscula,\n"
                                + "1 Minuscula\n"
                                + "1 Numero\n"
                                + "1 Simbolos permitidos @, #, $, %, &, *, -, _, !, ?.\n";
                    }
                }
                JOptionPane.showMessageDialog(lg, mensaje,"Advertencia",JOptionPane.WARNING_MESSAGE);
                estadoDeCosas(true);
            }
        }

        if (e.getSource() == lg.bRegistar) {
            abrirVistaRegistro();
        }
    }
}
