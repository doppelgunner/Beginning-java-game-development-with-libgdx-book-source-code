package fiftytwopickup;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 05/08/2016.
 */
public class FiftyTwoPickupLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LwjglApplication app = new LwjglApplication(new FiftyTwoPickupGame(), config);
    }
}
