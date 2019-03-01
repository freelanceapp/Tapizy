package ibt.com.tapizy.loading_indicator;

import ibt.com.tapizy.loading_indicator.sprite.Sprite;
import ibt.com.tapizy.loading_indicator.style.ChasingDots;
import ibt.com.tapizy.loading_indicator.style.Circle;
import ibt.com.tapizy.loading_indicator.style.CubeGrid;
import ibt.com.tapizy.loading_indicator.style.DoubleBounce;
import ibt.com.tapizy.loading_indicator.style.FadingCircle;
import ibt.com.tapizy.loading_indicator.style.FoldingCube;
import ibt.com.tapizy.loading_indicator.style.MultiplePulse;
import ibt.com.tapizy.loading_indicator.style.MultiplePulseRing;
import ibt.com.tapizy.loading_indicator.style.Pulse;
import ibt.com.tapizy.loading_indicator.style.PulseRing;
import ibt.com.tapizy.loading_indicator.style.RotatingCircle;
import ibt.com.tapizy.loading_indicator.style.RotatingPlane;
import ibt.com.tapizy.loading_indicator.style.ThreeBounce;
import ibt.com.tapizy.loading_indicator.style.WanderingCubes;
import ibt.com.tapizy.loading_indicator.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
