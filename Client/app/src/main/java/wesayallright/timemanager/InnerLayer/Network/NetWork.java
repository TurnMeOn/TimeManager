package wesayallright.timemanager.InnerLayer.Network;

import java.util.Date;

/**
 * Created by mj on 17-4-16.
 * 与网络相关的一些操作
 */

public class NetWork {
    private static String addr;
    private static boolean connected;
    public NetWork() {}

    public void setAddress(String s) {
        addr = s;
    }

    // 登陆,登陆成功返回用户ID，失败抛异常
    public String login(String identified, String password) {
        return "P1234567";
    }

    static public void downloadUserInformation(String userid) {
        // TODO:下载
    }

    static public void uploadUserInformation(String userid) {
        // TODO: 上传
    }

    static public Date remoteUserInformationUpdateTime(String userid) {
        // TODO: 远程个人信息更新时间
        return null;
    }
}
