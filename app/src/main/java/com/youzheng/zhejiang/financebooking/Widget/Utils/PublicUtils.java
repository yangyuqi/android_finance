package com.youzheng.zhejiang.financebooking.Widget.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qiuweiyu on 2018/2/9.
 */

public class PublicUtils {

    public static String SUCCESS = "1000" ;
    public static String RELOGIN = "1011";
    public static int px2dip(int pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String getMsg(String code){
        if (code.equals("1000")){
            return "成功";
        }else if (code.equals("1001")){
            return "参数不合法";
        }else if (code.equals("1002")){
            return "数据异常";
        }else if (code.equals("1003")){
            return "多次登录异常";
        }else if (code.equals("1004")){
            return "密码错误";
        }else if (code.equals("1005")){
            return "用户已存在";
        }else if (code.equals("1006")){
            return "短信验证码错误";
        }else if (code.equals("1007")){
            return "验证码错误";
        }else if (code.equals("1008")){
            return "验证码过期";
        }else if (code.equals("1009")){
            return "用户不存在";
        }else if (code.equals("1010")){
            return "账户信息异常";
        }else if (code.equals("200000")){
            return "第三方信息认证成功";
        }else if (code.equals("200001")){
            return "第三方信息认证失败";
        }else if (code.equals("200002")){
            return "未进行实名认证";
        }else if (code.equals("2001")){
            return "已完成认证";
        }else if (code.equals("2002")){
            return "账户冻结中";
        }else if (code.equals("2003")){
            return "交易密码错误";
        }else if (code.equals("2004")){
            return "余额不足";
        }else if (code.equals("2005")){
            return "产品已投满 ";
        }else if (code.equals("1011")){
            return "token信息异常";
        }else if (code.equals("1000")){
            return "成功";
        }else if (code.equals("1057")){
            return "仅限新用户购买";
        }

        return "成功";
    }

    public static float dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }


    public static String phoneNum(String phonenum){

        if(!TextUtils.isEmpty(phonenum) && phonenum.length() > 6 ){
            StringBuilder sb  =new StringBuilder();
            for (int i = 0; i < phonenum.length(); i++) {
                char c = phonenum.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }

            return sb.toString() ;
        }
        return "";
    }

    public static String formatToDouble(String dob){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(Double.parseDouble(dob));
    }


