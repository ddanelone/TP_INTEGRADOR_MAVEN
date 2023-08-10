package controladores;

import vistas.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelos.Caminos;
import modelos.CaminosDao;
import modelos.Electrodomesticos;
import modelos.ElectrodomesticosDao;
import modelos.Ordenes;
import modelos.OrdenesDao;
import modelos.Stock;
import modelos.StockDao;
import modelos.Sucursales;
import modelos.SucursalesDao;

public class SettingsController implements MouseListener, ChangeListener {

	private SystemView vista;
	// Sucursales
	Sucursales sucursal = new Sucursales();
	SucursalesDao sucursalDao = new SucursalesDao();

	// Electrodomesticos
	Electrodomesticos electro = new Electrodomesticos();
	ElectrodomesticosDao electroDao = new ElectrodomesticosDao();

	// Caminos
	Caminos camino = new Caminos();
	CaminosDao caminoDao = new CaminosDao();

	// Stock
	Stock stock = new Stock();
	StockDao stockDao = new StockDao();

	// Ordenes
	Ordenes orden = new Ordenes();
	OrdenesDao ordenDao = new OrdenesDao();

	private SucursalesControlador sucursal_cuenta;
	private ElectrodomesticosControlador electrodomestico_cuenta;
	private CaminosControlador camino_cuenta;
	private StockControlador stock_cuenta;
	private OrdenesControlador ordenes_cuenta;
	private AdministrarControlador administrar_cuenta;
	private InformesControlador informe_cuenta;

	public SettingsController(SystemView vista) {
		this.vista = vista;
		// Controlador de Sucursales
		sucursal_cuenta = new SucursalesControlador(sucursal, sucursalDao, vista);
		sucursal_cuenta.listarTodasLasSucursales();
		// Controlador de electrodomesticos
		electrodomestico_cuenta = new ElectrodomesticosControlador(electro, electroDao, vista);
		electrodomestico_cuenta.listarTodosLosElectrodomesticos();
		// Controlador de Caminos
		camino_cuenta = new CaminosControlador(camino, caminoDao, vista);
		camino_cuenta.listarTodosLosCaminos();
		// Controlador de Stock
		stock_cuenta = new StockControlador(stock, stockDao, vista);
		stock_cuenta.listarTodosLosStock();
		// Controlador de órdenes
		ordenes_cuenta = new OrdenesControlador(orden, ordenDao, vista);
		ordenes_cuenta.listarTodasLasOrdenes();
		// Controlador de Administracion
		administrar_cuenta = new AdministrarControlador(vista);
		administrar_cuenta.listarTodasLasSucursales();
		administrar_cuenta.listarTodosLosCaminos();
		// Controlador de informes
		informe_cuenta = new InformesControlador(vista);

		this.vista.jTabbedPane1.addChangeListener(this);
		this.vista.jLabelSucursales.addMouseListener(this);
		this.vista.jLabelElectrodomesticos.addMouseListener(this);
		this.vista.jLabelStock.addMouseListener(this);
		this.vista.jLabelCaminos.addMouseListener(this);
		this.vista.jLabelOrdenes.addMouseListener(this);
		this.vista.jLabelAdministracion.addMouseListener(this);
		this.vista.jLabelInformes.addMouseListener(this);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == vista.jLabelSucursales) {
			vista.jPanelSucursales.setBackground(new Color(152, 202, 63));
		} else if (e.getSource() == vista.jLabelElectrodomesticos) {
			vista.jPanelElectrodomesticos.setBackground(new Color(152, 202, 63));
		} else if (e.getSource() == vista.jLabelStock) {
			vista.jPanelStock.setBackground(new Color(152, 202, 63));
		} else if (e.getSource() == vista.jLabelCaminos) {
			vista.jPanelCaminos.setBackground(new Color(152, 202, 63));
		} else if (e.getSource() == vista.jLabelOrdenes) {
			vista.jPanelOrdenes.setBackground(new Color(152, 202, 63));
		} else if (e.getSource() == vista.jLabelAdministracion) {
			vista.jPanelAdministracion.setBackground(new Color(152, 202, 63));
		} else if (e.getSource() == vista.jLabelInformes) {
			vista.jPanelInformes.setBackground(new Color(152, 202, 63));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == vista.jLabelSucursales) {
			vista.jPanelSucursales.setBackground(new Color(18, 45, 61)); // [18,45,61]
		} else if (e.getSource() == vista.jLabelElectrodomesticos) {
			vista.jPanelElectrodomesticos.setBackground(new Color(18, 45, 61));
		} else if (e.getSource() == vista.jLabelStock) {
			vista.jPanelStock.setBackground(new Color(18, 45, 61));
		} else if (e.getSource() == vista.jLabelCaminos) {
			vista.jPanelCaminos.setBackground(new Color(18, 45, 61));
		} else if (e.getSource() == vista.jLabelOrdenes) {
			vista.jPanelOrdenes.setBackground(new Color(18, 45, 61));
		} else if (e.getSource() == vista.jLabelAdministracion) {
			vista.jPanelAdministracion.setBackground(new Color(18, 45, 61));
		} else if (e.getSource() == vista.jLabelInformes) {
			vista.jPanelInformes.setBackground(new Color(18, 45, 61));
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int selectedIndex = vista.jTabbedPane1.getSelectedIndex();
		switch (selectedIndex) {
		case 0:
			sucursal_cuenta.limpiarTabla();
			sucursal_cuenta.listarTodasLasSucursales();
			break;
		case 1:
			electrodomestico_cuenta.limpiarTabla();
			electrodomestico_cuenta.listarTodosLosElectrodomesticos();
			break;
		case 2:
			stock_cuenta.limpiarTabla();
			stock_cuenta.listarTodosLosStock();
			break;
		case 3:
			camino_cuenta.limpiarTabla();
			camino_cuenta.listarTodosLosCaminos();
			break;
		case 4:
		ordenes_cuenta.listarTodasLasOrdenes();
		break;
		case 5:
			administrar_cuenta.limpiarTablaCamino();
			administrar_cuenta.limpiarTablaSucursal();
			administrar_cuenta.listarTodasLasSucursales();
			administrar_cuenta.listarTodosLosCaminos();
			break;
		default:
			break;
		}
	}
}
