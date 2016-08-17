package rectangledestroyer;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class RectangleDestroyerLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.resizable = false;
        LwjglApplication app = new LwjglApplication(new RectangleDestroyerGame(),config);
    }
}
