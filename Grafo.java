import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;

    private ArrayList<Aresta<T>> arestas;

    public Grafo() {

        this.vertices = new ArrayList<Vertice<T>>();

        this.arestas = new ArrayList<Aresta<T>>();
    }

    public void removerUsuario(T usuario) {
        Vertice<T> vertice = getUsuario(usuario);

        if (vertice == null) {
            System.out.println("❌ Usuário não encontrado!");
            return;
        }

        // Remove todas as arestas associadas
        ArrayList<Aresta<T>> arestasParaRemover = new ArrayList<>();

        // Coleta arestas de entrada
        arestasParaRemover.addAll(vertice.getArestasEntrada());

        // Coleta arestas de saída
        arestasParaRemover.addAll(vertice.getArestasSaida());

        // Remove as arestas
        for (Aresta<T> aresta : arestasParaRemover) {
            this.arestas.remove(aresta);
            aresta.getInicio().removerArestaSaida(aresta);
            aresta.getFim().removerArestaEntrada(aresta);
        }

        // Remove o vértice
        this.vertices.remove(vertice);

        System.out.println("Usuário removido com sucesso!");
    }

    public ArrayList<Vertice<T>> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Aresta<T>> getArestas() {
        return arestas;
    }

    public void setArestas(ArrayList<Aresta<T>> arestas) {
        this.arestas = arestas;
    }

    public void addUsuario(T dado) {

        Vertice<T> novoVertice = new Vertice<T>(dado);

        this.vertices.add(novoVertice);

        System.out.println("Usuario cadastrado com sucesso!!  =D");
    }

    public void addSeguidor(T usuario1, T usuario2) {

        Vertice<T> inicio = this.getUsuario(usuario1);

        Vertice<T> fim = this.getUsuario(usuario2);

        Aresta<T> aresta = new Aresta<>(inicio, fim);

        inicio.adicionarArestaSaida(aresta);

        fim.adicionarArestaEntrada(aresta);

        this.arestas.add(aresta);

    }

    public Vertice<T> getUsuario(T dado) {

        Vertice<T> vertice = null;

        for (int i = 0; i < this.vertices.size(); i++) {

            if (this.vertices.get(i).getDado().equals(dado)) {

                vertice = this.vertices.get(i);

                break;
            }
        }

        return vertice;

    }

    public Vertice<T> buscarUsuario(T dado) {
        // Percorre todos os vértices
        for (Vertice<T> vertice : this.vertices) {
            if (vertice.getDado().equals(dado)) {
                return vertice; // Encontrou
            }
        }
        return null; // Não encontrou
    }

    public void encontrarCaminho(T origem, T destino) {
        Vertice<T> verticeOrigem = getUsuario(origem);
        Vertice<T> verticeDestino = getUsuario(destino);

        if (verticeOrigem == null || verticeDestino == null) {
            System.out.println("❌ Usuário não encontrado!");
            return;
        }

        if (verticeOrigem.equals(verticeDestino)) {
            System.out.println("✅ Origem e destino são a mesma pessoa!");
            return;
        }

        // BFS para encontrar caminho
        Queue<Vertice<T>> fila = new LinkedList<>();
        HashMap<Vertice<T>, Vertice<T>> anterior = new HashMap<>();
        HashSet<Vertice<T>> visitados = new HashSet<>();

        fila.add(verticeOrigem);
        visitados.add(verticeOrigem);
        anterior.put(verticeOrigem, null);

        boolean encontrou = false;

        while (!fila.isEmpty() && !encontrou) {
            Vertice<T> atual = fila.poll();

            if (atual.equals(verticeDestino)) {
                encontrou = true;
                break;
            }

            // Visita seguidores (arestas de saída)
            for (Aresta<T> aresta : atual.getArestasSaida()) {
                Vertice<T> vizinho = aresta.getFim();
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                    anterior.put(vizinho, atual);
                }
            }
        }

        if (!encontrou) {
            System.out.println("❌ Não há caminho entre " + origem + " e " + destino);
            return;
        }

        // Reconstrói o caminho
        ArrayList<Vertice<T>> caminho = new ArrayList<>();
        Vertice<T> atual = verticeDestino;

        while (atual != null) {
            caminho.add(0, atual); // Adiciona no início
            atual = anterior.get(atual);
        }

        // Exibe o caminho
        System.out.println("\n✅ CAMINHO ENCONTRADO:");
        System.out.println("📍 Distância: " + (caminho.size() - 1) + " conexão(ões)\n");

        for (int i = 0; i < caminho.size(); i++) {
            System.out.print("   " + caminho.get(i).getDado());
            if (i < caminho.size() - 1) {
                System.out.print(" → ");
            }
        }
        System.out.println("\n");
    }

    public void listarTodosUsuarios() {
        if (vertices.isEmpty()) {
            System.out.println("⚠️ Nenhum usuário cadastrado no grafo.");
            return;
        }

        System.out.println("📋 Usuários cadastrados no grafo:");
        for (Vertice<T> v : vertices) {
            System.out.println("• " + v.getDado());
        }
    }

    public void exibirPerfilCompleto(T usuario) {
    Vertice<T> vertice = getUsuario(usuario);
    
    if (vertice == null) {
        System.out.println("❌ Usuário não encontrado!");
        return;
    }
    
    T dados = vertice.getDado();
    
    System.out.println("\n╔════════════════════════════════════════╗");
    System.out.println("║           PERFIL DO USUÁRIO            ║");
    System.out.println("╚════════════════════════════════════════╝");
    System.out.println(dados);
    
    // Seguidores
    ArrayList<Aresta<T>> seguidores = vertice.getArestasEntrada();
    System.out.println("\n👥 SEGUIDORES (" + seguidores.size() + "):");
    if (seguidores.isEmpty()) {
        System.out.println("   (Nenhum seguidor ainda)");
    } else {
        for (Aresta<T> aresta : seguidores) {
            System.out.println("   • " + aresta.getInicio().getDado().toString().split("\n")[0]);
        }
    }
    
    // Seguindo
    ArrayList<Aresta<T>> seguindo = vertice.getArestasSaida();
    System.out.println("\n✨ SEGUINDO (" + seguindo.size() + "):");
    if (seguindo.isEmpty()) {
        System.out.println("   (Não está seguindo ninguém)");
    } else {
        for (Aresta<T> aresta : seguindo) {
            System.out.println("   • " + aresta.getFim().getDado().toString().split("\n")[0]);
        }
    }
}


}
