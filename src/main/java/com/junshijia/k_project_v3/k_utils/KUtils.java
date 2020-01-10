package com.junshijia.k_project_v3.k_utils;

import java.util.List;

public class KUtils {
    public static String List2InputFormat(List<Float> list){
        StringBuilder sb = new StringBuilder();
        for (Float aFloat : list) {
            sb.append(aFloat.toString()).append(" ");
        }
        return sb.toString();
    }
}
