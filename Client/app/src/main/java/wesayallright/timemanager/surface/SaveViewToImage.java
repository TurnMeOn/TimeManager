package wesayallright.timemanager.surface;

/**
 * Created by mj on 17-5-2.
 *
 * 把view保存成图片
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

public class SaveViewToImage {

    public SaveViewToImage() {}

    public void save(View v) {
        // v.setDrawingCacheEnabled(true); // 打开view缓存

        int width = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        v.measure(width, height);
        int h =v.getMeasuredHeight();
        int w =v.getMeasuredWidth();

        if (w == 0)
            Log.e(TAG, "save: w == 0");
        if (h == 0)
            Log.e(TAG, "save: h == 0");
        Bitmap b = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);

        //b = Bitmap.createBitmap(v.getDrawingCache()); // 创建bitmap
        FileOutputStream fos;
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                new File(sdRoot + "/DCIM/TimeManager").mkdir();
                File file = new File(sdRoot, "/DCIM/TimeManager/test.PNG");
                file.createNewFile();
                fos = new FileOutputStream(file);
            } else
                throw new Exception("创建文件失败!");

            b.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
