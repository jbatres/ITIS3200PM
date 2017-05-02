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
public class Wallet {

    private static Scanner in = new Scanner(System.in);
    private static String walletName;
    private static String walletInfo;
    private static char choice;
    static String myPassword = login.getKey();
    static Key aesKey = new SecretKeySpec(myPassword.getBytes(), "AES");


    public static char WalletHomeScreen(){
        System.out.println("\nWelcome To Your Wallet domain!");

        do {
            System.out.println("\nWhat woud you like to do: ");

            System.out.println("[1]\t Add wallet info to files");
            System.out.println("[2]\t Show wallet");
            System.out.println("[0]\t back to main menu");

            choice = in.nextLine().charAt(0);

            if (choice == '1')
                addWallet();
            else if (choice == '0')
                return choice = MenuItem.mainMenu();
            else if (choice == '2')
                ShowWallet();

        } while (choice != '0');




        return choice;
    }

    public static void ShowWallet()
    {
        String myPassword = login.getKey(); //the one saved when you logged in

        if(new File("Wallet-File.txt").exists()) //exists
        {
            try {
                File file = new File("Wallet-File.txt"); //create file object pointing to the password file
                FileReader fileReader = new FileReader(file); // new file reader for the buffer
                BufferedReader bufferedReader = new BufferedReader(fileReader); // buffer reader, needed to read line
                // by line from the password file
                HashMap<String,String> wallets = new HashMap<String,String>();//hash map (key=walletName, val= encrypted walletInfo)
                String line;

                //while loops goes through the password files and adds the key pair value to hash (walletName, encrypted walletInfo)
                while ((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(" ");
                    wallets.put(values[0], values[1]);
                }

                fileReader.close();//close the file reader (for buffer)

                //for loop to go through all key pair values and decrypt wallets
                for(Map.Entry<String, String> entry : wallets.entrySet()) {
                    String walletName = entry.getKey();

                    System.out.println("Wallet: " + walletName);

                }

                System.out.println("\nChose from above: ");
                String chosenWallet = in.nextLine();

                String decrypted = decrypt(wallets.get(chosenWallet), aesKey);
                System.out.println("The wallet info is:" + decrypted);



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
        //create an encoder object, allows to save encrypted walletInfo to text file
        Base64.Encoder encoder = Base64.getEncoder();
        //readable encrypted password
        String encryptedText = encoder.encodeToString(encryptedByte);

        return encryptedText;
    }

    public static String decrypt(String encryptedText, Key secretKey)
            throws Exception {
        //decoder for String (encypted password)
        Base64.Decoder decoder = Base64.getDecoder();
        //get encrypted walletInfo to an array of bytes
        byte[] encryptedTextByte = decoder.decode(encryptedText);

        //initialize cipher on decyption mode with secret key
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        //decrypt the password
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public static void addWallet(){
        System.out.println("Enter the wallet tittle you want to store: ");
        walletName = in.nextLine();

        System.out.println("Enter the wallet info you want to store: ");
        walletInfo = in.nextLine();

        try{
            //  Key aesKey = new SecretKeySpec(myPassword.getBytes(), "AES");

            String encryptedPassword = encrypt(walletInfo, aesKey); //encryp the password entered

            if(new File("Wallet-File.txt").exists()) //exists
            {
                PrintWriter writer = new PrintWriter(new FileWriter("Wallet-File.txt", true));
                writer.append(walletName + " " + encryptedPassword +"\n");
                writer.close();
            }
            else
            {
                PrintWriter writer = new PrintWriter("Wallet-File.txt");
                writer.print(walletName + " " + encryptedPassword +"\n");
                writer.close();
            }



        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


    }


}
