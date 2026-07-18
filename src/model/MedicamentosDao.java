package model;

import java.util.ArrayList;
import java.util.List;

public class MedicamentosDao implements Crud<Medicamentos> {

    @Override
    public List<Medicamentos> listar() {
        List<Medicamentos> listamedicamentos = new ArrayList();
        return listamedicamentos;
    }

    @Override
    public int setAgregar(Medicamentos tr) {
        return 0;
    }

    @Override
    public int setActualizar(Medicamentos tr) {
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        return 0;
    }
}
