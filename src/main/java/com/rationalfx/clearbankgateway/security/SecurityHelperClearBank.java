package com.rationalfx.clearbankgateway.security;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Random;

//	.getHeader("Authorization", auth);
@Component
public class SecurityHelperClearBank {
    public static final String CONTENT_TYPE = "Content-Type";

    public static final String APPLICATION_JSON = "application/json";

    public static final String AUTHORIZATION = "Authorization";

    public static final String SIGNING_ALGORITHM = "SHA256withRSA";

    @Autowired
    ClearBankConfig clearBankConfig;

    public static String getRandomNumber(int digCount) {

        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++)
            sb.append((char) ( '0' + rnd.nextInt(10) ));
        return sb.toString();
    }

    /**
     * This method Loads the private key from file system. Signs the input using private key and retuns base 64     *
     *
     * @param input
     * @return
     * @throws Exception
     */
    public String createDigitalSignature(byte[] input) throws Exception {

        PrivateKey privateKey = getPrivateKeyRSA(clearBankConfig.getPrivateKeyPath());
        System.out.println("privateKey is :" + privateKey);
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(input);
        byte[] digs = signature.sign();
        return new String(Base64.encodeBase64(digs));
    }

    public PrivateKey getPrivateKeyRSA(String filename) throws Exception {

        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    protected HttpHeaders getHeaderForPost(String digiSignature, String uniqueKeyCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Request-Id", uniqueKeyCode);
        headers.add("DigitalSignature", digiSignature);
        headers.add("Authorization", "Bearer " + clearBankConfig.getTokenBearerPost());
        System.out.println("************************************** Header(Bearer)           ----->Bearer " + clearBankConfig.getTokenBearerPost());
        System.out.println("************************************** Header(DigitalSignature) ----->" + digiSignature);
        System.out.println("************************************** Header(X-Request-Id)     ----->" + uniqueKeyCode);
        return headers;

    }

    public HttpEntity getEntityWithHeader() {

        HttpHeaders headers = getHeaderWithBearerToken();
        HttpEntity request = new HttpEntity(headers);
        return request;

    }

    public HttpHeaders getHeaderWithBearerToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer " + clearBankConfig.getTokenBearerPost());
        return headers;
    }

    public HttpHeaders getHeaderWithBearerTokenAndUniqueHeaderAndDigitalSignature(String digiSignature) {
        String hexMillis = Long.toHexString(Instant.now().toEpochMilli());
        String uniqueHeader = "TR-" + hexMillis + new Random(109990);
        System.out.println(uniqueHeader);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "application/json");
        headers.add("DigitalSignature", digiSignature);
        headers.add("Authorization", "Bearer " + clearBankConfig.getTokenBearerPost());
        headers.add("X-Request-Id", uniqueHeader);
        System.out.println("************************************** Header(Bearer)           ----->Bearer " + clearBankConfig.getTokenBearerPost());
        System.out.println("************************************** Header(DigitalSignature) ----->" + digiSignature);
        return headers;
    }

    public HttpHeaders getHeaderWithUniqueRequestId() {
        String hexMillis = Long.toHexString(Instant.now().toEpochMilli());
        String uniqueHeader = "TR-" + hexMillis + new Random(109990);
        System.out.println(uniqueHeader);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "application/json");
        headers.add("Authorization", "Bearer " + clearBankConfig.getTokenBearerPost());
        headers.add("X-Request-Id", uniqueHeader);
        return headers;

    }

}
