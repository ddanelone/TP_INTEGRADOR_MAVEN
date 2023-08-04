package modelos.sucursalesDao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelos.Sucursales;
import modelosDao.SucursalesDaoFalso;

class SucursalDaoTest {

    private SucursalesDaoFalso sucursalesDao;

    @BeforeEach
    void setUp() {
        // Inicializar la instancia de SucursalesDaoFalso para usarla en las pruebas
        sucursalesDao = new SucursalesDaoFalso();
    }

    @AfterEach
    void tearDown() {
        // Limpiar la lista de sucursales falsas después de cada prueba
        sucursalesDao.listaSucursalesFalsas.clear();
    }

    @Test
    void testRegistrarSucursalQuery() {
        // Registrar una sucursal
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
        // Registrar una sucursal con el mismo ID que ya existe en la lista (debería fallar)
        Sucursales sucursalPrevia = new Sucursales();
        sucursalPrevia.setId(111);
        sucursalPrevia.setCodigo(111);
        sucursalPrevia.setNombre("Sucursal AAA");
        sucursalPrevia.setHorarioApertura("07:00");
        sucursalPrevia.setHorarioCierre("17:00");
        sucursalPrevia.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursalPrevia);

        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);

        assertFalse(sucursalesDao.registrarSucursalQuery(sucursal));
    }

    @Test
    void testListaSucursalesQuery() {
        // Insertar algunas sucursales en la lista para las pruebas
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
    void testListaSucursalesQuery_Fallo() {
        // Insertar algunas sucursales en la lista para las pruebas
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
        assertFalse(listaSucursales.size() != 2);
    }

    @Test
    void testModificarSucursalQuery() {
        // Insertar una sucursal en la lista para la prueba
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
    void testModificarSucursalQuery_Fallo() {
        // No insertamos la sucursal en la lista para simular que no existe

        // Modificar la sucursal que no está registrada
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Nueva Sucursal A");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(false);

        assertFalse(sucursalesDao.modificarSucursalQuery(sucursal));
    }


    @Test
    void testModificarSucursalEstadoQuery() {
        // Insertar una sucursal en la lista para la prueba
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
    void testModificarSucursalEstadoQuery_Fallo() {
        // No insertamos la sucursal en la lista para simular que no existe

        // Modificar el estado operativo de una sucursal que no está registrada
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setOperativa(false);

        assertFalse(sucursalesDao.modificarSucursalEstadoQuery(sucursal));
    }


    @Test
    void testBorrarSucursalQuery() {
        // Insertar una sucursal en la lista para la prueba
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursal);

        // Realizar la prueba
        assertTrue(sucursalesDao.borrarSucursalQuery(111));
    }
    
    @Test
    void testBorrarSucursalQuery_Fallo() {
        // Insertar una sucursal en la lista para la prueba
        Sucursales sucursal = new Sucursales();
        sucursal.setId(111);
        sucursal.setCodigo(111);
        sucursal.setNombre("Sucursal AAA");
        sucursal.setHorarioApertura("08:00");
        sucursal.setHorarioCierre("18:00");
        sucursal.setOperativa(true);
        sucursalesDao.registrarSucursalQuery(sucursal);

        // Intentar eliminar una sucursal que no existe en la lista
        assertFalse(sucursalesDao.borrarSucursalQuery(222));
    }

}
