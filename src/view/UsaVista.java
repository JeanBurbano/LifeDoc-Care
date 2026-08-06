package view;

import controller.ReportesController;
import model.MetodosPublicos;
import model.Paciente;
import model.PacienteDao;


public class UsaVista {

    public static void main(String[] args) {
       ReportesInterfaz us = new ReportesInterfaz();
       ReportesController usc = new ReportesController(us);
       MetodosPublicos.abrirVentanaDisPoseOnClose(us);
//       String idMedico = "11111111";
//       Paciente pacienteBuscado = new PacienteDao().buscarPorId("123");
//       pacienteBuscado.getNumeroIdentificacion();
//       if(idMedico.equals( pacienteBuscado.getNumeroIdentificacion())){
//           
//       }
    }
}