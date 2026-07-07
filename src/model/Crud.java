package model;

public interface Crud<T> {

    public void listar(T lista);

    public int setAgregar(T tr);

    public int setActualizar(T tr);

    public int setEliminar(int id);

}
