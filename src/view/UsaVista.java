package view;

import controller.ReportesController;
import model.MetodosPublicos;


public class UsaVista {

    public static void main(String[] args) {
       ReportesInterfaz us = new ReportesInterfaz();
       ReportesController usc = new ReportesController(us);
       MetodosPublicos.abrirVentanaDisPoseOnClose(us);
       
    }

}