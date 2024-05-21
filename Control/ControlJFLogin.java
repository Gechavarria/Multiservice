/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFLogin;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Gerardo
 */
public class ControlJFLogin implements ActionListener {

    JFLogin control;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControlJFLogin() {
        control = new JFLogin();

        control.bIniciar.addActionListener(this);
        control.tUsuario.addActionListener(this);
        control.setVisible(true);
        control.setLocationRelativeTo(null);

    }

    public void validarUsu() {

        ConexionSQL conect = new ConexionSQL();
        Connection con = conect.conectar();

        int resultado = 0;
        String pass = String.valueOf(control.tContraseña.getPassword());
        String usuario = control.tUsuario.getText();
        String SQL = "select * from Usuarios where  usuario= '" + usuario + "' and contraseña='" + pass + "' ";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            if (rs.next()) {
                resultado = 1;

                if (resultado == 1) {
                    IO.imprimirAfirmacion("Inicio de Sesión Exitoso!");

                    ControlJFMenu ver = new ControlJFMenu();

                }

            } else {

                IO.imprimirError("Error de acceso, Usuario no Registrado");
            }

        } catch (Exception e) {

            IO.imprimirError("Eror de Registro" + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == control.bIniciar) {

            validarUsu();

        }

    }

}
