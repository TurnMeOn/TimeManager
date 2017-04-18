package wesayallright.timemanager.InnerLayer;

/**
 * Created by mj on 17-4-16.
 * 用于描述地理位置
 */

public class Location {

    public String country;
    public String province;
    public String city;
    public String zipCode;

    /**
     * 构造函数，把xml中的字符串解析成对象
     * @param Line
     */
    public Location(String Line) {
        //country = "中国";
        String[] sp = Line.split("-");
        province = sp[0];
        city = sp[1];
    }

    public String toString() {
        return this.province + "-" + this.city;
    }
}
