package com.owl.wifi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author  汤清
 * @version 创建时间：2016-3-20 上午23:32:22
 * 类说明   给参数加密公共类
 */
public class PathCode {
	
	public static String KEY = "http:\\wwww.owl.com";
	
	public static String encrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes());
        String value=new BASE64Encoder().encode(encrypted);
//      value=value.replaceAll("\\+", "%2B");
        return value;
    }

    public static String decrypt(String strKey, String strIn) throws Exception {
    	strIn =  replece(strIn);
    	System.out.println(strIn);
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(strIn);

        byte[] original = cipher.doFinal(encrypted1);
        String originalString = new String(original);
        return originalString;
    }
    
    public static String replece(String content){
        String a = content.replace(" ", "+");
        return a;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        
    	byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）

        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
        
//    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);  
//        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());  
//        keyFactory.generateSecret(keySpec);  
//        return keyFactory.generateSecret(keySpec); 
    }

    
	/*public static void main(String[] args) throws Exception {
		String Code = "123456";
        String key = "uttwo";
        String codE;
        codE = PathCode.encrypt(key, Code);
        System.out.println("原文：" + Code);
        System.out.println("密钥：" + key);
        System.out.println("密文：" + codE);
        System.out.println("解密：" + PathCode.decrypt(key, codE));
//      System.out.println("解密：" + PathCode.decrypt(key, "lreTDr1JS7Z/4iqa/34vrLJnTSVu1zTTEYrk8Gr+Ms38GmkVcEhz7Io0SY0CILMH"));
        
        System.out.println(PathCode.encrypt(key, "3577"));
        System.out.println(PathCode.decrypt(key, "Ci6Z8dVQz0cv9vez7oZOxg=="));
	}*/
}
