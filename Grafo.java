import java.util.ArrayList;

public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;

    private ArrayList<Aresta<T>> arestas;

    public Grafo(ArrayList<Vertice<T>> vertices, ArrayList<Aresta<T>> arestas) {

        this.vertices = new ArrayList<Vertice<T>>();

        this.arestas = new ArrayList<Aresta<T>>();
    }


    public void addUsuario(T dado){

        Vertice<T> novoVertice = new Vertice<T>(dado);

        this.vertices.add(novoVertice);
    }

    public void addSeguidor(double peso, T usuario1, T usuario2){

        Vertice<T> inicio = this.getUsuario(usuario1);

        Vertice<T> fim = this.getUsuario(usuario2);

        Aresta<T> aresta = new Aresta<>(peso, inicio, fim);

        inicio.adicionarArestaSaida(aresta);

        fim.adicionarArestaEntrada(aresta);

        this.arestas.add(aresta);


    }

    public Vertice<T> getUsuario(T dado){

        Vertice<T> vertice = null;

        for(int i = 0; i < this.vertices.size(); i ++){

            if(this.vertices.get(i).getDado().equals(dado)){

                vertice = this.vertices.get(i);

                break;
            }
        }

        return vertice;

    }
    
}
