/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author jairodri
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PintarCuadroVista extends JFrame {
    private Cuadro cuadro;
    private JPanel ventana;
    private JLabel nclusterLabel;
    private JComboBox<String> colorComboBox;
    private JButton paintButton;
    private int clusteractual = 0;
    private final Map<String, Color> COLORS = new LinkedHashMap<>() {{
        put("Blanco", Color.WHITE);
        put("Rojo", Color.RED);
        put("Verde", Color.GREEN);
        put("Azul", Color.BLUE);
        put("Amarillo", Color.YELLOW);
        put("Naranja", Color.ORANGE);
        put("Magenta", Color.MAGENTA);
        put("Cyan", Color.CYAN);
        put("Rosado", Color.PINK);
        put("Morado", new Color(128, 0, 128));
    }};

    public PintarCuadroVista() {
        super("Pintar Cuadro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        cuadro = new Cuadro();
        cuadro.obtenerCuadro("/recurso/Cuadro.txt");

        ventana = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCuadro(g);
            }
        };
        ventana.setPreferredSize(new Dimension(400, 400));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        

        nclusterLabel = new JLabel("Cluster: 1");
        nclusterLabel.setPreferredSize(new Dimension(80, 25));
        controlPanel.add(nclusterLabel);

        colorComboBox = new JComboBox<String>(COLORS.keySet().toArray(new String[0]));
        colorComboBox.setPreferredSize(new Dimension(10,10)); 
        controlPanel.add(colorComboBox);

        paintButton = new JButton("Pintar");
        paintButton.addActionListener(e -> paintCluster());
        paintButton.setPreferredSize(new Dimension(100,30)); 
        controlPanel.add(paintButton);

        add(ventana, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }

    private void paintCluster() {
        if (clusteractual < cuadro.getClusters().size()) {
            Cluster cluster = cuadro.getClusters().get(clusteractual);
            int colorselec = colorComboBox.getSelectedIndex();
            Color color = COLORS.get(colorComboBox.getItemAt(colorselec));

            for (int[] pixel : cluster.getPixels()) {
                cuadro.getMatriz()[pixel[0]][pixel[1]] = colorselec; 
            }

            clusteractual++;
            nclusterLabel.setText("Cluster: " + (clusteractual + 1));
            ventana.repaint();
            
        } else{
            JOptionPane.showMessageDialog(null, "Todos los clusters fueron pintados");
        }
    }

    private void drawCuadro(Graphics g) {
        int cellSize = Math.min(ventana.getWidth() / cuadro.getMatriz()[0].length, ventana.getHeight() / cuadro.getMatriz().length);
        g.setColor(Color.BLACK);
        for (int i = 0; i < cuadro.getMatriz().length; i++) {
            for (int j = 0; j < cuadro.getMatriz()[i].length; j++) {
                int color = cuadro.getMatriz()[i][j];
                g.setColor((Color) COLORS.values().toArray()[Math.max(0, Math.min(color, COLORS.size() - 1))]);
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    
}

