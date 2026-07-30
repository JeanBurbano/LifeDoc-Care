package controller;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class VisorArchivos {
    
    private VisorArchivos() {
        
    }
    
    public static Builder nuevo() {
        return new Builder();
    }
    
    public static File abrirYSeleccionar(JFileChooser selector, Component padre, Consumer<File> alSeleccionar, Runnable siCancela) {
        int r = selector.showOpenDialog(padre);
        if (r != JFileChooser.APPROVE_OPTION) {
            if (siCancela != null) {
                siCancela.run();
            }
            return null;
        }
        File archivo = selector.getSelectedFile();
        if (archivo == null || archivo.getName().isEmpty()) {
            if (alSeleccionar != null) {
                alSeleccionar.accept(archivo);
            }
        }
        return archivo;
    }
    
    public static class Builder {
        
        private String directorioInicial = System.getProperty("user.home");
        private String titulo = "Selecciona un archivo";
        private boolean multipleSeleccion = false;
        private int modoSeleccion = JFileChooser.FILES_ONLY;
        private boolean mostrarTodosLosArchivos = false;
        private final List<FileFilter> filtros = new ArrayList<>();
        
        public Builder directorioInicial(String ruta) {
            this.directorioInicial = ruta;
            return this;
        }
        
        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }
        
        public Builder multiSeleccion(boolean valor) {
            this.multipleSeleccion = valor;
            return this;
        }
        
        public Builder modoSeleccion(int modoSeleccion) {
            this.modoSeleccion = modoSeleccion;
            return this;
        }
        
        public Builder mostarTodosLosArchivos(boolean valor) {
            this.mostrarTodosLosArchivos = valor;
            return this;
        }
        
        public Builder agregarFiltro(String descripcion, String... extensiones) {
            filtros.add(new FileNameExtensionFilter(descripcion, extensiones));
            return this;
        }
        
        public Builder agregarFiltro(FileFilter filtro) {
            filtros.add(filtro);
            return this;
        }
        
        private void aplicarLookAndFeelWindows() {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } catch (Exception e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        public JFileChooser construirChooser() {
            aplicarLookAndFeelWindows();
            JFileChooser selector = new JFileChooser(directorioInicial);
            selector.setDialogTitle(titulo);
            selector.setMultiSelectionEnabled(multipleSeleccion);
            selector.setFileSelectionMode(modoSeleccion);
            selector.setAcceptAllFileFilterUsed(mostrarTodosLosArchivos);
            
            filtros.forEach(selector::addChoosableFileFilter);
            if (!filtros.isEmpty()) {
                selector.setFileFilter(filtros.get(0));
            }
            return selector;
        }
        
    }
}
