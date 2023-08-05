package controladores;

import vistas.SystemView;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsController implements MouseListener {

     private SystemView vista;
     
     public SettingsController(SystemView vista) {
         this.vista = vista;
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
        if(e.getSource()==vista.jLabelSucursales) {
            vista.jPanelSucursales.setBackground(new Color(152,202,63));
        } else if(e.getSource()== vista.jLabelElectrodomesticos) {
            vista.jPanelElectrodomesticos.setBackground(new Color(152,202,63));
        } else if(e.getSource()== vista.jLabelStock) {
            vista.jPanelStock.setBackground(new Color(152,202,63));    
        } else if(e.getSource()== vista.jLabelCaminos) {
            vista.jPanelCaminos.setBackground(new Color(152,202,63));
        } else if(e.getSource()== vista.jLabelOrdenes) {
            vista.jPanelOrdenes.setBackground(new Color(152,202,63));
        } else if(e.getSource()== vista.jLabelAdministracion) {
            vista.jPanelAdministracion.setBackground(new Color(152,202,63));
        } else if(e.getSource()== vista.jLabelInformes) {
            vista.jPanelInformes.setBackground(new Color(152,202,63));
        }
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==vista.jLabelSucursales) {
            vista.jPanelSucursales.setBackground(new Color(18,45,61)); //[18,45,61]
        } else if(e.getSource()== vista.jLabelElectrodomesticos) {
            vista.jPanelElectrodomesticos.setBackground(new Color(18,45,61));
        } else if(e.getSource()== vista.jLabelStock) {
            vista.jPanelStock.setBackground(new Color(18,45,61));
        } else if(e.getSource()== vista.jLabelCaminos) {
            vista.jPanelCaminos.setBackground(new Color(18,45,61));
        } else if(e.getSource()== vista.jLabelOrdenes) {
            vista.jPanelOrdenes.setBackground(new Color(18,45,61));
        } else if(e.getSource()== vista.jLabelAdministracion) {
            vista.jPanelAdministracion.setBackground(new Color(18,45,61));
        } else if(e.getSource()== vista.jLabelInformes) {
            vista.jPanelInformes.setBackground(new Color(18,45,61));
        }
    }
}
