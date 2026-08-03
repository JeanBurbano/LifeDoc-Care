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
import model.UsuDao;
import model.Usuario;

import view.RegistroUsuariosInterfaz;

public class RegistroUsuariosController implements ActionListener {

    private static UsuDao usuarioDao = new UsuDao();
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
        initField();
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

    protected void initField() {
        MetodosPublicos.soloLetras(rI.campoPrimerNombre, 30);
        MetodosPublicos.soloLetras(rI.campoSegundoNombre, 30);
        MetodosPublicos.soloLetras(rI.campoPrimerApellido, 30);
        MetodosPublicos.soloLetras(rI.campoSegundoApellido, 30);
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
        byte idTipoIdentificacion = obtenerTipoIdentificacion();
        String numeroIdentificacion = rI.campoNumeroID.getText();
        String primerNombre = rI.campoPrimerNombre.getText().toLowerCase();
        String segundoNombre = rI.campoSegundoNombre.getText().toLowerCase();
        String primerApellido = rI.campoPrimerApellido.getText().toLowerCase();
        String segundoApellido = rI.campoSegundoApellido.getText().toLowerCase();
        String sexoBiologico = String.valueOf(rI.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = rI.datePickerNacimiento.getDate();
        String correo = rI.campoCorreo.getText().trim();
        String telefono = rI.campoTelefono.getText();
        String contrasena = String.valueOf(rI.campoContraseña.getPassword()).trim();
        String grupoSisben = String.valueOf(rI.campoSisben.getSelectedItem());

        Validador validador = validarFormularioRegistro(idTipoIdentificacion, numeroIdentificacion,
                primerNombre, segundoNombre, primerApellido, segundoApellido, sexoBiologico,
                correo, telefono, contrasena, grupoSisben, fechaNacimiento);

        validacionesEspecificas(validador);

        String errores = validador.obtenerErrores();
        if (!errores.isEmpty()) {
            mostrarAdvertencia(errores);
            return;
        }

        if (existeUsuarioRegistrado(numeroIdentificacion, correo)) {
            return;
        }

        guardarNuevoUsuario(idTipoIdentificacion, numeroIdentificacion, primerNombre, segundoNombre,
                primerApellido, segundoApellido, sexoBiologico, correo, contrasena, telefono,
                grupoSisben, fechaNacimiento);
    }

    protected byte obtenerTipoIdentificacion() {
        return (byte) rI.campoTipoId.getSelectedIndex();
    }


    protected void validacionesEspecificas(Validador validador) {
    }

    protected Validador validarFormularioRegistro(int idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String sexoBiologico, String correo, String telefono, String contrasena,
            String grupoSisben, LocalDate fechaNacimiento) {

        Validador validador = new Validador();
        validador.validar(idTipoIdentificacion < 1,
                "Debe seleccionar un tipo de documento\n");
        validador.validar(numeroIdentificacion.isEmpty(), "El campo numero de identificacion esta vacio\n");
        if (!numeroIdentificacion.isEmpty()) {
            validador.validar(!MetodosPublicos.validarTamano(numeroIdentificacion, 8, 10),
                    "El numero de identificacion debe tener entre 8 y 10 digitos\n");
        }
        validador.validar(primerNombre.isEmpty(), "El campo primer nombre esta vacio\n");
        if (!primerNombre.isEmpty()) {
            validador.validar(!MetodosPublicos.validarTamano(primerNombre, 3), "El campo primer nombre debe contener 3 o mas caracteres\n");
        }

        if (!segundoNombre.isEmpty()) {
            validador.validar(!MetodosPublicos.validarTamano(segundoNombre, 3), "El campo segundo nombre debe contener 3 o mas caracteres\n");
        }

        validador.validar(primerApellido.isEmpty(), "El campo primer apellido está vacio\n");
        if (!primerApellido.isEmpty()) {
            validador.validar(!MetodosPublicos.validarTamano(primerApellido, 3), "El campo primer apellido debe contener 3 o mas caracteres\n");
        }

        if (!segundoApellido.isEmpty()) {
            validador.validar(!MetodosPublicos.validarTamano(segundoApellido, 3), "El campo segundo apellido debe contener 3 o mas caracteres\n");
        }

        validador.validar(sexoBiologico.isEmpty(), "Debe seleccionar el sexo biologico\n");

        validador.validar(correo.isEmpty(), "El campo correo esta vacio\n");

        if (!correo.isEmpty()) {
            validador.validar(!MetodosPublicos.validarFormatoCorreoGmail(correo), "El correo debe tener un formato valido de Gmail ejemplo@gmail.com\n");
        }

        if (!telefono.isEmpty()) {
            validador.validar(telefono.length() != 10, "El numero de telefono debe tener 10 dígitos\n");
            validador.validar(telefono.charAt(0) != '3', "El numero de telefono debe ser colombiano\n");
        }

        validador.validar(contrasena.isEmpty(), "El campo contraseña está vacio\n");
        if (!contrasena.isEmpty()) {
            validador.validar(!MetodosPublicos.validarContrasena(contrasena),
                    "La contraseña debe incluir mayuscula, minuscula, numero y caracter especial ($ @ # % & * - _ ! ?)\n");
        }

        validador.validar(grupoSisben.isEmpty(), "Debe seleccionar el grupo Sisben o No aplica si no estás registrado\n");

        validador.validar(fechaNacimiento == null, "Debe seleccionar la fecha de nacimiento\n");
        if (fechaNacimiento != null) {
            byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);
            validador.agregarSiNoVacio(validacionEdadTipoDocumento(edad, idTipoIdentificacion));
        }

        return validador;
    }

