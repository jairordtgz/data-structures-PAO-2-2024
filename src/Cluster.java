/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jairodri
 */
import java.util.*;

public class Cluster {
    private int numcluster; 
    private int color;
    private int tamaño;
    private List<int[]> pixels;

    public Cluster(int numcluster, int color) {
        this.numcluster = numcluster;
        this.color = color;
        this.tamaño = 0;
        this.pixels = new ArrayList<>();
    }

    public void añadirPixel(int fil, int col) {
        pixels.add(new int[]{fil, col});
        tamaño++;
    }

    public int getNumCluster() { 
        return numcluster;
    }

    public int getColor() {
        return color;
    }

    public int getTamaño() {
        return tamaño;
    }

    public List<int[]> getPixels() {
        return pixels;
    }
   
    @Override
    public String toString() {
        return "Cluster:" + numcluster + "  Color: " + color + "  Tamaño: " + tamaño;
    }
}
