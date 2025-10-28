import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean menu = true;

        Scanner scan = new Scanner(System.in);

        Grafo<Usuario> usuGrafo = new Grafo<Usuario>();

        int escolha = -1, idade;

        String nome, email;

        exibirBanner();

        while (menu) {
            exibirMenu();

            escolha = scan.nextInt();
            scan.nextLine();

            switch (escolha) {

                case 1:

                    System.out.println("Qual é o nome do usuario?");

                    nome = scan.nextLine();

                    System.out.println("Qual é o email do " + nome + "?");

                    email = scan.nextLine();

                    System.out.println("Qual é a idade do " + nome + "?");

                    idade = scan.nextInt();
                    scan.nextLine();

                    Usuario novoUsuario = new Usuario(nome, email, idade);

                    usuGrafo.addUsuario(novoUsuario);

                    break;

                case 2:
                    System.out.println("\nREMOVER PESSOA");
                    System.out.println("___________________________________________");

                    System.out.print("Nome da pessoa a remover: ");
                    nome = scan.nextLine();

                    Usuario usuarioRemover = buscarUsuarioPorNome(usuGrafo, nome);

                    if (usuarioRemover != null) {
                        usuGrafo.removerUsuario(usuarioRemover);
                    } else {
                        System.out.println("Usuário não encontrado!");
                    }
                    break;

                case 3:

                    System.out.println("\n🤝 CRIAR AMIZADE");
                    System.out.println("_________________________________________");

                    System.out.print("Nome da primeira pessoa: ");
                    String nome1 = scan.nextLine();

                    System.out.print("Nome da segunda pessoa: ");
                    String nome2 = scan.nextLine();

                    Usuario usuario1 = buscarUsuarioPorNome(usuGrafo, nome1);
                    Usuario usuario2 = buscarUsuarioPorNome(usuGrafo, nome2);

                    if (usuario1 != null && usuario2 != null) {
                        usuGrafo.addSeguidor(usuario1, usuario2);
                        System.out.println("✅ " + nome1 + " agora segue " + nome2 + "!");
                    } else {
                        System.out.println("❌ Um ou ambos os usuários não foram encontrados!");
                    }
                    break;
                case 4:
                    System.out.println("\n👥 LISTA DE TODAS AS PESSOAS");
                    System.out.println("_________________________");
                    usuGrafo.listarTodosUsuarios();
                    break;

                    case 5: // Ver amigos de alguém
                        System.out.println("\n👤 VER PERFIL E CONEXÕES");
                        System.out.println("________________________________");
                        
                        System.out.print("Nome da pessoa: ");
                        nome = scan.nextLine();

                        Usuario usuario = buscarUsuarioPorNome(usuGrafo, nome);
                        
                        if (usuario != null) {
                            usuGrafo.exibirPerfilCompleto(usuario);
                        } else {
                            System.out.println("❌ Usuário não encontrado!");
                        }
                        break;

                        default: 
                            menu = false;
                        break;
            }

        }

    }

    private static void exibirMenu() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      REDE SOCIAL - ESTRUTURA GRAFO     ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Adicionar pessoa                    ║");
        System.out.println("║ 2. Remover pessoa                      ║");
        System.out.println("║ 3. Criar amizade                       ║");
        System.out.println("║ 4. Listar todas as pessoas             ║");
        System.out.println("║ 5. Ver amigos de alguém                ║");
        System.out.println("║ 0. Sair                                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirBanner() {
        limparTela();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║                                                    ║");
        System.out.println("║      REDE SOCIAL - ESTRUTURA DE DADOS GRAFO        ║");
        System.out.println("║                                                    ║");
        System.out.println("║     Sistema de gerenciamento de conexões sociais   ║");
        System.out.println("║     Utilizando algoritmos de grafos                ║");
        System.out.println("║                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void limparTela() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Se falhar, apenas imprime linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    private static Usuario buscarUsuarioPorNome(Grafo<Usuario> grafo, String nome) {
        for (Vertice<Usuario> vertice : grafo.getVertices()) {
            if (vertice.getDado().getNome().equalsIgnoreCase(nome)) {
                return vertice.getDado();
            }
        }
        return null;
    }
}