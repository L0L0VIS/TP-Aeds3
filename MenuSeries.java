import java.util.Scanner;
import entidades.Serie;

public class MenuSeries 
{
    
    ArquivoSerie arqSeries;
    private static Scanner console = new Scanner(System.in);

    public MenuSeries() throws Exception 
    {
        arqSeries = new ArquivoSerie();
    }

    public void menu() 
    {

        int opcao;
        do 
        {

            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início > Series");
            System.out.println("\n1) Incluir");
            System.out.println("2) Buscar");
            System.out.println("3) Alterar");
            System.out.println("4) Excluir");
            System.out.println("0) Voltar");

            System.out.print("\nOpção: ");
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
                    incluirSerie();
                    break;
                case 2:
                    buscarSerie();
                    break;
                case 3:
                    alterarSerie();
                    break;
                case 4:
                    excluirSerie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }


    public void buscarSerie() 
    {
        System.out.println("\nBusca de Serie");
        String nome;

        System.out.print("\nNome da Serie: ");
        nome = console.nextLine();  // Lê o Nome digitado pelo usuário

        if(nome.isEmpty())
        {
            return; 
        }


        try 
        {
            Serie Serie = arqSeries.read(nome);  // Chama o método de leitura da classe Arquivo
            if (Serie != null) 
            {
                mostraSerie(Serie);  // Exibe os detalhes da Série encontrada
            } 
            else 
            {
                System.out.println("Serie não encontrado.");
            }
        } 
        catch(Exception e) 
        {
            System.out.println("Erro do sistema. Não foi possível buscar o Serie!");
            e.printStackTrace();
        }
    }   


    public void incluirSerie() 
    {
        System.out.println("\nInclusão de Serie");
        String nome = "";
        String sinopse = "";
        int ano = 0;
        String streaming = "";
        boolean dadosCorretos = false;

        do 
        {
            System.out.print("\nNome (vazio para cancelar): ");
            nome = console.nextLine();
            if(nome.length()==0)
                return;
        } while(nome.length()<1);

        do 
        {
            System.out.print("sinopse: ");
            sinopse = console.nextLine();
        } while(sinopse.length()==0);

        do 
        {
            dadosCorretos = false;
            System.out.print("Ano: ");
            if (console.hasNextInt()) 
            {
                ano = console.nextInt();
                dadosCorretos = true;
            } 
            else 
            {
                System.err.println("Ano! Por favor, insira um número válido.");
            }
            console.nextLine(); // Limpar o buffer 
        } while(!dadosCorretos);

        do 
        {
            System.out.print("streaming: ");
            streaming = console.nextLine();
        } while(streaming.length()==0);

        System.out.print("\nConfirma a inclusão da Serie? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') 
        {
            try 
            {
                Serie c = new Serie(nome, ano, sinopse, streaming);
                arqSeries.create(c);
                System.out.println("Serie incluída com sucesso.");
            } 
            catch(Exception e) 
            {
                System.out.println("Erro do sistema. Não foi possível incluir o Serie!");
                System.err.println(e);
            }
        }
    }

    public void alterarSerie() 
    {
        System.out.println("\nAlteração de Serie");
        String Nome;
        boolean NomeValido = false;

        do 
        {
            System.out.print("\nNome: ");
            Nome = console.nextLine();  // Lê o Nome digitado pelo usuário

            if(Nome.isEmpty())
                return; 

            // Validação do Nome (11 dígitos e composto apenas por números)
            if (Nome.length() < 2) 
            {
                NomeValido = true;  // Nome válido
            } 
            else 
            {
                System.out.println("Nome inválido. O Nome deve conter mais de  2 dígitos.");
            }
        } while (!NomeValido);


        try 
        {
            // Tenta ler o Série com o ID fornecido
            Serie Serie = arqSeries.read(Nome);

            if (Serie != null) 
            {
                System.out.println("Serie encontrada:");
                mostraSerie(Serie);  // Exibe os dados do Série para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();

                if (!novoNome.isEmpty()) 
                {
                    Serie.setNome(novoNome);  // Atualiza o nome se fornecido
                }

                // Alteração de Sinopse
                System.out.print("Nova sinopse (deixe em branco para manter a anterior): ");
                String novaSinopse = console.nextLine();

                if (!novaSinopse.isEmpty()) 
                {
                    Serie.setSinopse(novaSinopse);  // Atualiza a Sinopse se fornecida
                }

                // Alteração de Streaming
                System.out.print("\nNovo Streaming (deixe em branco para manter o anterior): ");
                String novoStreaming = console.nextLine();

                if (!novoStreaming.isEmpty()) 
                {
                    Serie.setStreaming(novoStreaming);  // Atualiza o Streaming se fornecido
                }

                // Alteração de ano
                System.out.print("Novo ano de lançamento (deixe em branco para manter o anterior): ");
                String novoAno = console.nextLine();

                if (!novoAno.isEmpty()) 
                {
                    try 
                    {
                        Serie.setAno(Integer.parseInt(novoAno));  // Atualiza o ano de lançamento se fornecido
                    } 
                    catch (NumberFormatException e) 
                    {
                        System.err.println("Erro. Valor mantido.");
                    }
                }

                // Confirmação da alteração
                System.out.print("\nConfirma as alterações? (S/N) ");
                char resp = console.next().charAt(0);
                if (resp == 'S' || resp == 's') {
                    // Salva as alterações no arquivo
                    boolean alterado = arqSeries.update(Serie);
                    if (alterado) {
                        System.out.println("Serie alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o Serie.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o Serie!");
            e.printStackTrace();
        }
        
    }


    public void excluirSerie() {
        System.out.println("\nExclusão de Serie");
        String nome;

        System.out.print("\nnome (Deixar em branco para cancelar): ");
        nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
        {
            return; 
        }

        try {
            // Tenta ler o Série com o ID fornecido
            Serie Serie = arqSeries.read(nome);
            if (Serie != null) {
                System.out.println("Serie encontrado:");
                mostraSerie(Serie);  // Exibe os dados do Série para confirmação

                System.out.print("\nConfirma a exclusão do Serie? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqSeries.delete(nome);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Serie excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o Serie.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Serie não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o Serie!");
            e.printStackTrace();
        }
    }


    public void mostraSerie(Serie Serie) {
    if (Serie != null) {
        System.out.println("\nDetalhes do Serie:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", Serie.getNome());
        System.out.printf("Ano de lançamento: %s%n", Serie.getAno());
        System.out.printf("Sinopse.......: %s%n", Serie.getSinopse());
        System.out.printf("Streaming...: %s%n", Serie.getStreaming());
        System.out.println("----------------------");
    }
}
}