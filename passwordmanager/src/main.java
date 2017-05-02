/**
 * Created by Jairo on 3/25/2017.
 */
import java.util.*;
import java.lang.*;


public class  main{

    private static Scanner in = new Scanner(System.in);
    private static char choice;
    private static String contactInfo;
    private static String newFold;

    public static void main(String[] args) {

        /*
        First and foremost,
        Call the login for this application
         */
        Boolean loginFlag = null; //to check whether it as succesful or not
        int attempts = 0; //to count the number of attempts someone fails inputting the password
        do {
            loginFlag = login.loginPage();
            if (loginFlag) {
                System.out.println("Login was successful!");
                break;//break of the whie loop if login is succesful
            }
            else if (!loginFlag) {
                attempts++;
                if(attempts==3)
                    System.exit(0);
                System.out.println("Login failed. Please try again");
            }
        }while (attempts < 3);

        choice = MenuItem.mainMenu();

        while(choice != '0'){
            if (choice == '1'){
                choice = Passwords.PasswordHomeScreen();
            }
            else if (choice == '2'){
                choice= Wallet.WalletHomeScreen();
            }
            else if (choice == '3'){
                choice = Contacs.ContactHomeScreen();
            }
            else if (choice == '4'){
                choice = newFolder.NewFolerScreen();
            }
            else if (choice == '0'){
                System.out.println("You decided to quit the program. Goodbye :)");
            }
            else{
                System.out.println("Invalid input. Try again.");
                choice = MenuItem.mainMenu();
                //menuItem();
            }
        }
    }
}
