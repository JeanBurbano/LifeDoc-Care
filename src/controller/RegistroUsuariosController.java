package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Hashed;
import model.MetodosPublicos;
import model.RegistroPersonasDao;
import model.Usuario;
import model.UsuarioDao;

import view.RegistroUsuariosInterfaz;

public class RegistroUsuariosController implements ActionListener {

    private static UsuarioDao usuarioDao = new UsuarioDao();
    protected static int id = 0;
    private static final byte ID_ROL_PACIENTE = 5;
    public static final int TI_REGISTRO_CIVIL = 1;
    public static final int TI_TARJETA_IDENTIDAD = 2;
    public static final int TI_CEDULA = 3;
    private static final String RUTA_FOTO_DEFECTO = "fotosPerfil/fotoDefecto.png";

    private RegistroUsuariosInterfaz rI;
    
    private File archivoImagenSeleccionado;

    public RegistroUsuariosController(RegistroUsuariosInterfaz rI) {
        init(rI);
        MetodosPublicos.soloLetras(this.rI.campoPrimerNombre, 30);
        MetodosPublicos.soloLetras(this.rI.campoSegundoNombre, 30);
        MetodosPublicos.soloLetras(this.rI.campoPrimerApellido, 30);
        MetodosPublicos.soloLetras(this.rI.campoSegundoApellido, 30);
    }

