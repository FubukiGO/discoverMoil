package com.yk.util;

public class Util {

    public static String formString(String result){
        if(result.trim()==null||"".equals(result.trim())){
            return null;
        }
        return result.trim().split("\n")[1];
    }




}
