package com.junshijia.k_project_v3.data_transfer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ProgramStatus {
    private int statusCode;
    private String fileName;
    private String outputData;

    public ProgramStatus() {
        this.fileName = "StatusCode.status";
        this.outputData = "";
    }

    private void readStatusCode(){
        try {
            this.outputData = FileUtils.readFileToString(new File(fileName),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeStatusCode(int status){
        File file = new File(fileName);
        String data = ""+status;
        try {
            FileUtils.write(file,data,"UTF-8",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOutputData() {
        this.readStatusCode();
        return outputData;
    }
}
