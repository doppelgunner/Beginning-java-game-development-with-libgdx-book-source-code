package starfishcollector;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class TurtleLauncher {

    public static void main(String[] args) {
        //change configuration settings
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.title = "Starfish Collector";

        TurtleGame turtleGame = new TurtleGame();
        LwjglApplication lwjglApplication = new LwjglApplication(turtleGame, config);
    }
}
