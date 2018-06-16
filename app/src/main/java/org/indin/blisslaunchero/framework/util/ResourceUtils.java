package org.indin.blisslaunchero.framework.util;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import org.indin.blisslaunchero.framework.Utilities;

import java.lang.reflect.InvocationTargetException;

public class ResourceUtils {

    /**
     * Sets a fake configuration to the passed Resources to allow access to resources
     * accessible to a sdk level. Used to backport adaptive icon support to different
     * devices.
     *
     * @param resources the resources to set the configuration to
     * @param sdk       the sdk level to become accessible
     * @throws NoSuchMethodException     if something is wrong
     * @throws IllegalAccessException    if something is very wrong
     * @throws InvocationTargetException if something is really very extremely wrong
     */
    public static void setFakeConfig(Resources resources, int sdk) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        int width, height;
        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (metrics.widthPixels >= metrics.heightPixels) {
            width = metrics.widthPixels;
            height = metrics.heightPixels;
        } else {
            width = metrics.heightPixels;
            height = metrics.widthPixels;
        }

        Configuration configuration = resources.getConfiguration();

        if (Utilities.ATLEAST_OREO) {
            AssetManager.class.getDeclaredMethod("setConfiguration", int.class, int.class, String.class, int.class, int.class,
                    int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class,
                    int.class, int.class, int.class, int.class)
                    .invoke(resources.getAssets(), configuration.mcc, configuration.mnc,
                            Utilities.ATLEAST_LOLLIPOP_MR1 ? configuration.locale.toLanguageTag() : null,
                            configuration.orientation, configuration.touchscreen, configuration.densityDpi,
                            configuration.keyboard, configuration.keyboardHidden, configuration.navigation,
                            width, height, configuration.smallestScreenWidthDp,
                            configuration.screenWidthDp, configuration.screenHeightDp, configuration.screenLayout,
                            configuration.uiMode, configuration.colorMode, sdk);
        } else {
            AssetManager.class.getDeclaredMethod("setConfiguration", int.class, int.class, String.class, int.class, int.class,
                    int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class,
                    int.class, int.class, int.class)
                    .invoke(resources.getAssets(), configuration.mcc, configuration.mnc,
                            Utilities.ATLEAST_LOLLIPOP_MR1 ? configuration.locale.toLanguageTag() : null,
                            configuration.orientation, configuration.touchscreen, configuration.densityDpi,
                            configuration.keyboard, configuration.keyboardHidden, configuration.navigation,
                            width, height, configuration.smallestScreenWidthDp,
                            configuration.screenWidthDp, configuration.screenHeightDp, configuration.screenLayout,
                            configuration.uiMode, sdk);
        }
    }

}

