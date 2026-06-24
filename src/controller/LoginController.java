package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    }
}
