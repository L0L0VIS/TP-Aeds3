import java.util.Scanner;
import Entidades.Serie;

public class MenuSéries 
{
    
    ArquivoSerie arqSéries;
    private static Scanner console = new Scanner(System.in);

    public MenuSéries() throws Exception 
    {
        arqSéries = new ArquivoSerie();
    }

    public void menu() 
    {

        int opcao;
        do 
        {

            System.out.println("\nPUCFlix 1.0");
            System.out.println("-----------");
            System.out.println("> Início > Séries");
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
                    incluirSérie();
                    break;
                case 2:
                    buscarSérie();
                    break;
                case 3:
                    alterarSérie();
                    break;
                case 4:
                    excluirSérie();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }


    public void buscarSérie() 
    {
        System.out.println("\nBusca de Série");
        String nome;

        System.out.print("\nNome da Serie: ");
        nome = console.nextLine();  // Lê o Nome digitado pelo usuário

        if(nome.isEmpty())
        {
            return; 
        }


        try 
        {
            Serie Série = arqSéries.read(nome);  // Chama o método de leitura da classe Arquivo
            if (Série != null) 
            {
                mostraSérie(Série);  // Exibe os detalhes do Série encontrado
            } 
            else 
            {
                System.out.println("Série não encontrado.");
            }
        } 
        catch(Exception e) 
        {
            System.out.println("Erro do sistema. Não foi possível buscar o Série!");
            e.printStackTrace();
        }
    }   


    public void incluirSérie() 
    {
        System.out.println("\nInclusão de Série");
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

        System.out.print("\nConfirma a inclusão da Série? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if(resp=='S' || resp=='s') 
        {
            try 
            {
                Serie c = new Serie(nome, ano, sinopse, streaming);
                arqSéries.create(c);
                System.out.println("Série incluída com sucesso.");
            } 
            catch(Exception e) 
            {
                System.out.println("Erro do sistema. Não foi possível incluir o Série!");
            }
        }
    }

    public void alterarSérie() 
    {
        System.out.println("\nAlteração de Série");
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
            Serie Série = arqSéries.read(Nome);

            if (Série != null) 
            {
                System.out.println("Série encontrada:");
                mostraSérie(Série);  // Exibe os dados do Série para confirmação

                // Alteração de nome
                System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                String novoNome = console.nextLine();

                if (!novoNome.isEmpty()) 
                {
                    Série.setNome(novoNome);  // Atualiza o nome se fornecido
                }

                // Alteração de Sinopse
                System.out.print("Nova sinopse (deixe em branco para manter a anterior): ");
                String novaSinopse = console.nextLine();

                if (!novaSinopse.isEmpty()) 
                {
                    Série.setSinopse(novaSinopse);  // Atualiza a Sinopse se fornecida
                }

                // Alteração de Streaming
                System.out.print("\nNovo Streaming (deixe em branco para manter o anterior): ");
                String novoStreaming = console.nextLine();

                if (!novoStreaming.isEmpty()) 
                {
                    Série.setStreaming(novoStreaming);  // Atualiza o Streaming se fornecido
                }

                // Alteração de ano
                System.out.print("Novo ano de lançamento (deixe em branco para manter o anterior): ");
                String novoAno = console.nextLine();

                if (!novoAno.isEmpty()) 
                {
                    try 
                    {
                        Série.setAno(Integer.parseInt(novoAno));  // Atualiza o ano de lançamento se fornecido
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
                    boolean alterado = arqSéries.update(Série);
                    if (alterado) {
                        System.out.println("Série alterado com sucesso.");
                    } else {
                        System.out.println("Erro ao alterar o Série.");
                    }
                } else {
                    System.out.println("Alterações canceladas.");
                }
            } else {
                System.out.println("Série não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível alterar o Série!");
            e.printStackTrace();
        }
        
    }


    public void excluirSérie() {
        System.out.println("\nExclusão de Série");
        String nome;

        System.out.print("\nnome (Deixar em branco para cancelar): ");
        nome = console.nextLine();  // Lê o nome digitado pelo usuário

        if(nome.isEmpty())
        {
            return; 
        }

        try {
            // Tenta ler o Série com o ID fornecido
            Serie Série = arqSéries.read(nome);
            if (Série != null) {
                System.out.println("Série encontrado:");
                mostraSérie(Série);  // Exibe os dados do Série para confirmação

                System.out.print("\nConfirma a exclusão do Série? (S/N) ");
                char resp = console.next().charAt(0);  // Lê a resposta do usuário

                if (resp == 'S' || resp == 's') {
                    boolean excluido = arqSéries.delete(nome);  // Chama o método de exclusão no arquivo
                    if (excluido) {
                        System.out.println("Série excluído com sucesso.");
                    } else {
                        System.out.println("Erro ao excluir o Série.");
                    }
                } else {
                    System.out.println("Exclusão cancelada.");
                }
            } else {
                System.out.println("Série não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro do sistema. Não foi possível excluir o Série!");
            e.printStackTrace();
        }
    }


    public void mostraSérie(Serie Série) {
    if (Série != null) {
        System.out.println("\nDetalhes do Série:");
        System.out.println("----------------------");
        System.out.printf("Nome......: %s%n", Série.getNome());
        System.out.printf("Ano de lançamento: %s%n", Série.getAno());
        System.out.printf("Sinopse.......: %s%n", Série.getSinopse());
        System.out.printf("Streaming...: %s%n", Série.getStreaming());
        System.out.println("----------------------");
    }
}
}