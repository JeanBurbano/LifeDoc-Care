package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import static model.MetodosPublicos.refrescarVentana;


public class OperarioInterfaz extends PacienteInterfaz{

//    Botones del menu principal
    
    public JButton btnAgendarCitas;
    public JButton btnPagos;
    public JButton btnComentarios;
    public JButton btnMisCitas;
    public JButton btnNotificaciones;
    public JButton btnConsultas;
    
    public JButton btnAgendar;      //Boton para agendar la cita
    public JPanel panelInfoCitas;   //Panel donde de muestran las citas
    
//    Botones de pagos
    public JButton btnVerFactura;
    public JButton btnProcesarPago;
    
    public OperarioInterfaz(String nombre, String nombreInterfaz, String rutaFotoP){
        super(nombre, nombreInterfaz, rutaFotoP);
        
        inicializarComponentesOperario();
        agregarBotonesMenuOperario();
//        
//        Vista inicial
        mostrarVistaPrincipalOperario();
        
    }
    
    private void inicializarComponentesOperario(){
        this.panelInfoCitas = new JPanel();
        this.panelInfoCitas.setLayout((new BoxLayout(panelInfoCitas, BoxLayout.Y_AXIS)));
        this.panelInfoCitas.setOpaque(false);
        
        this.btnAgendarCitas = new JButton("❤️ Agendar Cita");
        estilizarBoton(btnAgendar, (byte) 3);
        
        this.btnAgendarCitas = new JButton("📅 Agendar Citas");
        this.btnPagos = new JButton("💰 Pagos");
        this.btnComentarios = new JButton("🗨️ Comentarios");
        this.btnMisCitas = new JButton("🫂 Mis Citas");
        this.btnNotificaciones = new JButton("✉️ Notificaciones");
        this.btnConsultas = new JButton("📋 Consultas");
        
        this.btnVerFactura = new JButton("📄 Ver factura");
        this.btnProcesarPago = new JButton("💳 Procesar Pago");
        
    } 
    
    private void agregarBotonesMenuOperario(){
        
        cuerpo1.removeAll();

        agregarBotonCuerpo1(btnAgendarCitas);
        agregarBotonCuerpo1(btnPagos);
        agregarBotonCuerpo1(btnComentarios);
        agregarBotonCuerpo1(btnMisCitas);
        agregarBotonCuerpo1(btnNotificaciones);
        agregarBotonCuerpo1(btnConsultas);

        refrescarVentana(cuerpo1);
    }
    
    public void mostrarVistaPrincipalOperario(){
        
    }
    
}
