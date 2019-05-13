package infobite.ibt.tapizy.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentUtils {

    private FragmentManager fragmentManager;

    public FragmentUtils(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment fragment, String tag, int frameId) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(frameId, fragment, tag).commit();
    }

    public void replaceBackFragment(Fragment fragment, String tag, int frameId) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(frameId, fragment, tag).commit();
    }
}
