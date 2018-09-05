package edu.neu.ccs.wellness.adcaregiverapp.common.utils;

import android.content.Context;
import android.content.res.Resources;

public class DrawableUntils {

    public static int getDrawableIdByName(Context context, String resourceName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(resourceName, "drawable",
                context.getPackageName());
        return resourceId;
    }

    public static int getDrawableIdByNameAndStage(Context context, String resourceName, int stage) {
        int index = resourceName.lastIndexOf("_");
        resourceName = resourceName.substring(0, index + 1) + String.valueOf(stage);
        return getDrawableIdByName(context, resourceName);
    }

    public static int getDrawableWithoutBaseIdByNameAndStage(Context context, String resourceName, int stage){
        int index = resourceName.lastIndexOf("_");
        resourceName = resourceName.substring(0, index - 1) + String.valueOf(stage);
        return getDrawableIdByName(context, resourceName);
    }
}