    protected String validacionEdadTipoDocumento(byte edad, int idTipoIdentificacion) {
        String mensaje = "";
        if (edad > -1 && edad < 117) {
            if ((edad < 7) && idTipoIdentificacion != TI_REGISTRO_CIVIL) {
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
        boolean validador = false;
        Validador vali = new Validador();
        vali.validar(usuarioDao.validarCampoIdBs(numeroIdentificacion, "usuario", "numero_identificacion"),
                "Ya existe un usuario registrado con ese numero de identificacion\n");
        vali.validar(usuarioDao.validarCampoIdBs(correo, "usuario", "correo_electronico"),
                "Ya existe un usuario registrado con ese Correo electronico\n");
        if (vali.tieneErrores()) {
            mostrarAdvertencia(vali.obtenerErrores());
            validador = true;
        }
        return validador;
    }

    private void guardarNuevoUsuario(byte idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String sexoBiologico, String correo, String contrasena, String telefono,
            String grupoSisben, LocalDate fechaNacimiento) {

        String contrasenaHashed = Hashed.hashPassword(contrasena);
        String rutaFotoParaGuardar = calcularRutaDestinoFoto();

        boolean registroExitoso = persistirUsuario(idTipoIdentificacion, numeroIdentificacion,
                primerNombre, segundoNombre, primerApellido, segundoApellido, sexoBiologico,
                correo, contrasenaHashed, telefono, grupoSisben, fechaNacimiento, rutaFotoParaGuardar);

        if (registroExitoso) {
            copiarFotoPerfilSiCorresponde();
            JOptionPane.showMessageDialog(rI, "Cuenta creada correctamente", "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
            volverALogin();
        } else {
            mostrarAdvertencia("No se pudo completar el registro Intenta de nuevo");
        }
    }
    
    protected boolean persistirUsuario(byte idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String sexoBiologico, String correo, String contrasenaHashed, String telefono,
            String grupoSisben, LocalDate fechaNacimiento, String rutaFotoParaGuardar) {

        Usuario usu = new Usuario(
                id,
                ID_ROL_PACIENTE,
                idTipoIdentificacion,
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
                grupoSisben,
                true,
                rutaFotoParaGuardar);

        return new UsuDao().setAgregar(usu) > 0;
    }

    private void limpiar(){
        rI.campoPrimerNombre.setText("");
        rI.campoSegundoNombre.setText("");
        rI.campoPrimerApellido.setText("");
        rI.campoSegundoApellido.setText("");
        rI.campoCorreo.setText("");
        rI.campoTelefono.setText("");
        rI.campoContraseña.setText("");
        rI.campoSisben.setSelectedIndex(0);
        rI.comboSexo.setSelectedIndex(0);
        rI.campoNumeroID.setText("");
        rI.campoTipoId.setSelectedIndex(0);
        rI.datePickerNacimiento.setText("");
    }

    private void volverALogin() {
        limpiar();
        rI.dispose();
    }

    protected void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(rI, mensaje, "Datos invalidos", JOptionPane.ERROR_MESSAGE);
    }

    protected void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(rI, mensaje, "Datos invalidos", JOptionPane.WARNING_MESSAGE);
    }

    protected void abrirVentanaGuardar() {
        JFileChooser selector = VisorArchivos.nuevo()
                .titulo("Selecciona una foto de perfil")
                .agregarFiltro("Imagenes", "jpg", "jpeg", "png")
                .modoSeleccion(JFileChooser.FILES_ONLY)
                .construirChooser();

        VisorArchivos.abrirYSeleccionar(selector, rI,this::previsualizarImagenSeleccionada,() -> JOptionPane.showMessageDialog(rI, "No se ha seleccionado ninguna imagen"));
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