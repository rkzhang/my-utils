package com.apep.util.security.algorithm;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;

public class RSAToBCDCoder {

	private static final String PRIVATE_KEY = "private";
	
	public static final String PUBLIC_KEY = "public";
	
	/** 
     * 生成公钥和私钥 
     * @throws NoSuchAlgorithmException  
     * 
     */  
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        map.put(PUBLIC_KEY, publicKey);  
        map.put(PRIVATE_KEY, privateKey);  
        return map;  
    }  
    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    }  
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);  
        System.err.println(bcd.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }  
    /** 
     * ASCII码转BCD码 
     *  
     */  
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = asc_to_bcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }  
    public static byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }  
    /** 
     * BCD转字符串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }  
    /** 
     * 拆分字符串 
     */  
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /** 
     *拆分数组  
     */  
    public static byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    }  
    
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
       /* HashMap<String, Object> map = RSACoder.getKeys();  
        //生成公钥和私钥  
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");  
         */
        //模  
        String modulus = "116262705441292461767938684378745573385265261433835354533161260570850418836270718461079468519086567371916662924537118880790955592987214794462208450157461042923039456080271600322310596059357051099905562221749131076225607808428923699762172481968262836388564574671403872476197309991942349096313039457035406478567";  
        //公钥指数  
        String public_exponent = "65537";  
        //私钥指数  
        String private_exponent = "29771285269630439192968048602226348666425402709654468769939305657872831055896595773591034693185693175389557611724398874794906949685085351490986499390306404869838845033947919691721307992734678201041388835183007534056440442570362142578136442199597719747865426902120172091441361966348776310969207773895064659473";  
        System.out.println("modulus="+modulus);
        System.out.println("public_exponent="+public_exponent);
        System.out.println("private_exponent="+private_exponent);
        
        //明文  
        String ming = "{\"itemId\":\"123456\"}";  
        //使用模和指数生成公钥和私钥  
        RSAPublicKey pubKey = RSAToBCDCoder.getPublicKey(modulus, public_exponent);  
        RSAPrivateKey priKey = RSAToBCDCoder.getPrivateKey(modulus, private_exponent);  
        //加密后的密文  
        //String mi = RSACoder.encryptByPublicKey(ming, pubKey);  
        String mi = "88829C029D5CD098819B837B4928F52C62FAB898025B585E8DDB0CAC4200DA1CF4A2A9D7374864C5E98477EE697358AEEF669C713E901664F93BE5B52601547752E14054D831F8420F42D80712E8B65F3634D7538757F03C95F3E78C01ABB147A2EA1EF65411B12E774767F0136C297F0A4BBFE73759E124381BBAC3EEDADAC3507DA763020B961B5ED76888F0BB63DBBA3706F2B6B56E9C7393F6F034BDBAAF712E6FF6C4DFEC8719F0C24D59AA24AAE72003B51E14644C30157AE478346C0BF13DAC2FA4AA714B08D8468FE651DF5E79D77C57B1555CD2B07B7C6D7AB9138421B54FA3E7E22869AE076FA4BBB366CA39E8176DB8F57AC97A0E089895CE862965AC7E3D37ED84A8393346600AED7B695E9CCE570D66CDDD9B95B497DC2F86F39F04C4199A6361D78543B82BE181375E862927333AD86A57E8DF129C2AD5E0549B1E759F06A044C9B37236E4305CD3058B78ED56F2723683AFDAE1E4A92931E3672FC1B67FC7CB04175F0EC2D7E4E0092869627BB1D43D9A79C20A5C0512633B10F2EDB9B79622CCA3D0D3767AD1C011CAED4C92E77C78A5BC90E82406D97910F7DFFF8D1C2D1F1BBFFFAF19E3CE3645F4BE3ED070B3B4B51FC1C36BEE978623AE0729BC8C1DFF0A6495F93BA7DFAF6546C2D97AB1CB2891F9979BACAE392271879514C1C442A3B4BDA73770FA9AF6F663EB45EB3D761CB8EE7DBEA3CB11176F30E95484942DF4E328AC614AB0DB4DA8B4F359E3E444B1362EA98E6FE85EAFF41A7DCE152A1F06A0716873A1FFF2107FA0D73E9C6A032F7CA51745580144E8CACBFFD39CC2FDA2486B23FF787C9855358041D2D4DE2465E99EB8C7477546ED4EAF3B6091FD9EE5900EB157B70DD52B6C67604582078C7D0926AFD8509FC21B5A50C35591FBF893EAA820F7CBCD00997815ADA30D31E47AA4753250BE2C8733BE9BF0BA0E23059CBC8D01B4C5C85BBC4A288CAAFEAF4D1CD22D027DAFA84810B6536982B5EBB19BB0719B05781BA3EDCF8D026FE13E23021A40BB42DC2674BBDF015451D28DA75CDABEBE08E104588DCAE8C3DF20BC313F568F037EF0F8E8AFED93E3F1027C5496DB2B10DAE27E9CBF6D003FE0FBF60754DB9409AB12414CD2348C19E9D50D5AF13983096AF88F7940FA4A5CD51AA30C18FD340E2D1D535E4DDC6FC33FC7D65548DA939893D6AEB5B4505923557136160339E78E28F00EBA38F9F56337367E354C3B8269852964BF17612B5B26DA9FD0A12ACBBFBF18ABE8171E37FB30E0E8EF1822D1F3AB0E5C061925FE5F9B7F5D8B448C7892EF4F1D2FECC4DF02B828FB37BDA89521497E79365FC91E6122EE574E21073024A54C7B6331317308B73CFAFFFC392C7B4A07367882D5FD7B7D050669168D459D4B7749CF9907026EE4830062489FD79003939FB212CAE598E4D2C09D85FA2218126A9AAB8BD979EC9D73AF6EB6F083A6349CB0AF2A5E6AA54CB2BFC5E421BAFEB55190427AD6E501796FD5008FD2DA46AE26B958EF50147F4F58AA08674509E7C4FB3FB9519A1474868ACDE701F46E64EBE5939A566BBF066546D59138DC2FED74105C86CBD2B924A6CC8E2A1675BEB68AD16528E6E1EA1350E5DB6B2E1AC0F7CDA461B66B0A353D1ED7A14FF36075BD3FB0025EB889CBC051E47ABA0D82D45AD810E65363A88A897D2105D962C68B5148A585B9E1AD7FE093FFA75BF6BE78E4135E6CF21B175D05BFB6ED6E9FFB9DE66D7A375C6D1A3CBFD574C9E3679F1918BBF5CA76727B3DBE56CD790C9AD82942E7BC74ED8D449EE5EAF02B93CC5A28B3D07AABE898BF386F2A8C8BCF40E98161DDCA2D6A390AADEF2F3FE395367EB4ADC01BBD4499D480AF36C126748073241EE836D98D24924C5E14957CEB49DE5FF513AC8C4F397C4C3FBD350F7856F5B611214A725B164AE0DC061FDAC74214593C402961C4F2747A1292AE8F43522B2CB12DC30A0851F8DED6D96CAB88A51EBCA88A5A2F0073252B6538F75A61224843302BAAC8489608C16EC94A4F03AAAD0F27A5D0B607F32127A5A0C5A6EA0D4CCC5EA9CF518AF57D4D3ACE5E0E78AD7585451A6BF88C57DA6F1372B83428FAE07F9967940BC8363F4AE9614CFBFF9897CD0672B38758FE55A53C3A046314D3A9F9F222EE984D5F99321912F9E332910324E9A18D11741BB08F4A6994BAD7A243185EB1E9CD7140B04526C467A92AD0ABABAF5CB0323CF3125E5237007A12EB5B4853D882B1E41BFD2EB9E6F41113BA2994AB3EACF31BD8E6475500DB09AA9B80CA8AB1F9AF44D706631D7E64EDD7A12C63C6004C82BAC6D9A77797C1C14BFD56D16EBF3B545420E1688BBE352AC52BBD37E9E22834FA916E82876959D042264DB6C7A977D04591ED8274EE3E7164D35991E5C4293786DC22D1419676E669F1EE57C286AC980D7469FA094A3D7C57A2E5425CF530FEC3DC24D4B2135D2F851EC05A285CA1A8B44D1AD76402F4B9279F5A502017B616552F483DEDFCA5FDAB9AE635E6FF08849C6752F21669F6682EC99608A0B47B588D5A53620463EC98D244937758E6DE944A34F236F8927A438ECEC0AA0295CB42D9AAFD183FAC5242AC895E6199B615059DAF4503BBD347F454B9B0DB3930AA3AB126FD3D31A20D4AFC84BFAE974FCA0FA5AAE8BC4D25824774B491CA637DBE3746FA8FA00EA45C1277E45BBE1CF4F27F28E9C556D657C807EEF9F2638016DABC4FBEE193026D35895FEDC8FBF880CA11E85434E3E3AF476C53527BC362A8ADE4A388F12F8C44D21AECAB61D491EF44AD5CCD829645891D9C6400E09017C393922D4917795C122708850DF738F39E5C5F4D4558B9ED3FD2D725FDEA205FD6A7C7A1B67333304BEC8FFC227DA465E9B19E5674729D0C172AC7CC89E2B6FD9EEDB00F12C9";
        System.err.println("公钥加密密文 : " + mi);  
        //解密后的明文  
        String mingw = RSAToBCDCoder.decryptByPrivateKey(mi, priKey);  
        System.err.println("私钥解密明文: " + mingw);  
    } 
}
