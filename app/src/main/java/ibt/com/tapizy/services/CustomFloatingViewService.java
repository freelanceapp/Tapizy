package ibt.com.tapizy.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import ibt.com.tapizy.R;
import ibt.com.tapizy.utils.floating_view.FloatingViewListener;
import ibt.com.tapizy.utils.floating_view.FloatingViewManager;

import static ibt.com.tapizy.ui.activity.HomeActivity.homeActivity;

/**
 * FloatingViewのカスタマイズを行います。
 * サンプルとしてクリック時にはメールアプリを起動します。
 */
public class CustomFloatingViewService extends Service implements FloatingViewListener {

    /**
     * Intent key (Cutout safe area)
     */
    public static final String EXTRA_CUTOUT_SAFE_AREA = "cutout_safe_area";

    /**
     * 通知ID
     */
    private static final int NOTIFICATION_ID = 908114;

    /**
     * Prefs Key(Last position X)
     */
    private static final String PREF_KEY_LAST_POSITION_X = "last_position_x";

    /**
     * Prefs Key(Last position Y)
     */
    private static final String PREF_KEY_LAST_POSITION_Y = "last_position_y";

    /**
     * FloatingViewManager
     */
    private FloatingViewManager mFloatingViewManager;

    public String strImgUrl;
    public String strBotId;

    /**
     * {@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        strImgUrl = intent.getStringExtra("image_url");
        strBotId = intent.getStringExtra("bot_id");

        if (mFloatingViewManager != null) {
            return START_STICKY;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        final LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.widget_mail, null, false);
        final CircleImageView iconView = view.findViewById(R.id.imgBot);

        iconView.setMaxWidth(40);
        iconView.setMaxHeight(40);
        Glide.with(getApplicationContext())
                .load(strImgUrl)
                .centerCrop()
                .centerInside()
                .into(iconView);

        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        mFloatingViewManager.setSafeInsetRect((Rect) intent.getParcelableExtra(EXTRA_CUTOUT_SAFE_AREA));
        // Setting Options(you can change options at any time)
        loadDynamicOptions();
        // Initial Setting Options (you can't change options after created.)
        final FloatingViewManager.Options options = loadOptions(metrics);
        mFloatingViewManager.addViewToWindow(iconView, options);
        return START_REDELIVER_INTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFinishFloatingView() {
        stopSelf();
        homeActivity.removeFav(strBotId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        if (!isFinishing) {
            // Save the last position
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putInt(PREF_KEY_LAST_POSITION_X, x);
            editor.putInt(PREF_KEY_LAST_POSITION_Y, y);
            editor.apply();
        }
    }

    /**
     * Viewを破棄します。
     */
    private void destroy() {
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
    }


    private void loadDynamicOptions() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        final String displayModeSettings = sharedPref.getString("settings_display_mode", "");
        if ("Always".equals(displayModeSettings)) {
            mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);
        } else if ("FullScreen".equals(displayModeSettings)) {
            mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_HIDE_FULLSCREEN);
        } else if ("Hide".equals(displayModeSettings)) {
            mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_HIDE_ALWAYS);
        }

    }

    /**
     * FloatingViewのオプションを読み込みます。
     *
     * @param metrics X/Y座標の設定に利用するDisplayMetrics
     * @return Options
     */
    private FloatingViewManager.Options loadOptions(DisplayMetrics metrics) {
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Shape
        final String shapeSettings = sharedPref.getString("settings_shape", "");
        if ("Circle".equals(shapeSettings)) {
            options.shape = FloatingViewManager.SHAPE_CIRCLE;
        } else if ("Rectangle".equals(shapeSettings)) {
            options.shape = FloatingViewManager.SHAPE_RECTANGLE;
        }

        // Margin
        final String marginSettings = sharedPref.getString("settings_margin", String.valueOf(options.overMargin));
        options.overMargin = Integer.parseInt(marginSettings);

        // MoveDirection
        final String moveDirectionSettings = sharedPref.getString("settings_move_direction", "");
        if ("Default".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_DEFAULT;
        } else if ("Left".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_LEFT;
        } else if ("Right".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_RIGHT;
        } else if ("Nearest".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NEAREST;
        } else if ("Fix".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NONE;
        } else if ("Thrown".equals(moveDirectionSettings)) {
            options.moveDirection = FloatingViewManager.MOVE_DIRECTION_THROWN;
        }

        options.usePhysics = sharedPref.getBoolean("settings_use_physics", true);

        // Last position
        final boolean isUseLastPosition = sharedPref.getBoolean("settings_save_last_position", false);
        if (isUseLastPosition) {
            final int defaultX = options.floatingViewX;
            final int defaultY = options.floatingViewY;
            options.floatingViewX = sharedPref.getInt(PREF_KEY_LAST_POSITION_X, defaultX);
            options.floatingViewY = sharedPref.getInt(PREF_KEY_LAST_POSITION_Y, defaultY);
        } else {
            // Init X/Y
            final String initXSettings = sharedPref.getString("settings_init_x", "");
            final String initYSettings = sharedPref.getString("settings_init_y", "");
            if (!TextUtils.isEmpty(initXSettings) && !TextUtils.isEmpty(initYSettings)) {
                final int offset = (int) (48 + 8 * metrics.density);
                options.floatingViewX = (int) (metrics.widthPixels * Float.parseFloat(initXSettings) - offset);
                options.floatingViewY = (int) (metrics.heightPixels * Float.parseFloat(initYSettings) - offset);
            }
        }

        // Initial Animation
        final boolean animationSettings = sharedPref.getBoolean("settings_animation", options.animateInitialMove);
        options.animateInitialMove = animationSettings;

        return options;
    }
}
