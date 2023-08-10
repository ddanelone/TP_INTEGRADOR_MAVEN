package modelos.caminosDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import modelos.Caminos;
import modelos.CaminosDao;
import modelos.ConnectionMySQL;

class CaminosDaoTest {

	CaminosDao caminoDaoMock;
	Caminos caminoMock;

	@Mock
	ConnectionMySQL cnMock;

	@Mock
	Connection connMock;

	@Mock
	PreparedStatement pstMock;

	@Mock
	ResultSet rsMock;

	@InjectMocks
	CaminosDao caminoDao;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		caminoDaoMock = mock(CaminosDao.class);
		caminoMock = mock(Caminos.class);
	}

	@Test
    void testRegistrarCaminoQuery() throws SQLException {
        // Configuramos el comportamiento del mock de ConnectionMySQL para que devuelva el mock de Connection
        when(cnMock.getConnection()).thenReturn(connMock);

        // Configuramos el comportamiento del mock de Connection.prepareStatement para que devuelva el mock de PreparedStatement
        when(connMock.prepareStatement(anyString())).thenReturn(pstMock);

        // Configuramos el comportamiento del mock de PreparedStatement para que devuelva true
        when(pstMock.execute()).thenReturn(true);

        // Crear un objeto Caminos con los datos necesarios para el test
        Caminos camino = new Caminos(1, 2, 3, 100, 120, true, "Observaciones");

        // Llamamos al método que queremos probar
        boolean resultado = caminoDao.registrarCaminoQuery(camino);

        // Verificamos el resultado esperado
        assertTrue(resultado, "Se esperaba que el método retorne 'true'");
    }

	@Test
    void testRegistrarCaminoQueryException() throws SQLException {
        // Configuramos el comportamiento del mock de ConnectionMySQL para que devuelva el mock de Connection
        when(cnMock.getConnection()).thenReturn(connMock);

        // Configuramos el comportamiento del mock de Connection.prepareStatement para que devuelva el mock de PreparedStatement
        when(connMock.prepareStatement(anyString())).thenReturn(pstMock);

        // Configuramos el comportamiento del mock de PreparedStatement para que lance una SQLException
        when(pstMock.execute()).thenThrow(new SQLException("Error simulado al insertar"));

        // Crear un objeto Caminos con los datos necesarios para el test
        Caminos camino = new Caminos(1, 2, 3, 100, 120, true, "Observaciones");

        // Llamamos al método que queremos probar y verificamos que se maneje correctamente la excepción
        assertDoesNotThrow(() -> caminoDao.registrarCaminoQuery(camino),
                "Se esperaba que el método maneje la excepción sin lanzarla nuevamente");
    }

	@Test
    void testListaCaminosQuery() throws SQLException {
        // Configuramos el comportamiento del mock de ConnectionMySQL para que devuelva el mock de Connection
        when(cnMock.getConnection()).thenReturn(connMock);

        // Configuramos el comportamiento del mock de Connection.prepareStatement para que devuelva el mock de PreparedStatement
        when(connMock.prepareStatement(anyString())).thenReturn(pstMock);

        // Configuramos el comportamiento del mock de PreparedStatement.executeQuery para que devuelva el mock de ResultSet
        when(pstMock.executeQuery()).thenReturn(rsMock);

        // Configuramos el comportamiento del mock de ResultSet para que devuelva algunos datos de prueba
        when(rsMock.next()).thenReturn(true).thenReturn(false);
        when(rsMock.getInt("id")).thenReturn(1);
        when(rsMock.getInt("origen_id")).thenReturn(2);
        when(rsMock.getInt("destino_id")).thenReturn(3);
        when(rsMock.getInt("capacidad")).thenReturn(100);
        when(rsMock.getInt("tiempo")).thenReturn(120);
        when(rsMock.getBoolean("operativo")).thenReturn(true);
        when(rsMock.getString("observaciones")).thenReturn("Observaciones");

        // Llamamos al método que queremos probar
        List<Caminos> listaCaminos = caminoDao.listaCaminosQuery("");

        // Verificamos el resultado esperado
        assertEquals(1, listaCaminos.size(), "La lista de caminos debería tener un solo elemento");
        Caminos camino = listaCaminos.get(0);
        assertEquals(1, camino.getId());
        assertEquals(2, camino.getOrigenId());
        assertEquals(3, camino.getDestinoId());
        assertEquals(100, camino.getCapacidad());
        assertEquals(120, camino.getTiempo());
        assertTrue(camino.isOperativo());
        assertEquals("Observaciones", camino.getObservaciones());
    }

	@Test
    void testModificarCaminoQuery() throws SQLException {
        // Configuramos el comportamiento del mock de ConnectionMySQL para que devuelva el mock de Connection
        when(cnMock.getConnection()).thenReturn(connMock);

        // Configuramos el comportamiento del mock de Connection.prepareStatement para que devuelva el mock de PreparedStatement
        when(connMock.prepareStatement(anyString())).thenReturn(pstMock);

        // Configuramos el comportamiento del mock de PreparedStatement para que devuelva true
        when(pstMock.execute()).thenReturn(true);

        // Crear un objeto Caminos con los datos necesarios para el test
        Caminos camino = new Caminos(1, 2, 3, 100, 120, true, "Observaciones");

        // Llamamos al método que queremos probar
        boolean resultado = caminoDao.modificarCaminoQuery(camino);

        // Verificamos el resultado esperado
        assertTrue(resultado, "Se esperaba que el método retorne 'true'");
    }

	@Test
	void testModificarCaminoEstadoEstadoQuery() throws SQLException {
	    // Arrange: Configuración del escenario
	    when(cnMock.getConnection()).thenReturn(connMock);
	    when(connMock.prepareStatement(anyString())).thenReturn(pstMock);
	    when(pstMock.execute()).thenReturn(true);

	    Caminos camino = new Caminos(1, 2, 3, 100, 120, true, "Observaciones");

	    // Act: Llamada al método que queremos probar
	    boolean resultado = caminoDao.modificarCaminoEstadoEstadoQuery(camino);

	    // Assert: Verificación del resultado esperado
	    assertTrue(resultado, "Se esperaba que el método retorne 'true'");
	}


	@Test
    void testBorrarCaminoQuery() throws SQLException {
        // Configuramos el comportamiento del mock de ConnectionMySQL para que devuelva el mock de Connection
        when(cnMock.getConnection()).thenReturn(connMock);

        // Configuramos el comportamiento del mock de Connection.prepareStatement para que devuelva el mock de PreparedStatement
        when(connMock.prepareStatement(anyString())).thenReturn(pstMock);

        // Configuramos el comportamiento del mock de PreparedStatement para que devuelva true
        when(pstMock.execute()).thenReturn(true);

        // Llamamos al método que queremos probar
        boolean resultado = caminoDao.borrarCaminoQuery(1);

        // Verificamos el resultado esperado
        assertTrue(resultado, "Se esperaba que el método retorne 'true'");
    }

}