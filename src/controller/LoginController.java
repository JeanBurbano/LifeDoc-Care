package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import view.Login;

public class LoginController implements ActionListener {

    Login lg = new Login();

    public LoginController(Login lg) {
        this.lg = lg;
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.lg.bIngresar) {
            String id = this.lg.getId();
            String contrasena = this.lg.getPassword();
            //[solo numeros]{minimo,maximo}(?= .*Almenos una mayuscula)(?=.*almenos uno de los simbolos permitidos)[A-Za-z0-9/$#?!%]{minimo,infinito}
            if ((!id.isEmpty() && !contrasena.isEmpty()) && id.matches("[0-9 ]{8,10}") && contrasena.matches("(?=.*[A-Z])(?=.*[/$#?!%])[A-Za-z0-9/$#?!%]{8,}")) {
                JOptionPane.showMessageDialog(lg, "sesion iniciada");
            } else if (id.isEmpty() && contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(lg, "Los dos campos son obligatorios");
            } else {
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "El Campo Id es obligatorio");
                } else if (contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "El Campo De Constrasena es obligario");
                }
                if (!MetodosPublicos.validarId(id)) {
                    JOptionPane.showMessageDialog(lg, "El campo id contiene caracteres no validos");
                }
                if (MetodosPublicos.idValidarLongitud(id)) {
                    JOptionPane.showMessageDialog(lg, "El campo id no cumple con longitud de minimo como 8 y maximo como 10");
                }
                if (MetodosPublicos.contrasenaCaracteresInvalidos(contrasena)) {
                    JOptionPane.showMessageDialog(lg, "La contrasena tiene caracteres no validos");
                }
                if (MetodosPublicos.validarLongitudCt(contrasena)) {
                    JOptionPane.showMessageDialog(lg, "La contrasena debe ser como minimo 8 caracteres");
                }
                if (MetodosPublicos.validarObligatoriedad(contrasena)) {
                    JOptionPane.showMessageDialog(lg, "La contrasena debe de cumplir con una minuscula una mayuscula un numero y un de los simbolos permitidos /$#?!%");
                }
            }
        }
    }
}
