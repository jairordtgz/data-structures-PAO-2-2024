


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author jairodri
 */
import java.io.*; 
import java.util.*; 
import javax.swing.*;
public class ProyectoESTD_1P {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String file = "/recurso/Cuadro.txt";
        Cuadro cuadro = new Cuadro(); 
        cuadro.obtenerCuadro(file);
        cuadro.mostrarClusters();
        
        SwingUtilities.invokeLater(() -> new PintarCuadroVista().setVisible(true));
   
    }
    
}
