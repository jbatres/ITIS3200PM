/**
 * Created by Jairo on 3/25/2017.
 */
import java.util.Scanner;

public class main {
        public static void main(String[] args) {
            //Scanner for user's input
            Scanner in = new Scanner(System.in);

            //quit flag for program
            boolean quit = false;

            //Menu choice
            int menuItem;
            do {
                System.out.println("[1]\t Passwords!");
                System.out.println("[2]\t Wallet");
                System.out.println("[3]\t Contacts");

                //Get menu choice for user
                System.out.print("Choose menu item: ");
                menuItem = in.nextInt();

                switch (menuItem) {
                    case 1:
                        System.out.println("Passwords!");
                        // do something...
                        break;
                    case 2:
                        System.out.println("Wallets!");
                        // do something...
                        break;
                    case 3:
                        System.out.println("Contacts!");
                        // do something...
                        break;
                    case 0:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (!quit);
            System.out.println("Exiting....");
        }

}
