package wesayallright.timemanager.InnerLayer;

import android.provider.DocumentsContract;

import org.w3c.dom.Document;

import wesayallright.timemanager.InnerLayer.LocalFile.DOMParser;
import wesayallright.timemanager.InnerLayer.LocalFile.LocalFile;
import wesayallright.timemanager.InnerLayer.Network.NetWork;
import java.util.Date;
import wesayallright.timemanager.InnerLayer.Group.GroupList;

/**
 * Created by mj on 17-4-16.
 * 用户类
 */

public class User {

    private enum Sex {male, female};

    public Date updateTime;

    public String userId;
    public String nickName;
    public String realName;
    public Location location;

    public int age;
    public Sex sex;
    public String phone;
    public String email;
    public String schoolId;
    public String schoolName;
    public String major;
    public int grade;

    public GroupList groups;
    //CalendarList calendar;

    // 空的构造函数
    public User() {}

    // 登陆
    public static User signIn(String identified, String password) {
        User u = new User();
        NetWork n = new NetWork();
        try {
            u.userId = n.login(identified, password);
        } catch(Exception e) {
            // TODO:登陆失败重新登陆(继续向上抛异常，交给界面层处理)
            e.printStackTrace();
        }
        u.update();// 更新这个人的信息
        return u;
    }
    // 注册
    public static User signUp(String identified, String password, String email) {
        // TODO: 注册
        User u = new User();
        return u;
    }
    // 同步数据
    public void update() {
        NetWork n = new NetWork();
        if (updateTime == null) {
            n.downloadUserInformation(userId);
        } else {
            Date remoteDate = n.remoteUserInformationUpdateTime(userId);
            if (updateTime.before(remoteDate)) {
                // 如果本地时间比远程时间早，则下载
                n.downloadUserInformation(userId);
            } else {
                // 本地比远程新，上传
                n.uploadUserInformation(userId);
            }
        }

        Document xml = new LocalFile(userId).parseXML();
        // TODO: 解析xml

    }
    // 保存数据
    public boolean save() {
        return false;
    }
}
