package com.owm.led.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

/**
 * Android的屏幕亮度好像在2.1+的时候提供了自动调节的功能，
 * 所以，如果当开启自动调节功能的时候， 我们进行调节好像是没有一点作用的，
 * 这点让我很是无语，结果只有进行判断，看是否开启了屏幕亮度的自动调节功能。
 * <p>
 * Created by owm on 2017/8/15.
 */
public class BrightnessUtils {

    /**
     * 判断是否开启了自动亮度调节
     */
    public static boolean isAutoBrightness(Activity activity) {
        boolean autoBrightness = false;
        try {
            autoBrightness = Settings.System.getInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoBrightness;
    }

    /**
     * 获取屏幕的亮度
     * @param activity activity
     * @return 当前亮度
     */
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     * @param activity activity
     * @param brightness 设置亮度值
     */
    public static void setBrightness(Activity activity, int brightness) {
        // Settings.System.putInt(activity.getContentResolver(),

        // Settings.System.SCREEN_BRIGHTNESS_MODE,

        // Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        lp.screenBrightness = (float) brightness * (1f / 255f);
        Log.d("lxy", "set  lp.screenBrightness == " + lp.screenBrightness);

        activity.getWindow().setAttributes(lp);
    }

    /**
     * 停止自动亮度调节
     * @param activity activity
     */
    public static void stopAutoBrightness(Activity activity) {
        if (activity == null) {
            return;
        }
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启亮度自动调节
     * @param activity activity
     */
    public static void startAutoBrightness(Activity activity) {
        if (activity == null) {
            return;
        }
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    // 至此，应该说操作亮度的差不多都有了，结束！
    // 哎，本来认为是应该结束了，但是悲剧得是，既然像刚才那样设置的话，只能在当前的activity中有作用，一段退出的时候，会发现毫无作用，悲剧，原来是忘记了保存了。汗！

    /**
     * 保存亮度设置状态
     * @param activity activity
     * @param brightness brightness
     */
    public static void saveBrightness(Activity activity, int brightness) {
        if (activity == null) {
            return;
        }
        Uri uri = android.provider.Settings.System
                .getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        android.provider.Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,
                brightness);
        // resolver.registerContentObserver(uri, true, myContentObserver);
        activity.getContentResolver().notifyChange(uri, null);
    }
}