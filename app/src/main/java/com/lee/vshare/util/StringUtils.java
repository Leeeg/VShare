package com.lee.vshare.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CreateDate：19-1-3 on 下午5:14
 * Describe:
 * Coder: lee
 */
public class StringUtils {

    public static String dataToString(Date date){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }catch (Exception e){
            return "unknown";
        }
    }

    public static boolean isStringValid(String s){
        if (null == s || s.isEmpty()){
            return false;
        }
        return true;
    }

}
