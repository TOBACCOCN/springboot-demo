package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 加解密工具类
 *
 * @author zhangyonghong
 * @date 2020.4.27
 */
@Slf4j
public class RSAUtil {

    private static final String ALGORITHM = "RSA";

    public static String[] generateKey() {
        Map<String, String> keyMap = new HashMap<>(1);
        String[] array = new String[2];
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(ALGORITHM);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // XXXXXXXXXXXXXXXXXXXXXXXX
            // 使用 Base64.getEncoder().withoutPadding() 编码的公钥、私钥，
            // 可能会导致 IOS 端用该公钥加密的数据，服务端用该私钥解不开
            // XXXXXXXXXXXXXXXXXXXXXXXX
            array[0] = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            array[1] = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        return array;
    }

    /**
     * 加密
     *
     * @param message   待加密内容
     * @param publicKey 公钥
     * @return 加密后内容
     */
    public static String encrypt(String message, String publicKey) {
        // base64 编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        // byte[] decoded = Base64.getUrlDecoder().decode(publicKey);
        try {
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM)
                    .generatePublic(new X509EncodedKeySpec(decoded));
            // XXXXXXXXXXXXXXXXXXXXXXXX
            // RSA 加密，安卓端请使用 Cipher.getInstance("RSA/None/PKCS1Padding");
            // XXXXXXXXXXXXXXXXXXXXXXXX
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            // 需要进行 base64 编码，不然是乱码
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
            // return Base64.getEncoder().withoutPadding().encodeToString(cipher.doFinal(message.getBytes()));
            // return Base64.getUrlEncoder().withoutPadding().encodeToString(cipher.doFinal(message.getBytes()));
        } catch (Exception e) {
            log.info(">>>>> RSA_ENCRYPT_ERROR");
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param encrypt    已加密内容
     * @param privateKey 私钥
     * @return 解密后内容
     */
    public static String decrypt(String encrypt, String privateKey) {
        // base64 编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        // byte[] decoded = Base64.getUrlDecoder().decode(privateKey);
        try {
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM)
                    .generatePrivate(new PKCS8EncodedKeySpec(decoded));
            // XXXXXXXXXXXXXXXXXXXXXXXX
            // RSA 解密，安卓端请使用 Cipher.getInstance("RSA/None/PKCS1Padding");
            // XXXXXXXXXXXXXXXXXXXXXXXX
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            // 将经过 base64 编码后的字符串解码
            byte[] bytes = Base64.getDecoder().decode(encrypt.getBytes());
            // byte[] bytes = Base64.getUrlDecoder().decode(encrypt.getBytes());
            return new String(cipher.doFinal(bytes));
        } catch (Exception e) {
            log.info(">>>>> RSA_DECRYPT_ERROR");
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

}
