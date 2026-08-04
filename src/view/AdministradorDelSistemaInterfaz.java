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
import model.UsuarioPublico;

public class AdministradorDelSistemaInterfaz extends PacienteInterfaz {

    private final static String ARREGLO_COLUMNAS[] = {"Id", "Nombre Completo", "Edad", "Correo", "Numero Celular", "Rol", "Estado"};
    public JPanel panelUsuarios, panelBotones;
    public JButton btnRol, btnHabilitar, btnDesabilitar, btnLimpiar,btnUsuarioTargeta;
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

    public AdministradorDelSistemaInterfaz(String nombreInterfaz, UsuarioPublico usuario) {
        super(nombreInterfaz, usuario);
        panelUsuarios = new JPanel();
        panelUsuarios.setLayout(new BorderLayout());
        panelUsuarios.setBorder(BorderFactory.createLineBorder(COLOR_AZUL_CORPORATIVO));
        panelUsuarios.setOpaque(false);

        tabla = new JTable();
        tabla.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 14));
        tabla.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Solo una fila a la vez
        tabla.getTableHeader().setReorderingAllowed(false);

        btnRol = new JButton("Usuarios", new ImageIcon("iconsP/friends.png"));
        btnHabilitar = new JButton("Habilitar ", new ImageIcon("iconsP/accept.png"));
        MetodosPublicos.estilizarBoton(btnHabilitar, (byte) 7);
        btnDesabilitar = new JButton("Desabilitar ", new ImageIcon("iconsP/quejas.png"));
        MetodosPublicos.estilizarBoton(btnDesabilitar, (byte) 6);
        btnLimpiar = new JButton("Limpiar ", new ImageIcon("iconsP/clean.png"));
        MetodosPublicos.estilizarBoton(btnLimpiar, (byte) 1);
        btnLimpiar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUsuarioTargeta = new JButton("Tarjeta",new ImageIcon("iconsP/carta.png"));
        MetodosPublicos.estilizarBoton(btnUsuarioTargeta, (byte) 1);
        btnUsuarioTargeta.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panelBotones = new JPanel();
        this.panelBotones.setOpaque(false);
        this.panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        this.panelBotones.add(Box.createVerticalStrut(170));
        this.panelBotones.add(btnHabilitar);
        this.panelBotones.add(Box.createVerticalStrut(20));
        this.panelBotones.add(btnDesabilitar);
        this.panelBotones.add(Box.createVerticalStrut(20));
        this.panelBotones.add(btnLimpiar);
        this.panelBotones.add(Box.createVerticalStrut(20));
        this.panelBotones.add(btnUsuarioTargeta);
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

    public void cargarUsuarios(List<UsuarioPublico> usuarios) {
        mDefaultTableModel.setRowCount(0); //limpia filas no columnas
        for (UsuarioPublico p : usuarios) {
            String nombreCompleto = p.getPrimerNombre()+" "+p.getPrimerApellido();
            String rol = p.getRol();
            String estado = p.isEstado() ? "Activo" : "Deshabilitado";
            mDefaultTableModel.addRow(new Object[]{
                p.getIdUsuario(), nombreCompleto, p.getEdad(), p.getCorreo(),
                p.getNumeroCelular(), rol, estado
            });
        }
    }
}
