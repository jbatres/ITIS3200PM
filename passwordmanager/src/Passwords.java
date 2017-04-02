import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Jairo on 4/2/2017.
 */
public class Passwords {

    private static Scanner in = new Scanner(System.in);
    private static String pass;
    private static char choice;

    public static char PasswordHomeScreen(){
        System.out.println("Welcome To Your Password File!");
      //  do{

            System.out.println("Enter the Password you want to store: ");
            pass = in.nextLine();

        try{


            PrintWriter writer = new PrintWriter("Password-File.txt" , "UTF-8");
            writer.println(pass);

            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }


            System.out.println("If you want to exit to the main menu, enter the number '0'");
            System.out.println("However, if you want to store another password, enter any charachter other than '0'");
            choice = in.nextLine().charAt(0);

            if(choice == '0'){
                 choice = MenuItem.mainMenu();
            }
            else{
                PasswordHomeScreen();
            }


        //}while(choice != '0');
        return choice;
    }


}
