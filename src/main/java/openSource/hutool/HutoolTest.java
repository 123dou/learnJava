package openSource.hutool;

import cn.hutool.system.*;
import cn.hutool.system.oshi.OshiUtil;
import org.junit.Test;
import oshi.hardware.ComputerSystem;

public class HutoolTest {

    public static void main(String[] args) {

    }

    @Test
    public void testSystem() {
        JavaInfo javaInfo = new JavaInfo();
        System.out.println(javaInfo.getVendor());
        JvmInfo jvmInfo = new JvmInfo();
        System.out.println(jvmInfo.getInfo());
        OsInfo osInfo = new OsInfo();
        System.out.println(osInfo.getName());
        UserInfo userInfo = new UserInfo();
        System.out.println(userInfo.getName());
        System.out.println(SystemUtil.getCurrentPID());
        ComputerSystem system = OshiUtil.getSystem();
        System.out.println(system);
    }

    @Test
    public void testSetting() {

    }

    @Test
    public void testCore() {

    }

    @Test
    public void testCrypto() {

    }

    @Test
    public void testScript() {

    }

    @Test
    public void testAop() {

    }

}
