package AES_Encryption;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AES_Encryption {

    private static final String ALGO = "AES";
    private final byte[] keyValue;

    public AES_Encryption(String key) {
        keyValue = key.getBytes();
    }

    public String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }

    
    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public static void main(String args[]) {
        try {
        AES_Encryption aes = new AES_Encryption("fjakdie82759kshg"); //this is my key 
            String encdata = aes.encrypt("Hello World"); // this is my input
            System.out.println("Encrypted Data : " + encdata);

            String decdata = aes.decrypt(encdata);
            System.out.println("Descrypted Data : " + decdata);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            Logger.getLogger(AES_Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
