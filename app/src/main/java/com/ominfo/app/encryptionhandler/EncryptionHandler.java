/*
package com.ominfo.app.encryptionhandler;

import android.util.Base64;

import com.ominfo.app.util.LogUtil;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

*/
/*
 this class is used for generating public private key and encrypt decrypt data for API calling
 * *//*

public class EncryptionHandler {

    private static final String TAG = EncryptionHandler.class.getSimpleName();

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }


    public interface SecurityConstants {
        int KEY_SIZE = 2048;
        String TYPE_AES = "AES";
        String TYPE_RSA = "RSA";
    }

    */
/**
     * Store private and public key securely to avoid loading new ones every-time
     *//*

    private static String privateKey;
    private static String publicKey;

    private static void generateKeys() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(SecurityConstants.TYPE_RSA);
            kpg.initialize(SecurityConstants.KEY_SIZE);
            KeyPair kp = kpg.generateKeyPair();
            privateKey = new String(Base64.encode(kp.getPrivate().getEncoded()));
            publicKey = new String(Base64.encode(kp.getPublic().getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        }

    }

    public static PublicKey loadPublicKeyFromString() {

        try {
            if (publicKey == null) {
                generateKeys();
            }

            String key = publicKey.replace("-----BEGIN PUBLIC KEY-----\n", "");
            key = key.replace("-----END PUBLIC KEY-----", "");

            byte[] encodedPublicKey = Base64.decode(publicKey);

            // decode the encoded RSA public key
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.TYPE_RSA);
            return keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static PublicKey loadServerPublicKey(String publicKey) {
        try {

            String key = publicKey.replace("-----BEGIN PUBLIC KEY-----\n", "");
            key = key.replace("-----END PUBLIC KEY-----", "");

            byte[] encodedPublicKey = Base64.decode(key);
            LogUtil.printLog("KEY string ------>", new String(encodedPublicKey));
            // decode the encoded RSA public key
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.TYPE_RSA);
            return keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static PublicKey loadServerPublicKeyUpdated(String publicKey) {
        try {

            byte[] encodedPublicKey = Base64.decode(publicKey);
            String temp = new String(encodedPublicKey);
            temp = temp.replace("-----BEGIN PUBLIC KEY-----", "");
            temp = temp.replace("-----END PUBLIC KEY-----", "");
            temp = temp.replace("\n", "");
            temp = temp.replace("\r", "");
            temp = temp.replace("\t", "");
            temp = temp.replace(" ", "");


            // decode the encoded RSA public key
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decode(temp));
            KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.TYPE_RSA);
            return keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static PrivateKey loadPrivateKeyFromString() {

        try {
            if (privateKey == null) {
                generateKeys();
            }

            String privKeyPEM = privateKey.replace("-----BEGIN RSA PRIVATE KEY-----\n", "");
            privKeyPEM = privKeyPEM.replace("-----END RSA PRIVATE KEY-----", "");

            byte[] decodedKey = Base64.decode(privKeyPEM);

            // PKCS8 decode the encoded RSA private key
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.TYPE_RSA);
            return keyFactory.generatePrivate(privKeySpec);
        } catch (InvalidKeySpecException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static PrivateKey loadPrivateKeyFromStringUpdated(String base64PrimaryKey) {

        try {

            byte[] encodedPublicKey = Base64.decode(base64PrimaryKey);
            String temp = new String(encodedPublicKey);
            temp = temp.replace("-----BEGIN RSA PRIVATE KEY-----", "");
            temp = temp.replace("-----END RSA PRIVATE KEY-----", "");
            temp = temp.replace("\n", "");
            temp = temp.replace("\r", "");
            temp = temp.replace("\t", "");
            temp = temp.replace(" ", "");

            byte[] decodedKey = Base64.decode(temp);

            // PKCS8 decode the encoded RSA private key
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(SecurityConstants.TYPE_RSA);
            return keyFactory.generatePrivate(privKeySpec);
        } catch (InvalidKeySpecException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static byte[] rsaDecrypt(String base64EncryptedText) {

        try {
            PrivateKey key = loadPrivateKeyFromString();

            // decrypts the message
//            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-256andMGF1Padding");
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1andMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(Base64.decode(base64EncryptedText));
        } catch (IllegalBlockSizeException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (BadPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidKeyException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static byte[] rsaDecryptUpdated(String base64PrivateKey, String base64EncryptedText) {

        try {
            PrivateKey key = loadPrivateKeyFromStringUpdated(base64PrivateKey);

            // decrypts the message
            //Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-256andMGF1Padding");
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1andMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(Base64.decode(base64EncryptedText));
        } catch (IllegalBlockSizeException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (BadPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidKeyException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static byte[] rsaEncrypt(byte[] plaintext, PublicKey publicKey) {
        try {
            // encrypts the message
            //Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-256andMGF1Padding");
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1andMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(plaintext);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidKeyException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (BadPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (IllegalBlockSizeException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static String aesDecrypt(byte[] symKey, String encrypted) {

        try {
            //Add an initialization vector
            byte[] iv = new byte[16]; // initialization vector with all 0
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Key key = new SecretKeySpec(symKey, SecurityConstants.TYPE_AES);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            byte[] original = cipher.doFinal(Base64.decode(encrypted));

            return new String(original);
        } catch (IllegalBlockSizeException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (BadPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidKeyException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidAlgorithmParameterException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    */
