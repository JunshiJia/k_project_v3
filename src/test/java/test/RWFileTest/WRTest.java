package test.RWFileTest;

import com.junshijia.k_project_v3.data_transfer.ProgramStatus;
import com.junshijia.k_project_v3.k_utils.KUtils;
import org.junit.Test;

public class WRTest {
    @Test
    public void WETest(){
        ProgramStatus wr = new ProgramStatus();
        wr.writeStatusCode(0);
        System.out.println(wr.getOutputData());
    }

    @Test
    public void commonioTest(){
        KUtils.WriteAlg2File("33321");
    }
}
