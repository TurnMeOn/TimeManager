package wesayallright.timemanager.InnerLayer.Group;

import org.w3c.dom.Element;

import java.util.ArrayList;

import wesayallright.timemanager.InnerLayer.LocalFile.DOMParser;

/**
 * Created by mj on 17-4-16.
 * 群组类
 */

public class Group {
    private enum Privilege {owner, manager, member}
    public String name;
    public String id;
    public ArrayList<String> members;
    public ArrayList<String> managers;
    public String owner;
    public boolean visible;
    public boolean fullDetail;
    private Privilege whatsme;

    public Group(String groupId) {
        id = groupId;
    }

    static Group simpleAdd(Element n) {
        DOMParser.printXML(n);
        Group group = new Group(n.getAttribute("id"));
        group.name = n.getAttribute("name");
        group.visible = Boolean.valueOf(n.getAttribute("visible"));
        group.whatsme = Privilege.valueOf(n.getAttribute("privilege"));
        group.fullDetail = false;
        return group;
    }
}
