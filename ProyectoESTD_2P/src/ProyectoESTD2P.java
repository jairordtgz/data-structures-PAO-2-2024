public class ProyectoESTD2P {
    public static void main(String[] args)  {

        System.setProperty("org.graphstream.ui", "swing");

        GrafoVisibilidad grafo = new GrafoVisibilidad();
        String archivo = "recurso\\ambiente.txt";

        grafo.generarGrafo(archivo);
        grafo.mostrarGrafo();
        grafo.mostrarCamino();
        grafo.simularMovimiento();
        

    }
}

