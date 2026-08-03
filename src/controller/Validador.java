package controller;

public class Validador {

    private StringBuilder errores = new StringBuilder();

    public Validador validar(boolean condicionInvalida, String mensajeError) {
        if (condicionInvalida) errores.append(mensajeError);
        return this;
    }

    public Validador agregarSiNoVacio(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) errores.append(mensaje);
        return this;
    }
    
    public boolean tieneErrores() {
        return !errores.isEmpty();
    }

    public String obtenerErrores() {
        return errores.toString();
    }
}
