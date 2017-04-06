import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Created by jairo_000 on 4/3/2017.
 */
public class MenuItem {
    private static char choice;
    private static Scanner in = new Scanner(System.in);

    public static char mainMenu(){

        //Menu Choices
        System.out.println("Choose menu item from the options below: ");
        System.out.println();

        System.out.println("[1]\t Passwords");
        System.out.println("[2]\t Wallet");
        System.out.println("[3]\t Contacts");
        System.out.println("[4]\t Create a new folder for other information");
        System.out.println("[0]\t Quit the program");

        choice = in.nextLine().charAt(0);
        //Return the user choice
        return choice;
    }
}
