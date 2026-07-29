package model;

import java.time.LocalDate;

public class Medicamentos {

    private String nRegistroSanitario;
    private String nombre;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private String cantidad;
    private int idTipoMedicamento;
    private String nombreTipoMedicamento;
    private int idCategoria;
    private String nombreCategoria;
    private boolean estado;
    private int stock;
    private String urlFoto;
    
    public Medicamentos(){}

    public Medicamentos(String nombre) {
        this.nombre = nombre;
    }

    
    public String getnRegistroSanitario() {
        return nRegistroSanitario;
    }

    public void setnRegistroSanitario(String nRegistroSanitario) {
        this.nRegistroSanitario = nRegistroSanitario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdTipoMedicamento() {
        return idTipoMedicamento;
    }

    public void setIdTipoMedicamento(int idTipoMedicamento) {
        this.idTipoMedicamento = idTipoMedicamento;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNombreTipoMedicamento() {
        return nombreTipoMedicamento;
    }

    public void setNombreTipoMedicamento(String nombreTipoMedicamento) {
        this.nombreTipoMedicamento = nombreTipoMedicamento;
    }
    
    
    
    
    
}
