/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ArchivoPDF;
import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFRegistroVenta;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gerardo
 */
public class ControlJFRegistroVenta implements ActionListener {

    JFRegistroVenta control;
    DefaultTableModel modelo = new DefaultTableModel();
    ArchivoPDF archivo;
    double total = 0.0;

    @SuppressWarnings({"LeakingThisInConstructor", "OverridableMethodCallInConstructor"})
    public ControlJFRegistroVenta() {
        control = new JFRegistroVenta();
        control.tNombresCliente.addActionListener(this);
        control.tApellidosCliente.addActionListener(this);
        control.tRUC.addActionListener(this);
        control.tDireccion.addActionListener(this);
        control.tRazonSocial.addActionListener(this);
        control.bAgregarCliente.addActionListener(this);
        control.cSeleccionarCliente.addActionListener(this);
        control.cSeleccionarProducto.addActionListener(this);
        control.tCodigoProducto.addActionListener(this);
        control.tNombreProducto.addActionListener(this);
        control.tValorProducto.addActionListener(this);
        control.tFecha.addActionListener(this);
        control.bAgregraProducto.addActionListener(this);
        control.bImprimir.addActionListener(this);
        control.tcantidadInviseible.setEnabled(true);

        control.setVisible(true);
        control.setLocationRelativeTo(null);
        llenarComboCliente();
        llenarComboProducto();
        mostrarFecha();
        modelo.addColumn("Cantidad");
        modelo.addColumn("Codigo");
        modelo.addColumn("Productos");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio Unitario");
        modelo.addColumn("Importe");

        control.tDescripcionProdcuto.setEnabled(false);

    }

    public void mostrarFecha() {
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        control.tFecha.setText(dia + "/" + (mes + 1) + "/" + año);

    }

