package cn.v1.unionc_user.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import cn.v1.unionc_user.UnioncApp;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.data.SPUtil;
import cn.v1.unionc_user.network_frame.UnionAPI;


/**
 * Created by lawrence on 14/11/3.
 */
public class MobileConfigUtil {

    public static String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()
                            && !ip.isLinkLocalAddress()) {
                        return ipaddress = ip.getHostAddress();
                    }
                }

            }
        } catch (SocketException e) {
            Log.e("feige", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;

    }

    /**
     * 获取版本名字
     * @return
     */
    public static String getVersionName(){
        String mVersion = "";
        if(TextUtils.isEmpty(mVersion)) {
            try {
                // 获取packagemanager的实例
                PackageManager packageManager = UnioncApp.getInstance().getPackageManager();
                // getPackageName()是你当前类的包名，0代表是获取版本信息
                PackageInfo packInfo = packageManager.getPackageInfo( UnioncApp.getInstance().getPackageName(), 0);
                mVersion = packInfo.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mVersion;
    }

    /**
     * 获取手机唯一标识
     * @return
     */
    public static String getMacCode() {
        if (TextUtils.isEmpty(getImsi()))
            if (TextUtils.isEmpty(getImei()))
                if(!TextUtils.isEmpty(getMacAddress()))
                    return "MACADDRESS_"+getMacAddress();
                else
                    return "UUID_"+ getUUID();
            else
                return "IMEI_"+getImei();
        return "IMSI_"+getImsi();
    }

    /**
     * 获取手机的IMSI
     * @return
     */
    private static String getImsi() {
        String imsi = "";
        try {
            TelephonyManager tm = (TelephonyManager) UnioncApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            if (!TextUtils.isEmpty(tm.getSubscriberId())) {
                imsi = tm.getSubscriberId();
            }
        } catch (Exception e) {
        }
        return imsi;
    }

    /**
     * 获取手机的IMEI
     * @return
     */
    private static String getImei(){
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) UnioncApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            if (!TextUtils.isEmpty(tm.getDeviceId())) {
                imei = tm.getDeviceId();
            }
        } catch (Exception e) {
        }
        return imei;
    }

    /**
     * 获取手机MacAddress
     * @return
     */
    private static String getMacAddress(){
        String macAddress = "";
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wfm = (WifiManager)UnioncApp.getInstance().getSystemService(Context.WIFI_SERVICE);
            if(!TextUtils.isEmpty(wfm.getConnectionInfo().getMacAddress())){
                macAddress = wfm.getConnectionInfo().getMacAddress();
            }
        } catch (Exception e) {
        }
        return macAddress;

    }

    /**
     * UUID
     * @return
     */
    private static String getUUID(){
        if(TextUtils.isEmpty((String) SPUtil.get(UnioncApp.getInstance(), Common.KEY_UUID,""))){
            SPUtil.put(UnioncApp.getInstance(),Common.KEY_UUID, UUID.randomUUID().toString());
        }
        return (String) SPUtil.get(UnioncApp.getInstance(), Common.KEY_UUID,"");
    }

}
