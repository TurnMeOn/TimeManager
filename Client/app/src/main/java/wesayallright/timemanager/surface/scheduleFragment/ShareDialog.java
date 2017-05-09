package wesayallright.timemanager.surface.scheduleFragment;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import wesayallright.timemanager.InnerLayer.Network.SendPost;
import wesayallright.timemanager.R;

/**
 * Created by mj on 17-5-2.
 * 分享对话框
 */

public class ShareDialog {

    private HashMap<String, String> data;

    public ShareDialog(){
        data = new HashMap<>();
    }

    public void setCount(int count)
    {
        data.put("count",""+count);
    }

    public void setTime(String start,String end)
    {
        data.put("startAndEnd",start+"/"+end);
    }

    public void addPramas(String key, String value) {
        data.put(key, value);

    }

    public void show(Activity a) {
        AlertDialog.Builder d = new AlertDialog.Builder(a);
        View v = View.inflate(a, R.layout.share_dialog, null);
        TextView success = (TextView)v.findViewById(R.id.sd_success);
        TextView url = (TextView)v.findViewById(R.id.sd_destination);


       data.put("user", "super");
//        data.put("count",  "10");
//        data.put("startAndEnd","7:00/16:00");
//        data.put("1",  "课1/1/7:00/10:00");
//        data.put("2",  "课2/2/7:00/8:00");
//        data.put("3",  "课3/3/8:00/9:30");
//        data.put("4",  "课4/4/7:00/8:00");
//        data.put("5",  "课5/5/11:00/13:00");
//        data.put("6",  "课6/4/10:00/15:00");
//        data.put("7",  "课7/6/7:00/9:00");
//        data.put("8",  "课8/7/7:00/8:00");
//        data.put("9",  "课9/2/10:00/13:00");
//        data.put("10",  "课10/3/10:00/12:00");

        SendPost remote = new SendPost();
        remote.addParam(data);
        Thread t = new Thread(remote);

        t.start();

        while (!remote.finish)
            ;

        Log.i("返回结果", "11" + remote.result);

        if (!remote.result.equals("fail")) {
            success.setText("成功");
            success.setTextColor(0xff00ff0d);
            url.setText(remote.result );
        }
        else {
            success.setText("失败");
            success.setTextColor(0xffff0000);
            url.setText("(╯°Д°）╯︵ /(.□ . \\\\)");
        }

        d.setView(v);
        d.setTitle("分享日程");
        d.show();
    }
}
