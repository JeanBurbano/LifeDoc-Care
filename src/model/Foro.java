package model;

import java.time.LocalDateTime;

public class Foro {

    private int idForo;
    private String tipoMensaje;
    private String asunto;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private int idUsuario;
    private String nombreUsuario;

    public Foro(String tipoMensaje, String asunto, String descripcion, int idUsuario) {
        this.tipoMensaje = tipoMensaje;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
    }

    public Foro(String tipoMensaje, String asunto, String descripcion, String nombreUsuario) {
        this.tipoMensaje = tipoMensaje;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.nombreUsuario = nombreUsuario;
    }

    public Foro(int idForo, String tipoMensaje, String asunto, String descripcion,
            LocalDateTime fechaPublicacion, int idUsuario, String nombreUsuario) {
        this.idForo = idForo;
        this.tipoMensaje = tipoMensaje;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdForo() {
        return idForo;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