    protected void init(RegistroUsuariosInterfaz rI) {
        this.rI = rI;
        this.rI.btnRegistrarse.addActionListener(this);
        this.rI.btnVolverA.addActionListener(this);
        this.rI.btnSeleccionarFoto.addActionListener(this);
        MetodosPublicos.soloNumeros(rI.campoNumeroID, 10);
        MetodosPublicos.soloNumeros(rI.campoTelefono, 10);
        archivoImagenSeleccionado = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rI.btnRegistrarse) {
            registrar();
        } else if (e.getSource() == rI.btnVolverA) {
            volverALogin();
        } else if (e.getSource() == rI.btnSeleccionarFoto) {
            abrirVentanaGuardar();
        }
    }

    private void registrar() {
        int idTipoIdentificacion = rI.campoTipoId.getSelectedIndex();
        String numeroIdentificacion = rI.campoNumeroID.getText().trim();
        String primerNombre = rI.campoPrimerNombre.getText().trim();
        String segundoNombre = rI.campoSegundoNombre.getText().trim();
        String primerApellido = rI.campoPrimerApellido.getText().trim();
        String segundoApellido = rI.campoSegundoApellido.getText().trim();
        String sexoBiologico = String.valueOf(rI.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = rI.datePickerNacimiento.getDate();
        String correo = rI.campoCorreo.getText().trim();
        String telefono = rI.campoTelefono.getText().trim();
        String contrasena = new String(rI.campoContraseña.getPassword());
        String grupoSisben = String.valueOf(rI.campoSisben.getSelectedItem());

        String errores = validarFormularioRegistro(idTipoIdentificacion, numeroIdentificacion,
                primerNombre, segundoNombre, primerApellido, segundoApellido, sexoBiologico,
                correo, telefono, contrasena, grupoSisben, fechaNacimiento).obtenerMensaje();

        if (!errores.isEmpty()) {
            mostrarError(errores);
            return;
        }

        if (existeUsuarioRegistrado(numeroIdentificacion, correo)) {
            return;
        }

        guardarNuevoUsuario(idTipoIdentificacion, numeroIdentificacion, primerNombre, segundoNombre,
                primerApellido, segundoApellido, sexoBiologico, correo, telefono, contrasena,
                grupoSisben, fechaNacimiento);
    }

    protected Validador validarFormularioRegistro(int idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String sexoBiologico, String correo, String telefono, String contrasena,
            String grupoSisben, LocalDate fechaNacimiento) {

        Validador validador = new Validador();

        validador.validar(() -> idTipoIdentificacion < 0,
                "Debe seleccionar un tipo de documento\n");

        validador.validar(() -> numeroIdentificacion.isEmpty(),
                "El campo numero de identificacion esta vacio\n");
        if (!numeroIdentificacion.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarNumero(numeroIdentificacion)
                            || !MetodosPublicos.validarTamano(numeroIdentificacion, 8, 10),
                    "El numero de identificacion debe tener entre 8 y 10 digitos\n");
        }

        validador.validar(() -> primerNombre.isEmpty(),
                "El campo primer nombre está vacio\n");
        if (!primerNombre.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarSoloLetras(primerNombre),
                    "El primer nombre solo debe contener letras\n");
        }

        if (!segundoNombre.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarSoloLetras(segundoNombre),
                    "El segundo nombre solo debe contener letras\n");
        }

        validador.validar(() -> primerApellido.isEmpty(),
                "El campo primer apellido está vacio\n");
        if (!primerApellido.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarSoloLetras(primerApellido),
                    "El primer apellido solo debe contener letras\n");
        }

        if (!segundoApellido.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarSoloLetras(segundoApellido),
                    "El segundo apellido solo debe contener letras\n");
        }

        validador.validar(() -> sexoBiologico.isEmpty() || sexoBiologico.equals("null"),
                "Debe seleccionar el sexo biologico\n");

        validador.validar(() -> correo.isEmpty(),
                "El campo correo esta vacio\n");
        if (!correo.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarFormatoCorreoGmail(correo),
                    "El correo debe tener un formato valido de Gmail (ejemplo@gmail.com)\n");
        }

        if (!telefono.isEmpty()) {
            boolean soloNumeros = MetodosPublicos.validarNumero(telefono);
            validador.validar(() -> !soloNumeros,
                    "El numero de telefono debe contener solo numeros\n");
            if (soloNumeros) {
                validador.validar(() -> telefono.length() != 10,
                        "El numero de telefono debe tener 10 dígitos\n");
                if (telefono.length() == 10) {
                    validador.validar(() -> telefono.charAt(0) != '3',
                            "El numero de telefono debe ser colombiano\n");
                }
            }
        }

        validador.validar(() -> contrasena.isEmpty(),
                "El campo contraseña está vacio\n");
        if (!contrasena.isEmpty()) {
            validador.validar(() -> !MetodosPublicos.validarContrasena(contrasena),
                    "La contraseña debe incluir mayuscula, minuscula, numero y caracter especial ($ @ # % & * - _ ! ?)\n");
        }

        validador.validar(() -> grupoSisben.isEmpty() || grupoSisben.equals("null"),
                "Debe seleccionar el grupo Sisben o No aplica si no estás registrado\n");

        validador.validar(() -> fechaNacimiento == null,
                "Debe seleccionar la fecha de nacimiento\n");
        if (fechaNacimiento != null) {
            byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);
            validador.agregarSiNoVacio(validacionEdadTipoDocumento(edad, idTipoIdentificacion));
        }

        return validador;
    }

    protected String validacionEdadTipoDocumento(byte edad, int idTipoIdentificacion) {
        String mensaje = "";
        if (edad > -1 && edad < 117) {
            if ((edad >= 0 && edad < 7) && idTipoIdentificacion != TI_REGISTRO_CIVIL) {
                mensaje += "Para menores de 7 años el documento debe ser Registro Civil\n";
            } else if (edad >= 7 && edad < 18 && idTipoIdentificacion != TI_TARJETA_IDENTIDAD) {
                mensaje += "Para menores de edad 7 a 17 años el documento debe ser Tarjeta de Identidad\n";
            } else if (edad > 17 && idTipoIdentificacion != TI_CEDULA) {
                mensaje += "Para mayores de edad el documento debe ser Cedula de Ciudadania\n";
            }
        } else {
            mensaje += "No se permiten personas con tal capacidad de edad\n";
        }
        return mensaje;
    }

    private boolean existeUsuarioRegistrado(String numeroIdentificacion, String correo) {
        StringBuilder mensaje = new StringBuilder();

        if (usuarioDao.validarCampoIdBs(numeroIdentificacion, "usuario", "id_usuario")) {
            mensaje.append("Ya existe un usuario registrado con ese numero de identificacion\n");
        }
        if (usuarioDao.validarCampoIdBs(correo, "usuario", "correo_electronico")) {
            mensaje.append("Ya existe un usuario registrado con ese Correo electronico\n");
        }

        if (mensaje.length() > 0) {
            mostrarError(mensaje.toString());
            return true;
        }
        return false;
    }

    private void guardarNuevoUsuario(int idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String sexoBiologico, String correo, String telefono, String contrasena,
            String grupoSisben, LocalDate fechaNacimiento) {

        byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);
        String contrasenaHashed = Hashed.hashPassword(contrasena);
        String rutaFotoParaGuardar = calcularRutaDestinoFoto();

        Usuario usu = new Usuario(
                id,
                ID_ROL_PACIENTE,
                (byte) idTipoIdentificacion,
                numeroIdentificacion,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                correo,
                contrasenaHashed,
                fechaNacimiento,
                sexoBiologico,
                telefono,
                edad,
                grupoSisben,
                true,
                rutaFotoParaGuardar);

        int idUsuarioGenerado = new RegistroPersonasDao().setAgregar(usu);

        if (idUsuarioGenerado > 0) {
            copiarFotoPerfilSiCorresponde();
            JOptionPane.showMessageDialog(rI, "Cuenta creada correctamente", "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            volverALogin();
        } else {
            mostrarError("No se pudo completar el registro Intenta de nuevo");
        }
    }

    private void volverALogin() {
        this.rI.dispose();
    }

    protected void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(rI, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }

    protected void abrirVentanaGuardar() {
        JFileChooser selector = VisorArchivos.nuevo()
                .titulo("Selecciona una foto de perfil")
                .agregarFiltro("Imagenes", "jpg", "jpeg", "png")
                .modoSeleccion(JFileChooser.FILES_ONLY)
                .construirChooser();

        VisorArchivos.abrirYSeleccionar(selector, rI,
                this::previsualizarImagenSeleccionada,
                () -> JOptionPane.showMessageDialog(rI, "No se ha seleccionado ninguna imagen"));
    }

    private void previsualizarImagenSeleccionada(File archivo) {
        try {
            ImageIcon imagenOriginal = new ImageIcon(archivo.getAbsolutePath());
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(
                    rI.previsualizacionFoto.getWidth(), rI.previsualizacionFoto.getHeight(), Image.SCALE_SMOOTH);
            rI.setImagen(new ImageIcon(imagenEscalada));
            this.archivoImagenSeleccionado = archivo;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(rI, "No se pudo cargar la imagen seleccionada");
            this.archivoImagenSeleccionado = null;
        }
    }

    private String calcularRutaDestinoFoto() {
        if (archivoImagenSeleccionado == null) {
            return RUTA_FOTO_DEFECTO;
        }
        return "fotosPerfil/" + archivoImagenSeleccionado.getName();
    }

    private void copiarFotoPerfilSiCorresponde() {
        if (archivoImagenSeleccionado == null) {
            return; 
        }
        try {
            Files.copy(
                    archivoImagenSeleccionado.toPath(),
                    FileSystems.getDefault().getPath("fotosPerfil", archivoImagenSeleccionado.getName()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
