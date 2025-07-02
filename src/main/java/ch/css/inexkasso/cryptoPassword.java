package ch.css.inexkasso;
import java.util.Base64;
public class cryptoPassword{


    public static String encrypt(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }

    public static String decrypt(String base64Text) {
        return new String(Base64.getDecoder().decode(base64Text));
    }
}

