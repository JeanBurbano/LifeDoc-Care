package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Validador {

    private final List<String> errores = new ArrayList<>();

    public Validador validar(BooleanSupplier condicionInvalida, String mensajeError) {
        if (condicionInvalida.getAsBoolean()) {
            errores.add(mensajeError);
        }
        return this;
    }

    public Validador agregarSiNoVacio(String mensaje) {
        if (mensaje != null && !mensaje.isEmpty()) {
            errores.add(mensaje);
        }
        return this;
    }

    public Validador siNoEstaVacio(String valor, Runnable validacionesDeFormato) {
        if (valor != null && !valor.isEmpty()) {
            validacionesDeFormato.run();
        }
        return this;
    }

    public boolean tieneErrores() {
        return !errores.isEmpty();
    }

    public List<String> obtenerErrores() {
        return errores;
    }

    public String obtenerMensaje() {
        StringBuilder sb = new StringBuilder();
        errores.forEach(sb::append);
        return sb.toString();
    }
}
