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

    public static void constructorCreadorPdf(String nombre, String mensaje) {
        CreadorPdf creadorPdf = new CreadorPdf();
        creadorPdf.setCrearPdf(nombre, mensaje);
    }

    public void setCrearPdf(String nombre, String mensaje) {
        this.nombre = nombre;
        //Abrir ventanita esa de java para elejir donde guardar
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar PDF");
        chooser.setSelectedFile(new File(nombre + ".pdf"));
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".pdf") || f.isDirectory();
            }
            @Override
            public String getDescription() {
                return "Archivos PDF (*.pdf)";
            }
        });

        int resultado = chooser.showSaveDialog(null);
        if (resultado != JFileChooser.APPROVE_OPTION) {
            return; //El usuario cancelo
        }

        File archivo = chooser.getSelectedFile();
        //Aqui vamos a asegurar que usuario tenga esa cosa que es como una extencion pdf
        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }

        try {
            FileOutputStream archivoOut = new FileOutputStream(archivo);
            Document documento = new Document();
            PdfWriter.getInstance(documento, archivoOut);
            documento.open();
            Paragraph parrafo = new Paragraph("Historial Clínico\n" + mensaje);
            parrafo.setAlignment(1);
            documento.add(parrafo);
            documento.close();
            JOptionPane.showMessageDialog(null, "PDF creado con éxito en:\n" + archivo.getAbsolutePath(), "", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo crear el archivo", "", JOptionPane.ERROR_MESSAGE);
        } catch (DocumentException de) {
            JOptionPane.showMessageDialog(null, "Error al generar el documento", "", JOptionPane.ERROR_MESSAGE);
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
