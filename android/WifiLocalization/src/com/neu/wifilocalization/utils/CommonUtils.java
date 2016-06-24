package com.neu.wifilocalization.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Vibrator;
import android.text.format.DateUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CommonUtils {

    public static String imagePath = "/IPS/images";
    public static String audioDir = Environment.getExternalStorageDirectory() + "/IPS/audios";
    public static String audioFile = audioDir + "/temp.amr";

    public static String formatUnixTime(long lDate) {
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(lDate * 1000));
    }

    public static boolean isValidateUserName(String username) {
        Matcher matcher = Pattern.compile("^[0-9a-zA-Z_]+$").matcher(username);
        return matcher.matches();
    }

    public static String getLastUpdatedLabel(Context context) {
        String label = DateUtils.formatDateTime(context.getApplicationContext(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        return label;
    }

    public static void initPath(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void initAudioPath() {
        File dir = new File(CommonUtils.audioDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(CommonUtils.audioFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }

    public static String formatImageFilePath(String fileName) {
        initPath(Environment.getExternalStorageDirectory() + imagePath);
        return Environment.getExternalStorageDirectory() + imagePath + "/" + fileName;
    }

    public static String getPicPath() {
        File file = new File(Environment.getExternalStorageDirectory() + imagePath + "/" + getCurrentTimeInMillionSec()
                + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
        return file.getAbsolutePath();
    }

    public static String getStringOfCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getCurrentTimeInMillionSec() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static void showSoftInput(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 图片上画字
     * */
    public static Bitmap drawTextAtBitmap(Bitmap bitmap, String text) {

        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        // 创建一个和原图同样大小的位图
        Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newbit);
        Paint paint = new Paint();

        // 在原始位置0，0插入原图
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setColor(Color.parseColor("#dedbde"));
        paint.setTextSize(20);

        // 在原图指定位置写上字
        canvas.drawText(text, 53, 30, paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储
        canvas.restore();
        return newbit;
    }

    /**
     * <p>
     * 将文件转成base64 字符串
     * </p>
     * 
     * @param path
     *            文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * *
     * <p>
     * 将base64字符解码保存文件
     * </p>
     * * @param base64Code * @param targetPath * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    // 震动
    public static void Vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    // 震动
    public static void Vibrate(final Activity activity, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }

    public static long getCurrentTimeInSecond() {
        Date currentTime = new Date();
        long time = currentTime.getTime();
        return time;
    }

    public static String getTimeDifference(long past) {
        long l = getCurrentTimeInSecond() - past * 1000;
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
        if (day != 0) {
            if (day > 31) {
                return "1个月前的位置";
            } else {
                return day + "天前的位置";
            }
        } else if (hour != 0) {
            return hour + "小时前的位置";
        } else if (min != 0) {
            return min + "分钟前的位置";
        } else if (s < 30) {
            return "现在的位置";
        } else {
            return "刚刚的位置";
        }

    }

}
