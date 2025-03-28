package menus;


import arquivos.ArquivoEpisodio;
import arquivos.ArquivoSerie;
import Entidades.*;

import java.util.Scanner;

public class MenuEpisodio
{
    
    ArquivoEpisodio arqEpisodio;
    private static Scanner console = new Scanner(System.in);

    public MenuEpisodio() throws Exception 
    {
        arqEpisodio = new ArquivoEpisodio();
    }

    public void menu() 
    {

        int opcao;
        do 
        {

            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início > Episodio");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
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
        String nomeS = "";
        Short Duração = 0;
        int data = 0;
        Short Temporada = 0;
        boolean dadosCorretos = false;
        ArquivoSerie arqSerie;


        try {
            arqSerie = new ArquivoSerie();  
            if (!arqSerie.temSeries()) 
            {
                System.out.println("Erro: Nenhuma série cadastrada! Cadastre uma série antes de incluir episódios.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Erro ao acessar os arquivos de séries.");
            return;
        }
    
        do {
            System.out.print("\nNome da serie (vazio para cancelar): ");
            nomeS = console.nextLine();
            if (nomeS.isEmpty()) return;
    
            try {
                Serie serie = arqSerie.read(nomeS);
                if (serie == null) {
                    System.out.println("Erro: Série não encontrada! Digite um nome válido.");
                    nomeS = "";
                }
            } catch (Exception e) {
                System.out.println("Erro ao buscar série.");
                return;
            }
        } while (nomeS.isEmpty());
        do 
        {
            System.out.print("\nNome: ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
        } while(nome.length()<1);


        do 
        {
            System.out.print("Duração: ");
            Duração = console.nextShort();
        } while(Duração == 0);

        do 
        {
            dadosCorretos = false;
            System.out.print("data de lancamento: ");
            if (console.hasNextInt()) 
            {
                data = console.nextInt();
                dadosCorretos = true;
            } 
            else 
            {
                System.err.println("Data! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        do 
        {
            System.out.print("Temporada: ");
            Temporada = console.nextShort();
        } while(Temporada == 0);

        System.out.print("\nConfirma a inclusao da Episodio? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') 
        {
            try 
            {
                Episodio c = new Episodio(0, nome, Temporada, data, Duração);
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
                mostraEpisodio(Episodio);  // Exibe os detalhes da Série encontrada
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
                System.out.print("Nova Duração (deixe em branco para manter a anterior): ");
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
        System.out.printf("Ano de lançamento: %s%n",    Episodio.getData_lancamento());
        System.out.printf("Temporada.......: %s%n",       Episodio.getTemporada());
        System.out.printf("Duração...: %s%n",         Episodio.getDuracao());
        System.out.println("----------------------");
    }
}
}
