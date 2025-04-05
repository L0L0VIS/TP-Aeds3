package menus;

import arquivos.ArquivoEpisodio;
import arquivos.ArquivoSerie;
import entidades.*;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.*;

public class MenuEpisodio {

    int id_serie = -1;
    String nome_serie = "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ArquivoEpisodio arqEpisodio;
    ArquivoSerie arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodio() throws Exception {
        arqEpisodio = new ArquivoEpisodio();
        arqSeries = new ArquivoSerie();
    }

    public void definir_serie() throws Exception {
        boolean serie_defined = false;

        while (!serie_defined) {
            System.out.print("Digite o nome da serie a ser acessada os episódios: ");
            nome_serie = console.nextLine();

            while (nome_serie.isEmpty()) {
                System.out.println("Erro de entrada: Digite novamente");
                nome_serie = console.nextLine();
            }

            Serie serie = arqSeries.read(nome_serie);
            if (serie != null) {
                id_serie = serie.getId();
                serie_defined = true;
            } else {
                System.out.println("Serie nao encontrada.");
            }
        }
    }

    public void menu() throws Exception {
        if (id_serie == -1) {
            definir_serie();
        }

        int opcao;
        do {
            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.print("> Início > Episodio (");
            System.out.print(nome_serie);
            System.out.println(")");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("5) Redefinir Série");
            System.out.println("0) Voltar");

            System.out.print("\nOpçao: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirEpisodio();
                    break;
                case 2:
                    buscarEpisodio();
                    break;
                case 3:
                    alterarEpisodio();
                    break;
                case 4:
                    excluirEpisodio();
                    break;
                case 5:
                    definir_serie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opçao inválida!");
                    break;
            }

        } while (opcao != 0);
    }
    
    public void incluirEpisodio() {
        System.out.println("\nInclusao de Episodio");
        String titulo;
        int duracao;
        String dataLancamento;
        int temporada;
        int numero;
        
        System.out.print("\nTítulo: ");
        titulo = console.nextLine();
        if (titulo.isEmpty()) return;
        
        System.out.print("Número do episódio: ");
        while (!console.hasNextInt()) {
            console.next();
            System.out.print("Digite um número válido: ");
        }
        numero = console.nextInt();
        console.nextLine();
        
        System.out.print("Temporada: ");
        while (!console.hasNextInt()) {
            console.next();
            System.out.print("Digite um número válido: ");
        }
        temporada = console.nextInt();
        console.nextLine();
        
        System.out.print("Data de lançamento (dd/MM/yyyy): ");
        dataLancamento = console.nextLine();
        LocalDate dataLocal;
        try {
            dataLocal = LocalDate.parse(dataLancamento, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Episódio nao cadastrado.");
            return;
        }
        
        System.out.print("Duraçao (minutos): ");
        while (!console.hasNextInt()) {
            console.next();
            System.out.print("Digite um número válido: ");
        }
        duracao = console.nextInt();
        console.nextLine();
        
        System.out.print("\nConfirma a inclusao do Episódio? (S/N): ");
        char resp = console.next().charAt(0);
        console.nextLine();
        
        if (resp == 'S' || resp == 's') {
            try {
                Episodio ep = new Episodio(id_serie, titulo, temporada, numero, dataLancamento, duracao);
                arqEpisodio.create(ep);
                System.out.println("Episódio incluído com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro do sistema. Nao foi possível incluir o episódio!");
                e.printStackTrace();
            }
        }
    }
    
    public void buscarEpisodio() {
        System.out.println("\nBusca de Episodio");
        System.out.print("\nNome do Episódio: ");
        String titulo = console.nextLine();
        
        if (titulo.isEmpty()) return;
        
        try {
            Episodio episodio = arqEpisodio.read(id_serie, titulo);
            if (episodio != null) {
                mostraEpisodio(episodio);
            } else {
                System.out.println("Episódio nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Nao foi possível buscar o Episódio!");
            e.printStackTrace();
        }
    }

    public void alterarEpisodio() {
        System.out.println("\nAlteraçao de Episodio");

        System.out.print("\nNome: ");
        String nome = console.nextLine();

        if (nome.isEmpty()) return;

        try {
            Episodio episodio = arqEpisodio.read(id_serie, nome);
            if (episodio != null) {
                System.out.println("Episodio encontrado:");
                mostraEpisodio(episodio);

                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();
                if (!novoNome.isEmpty()) episodio.setTitulo(novoNome);

                System.out.print("Nova Duraçao (deixe em branco para manter a anterior): ");
                String novaDuracao = console.nextLine();
                if (!novaDuracao.isEmpty()) episodio.setDuracao(Short.parseShort(novaDuracao));

                System.out.print("Nova Temporada (deixe em branco para manter a anterior): ");
                String novaTemporada = console.nextLine();
                if (!novaTemporada.isEmpty()) episodio.setTemporada(Short.parseShort(novaTemporada));

                System.out.print("Nova data de lançamento (dd/mm/yyyy): ");
                String novaData = console.nextLine();
                if (!novaData.isEmpty()) {
                    try {
                        episodio.setDataLancamento(novaData);
                    } catch (Exception e) {
                        System.out.println("Data inválida. Valor mantido.");
                    }
                }

                System.out.print("\nConfirma as alteraçoes? (S/N): ");
                char resp = console.next().charAt(0);
                console.nextLine(); // limpar buffer
                if (resp == 'S' || resp == 's') {
                    boolean alterado = arqEpisodio.update(episodio);
                    System.out.println(alterado ? "Episodio alterado com sucesso." : "Erro ao alterar o Episodio.");
                } else {
                    System.out.println("Alteraçoes canceladas.");
                }
            } else {
                System.out.println("Episodio nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Nao foi possível alterar o Episodio!");
            e.printStackTrace();
        }
    }

    public void excluirEpisodio() {
        System.out.println("\nExclusao de Episodio");
        System.out.print("\nTítulo (Deixar em branco para cancelar): ");
        String titulo = console.nextLine();
        
        if (titulo.isEmpty()) return;

        try {
            Episodio episodio = arqEpisodio.read(id_serie, titulo);
            if (episodio != null) {
                System.out.println("Episódio encontrado:");
                mostraEpisodio(episodio);

                System.out.print("\nConfirma a exclusao do Episódio? (S/N): ");
                char resp = console.next().charAt(0);
                console.nextLine();

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodio.delete(episodio.getId());
                    System.out.println(excluido ? "Episódio excluído com sucesso." : "Erro ao excluir o Episódio.");
                } else {
                    System.out.println("Exclusao cancelada.");
                }
            } else {
                System.out.println("Episódio nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Nao foi possível excluir o Episódio!");
            e.printStackTrace();
        }
    }

    public void mostraEpisodio(Episodio episodio) {
        if (episodio != null) {
            System.out.println("\nDetalhes do Episódio:");
            System.out.println("----------------------");
            System.out.printf("Título.............: %s%n", episodio.getTitulo());
            System.out.printf("Temporada..........: %d%n", episodio.getTemporada());
            System.out.printf("Número.............: %d%n", episodio.getNumero());
            System.out.printf("Data de lançamento.: %s%n", episodio.getDataLancamento());
            System.out.printf("Duraçao............: %d min%n", episodio.getDuracao());
            System.out.println("----------------------");
        }
    }
}
