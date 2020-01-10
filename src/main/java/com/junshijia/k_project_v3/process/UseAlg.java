package com.junshijia.k_project_v3.process;

import com.junshijia.k_project_v3.k_utils.KUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UseAlg {
    private InitInput input;
    private List<Float> outputK;
    private List<Float> outputCp;
    private Integer outputSeq;
    private Integer outputFlag;
    private Integer outputResultBit;


    public UseAlg() {
        this.input = new InitInput();
        this.outputK = new ArrayList<>();
        this.outputCp = new ArrayList<>();
    }

    public void usePython(){
        int count = 0;

        input.getK().set(0,0.7F);

        String args2 = "python F:\\a.py F:\\aaa.csv "+input.getInputK()+(input.getSeq())+" "+
                input.getInputCp()+input.getResult();
        System.out.println(args2);
        try {
            java.lang.Process pr = Runtime.getRuntime().exec(args2);

            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                readAlgOutput(line,count++);
            }
            in.close();
            int re=pr.waitFor();
            System.out.println(re==1?"----状态码1----运行失败":"----状态码0----运行成功");
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

    private void renewInput(){

    }

    public List<Float> getOutputK() {
        return outputK;
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
