package com.junshijia.k_project_v3.data_transfer;

import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.WriteCoilRequest;

import java.sql.SQLOutput;

public class WRECSMod {
    //modbus para
    private IpParameters ipParameters;
    private ModbusFactory factory;
    private ModbusMaster master;
    private BatchRead<Integer> batch;
    //address ip port
    private int port;
    private String ip;
    //output
    private float currentKopt;


    public WRECSMod(String ecsIp, int ecsPort) {
        //this.setIpPortAdd();
        this.batch = new BatchRead<Integer>();
        //slave的信息
        this.ipParameters = new IpParameters();
        ipParameters.setHost(ecsIp);
        ipParameters.setPort(ecsPort);
        this.factory = new ModbusFactory();
    }

    private void setMasterAndInit(){
        this.master = factory.createTcpMaster(this.ipParameters, false);
        this.master.setTimeout(4000);
        this.master.setRetries(1);
        boolean flag  = true;
        while(flag) {
            try {
                this.master.init();
                flag = false;
            } catch (ModbusInitException e) {
                System.out.println("slave port:9876 wait 400s and re-initiate...");
                try {
                    Thread.sleep(400000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void setIpPortAdd(){
        this.ip = "127.0.0.1";
        this.port = 9876;
    }

    //读前置系统是否完成
    public void readSlave(){
        boolean flag = true;
        this.setMasterAndInit();
        this.batch.addLocator(0, BaseLocator.holdingRegister(1, 50, DataType.FOUR_BYTE_FLOAT));
        this.batch.addLocator(1, BaseLocator.inputRegister(1, 1, DataType.FOUR_BYTE_FLOAT));

        while(flag) {
            try {
                batch.setContiguousRequests(false);
                BatchResults<Integer> results = master.send(batch);
                //System.out.println(results.getValue(0));
                this.currentKopt = (float)results.getValue(0);

                flag = false;
            } catch (ModbusTransportException e) {
                System.out.println("reconnect to master 40s latter...");
                this.master.destroy();
                try {
                    Thread.sleep(40000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.setMasterAndInit();
            } catch (ErrorResponseException e) {
                e.printStackTrace();
            } finally {
                this.master.destroy();
            }
        }
    }

    public void writeK(int function, Float kopt){
        boolean flag = true;
        this.setMasterAndInit();
        //BaseLocator<Number> k_reg = BaseLocator.holdingRegister(1, 50, DataType.FOUR_BYTE_FLOAT_SWAPPED);

        BaseLocator.inputStatus(1,0);
        //master.setValue(locator, 10000000);
        while(flag) {
            try {


                if(function==1) {
                    //打开开关然后关闭，触发ecs采集
                    new WriteCoilRequest(1, 0, true);
                    Thread.sleep(4000);
                    new WriteCoilRequest(1, 0, false);
                }else if(function == 2 && kopt != 0F){
                    //需要测试
                    BaseLocator<Number> ecsK = BaseLocator.holdingRegister(
                            1, 1000, DataType.FOUR_BYTE_FLOAT_SWAPPED);
                    master.setValue(ecsK, kopt);
                    System.out.println(master.getValue(ecsK));
                    //打开开关然后关闭，触发ecs采集
                    new WriteCoilRequest(1, 0, true);
                    Thread.sleep(4000);
                    new WriteCoilRequest(1, 0, false);
                }else{
                    System.out.println("error...funciton == 2 and kopt == 0F");
                }

                flag = false;
            } catch (Exception e) {
                System.out.println("reconnect to master 40s latter...");
                this.master.destroy();
                try {
                    Thread.sleep(40000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.setMasterAndInit();
            } finally {
                this.master.destroy();
            }
        }
    }

    public float getCurrentKopt() {
        this.readSlave();
        return currentKopt;
    }
}
