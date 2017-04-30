import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jairo on 4/2/2017.
 */
public class Passwords {

    private static Scanner in = new Scanner(System.in);
    private static String account;
    private static String pass;
    private static char choice;
    static String myPassword = "Bar12345Bar12345";
    static Key aesKey = new SecretKeySpec(myPassword.getBytes(), "AES");


    public static char PasswordHomeScreen(){
        System.out.println("\nWelcome To Your Password domain!");

        do {
            System.out.println("\nWhat woud you like to do: ");

            System.out.println("[1]\t Add account to files");
            System.out.println("[2]\t Show accounts");
            System.out.println("[0]\t back to main menu");

            choice = in.nextLine().charAt(0);

            if (choice == '1')
                addAccount();
            else if (choice == '0')
                return choice = MenuItem.mainMenu();
            else if (choice == '2')
                ShowAccounts();

        } while (choice != '0');


        return choice;
    }

    public static void ShowAccounts()
    {
        String myPassword = "Bar12345Bar12345"; //the one saved when you logged in

        if(new File("Password-File.txt").exists()) //exists
        {
            try {
                File file = new File("Password-File.txt"); //create file object pointing to the password file
                FileReader fileReader = new FileReader(file); // new file reader for the buffer
                BufferedReader bufferedReader = new BufferedReader(fileReader); // buffer reader, needed to read line
                // by line from the password file
                HashMap<String,String> passwords = new HashMap<String,String>();//hash map (key=account, val= encrypted pass)
                String line;

                //while loops goes through the password files and adds the key pair value to hash (account, encrypted pass)
                while ((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(" ");
                    passwords.put(values[0],values[1]);
                }
                fileReader.close();//close the file reader (for buffer)

                //for loop to go through all key pair values and decrypt passwords
                for(Map.Entry<String, String> entry : passwords.entrySet()) {
                    String account = entry.getKey();

                    System.out.println("Account: " + account);
                }

                System.out.println("\nChose from above: ");
                String chosenAccount = in.nextLine();

                String decrypted = decrypt(passwords.get(chosenAccount), aesKey);
                System.out.println("Your password is:" + decrypted);



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String encrypt(String plainText, Key secretKey)
            throws Exception {
        //byte array with the password
        byte[] plainTextByte = plainText.getBytes();
        //new instance of Cipher class with AES algorithm
        Cipher cipher = Cipher.getInstance("AES");
        //initialize cipher on encryption mode with secret key
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        //encrypted array of bites, password is encrypted
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        //create an encoder object, allows to save encrypted pass to text file
        Base64.Encoder encoder = Base64.getEncoder();
        //readable encrypted password
        String encryptedText = encoder.encodeToString(encryptedByte);

        return encryptedText;
    }

    public static String decrypt(String encryptedText, Key secretKey)
            throws Exception {
        //decoder for String (encypted password)
        Base64.Decoder decoder = Base64.getDecoder();
        //get encrypted pass to an array of bytes
        byte[] encryptedTextByte = decoder.decode(encryptedText);

        //initialize cipher on decyption mode with secret key
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        //decrypt the password
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public static void addAccount(){
        System.out.println("Enter the Account name you want to store: ");
        account = in.nextLine();

        System.out.println("Enter the Password you want to store: ");
        pass = in.nextLine();

        boolean passCheckFlag = databaseCheck(pass);
        System.out.println(passCheckFlag);

        if(passCheckFlag){
        do{
            System.out.println("Warning! Please keep all passwords unique!");
            System.out.println("re enter the password you want to store!");
            pass = in.nextLine();
            passCheckFlag = databaseCheck(pass);
        }while (passCheckFlag == true);
        }

        try{
            //  Key aesKey = new SecretKeySpec(myPassword.getBytes(), "AES");

            String encryptedPassword = encrypt(pass, aesKey); //encryp the password entered

            if(new File("Password-File.txt").exists()) //exists
            {
                PrintWriter writer = new PrintWriter(new FileWriter("Password-File.txt", true));
                writer.append(account + " " + encryptedPassword +"\n");
                writer.close();
            }
            else
            {
                PrintWriter writer = new PrintWriter("Password-File.txt");
                writer.print(account + " " + encryptedPassword +"\n");
                writer.close();
            }



        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public static boolean databaseCheck(String password){

        try {
            if(!new File("Password-File.txt").exists())
                return false;


            File file = new File("Password-File.txt"); //create file object pointing to the password file
            FileReader fileReader = new FileReader(file); // new file reader for the buffer
            BufferedReader bufferedReader = new BufferedReader(fileReader); // buffer reader, needed to read line
            // by line from the password file
            HashMap<String,String> passwords = new HashMap<String,String>();//hash map (key=account, val= encrypted pass)
            String line;

            //while loops goes through the password files and adds the key pair value to hash (account, encrypted pass)
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(" ");
                passwords.put(values[0],values[1]);
            }
            fileReader.close();//close the file reader (for buffer)

            //for loop to go through all key pair values and decrypt passwords
            for(Map.Entry<String, String> entry : passwords.entrySet()) {
                String account = entry.getKey();
                String decrypted = decrypt(passwords.get(account), aesKey);
                if(decrypted.equals(password))
                    return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
}
