/**
 * Created by Jairo on 4/2/2017.
 */
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.spec.*;
import javax.xml.bind.*;

public class MasterPasswordENC
{
    public static final int PBINDEX = 4;
    public static final int iterationINDEX = 1;
    public static final int sections = 5;
    public static final int algoINDEX = 0;
    public static final int hashINDEX = 2;
    public static final int saltINDEX = 3;

    public static final int saltSize = 32;
    public static final int hashSize = 24;
    public static final int PBKDF2Iterations = 64000; //by default 32000, double to increase protection of the password

    private static byte[] fromBase64(String hex)
            throws IllegalArgumentException
    {
        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array)
    {
        return DatatypeConverter.printBase64Binary(array);
    }

    private static String format(){
        return "PBKDF2WithHmacSHA1";
    }

    static public class operationEX extends Exception {
        public operationEX(String warning) {
            super(warning);
        }
        public operationEX(String warning, Throwable source) {
            super(warning, source);
        }
    }
    static public class hashEX extends Exception {
        public hashEX(String warning) {
            super(warning);
        }
        public hashEX(String warning, Throwable source) {
            super(warning, source);
        }
    }

    public static String newHash(String password)
            throws operationEX
    {
        return newHash(password.toCharArray());
    }

    /*
    newHash() takes a string, and returns a hash that can be verefy  VerifyPassword() to check if the
    password is correct..
     */

    public static String newHash(char[] password)
            throws operationEX //this exection is throw when the system in which the application is running can not
    //safely create a good/safe hash
    {
        SecureRandom randomSEC = new SecureRandom();
        byte[] salt = new byte[saltSize];//get salt with specific salt size
        randomSEC.nextBytes(salt);

        byte[] hash = pb(password, salt, PBKDF2Iterations, hashSize); //hash the password
        int size = hash.length;

        //format specefy for this hashing and application.
        String ENCformat = "sha1:" + PBKDF2Iterations + ":" + size + ":" + toBase64(salt) + ":" + toBase64(hash);
        return ENCformat;
    }

    public static boolean checkPass(String password, String Hash)
            throws operationEX, hashEX
    {
        return checkPass(password.toCharArray(), Hash);
    }

    public static boolean checkPass(char[] password, String Hash)
            throws operationEX, hashEX
    {
        String[] params = Hash.split(":"); //we need to strack the format of the hash

        //check to make sure the format is correct
        if (params.length != sections) {
            throw new hashEX("Not to correct format");
        }

        //check to make sure sha1 is the format
        if (!params[algoINDEX].equals("sha1")) {
            throw new operationEX("bad hash type.");
        }

        int laps = 0;//use in the pase interations below
        try {
            laps = Integer.parseInt(params[iterationINDEX]);
        } catch (NumberFormatException ex) {
            throw new hashEX("Error parsis integer.", ex);
        }

        //check to make sure we actually loop/lap through the whole index
        if (laps < 1) {
            throw new hashEX("Invalid number of laps");
        }


        byte[] salt = null;
        try {
            salt = fromBase64(params[saltINDEX]);
        } catch (IllegalArgumentException ex) {
            throw new hashEX("salt failed.", ex);
        }

        byte[] hash = null;
        try {
            hash = fromBase64(params[PBINDEX]);
        } catch (IllegalArgumentException ex) {
            throw new hashEX("pb failed.", ex);
        }

        int hashSize = 0;
        try {
            hashSize = Integer.parseInt(params[hashINDEX]);
        } catch (NumberFormatException ex) {
            throw new hashEX("failed to parse the hash.", ex);
        }

        if (hashSize != hash.length) {
            throw new hashEX("Hash do no match.");
        }

        //now we compute the hash, with the same format as create hash to verefy they are the same
        byte[] secondHash = pb(password, salt, laps, hash.length);

        //password is correct if they both match
        return slowEquasl(hash, secondHash);
    }
    /*
   slow equals comparing the hashes in length-constant-time
     */

    private static boolean slowEquasl(byte[] a, byte[] b)
    {
        int difference = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            difference |= a[i] ^ b[i];
        }
        return difference == 0;
    }

    private static byte[] pb(char[] password, byte[] salt, int iterations, int bytes)
            throws operationEX
    {
        try {
            PBEKeySpec formatSpeficications = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(format());
            return skf.generateSecret(formatSpeficications).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new operationEX("has is not supported.", ex);
        } catch (InvalidKeySpecException ex) {
            throw new operationEX("Invalid key entry", ex);
        }
    }



}
