package model;

import java.util.List;

public interface Crud<T> {
    List<T> listar();
    int setAgregar(T tr);
    int setActualizar(T tr);
    int setEliminar(int id);
}