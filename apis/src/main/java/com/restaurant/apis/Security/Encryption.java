package com.restaurant.apis.Security;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;

public class Encryption {

    // Add your private key here
    private String privateKeyString = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMiEqmEaOj9iSUKjtgyJNKtfBu2oSHbd\n" +
            "gHJARr/3H2JLrw/Ku3CZ/u9w6t5TN+YvpYzADe4UU+2qzXCqHZ5aYlECAwEAAQ==\n" +
            "-----END RSA PRIVATE KEY-----";

    private PrivateKey privateKey;

    public Encryption() throws Exception {
        privateKeyString = privateKeyString
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    public String decryptMessage(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, "UTF-8");
    }
}
