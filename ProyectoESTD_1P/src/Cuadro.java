/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jairodri
 */
import java.io.*;
import java.util.*;

public class Cuadro {
    private int[][] matriz;
    private List<Cluster> clusters;
    

    public Cuadro() {
        this.clusters = new ArrayList<>();
    }

    public int[][] obtenerCuadro(String ruta) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Cuadro.class.getResourceAsStream(ruta)))) {
            int filas = Integer.parseInt(reader.readLine());
            int columnas = Integer.parseInt(reader.readLine());
            matriz = new int[filas][columnas];

            for (int i = 0; i < filas; i++) {
                String[] linea = reader.readLine().split(",");
                matriz[i] = Arrays.stream(linea).mapToInt(Integer::parseInt).toArray();
            }

            identificarClusters(); 

        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            matriz = null;
        }

        return matriz;
    }

    private void identificarClusters() {
        boolean[][] clusteridentif = new boolean[matriz.length][matriz[0].length];
        int ncluster = 1; 

        for (int f = 0; f < matriz.length; f++) {
            for (int c = 0; c < matriz[f].length; c++) {
                if (!clusteridentif[f][c]) {
                    Cluster cluster = llenarCluster(f, c, clusteridentif, ncluster);
                    clusters.add(cluster);
                    ncluster++;
                    
                }
            }
        }
        
        clusters.sort((c1, c2) -> Integer.compare(c2.getTamaño(), c1.getTamaño()));
    }
    
    private Cluster llenarCluster(int i, int j, boolean[][] visitados, int clusterId) {
    int color = matriz[i][j];
    Cluster cluster = new Cluster(clusterId, color);
    ArrayDeque<int[]> pila = new ArrayDeque<>();
    pila.push(new int[]{i, j});
    visitados[i][j] = true; // ✅ Marca al inicio

    while (!pila.isEmpty()) {
        int[] pos = pila.pop();
        int x = pos[0];
        int y = pos[1];
        cluster.añadirPixel(x, y);

        int[][] vecinos = {{-1,0},{1,0},{0,-1},{0,1}}; 
        for (int[] v : vecinos) {
            int nx = x + v[0];
            int ny = y + v[1];
            if (nx >= 0 && ny >= 0 && nx < matriz.length && ny < matriz[0].length) {
                if (!visitados[nx][ny] && matriz[nx][ny] == color) {
                    visitados[nx][ny] = true; 
                    pila.push(new int[]{nx, ny});
                }
            }
        }
    }

    return cluster;
}


    public List<Cluster> getClusters() {
        return clusters;
    }

    public void mostrarClusters() {
        for (Cluster cluster : clusters) {
            System.out.println(cluster);
        }
    }

    public int[][] getMatriz() {
        return matriz;
    }
}

