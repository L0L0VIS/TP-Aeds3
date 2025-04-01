package menus;


import arquivos.ArquivoEpisodio;
import arquivos.ArquivoSerie;
import entidades.*;

import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.*;

public class MenuEpisodio
{
    
    int id_serie = -1;
    String nome_serie = "";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
    ArquivoEpisodio arqEpisodio;
    ArquivoSerie arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodio() throws Exception 
    {
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

            Serie serie = arqSeries.read(nome_serie);  // Chama o método de leitura da classe Arquivo
            if (serie != null) 
            {
                id_serie = serie.getId();
                serie_defined = true;
            } 
            else 
            {
                System.out.println("Serie nao encontrada.");
            }

        }


    }

    public void menu() throws Exception 
    {

        if (id_serie == -1) {
            definir_serie();
        }

        int opcao;
        do 
        {

            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.print  ("> Início > Episodio (");
            System.out.print  (nome_serie);
            System.out.println(")");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("5) Redefinir Série");
            System.out.println("0) Voltar");

            System.out.print("\nOpçao: ");
            try 
            {
                opcao = Integer.valueOf(console.nextLine());
            } 
            catch(NumberFormatException e) 
            {
                opcao = -1;
            }

            switch (opcao) 
            {
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
                case 0:
                    break;
                default:
                    System.out.println("Opçao inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void incluirEpisodio() 
    {
        System.out.println("\nInclusao de Episodio");
        String nome = "";
        Short Duração = 0;
        String dataS = "";
        LocalDate dataLocal;
        int data = 0;
        Short Temporada = 0;
        boolean dadosCorretos = false;

        do 
        {
            System.out.print("\nNome: ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
        } while(nome.length()<1);


        do 
        {
            System.out.print("Duraçao: ");
            Duração = console.nextShort();
        } while(Duração == 0);

        do 
        {
            dadosCorretos = false;
            System.out.print("Data de lançamento (dd/mm/yyyy): ");
                
            console.nextLine(); // Limpar o buffer 

            dataS = console.nextLine();
            dataLocal = LocalDate.parse(dataS, formatter);

            data = (int) dataLocal.toEpochDay();
            dadosCorretos = true;

        } while(!dadosCorretos);

        do 
        {
            System.out.print("Temporada: ");
            Temporada = (short) ( console.nextInt() );
        } while(Temporada == 0);

        String respS = console.nextLine();

        System.out.print("\nConfirma a inclusao da Episodio? (S/N): ");
        respS = console.nextLine();
        //System.out.println(respS + " - Teste");
        char resp = respS.charAt(0);
        if(resp=='S' || resp=='s') 
        {
            try
            {
                Episodio c = new Episodio(id_serie, nome, Temporada, data, Duração);
                arqEpisodio.create(c);
                System.out.println("Episodio incluída com sucesso.");
            } 
            catch(Exception e) 
            {
                System.out.println("Erro do sistema. Nao foi possível incluir a Episodio!");
                System.err.println(e);
            }
        }
    }


    public void buscarEpisodio() 
    {
        System.out.println("\nBusca de Episodio");
        String nome;

        System.out.print("\nNome da Episodio: ");
        nome = console.nextLine();  // Lê o Nome digitado pelo usuário

        if(nome.isEmpty())
        {
            return; 
        }


        System.out.println("Buscando " + nome);
        try 
        {
            Episodio Episodio = arqEpisodio.read(nome);  // Chama o método de leitura da classe Arquivo
            if (Episodio != null) 
            {
                mostraEpisodio(Episodio);  // Exibe os detalhes do Episódio encontrado
            } 
            else 
            {
                System.out.println("Episodio nao encontrada.");
            }
        } 
        catch(Exception e) 
        {
            System.out.println("Erro do sistema. Nao foi possível buscar a Episodio!");
            e.printStackTrace();
        }
    }   

    public void alterarEpisodio() 
    {
        System.out.println("\nAlteraçao de Episodio");
        String nome;
        boolean nomeValido = false;

        do 
        {
            System.out.print("\nNome: ");
            nome = console.nextLine();  // Lê o nome digitado pelo usuário

            if(nome.isEmpty()) {
                return;
            }
            else {
                nomeValido = true;
            }
        } while (!nomeValido);


        try 
        {
            // Tenta ler o Série com o ID fornecido
            Episodio Episodio = arqEpisodio.read(nome);

            if (Episodio != null) 
            {
                System.out.println("Episodio encontrada:");
                mostraEpisodio(Episodio);  // Exibe os dados do Série para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();

                if (!novoNome.isEmpty()) 
                {
                    Episodio.setNome(novoNome);  // Atualiza o nome se fornecido
                }

                // Alteração de Duração
                System.out.print("Nova Duraçao (deixe em branco para manter a anterior): ");
                String novaDuração = console.nextLine();

                if (!novaDuração.isEmpty()) 
                {
                    Episodio.setDuracao(Short.parseShort(novaDuração));  // Atualiza a Duração se fornecida
                }

                // Alteração de Temporada
                System.out.print("\nNova Temporada (deixe em branco para manter o anterior): ");
                String novoTemporada = console.nextLine();

                if (!novoTemporada.isEmpty()) 
                {
                    Episodio.setTemporada(Short.parseShort(novoTemporada));  // Atualiza o Temporada se fornecido
                }

                // Alteração de ano
                System.out.print("Novo ano de lançamento (deixe em branco para manter o anterior): ");
                String novoAno = console.nextLine();

                if (!novoAno.isEmpty()) 
                {
                    try 
                    {
                        Episodio.setData_lancamento(Integer.parseInt(novoAno));  // Atualiza o ano de lançamento se fornecido
                    } 
                    catch (NumberFormatException e) 
                    {
                        System.err.println("Erro. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alteraçoes? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqEpisodio.update(Episodio);
                    if (alterado) {
                        System.out.println("Episodio alterado com sucesso.");
                        
                        nome = console.nextLine(); // Para corrigir um input extra
                    } else {
                        System.out.println("Erro ao alterar o Episodio.");
                    }
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
        String nome;

        System.out.print("\nNome (Deixar em branco para cancelar): ");
        nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
        {
            return; 
        }

        try {
            // Tenta ler o Série com o ID fornecido
            Episodio Episodio = arqEpisodio.read(nome);
            if (Episodio != null) {
                System.out.println("Episodio encontrado:");
                mostraEpisodio(Episodio);  // Exibe os dados do Série para confirmação

                System.out.print("\nConfirma a exclusao do Episodio? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqEpisodio.delete(nome);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Episodio excluído com sucesso.");

                        nome = console.nextLine(); // Para corrigir um input extra
                    } else {
                        System.out.println("Erro ao excluir o Episodio.");
                    }
                } else {
                    System.out.println("Exclusao cancelada.");
                }
            } else {
                System.out.println("Episodio nao encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Nao foi possível excluir o Episodio!");
            e.printStackTrace();
        }
    }


    public void mostraEpisodio(Episodio Episodio) {
    if (Episodio != null) {
        System.out.println("\nDetalhes do Episodio:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n",           Episodio.getNome());
        System.out.printf("Data de lançamento: %s%n",   LocalDate.ofEpochDay((long) Episodio.getData_lancamento()).format(formatter)  );
        System.out.printf("Temporada.......: %s%n",     Episodio.getTemporada());
        System.out.printf("Duraçao...: %s%n",           Episodio.getDuracao());
        System.out.println("----------------------");
    }
}
}
