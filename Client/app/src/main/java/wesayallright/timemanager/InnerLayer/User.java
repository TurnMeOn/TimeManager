package wesayallright.timemanager.InnerLayer;

import android.media.TimedText;
import android.nfc.Tag;
import android.provider.DocumentsContract;
import android.sax.RootElement;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import wesayallright.timemanager.InnerLayer.LocalFile.DOMParser;
import wesayallright.timemanager.InnerLayer.LocalFile.LocalFile;
import wesayallright.timemanager.InnerLayer.Network.NetWork;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import wesayallright.timemanager.InnerLayer.Group.GroupList;
import wesayallright.timemanager.InnerLayer.exception.NotThisUserException;
import wesayallright.timemanager.InnerLayer.exception.WrongID;

/**
 * Created by mj on 17-4-16.
 * 用户类
 */

public class User {

    private enum Sex {male, female}

    public Date updateTime;

    public String userId;
    public String nickName;
    public String realName;
    public Location location;

    public int age;
    public Sex sex;
    public String studentId;
    public String phone;
    public String email;
    public String schoolId;
    public String schoolName;
    public String major;
    public int grade;
    public Date enrollDate;

    public GroupList groups;
    //CalendarList calendar;

    // 空的构造函数
    public User() {}

    // 登陆
    public static User signIn(String identified, String password) throws UnsupportedEncodingException {
        User u = new User();
        NetWork n = new NetWork();
        try {
            u.userId = n.login(identified, password);
        } catch (Exception e) {
            // TODO:登陆失败重新登陆(继续向上抛异常，交给界面层处理)
            e.printStackTrace();
        }
        u.loadInformation(); // 读取和整理个人信息，包括群组列表
        //u.update();// 更新这个人的信息
        Package.currentUser = u;
        return u;
    }
    // 注册
    public static User signUp(String identified, String password, String email) throws UnsupportedEncodingException {
        // TODO: 注册

        return signIn(identified, password);
    }
    // 同步数据
    public void update() throws UnsupportedEncodingException {
        if (updateTime == null) {
            NetWork.downloadUserInformation(userId);
        } else {
            Date remoteDate = NetWork.remoteUserInformationUpdateTime(userId);
            if (updateTime.before(remoteDate)) {
                // 如果本地时间比远程时间早，则下载
                NetWork.downloadUserInformation(userId);
            } else { // TODO:相等时间
                // 本地比远程新，上传
                NetWork.uploadUserInformation(userId);
            }
        }

        Document xml = new LocalFile(userId).parseXML();
        // TODO: 解析xml

    }
    // 保存数据
    public boolean save() {
        return false;
    }
    // 读取个人信息
    public void loadInformation() throws UnsupportedEncodingException {
        Package.currentUser = this; // 把当前登陆的用户保存到Package中

        // 从服务器上下载文件
        // TODO:网络模块

        NetWork.downloadUserInformation(userId);
        NetWork.downlocaUserCalendar(userId);
        NetWork.downloadUserGroupList(userId);


        // 在本地加载文件
        try {
            Log.i("User at here", userId);
            LocalFile userInformation = LocalFile.loadUser(userId);

            LocalFile userCalendar = LocalFile.loadCalendar(userId);
            LocalFile userGroupList = LocalFile.loadGroupList(userId);

            if (!userInformation.exists()) {
                Log.e("FILE", "文件不存在");
            }


            // 解析XML成Document
            Document userXML = (new DOMParser()).parse(userInformation);
            Document calendarXML = (new DOMParser()).parse(userCalendar);
            Document groupXML = (new DOMParser()).parse(userGroupList);

            // 从XML中读入数据
            Package.calendarXMLTree = calendarXML; // 陆文辉非要用course数组

            setValue(userXML);
            groups = new GroupList(groupXML);

        } catch (WrongID e) {
            Log.i("WrongID", "loadInformation: " + e.id);
            e.printStackTrace();
        } catch (ParseException | NotThisUserException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把xml中的内容读到user类的属性中
     */
    private void setValue(Document userXML) throws NotThisUserException, ParseException {
        Element rootElement = userXML.getDocumentElement();
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        updateTime = dateTimeFormat.parse(rootElement.getAttribute("updateTime"));
        if(! userId.equals(rootElement.getAttribute("id"))) {
            throw new NotThisUserException();
        }
        nickName = rootElement.getAttribute("nickname");
        realName = rootElement.getAttribute("realname");
        age = Integer.valueOf(rootElement.getAttribute("age"));
        sex = Sex.valueOf(rootElement.getAttribute("sex")); // 厉害了,我的枚举
        phone = rootElement.getAttribute("mobile");
        email = rootElement.getAttribute("email");
        schoolId = rootElement.getAttribute("schoolID");
        schoolName = rootElement.getAttribute("schoolName");
        location = new Location(rootElement.getAttribute("location"));
        location.setZipCode(rootElement.getAttribute("zipCode"));
        major = rootElement.getAttribute("major");
        grade = Integer.valueOf(rootElement.getAttribute("grade"));
        enrollDate = dateFormat.parse(rootElement.getAttribute("enrollDate"));
        studentId = rootElement.getAttribute("studentID");
    }

    public void printClass() {
        Log.i("PRINTUSER",
                "nickname=" + nickName + "\n" +
                "realName" + realName + "\n" +
                "age" + age + "\n" +
                //"sex" + sex.toString() + "\n" +
                "phone" + phone + "\n" +
                "email" + email + "\n" +
                "schoolID" + schoolId + "\n" +
                "schoolName" + schoolName + "\n" +
                "major" + major + "\n" +
                "grade" + grade + "\n");
    }
}
