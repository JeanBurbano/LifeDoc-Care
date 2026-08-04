package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Especialidad;
import model.EspecialidadDao;
import model.Medico;
import model.MedicoDao;
import model.MetodosPublicos;
import model.Operario;
import model.OperarioDao;
import model.Rol;
import model.RolDao;
import model.UsuDao;
import view.RegistroPersonalInterfaz;

public class RegistroPersonalController extends RegistroUsuariosController {
    
    public static final int INDICE_MEDICO = 1;
    private static final int ID_ROL_MEDICO = 3;
    private static final int ID_ROL_OPERARIO = 4;

    private RegistroPersonalInterfaz rpI;
    private UsuDao usuarioDao;
    private MedicoDao medicoDao;
    private OperarioDao operarioDao;
    private RolDao rolDao;
    private EspecialidadDao especialidadDao;
    private List<Rol> roles = new ArrayList<>();
    private List<Especialidad> especialidades = new ArrayList<>();

    public RegistroPersonalController(RegistroPersonalInterfaz rpI) {
        super(rpI);
        this.rpI = rpI;
        this.usuarioDao = new UsuDao();
        this.medicoDao = new MedicoDao();
        this.operarioDao = new OperarioDao();
        this.rolDao = new RolDao();
        this.especialidadDao = new EspecialidadDao();
        cargarComboRoles();
        cargarComboEspecialidad();
        habilitarEspecilidadM(false);
        EventoEspecialidad();
    }

    private void cargarComboRoles() {
        rpI.campoRol.removeAllItems();
        rpI.campoRol.addItem("Seleccione un rol");
        roles = rolDao.listar();
        roles.forEach(rol -> rpI.campoRol.addItem(rol.getNombreRol()));
    }

    private void cargarComboEspecialidad() {
        rpI.especialidad.removeAllItems();
        rpI.especialidad.addItem("Seleccione una especialidad");
        especialidades = especialidadDao.listar();
        especialidades.forEach(e -> rpI.especialidad.addItem(e.getNombreEsp()));
    }

    @Override
    protected byte obtenerTipoIdentificacion() {
        return TI_CEDULA;
    }

    @Override
    protected void validacionesEspecificas(Validador validador) {
        Rol rolSeleccionado = obtenerRolSeleccionado();
        validador.validar(rolSeleccionado == null, "Debe seleccionar un rol para el personal\n");

        if (rolSeleccionado != null && rolSeleccionado.getIdRol() == ID_ROL_MEDICO) {
            validador.validar(obtenerEspecialidadSeleccionada() == null,
                    "Debe seleccionar una especialidad para el medico\n");
        }
    }
    
    protected void EventoEspecialidad(){
        rpI.campoRol.addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange() == ItemEvent.SELECTED){
                    
                    int indiceSeleccionado = rpI.campoRol.getSelectedIndex();
                    
                    if(indiceSeleccionado == INDICE_MEDICO){
                        habilitarEspecilidadM(true);
                        
                    }else{
                        habilitarEspecilidadM(false);
                        
                    }
                    
                }
            }
        });
    }
    
    protected void habilitarEspecilidadM(boolean v){
        rpI.especialidad.setEnabled(v);
    }

    @Override
    protected boolean persistirUsuario(byte idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String sexoBiologico, String correo, String contrasenaHashed, String telefono,
            String grupoSisben, LocalDate fechaNacimiento, String rutaFotoParaGuardar) {

        Rol rolSeleccionado = obtenerRolSeleccionado();
        byte edad = MetodosPublicos.calcularEdad(fechaNacimiento);

        if (rolSeleccionado.getIdRol() == ID_ROL_MEDICO) {
            Especialidad especialidadSeleccionada = obtenerEspecialidadSeleccionada();

            Medico medico = new Medico(
                    id,
                    (byte) ID_ROL_MEDICO,
                    idTipoIdentificacion,
                    numeroIdentificacion,
                    primerNombre,
                    segundoNombre,
                    primerApellido,
                    segundoApellido,
                    correo,
                    fechaNacimiento,
                    sexoBiologico,
                    telefono,
                    edad,
                    grupoSisben,
                    true,
                    rutaFotoParaGuardar,
                    especialidadSeleccionada.getNombreEsp());

            return usuarioDao.setAgregar(medico, contrasenaHashed, especialidadSeleccionada.getIdEsp()) > 0;
        }

        Operario operario = new Operario(
                id,
                (byte) ID_ROL_OPERARIO,
                idTipoIdentificacion,
                numeroIdentificacion,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido,
                correo,
                fechaNacimiento,
                sexoBiologico,
                telefono,
                edad,
                grupoSisben,
                true,
                rutaFotoParaGuardar);

        return usuarioDao.setAgregar(operario, contrasenaHashed) > 0;
    }
   

    private Rol obtenerRolSeleccionado() {
        int indice = rpI.campoRol.getSelectedIndex();
        if (indice < 1 || indice > roles.size()) {
            return null;
        }
        return roles.get(indice - 1);
    }

    private Especialidad obtenerEspecialidadSeleccionada() {
        int indice = rpI.especialidad.getSelectedIndex();
        if (indice < 1 || indice > especialidades.size()) {
            return null;
        }
        return especialidades.get(indice - 1);
    }
    
    
}