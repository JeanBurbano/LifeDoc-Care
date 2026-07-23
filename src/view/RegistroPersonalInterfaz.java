package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import model.MetodosPublicos;

public class RegistroPersonalInterfaz extends RegistroUsuariosInterfaz{
    
    public JComboBox<String> campoRol;
    private static final String[] ARREGLO_ID_PERSONAL = {"Cedula Ciudadania"};
    
    public RegistroPersonalInterfaz(String nombre){
        super(nombre);
        this.tituloCreacion.setText("Registrar Personal del centro");
        this.campoTipoId.setModel(new DefaultComboBoxModel<>(ARREGLO_ID_PERSONAL));
        
        this.campoRol = new JComboBox<>();
        MetodosPublicos.crearComboEstilizado(campoRol);
        
        // se reorganiza la fila 2 del GridBagLayout en camposDatos para poner el rol al lado de la contraseña

        GridBagConstraints gbDatos = new GridBagConstraints();
        gbDatos.insets = new Insets(0, 0, 0, 30);
        gbDatos.anchor = GridBagConstraints.WEST;
        gbDatos.fill = GridBagConstraints.NONE;
        gbDatos.gridy = 2;
        
        gbDatos.gridx = 1;
        gbDatos.insets = new Insets(0, 0, 0, 0);
        camposDatos.add(MetodosPublicos.crearCampoConEtiqueta("Rol del Personal *", campoRol), gbDatos);
        
        MetodosPublicos.refrescarVentana(cuerpo2);
        
    }
    
}
