package anjuyi.cc.edeco.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import Decoder.BASE64Encoder;

/**
 * Created by ly on 2016/5/26 17:37.
 * 跟App相关的辅助类
 */
public class AppUtils {
    private AppUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 解析String 类型的arrays.xml元素
     *
     * @param context
     * @param arrayId
     * @return
     */
    public static List<String> stringArrayToList(Context context, int arrayId) {
        return Arrays.asList(context.getResources().getStringArray(arrayId));
    }

    /**
     * 手机号校验
     */
    public static final String ISPHONENUM = "^[1][3,4,5,7,8][0-9]{9}$";
    /**
     * 请输入6-20位字母和数字组合的登录密码
     * 密码校验
     */
    public static final String ISPWD = "([0-9](?=[0-9]*?[a-zA-Z])\\w{5,19})|([a-zA-Z](?=[a-zA-Z]*?[0-9])\\w{5,19})";
    /**
     * 校验密码强度
     * 密码的强度必须是包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间。
     */
    public static final String ISPASSWORD = "^(?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";
    /**
     * 校验中文
     * 字符串仅能是中文
     */
    public static final String ONLYCHINESE = "^[\\\\u4e00-\\\\u9fa5]{0,}$";

    /**
     * 由数字、26个英文字母或下划线组成的字符串
     */
    public static final String ISWORD = "^\\\\w+$";

    /**
     * 校验E-Mail 地址
     * 同密码一样，下面是E-mail地址合规性的正则检查语句。
     */
    public static  final String ISEMAIL = "[\\\\w!#$%&'*+/=?^_`{|}~-]+(?:\\\\.[\\\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\\\w](?:[\\\\w-]*[\\\\w])?\\\\.)+[\\\\w](?:[\\\\w-]*[\\\\w])?";


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     */
    public static String getVersionName(Context context)
    {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取设备ID
     */
    public static String getDeviceId(Context context)
    {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
    }

    /**
     * base64加密
     * @param s
     * @return
     */
    public static String getBASE64(String s) {
        if (s == null) return null;
        return (new BASE64Encoder()).encode(s.getBytes());
    }

    /**
     * double类型的price变 ￥0.00
     * @param price
     * @return
     */
    public static String formatPrice(double price){
        DecimalFormat df = new DecimalFormat("0.00");
        String format ="￥" +df.format(price);
        return format;
    }

    /**
     * Try to return the absolute file path from the given Uri  兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }



}
