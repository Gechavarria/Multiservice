/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.IO;
import Vista.JFMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Gerardo
 */
public class ControlJFMenu implements ActionListener {
    
    JFMenu control;
    
    public ControlJFMenu() {
        control = new JFMenu();
        control.bCerrar.addActionListener(this);
        control.bArchivo.addActionListener(this);
        control.setVisible(true);
        control.setLocationRelativeTo(null);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if (control.bCerrar.isSelected()==true) {
            
            
            IO.imprimirAfirmacion("Aplicación Finalizada");
            System.exit(0);
        }
        
    }
    
}
