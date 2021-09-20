package com.ominfo.app.encryptionhandler;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ominfo.app.interfaces.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class AESEncrypterUpdated {
    private static final String TAG = "AESEncrypterUpdated_TAG";

    /**
     * This generates the secret key to use for the
     * encryption process
     *
     * @param keyValue the key value
     * @return a secret key
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public Key getEncryptionKey(String keyValue) throws NoSuchAlgorithmException {
        return new SecretKeySpec(keyValue.getBytes(), "AES");
    }

    /**
     * This function encrypts the data and encodes it in
     * base64
     *
     * @param data      the data we need to encrypt
     * @param secretKey the secretKey to use in the encryption
     * @return base64Encoded encrypted data
     * @throws NoSuchPaddingException             NoSuchPaddingException
     * @throws NoSuchAlgorithmException           NoSuchAlgorithmException
     * @throws InvalidKeyException                InvalidKeyException
     * @throws BadPaddingException                BadPaddingException
     * @throws IllegalBlockSizeException          IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String encrypt(String data, Key secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        IvParameterSpec iv = new IvParameterSpec(Constants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
//        return new String(
//                Base64.getEncoder().encode(cipher.doFinal(data.getBytes())),
//                StandardCharsets.UTF_8
//        );
    }



    /**
     * This function decrypts the data using the secret Key.We start by
     * decoding the base64 encoded string then decrypt
     *
     * @param encryptedData the encrypted data
     * @param secretKey     the shared secret key used to encrypt the data
     * @return the string that was encrypted
     * @throws NoSuchPaddingException             NoSuchPaddingException
     * @throws NoSuchAlgorithmException           NoSuchAlgorithmException
     * @throws InvalidKeyException                InvalidKeyException
     * @throws BadPaddingException                BadPaddingException
     * @throws IllegalBlockSizeException          IllegalBlockSizeException
     * @throws IOException                        the io exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String decrypt(String encryptedData, Key secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {
        String key = Constants.SECRET_KEY;
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);
        SecretKeySpec spec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//        byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        System.out.println(TAG + " Decoded Value Bytes " + Arrays.toString(decodedValue));
        return new String(cipher.doFinal(decodedValue));
    }

    /**
     * Handles Decryption of Strings
     *
     * @param value String
     * @return String decrypted value
     */
    public static String getDecryptedValue(String value) {

//        try {
//            AESEncrypterUpdated encrypter = new AESEncrypterUpdated();
//            Key key = encrypter.getEncryptionKey(Constants.SECRET_KEY);
//            return encrypter.decrypt(value, key);
//        } catch (Exception ex) {
//            System.out.println(TAG + ex.toString());
//            return "";
//        }

        try {
            AESEncrypterUpdated encrypter = new AESEncrypterUpdated();
            Key key = encrypter.getEncryptionKey(Constants.SECRET_KEY);
            return encrypter.decryptVisa(value, key);
        } catch (Exception ex) {
            System.out.println(TAG + ex.toString());
            return "";
        }
    }

    /**
     * Handles Encryption of Strings
     *
     * @param value String
     * @return String encrypted value
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getEncryptedValue(String value) {

        try {
            AESEncrypterUpdated encrypter = new AESEncrypterUpdated();
            Key key = encrypter.getEncryptionKey(Constants.SECRET_KEY);
            String s= value.replace(" ","");
            return encrypter.encrypt(s, key);
        } catch (Exception ex) {
            System.out.println(TAG + ex.toString());
            return "";
        }
    }

    /**
     * Test Class For Encryption and Decryption
     *
     * @param args args
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void main(String[] args) {
        AESEncrypterUpdated encrypter = new AESEncrypterUpdated();
        try {
            Key key = encrypter.getEncryptionKey(Constants.SECRET_KEY);
            String temp = encrypter.encrypt("3.0", key);
            System.out.println(TAG + "Original Text : " + "3.0");
            System.out.println(TAG + "Encrypted text : " + temp);
            System.out.println(TAG + "Decrypted text : " + encrypter.decrypt(temp, key));
        } catch (Exception ex) {
            System.out.println(TAG + "Exception" + ex.toString());
        }
    }





    /**
     * Handles Decryption of Strings
     *
     * @param value String
     * @return String decrypted value
     */
    public static String getDecryptedValueVisa(String value) {

        try {
            AESEncrypterUpdated encrypter = new AESEncrypterUpdated();
            Key key = encrypter.getEncryptionKey(Constants.SECRET_KEY);
            return encrypter.decryptVisa(value, key);
        } catch (Exception ex) {
            System.out.println(TAG + ex.toString());
            return "";
        }
    }

    public String decryptVisa(String encryptedData, Key secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {
        IvParameterSpec iv = new IvParameterSpec(Constants.SECRET_KEY.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        return new String(cipher.doFinal(decodedValue));
    }



    /**
     * This function encrypts the data and encodes it in
     * base64
     *
     * @param data      the data we need to encrypt
     * @param secretKey the secretKey to use in the encryption
     * @return base64Encoded encrypted data
     * @throws NoSuchPaddingException             NoSuchPaddingException
     * @throws NoSuchAlgorithmException           NoSuchAlgorithmException
     * @throws InvalidKeyException                InvalidKeyException
     * @throws BadPaddingException                BadPaddingException
     * @throws IllegalBlockSizeException          IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String encryptDBKey(String data, Key secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        IvParameterSpec iv = new IvParameterSpec(Constants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
    }

    /**
     *this method decrypt the database key
     */
    public String decryptDBKey(String encryptedData, Key secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException {
        IvParameterSpec iv = new IvParameterSpec(Constants.SECRET_KEY.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] decodedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        return new String(cipher.doFinal(decodedValue));
    }
}
