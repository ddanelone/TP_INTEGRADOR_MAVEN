package controladores;

import modelos.Sucursales;
import modelos.SucursalesDao;
import vistas.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SucursalesControlador implements ActionListener, MouseListener, KeyListener {

    private Sucursales sucursal;
    private SucursalesDao sucursalDao;
    private SystemView vista;
    DefaultTableModel modelo = new DefaultTableModel();
    private Object[] options = {"Sí", "No"};

    public SucursalesControlador(Sucursales sucursal, SucursalesDao sucursalDao, SystemView vista) {
        this.sucursal = sucursal;
        this.sucursalDao = sucursalDao;
        this.vista = vista;
        //Botón de registrar sucuarsal
        this.vista.btn_sucursales_crear.addActionListener(this);
        //Ponemos a la tabla en escucha
        this.vista.tabla_sucursales.addMouseListener(this);
        //Ponemos el botón de buscar en escucha del teclado
        this.vista.sucursales_search.addKeyListener(this);
        //Ponemos a la escucha el botón de modificar tabla
        this.vista.btn_sucursales_modificar.addActionListener(this);
        //Botón de elminar Sucursal y de cancelar
        this.vista.btn_sucursales_eliminar.addActionListener(this);
        this.vista.btn_sucursales_cancelar.addActionListener(this);
        //Ponemos en escucha el Label
        this.vista.jLabelSucursales.addMouseListener(this);
        //Evento para controlar los valores de ingreso en Horarios
        this.vista.txt_sucursales_apertura.addKeyListener(this);
        this.vista.txt_sucursales_cierre.addKeyListener(this);
        this.vista.txt_sucursales_codigo.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btn_sucursales_crear) {
            //verificamos si los campos están vacíos
            if (vista.txt_sucursales_codigo.getText().equals("")
                    || vista.txt_sucursales_nombre.getText().equals("")
                    || vista.txt_sucursales_apertura.getText().equals("")
                    || vista.txt_sucursales_cierre.getText().equals("")
                    || vista.cmb_estado_sucursal.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //Realizar la inserción
                sucursal.setCodigo(Integer.parseInt(vista.txt_sucursales_codigo.getText().trim()));
                sucursal.setNombre(vista.txt_sucursales_nombre.getText().trim());
                sucursal.setHorarioApertura(vista.txt_sucursales_apertura.getText().trim());
                sucursal.setHorarioCierre(vista.txt_sucursales_cierre.getText().trim());
                boolean operativa;
                if (vista.cmb_estado_sucursal.getSelectedItem().toString() == "Operativa") {
                    operativa = true;
                } else {
                    operativa = false;
                }

                sucursal.setOperativa(operativa);

                if (sucursalDao.registrarSucursalQuery(sucursal)) {
                    limpiarTabla();
                    limpiarCampos();
                    listarTodasLasSucursales();
                    JOptionPane.showMessageDialog(null, "Sucursal registrada con éxito");
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha producido un error, sucursal no registrada");
                }
            }
        } else if (e.getSource() == vista.btn_sucursales_modificar) {
            if (vista.txt_sucursales_id.equals("")) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una Sucursal para ser modificada");
            } else {
                //Verificamos si los campos están vacíos
                if (vista.txt_sucursales_codigo.getText().equals("")
                        || vista.txt_sucursales_nombre.getText().equals("")
                        || vista.txt_sucursales_apertura.getText().equals("")
                        || vista.txt_sucursales_cierre.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    //Realizar la actualización
                    sucursal.setId(Integer.parseInt(vista.txt_sucursales_id.getText().trim()));
                    sucursal.setCodigo(Integer.parseInt(vista.txt_sucursales_codigo.getText().trim()));
                    sucursal.setNombre(vista.txt_sucursales_nombre.getText().trim());
                    sucursal.setHorarioApertura(vista.txt_sucursales_apertura.getText().trim());
                    sucursal.setHorarioCierre(vista.txt_sucursales_cierre.getText().trim());
                    boolean operativa;
                    if (vista.cmb_estado_sucursal.getSelectedItem().toString() == "Operativa") {
                        operativa = true;
                    } else {
                        operativa = false;
                    }
                    sucursal.setOperativa(operativa);
                    if (sucursalDao.modificarSucursalQuery(sucursal)) {
                        limpiarTabla();
                        limpiarCampos();
                        listarTodasLasSucursales();
                        JOptionPane.showMessageDialog(null, "Sucursal modificada con éxito");
                        vista.btn_sucursales_crear.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Se ha producido un error, no se ha podido modificar la sucursal");
                    }
                }
            }
        } else if (e.getSource() == vista.btn_sucursales_eliminar) {
            int fila = vista.tabla_sucursales.getSelectedRow();

            //Si el usuario no seleccionó nada, el método devuelve -1
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una sucursal para eliminar");
            } else {
                int id = Integer.parseInt(vista.tabla_sucursales.getValueAt(fila, 0).toString());
                int confirmacion = JOptionPane.showOptionDialog(null, "¿Seguro de eliminar esta Sucursal?", "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (confirmacion == 0 && sucursalDao.borrarSucursalQuery(id) != false) {
                    limpiarCampos();
                    limpiarTabla();
                    vista.btn_sucursales_crear.setEnabled(true);
                    listarTodasLasSucursales();
                    JOptionPane.showMessageDialog(null, "Sucursal eliminada exitosamente");
                }
            }
        } else if (e.getSource() == vista.btn_sucursales_cancelar) {
            limpiarCampos();
            vista.btn_sucursales_crear.setEnabled(true);
        }

    }

    //Listar todas las sucursales
    public void listarTodasLasSucursales() {
        List<Sucursales> lista = sucursalDao.listaSucursalesQuery(vista.sucursales_search.getText());
        modelo = (DefaultTableModel) vista.tabla_sucursales.getModel();
        Object[] col = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            col[0] = lista.get(i).getId();
            col[1] = lista.get(i).getCodigo();
            col[2] = lista.get(i).getNombre();
            //Lógica para que ponga "Operativa / No Operativa en la tabla
            col[3] = lista.get(i).isOperativa() ? "Operativa" : "No Operativa";
            col[4] = lista.get(i).getHorarioApertura();
            col[5] = lista.get(i).getHorarioCierre();
            modelo.addRow(col);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.tabla_sucursales) {
            int fila = vista.tabla_sucursales.rowAtPoint(e.getPoint());
            //La fila la proveé el método de arriba, pero la columna la sacamos de la posiición en la tabla
            vista.txt_sucursales_id.setText(vista.tabla_sucursales.getValueAt(fila, 0).toString());
            vista.txt_sucursales_codigo.setText(vista.tabla_sucursales.getValueAt(fila, 1).toString());
            vista.txt_sucursales_nombre.setText(vista.tabla_sucursales.getValueAt(fila, 2).toString());
            vista.cmb_estado_sucursal.setSelectedItem(vista.tabla_sucursales.getValueAt(fila, 3).toString());
            vista.txt_sucursales_apertura.setText(vista.tabla_sucursales.getValueAt(fila, 4).toString());
            vista.txt_sucursales_cierre.setText(vista.tabla_sucursales.getValueAt(fila, 5).toString());

            //Desahbilitar
            //vista.txt_sucursales_id.setEditable(false);
            vista.btn_sucursales_crear.setEnabled(false);
        } else if (e.getSource() == vista.jLabelSucursales) {
            vista.jTabbedPane1.setSelectedIndex(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Este método se llama cada vez que el usuario ingresa una tecla en el JTextField
        if (e.getSource() == vista.txt_sucursales_apertura) {
            // Obtener el texto actual del JTextField
            String input = vista.txt_sucursales_apertura.getText().trim();

            // Definir la expresión regular para el formato de hora deseado (dos pares de números separados por ":")
            String regex = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

            // Verificar si el texto ingresado coincide con la expresión regular
            boolean isValid = Pattern.matches(regex, input);

            // Cambiar el color del JTextField dependiendo de si es válido o no
            if (isValid) {
                vista.txt_sucursales_apertura.setForeground(java.awt.Color.BLACK);
            } else {
                vista.txt_sucursales_apertura.setForeground(java.awt.Color.RED);
            }
        } else if (e.getSource() == vista.txt_sucursales_cierre) {
            // Obtener el texto actual del JTextField
            String input = vista.txt_sucursales_cierre.getText().trim();

            // Definir la expresión regular para el formato de hora deseado (dos pares de números separados por ":")
            String regex = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

            // Verificar si el texto ingresado coincide con la expresión regular
            boolean isValid = Pattern.matches(regex, input);

            // Cambiar el color del JTextField dependiendo de si es válido o no
            if (isValid) {
                vista.txt_sucursales_cierre.setForeground(java.awt.Color.BLACK);
            } else {
                vista.txt_sucursales_cierre.setForeground(java.awt.Color.RED);
            }
        } else if (e.getSource() == vista.txt_sucursales_codigo) {
            // Este método se llama cada vez que el usuario ingresa una tecla en el JTextField

            // Obtener la tecla ingresada por el usuario
            char c = e.getKeyChar();

            // Definir la expresión regular para solo permitir números enteros
            String regex = "\\d";

            // Verificar si la tecla ingresada coincide con la expresión regular
            if (!Character.toString(c).matches(regex)) {
                // Si la tecla no coincide, se consume el evento, evitando que se agregue al JTextField
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.sucursales_search) {
            limpiarTabla();
            listarTodasLasSucursales();
        }
    }

    //Método para limpiar los campos de la pantalla
    public void limpiarCampos() {
        vista.txt_sucursales_id.setText("");
        vista.txt_sucursales_codigo.setText("");
        vista.txt_sucursales_apertura.setText("");
        vista.txt_sucursales_cierre.setText("");
        vista.txt_sucursales_nombre.setText("");
        vista.cmb_estado_sucursal.setSelectedIndex(0);
    }

    //Método para limpiar la tabla
    public void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i--;
        }
    }
}
