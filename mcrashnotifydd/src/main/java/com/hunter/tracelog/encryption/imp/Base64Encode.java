package com.hunter.tracelog.encryption.imp;

import android.util.Base64;

import com.hunter.tracelog.encryption.IEncryption;

/**
 * Description:Base64编码
 * author: mayuhai
 * created on: 2019-07-09 17:09
 */
public class Base64Encode implements IEncryption {

    /**
     * @param content 需要编码的内容
     * @return 编码后的内容
     * @throws Exception
     */
    @Override
    public String encrypt(String content) throws Exception {
        String encodedString = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
//        Log.e("Base64", "Base64---->" + encodedString);
        return encodedString;
    }

    @Override
    public String encrypt(String key, String src) throws Exception {
        return null;
    }

    @Override
    public String decrypt(String key, String content) throws Exception {
        return null;
    }

    /**
     * @param content 编码的内容
     * @return 解码的内容
     * @throws Exception
     */
    @Override
    public String decrypt(String content) throws Exception {
        String decodedString =new String(Base64.decode(content,Base64.DEFAULT));
//        Log.e("Base64", "Base64---->" + decodedString);
        return decodedString;
    }
}
