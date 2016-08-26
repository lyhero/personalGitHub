package com.honeywell.myversionupdate;

/**
 * Created by H154326 on 16/8/11.
 * Email: yang.liu6@honeywell.com
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class VersionCommon {
    public static final String TAG = "VersionCommon";
    public static final String SERVER_IP="http://192.168.1.105/";
    public static final String SERVER_ADDRESS=SERVER_IP+"try_downloadFile_progress_server/index.php";//软件更新包地址
    public static final String UPDATESOFTADDRESS=SERVER_IP+"try_downloadFile_progress_server/update_pakage/baidu.apk";//软件更新包地址

    /**
     * 向服务器发送查询请求，返回查到的StringBuilder类型数据
     *
     * @param
     *            <NameValuePair> vps POST进来的参值对
     * @return StringBuilder builder 返回查到的结果
     * @throws Exception
     */
    public static StringBuilder post_to_server(List<NameValuePair> vps) {
        Log.e(TAG,"post_to_server");
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,2000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,2000);

//        DefaultHttpClient httpclient = new DefaultHttpClient();

        //httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        try {
            HttpResponse response = null;
            // 创建httpost.访问本地服务器网址
            HttpPost httpost = new HttpPost(SERVER_ADDRESS);
            //HttpPost httpost = new HttpPost("www.baidu.com");
            StringBuilder builder = new StringBuilder();

            httpost.setEntity(new UrlEncodedFormEntity(vps, HTTP.UTF_8));
            response = httpclient.execute(httpost); // 执行

            if (response.getEntity() != null) {
                Log.e(TAG,"post_to_server,response.getEntity");
                // 如果服务器端JSON没写对，这句是会出异常，是执行不过去的
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                String s = reader.readLine();
                for (; s != null; s = reader.readLine()) {
                    builder.append(s);
                }
            }
            return builder;

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("msg1",e.getMessage());
            return null;
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();// 关闭连接
                // 这两种释放连接的方法都可以
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e("msg",e.getMessage());
            }
        }
    }

    /**
     * 获取软件版本号
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
//        try {
//            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = BuildConfig.VERSION_CODE;//获取gradle中的version_code
////            verCode = context.getPackageManager().getPackageInfo(
////                    "com.example.try_downloadfile_progress", 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("msg",e.getMessage());
//        }
        return verCode;
    }
    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
//        try {
            verName = BuildConfig.VERSION_NAME;//获取gradle中的version_name
//            verName = context.getPackageManager().getPackageInfo(
//                    "com.example.try_downloadfile_progress", 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("msg",e.getMessage());
//        }
        return verName;
    }
}
