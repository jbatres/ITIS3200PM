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
public class newFolder {

    private static Scanner in = new Scanner(System.in);
    private static String newKeyName;
    private static String walueInfo;
    private static char choice;
    static String myPassword = login.getKey();
    static Key aesKey = new SecretKeySpec(myPassword.getBytes(), "AES");


    public static char NewFolerScreen(){
        System.out.println("\nWelcome To New Folder!");
        System.out.println("\nYou can create your own key/value pairs!");

        do {
            System.out.println("\nWhat would you like to do: ");

            System.out.println("[1]\t Add a new key to file");
            System.out.println("[2]\t Show keys");
            System.out.println("[0]\t back to main menu");

            choice = in.nextLine().charAt(0);

            if (choice == '1')
                addKeys();
            else if (choice == '0')
                return choice = MenuItem.mainMenu();
            else if (choice == '2')
                ShowKeys();

        } while (choice != '0');




        return choice;
    }

    public static void ShowKeys()
    {
        String myPassword = login.getKey(); //the one saved when you logged in

        if(new File("NewFolder-File.txt").exists()) //exists
        {
            try {
                File file = new File("NewFolder-File.txt"); //create file object pointing to the password file
                FileReader fileReader = new FileReader(file); // new file reader for the buffer
                BufferedReader bufferedReader = new BufferedReader(fileReader); // buffer reader, needed to read line
                // by line from the password file
                HashMap<String,String> folderKeys = new HashMap<String,String>();//hash map (key=newKeyName, val= encrypted walueInfo)
                String line;

                //while loops goes through the password files and adds the key pair value to hash (newKeyName, encrypted walueInfo)
                while ((line = bufferedReader.readLine()) != null) {
                    String[] values = line.split(" ");
                    folderKeys.put(values[0], values[1]);
                }

                fileReader.close();//close the file reader (for buffer)

                //for loop to go through all key pair values and decrypt folderKeys
                for(Map.Entry<String, String> entry : folderKeys.entrySet()) {
                    String keyName = entry.getKey();

                    System.out.println("Key: " + keyName);
                }

                System.out.println("\nChose from above: ");
                String chosenWallet = in.nextLine();

                String decrypted = decrypt(folderKeys.get(chosenWallet), aesKey);
                System.out.println("The value info is:" + decrypted);



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
        //create an encoder object, allows to save encrypted walueInfo to text file
        Base64.Encoder encoder = Base64.getEncoder();
        //readable encrypted password
        String encryptedText = encoder.encodeToString(encryptedByte);

        return encryptedText;
    }

    public static String decrypt(String encryptedText, Key secretKey)
            throws Exception {
        //decoder for String (encypted password)
        Base64.Decoder decoder = Base64.getDecoder();
        //get encrypted walueInfo to an array of bytes
        byte[] encryptedTextByte = decoder.decode(encryptedText);

        //initialize cipher on decyption mode with secret key
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        //decrypt the password
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

    public static void addKeys(){
        System.out.println("Enter the key tittle you want to store:  ");
        newKeyName = in.nextLine();

        System.out.println("Enter the key value info you want to store:  ");
        walueInfo = in.nextLine();

        try{
            //  Key aesKey = new SecretKeySpec(myPassword.getBytes(), "AES");

            String encryptedPassword = encrypt(walueInfo, aesKey); //encryp the password entered

            if(new File("NewFolder-File.txt").exists()) //exists
            {
                PrintWriter writer = new PrintWriter(new FileWriter("NewFolder-File.txt", true));
                writer.append(newKeyName + " " + encryptedPassword +"\n");
                writer.close();
            }
            else
            {
                PrintWriter writer = new PrintWriter("NewFolder-File.txt");
                writer.print(newKeyName + " " + encryptedPassword +"\n");
                writer.close();
            }

        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


    }


}
