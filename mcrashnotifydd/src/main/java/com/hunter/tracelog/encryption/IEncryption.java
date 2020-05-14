package com.hunter.tracelog.encryption;

/**
 * 加密的接口
 * author: mayuhai
 * created on: 2019/6/20 12:29 PM
 */
public interface IEncryption {
    /**
     * 使用默认的密钥加密字符串
     *
     * @param content 需要加密的字符串
     * @return 返回已经加密完成的字符串
     */
    String encrypt(String content) throws Exception;

    /**
     * 使用自定义密钥加密字符串
     *
     * @param key 加密的密钥
     * @param src 需要加密的字符串
     * @return 加密完成的字符串
     * @throws Exception
     */
    String encrypt(String key, String src) throws Exception;


    /**
     * 使用自定义密钥解密字符串
     *
     * @param content 需要加密的字符串
     * @return 解密后的文本
     * @throws Exception
     */
    String decrypt(String key, String content) throws Exception;

    /**
     * 使用默认的密钥解密字符串
     *
     * @param content 需要解密的字符串
     * @return 返回已经解密完成的字符串
     */
    String decrypt(String content) throws Exception;


}
