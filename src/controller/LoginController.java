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
    private Login lg;
    private UsuarioDao usuDao = new UsuarioDao();
    private Paciente usu;
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

    public LoginController(Login lg, RecuperacionContrasenaInterfaz rc) {
        this.lg = lg;
        ojodelLogin();
        agregarActionListenerABotonesDeLogin();
        this.rc = rc;
        vistaRecuperarContrasena();
        this.c = 0;
        this.bloqueado = false;
    }

    public void abrirVistaRegistro() {
        Thread hiloVistasRegistro = new Thread(() -> {
            RegistroUsuariosInterfaz ru = new RegistroUsuariosInterfaz("Registro");
            ru.setVisible(true);
            ru.setDefaultCloseOperation(EXIT_ON_CLOSE);
            ru.setExtendedState(MAXIMIZED_BOTH);
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

    private void creaUsuSegunRol(byte id_rol, String id, String contrasena) {
        switch (id_rol) {
            case 1:
                //Aqui se crearia usuario administrador del sistema pero como todavia no esta contruido la clase no lo hago
                this.usu = usuDao.login(id, contrasena);
                break;
            case 2:
                //Aqui se crearia usuario administrador del centro pero como todavia no esta contruido la clase no lo hago
                this.usu = usuDao.login(id, contrasena);
                break;
            case 3:
                //Aqui se crearia medico del sistema pero como todavia no esta contruido la clase no lo hago
                this.usu = usuDao.login(id, contrasena);
                break;
            case 4:
                //Aqui se crearia usuario operarario pero como todavia no esta contruido la clase no lo hago
                this.usu = usuDao.login(id, contrasena);
                break;
            case 5:
                this.usu = usuDao.login(id, contrasena);
                break;
            default:

        }
    }

    //Este metodo tadavi no se si ponerlo en el abirInterfaz para abrir las vistas sin problemas
    private void verInterfaz(PacienteInterfaz p) {
        p.setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setExtendedState(MAXIMIZED_BOTH);
        p.setVisible(true);
    }

    //Metodo reutilizable abre la interfaz correspondiente segun el rol del usuario que inicio sesion
    private void abrirInterfazSegunRol(Paciente usuario) {
        switch (usuario.getId_rol()) {
            case 1:
                AdministradorDelSistemaInterfaz adminSistem = new AdministradorDelSistemaInterfaz("Administrador del sistema", usuario);
                AdministradorDelSistemaController cp = new AdministradorDelSistemaController(adminSistem);
                verInterfaz(adminSistem);
                break;
            case 2:
                AdministradorCentroInterfaz adminI = new AdministradorCentroInterfaz("Administrador del centro", usuario);
                AdminCentroController adminC = new AdminCentroController(adminI);
                verInterfaz(adminI);
                break;
            case 3:
                MedicoInterfaz i = new MedicoInterfaz("Medico", usuario);
                MedicoController mc = new MedicoController(i);
                verInterfaz(i);
                break;
            case 4:
                OperarioInterfaz opI = new OperarioInterfaz("Operario", usuario);
                OperarioController controller = new OperarioController(opI);
                verInterfaz(opI);
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
            String id = this.lg.getId();
            String contrasena = this.lg.getPassword();
            if (c >= MAX_INTENTOS) {
                bloquearFormularioTemporalmente();
                this.lg.limpiar();
            } else if (estadito(id, contrasena)) {
                estadoDeCosas(true);
                byte idRol = usuDao.sacarIdRol(id, contrasena);
                creaUsuSegunRol(idRol, id, contrasena);
                if (usu != null && usu.getEstado()) {
                    this.c = 0;
                    this.lg.dispose();
                    MetodosPublicos.reproducirSonido("bienvenido.wav");
                    abrirInterfazSegunRol(usu);
                } else {
                    boolean verificardor = usuDao.validarCampoIdBs(id, "usuario", "numero_identificacion");
                    if (usu == null && !verificardor) {
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
                contrasena = null;
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

        if (e.getSource() == lg.bRegistar) {
            abrirVistaRegistro();
        }
    }
}
