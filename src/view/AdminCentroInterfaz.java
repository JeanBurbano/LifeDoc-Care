/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.MetodosPublicos;

/**
 *
 * @author lunaa
 */
public class AdminCentroInterfaz extends PacienteInterfaz {

    public JButton btnPersonalCentro, btnInventarioMedicamentos, btnregistrarUsuario;
    public JPanel personalC, InventarioM;

    public AdminCentroInterfaz(String nombrePersona, String nombreInterfaz, String url) {
        super(nombrePersona, nombreInterfaz, url);
        this.btnPersonalCentro = new JButton("👥 Personal del Centro");
        this.btnregistrarUsuario = new JButton("Registrar Personal");
        super.estilizarBoton(btnregistrarUsuario, (byte)5);
        this.btnInventarioMedicamentos = new JButton("💊 Inventario de Medicamentos");
        super.agregarBotonCuerpo1(btnPersonalCentro);
        super.agregarBotonCuerpo1(btnInventarioMedicamentos);
        this.personalC = new JPanel();
        this.personalC.setLayout(new FlowLayout(FlowLayout.LEFT));
        personalC.setOpaque(false);

    }

    
    public void habilitarBotones(JButton botonActivo) {
        if (botonActivo != btnPersonalCentro && !btnPersonalCentro.isEnabled()) {
            this.btnPersonalCentro.setEnabled(true);
        }
        if (botonActivo != btnInventarioMedicamentos && !btnInventarioMedicamentos.isEnabled()) {
            this.btnInventarioMedicamentos.setEnabled(true);
        }
    }

    public void mostrarVistaPersonalCentro() {
        MetodosPublicos.vaciarPanel(cuerpo2);
        MetodosPublicos.vaciarPanel(personalC);
        
        JLabel tituloPersonalRegistrado = new JLabel("Personal del Centro Registrado");
        
        tituloPersonalRegistrado.setFont(new Font("arial", Font.BOLD, 20));
        tituloPersonalRegistrado.setForeground(PacienteInterfaz.COLOR_AZUL_CORPORATIVO);
        this.personalC.add(tituloPersonalRegistrado);
        this.personalC.add(Box.createHorizontalStrut(10));
        this.personalC.add(btnregistrarUsuario);
            personalC.setPreferredSize(new Dimension(Short.MAX_VALUE,50));
        super.cuerpo2.add(personalC, BorderLayout.WEST);
        MetodosPublicos.refrescarVentana(personalC);
        MetodosPublicos.refrescarVentana(cuerpo2);
        
    }

}
