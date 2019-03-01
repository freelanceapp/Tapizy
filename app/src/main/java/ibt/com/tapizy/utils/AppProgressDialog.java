package ibt.com.tapizy.utils;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import ibt.com.tapizy.R;
import ibt.com.tapizy.loading_indicator.sprite.Sprite;
import ibt.com.tapizy.loading_indicator.style.DoubleBounce;

public class AppProgressDialog {


    public static void snack(View container, String msg, String title, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar
                .make(container, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(title, listener);
        snackbar.show();
    }

    public static void show(Dialog mProgressDialog) {
        try {
            if (mProgressDialog.isShowing())
                return;
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setContentView(R.layout.layout_progress_bar);

            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            ProgressBar progressBar = mProgressDialog.findViewById(R.id.progresssBar);
            Sprite doubleBounce = new DoubleBounce();
            progressBar.setIndeterminateDrawable(doubleBounce);
            mProgressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hide(Dialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            //stopAnim(mProgressDialog);
            mProgressDialog.dismiss();
        }
    }

  /*  private static void startAnim(Dialog mProgressDialog) {
        ((AVLoadingIndicatorView) mProgressDialog.findViewById(R.id.avi)).smoothToShow();//show();
        // or avi.smoothToShow();
    }

    private static void stopAnim(Dialog mProgressDialog) {
        ((AVLoadingIndicatorView) mProgressDialog.findViewById(R.id.avi)).smoothToHide();
        // or avi.smoothToHide();
    }*/


}