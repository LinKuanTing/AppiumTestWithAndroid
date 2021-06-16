package com.example.wghtest.other;

import com.sun.crypto.provider.SunJCE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class FnFileEncrypt {
    //RSA 對稱加密
    public static final String KEY_ALGORITHM = "RSA";
    //密鑰長度1024 最大字節為117   密鑰長度2048 最大字節245
    // RSA算法只能加密以下數據：RSA密鑰長度的最大字節長度（以位為單位）除以8減去11個填充字節
    // 即最大字節數=密鑰長度（以位為單位）/ 8-11
    //密鑰大小
    private static final int keySize = 1024;
    //最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    //最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    //KeyPairGenerator 類用於生成公鑰和私鑰對，基於 RSA 演算法生成物件
    private KeyPairGenerator keyPairGenerator;
    //KeyPair 生成一對密鑰(公私)
    private KeyPair keyPair;
    //公私密鑰
    public RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    //Cipher 負責完成加密或解密的工作
    private Cipher cipher;
    //公鑰加密
    private byte[] srcBytes;
    private byte[] resultBytes;
    //私鑰解密
    private byte[] decBytes;



    public FnFileEncrypt() throws Exception {
        Security.addProvider(new SunJCE());
        //AES演算法的密鑰生成器
        keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //生成密鑰 密鑰大小1024位
        keyPairGenerator.initialize(keySize);
        //生成一對密鑰
        keyPair = keyPairGenerator.generateKeyPair();
        //得到公鑰
        publicKey = (RSAPublicKey) keyPair.getPublic();
        //得到私鑰
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // Cipher負責完成加密或解密工作，基於RSA
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");


        //沿用之前所儲存的金鑰
        loadPrivateKey(getContent("D:\\priKey.txt"));
        loadPublicKey(getContent("D:\\pubKey.txt"));
    }

    //公鑰加密
    public byte[] Encryptor(String strMsg) throws Exception {
        if (publicKey == null){ return null; }

        // 根據公鑰，對 Cipher 物件進行初始化，ENCRYPT_MODE 表示加密模式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //分段加密
        byte[] data = strMsg.getBytes();
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0){
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }

        byte[] encryptedDate = out.toByteArray();
        out.close();
        return encryptedDate;
    }

    //私鑰解密
    public String Decryptor(byte[] srcBytes) throws Exception {
        if (privateKey == null) { return null; }

        // 根據私鑰，對 Cipher 物件進行初始化，DECRYPT_MODE 表示加密模式
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //分段解密
        int inputLen = srcBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0){
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(srcBytes, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen-offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        String decryptedDate = out.toString();
        out.close();
        return decryptedDate;
    }


    //金鑰儲存
    public void savePublicKey() throws IOException {
        //獲取編碼格式
        byte[] pubEncBytes = publicKey.getEncoded();
        //將編碼格式設為 Base64 儲存
        String pubEncBase64 = new BASE64Encoder().encode(pubEncBytes);
        //System.out.println(pubEncBase64);
        //保存到指定文件
        FileWriter fw = new FileWriter(new File("D:\\pubKey.txt"));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(pubEncBase64);
        bw.close();
    }

    public void savePrivateKey() throws IOException {
        //獲取編碼格式
        byte[] priEncBytes = privateKey.getEncoded();

        //將編碼格式設為 Base64 方便儲存
        String priEncBase64 = new BASE64Encoder().encode(priEncBytes);
        //System.out.println(priEncBase64);
        //保存到指定文件
        FileWriter fw = new FileWriter(new File("D:\\priKey.txt"));
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(priEncBase64);
        bw.close();
    }

    //讀取金鑰
    public String getContent(String strPath) {
        String encoding = "UTF-8";
        File file = new File(strPath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public void loadPublicKey(String publicKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public void loadPrivateKey(String privateKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }


    public void printKey(){
        System.out.println(new BASE64Encoder().encode(privateKey.getEncoded()));
        System.out.println("~~~~~~~~~~~~~~~");
        System.out.println(new BASE64Encoder().encode(publicKey.getEncoded()));
    }

/*
    public String loadFileContent(String strPath) throws Exception {
        FileInputStream fis = new FileInputStream(strPath);
        byte[] buffer = new byte[size];
        fis.read(buffer);
        fis.close();

        return new String(cipher.doFinal(buffer));
    }

    public void saveFileContent(String strPath, String strMessage) throws Exception {
        FileOutputStream fos = new FileOutputStream(strPath);
        size = strMessage.length();
        byte[] buffer;
        buffer = cipher.doFinal(strMessage.getBytes());

        fos.write(buffer);
        fos.close();
    }
 */


    public byte[] loadFileContent(String strPath) throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        FileInputStream fis = new FileInputStream(strPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String strReadLine = "";
        while (br.ready()){
            strReadLine += br.readLine();
        }

        return decoder.decode(strReadLine);
    }

    public void saveFileContent(String strPath, byte[] buffer) throws Exception {
        final Base64.Encoder encoder = Base64.getEncoder();
        FileWriter fw = new FileWriter(strPath);
        BufferedWriter bw = new BufferedWriter(fw);
        final String encodedText = encoder.encodeToString(buffer);

        bw.write(encodedText);
        bw.flush();
        bw.close();
    }

}