    public static void setTabLine(TabLayout tab, int tab_left, int tab_right) {
        Class<?> tabLayout = tab.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tab_left, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tab_right, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

//    public static String getPhotoImEi(Context context){
//        TelephonyManager mTm = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
//        String imei = mTm.getDeviceId();
//        String imsi = mTm.getSubscriberId();
//        String mtype = android.os.Build.MODEL; // 手机型号
//        String mtyb= android.os.Build.BRAND;//手机品牌
//        String numer = mTm.getLine1Number(); // 手机号码，有的可得，有的不可得
//        return imei ;
//    }


    /**
     * 打卡软键盘
     *
     * @param
     * @param
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param
     * @param
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    /**
     * 验证手机号码
     * @param mobile
     * @return
     */
    public static boolean checkMobileNumber(String mobile){
        boolean flag = false;
        try{
            String regexStr = "((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
            Pattern regex = Pattern.compile(regexStr);
            Matcher matcher = regex.matcher(mobile);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    public static boolean StringIsNull(String data){
        if (data!=null){
            if (!data.equals("")){
                return false;
            }
        }
        return true ;
    }

    /**
     * md5加密方法
     * @param password
     * @return
     */
    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        }catch (Exception e){
            return "";
        }
    }

    /**

     * 是否是英文

     * @param

     * @return

     */

    public static boolean isEnglish(String charaString){

        return charaString.matches("^[a-zA-Z]*");

    }


    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (Exception e) {
        }
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static List<String> testDay(int intervals ) {
        List<String> pastDaysList = new ArrayList<>();
        List<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
//            pastDaysList.add(getPastDate(i));
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }


    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static void getNoLinkData() {
        ArrayList<String> date = new ArrayList<>();
        List<String> testDay = PublicUtils.testDay(7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String s : testDay){
            String new_str = null;
            try {
                new_str = s + PublicUtils.getWeekOfDate(sdf.parse(s));
                date.add(new_str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        getTime("7:30","9:00");
        getTime("9:00","11:00");
        getTime("11:00","13:00");
        getTime("13:00","15:00");
        getTime("15:00","17:00");

        ArrayList<String> test = new ArrayList<>();
        test.add("7:30-9:00");
        test.add("9:00-11:00");
        test.add("11:00-13:00");
        test.add("13:00-15:00");
        test.add("15:00-17:00");
//        getTime().add(test);
//        time.add(test);
//        time.add(test);
//        time.add(test);
//        time.add(test);
//        time.add(test);

    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<ArrayList<String>> getTime(String begin , String end){
         ArrayList<String> com_time = new ArrayList<>();
         ArrayList<ArrayList<String>> time = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(begin);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (PublicUtils.belongCalendar(now,beginTime,endTime)){
            if (begin.equals("7:30")&&end.equals("9:00")){
                com_time.add("7:30-9:00");
                com_time.add("9:00-11:00");
                com_time.add("11:00-13:00");
                com_time.add("13:00-15:00");
                com_time.add("15:00-17:00");
            }else if (begin.equals("9:00")&&end.equals("11:00")){
                com_time.add(begin+"-"+end);
                com_time.add("11:00-13:00");
                com_time.add("13:00-15:00");
                com_time.add("15:00-17:00");
            }else if (begin.equals("11:00")&&end.equals("13:00")){
                com_time.add(begin+"-"+end);
                com_time.add("13:00-15:00");
                com_time.add("15:00-17:00");
                com_time.add("17:00-19:00");
            }else if (begin.equals("13:00")&&end.equals("15:00")){
                com_time.add(begin+"-"+end);
                com_time.add("15:00-17:00");
            }else if (begin.equals("15:00")&&end.equals("17:00")){
                com_time.add(begin+"-"+end);
            }else {
                return time;
            }
            time.add(com_time);
            return time;
        }
        return time;
    }

    public static List<List<String>> getDataNum(List<String> title_list){
        List<List<String>> listList = new ArrayList<>();
        for (int i = 0 ;i<title_list.size();i++){
            listList.add(PublicUtils.testDay(7));
        }
        return listList;
    }

    public static List<List<List<String>>> getTime(List<String> title_list){
        List<List<List<String>>> k_list = new ArrayList<>();
        for (int m = 0 ;m<getDataNum(title_list).size();m++){
            List<List<String>> vv = new ArrayList<>();
            vv.add(showtime());
            k_list.add(vv);
        }
        return k_list;
    }

    public static List<String> showtime(){
        List<String> strings = new ArrayList<>();
        strings.add("24:00");
        strings.add("23:00");
        strings.add("22:00");
        strings.add("21:00");
        strings.add("20:00");
        strings.add("19:00");
        strings.add("18:00");
        strings.add("17:00");
        strings.add("16:00");
        strings.add("15:00");
        strings.add("14:00");
        strings.add("13:00");strings.add("12:00");
        strings.add("11:00");
        strings.add("10:00");
        strings.add("9:00");
        strings.add("8:00");strings.add("8:00");strings.add("6:00");strings.add("5:00");strings.add("4:00");
        strings.add("3:00");
        strings.add("2:00");
        strings.add("1:00");
        return strings;
    }

    public static void getAuth(Activity activity){
        String[] v = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,};
        ActivityCompat.requestPermissions(activity,v,1);
    }



    /** Create a File Uri for saving a video */
    public static Uri getOutputMediaFileUri() {
        //get the mobile Pictures directory
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //get the current time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File videoFile = new File(picDir.getPath() + File.separator + "VIDEO_" + timeStamp + ".mp4");

        return Uri.fromFile(videoFile);
    }


}
