package com.junshijia.k_project_v3.k_value_process;

import com.junshijia.k_project_v3.data_transfer.FetchDB2CSV;
import com.junshijia.k_project_v3.data_transfer.ReadMainControl;
import com.junshijia.k_project_v3.data_transfer.WRECSMod;
import com.junshijia.k_project_v3.k_utils.KUtils;

public class KValueProcess {
    private ReadMainControl mc;
    private WRECSMod ecs;
    private FetchDB2CSV db;
    private InitInput firstInput;
    private UseAlg alg;


    public KValueProcess(String mcIp, int mcPort, String ecsIp, int ecsPort, int turbineNum) {
        System.out.println("starting...");
        this.mc = new ReadMainControl(mcIp, mcPort);
        this.ecs = new WRECSMod(ecsIp, ecsPort);
        this.db = new FetchDB2CSV(turbineNum);
        this.alg = new UseAlg();

    }

    public void processLoop(){
        System.out.println("loop starting...");
        //1读主控,写入第一次输入
        this.firstInput = new InitInput(this.mc.getResult());
        //2写ecs的coil00001，开始取值
        this.ecs.writeK(1,0F);
        //3读ecs,开始循环
        //3.1先初次调用算法
        this.db.fetchInfo();
        //如果中途断电
        if(KUtils.ifFileExists()){
            this.alg.parseAlg2PythonFunc(KUtils.readOldAlgFromFile());
        }else {
            this.alg.usePython(firstInput.getInputK(), firstInput.getSeq().toString() + " ",
                    firstInput.getInputCp(), firstInput.getResult().toString());
        }
        //3.2循环调用算法
        while(this.alg.getOutputFlag() != 1){
            //4读数据库
            this.ecs.writeK(2,this.alg.getCurrentK());
            this.db.fetchInfo();
            //5用算法
            this.alg.usePython(KUtils.List2InputFormat(this.alg.getOutputK()),
                    this.alg.getOutputSeq().toString()+" ",
                    KUtils.List2InputFormat(this.alg.getOutputCp()),
                    this.alg.getOutputResultBit().toString());
            //6返回3.2
        }

        //7写ecs的最终k
        this.ecs.writeK(2,this.alg.getFinalK());
        //8删除记忆文件
        KUtils.DeleteAlgFile();
        System.out.println("process over, final K has been written, program exit normally...");
    }

}
