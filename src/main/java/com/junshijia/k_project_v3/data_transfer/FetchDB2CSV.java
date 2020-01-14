package com.junshijia.k_project_v3.data_transfer;

import com.junshijia.k_project_v3.dao.TenMsDao;
import com.junshijia.k_project_v3.domain.TenMsData;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FetchDB2CSV {
    private int turbineNumber;
    private String dbName;

    //打开数据库的配置
    private InputStream is;
    private SqlSessionFactory factory;
    private SqlSession session;

    private TenMsDao dataDao;
    private List<TenMsData> dataList;
    private int maxId;

    public FetchDB2CSV(int turbineNumber) {
        this.turbineNumber = turbineNumber;
        this.setDbName();
    }

    private void connectDB(){
        //关闭文件输入流
        if(is!=null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.is = Resources.getResourceAsStream("DatabaseConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //打开配置文件中的test1，也就是使用第一个database
        factory = new SqlSessionFactoryBuilder().build(is, "megaSpace");

        //关闭文件输入流
        if(is!=null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //从数据库获取一次数据
    public void fetchInfo(){
        boolean flag = true;
        while(flag) {
            try {
                this.connectDB();
                //0 得到session
                this.session = this.factory.openSession();
                this.dataDao = session.getMapper(TenMsDao.class);

                //1 得到dataList
                this.maxId = dataDao.findLast(this.dbName).getId();
                if(this.maxId>90000) {
                    this.dataList = dataDao.findAll(this.dbName);
                }else{
                    System.out.println("w8 ecs to write table...");
                    Thread.sleep(6000000);
                }
                //2 清除缓存
                this.session.commit();
                this.session.clearCache();
                //测试：输出结果
                //System.out.println(data);
                //3 关闭session
                session.close();
                this.list2Csv();
                flag = false;
            } catch (Exception e) {
                System.out.println(this.dbName+": DB error, wait 5min and reconnect...");
                e.printStackTrace();
                try {
                    Thread.sleep(300000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.connectDB();
            }
        }
    }

    private void setDbName(){
        this.dbName = "wt"+turbineNumber+"_k_regulate";
    }

    public List<TenMsData> getDataList() {
        return dataList;
    }
    private void list2Csv(){
        StringBuilder head = new StringBuilder();
        StringBuilder body;
        head.append("HMI_IReg[106]").append("HMI_IReg[110]").append("HMI_IReg[168]").append("HMI_IReg[210]");
        head.append("HMI_IReg[211]").append("HMI_IReg[282]").append("HMI_Disc[912]").append("HMI_Disc[919]");
        File file = new File("/home/k_value/inputFile.csv");

        try {
            if(file.exists()){
                FileUtils.forceDelete(file);
            }
            FileUtils.write(file,head.toString(),"UTF-8",true);
            for(TenMsData data : this.dataList){
                body = new StringBuilder();
                body.append(data.getHMI_IReg106().toString()).append(data.getHMI_IReg110().toString());
                body.append(data.getHMI_IReg168().toString()).append(data.getHMI_IReg210().toString());
                body.append(data.getHMI_IReg211().toString()).append(data.getHMI_IReg282().toString());
                body.append(data.getHMI_Disc912().toString()).append(data.getHMI_Disc919().toString());
                FileUtils.write(file,body.toString(),"UTF-8",true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
