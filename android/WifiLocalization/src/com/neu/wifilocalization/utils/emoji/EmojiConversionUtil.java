package com.neu.wifilocalization.utils.emoji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.neu.wifilocalization.R;
import com.neu.wifilocalization.model.EmojiEntity;

/**
 * 
 ****************************************** 
 * @author 廖乃波
 * @文件名称 : FaceConversionUtil.java
 * @创建时间 : 2013-1-27 下午02:34:09
 * @文件描述 : 表情轉換工具
 ****************************************** 
 */
public class EmojiConversionUtil {

    /** 每一页表情的个数 */
    private int pageSize = 20;

    private static EmojiConversionUtil mFaceConversionUtil;

    /** 保存于内存中的表情HashMap */
    private HashMap<String, String> emojiMap = new HashMap<String, String>();

    /** 保存于内存中的表情集合 */
    private List<EmojiEntity> emojis = new ArrayList<EmojiEntity>();

    /** 表情分页的结果集合 */
    public List<List<EmojiEntity>> emojiLists = new ArrayList<List<EmojiEntity>>();

    private EmojiConversionUtil() {

    }

    public static EmojiConversionUtil getInstace() {
        if (mFaceConversionUtil == null) {
            mFaceConversionUtil = new EmojiConversionUtil();
        }
        return mFaceConversionUtil;
    }

    /**
     * 得到一个SpanableString对象，通过传入的字符串,并进行正则判断
     * 
     * @param context
     * @param str
     * @return
     */
    public SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString(str);
        // 正则表达式比配字符串里是否含有表情，如： 我好[开心]啊
        String zhengze = "\\[[^\\]]+\\]";
        // 通过传入的正则表达式来生成一个pattern
        Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression(context, spannableString, sinaPatten, 0);
        } catch (Exception e) {
            Log.e("dealExpression", e.getMessage());
        }
        return spannableString;
    }

    /**
     * 添加表情
     * 
     * @param context
     * @param imgId
     * @param spannableString
     * @return
     */
    public SpannableString addFace(Context context, int imgId, String spannableString) {
        if (TextUtils.isEmpty(spannableString)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgId);
        bitmap = Bitmap.createScaledBitmap(bitmap, 45, 45, true);
        ImageSpan imageSpan = new ImageSpan(context, bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
     * 
     * @param context
     * @param spannableString
     * @param patten
     * @param start
     * @throws Exception
     */
    private void dealExpression(Context context, SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            String value = emojiMap.get(key);
            if (TextUtils.isEmpty(value)) {
                continue;
            }
            int resId = context.getResources().getIdentifier(value, "drawable", context.getPackageName());
            // 通过上面匹配得到的字符串来生成图片资源id
            // Field field=R.drawable.class.getDeclaredField(value);
            // int resId=Integer.parseInt(field.get(null).toString());
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                bitmap = Bitmap.createScaledBitmap(bitmap, 85, 85, true);

                Drawable d = context.getResources().getDrawable(resId);
                d.setBounds(0, 0, 45, 45);// 设置表情图片的显示大小

                // 通过图片资源id来得到bitmap，用一个ImageSpan来包装
                ImageSpan imageSpan = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                // 计算该图片名字的长度，也就是要替换的字符串的长度
                int end = matcher.start() + key.length();
                // 将该图片替换字符串中规定的位置中
                spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (end < spannableString.length()) {
                    // 如果整个字符串还未验证完，则继续。。
                    dealExpression(context, spannableString, patten, end);
                }
                break;
            }
        }
    }

    public void getFileText(Context context) {
        ParseData(ReadEmojiFileUtils.getEmojiFile(context), context);
    }

    /**
     * 解析字符
     * 
     * @param data
     */
    private void ParseData(List<String> data, Context context) {
        if (data == null) {
            return;
        }
        EmojiEntity emojEentry;
        try {
            for (String str : data) {
                String[] text = str.split(",");
                String fileName = text[0].substring(0, text[0].lastIndexOf("."));
                emojiMap.put(text[1], fileName);
                int resID = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());

                if (resID != 0) {
                    emojEentry = new EmojiEntity();
                    emojEentry.setId(resID);
                    emojEentry.setCharacter(text[1]);
                    emojEentry.setFaceName(fileName);
                    emojis.add(emojEentry);
                }
            }
            int pageCount = (int) Math.ceil(emojis.size() / 20 + 0.1);

            for (int i = 0; i < pageCount; i++) {
                emojiLists.add(getData(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取分页数据
     * 
     * @param page
     * @return
     */
    private List<EmojiEntity> getData(int page) {
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;

        if (endIndex > emojis.size()) {
            endIndex = emojis.size();
        }
        // 不这么写，会在viewpager加载中报集合操作异常，我也不知道为什么
        List<EmojiEntity> list = new ArrayList<EmojiEntity>();
        list.addAll(emojis.subList(startIndex, endIndex));
        if (list.size() < pageSize) {
            for (int i = list.size(); i < pageSize; i++) {
                EmojiEntity object = new EmojiEntity();
                list.add(object);
            }
        }
        if (list.size() == pageSize) {
            EmojiEntity object = new EmojiEntity();
            object.setId(R.drawable.face_del_icon);
            list.add(object);
        }
        return list;
    }
}