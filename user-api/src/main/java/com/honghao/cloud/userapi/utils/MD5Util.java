package com.honghao.cloud.userapi.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;

/*** 
 * MD5加密 生成md5码
 * @return 返回md5码
 */
public class MD5Util {

    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    public static void main(String[] args) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyddMM");
    	String da = sdf.format(System.currentTimeMillis());
    	String string = "YDP"+"张三"+"15112345678"+"1234"+"210000"+"上海云递配公司"+da;
    	try {
			MD5Util.md5Encode(string).toUpperCase();
		} catch (Exception e) {
		
		}
	}

}