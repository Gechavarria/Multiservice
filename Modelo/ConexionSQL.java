/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Gerardo
 */
public class ConexionSQL {

    Connection conexion = null;

    public Connection conectar() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/Multiservicios", "Gerardo10", "Vaiolett2020");

        } catch (Exception e) {
            IO.imprimir("Error de conexion" + e);

        }
        return conexion;

    }

}
