package base_touchpad.controller;

import com.badlogic.gdx.controllers.PovDirection;

/**
 * Created by robertoguazon on 16/07/2016.
 */
public class XboxGamepad {

    //button codes
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;
    public static final int BUTTON_LEFT_SHOULDER = 4;
    public static final int BUTTON_RIGHT_SHOULDER = 5;
    public static final int BUTTON_BACK = 6;
    public static final int BUTTON_START = 7;
    public static final int BUTTON_LEFT_CLICK = 8;
    public static final int BUTTON_RIGHT_CLICK = 9;

    //directional pad codes
    public static final PovDirection DPAD_UP = PovDirection.north;
    public static final PovDirection DPAD_DOWN = PovDirection.south;
    public static final PovDirection DPAD_RIGHT = PovDirection.east;
    public static final PovDirection DPAD_LEFT = PovDirection.west;

    //joystick axis codes
    //x-axis: -1 = left, + 1 = right
    //y-axis: -1 = up, + 1 = down
    public static final int AXIS_LEFT_X = 1;
    public static final int AXIS_LEFT_Y = 0;
    public static final int AXIS_RIGHT_X = 3;
    public static final int AXIS_RIGHT_Y = 2;

    //trigger codes
    //left & right trigger buttons treated as a single axis same ID value
    //values - Left trigger: 0 to +1. Right trigger: 0 to -1.
    //Note: values are additive; they can cancel each other if both are pressed.
    public static final int AXIS_LEFT_TRIGGER = 4;
    public static final int AXIS_RIGHT_TRIGGER = 4;

}
