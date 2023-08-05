package controladores;

import modelos.Caminos;
import modelos.CaminosDao;
import modelos.Edge;
import modelos.GrafoCaminos;
import modelos.Graph;
import modelos.Sucursales;
import modelos.SucursalesDao;
import modelos.Vertex;
import vistas.Grafo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrafoControlador implements ActionListener {

    private Grafo grafo; //Vista
    private Graph graph; //Modelo
    private Map<Vertex, Point> vertexPositions;

    //Inicializo las clases que necesito
    private Sucursales sucursal = new Sucursales();
    private SucursalesDao sucursalDao = new SucursalesDao();
    private Caminos camino = new Caminos();
    private CaminosDao caminoDao = new CaminosDao();

    public GrafoControlador(Grafo grafo) {
        this.grafo = grafo;  //Vista
        
        graph = createGraphWithPageRank();
        
//Generar Coordenadas del grafo
        vertexPositions = new HashMap<>();
        generateCoordinates();
        
        //Pongo a la escucha el botón de cerrar
        this.grafo.btn_grafo_close.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == grafo.btn_grafo_close) {
            grafo.dispose();
        }
    }

    // Método para crear el grafo, reemplaza este método con el código para crear el grafo
    private Graph createGraph() {
        GrafoCaminos grafoCamino = new GrafoCaminos(caminoDao.listaCaminosQuery(""), sucursalDao.listaSucursalesQuery(""));
        Graph graph = grafoCamino.getGrafo();

        // Paso 1: Crear un conjunto para almacenar los vértices únicos
        Set<Vertex> uniqueVertices = new HashSet<>();

        // Paso 2: Recorrer todas las aristas y agregar los vértices al conjunto
        for (Edge edge : graph.getEdges()) {
            uniqueVertices.add(edge.getOrigin());
            uniqueVertices.add(edge.getEnd());
        }

        // Paso 3: Crear un nuevo grafo y agregar los vértices únicos
        Graph filteredGraph = new Graph();
        for (Vertex vertex : uniqueVertices) {
            filteredGraph.addVertex(vertex);
        }

        // Paso 4: Recorrer nuevamente todas las aristas y agregar solo aquellas que conecten vértices presentes en el nuevo grafo
        for (Edge edge : graph.getEdges()) {
            Vertex origin = edge.getOrigin();
            Vertex end = edge.getEnd();

            if (filteredGraph.getVertex().contains(origin) && filteredGraph.getVertex().contains(end)) {
                filteredGraph.addEdge(origin, end, edge.getValue());
            }
        }
        //Y si lo ordenamos por page rank????
        return filteredGraph;
    }

    private Graph createGraphWithPageRank() {
        GrafoCaminos grafoCamino = new GrafoCaminos(caminoDao.listaCaminosQuery(""), sucursalDao.listaSucursalesQuery(""));
        Graph graph = grafoCamino.getGrafo();

        // Paso 1: Crear un conjunto para almacenar los vértices únicos
        Set<Vertex> uniqueVertices = new HashSet<>();

        // Paso 2: Recorrer todas las aristas y agregar los vértices al conjunto
        for (Edge edge : graph.getEdges()) {
            uniqueVertices.add(edge.getOrigin());
            uniqueVertices.add(edge.getEnd());
        }

        // Paso 3: Crear un nuevo grafo y agregar los vértices únicos
        Graph filteredGraph = new Graph();
        for (Vertex vertex : uniqueVertices) {
            filteredGraph.addVertex(vertex);
        }

        // Paso 4: Recorrer nuevamente todas las aristas y agregar solo aquellas que conecten vértices presentes en el nuevo grafo
        for (Edge edge : graph.getEdges()) {
            Vertex origin = edge.getOrigin();
            Vertex end = edge.getEnd();

            if (filteredGraph.getVertex().contains(origin) && filteredGraph.getVertex().contains(end)) {
                filteredGraph.addEdge(origin, end, edge.getValue());
            }
        }

        // Obtener la lista de vértices ordenados por PageRank
        List<Vertex> sortedVertices = calculatePageRank(0.85, 100, filteredGraph);

        // Actualizar el grafo con la nueva lista ordenada de vértices
        filteredGraph.updateVertices(sortedVertices);

        return filteredGraph;
    }

    private List<Vertex> calculatePageRank(double dampingFactor, int iterations, Graph graph) {
        // Asignar valor inicial de 1.0 a cada vértice
        for (Vertex vertex : graph.getVertex()) {
            vertex.setPageRank(1.0);
        }

        // Realizar las iteraciones para calcular el PageRank
        for (int i = 0; i < iterations; i++) {
            // Copiar los valores de PageRank actuales para cada vértice
            Map<Vertex, Double> currentPageRanks = new HashMap<>();
            for (Vertex vertex : graph.getVertex()) {
                currentPageRanks.put(vertex, vertex.getPageRank());
            }

            // Calcular los nuevos PageRank en esta iteración
            for (Vertex vertex : graph.getVertex()) {
                double sum = 0.0;
                List<Vertex> neighbours = graph.getNeighbours(vertex);
                for (Vertex neighbour : neighbours) {
                    int outDegree = graph.gradoSalida(neighbour);
                    if (outDegree > 0) {
                        sum += currentPageRanks.get(neighbour) / outDegree;
                    }
                }
                double newPageRank = (1 - dampingFactor) + dampingFactor * sum;
                vertex.setPageRank(newPageRank);
            }
        }

        // Usar un conjunto HashSet para evitar duplicados y mantener el orden según el PageRank
        Set<Vertex> uniqueVertices = new HashSet<>(graph.getVertex());

        // Convertir el conjunto en una lista y ordenarla según el PageRank descendente
        List<Vertex> sortedVertices = new ArrayList<>(uniqueVertices);
        sortedVertices.sort((v1, v2) -> Double.compare(v2.getPageRank(), v1.getPageRank()));

        return sortedVertices;
    }

    private void generateCoordinates() {
        vertexPositions = new HashMap<>();

         int x = 10;
        int y = 240;
        int[] yValues = {240, 90, 390,240,90,390};
        int [] xValues = {150, 0, 0};

        int i = 0;
        for (Vertex vertex : graph.getVertex()) {

            // Verificar si el valor de y debe alterna 
            y = yValues[i % 6];
            // Agregar la posición actual del vértice al mapa de vertexPositions
            if ((i % 3) == 0) {
                vertexPositions.put(vertex, new Point(x + 75, y));
            } else {
                vertexPositions.put(vertex, new Point(x, y));
            }
            x += xValues[i % 3];

            i++;
            // Imprimir las coordenadas actuales (opcional, para verificar)
            // System.out.println("X= " + x + " *** Y= " + y);
        }
    }

    public void dibujarGrafo(Graphics2D g2d) {

        // Dibujar las aristas con flechas
        for (Edge edge : graph.getEdges()) {
            Vertex origin = edge.getOrigin();
            Vertex end = edge.getEnd();

            Point originPosition = vertexPositions.get(origin);
            int x1 = (int) originPosition.getX() + 10;
            int y1 = (int) originPosition.getY() - 3;

            Point endPosition = vertexPositions.get(end);
            int x2 = (int) endPosition.getX() - 2;
            int y2 = (int) endPosition.getY() - 4;

            g2d.setColor(Color.RED);
            drawArrow(g2d, x1, y1, x2, y2);
            g2d.drawString(String.valueOf(edge.getValue()), (x1 + x2) / 2, (y1 + y2) / 2);
        }
        // Dibujar los vértices
        for (Vertex vertex : graph.getVertex()) {
            Point position = vertexPositions.get(vertex);
            int x = (int) position.getX();
            int y = (int) position.getY();

            g2d.setColor(Color.BLUE);
            g2d.fillOval(x - 5, y - 5, 25, 25);
            g2d.drawString(nombreSucursal(vertex.getValue()), x - 10, y - 15);
        }
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine(x1, y1, x2, y2);
        g2d.fillPolygon(new int[]{x2, (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6)), (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6))},
                new int[]{y2, (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6)), (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6))}, 3);
    }

    private String nombreSucursal(int id) {
        List<Sucursales> lista = sucursalDao.listaSucursalesQuery("" + id);
        return lista.isEmpty() ? "" : lista.get(0).getNombre();

    }

}
