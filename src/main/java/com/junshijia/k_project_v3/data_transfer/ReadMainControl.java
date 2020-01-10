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

public class ReadMainControl {
    //modbus para
    private IpParameters ipParameters;
    private ModbusFactory factory;
    private ModbusMaster master;
    private BatchRead<Integer> batch;
    //address ip port
    private int port;
    private String ip;
    private int id;
    private int address;
    private float result;

    public ReadMainControl(String ip) {
        this.ip = ip;
        this.setIpPortAdd();
        this.batch = new BatchRead<Integer>();
        //加一个循环，id=2-6都要读

        this.batch.addLocator(0, BaseLocator.inputRegister(1, this.address, DataType.FOUR_BYTE_FLOAT));

        //master的信息
        this.ipParameters = new IpParameters();
        ipParameters.setHost(this.ip);
        ipParameters.setPort(this.port);
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
                System.out.println("slave port:715 wait 40s and re-initiate...");
                try {
                    Thread.sleep(40000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void setIpPortAdd(){
        //this.ip = "192.168.101.244";
        //this.ip = "127.0.0.1";
        this.port = 9876;
        //id = 2-6
        this.id = 2;
        this.address = 1699;
    }

    public void readSlaveOnce(){
        boolean flag = true;
        this.setMasterAndInit();

        while(flag) {
            try {
                batch.setContiguousRequests(false);
                BatchResults<Integer> results = master.send(batch);
                this.result = (float)results.getValue(0);

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

    public float getResult() {
        this.readSlaveOnce();
        return result;
    }
}
