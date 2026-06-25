package model;

import javax.swing.JComponent;

public interface MetodosPublicos {

    //***************AQUI CREO UNA FUNCION QUE ES MUY IMPORTANTE*********************
    /*
   Esta funcion me permitiria refrescar un panel despues de agregarle los componentes.
   no les pondre el porque porque es muy largo de explicar solo sepan que cada vez que terminen de agregar algunos 
   componenetes a un panel para que se puedan vizualizar esos cambios correctamente llamana este metodo y por parametro le pasan el panel
     */
    default void refrescarVentana(JComponent panel) {
        panel.revalidate();//Recalcula el layout interno del panel
        panel.repaint();//Vuelve a dibujar el panel con los cambios
    }

    //Aqui creo el metodo para baciar panel si ya esta lleno
    default void vaciarPanel(JComponent componente) {
        if (componente.getComponentCount() > 0) {
            componente.removeAll();
            refrescarVentana(componente);
        }
    }    
}
