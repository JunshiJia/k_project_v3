package com.junshijia.k_project_v3;

import com.junshijia.k_project_v3.k_value_process.KValueProcess;

public class Main {
    public static void main(String[] args) {
        String mcIp = "127.0.0.1";
        int mcPort = 715;
        String ecsIp = "127.0.0.1";
        int ecsPort = 715;
        int turbineNum = 1;
        KValueProcess kValueProcess = new KValueProcess(mcIp,mcPort,ecsIp,ecsPort,turbineNum);
    }
}
