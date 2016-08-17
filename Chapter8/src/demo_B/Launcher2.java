package demo_B;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 15/08/2016.
 */
public class Launcher2 {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        LwjglApplication app = new LwjglApplication(new DemoGame(), config);
    }
}
