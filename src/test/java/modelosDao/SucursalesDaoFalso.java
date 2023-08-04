package modelosDao;

import modelos.Sucursales;
import modelos.SucursalesDao;

import java.util.ArrayList;
import java.util.List;

public class SucursalesDaoFalso extends SucursalesDao {

    public List<Sucursales> listaSucursalesFalsas = new ArrayList<>();

    // Sobreescribir el método para registrar una sucursal
    @Override
    public boolean registrarSucursalQuery(Sucursales sucursal) {
        // Verificar que el ID no exista en la lista de sucursales falsas
        for (Sucursales s : listaSucursalesFalsas) {
            if (s.getId() == sucursal.getId()) {
                return false; // El ID ya existe, no se puede registrar
            }
        }

        // Agregar la sucursal a la lista de sucursales falsas
        listaSucursalesFalsas.add(sucursal);
        return true; // Registro exitoso
    }

    // Sobreescribir el método para listar sucursales
    @Override
    public List<Sucursales> listaSucursalesQuery(String valor) {
        // Simplemente retornamos la lista de sucursales falsas
        return listaSucursalesFalsas;
    }

    // Sobreescribir el método para modificar una sucursal
    @Override
    public boolean modificarSucursalQuery(Sucursales sucursal) {
        // Simplemente buscamos la sucursal en la lista y la modificamos
        for (Sucursales s : listaSucursalesFalsas) {
            if (s.getId() == sucursal.getId()) {
                s.setCodigo(sucursal.getCodigo());
                s.setNombre(sucursal.getNombre());
                s.setHorarioApertura(sucursal.getHorarioApertura());
                s.setHorarioCierre(sucursal.getHorarioCierre());
                s.setOperativa(sucursal.isOperativa());
                return true; // Consideramos que siempre es exitoso
            }
        }
        return false; // Si no se encontró la sucursal, devuelve falso
    }

    // Sobreescribir el método para modificar el estado operativo de una sucursal
    @Override
    public boolean modificarSucursalEstadoQuery(Sucursales sucursal) {
        // Simplemente buscamos la sucursal en la lista y modificamos el estado operativo
        for (Sucursales s : listaSucursalesFalsas) {
            if (s.getId() == sucursal.getId()) {
                s.setOperativa(sucursal.isOperativa());
                return true; // Consideramos que siempre es exitoso
            }
        }
        return false; // Si no se encontró la sucursal, devuelve falso
    }

    // Sobreescribir el método para eliminar sucursal
    @Override
    public boolean borrarSucursalQuery(int id) {
        // Simplemente buscamos la sucursal en la lista y la eliminamos
        for (Sucursales s : listaSucursalesFalsas) {
            if (s.getId() == id) {
                listaSucursalesFalsas.remove(s);
                return true; // Consideramos que siempre es exitoso
            }
        }
        return false; // Si no se encontró la sucursal, devuelve falso
    }
}
