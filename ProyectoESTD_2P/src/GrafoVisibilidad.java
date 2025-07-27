import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

public class GrafoVisibilidad {
    private Graph grafo;
    private String inicio; 
    private String meta; 
    private Random pesord; 

    public GrafoVisibilidad() {
        grafo = new SingleGraph("Grafo de Visibilidad");
        pesord = new Random(); 
        this.inicio=inicio;
        this.meta=meta;
    }

    public void generarGrafo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            LinkedList<String> vertices = new LinkedList<>();
            LinkedList<double[][]> obstaculos = new LinkedList<>();

            String linea;
            int nlinea = 0;

            while ((linea = br.readLine()) != null) {
                nlinea++;
                if (nlinea == 1 || nlinea == 2) { 
                    String[] coordenadas = linea.replace("(", "").replace(")", "").split(",");
                    String vertice = coordenadas[0].trim() + "," + coordenadas[1].trim();
                    vertices.add(vertice);
                    grafo.addNode(vertice);
                    double x = Double.parseDouble(coordenadas[0].trim());
                    double y = Double.parseDouble(coordenadas[1].trim());
                    grafo.getNode(vertice).setAttribute("xy", x, y);
                    grafo.getNode(vertice).setAttribute("layout.pin", true);
                    
                    
                    if (nlinea == 1) {
                        inicio = vertice;
                    } else {
                        meta = vertice;
                    }
                } else { 
                    String[] partes = linea.split(";");
                    double[][] obstaculo = new double[partes.length][2];

                    for (int i = 0; i < partes.length; i++) {
                        String[] coordenadas = partes[i].replace("(", "").replace(")", "").split(",");
                        obstaculo[i][0] = Double.parseDouble(coordenadas[0].trim());
                        obstaculo[i][1] = Double.parseDouble(coordenadas[1].trim());

                        String vertice = coordenadas[0].trim() + "," + coordenadas[1].trim();
                        vertices.add(vertice);
                        grafo.addNode(vertice);
                        grafo.getNode(vertice).setAttribute("xy", obstaculo[i][0], obstaculo[i][1]);
                        grafo.getNode(vertice).setAttribute("layout.pin", true);
                    }
                    obstaculos.add(obstaculo);
                }
            }

            
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = i + 1; j < vertices.size(); j++) {
                    String v1 = vertices.get(i);
                    String v2 = vertices.get(j);

                    if (!interseca(v1, v2, obstaculos)) {
                        String edgeId = v1 + "-" + v2;
                        grafo.addEdge(edgeId, v1, v2, false);
                        
                        int peso = pesord.nextInt(10) + 1; 
                        grafo.getEdge(edgeId).setAttribute("weight", peso);
                        grafo.getEdge(edgeId).setAttribute("ui.label", String.valueOf(peso));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean interseca(String v1, String v2, LinkedList<double[][]> obstaculos) {
        String[] c1 = v1.split(",");
        String[] c2 = v2.split(",");

        double x1 = Double.parseDouble(c1[0]);
        double y1 = Double.parseDouble(c1[1]);
        double x2 = Double.parseDouble(c2[0]);
        double y2 = Double.parseDouble(c2[1]);

        for (double[][] obstaculo : obstaculos) {
            for (int i = 0; i < obstaculo.length; i++) {
                double x3 = obstaculo[i][0];
                double y3 = obstaculo[i][1];
                double x4 = obstaculo[(i + 1) % obstaculo.length][0];
                double y4 = obstaculo[(i + 1) % obstaculo.length][1];

                if (esCruzado(x1, y1, x2, y2, x3, y3, x4, y4)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esCruzado(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (den==0) return false; 
        
        double t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
        double u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;

        return t>0 && t<1 && u>0 && u < 1;
        
    }

    public void mostrarGrafo() {
        grafo.setAttribute("ui.title", "Grafo de Visibilidad");
        grafo.display(false);
    }

    
    public void mostrarCamino() {
    
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, "result", "weight");
        dijkstra.init(grafo);
        dijkstra.setSource(grafo.getNode(inicio));
        dijkstra.compute();

       
        Node target = grafo.getNode(meta);
        Path path = dijkstra.getPath(target);

        
        path.edges().forEach(edge -> edge.setAttribute("ui.class", "Camino"));
        path.nodes().forEach(node -> node.setAttribute("ui.class", "Visitado"));

        
        System.out.println("Distancia más corta a " + meta + ": " + dijkstra.getPathLength(target));
        System.out.println("Camino: " + path.toString());

        String styleSheet = 
            "node {" +
            "   size: 30px;" +
            "   fill-color: #666666;" +
            "   text-size: 20;" +
            "}" +
            "node.Visitado {" +
            "   fill-color: #ff0000;" +
            "}" +
            "edge {" +
            "   size: 5px;" +
            "   text-size:16;" +
            "}" +
            "edge.Camino {" +
            "   fill-color: #0000ff;" +
            "   size: 5px;" +
            "   text-size:20;" +
            "}";

        
        grafo.setAttribute("ui.stylesheet", styleSheet);
        grafo.display(false);
    }

    
    public void simularMovimiento() {
        Dijkstra dijkstra2 = new Dijkstra();
        dijkstra2.init(grafo);
        dijkstra2.setSource(grafo.getNode(inicio));
        dijkstra2.compute();

        

        Path camino = dijkstra2.getPath(grafo.getNode(meta));
        if (camino != null) {
            for (Node node : camino.getNodeSet()) {
                
                try{
                    System.out.println("Moviendo a: " + node.getId());
                    Thread.sleep(1000);
                } catch(Exception e){
                    System.out.println("Error al mostrar el movimiento: "+e.getMessage());

                }
                
               
            }
        }
        
        
      
    }
}

 

 