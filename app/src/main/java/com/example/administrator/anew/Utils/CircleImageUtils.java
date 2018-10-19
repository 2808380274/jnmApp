package com.example.administrator.anew.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;




public class CircleImageUtils {

    /**
     * 【1.1】用【Base64】把【压缩(圆形头像转化的Bitmap)后得到的(字节数组输出流)】转化为【String字符串】
     *
     * @param imageView 需要转化为String字符串的圆形头像
     * @return 返回String字符串
     */
    public static String getCircleImageString(ImageView imageView) {
        //第一步：保存为Drawable对象，并转化为bitmap位图
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第二步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteStream);
        //第三步：利用base64将我们的字节数组输出流转换成String
        byte[] bytes = byteStream.toByteArray();
        String imageString = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        return imageString;
    }

    /**
     * 【1.2】用Base64把【Bitmap压缩后的字节数组输出流】转化为【String字符串】,
     * 并保存到SharedPreferences中去
     *
     * @param context   上下文
     * @param imageView 需要保存到SharedPreferences中去的圆形头像
     */
    public static void putCircleImageToShare(Context context, ImageView imageView) {
        String imageString = getCircleImageString(imageView);
        SPUtils.putString(context, "image_title", imageString);
    }


    /**
     * 【2.1】拿到Base64加密过【String字符串】转化为【字节数组输出流】，最后生成【Bitmap】位图
     *
     * @param base64Code Base64加密过String字符串
     * @return 圆形头像的bitmap位图
     */
    public static Bitmap getCircleImageBitmap(String base64Code) {
        if (base64Code != null) {
            //第一步：利用base64将我们的String转换成字节数组输出流
            byte[] bytes = Base64.decode(base64Code, Base64.DEFAULT);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            //第二步：生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }

    /**
     * 【2.2】从SharedPreferences拿到Base64加密过【String字符串】并转化为【字节数组输出流】，
     * 最后生成【Bitmap】位图，来显示图片
     *
     * @param context 上下文
     * @return 返回圆形头像的Bitmap位图
     */
    public static Bitmap getCircleImageFromShare(Context context) {
        String imageString = SPUtils.getString(context, "image_title", null);
        Bitmap bitmap = getCircleImageBitmap(imageString);
        return bitmap;
    }


}
