package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.MetodosPublicos;
import model.Paciente;

public class AdministradorDelSistemaInterfaz extends PacienteInterfaz {

    private final static String ARREGLO_COLUMNAS[] = {"Id", "Nombre Completo", "Edad", "Correo", "Numero Celular", "Rol","Estado"};
    public JPanel panelUsuarios, panelBotones;
    public JButton btnRol, btnHabilitar, btnDesabilitar, btnLimpiar;
    public DefaultTableModel mDefaultTableModel;
    public JTable tabla;

    private void procesoTabla() {
        for (String clave : ARREGLO_COLUMNAS) {
            mDefaultTableModel.addColumn(clave);
        }
        this.tabla.setModel(mDefaultTableModel);
        JScrollPane scrollPane = new JScrollPane(tabla);
        this.panelUsuarios.add(scrollPane, BorderLayout.CENTER);
    }

    public AdministradorDelSistemaInterfaz(String nombreInterfaz, Paciente usuario) {
        super(nombreInterfaz, usuario);
        this.panelUsuarios = new JPanel();
        this.panelUsuarios.setLayout(new BorderLayout());
        this.panelUsuarios.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        this.panelUsuarios.setOpaque(false);

        this.tabla = new JTable();
        this.tabla.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        this.tabla.setFont(new Font("Tahoma", Font.PLAIN, 12));
        this.tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Solo una fila a la vez
        this.tabla.getTableHeader().setReorderingAllowed(false);

        this.btnRol = new JButton("Usuarios", new ImageIcon("iconsP/friends.png"));
        this.btnHabilitar = new JButton("Habilitar ", new ImageIcon("iconsP/accept.png"));
        MetodosPublicos.estilizarBoton(btnHabilitar, (byte) 7);
        this.btnDesabilitar = new JButton("Desabilitar ", new ImageIcon("iconsP/quejas.png"));
        MetodosPublicos.estilizarBoton(btnDesabilitar, (byte) 6);
        this.btnLimpiar = new JButton("Limpiar ", new ImageIcon("iconsP/clean.png"));
        MetodosPublicos.estilizarBoton(btnLimpiar, (byte) 1);
        this.btnLimpiar.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.panelBotones = new JPanel();
        this.panelBotones.setOpaque(false);
        this.panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        this.panelBotones.add(Box.createVerticalStrut(170));
        this.panelBotones.add(btnHabilitar);
        this.panelBotones.add(Box.createVerticalStrut(20));
        this.panelBotones.add(btnDesabilitar);
        this.panelBotones.add(Box.createVerticalStrut(20));
        this.panelBotones.add(btnLimpiar);
        super.agregarBotonCuerpo1(btnRol);

        mDefaultTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        procesoTabla();
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    public void vistaUsuarios() {
        MetodosPublicos.vaciarPanel(panelUsuarios);
        MetodosPublicos.vaciarPanel(cuerpo2);
        super.cuerpo2.setLayout(new BorderLayout(20, 20));
        super.cuerpo2.setBorder(new EmptyBorder(40, 40, 40, 40));

        JScrollPane scrollPane = new JScrollPane(tabla);
        this.panelUsuarios.add(scrollPane, BorderLayout.CENTER);
        super.cuerpo2.add(panelBotones, BorderLayout.WEST);
        super.cuerpo2.add(panelUsuarios, BorderLayout.CENTER);

        MetodosPublicos.refrescarVentana(panelUsuarios);
        MetodosPublicos.refrescarVentana(cuerpo2);
    }

    @Override
    public void habilitarBotonesMenu(JButton botonActivo) {
        super.habilitarBotonesMenu(botonActivo);
        if (botonActivo != btnRol && !btnRol.isEnabled()) {
            this.btnRol.setEnabled(true);
        }
    }

    public void cargarUsuarios(List<Paciente> usuarios) {
        mDefaultTableModel.setRowCount(0); //Limpia filas no columnas
        for (Paciente p : usuarios) {
            String segundoNombre = (p.getSegundoNombre() == null) ? "" : p.getSegundoNombre();
            String segundoApellido = (p.getSegundoApellido() == null) ? "" : p.getSegundoApellido();
            String nombreCompleto = String.join(" ",
                    p.getPrimerNombre(), segundoNombre, p.getPrimerApellido(), segundoApellido)
                    .replaceAll("\\s+", " ").trim();
            String rol = (p.getId_rol() == 1) ? "Administrador del sistema" : p.getId_rol() == 2 ? "Administrador del centro"
                    : p.getId_rol() == 3 ? "Medico" : p.getId_rol() == 4 ? "Operario" : "Paciente";
            String estado = p.getEstado() ? "Activo" : "Deshabilitado";
            mDefaultTableModel.addRow(new Object[]{
                p.getIdUsuario(), nombreCompleto, p.getEdad(), p.getCorreoElectronico(),
                p.getNumeroTelefonico(), rol, estado
            });
        }
    }
}
