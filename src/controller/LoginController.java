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
import model.RegistroPersonasDao;
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
    private static final String SONIDO_BIENVENIDO = "bienvenido.wav";
    private static final String SONIDO_BIENVENIDA = "bienvenida.wav";
    private static final String SEXO_MASCULINO = "Masculino";

    //Variables de instancia
    private RecuperacionContrasenaInterfaz rc;
    private RegistroUsuariosInterfaz ur;
    private Login lg;
    private UsuarioDao usuDao = new UsuarioDao();
    private byte c;
    private boolean bloqueado;

    //Controlador
    public LoginController(Login lg, RecuperacionContrasenaInterfaz rc, RegistroUsuariosInterfaz ur) {
        this.lg = lg;
        ojodelLogin();
        agregarActionListenerABotonesDeLogin();
        MetodosPublicos.soloNumeros(lg.getField(), 10);
        this.rc = rc;
        vistaRecuperarContrasena();
        this.c = 0;
        this.bloqueado = false;
        this.ur = ur;
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lg.bIngresar) {
            procesarIntentoDeIngreso();
        } else if (e.getSource() == lg.bRegistar) {
            abrirVistaRegistro();
        }
    }

    private void procesarIntentoDeIngreso() {
        estadoDeCosas(false);

        String id = lg.getId();
        String contrasena = lg.getPassword();

        if (bloqueado || c >= MAX_INTENTOS) {
            bloquearFormularioTemporalmente();
            lg.limpiar();
            return;
        }

        Validador validador = validarFormatoCredenciales(id, contrasena);
        if (validador.tieneErrores()) {
            registrarIntentoFallido();
            JOptionPane.showMessageDialog(lg, validador.obtenerMensaje(), "Advertencia", JOptionPane.WARNING_MESSAGE);
            estadoDeCosas(true);
            lg.limpiar();
            return;
        }

        intentarIniciarSesion(id, contrasena);
        estadoDeCosas(true);
        lg.limpiar();
    }

    private Validador validarFormatoCredenciales(String id, String contrasena) {
        Validador validador = new Validador();

        validador.validar(() -> id.isEmpty(), "Campo id es obligatorio.\n");
        if (!id.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarNumero(id),
                    "Campo id contiene caracteres invalidos.\n");
            validador.validar(() -> !MetodosPublicos.validarTamano(id, 8, 10),
                    "Campo id debe contener 8 o 10 caracteres.\n");
        }

        validador.validar(() -> contrasena.isEmpty(), "Campo Contrasena es obligatorio.\n");
        if (!contrasena.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarTamano(contrasena, 8),
                    "El campo contrasena debe contener como minimo 8 caracteres.\n");
            validador.validar(() -> !MetodosPublicos.validarContrasena(contrasena),
                    "La contrasena debe cumplir con estos parametros:\n"
                    + "1 Mayuscula, 1 Minuscula, 1 Numero,\n"
                    + "1 Simbolo permitido (@, #, $, %, &, *, -, _, !, ?).\n");
        }

        return validador;
    }

    private void intentarIniciarSesion(String id, String contrasena) {
        Usuario usuario = new RegistroPersonasDao().getUsuario(id);

        if (usuario == null) {
            registrarIntentoFallido();
            JOptionPane.showMessageDialog(lg, "El usuario no existe");
            return;
        }

        if (!credencialesValidas(usuario, contrasena)) {
            registrarIntentoFallido();
            mostrarMotivoDeFalloDeIngreso(usuario, contrasena);
            return;
        }

        c = 0;
        ingresarComoUsuario(usuario);
    }

    private boolean credencialesValidas(Usuario usuario, String contrasena) {
        return usuario.isEstado() && Hashed.verifyPassword(contrasena, usuario.getContrasena());
    }

    private void mostrarMotivoDeFalloDeIngreso(Usuario usuario, String contrasena) {
        if (!Hashed.verifyPassword(contrasena, usuario.getContrasena())) {
            JOptionPane.showMessageDialog(lg, "La contraseña es incorrecta");
        } else if (!usuario.isEstado()) {
            JOptionPane.showMessageDialog(lg, "El usuario esta inhabilitado");
        }
    }

    private void ingresarComoUsuario(Usuario usuario) {
        usuario.setContrasena("**@@@@$$$???<<>>");
        reproducirSonidoDeBienvenida(usuario);
        abrirInterfazSegunRol(usuario);
    }

    private void reproducirSonidoDeBienvenida(Usuario usuario) {
        String sonido = SEXO_MASCULINO.equals(usuario.getSexoBiologico()) ? SONIDO_BIENVENIDO : SONIDO_BIENVENIDA;
        MetodosPublicos.reproducirSonido(sonido);
    }

    private void registrarIntentoFallido() {
        c++;
    }

    private void verInterfaz(javax.swing.JFrame frame) {
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    //Aqui abre la interfaz correspondiente segun el rol del usuario que inicio sesion
    private void abrirInterfazSegunRol(Usuario usuario) {
        switch (usuario.getIdRol()) {
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

    //Metodo bloquea el formulario 5 minutos sin congelar la interfaz
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
}
