package wesayallright.timemanager.InnerLayer.LocalFile;

import org.w3c.dom.Document;

/**
 * Created by mj on 17-4-16.
 * 文件类，所有对文件的操作均通过此类实现
 */

public class LocalFile extends java.io.File{
    private static String workPath; // 本程序执行时的位置，由MainActivity获取

    public LocalFile(String fname) {
        super(workPath + "/" +
                ((fname.charAt(0) == 'U')?"User/":
                        (fname.charAt(0)=='G')?"Group/":
                                (fname.charAt(0) == 'C')?"Calendar/":"BadDir") + fname);
        // holy shit。按编号分文件夹！
    }
    public void setCwd(String cwd) {
        workPath = cwd;
    }

    public Document parseXML() {
        return new DOMParser().parse(this);
    }

}
