import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * Created by Jairo on 4/2/2017.
 */
public class Wallet {

    private static Scanner in = new Scanner(System.in);
    private static String wallInfo;
    private static char choice;

    public static char WalletHomeScreen(){

        System.out.println("Welcome To Your wallet File!");
        //  do{

        System.out.println("Enter the Information you want to store: ");
        wallInfo = in.nextLine();

        try{


            PrintWriter writer = new PrintWriter("Wallet-File.txt" , "UTF-8");
            writer.println(wallInfo);

            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }


        System.out.println("If you want to exit to the main menu, enter the number '0'");
        System.out.println("However, if you want to store other wallet information, enter any charachter other than '0'");
        choice = in.nextLine().charAt(0);

        if(choice == '0'){
            choice = MenuItem.mainMenu();
        }
        else{
            WalletHomeScreen();
        }


        //}while(choice != '0');
        return choice;


    }
}
