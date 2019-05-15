package infobite.ibt.tapizy.utils.slide_layout;

/*
 * Created by rom on 15.12.16.
 */

public interface ISlideChangeListener {

    void onSlideStart(SlideLayout slider);

    void onSlideChanged(SlideLayout slider, float percentage);

    void onSlideFinished(SlideLayout slider, boolean done);

    void onSetSwipeLitner(SlideLayout slider, boolean done, String swipe);

}
