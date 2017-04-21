package wesayallright.timemanager.InnerLayer.exception;

/**
 * Created by mj on 17-4-19.
 * 错误id
 */

public class WrongID extends Exception {
    public String id;
    public WrongID (String id1) {
        this.id = id1;
    }
}
