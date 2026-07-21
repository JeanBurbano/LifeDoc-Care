package model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class CreadorPdf {

    private String nombre;

    public CreadorPdf(String nombre) {
        this.nombre = nombre;
    }

    public CreadorPdf() {
    }

    public void setCrearPdf(String nombre, String mensaje) {
        this.nombre = nombre;
        try {
            FileOutputStream archivo = new FileOutputStream(nombre + ".pdf");
            Document documento = new Document();

            PdfWriter.getInstance(documento, archivo);
            documento.open();
            Paragraph parrafo = new Paragraph("My first pdf \n" + mensaje);
            parrafo.setAlignment(1);
            documento.add(parrafo);
            documento.add(new Paragraph("Estos datos son del archivo" + nombre));
            documento.close();
            JOptionPane.showMessageDialog(null, "Este archivo se Creo Con exito", "", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {

            JOptionPane.showMessageDialog(null, "No se creo con exito", "", JOptionPane.ERROR_MESSAGE);

        } catch (DocumentException de) {
            JOptionPane.showMessageDialog(null, "Este documento no se creo con exito", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getVisualizarPdf() {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith("pdf") || f.isDirectory();
            }

            @Override
            public String getDescription() {

                return "pdf File";
            }
        }
        );
        int r = chooser.showOpenDialog(chooser);

        if (r == JFileChooser.APPROVE_OPTION) {

            String nombre1 = chooser.getSelectedFile().getPath();

            try {

                File path = new File(nombre1);
                Desktop.getDesktop().open(path);
            } catch (Exception eio) {
                JOptionPane.showMessageDialog(null, "tenemos problemas", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
