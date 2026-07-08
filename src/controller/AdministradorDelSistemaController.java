package controller;

import java.awt.event.ActionEvent;
import view.AdministradorDelSistemaInterfaz;

public class AdministradorDelSistemaController extends PacienteController {

    AdministradorDelSistemaInterfaz adminSistem;

    public AdministradorDelSistemaController(AdministradorDelSistemaInterfaz adminSistem) {
        super(adminSistem); // Registra todos los listeners del paciente automaticamente
        adminSistem.btnRol.addActionListener(this);
        adminSistem.btnAsignarRol.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e); // Primero dejo que PacienteController maneje sus botones

        // casteo el pacienteI heredado, que SIEMPRE está disponible.
        this.adminSistem = (AdministradorDelSistemaInterfaz) pacienteI;

        if (e.getSource() == adminSistem.btnRol) {
            adminSistem.vistaUsuarios();
            adminSistem.btnRol.setEnabled(false);
            adminSistem.habilitarBotonesMenu(adminSistem.btnRol);
        }
        if (e.getSource() == adminSistem.btnAsignarRol) {
            adminSistem.vistaUsuarios();
            adminSistem.btnAsignarRol.setEnabled(false);
            adminSistem.habilitarBotonesMenu(adminSistem.btnAsignarRol);
        }
    }
}
