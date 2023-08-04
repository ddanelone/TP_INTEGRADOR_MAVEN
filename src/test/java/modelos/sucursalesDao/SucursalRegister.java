package modelos.sucursalesDao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelos.SucursalesDao;
import modelos.Sucursales;
import java.sql.SQLException;

class SucursalRegister {

    private SucursalesDao sucursalesDao;

    @BeforeEach
    void setUp() {
        // Inicializar la instancia de SucursalesDao para usarla en las pruebas
        sucursalesDao = new SucursalesDao();
    }

    @AfterEach
    void tearDown() {
        // Limpiar la base de datos después de cada prueba para evitar dependencias entre ellas
        // Eliminamos todas las sucursales registradas en la prueba
        List<Sucursales> listaSucursales = sucursalesDao.listaSucursalesQuery("");
        for (Sucursales sucursal : listaSucursales) {
            sucursalesDao.borrarSucursalQuery(sucursal.getId());
        }
    }

    @Test
    void testRegistrarSucursalQuery() {
        // Registrar una sucursal con un ID que aún no existe en la base de datos
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);

        assertTrue(sucursalesDao.registrarSucursalQuery(sucursal));
    }

    @Test
    void testRegistrarSucursalQuery_Fallo() {
        // Registrar una sucursal con el mismo ID que ya existe en la base de datos (debería fallar)
        Sucursales sucursalPrevia = new Sucursales();
        sucursalPrevia.setId(111);
        sucursalPrevia.setCodigo(111);
        sucursalPrevia.setNombre("Sucursal AAA");
        sucursalPrevia.setHorarioApertura("07:00");
        sucursalPrevia.setHorarioCierre("17:00");
        sucursalPrevia.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursalPrevia);

        Sucursales sucursal = new Sucursales();
        //sucursal.setId(1);
        sucursal.setCodigo(1111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);

        assertTrue(sucursalesDao.registrarSucursalQuery(sucursal));
    }

    @Test
    void testListaSucursalesQuery_Fail() {
        // Insertar algunas sucursales en la base de datos para las pruebas
        Sucursales sucursal1 = new Sucursales();
        sucursal1.setId(111);
        sucursal1.setCodigo(111);
        sucursal1.setNombre("Sucursal AAA");
        sucursal1.setHorarioApertura("08:00");
        sucursal1.setHorarioCierre("18:00");
        sucursal1.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursal1);

        Sucursales sucursal2 = new Sucursales();
        sucursal2.setId(222);
        sucursal2.setCodigo(222);
        sucursal2.setNombre("Sucursal BBB");
        sucursal2.setHorarioApertura("09:00");
        sucursal2.setHorarioCierre("17:30");
        sucursal2.setOperativa(false);
        sucursalesDao.registrarSucursalQuery(sucursal2);

        // Realizar la prueba
        List<Sucursales> listaSucursales = sucursalesDao.listaSucursalesQuery("");
        assertEquals(2, listaSucursales.size());
    }

    @Test
    void testModificarSucursalQuery() {
        // Insertar una sucursal en la base de datos para la prueba
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursal);

        // Modificar la sucursal
        sucursal.setNombre("Nueva Sucursal A");
        sucursal.setOperativa(false);

        assertTrue(sucursalesDao.modificarSucursalQuery(sucursal));
    }

    @Test
    void testModificarSucursalEstadoQuery() {
        // Insertar una sucursal en la base de datos para la prueba
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursal);

        // Modificar el estado operativo de la sucursal
        sucursal.setOperativa(false);

        assertTrue(sucursalesDao.modificarSucursalEstadoQuery(sucursal));
    }

    @Test
    void testBorrarSucursalQuery() {
        // Insertar una sucursal en la base de datos para la prueba
        Sucursales sucursal = new Sucursales();
        sucursal.setId(1);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursal);

        // Realizar la prueba
        assertTrue(sucursalesDao.borrarSucursalQuery(111));
    }
}
