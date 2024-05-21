/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFActualizarProducto;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gerardo
 */
public class ControlJFActualizarProducto implements ActionListener {

    JFActualizarProducto control;
  
    @SuppressWarnings({"LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
    public ControlJFActualizarProducto() {
        control = new JFActualizarProducto();
        control.tNombreProducto.addActionListener(this);
        control.cTipoProducto.addActionListener(this);
        control.tPrecioCompra.addActionListener(this);
        control.tPrecioVenta.addActionListener(this);
        control.tCodigo.addActionListener(this);
        control.bActualizar.addActionListener(this);
        control.tID.addActionListener(this);
        control.bEliminarProducto.addActionListener(this);
        control.bMostrarTabla.addActionListener(this);
        control.setVisible(true);
        control.setLocationRelativeTo(null);
        mostrarTabla();
        control.setResizable(false);
        
        control.tID.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()== control.bActualizar){
            
            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();
           
            String tipo = (String) control.cTipoProducto.getSelectedItem();
            int cantidad = Integer.parseInt(control.sCantidad.getValue().toString());
            String SQL = "update productos set nombre=?,tipo=?,precio_compra=?,precio_venta=?,descripcion=?,codigo=?,cantidad=? where id=?";
            try {
               
                PreparedStatement pst = con.prepareStatement(SQL);
                
                pst.setString(1, control.tNombreProducto.getText());
                pst.setString(2, tipo);
                pst.setDouble(3, Double.parseDouble(control.tPrecioCompra.getText()));
                pst.setDouble(4, Double.parseDouble(control.tPrecioVenta.getText()));
                pst.setString(5, control.tDescripcion.getText());
                pst.setInt(6, Integer.parseInt(control.tCodigo.getText()));
                pst.setInt(7,cantidad);
                pst.setInt(8, Integer.parseInt(control.tID.getText()));
                
              

                pst.executeUpdate();
                IO.imprimirAfirmacion("Actualización Exitoso");
            } catch (Exception e) {

                IO.imprimirError("Error de Actualización" + e);
            }
         
        }
        
        if(ae.getSource()== control.bEliminarProducto){
            
            String password = IO.leerString("Ingresar Contraseña");
            if(password.equalsIgnoreCase("multiservice")){
             int opcion= IO.imprimirOpcion("Esta seguro que desea eliminar el registro");
            if(opcion== 0){
            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();

            String SQL = "delete from productos where id=?";
            try {

                PreparedStatement pst = con.prepareStatement(SQL);

               
                pst.setInt(1, Integer.parseInt(control.tID.getText()));

                pst.executeUpdate();
                
                IO.imprimirAfirmacion("Eliminación Exitoso");
            
                
            } catch (Exception e) {

                IO.imprimirError("Error de Eliminación" + e);
            } 
           
            }
            
        }else{
                IO.imprimirError("Acceso Denegado");
            }
            
        }
        if(ae.getSource()== control.bMostrarTabla){
            mostrarTabla();
        }
        
    }

    public void mostrarTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre Prducto");
        modelo.addColumn("Tipo Producto");
        modelo.addColumn("Precio Compra");
        modelo.addColumn("Precio Venta");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Codigo");
        modelo.addColumn("Cantidad");

        ConexionSQL conect = new ConexionSQL();
        Connection con = conect.conectar();

        String SQL = "select * from productos ";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                modelo.addRow(new String[]{"" + rs.getString("id"), "" + rs.getString("nombre"), "" + rs.getString("tipo"), "" + rs.getDouble("precio_compra"), ""
                    + rs.getDouble("precio_venta"), "" + rs.getString("descripcion"), "" + rs.getInt("codigo"), "" + rs.getInt("cantidad")

                });
                control.tTablaProductos.setModel(modelo);
            }

        } catch (Exception e) {

            IO.imprimirError("Eror de conexion" + e.getMessage());
        }

    }
     

}
