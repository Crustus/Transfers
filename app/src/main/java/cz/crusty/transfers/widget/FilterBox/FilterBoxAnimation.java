package cz.crusty.transfers.widget.FilterBox;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import cz.crusty.transfers.BuildConfig;

/**
 * Created by Crusty. 28.08.2018
 */
public class FilterBoxAnimation {

    private static final String TAG = FilterBoxAnimation.class.getSimpleName();

    public static void collapse(ViewGroup viewGroup, int height) {
        start(viewGroup, height, true);
    }

    public static void expand(ViewGroup viewGroup, int height) {
        start(viewGroup, height, false);
    }

    private static void start(final ViewGroup viewGroup, int height, final boolean collapse) {
        int from = collapse ? height : 0;
        int to = collapse ? 0 : height;
        if(BuildConfig.DEBUG)
            Log.d(TAG, "from "+from+", to "+to);
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                layoutParams.height = val;
                viewGroup.setLayoutParams(layoutParams);
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                viewGroup.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.setVisibility(collapse ? View.GONE : View.VISIBLE);
            }
        });
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setDuration(500);
        anim.start();
    }
}
