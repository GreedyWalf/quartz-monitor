package com.qs.quartz.encypt;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import com.qs.quartz.BaseTest;
import org.apache.commons.io.Charsets;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class TestEncypt extends BaseTest {

    public static final String privateKey = "30820275020100300d06092a864886f70d01010105000482025f3082025b020100028181009dde6d26c3b8db526d1007742059f91ec5e4aa58293ef89c4b70e4c9536b0ca3859f9aa53c9dff9353da0d4842c7648cdb5c60988407c39eec194c2da8772b994976a65ea9c5c6d4eed9e7700e82ca68d418fd03d6697eaf7b437171b38365f1344fa18046f5cb8f842743d7562595713234f85e18130cc51e8e566babfaf0ff02030100010281807f2ba32fdbf078b4b0687e289cbafdc43d53d3a90b28cfea4f9754a59d4e19b34c3be2be82d320035543ccf94dda0374b86c74dfc753ddd0928e5a60595a0e42bab62dab9a8aea1ac0260de23406354031164bedea68c1c7e35e90622c78b31d87437cea174b96d9b69fbc9e51ac148a92eedd624dc77fa6629388d88ef7e191024100dc81b44aabbd52a5e26acfc65b8f208447b3719aff3c83753e68c6d624840d0bde6f42f7765cd176ba2fe4d50f3b0f1395f66acbe76ef426da018ca16920cb19024100b747a40ac9a2bc4349a0446c87196238812c6485fa3020a5240ef232540fcfe1f42a9bca68709a36abfb615395f5b5f4e91758a5986d86df98189130cbfe37d702400c53178fa0dfb911da80dbd21b65f98c4b31a564e3652f77cb203214dfff9d770f5caaa2883411e50fed035e4136acd60c68b479671b157c626cf9be3fd0fc69024052de518d8f1dc581a7088fe7822e37fad46cfe0695d8ace9fe23c3de7da3a89ac18b82654253a76690dc586532a8a65cd607784d675e1e5d7aa7a0fe2f3e028102402a7b0dddbdf28497a1ef5d3bcc714044dcf0ef64d9410df8396d39c2617c62628c61425081d33a100a27ac64147805569de5108a670c8db0a37cefc1e8682ffb";
    public static final String publicKey = "30819f300d06092a864886f70d010101050003818d00308189028181009dde6d26c3b8db526d1007742059f91ec5e4aa58293ef89c4b70e4c9536b0ca3859f9aa53c9dff9353da0d4842c7648cdb5c60988407c39eec194c2da8772b994976a65ea9c5c6d4eed9e7700e82ca68d418fd03d6697eaf7b437171b38365f1344fa18046f5cb8f842743d7562595713234f85e18130cc51e8e566babfaf0ff0203010001";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        RSA rsa = new RSA(privateKey, publicKey);
        byte[] encryptData = rsa.encrypt(StrUtil.bytes("测试", Charsets.UTF_8), KeyType.PublicKey);
        String encryptDataStr = StrUtil.str(encryptData, Charsets.UTF_8);
        System.out.println("===>>密文：" + encryptDataStr);

        String value = StrUtil.str(rsa.decrypt(encryptData, KeyType.PrivateKey), Charsets.UTF_8);
        System.out.println("===>>value=" + value);
    }


    @Test
    public void test() {
        RSA rsa = new RSA(privateKey, publicKey);
        byte[] encryptData = rsa.encrypt("张三和李四", KeyType.PublicKey);
        String encryptDataHexStr = HexUtil.encodeHexStr(encryptData);
        System.out.println("===>>>转换为16进制字符串的密文：" + encryptDataHexStr);
        System.out.println("===>>密文长度：" + encryptDataHexStr.length());


        byte[] decrypt = rsa.decrypt(encryptDataHexStr, KeyType.PrivateKey);
        String decryptDataStr = StrUtil.str(decrypt, Charsets.UTF_8);
        System.out.println("====>>>明文：" + decryptDataStr);

        byte[] decrypt2 = rsa.decrypt(HexUtil.decodeHex(encryptDataHexStr), KeyType.PrivateKey);
        String decryptDataStr2 = StrUtil.str(decrypt2, Charsets.UTF_8);
        System.out.println("====>>>明文2：" + decryptDataStr2);
    }


    /**
     * DES适合加密大数据量场合；
     * RSA用来加密DES的秘钥，提高DES秘钥的安全性；
     */
    @Test
    public void test2() {
        AES aes = new AES();
        String keyHexStr = HexUtil.encodeHexStr(aes.getSecretKey().getEncoded());
        System.out.println("==>>key=" + keyHexStr);
        System.out.println("==>>length=" + keyHexStr.length());

        RSA rsa = new RSA(null, publicKey);
        String encryptKeyHexStr = HexUtil.encodeHexStr(rsa.encrypt(keyHexStr, KeyType.PublicKey));
        System.out.println("==>>encryptKey=" + encryptKeyHexStr);
        System.out.println("==>>length=" + encryptKeyHexStr.length());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("0123456789");
        }

        String encryptHexStr = HexUtil.encodeHexStr(aes.encrypt(sb.toString()));
        System.out.println("==>>" + encryptHexStr);
        System.out.println("===>>" + encryptHexStr.length());
    }


    @Test
    public void test3(){
        MD5 md5 = new MD5();
        String md5Hex = md5.digestHex("000001", Charsets.UTF_8);
        System.out.println("===>>" + md5Hex.length());
    }
}
