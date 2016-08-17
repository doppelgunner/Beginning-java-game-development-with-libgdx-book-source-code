package project3d;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 13/08/2016.
 */
public class Launcher1 {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        TheTest program = new TheTest();
        LwjglApplication app = new LwjglApplication(program, config);
    }
}
