package wesayallright.timemanager.InnerLayer.Network;

import java.util.HashMap;

/**
 * Created by mj on 17-5-9.
 * 用于向服务器发送Get请求
 */

public class SentGet extends Thread{
    public String result;
    public boolean finish;

    private HashMap<String, String> params;
    private String addr;
    private String url;

    public SentGet() {
        super();
        params = new HashMap<>();
        url = "127.0.0.1:8000";
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addParam(HashMap <String, String> m) {
        params.putAll(m);
    }

    public void setUri(String u) {
        addr = url + '/' + u;
    }

    @Override
    public void run() {
        finish = false;

        try {
            String request; //= params.entrySet().stream();
            // TODO: 请求字符串

        } catch(Exception e) {
            e.printStackTrace();
        }
        finish = true;
    }
}
