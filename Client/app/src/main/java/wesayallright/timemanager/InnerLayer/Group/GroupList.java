package wesayallright.timemanager.InnerLayer.Group;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import wesayallright.timemanager.InnerLayer.LocalFile.DOMParser;
import wesayallright.timemanager.InnerLayer.Package;
import wesayallright.timemanager.InnerLayer.exception.NotThisUserException;

/**
 * Created by mj on 17-4-16.
 * 群组列表
 */

public class GroupList {

    public ArrayList<Group> groupList;
    public int changedTimes;
    public Date updateTime;

    public GroupList(Document groupXML) throws NotThisUserException, ParseException {
        Element rootElement = groupXML.getDocumentElement();
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        // 设置修改时间
        if (! Package.currentUser.userId.equals(rootElement.getAttribute("belongs"))) {
            DOMParser.printXML(rootElement);
            throw new NotThisUserException();
        }
        updateTime = dateTimeFormat.parse(rootElement.getAttribute("updateTime"));
        // 循环添加每一个项目
        NodeList groups = rootElement.getElementsByTagName("group");

        groupList = new ArrayList<>();
        for (int i = 0; i < groups.getLength(); i++) {
             groupList.add(Group.simpleAdd((Element)groups.item(i)));
        }
        changedTimes = 0;
    }

}
