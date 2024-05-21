/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFAgregarCliente;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gerardo
 */
public class ControlJFAgregarCliente implements ActionListener {

    JFAgregarCliente control;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControlJFAgregarCliente() {
        control = new JFAgregarCliente();
        control.tRazonSocial.addActionListener(this);
        control.tRuc.addActionListener(this);
        control.tNombreCliente.addActionListener(this);
        control.tApellidoCliente.addActionListener(this);
        control.tDireccion.addActionListener(this);
        control.tCelular.addActionListener(this);
        control.tCorreo.addActionListener(this);
        control.bAgregarCliente.addActionListener(this);
        control.bNuevo.addActionListener(this);
        control.setVisible(true);
        control.setLocationRelativeTo(null);

        control.tRazonSocial.setEnabled(false);
        control.tRuc.setEnabled(false);
        control.tNombreCliente.setEnabled(false);
        control.tApellidoCliente.setEnabled(false);
        control.tDireccion.setEnabled(false);
        control.tCelular.setEnabled(false);
        control.tCorreo.setEnabled(false);
        control.bAgregarCliente.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == control.bAgregarCliente) {
            ConexionSQL conect = new ConexionSQL();
            Connection con = conect.conectar();

            String SQL = "insert into clientes(razon_social,ruc,nombre,apellido,direccion,telefono,correo)values(?,?,?,?,?,?,?)";
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

                pst.executeUpdate();
                IO.imprimirAfirmacion("Registro de Cliente Exitoso");
            } catch (Exception e) {

                IO.imprimirError("Error de Registro");
            }
       
        }
        if (ae.getSource() == control.bNuevo) {
            control.tRazonSocial.setEnabled(true);
            control.tRuc.setEnabled(true);
            control.tNombreCliente.setEnabled(true);
            control.tApellidoCliente.setEnabled(true);
            control.tDireccion.setEnabled(true);
            control.tCelular.setEnabled(true);
            control.tCorreo.setEnabled(true);
            control.bAgregarCliente.setEnabled(true);
        }

    }

    private static boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

}
