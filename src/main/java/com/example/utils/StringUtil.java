package com.example.utils;

public class StringUtil {
    
    // コンストラクタ
    StringUtil(){

    }

    /**
     * NullorEmpty判定
     * @param strVal
     * @return boolean
     */
    public static boolean IsNullorEmpty(String strVal) {

        if (strVal == null || strVal.isEmpty()) {
            return true;
        }

        return false;
    }

}
