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
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
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

    private RegistroUsuariosInterfaz rI;
    private String rutaImagen;

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
        rutaImagen = "fotosPerfil/fotoDefecto.png";
    }

    private String iE(boolean condicionInvalida, String valor, String mensaje) {
        return condicionInvalida ? valor + mensaje : valor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rI.btnRegistrarse) {
            registrar();
            return;
        }

        if (e.getSource() == rI.btnVolverA) {
            volverALogin();
            return;
        }
        if (e.getSource() == rI.btnSeleccionarFoto) {
            abrirVentanaGuardar();
            return;
        }
    }

    private String validacionesTodosCampoCompleto(int idTipoIdentificacion, String numeroIdentificacion, String primerNombre,
            String primerApellido, String sexoBiologico, String correo, String contrasena, String grupoSisben, LocalDate fechaNacimiento) {
        String mensaje = "";
        mensaje = iE(idTipoIdentificacion < 0, mensaje, "Debe seleccionar un tipo de documento\n");
        mensaje = iE(numeroIdentificacion.isEmpty(), mensaje, "El campo numero de identificacion esta vacio\n");
        mensaje = iE(primerNombre.isEmpty(), mensaje, "El campo primer nombre esta vacio\n");
        mensaje = iE(primerApellido.isEmpty(), mensaje, "El campo primer apellido esta vacio\n");
        mensaje = iE(sexoBiologico.isEmpty() || sexoBiologico.equals("null"), mensaje, "Debe seleccionar el sexo biologico\n");
        mensaje = iE(correo.isEmpty(), mensaje, "El campo correo esta vacio\n");
        mensaje = iE(contrasena.isEmpty(), mensaje, "El campo contraseña esta vacio\n");
        mensaje = iE(grupoSisben.isEmpty() || grupoSisben.equals("null"), mensaje, "Debe seleccionar el grupo Sisben\n");
        mensaje = iE(fechaNacimiento == null, mensaje, "Debe seleccionar la fecha de nacimiento\n");
        return mensaje;
    }

    private String validacionesCriterios(String primerNombre, String segundoNombre, String primerApellido,
            String segundoApellido, String numeroIdentificacion, String correo, String telefono, String contrasena) {
        String mensaje = "";

        mensaje = iE(!MetodosPublicos.validarSoloLetras(primerNombre), mensaje,
                "El primer nombre solo debe contener letras\n");
        mensaje = iE(!segundoNombre.isEmpty() && !MetodosPublicos.validarSoloLetras(segundoNombre), mensaje,
                "El segundo nombre solo debe contener letras\n");
        mensaje = iE(!MetodosPublicos.validarSoloLetras(primerApellido), mensaje,
                "El primer apellido solo debe contener letras\n");
        mensaje = iE(!segundoApellido.isEmpty() && !MetodosPublicos.validarSoloLetras(segundoApellido), mensaje,
                "El segundo apellido solo debe contener letras\n");
        mensaje = iE(!MetodosPublicos.validarNumero(numeroIdentificacion)
                || !MetodosPublicos.validarTamano(numeroIdentificacion, 8, 10), mensaje,
                "El numero de identificacion debe tener entre 8 y 10 digitos\n");
        mensaje = iE(!MetodosPublicos.validarFormatoCorreoGmail(correo), mensaje,
                "El correo debe tener un formato valido de Gmail (ejemplo@gmail.com)\n");

        if (!telefono.isEmpty()) {
            if (!MetodosPublicos.validarNumero(telefono)) {
                mensaje += "El numero de telefono debe contener solo numeros\n";
            } else if (telefono.length() != 10) {
                mensaje += "El numero de telefono debe tener 10 digitos\n";
            } else if (!String.valueOf(telefono.charAt(0)).equals("3")) {
                mensaje += "El numero de telefono debe ser colombiano\n";
            }
        }

        mensaje = iE(!MetodosPublicos.validarContrasena(contrasena), mensaje,
                "La contraseña debe incluir mayuscula, minuscula, numero y caracter especial ($ @ # % & * - _ ! ?)\n");
        mensaje = iE(rI.campoSisben.getSelectedIndex() == 0, mensaje,
                "Selecciona el grupo Sisben (o 'No aplica' si no estas registrado)\n");

        return mensaje;
    }

    protected String validacionEdadTipoDocumento(byte edad, int idTipoIdentificacion) {
        String mensaje = "";
        if (edad > -1 && edad < 117) {
            if ((edad >= 0 && edad < 7) && idTipoIdentificacion != TI_REGISTRO_CIVIL) {
                mensaje += "Para menores de 7 años el documento debe ser Registro Civil\n";
            } else if (edad >= 7 && edad < 18 && idTipoIdentificacion != TI_TARJETA_IDENTIDAD) {
                mensaje += "Para menores de edad (7 a 17 años) el documento debe ser Tarjeta de Identidad\n";
            } else if (edad > 17 && idTipoIdentificacion != TI_CEDULA) {
                mensaje += "Para mayores de edad el documento debe ser Cedula de Ciudadania\n";
            }
        } else {
            mensaje += "No se permiten personas con tal capacidad de edad\n";
        }
        return mensaje;
    }

    private void registrar() {
        int idTipoIdentificacion = rI.campoTipoId.getSelectedIndex();
        String numeroIdentificacion = rI.campoNumeroID.getText().trim();
        String primerNombre = rI.campoPrimerNombre.getText().trim();
        String primerApellido = rI.campoPrimerApellido.getText().trim();
        String sexoBiologico = String.valueOf(rI.comboSexo.getSelectedItem());
        LocalDate fechaNacimiento = rI.datePickerNacimiento.getDate();
        String correo = rI.campoCorreo.getText().trim();
        String telefono = rI.campoTelefono.getText().trim();
        String contrasena = new String(rI.campoContraseña.getPassword());
        String grupoSisben = String.valueOf(rI.campoSisben.getSelectedItem());

        boolean validador = ((idTipoIdentificacion > -1)
                && (!numeroIdentificacion.isEmpty() && !primerNombre.isEmpty()
                && !primerApellido.isEmpty() && !sexoBiologico.isEmpty() && !correo.isEmpty()
                && !contrasena.isEmpty() && !grupoSisben.isEmpty())
                && fechaNacimiento != null);

        if (validador) {
            String segundoNombre = rI.campoSegundoNombre.getText().trim();
            String segundoApellido = rI.campoSegundoApellido.getText().trim();
            byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);
            System.out.println(edad);
            String mensaje2 = validacionesCriterios(primerNombre, segundoNombre, primerApellido,
                    segundoApellido, numeroIdentificacion, correo, telefono, contrasena);
            mensaje2 += validacionEdadTipoDocumento(edad, idTipoIdentificacion);
            if (!(mensaje2.isEmpty())) {
                mostrarError(mensaje2);
                return;
            }
            if (usuarioDao.validarCampoIdBs(numeroIdentificacion, "usuario", "id_usuario")) {
                mensaje2 += "Ya existe un usuario registrado con ese numero de identificacion\n";

            }
            if (usuarioDao.validarCampoIdBs(correo, "usuario", "correo_electronico")) {
                mensaje2 += "Ya existe un usuario registrado con ese Correo electronico\n";
            }
            if (!(mensaje2.isEmpty())) {
                mostrarError(mensaje2);
                return;
            }
            String sisben = String.valueOf(rI.campoSisben.getSelectedItem());
            String contrasenaHashed = Hashed.hashPassword(contrasena);
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
                    sisben,
                    true,
                    rutaImagen);
            int idUsuarioGenerado = new RegistroPersonasDao().setAgregar(usu);
            if (idUsuarioGenerado > 0) {
                JOptionPane.showMessageDialog(rI, "Cuenta creada correctamente", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                volverALogin();
            } else {
                mostrarError("No se pudo completar el registro. Intenta de nuevo");
            }
        } else {
            String mensaje = validacionesTodosCampoCompleto(idTipoIdentificacion, numeroIdentificacion, primerNombre,
                    primerApellido, sexoBiologico, correo, contrasena, grupoSisben, fechaNacimiento);
            mostrarError(mensaje);
        }
    }

    private void volverALogin() {
        this.rI.dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(rI, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }

    protected void abrirVentanaGuardar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagenes", "jpg", "png");
        JFileChooser selectorArchivo = new JFileChooser("C:\\\\");
        selectorArchivo.setMultiSelectionEnabled(false);
        selectorArchivo.setFileFilter(filtro);
        selectorArchivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        selectorArchivo.setDialogTitle("Selecciona un archivo");
        int resultado = selectorArchivo.showOpenDialog(rI);
        if (resultado == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(rI, "No se ha seleccionado ninguna imagen");
            return;
        }
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File nombreArchivo = selectorArchivo.getSelectedFile();
            if (nombreArchivo == null || nombreArchivo.getName().isEmpty()) {
                JOptionPane.showMessageDialog(rI, "Nombre de archivo incorrecto");
            } else {
                rutaImagen = String.valueOf(nombreArchivo.getParent()) + "/" + nombreArchivo.getName();
                try {
                    ImageIcon imagenOriginal = new ImageIcon(rutaImagen);
                    Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(rI.previsualizacionFoto.getWidth(),
                            rI.previsualizacionFoto.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
                    rI.setImagen(iconoEscalado);
                    Files.copy(FileSystems.getDefault().getPath(rutaImagen),
                            FileSystems.getDefault().getPath("fotosPerfil/" + nombreArchivo.getName()),
                            StandardCopyOption.REPLACE_EXISTING);
                    rutaImagen = "fotosPerfil/" + nombreArchivo.getName();
                } catch (Exception e) {
                    e.printStackTrace();
                    rutaImagen = "";
                }
            }
        }

    }
}
