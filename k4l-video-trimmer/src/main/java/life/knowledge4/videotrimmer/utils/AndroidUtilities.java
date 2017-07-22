package life.knowledge4.videotrimmer.utils;


import android.content.Context;


public class AndroidUtilities {
    public static float density = 1;

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public static void checkDisplaySize(Context context) {
            density = context.getResources().getDisplayMetrics().density;
    }
}
