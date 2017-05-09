package wesayallright.timemanager.InnerLayer.Network;

import android.content.res.Resources;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mj on 17-5-2.
 * 发送POST请求
 */

public class SendPost extends Thread{
    public boolean finish;
    public String result;

    private HashMap<String, String> params;

    private String addr;
    private String url;

    public SendPost() {
        params = new HashMap<>();
        url = "127.0.0.1:8080";

    }

    public void setUri(String uri) {
        switch (addr = url + '/' + uri) {
        }
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addParam(HashMap<String, String> m) {
        params.putAll(m);
    }

    @Override
    public void run() {
        finish = false;


        try {
            StringBuilder d = new StringBuilder();
            for (Map.Entry<String, String> i : params.entrySet()) {
                d.append(i.getKey() + "=" + i.getValue() + "&");
            }
            String sd = new String(d);

            URL url = new URL(addr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream o = con.getOutputStream();
            o.write(sd.getBytes("UTF-8"));
            o.flush();
            o.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + addr);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //打印结果
            System.out.println(response.toString());
            result = response.toString();

        } catch (IOException e) {
            result = "fail";
            e.printStackTrace();
        }
        finish = true;
    }
}
