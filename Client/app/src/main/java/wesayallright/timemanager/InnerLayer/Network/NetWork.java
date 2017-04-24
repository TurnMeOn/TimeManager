package wesayallright.timemanager.InnerLayer.Network;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by mj on 17-4-16.
 * 与网络相关的一些操作
 */

public class NetWork {
    private static String host;
    private static int port;
    private static boolean connected;
    public NetWork() {}

    public void setAddress(String s) {
        NetWork.host = s;
    }
    public void setAddress() {
        NetWork.host = "127.0.0.1";
        NetWork.port = 9999;
    }

    // 登陆,登陆成功返回用户ID，失败抛异常
    public String login(String identified, String password) {
        return "U1234567";
    }

    static public void downloadUserInformation(String userid) throws UnsupportedEncodingException {
        request("user_" + userid + ".xml");
    }

    static public void downlocaUserCalendar(String userid) throws UnsupportedEncodingException {
        request("cal_" + userid + ".xml");
    }

    static public void downloadUserGroupList(String userid) throws UnsupportedEncodingException {
        request("gpl" + userid + ".xml");
    }

    static public void request(String requestFile) throws UnsupportedEncodingException {
        byte[] fnameBytes = new byte[128];
        byte[] fnameLengthBytes = new byte[16];
        byte[] fsizeBytes = new byte[8];
        byte[] md5Bytes = new byte[32];
        byte[] endSign = new byte[3];
        try {
            Socket sock = new Socket(NetWork.host, NetWork.port);
            Log.i("Network", "Connected successful"); // 连接到服务器
            DataInputStream is = new DataInputStream(sock.getInputStream()); // 创建读取数据流
            DataOutputStream os =new DataOutputStream(sock.getOutputStream()); // 发送数据流

            ByteBuffer requestFileBytes = ByteBuffer.allocate(128); // 要下载的文件名数据缓冲,128字节
            requestFileBytes.put(requestFile.getBytes()); // 把文件名转化成字节数组
            os.write(requestFileBytes.array()); // 发送数据

            byte[] header = new byte[179];
            // header长179字节, 128字节文件名, 8字节文件名长度, 8字节文件长度, 32字节MD5, 3字节EOS
            //is.read(header); // 读取header


            is.read(fnameBytes); // 读入文件名
            is.read(fnameLengthBytes); // 读入文件名长度
            is.read(fsizeBytes);  // 读入 文件大小
            is.read(md5Bytes);  // 读入MD5
            is.read(endSign); // 读入结束符

            String fname = new String(fnameBytes, "utf8");
            long fnameLength = toInteger(fnameLengthBytes, 8);
            long fsize = toInteger(fsizeBytes, 8);
            String md5 = new String(md5Bytes, "utf8");

            if (!(new String(endSign, "utf8")).equals("EOS")) {
                // 如果结束符不是EOS,说明传输遇到问题
                throw new NetworkErrorException();
            }
            Log.i("文件Header:", "文件名:" + fname);
            Log.i("文件Header:", "文件名长度:" + fnameLength);
            Log.i("文件Header:", "文件大小:" + fsize);
            Log.i("文件Header:", "md5:" + md5);

            byte[] buffer = new byte[128];
            long recvSize = 0;
            File f = new File(requestFile);
            if (!f.exists())
                if (! f.createNewFile()) {
                    throw new UnknownError();
                }
            FileOutputStream of = new FileOutputStream(f);

            while (recvSize < fsize) {
                is.read(buffer);
                of.write(buffer);
                recvSize += 1024;
            }

            Log.i("接收文件", "接收文件" + requestFile + "成功");

            of.close();


        } catch (IOException | NetworkErrorException | UnknownError e) {
            if(e.equals(new NetworkErrorException())) {
                Log.e("网络错误", "接收文件header失败");
                Log.e("文件Header:", "文件名:" + (new String(fnameBytes, "utf8")));
                Log.e("文件Header:", "文件名长度:" + toInteger(fnameLengthBytes, 8));
                Log.e("文件Header:", "文件大小:" + toInteger(fsizeBytes, 8));
                Log.e("文件Header:", "md5:" + (new String(md5Bytes, "utf8")));
                Log.e("文件Header:", "结束符:" + (new String(endSign, "utf8")));
            }
            e.printStackTrace();
        }
    }

    static public void uploadUserInformation(String userid) {
        // TODO: 上传
    }

    static public Date remoteUserInformationUpdateTime(String userid) {
        // TODO: 远程个人信息更新时间
        return null;
    }

    private static long toInteger(byte[] b, int n) {
        long ans = 0;
		/*
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 8; j++) {
				if ((b[i] & (0x80 >> j)) != 0)
					System.out.print('1');
				else
					System.out.print('0');
			}
			System.out.print(" ");
		}
		System.out.print('\n');
		*/
        ans = 0;
        for (int i = n - 1; i >= 0; i--) {
            ans |=  b[i] << i * 8;

        }
        return ans;
    }
}
