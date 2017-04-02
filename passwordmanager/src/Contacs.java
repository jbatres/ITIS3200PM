import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * Created by Jairo on 4/2/2017.
 */
public class Contacs {

    private static Scanner in = new Scanner(System.in);
    private static String contactInfo;
    private static char choice;

    public static char ContatsHomeScreen(){

        System.out.println("Welcome To Your Contact file!");
        //  do{

        System.out.println("Enter the contact information you want to store: ");
       contactInfo = in.nextLine();

        try{


            PrintWriter writer = new PrintWriter("Contact-File.txt" , "UTF-8");
            writer.println(contactInfo);

            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }


        System.out.println("If you want to exit to the main menu, enter the number '0'");
        System.out.println("However, if you want to store other Contact information, enter any charachter other than '0'");
        choice = in.nextLine().charAt(0);

        if(choice == '0'){
            choice = MenuItem.mainMenu();
        }
        else{
            ContatsHomeScreen();
        }


        //}while(choice != '0');
        return choice;


    }
}
