package spacerocks;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 17/07/2016.
 */
public class SpaceRocksLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        LwjglApplication lwjglApplication = new LwjglApplication(new SpaceRocksGame(), config);
    }
}