    public void llenarComboCliente() {
        ConexionSQL conect = new ConexionSQL();
        Connection con = conect.conectar();

        String SQL = "select * from clientes";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                control.cSeleccionarCliente.addItem(rs.getInt("id") + " " + rs.getString("nombre") + " " + rs.getString("apellido"));

            }

        } catch (Exception e) {

            IO.imprimirError("Eror de conexion" + e.getMessage());
        }

    }

    public void llenarComboProducto() {
        ConexionSQL conect = new ConexionSQL();
        Connection con = conect.conectar();

        String SQL = "select * from productos";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                control.cSeleccionarProducto.addItem(rs.getInt("codigo") + " " + rs.getString("nombre") + " " + rs.getString("descripcion"));

            }

        } catch (Exception e) {

            IO.imprimirError("Eror de conexion" + e.getMessage());
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == control.cSeleccionarCliente) {

            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();

            String SQL = "select * from clientes ";
            int selectID = Integer.parseInt(control.cSeleccionarCliente.getSelectedItem().toString().split(" ")[0]);

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(SQL);

                while (rs.next()) {

                    if (selectID == rs.getInt("id")) {
//                    
                        control.tNombresCliente.setText(rs.getString("nombre"));
                        control.tApellidosCliente.setText(rs.getString("apellido"));
                        control.tDireccion.setText(rs.getString("direccion"));
                        control.tRUC.setText(rs.getString("ruc"));
                        control.tRazonSocial.setText(rs.getString("razon_social"));

                    }
                }

            } catch (Exception e) {

                IO.imprimirError("Eror de conexion" + e.getMessage());
            }

        }
        if (ae.getSource() == control.bAgregarCliente) {
            ControlJFAgregarCliente ir = new ControlJFAgregarCliente();
        }
        if (ae.getSource() == control.cSeleccionarProducto) {

            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();

            String SQL = "select * from productos ";
            int selectCodigo = Integer.parseInt(control.cSeleccionarProducto.getSelectedItem().toString().split(" ")[0]);

            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(SQL);

                while (rs.next()) {

                    if (selectCodigo == rs.getInt("codigo")) {
                        String codigo = String.valueOf(rs.getInt("codigo"));
                        control.tCodigoProducto.setText(codigo);
                        control.tNombreProducto.setText(rs.getString("nombre"));
                        String valor = String.valueOf(rs.getInt("precio_venta"));
                        control.tValorProducto.setText(valor);
                        control.tDescripcionProdcuto.setText(rs.getString("descripcion"));
                        String cantidadInvisible = String.valueOf(rs.getInt("cantidad"));
                        control.tcantidadInviseible.setText(cantidadInvisible);

                    }
                }

            } catch (Exception e) {

                IO.imprimirError("Eror de conexion" + e.getMessage());
            }

        }
        if (ae.getSource() == control.bAgregraProducto) {

            int cantidad = Integer.parseInt(control.sCantidad.getValue().toString());

            int valor = Integer.parseInt(control.tValorProducto.getText());
            int importe = cantidad * valor;

            modelo.addRow(new String[]{"" + control.sCantidad.getValue().toString(), "" + control.tCodigoProducto.getText(), "" + control.tNombreProducto.getText(), ""
                + control.tDescripcionProdcuto.getText(), "" + control.tValorProducto.getText(), "" + importe});
            control.tTablaFinal.setModel(modelo);

            control.tCodigoProducto.setText("");
            control.tNombreProducto.setText("");
            //control.cSeleccionarProducto.setSelectedIndex(0);
            control.tValorProducto.setText("");
            // control.cSeleccionarCliente.setSelectedIndex(0);
            control.tNombresCliente.setText("");
            control.tApellidosCliente.setText("");
            control.tRazonSocial.setText("");
            control.tRUC.setText("");
            control.tDireccion.setText("");

            double fila = 0.0;

            for (int i = 0; i < control.tTablaFinal.getRowCount(); i++) {

                fila = Double.parseDouble(control.tTablaFinal.getValueAt(i, 5).toString());

                total += fila;

                control.lTotal.setText("$" + total);

            }

        }
        if (ae.getSource() == control.bImprimir) {

            int conteo = 0;
            archivo = new ArchivoPDF("FV-" + control.tGuia.getText() + "" + control.tFecha.getText().replace("/", "-") +("_")+ conteo + 1 + ".pdf");
            double devolucion = 0.0;

            double cantidadIngresada = 0.0;
            cantidadIngresada = Double.parseDouble(control.tCantidadIngresada.getText());

            devolucion = cantidadIngresada - total;

            control.lDevolucion.setText("$" + devolucion);

            int i = 0;
            archivo.abrir();

            archivo.addParrafo("No Responsable de Iva", "");
            archivo.addParrafo("NIT: ********-*", "");
            archivo.addParrafo("Dirección: CR**¨-**#**", "");
            archivo.addParrafo("", "Telefono: **********" + "" + "\n");
            archivo.addParrafo("", "Factura de venta                           " + "PMV-" + (i + 1) + "\n");
            archivo.addParrafo("", "Fecha: " + control.tFecha.getText() + "\n");
            //archivo.addParrafo("", "Vendedor 1" + usuario.tUsuario.getText() + "\n");
            archivo.addParrafo("", "Prdouctos-------------------------------------------------------------\n");

            modelo.addRow(new String[]{"" + control.sCantidad.getValue().toString(), "" + control.tCodigoProducto.getText(), "" + control.tNombreProducto.getText(), ""
                + control.tDescripcionProdcuto.getText(), "" + control.tValorProducto.getText(), ""});
            control.tTablaFinal.setModel(modelo);
            archivo.addParrafo("", modelo + "\n");

            archivo.addParrafo("", "" + control.tNombreProducto.getText());
            archivo.addParrafo("", "Total                                    " + control.lTotal.getText() + "\n");
            archivo.addParrafo("----------------Forma de Pago----------------", "" + "\n");
            archivo.addParrafo("", "Efectivo                                 " + "$" + Double.parseDouble(control.tCantidadIngresada.getText()) + "\n");
            archivo.addParrafo("", "Cambio                                  " + control.lDevolucion.getText() + "\n");
            archivo.addParrafo("", "Punto de venta" + "\n");
            archivo.addParrafo("****************Gracias por su Compra****************", "" + "\n");
            archivo.addParrafo("                ¡Vuleve Pronto!                       ", "" + "\n");
            archivo.addParrafo("              Domicilios: *********                       ", "" + "\n");
            archivo.addParrafo("", "GESoft                                                    Brindamos solución a tus problemas rapidamente  " + "\n");

            archivo.mostrar();

            modelo.getDataVector().removeAllElements();
            control.tTablaFinal.updateUI();

            archivo.cerrar();

            control.lTotal.setText("");
            control.tCantidadIngresada.setText("");
//        control.lDevolucion.setText("");

        }

    }
}
