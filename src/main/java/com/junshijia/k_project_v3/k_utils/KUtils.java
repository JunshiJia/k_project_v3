package com.junshijia.k_project_v3.k_utils;

import com.junshijia.k_project_v3.domain.TenMsData;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class KUtils {
    public static String List2InputFormat(List<Float> list){
        StringBuilder sb = new StringBuilder();
        for (Float aFloat : list) {
            sb.append(aFloat.toString()).append(" ");
        }
        return sb.toString();
    }

    public static void WriteAlg2File(String alg){
        File file = new File("/home/k_value/alg.txt");
        try {
            if(file.exists()){
                FileUtils.forceDelete(file);
            }
            FileUtils.write(file,alg,"UTF-8",false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

      public static void DeleteAlgFile(){
        File file = new File("/home/k_value/alg.txt");
        try {
            if(file.exists()){
                FileUtils.forceDelete(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean ifFileExists(){
        File file = new File("/home/k_value/alg.txt");

        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }

    public static String readOldAlgFromFile() {
        String data = "";
        try {
            data = FileUtils.readFileToString(
                    new File("/home/k_value/alg.txt"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}
