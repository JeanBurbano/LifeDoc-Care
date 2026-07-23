/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author lunaa
 */
public class SeleccionImagenes {
    
    /**
     * Copia el archivo elegido en el JFileChooser a la carpeta
     * imagenesMedicamento del proyecto, renombrándolo con el número de registro
     * sanitario para que sea fácil de ubicar.
     */
    public String copiarImagenMedicamento(File origen, String nRegistroSanitario) {
        try {
            String extension = origen.getName().substring(origen.getName().lastIndexOf('.'));
            String nombreArchivo = nRegistroSanitario + extension;
            File destino = new File("imagenesMedicamento/" + nombreArchivo);
            java.nio.file.Files.copy(origen.toPath(), destino.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return "imagenesMedicamento/" + nombreArchivo;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar la imagen: " + ex.getMessage());
            return "imagenesMedicamento/fotoDefecto.png";
        }
    }
}
