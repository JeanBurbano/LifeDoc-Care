/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Locale;
import javax.swing.BorderFactory;

/**
 *
 * @author lunaa
 */
public class FechaCalendarioEstilizar {
    /**
     * Crea un selector de fecha (DatePicker de LGoodDatePicker) ya
     * estilizado y restringido para solo permitir fechas desde hoy en
     * adelante (útil para fechas de vencimiento, citas, etc.).
     *
     * @return el DatePicker listo para usarse
     */
    public static DatePicker crearDatePickerEstilizado() {
        DatePickerSettings settings = new DatePickerSettings(new Locale("es")); // configuración en español
        settings.setFormatForDatesCommonEra("yyyy-MM-dd"); // formato de fecha mostrado al usuario
        settings.setAllowKeyboardEditing(false); // solo se puede elegir la fecha con el calendario, no escribirla
        settings.setVisibleDateTextField(true); // se muestra el campo de texto junto al botón de calendario

        // Colores para que combine con el resto del formulario
        settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, Color.WHITE); // fondo del panel del calendario
        settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundNormalDates, Color.WHITE); // fondo de los días normales
        settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, PacienteInterfaz.COLOR_VERDE_ACENTO); // día seleccionado en verde
        settings.setColorBackgroundWeekdayLabels(Color.WHITE, false); // fondo blanco para los nombres de los días
        settings.setFontValidDate(new Font("Arial", Font.PLAIN, 13)); // fuente de los días válidos

        DatePicker datePicker = new DatePicker(settings); // se construye el DatePicker con la configuración anterior

        // El rango de fechas solo puede aplicarse DESPUÉS de construir el DatePicker
        LocalDate hoy = LocalDate.now(); // fecha actual del sistema
        settings.setDateRangeLimits(hoy, null); // solo permite seleccionar desde hoy en adelante (sin límite superior)

        // Estilo del campo de texto y el botón de calendario
        datePicker.getComponentDateTextField().setFont(new Font("Arial", Font.PLAIN, 15));
        datePicker.getComponentDateTextField().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        datePicker.getComponentDateTextField().setBackground(Color.WHITE);
        datePicker.getComponentToggleCalendarButton().setBackground(Color.WHITE);
        datePicker.getComponentToggleCalendarButton().setBorder(
                BorderFactory.createLineBorder(PacienteInterfaz.COLOR_VERDE_ACENTO, 1, true));

        return datePicker; // listo para agregarse al formulario
    }
}
