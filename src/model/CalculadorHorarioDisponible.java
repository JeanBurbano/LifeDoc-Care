package model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalculadorHorarioDisponible {

    private static final int DURACION_CITA_MIN = 30;

    public static String diasSemanal(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        switch (dia) {
            case MONDAY:
                return "Lunes";
            case TUESDAY:
                return "Martes";
            case WEDNESDAY:
                return "Miercoles";
            case THURSDAY:
                return "Jueves";
            case FRIDAY:
                return "Viernes";
            case SATURDAY:
                return "Sabado";
            default:
                return null; // Domingo: la clinica no trabaja
        }
    }

    //Busca dentro del horario del medico el tramo que corresponde a la fecha dada.  
    public static HorarioDia buscarDiaParaFecha(Horario horario, LocalDate fecha) {
        String nombreDia = diasSemanal(fecha);
        if (nombreDia == null || horario == null || horario.getDias() == null) {
            return null;
        }
        for (HorarioDia hd : horario.getDias()) {
            if (nombreDia.equalsIgnoreCase(hd.getDiaSemana())) {
                return hd;
            }
        }
        return null; //El medico no trabaja ese dia de la semana
    }

    //Genera los slots de 30 min del tramo salta el almuerzo y quita los ya ocupados.
    public static List<LocalTime> calcularDisponibles(HorarioDia diaHorario, List<LocalTime> horasOcupadas) {
        List<LocalTime> disponibles = new ArrayList<>();
        if (diaHorario == null) {
            return disponibles;
        }
        
        LocalTime inicio = LocalTime.parse(diaHorario.getHoraInicio());
        LocalTime fin = LocalTime.parse(diaHorario.getHoraFin());
        LocalTime descansoInicio = LocalTime.parse(diaHorario.getDescansoInicio());
        LocalTime descansoFin = LocalTime.parse(diaHorario.getDescansoFin());

        LocalTime actual = inicio;
        while (!actual.plusMinutes(DURACION_CITA_MIN).isAfter(fin)) {
            LocalTime finSlot = actual.plusMinutes(DURACION_CITA_MIN);

            boolean chocaConAlmuerzo = actual.isBefore(descansoFin) && finSlot.isAfter(descansoInicio);
            boolean ocupado = horasOcupadas != null && horasOcupadas.contains(actual);

            if (!chocaConAlmuerzo && !ocupado) {
                disponibles.add(actual);
            }
            actual = finSlot;
        }
        return disponibles;
    }
}
