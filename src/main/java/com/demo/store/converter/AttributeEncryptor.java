package com.demo.store.converter;

import com.demo.store.utils.StringUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.security.Key;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Converter
@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String ENCRYPT_TYPE = "AES";
    private static final int NONCE_SIZE = 32;

    private static Key secretKey;
    private static Cipher encryptorCipher;
    private static Cipher decryptorCipher;

    @Autowired
    public void init(@Value("${encryptor.secret-key}") String secret) {
        try {
            byte[] key = secret.substring(0, NONCE_SIZE).getBytes();
            secretKey = new SecretKeySpec(key, ENCRYPT_TYPE);
            encryptorCipher = Cipher.getInstance(ENCRYPT_TYPE);
            encryptorCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            decryptorCipher = Cipher.getInstance(ENCRYPT_TYPE);
            decryptorCipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (StringUtils.isEmpty(attribute)) {
            return attribute;
        }

        try {
            return Base64.getEncoder()
                    .encodeToString(encryptorCipher.doFinal(attribute.getBytes()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return dbData;
        }

        try {
            return new String(decryptorCipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (BadPaddingException e) {
            return dbData;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
