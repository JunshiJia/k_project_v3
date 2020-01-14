package com.junshijia.k_project_v3.k_value_process;

import com.junshijia.k_project_v3.k_utils.KUtils;

import java.util.ArrayList;
import java.util.List;

public class InitInput {
    private List<Float> k;
    private Integer seq;
    private List<Float> cp;
    private Integer result;


    //字符串输入
    private String inputK;
    private String inputCp;

    //外部输入
    public InitInput(Float kopt) {
        k = new ArrayList<>();
        cp = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            k.add(0F);
            cp.add(0F);
        }
        this.k.set(0,kopt);
        this.seq = 1;
        this.result = 0;
    }


    public List<Float> getK() {
        return k;
    }

    public void setK(List<Float> k) {
        this.k = k;
    }

    public List<Float> getCp() {
        return cp;
    }

    public void setCp(List<Float> cp) {
        this.cp = cp;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getInputK() {
        this.inputK = KUtils.List2InputFormat(this.k);
        return this.inputK;
    }

    public void setInputK(String inputK) {
        this.inputK = inputK;
    }

    public String getInputCp() {
        this.inputCp = KUtils.List2InputFormat(this.cp);
        return this.inputCp;
    }

    public void setInputCp(String inputCp) {
        this.inputCp = inputCp;
    }
}
