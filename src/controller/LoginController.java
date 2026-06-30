package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.MetodosPublicos;
import view.Login;

public class LoginController implements ActionListener {
    
    Login lg;
    
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
            final boolean validar = (MetodosPublicos.validarTamano(id, 8, 10) &&
                    MetodosPublicos.validarid(id)) &&
                    (MetodosPublicos.validarTamano(contrasena, 8) && 
                    MetodosPublicos.validarContrasena(contrasena));
            
            if (validar) {
                JOptionPane.showMessageDialog(lg, "iniciar sesion");
                contrasena=null;
            } else {
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "Campo id es obligatorio");
                } else {
                    if (!MetodosPublicos.validarTamano(id, 8, 10)) {
                        JOptionPane.showMessageDialog(lg, "Campo id debe contener 8 o 10 caracteres");
                    }
                    if (!MetodosPublicos.validarid(id)) {
                        JOptionPane.showMessageDialog(lg, "Campo id contiene caracteres invalidos");
                    }
                }
                if (contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(lg, "Campo Contrasena es obligatorio");
                } else {
                    if(!MetodosPublicos.validarTamano(contrasena, 8)){
                        JOptionPane.showMessageDialog(lg, "El campo contrasena debe dcontener como minimo 8 caracteres");
                    }
                    if(!MetodosPublicos.validarContrasena(contrasena)){
                        JOptionPane.showMessageDialog(lg, "La contrasena debe de cumplir con estos parametros\n"
                                + "Minimo 8 caracteres\n"
                                + "1 Mayuscula,\n"
                                + "1 Minuscula\n"
                                + "1 Numero\n"
                                + "1 Simbolos permitidos @, #, $, %, &, *, -, _, !, ?");
                    }
                }
            }
        }
    }
}
