/**
 * Created by Jairo on 3/25/2017.
 */
import java.util.*;
import java.io.*;
import java.lang.*;


public class  main{

    private static Scanner in = new Scanner(System.in);
    private static char choice;
    private static String contactInfo;
    private static String newFold;


    public static void newFolder(){
        do{
            System.out.println("Enter the Name of the new folder you want to create: ");
            newFold = in.nextLine();

            System.out.println("Enter the Information you want to store: ");
            String newFoldInfo = in.nextLine();

            System.out.println("If you want to exit to the main menu, enter the number '0'");
            System.out.println("However, if you want to store other Contact information, enter any character other than '0'");
            choice = in.nextLine().charAt(0);

            if(choice == '0'){
                choice = MenuItem.mainMenu();
            }

            try{


                PrintWriter writer = new PrintWriter(newFold + ".txt" , "UTF-8");
                writer.println(newFoldInfo);

                writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }

        }while(choice != '0');
    }



    public static void main(String[] args) {

       // choice = menuItem();

        choice = MenuItem.mainMenu();

        while(choice != '0'){
            if (choice == '1'){
                choice = Passwords.PasswordHomeScreen();
            }
            else if (choice == '2'){
                choice= Wallet.WalletHomeScreen();
            }
            else if (choice == '3'){
                choice = Contacs.ContatsHomeScreen();
            }
            else if (choice == '4'){
                newFolder();
            }
            else if (choice == '0'){
                System.out.println("You decided to quit the program. Goodbye :)");
            }
            else{
                System.out.println("Invalid input. Try again.");
                //menuItem();
            }
        }
    }
}
