package com.junshijia.k_project_v3.k_value_process;


import com.junshijia.k_project_v3.k_utils.KUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UseAlg {
    private List<Float> outputK;
    private List<Float> outputCp;
    private Integer outputSeq;
    private Integer outputFlag;
    private Integer outputResultBit;
    private String arg;


    public UseAlg() {
        this.outputK = new ArrayList<>();
        this.outputCp = new ArrayList<>();
    }

    public void usePython(String inputK, String inputSeq, String inputCp, String inputResult) {
        this.arg = "python F:\\a.py F:\\aaa.csv " +
                inputK + inputSeq + inputCp + inputResult;

        System.out.println(this.arg);
        //把输入传到文件，包括算法计算的值等
        KUtils.WriteAlg2File(this.arg);
        //调用算法
        this.parseAlg2PythonFunc(this.arg);
    }

    public void parseAlg2PythonFunc(String args){
        int count = 0;
        try {
            java.lang.Process pr = Runtime.getRuntime().exec(args);

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                //写结果
                readAlgOutput(line,count++);
            }
            in.close();
            int re=pr.waitFor();
            System.out.println(re==1?"python算法：----状态码1----运行失败":"----状态码0----运行成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readAlgOutput(String line, int count){
        String[] datas;
        if(count == 0){
            datas = line.split(",");
            for(String k : datas ) {
                this.outputK.add(Float.valueOf(k));
            }
            System.out.println(this.outputK);
        }else if(count == 1){
            this.setOutputSeq(Integer.valueOf(line));
            System.out.println(outputSeq);
        }else if(count == 2){
            datas = line.split(",");
            for(String cp : datas ) {
                this.outputCp.add(Float.valueOf(cp));
            }
            System.out.println(this.outputCp);
        }else if(count == 3){
            this.setOutputResultBit(Integer.valueOf(line));
            System.out.println(outputResultBit);
        }else if(count == 4){
            this.setOutputFlag(Integer.valueOf(line));
            System.out.println(outputFlag);
        }
    }

    public List<Float> getOutputK() {
        return outputK;
    }

    public Float getCurrentK(){
        int i;
        Float currentK = 0F;
        for(i = 0;i < outputK.size();i++){
            if(outputK.get(i)==0){
                break;
            }
            currentK = outputK.get(i);
        }
        return currentK;
    }

    public Float getFinalK(){
        if(this.getOutputFlag()==1) {
            return this.outputK.get(outputResultBit);
        }else{
            System.out.println("result bit is 0, no final K");
            return 0F;
        }
    }

    public void setOutputK(List<Float> outputK) {
        this.outputK = outputK;
    }

    public List<Float> getOutputCp() {
        return outputCp;
    }

    public void setOutputCp(List<Float> outputCp) {
        this.outputCp = outputCp;
    }

    public Integer getOutputSeq() {
        return outputSeq;
    }

    public void setOutputSeq(Integer outputSeq) {
        this.outputSeq = outputSeq;
    }

    public Integer getOutputFlag() {
        return outputFlag;
    }

    public void setOutputFlag(Integer outputFlag) {
        this.outputFlag = outputFlag;
    }

    public Integer getOutputResultBit() {
        return outputResultBit;
    }

    public void setOutputResultBit(Integer outputResultBit) {
        this.outputResultBit = outputResultBit;
    }
}
