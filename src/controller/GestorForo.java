package controller;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Foro;
import model.ForoDao;
import view.PacienteInterfaz;

public class GestorForo {

    private final PacienteInterfaz pacienteI;
    private final ForoDao forodao;
    private List<Foro> foro;
    private boolean verificador;

    public GestorForo(PacienteInterfaz pacienteI) {
        this.pacienteI = pacienteI;
        this.forodao = new ForoDao();
        this.foro = forodao.listar();
    }

    protected void procesoBtnComentarios() {
        pacienteI.habilitarBotonesMenu(pacienteI.btnComentarios);
        pacienteI.mostrarVistaComentarios();
        pacienteI.btnSugerencias.doClick();
    }

    protected void procesoBtnSugerencia() {
        estadoBotones(pacienteI.btnSugerencias, pacienteI.btnQuejas, pacienteI.btnForo);
        this.verificador = true;
        pacienteI.construirFormularioComentario();
    }

    protected void procesoBtnQuejas() {
        estadoBotones(pacienteI.btnQuejas, pacienteI.btnSugerencias, pacienteI.btnForo);
        this.verificador = false;
        pacienteI.construirFormularioComentario();
    }

    protected void procesoBtnForo() {
        estadoBotones(pacienteI.btnForo, pacienteI.btnQuejas, pacienteI.btnSugerencias);
        pacienteI.mostarPanelComentarioVacio();
        for (Foro clave : foro) {
            pacienteI.agregarAlPanelComentarios(clave.getTipoMensaje(), clave.getAsunto(),
                    clave.getNombreUsuario(), clave.getDescripcion());
        }
    }

    protected void procesoBtnEnviar() {
        String asunto = pacienteI.campoAsunto.getText().trim();
        String descripcion = pacienteI.areaDescripcion.getText().trim();
        if (asunto.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(pacienteI, "Los campos deben contener algo");
            return;
        }

        String tipoMensaje = verificador ? "Sugerencia" : "Queja";
        Foro nuevoComentario = new Foro(tipoMensaje, asunto, descripcion, pacienteI.getUsuario().getIdUsuario());
        int filasInsertadas = forodao.setAgregar(nuevoComentario);

        if (filasInsertadas > 0) {
            foro.add(0, new Foro(tipoMensaje, asunto, descripcion, pacienteI.getUsuario().getPrimerNombre()));
            JOptionPane.showMessageDialog(pacienteI, "Tu " + tipoMensaje.toLowerCase() + " fue enviada correctamente");
            pacienteI.campoAsunto.setText("");
            pacienteI.areaDescripcion.setText("");
        } else {
            JOptionPane.showMessageDialog(pacienteI, "No se pudo enviar tu " + tipoMensaje.toLowerCase() + ", intenta nuevamente");
        }
    }

    private void estadoBotones(JButton activo, JButton b2, JButton b3) {
        activo.setEnabled(false);
        b2.setEnabled(true);
        b3.setEnabled(true);
    }
}