package stooged.ps4serve;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utils {

    private static Toast msgToast = null;

    public static String getDataDir(Context context) {
        try
        {
            return  context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return null;
        }
    }

    public static String getIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface iface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = iface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    public static void createResFile(Context context, int resourceId, String resourceName){
        String fpath = getDataDir(context) + "/" + resourceName;
        try{
            InputStream in = context.getResources().openRawResource(resourceId);
            FileOutputStream out = new FileOutputStream(fpath);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static void SaveSetting(Context context, String sKey, String sValue)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(sKey, sValue);
        editor.apply();
    }

    public static String GetSetting(Context context, String sKey, String sDefault)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return sharedPref.getString(sKey, sDefault);
    }

    public static void ShowToast(Context mContext, String msg, int duration )
    {
        if(msgToast!=null) {
            msgToast.cancel();
        }
        msgToast = Toast.makeText(mContext, msg, duration);
        msgToast.show();
    }

}