/**
     * AES encrypt message using server key
     *
     * @param serverKey
     * @param message
     * @return
     *//*

    public static String aesEncrypt(PublicKey serverKey, String message) {

        try {
            KeyGenerator kgenerator = KeyGenerator.getInstance(SecurityConstants.TYPE_AES);
            SecureRandom random = new SecureRandom();
            kgenerator.init(128, random);
            Key aesKey = kgenerator.generateKey();

            int aesKeyLen = aesKey.getEncoded().length;
            //assertEquals(16, aesKeyLen); Hex

            byte[] aesKeyEncrypted = rsaEncrypt(aesKey.getEncoded(), serverKey);
            if (aesKeyEncrypted == null) {
                return null;
            }

            //Add an initialization vector
            byte[] iv = new byte[16]; // initialization vector with all 0
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            String aesKeyEncryptedBase64 = Base64.toBase64String(aesKeyEncrypted);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);

            byte[] encrypted = cipher.doFinal(message.getBytes());
            String messageEncrypted = Base64.toBase64String(encrypted);
            String aesKeyLenHex = String.format("%03X", aesKeyEncryptedBase64.length());

            return aesKeyLenHex + aesKeyEncryptedBase64 + messageEncrypted;

        } catch (NoSuchAlgorithmException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidKeyException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (NoSuchPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (BadPaddingException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (IllegalBlockSizeException e) {
            LogUtil.printLog(TAG, e.toString());
        } catch (InvalidAlgorithmParameterException e) {
            LogUtil.printLog(TAG, e.toString());
        }

        return null;
    }

    public static String decryptMessage(String privateKey, String encryptedMessage) {

        String symLengthHex = encryptedMessage.substring(0, 3);

        int symBase64Length = Integer.parseInt(symLengthHex, 16);
        String symBase64 = encryptedMessage.substring(3, symBase64Length + 3);
        String messageEncrypted = encryptedMessage.substring(symBase64Length + 3, encryptedMessage.length());

        // decrypts the symmetricKey
        byte[] symKey = EncryptionHandler.rsaDecryptUpdated(privateKey, symBase64);

        if (symKey == null) {
            return "";
        }

        LogUtil.printLog("TAG", "SymBase64 : " + symBase64);
        LogUtil.printLog("TAG", "EncryptedMessage64 : " + messageEncrypted);
        LogUtil.printLog("TAG", "SymKey length : " + symKey.length);

        String decrypted = aesDecrypt(symKey, messageEncrypted);
       // LogUtil.printLog("TAG", "DecryptedMessage : " + decrypted);

        return decrypted;
//        messageView.setText(String.format("DECRYPTED MESSAGE: \n%s", decrypted));
    }

*/
/*
    public Key getEncryptionKey(String keyValue) throws NoSuchAlgorithmException {
        return new SecretKeySpec(keyValue.getBytes(), "AES/CBC/PKCS5PADDING");
    }

    public static String decrypt(String encryptedData) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, IOException {

        EncryptionHandler encrypter = new EncryptionHandler();
        Key secretKey = encrypter.getEncryptionKey(Constants.SECRET_KEY);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedValue = cipher.doFinal(Base64.decode(encryptedData));
//        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        return new String(cipher.doFinal(decodedValue));
    }*//*

}
*/
