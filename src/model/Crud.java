package model;

import java.util.List;

public interface Crud<T> {

    public <T> List listar();

    public int setAgregar(T tr);

    public int setActualizar(T tr);

    public int setEliminar(int id);
    
    public T getCualUsuario();
    
}
