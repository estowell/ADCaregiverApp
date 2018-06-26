package edu.neu.ccs.wellness.adcaregiverapp.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by amritanshtripathi on 6/11/18.
 */

public class NumberUtils {

    public static int getRandomNumber(int start, int end) {
        Random r = new Random();
        return r.nextInt(end - start) + start;
    }

    public static int getHeightPx(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Point size = new Point();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getSize(size);
        return size.y;
    }
}
