package treasurequest;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 07/08/2016.
 */
public class TreasureQuestLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LwjglApplication app = new LwjglApplication(new TreasureQuestGame(), config);
    }
}
