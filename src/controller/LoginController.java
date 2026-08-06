package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.MetodosPublicos;
import model.UsuarioPublicoDao;
import view.AdministradorCentroInterfaz;
import view.AdministradorDelSistemaInterfaz;
import view.Login;
import view.MedicoInterfaz;
import view.OperarioInterfaz;
import view.PacienteInterfaz;
import view.RecuperacionContrasenaInterfaz;
import view.RegistroUsuariosInterfaz;
import model.UsuarioPublico;
import model.SegunRol;

public class LoginController implements ActionListener, SegunRol {

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
    private UsuarioPublicoDao usuDao = new UsuarioPublicoDao();
    private UsuarioPublico usuario;
    private byte c;
    private boolean bloqueado;
    private Validador validador = new Validador();

    //Controlador
    public LoginController(Login lg, RecuperacionContrasenaInterfaz rc, RegistroUsuariosInterfaz ur) {
        this.lg = lg;
        MetodosPublicos.soloNumeros(this.lg.getField(), 10);
        MetodosPublicos.tamanoField(this.lg.getFieldPassword(), 64);
        ojodelLogin();
        this.ur = ur;
        agregarActionListenerABotonesDeLogin();
        this.rc = rc;
        vistaRecuperarContrasena();
        this.c = 0;
        this.bloqueado = false;
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
                        RecuperarContrasenaController cRc = new RecuperarContrasenaController(rc);
                        MetodosPublicos.abrirVentana(rc);
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
            MetodosPublicos.abrirVentana(ur);
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
            return;
        }
        if (e.getSource() == lg.bRegistar) {
            abrirVistaRegistro();
            return;
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

        validador = validarFormatoCredenciales(id, contrasena);
        if (validador.tieneErrores()) {
            registrarIntentoFallido();
            JOptionPane.showMessageDialog(lg, validador.obtenerErrores(), "Advertencia", JOptionPane.WARNING_MESSAGE);
            estadoDeCosas(true);
            lg.limpiar();
            validador.vaciarStringBuilder();
            return;
        }

        intentarIniciarSesion(id, contrasena);
        estadoDeCosas(true);
        lg.limpiar();
    }

    private Validador validarFormatoCredenciales(String id, String contrasena) {
        Validador validador1 = new Validador();

        validador1.validar(id.isEmpty(), "Campo id es obligatorio.\n");
        if (!id.isEmpty()) {
            validador1.validar(!MetodosPublicos.validarNumero(id),
                    "Campo id contiene caracteres invalidos.\n");
            validador1.validar(!MetodosPublicos.validarTamano(id, 8, 10),
                    "Campo id debe contener 8 o 10 numeros.\n");
        }

        validador1.validar(contrasena.isEmpty(), "Campo Contrasena es obligatorio.\n");
        if (!contrasena.isEmpty()) {
            validador1.validar(!MetodosPublicos.validarTamano(contrasena, 8),
                    "El campo contrasena debe contener como minimo 8 caracteres.\n");
            validador1.validar(!MetodosPublicos.validarContrasena(contrasena),
                    "La contrasena debe cumplir con estos parametros:\n"
                    + "1 Mayuscula, 1 Minuscula, 1 Numero,\n"
                    + "1 Simbolo permitido (@, #, $, %, &, *, -, _, !, ?).\n");
        }

        return validador1;
    }

    private void intentarIniciarSesion(String id, String contrasena) {
        usuario = usuDao.getUsuario(id, contrasena);

        if (usuario == null) {
            registrarIntentoFallido();
            if(!usuDao.validarCampoIdBs(id,"usuario","numero_identificacion")){
                JOptionPane.showMessageDialog(lg, "El usuario no existe");
            }else{
                JOptionPane.showMessageDialog(lg, "Usuario o contrasena incorrectos");
            }
        } else if (!usuario.isEstado()) {
            JOptionPane.showMessageDialog(lg, "El usuario esta inhabilitado", "Inhabilitado", JOptionPane.WARNING_MESSAGE);
        } else {
            c = 0;
            ingresarComoUsuario(usuario);
        }
    }

    private void ingresarComoUsuario(UsuarioPublico usuario) {
        reproducirSonidoDeBienvenida(usuario);
        cargarSegunRol(usuario.getIdRol());
    }

    private void reproducirSonidoDeBienvenida(UsuarioPublico usuario) {
        String sonido = SEXO_MASCULINO.equals(usuario.getSexoBiologico()) ? SONIDO_BIENVENIDO : SONIDO_BIENVENIDA;
        MetodosPublicos.reproducirSonido(sonido);
    }

    private void registrarIntentoFallido() {
        c++;
    }

    @Override
    public void cargarSegunRol(byte n) {
        switch (n) {
            case 1:
                AdministradorDelSistemaInterfaz adminSistem = new AdministradorDelSistemaInterfaz("Administrador del sistema", usuario);
                AdministradorDelSistemaController cp = new AdministradorDelSistemaController(adminSistem);
                MetodosPublicos.abrirVentana(adminSistem);
                break;
            case 2:
                AdministradorCentroInterfaz adminI = new AdministradorCentroInterfaz("Administrador del centro", usuario);
                AdminCentroController adminC = new AdminCentroController(adminI);
                MetodosPublicos.abrirVentana(adminI);
                break;
            case 3:
                MedicoInterfaz i = new MedicoInterfaz("Medico", usuario);
                MedicoController mc = new MedicoController(i);
                MetodosPublicos.abrirVentana(i);
                break;
            case 4:
                OperarioInterfaz opI = new OperarioInterfaz("Operario", usuario);
                OperarioController controller = new OperarioController(opI);
                MetodosPublicos.abrirVentana(opI);
                break;
            case 5:
                PacienteInterfaz p = new PacienteInterfaz("Paciente", usuario);
                PacienteController clg = new PacienteController(p);
                MetodosPublicos.abrirVentana(p);
                break;
            default:
                System.out.println("se produjo un error rol no valido");
                break;
        }
    }

    //Metodo bloquea el formulario 5 minutos sin congelar la interfaz
    private void bloquearFormularioTemporalmente() {
        bloqueado = true;
        JOptionPane.showMessageDialog(lg, "Los has intentado muchas veces por favor espera " + MINUTOS_BLOQUEO + " minutos");
        Timer temporizador = new Timer(MINUTOS_BLOQUEO * 60 * 1000, ev -> {
            bloqueado = false;
            c = 0;
            estadoDeCosas(true);
        });
        temporizador.setRepeats(false);
        temporizador.start();
    }
}
