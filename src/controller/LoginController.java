package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MetodosPublicos;
import view.Login;

public class LoginController implements ActionListener,MetodosPublicos{
    
    Login lg = new Login();
    public LoginController(Login lg) {
        this.lg = lg;
        this.lg.bRegistar.addActionListener(this);
        this.lg.bIngresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.lg.bIngresar){
            final String user = this.lg.getName();
            final String contrasena = this.lg.getPassword();
//            if(){
//                
//            }else{
//               if(){
//                   
//               }
//               if(){
//                   
//               }
//            }
        }
    }
}
