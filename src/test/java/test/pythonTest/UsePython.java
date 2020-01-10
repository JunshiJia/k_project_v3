package test.pythonTest;

import com.junshijia.k_project_v3.process.InitInput;
import com.junshijia.k_project_v3.process.UseAlg;
import org.junit.Test;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.server.ExportException;
import java.util.List;

public class UsePython {
    @Test
    public void usePython() throws Exception {
        InitInput i = new InitInput();
        i.getK().set(0,0.7F);

        String[] args = new String[] { "python", "F:\\a.py", "F:\\aaa.csv",
                i.getInputK(), String.valueOf(i.getSeq()),
                i.getInputCp(), String.valueOf(i.getResult()) };

        String args2 = "python F:\\a.py F:\\aaa.csv "+i.getInputK()+(i.getSeq())+" "+
                i.getInputCp()+i.getResult();
        System.out.println(args2);
        Process pr = Runtime.getRuntime().exec(args2);


        //System.out.println( (i.getK()));
        //Process pr = Runtime.getRuntime().exec("python F:\\b.py");
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println("line:" + line);
        }
        in.close();
        int re=pr.waitFor();
        System.out.println(re==1?"----状态码1----运行失败":"----状态码0----运行成功");
    }

    @Test
    public void usePython2() {
        UseAlg alg = new UseAlg();
        alg.usePython();
    }
}
