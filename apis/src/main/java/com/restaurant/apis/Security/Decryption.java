package com.restaurant.apis.Security;

import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import java.util.Base64;

public class Decryption {
    private String privateKeyStr = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIBOwIBAAJBAMiEqmEaOj9iSUKjtgyJNKtfBu2oSHbdgHJARr/3H2JLrw/Ku3CZ\n" +
            "/u9w6t5TN+YvpYzADe4UU+2qzXCqHZ5aYlECAwEAAQJAZaP5yWbGB8MGZ6UcbpZO\n" +
            "AGAW3vjiYn480a0LL9tQKT5WNeL44eC6vyKGsD+vIbHVPvWOJsnch/ev6Z5NYm2V\n" +
            "0QIhAOUO1Yh9XKGFz6uQy4x51mXfcmXfRgodgi3dotT5UHSzAiEA4Bp4vOpZiLES\n" +
            "bAIdPIFFb8HvM2IgCnwg6wX850tYtusCIQChehCs1PuNXuDk6QGl+WpcjOZ/zKP6\n" +
            "k4znOp0FGPFQIwIhAMSGcls5KEs2/XC6aekldD8NUzc8Vdzb/gIcviwXZzUBAiBw\n" +
            "46juSXi2I57Zhqk81urL4sL3pW+bGgu/kLt+ywYwTg==\n" +
            "-----END RSA PRIVATE KEY-----";

    private PrivateKey privateKey;

    public Decryption() throws Exception {
        privateKeyStr = privateKeyStr
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
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
