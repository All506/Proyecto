package Security;

import java.util.Base64;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Alán
 */
//Para encriptar y desencriptar se utilizará la misma llave, que será enviada tanto antes de guardar en XML como después
//El sistema de encriptación utilizado será el "Advance Encryption System" de 1998
public class AES {

    private String key = "proyecto";

    public SecretKeySpec createKey(String key) {

        try {
            byte[] str = key.getBytes("UTF-8"); //Configuración para manejar caracteres del español
            MessageDigest md = MessageDigest.getInstance("SHA-1"); //Método a utilizar
            str = md.digest(str);
            str = Arrays.copyOf(str, 16); //Tamaño de 16 bytes
            SecretKeySpec secretKeySpec = new SecretKeySpec(str, "AES"); //Método de encriptación AES 
            return secretKeySpec;
        } catch (Exception e) {
            return null;
        }
    }

    //Método para encriptar
    public String encrypt(String str, String key) { //Recibe la cadena a encriptar

        try {
            SecretKeySpec secretKeySpec = createKey(key); //Se crea la llave de desencriptar
            Cipher cipher = Cipher.getInstance("AES"); //El método para encriptar
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] strByte = str.getBytes("UTF-8");
            byte[] strEnc = cipher.doFinal(strByte);

            String finalEnc = Base64.getEncoder().encodeToString(strEnc);
            return finalEnc;
        } catch (Exception e) {
            return null;
        }
    }

    //Método para desencriptar
    public String deCrypt(String str, String key) { //Recibe la cadena a encriptar

        try {
            SecretKeySpec secretKeySpec = createKey(key); //Se crea la llave de desencriptar
            Cipher cipher = Cipher.getInstance("AES"); //El método para encriptar
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] strByte = Base64.getDecoder().decode(str);
            byte[] strDec = cipher.doFinal(strByte);

            String finalDec = new String(strDec);
            return finalDec;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
