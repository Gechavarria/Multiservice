/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFActualizarCliente;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Gerardo
 */
public class ControlJFActualizarCliente implements ActionListener {

    JFActualizarCliente control;

    @SuppressWarnings({"LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
    public ControlJFActualizarCliente() {
        control = new JFActualizarCliente();
        control.tRazonSocial.addActionListener(this);
        control.tRuc.addActionListener(this);
        control.tNombreCliente.addActionListener(this);
        control.tApellidoCliente.addActionListener(this);
        control.tDireccion.addActionListener(this);
        control.tCelular.addActionListener(this);
        control.tCorreo.addActionListener(this);
        control.tID.addActionListener(this);
        control.bActualizar.addActionListener(this);
        control.bEliminar.addActionListener(this);
        control.bMostrarTabla.addActionListener(this);
        control.setVisible(true);
        control.setLocationRelativeTo(null);
        mostrarTabla();
        control.tID.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       
        
        if (ae.getSource() == control.bActualizar) {
                int select = control.tTablaCliente.getSelectedRow();
        if (select <0) {
            control.tID.setText(control.tTablaCliente.getValueAt(select, 0).toString());
            control.tRazonSocial.setText(control.tTablaCliente.getValueAt(select, 1).toString());
            control.tRuc.setText(control.tTablaCliente.getValueAt(select, 2).toString());
            control.tNombreCliente.setText(control.tTablaCliente.getValueAt(select, 3).toString());
            control.tApellidoCliente.setText(control.tTablaCliente.getValueAt(select, 4).toString());
            control.tDireccion.setText(control.tTablaCliente.getValueAt(select, 5).toString());
            control.tCelular.setText(control.tTablaCliente.getValueAt(select, 6).toString());
            control.tCorreo.setText(control.tTablaCliente.getValueAt(select, 7).toString());
            
        } else {
            IO.imprimir("Fila no seleccionada");
        }   
       
            
            
            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();

            String SQL = "update clientes set razon_social=?,ruc=?,nombre=?,apellido=?,direccion=?,telefono=?,correo=? where id=?";
            try {

                PreparedStatement pst = con.prepareStatement(SQL);

                pst.setString(1, control.tRazonSocial.getText());
                pst.setString(2, control.tRuc.getText());
                pst.setString(3, control.tNombreCliente.getText());
                pst.setString(4, control.tApellidoCliente.getText());
                pst.setString(5, control.tDireccion.getText());
                pst.setString(6, control.tCelular.getText());
                String entradaEmail = control.tCorreo.getText();

                if (validarEmail(entradaEmail)) {
                    pst.setString(7, entradaEmail);
                } else {
                    IO.imprimirError("Correo Electronico invalido");
                    control.tCorreo.setText("");
                }

                pst.setInt(8, Integer.parseInt(control.tID.getText()));

                pst.executeUpdate();
              
                IO.imprimirAfirmacion("Actualización Exitoso");
            } catch (Exception e) {

                IO.imprimirError("Error de Actualización" + e);
            } 
        }
        if(ae.getSource()== control.bEliminar){
            
            String password = IO.leerString("Ingresar Contraseña");
            if(password.equalsIgnoreCase("multiservice")){
             int opcion= IO.imprimirOpcion("Esta seguro que desea eliminar el registro");
            if(opcion== 0){
            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();

            String SQL = "delete from clientes where id=?";
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
        modelo.addColumn("Razón Social");
        modelo.addColumn("RUP");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Tel/Cel");
        modelo.addColumn("Correo");

        ConexionSQL conect = new ConexionSQL();
        Connection con = conect.conectar();

        String SQL = "select * from clientes ";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                modelo.addRow(new String[]{"" + rs.getInt("id"), "" + rs.getString("razon_social"), "" + rs.getString("ruc"), "" + rs.getString("nombre"), ""
                    + rs.getString("apellido"), "" + rs.getString("direccion"), "" + rs.getString("telefono"), "" + rs.getString("correo")

                });
                control.tTablaCliente.setModel(modelo);
               
                
            }

        } catch (Exception e) {

            IO.imprimirError("Eror de conexion" + e.getMessage());
        }
        
        

    }

    private static boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

}
