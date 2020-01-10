package test.RWFileTest;

import com.junshijia.k_project_v3.data_transfer.ProgramStatus;
import org.junit.Test;

public class WRTest {
    @Test
    public void WETest(){
        ProgramStatus wr = new ProgramStatus();
        wr.writeStatusCode(0);
        System.out.println(wr.getOutputData());
    }
}
