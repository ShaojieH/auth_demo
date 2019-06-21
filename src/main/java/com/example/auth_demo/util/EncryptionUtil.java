package com.example.auth_demo.util;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static com.example.auth_demo.bean.TokenBean.base64PrivateKey;
import static com.example.auth_demo.bean.TokenBean.base64PublicKey;

@Slf4j
public class EncryptionUtil {
    public static String getHmacForMap(Map<String, String> params) {
        Map<String, String> treeMap = new TreeMap<>(params);
        String str = "";
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            if ( !StringUtils.isEmpty(params.get(entry.getKey()))) {
                String data = entry.getValue();
                if (!StringUtils.isEmpty(data)) {
                    treeMap.put(entry.getKey(), someTransform(data));
                }
            }
            if (!"".equals(str)) {
                str = str + "||";
            }
            str = str + entry.getKey() + "=" + (entry.getValue());
        }
        return sha256HexEncode(str);
    }

    private static String someTransform(String data) {
        // TODO
        return data;
    }


    private static String sha256Encode(byte[] str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str);
        return new String(md.digest());
    }

    private static String sha256HexEncode(String str){
        return  Hashing.sha256()
                .hashString(str, StandardCharsets.UTF_8)
                .toString();
    }



    private static byte[] md5Encode(byte[] str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str);
        return new BigInteger(1, md.digest()).toString(16).getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] b64Encode(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }


    private static String b64EncodeString(String str) {
        return new String(Base64.getEncoder().encode(str.getBytes()));
    }


    private static byte[] b64Decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    private static PublicKey getPublicKey() throws Exception {
        PublicKey publicKey;
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b64Decode(base64PublicKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    private static PrivateKey getPrivateKey() throws Exception {
        PrivateKey privateKey;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    private static byte[] rsaEncode(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
        return cipher.doFinal(data);
    }

    private static byte[] rsaDecode(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        return cipher.doFinal(data);
    }

    public static String encryptPassword(String password) throws Exception {
        return new String(b64Encode(rsaEncode(md5Encode(password.getBytes()))));
    }

    public static String decryptPassword(String password) throws Exception {
        return new String(rsaDecode(b64Decode(password.getBytes())));
    }


}
