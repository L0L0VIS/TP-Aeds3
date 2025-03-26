import java.util.Scanner;

import menus.*;


public class Principal 
{
    public static void main(String[] args) 
    {

        Scanner console;

        try 
        {
            console = new Scanner(System.in);
            int opcao;

            do 
            {

                System.out.println("\n\nPUCFlix 1.0");
                System.out.println("---------------");
                System.out.println("> Início");
                System.out.println("\n1) Séries");
                System.out.println("2) Episódios");
                System.out.println("3) Atores");
                System.out.println("0) Sair");

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
                        (new MenuSeries()).menu();
                        break;
                    case 2:
                        (new MenuEpisodio()).menu();
                        break;      
                    case 0:
                        break;
                    default:
                        System.out.println("Opçao inválida!");
                        break;
                }

            } while (opcao != 0);



        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}