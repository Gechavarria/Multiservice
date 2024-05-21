/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFAgregarProducto;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

/**
 *
 * @author Gerardo
 */
public class ControlJFAgregarProducto implements ActionListener {

    JFAgregarProducto control;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControlJFAgregarProducto() {
        control = new JFAgregarProducto();
        control.tNombreProducto.addActionListener(this);
        control.cTipoProducto.addActionListener(this);
        control.tPrecioCompra.addActionListener(this);
        control.tPrecioVenta.addActionListener(this);
        control.tCodigoProducto.addActionListener(this);
        control.bAgregar.addActionListener(this);
        control.bNuevo.addActionListener(this);
        
        control.setVisible(true);
        control.setLocationRelativeTo(null);

        control.tNombreProducto.setEnabled(false);
        control.cTipoProducto.setEnabled(false);
        control.tPrecioCompra.setEnabled(false);
        control.tPrecioVenta.setEnabled(false);
        control.tDescripcion.setEnabled(false);
        control.tCodigoProducto.setEnabled(false);
        control.bAgregar.setEnabled(false);
        control.sCantidad.setEnabled(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == control.bAgregar) {

            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();
            String tipo = (String) control.cTipoProducto.getSelectedItem();
             int cantidad = Integer.parseInt(control.sCantidad.getValue().toString());
            String SQL = "insert into productos(nombre,tipo,precio_compra,precio_venta,descripcion,codigo,cantidad)values(?,?,?,?,?,?,?)";
            try {
                PreparedStatement pst = con.prepareStatement(SQL);
                pst.setString(1, control.tNombreProducto.getText());
                pst.setString(2, tipo);
                pst.setDouble(3, Double.parseDouble(control.tPrecioCompra.getText()));
                pst.setDouble(4, Double.parseDouble(control.tPrecioVenta.getText()));
                pst.setString(5, control.tDescripcion.getText());
                pst.setInt(6, Integer.parseInt(control.tCodigoProducto.getText()));
                pst.setInt(7,cantidad);

                pst.executeUpdate();
                IO.imprimirAfirmacion("Registro Exitoso");
            } catch (Exception e) {

                IO.imprimirError("Error de Registro");
            }
            
            control.tNombreProducto.setText("");
            control.cTipoProducto.setSelectedItem("");
            control.tPrecioCompra.setText("");
            control.tPrecioVenta.setText("");
            control.tDescripcion.setText("");
            control.tCodigoProducto.setText("");

        }
        if (ae.getSource() == control.bNuevo) {
            control.tNombreProducto.setEnabled(true);
            control.cTipoProducto.setEnabled(true);
            control.tPrecioCompra.setEnabled(true);
            control.tPrecioVenta.setEnabled(true);
            control.tDescripcion.setEnabled(true);
            control.tCodigoProducto.setEnabled(true);
            control.bAgregar.setEnabled(true);
            control.sCantidad.setEnabled(true);
            

        }
       

    }
}
