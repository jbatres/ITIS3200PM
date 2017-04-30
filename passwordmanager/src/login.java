import java.util.*;
import java.io.*;
import java.lang.*;

public class login
{
    private static Scanner in = new Scanner(System.in);
    private static File maspass = new File("masterpass.txt");//File for master password

    public static boolean loginPage() {

        Boolean login = null; //login fails = false, success = true
        boolean bool = false; //flag to check if files exists
        //True = File exists, the returning user
        //false= files does not exists, new user set up

        try {
            // tests if file master password exists, if it exists, then this is a returning user
            bool = maspass.exists();

            if(bool)
                login= ReturningUser(); //call returning user if this is a returning user
            else if (!bool)
                login= NewUserSetUp(); //call new user set up if this is a new user.

        } catch(Exception e) {

            // if any error occurs
            e.printStackTrace();
        }

        return login;
    }

    public static boolean NewUserSetUp(){

        String password = "";//first password that the user inputs
        String secondPass = ""; //second password that the user inputs
        String hash="";//hash for the password
        boolean verification; //for requirments verification

        System.out.println("Welcome to the new user set up!");
        System.out.println("You need to set up a master password for this application!");
        System.out.println("-At least 10 characters ");
        System.out.println("-Must contain at least one digit ");
        System.out.println("-Must contain at least one lower case");
        System.out.println("-Must contain at least one upper case");
        System.out.println("-Must contain at least one of the following: [@#$%^&+=] ");
        System.out.println("-No white spaces ");

        do{
            System.out.println("Enter password master password: ");
            password = in.nextLine();

            verification = authenticate(password); //check if password has all the requirments

            if(verification) { //if it contains all requirements, then input it again
                System.out.println("Enter password master password again: ");
                secondPass = in.nextLine();
            }
        }while(!password.equals(secondPass) || !verification); //Both passwords must match before moving foward and verification passes

        //after getting the master password, we need to hash it
        try
        {
            hash = MasterPasswordENC.newHash(password); //create a hash with the given password.

        }catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
            System.exit(1);
        }

        //now we need to create a file with and write the master password hash to it
        try {
            // create new file in the system
            maspass.createNewFile();

            PrintWriter writer = new PrintWriter(maspass, "UTF-8");
            writer.println(hash);
            writer.close();
        } catch(Exception e) {

            // if any error occurs
            e.printStackTrace();
        }

        //call returning user after user set up is done
        return ReturningUser();

    }


    public static boolean ReturningUser(){
        String password = "";//user's input password
        Boolean flag = null; //flag to match user's password with stored master password
        //true= the password input matches the stored one
        //false = user password does not match the master password

        String masterpassword = "";

        System.out.println("Welcome! Please login.");
        System.out.println("Enter your master password: ");

        password = in.nextLine();

        //retrive user's password ' hash
        try  {
            BufferedReader buffer = new BufferedReader(new FileReader("masterpass.txt"));
            masterpassword= buffer.readLine();
        } catch (IOException e) {
            // if any error occurs
            e.printStackTrace();
        }

        //verify password by sending the password and the existing hash to verify password
        boolean raised = false;
        try {
            flag = MasterPasswordENC.checkPass(password, masterpassword); //Send the password that the user
        } catch (MasterPasswordENC.hashEX ex) {                  //input with the correct master hash password
            raised = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        return flag;

        /*

        if (flag == true)
            System.out.println("The passwords were the same. Flag is "+ flag);
        else if (flag == false)
            System.out.println("The passwords were not the same. Flag is "+ flag);

        */
    }

    public static boolean authenticate(String password)
    {

        if ((password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{10,}$")))
            return true;
        else {

            System.out.println("Your password did not meet requirements!");
            return false;
        }
    }
}