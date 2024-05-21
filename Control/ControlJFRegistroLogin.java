/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.ConexionSQL;
import Modelo.IO;
import Vista.JFRegistroLogin;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

/**
 *
 * @author Gerardo
 */
public class ControlJFRegistroLogin implements ActionListener {

    JFRegistroLogin control;

    @SuppressWarnings("LeakingThisInConstructor")
    public ControlJFRegistroLogin() {
        control = new JFRegistroLogin();

        control.bRegsitrar.addActionListener(this);
        control.tUsuario.addActionListener(this);
        control.tContraseña.addActionListener(this);
        control.bVolver.addActionListener(this);
        control.setVisible(true);
        control.setLocationRelativeTo(null);

    }

    public void agregarUsu() {

        ConexionSQL conect = new ConexionSQL();
        Connection con = conect.conectar();

        String pass = String.valueOf(control.tContraseña.getText());
        String SQL = "insert into Usuarios(usuario,contraseña)values(?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, control.tUsuario.getText());
            pst.setString(2, pass);

            pst.executeUpdate();
            IO.imprimirAfirmacion("Registro Exitoso");

        } catch (Exception e) {

            IO.imprimirError("Eror de Registro");
        }
    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == control.bRegsitrar) {

            agregarUsu();

        }
        if(ae.getSource()== control.bVolver){
            
          control.dispose();
        }
    }

}
