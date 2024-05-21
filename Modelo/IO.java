/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javax.swing.JOptionPane;

/**
 *
 * @author Hans A
 */
public class IO {
    public static double leerDecimal(String mensaje) {

        double numero;
        numero = Double.parseDouble(JOptionPane.showInputDialog(mensaje));
        return numero;

    }

    public static double leerDecimalPositivo(String mensaje) {
        double numero = -1.0;
        do {
            try {   // INTENTAR, TRATAR
                numero = Double.parseDouble(JOptionPane.showInputDialog(mensaje));
                if (numero < 0) {
                    imprimirError("ERROR: Digite un número positivo");
                }
            } catch (NumberFormatException error) {   // CAPTURAR, AGARRAR
                imprimirError("ERROR: por favor, digite un número");
            }
        } while (numero < 0);
        return numero;
    }

    public static double leerEnteroPositivo(String mensaje) {

        int numero = 0;
        do {
            try {
                numero = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
                if (numero < 0) {
                    imprimirError("ERROR: Digite un numero positivo ");

                }
            } catch (NumberFormatException error) {
                imprimirError("ERROR: por favor, digite un número");

            }
        } while (numero < 0);

        return numero;
    }

    public static int leerEntero(String mensaje) {
        int numero;
        numero = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
        return numero;
    }

    public static void imprimir(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public static void imprimirError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Multiservice", JOptionPane.ERROR_MESSAGE);
    }
     public static void imprimirAfirmacion(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Multiservice", JOptionPane.INFORMATION_MESSAGE);
    }

    public static char leerchar(String mensaje) {

        String respuesta = JOptionPane.showInputDialog(mensaje);
        char caracter = respuesta.charAt(0);

        return caracter;

    }

    public static String leerString(String mensaje) {

        String palabra = "";
        palabra = JOptionPane.showInputDialog(mensaje);

        return palabra;

    }

    public static int imprimirOpcion(String mensaje) {

        int opcion = 0;

        opcion = JOptionPane.showConfirmDialog(null, mensaje , "ATENCIÓN" , JOptionPane.YES_NO_OPTION);

        return opcion;
    }
 
}
