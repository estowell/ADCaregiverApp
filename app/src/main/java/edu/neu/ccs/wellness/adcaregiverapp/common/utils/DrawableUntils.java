package edu.neu.ccs.wellness.adcaregiverapp.common.utils;

import android.content.Context;
import android.content.res.Resources;

public class DrawableUntils {

    public static int getDrawableIdByName(Context context, String resourceName){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(resourceName, "drawable",
                context.getPackageName());
        return resourceId;
    }
}
