package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import view.Login;

public class LoginController implements ActionListener, MetodosPublicos {

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
            if ((!id.isEmpty() && !contrasena.isEmpty()) && id.matches("\\d{8,10}") && contrasena.matches("(?=.{8,10}$)[a-zA-Z0-9]+([/$#?!%][a-zA-Z0-9]*)*")) {
                JOptionPane.showMessageDialog(lg, "sesion iniciada");
            } else if (id.isEmpty() && contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(lg, "Los dos campos son obligatorios");
            } else {
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "El Campo Id es obligatorio");
                } else if (contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "El Campo De Constrasena es obligario");
                }
                if (!id.isEmpty() && Pattern.compile("[^0-9]").matcher(id).find()) {
                    JOptionPane.showMessageDialog(lg, "El campo id contiene caracteres no validos");
                }
                if (!id.isEmpty() && id.length() < 8 || id.length() > 10) {
                    JOptionPane.showMessageDialog(lg, "El campo id no cumple con longitud de minimo como 8 y maximo como 10");
                }
                if (!contrasena.isEmpty() && Pattern.compile("[^a-zA-Z0-9/$#?!%]").matcher(contrasena).find()) {
                    JOptionPane.showMessageDialog(lg, "La contrasena tiene caracteres no validos");
                }
                if (!contrasena.isEmpty() && Pattern.compile("[/$#?!%]{2}").matcher(contrasena).find()) {
                    JOptionPane.showMessageDialog(lg, "La contrasena No se permiten simbolos consecutivos");
                }
                if (!contrasena.isEmpty() && contrasena.length() < 8 || contrasena.length() > 10) {
                    JOptionPane.showMessageDialog(lg, "La contrasena debe ser como minimo 8 caracteres y maximo 10");
                }
            }
        }
    }
}
