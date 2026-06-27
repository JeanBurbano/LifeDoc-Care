/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Font;
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
        this.btnInventarioMedicamentos = new JButton("💊 Inventario de Medicamentos");
        super.agregarBotonCuerpo1(btnPersonalCentro);
        super.agregarBotonCuerpo1(btnregistrarUsuario);
        super.agregarBotonCuerpo1(btnInventarioMedicamentos);

    }

    @Override
    protected void habilitarBotonesMenu(JButton botonActivo) {
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
        tituloPersonalRegistrado.setForeground(Color.red);
    }

}
