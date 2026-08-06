package controller;

import java.awt.event.ActionEvent;
import view.ReportesInterfaz;
import java.awt.event.ActionListener;
import model.GestorEstadisticas;
import javax.swing.JPanel;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import model.EstadisticaCitas;
import model.EstadisticasCitasModel;
import model.UsuarioPublico;
import model.UsuarioPublicoDao;

public class ReportesController implements ActionListener {

    protected ReportesInterfaz rI = new ReportesInterfaz();
    int totalAgendadas, totalConfirmadas, totalCanceladas, totalReagendadas, totalAtendidas;
    private UsuarioPublicoDao usuDao;
    List<UsuarioPublico> listausuarios;

    public ReportesController(ReportesInterfaz rI) {
        this.rI = rI;
        agregarActionListener();
        usuDao = new UsuarioPublicoDao();
    }

    public void agregarActionListener() {
        rI.btnCerrar.addActionListener(this);
        rI.btnUsuSisben.addActionListener(this);
        rI.btnInfoCitas.addActionListener(this);
        rI.btnAtencionMedica.addActionListener(this);
        rI.btnFinancieros.addActionListener(this);
    }

    public void procesoBtnInfoCitas() {
        rI.habilitarBotonesMenu(rI.btnInfoCitas);

        new SwingWorker<Void, Void>() {
            int[] totales;
            List<EstadisticaCitas> lista;

            @Override
            protected Void doInBackground() {
                totales = new GestorEstadisticas().obtenerTotales();
                lista = new EstadisticasCitasModel().listar();
                return null;
            }

            @Override
            protected void done() {
                totalAgendadas = totales[0];
                totalConfirmadas = totales[1];
                totalCanceladas = totales[2];
                totalReagendadas = totales[3];
                totalAtendidas = totales[4];

                JPanel panelContenido = rI.crearPanelConValores(
                        String.valueOf(totalAgendadas), String.valueOf(totalConfirmadas),
                        String.valueOf(totalCanceladas), String.valueOf(totalReagendadas),
                        String.valueOf(totalAtendidas));

                JPanel panelGrafico = rI.crearGraficoPorDia(lista);
                rI.cargarGraficosCitasCuerpo2(panelContenido, panelGrafico);
            }
        }.execute();
    }

    public void procesobtnUsuarioSisben() {

        new SwingWorker<Void, Void>() {

            int cA = 0, cB = 0, cC = 0, cD = 0, cN = 0;

            @Override
            protected Void doInBackground() {

                listausuarios = new ArrayList<>(usuDao.listar());

                for (UsuarioPublico usu : listausuarios) {
                    switch (usu.getSisben()) {
                        case "A":
                            cA++;
                            break;
                        case "B":
                            cB++;
                            break;
                        case "C":
                            cC++;
                            break;
                        case "D":
                            cD++;
                            break;
                        default:
                            cN++;
                            break;
                    }
                }

                return null;
            }

            @Override
            protected void done() {
                rI.cargarGraficoSisben(cA, cB, cC, cD, cN);
            }

        }.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rI.btnCerrar) {
            rI.dispose();
            return;
        }
        if (e.getSource() == rI.btnUsuSisben) {
            rI.habilitarBotonesMenu(rI.btnUsuSisben);
            procesobtnUsuarioSisben();
            return;
        }
        if (e.getSource() == rI.btnInfoCitas) {
            procesoBtnInfoCitas();
            return;
        }
        if (e.getSource() == rI.btnAtencionMedica) {
            rI.habilitarBotonesMenu(rI.btnAtencionMedica);
            rI.vaciarPanelCuerpo2();
            JOptionPane.showMessageDialog(null, "Esta en proceso");
            return;
        }
        if (e.getSource() == rI.btnFinancieros) {
            rI.habilitarBotonesMenu(rI.btnFinancieros);
            rI.vaciarPanelCuerpo2();
            JOptionPane.showMessageDialog(null, "Esta en proceso");
            return;
        }
    }
}
