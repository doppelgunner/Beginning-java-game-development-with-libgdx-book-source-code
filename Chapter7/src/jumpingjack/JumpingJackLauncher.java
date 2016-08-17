package jumpingjack;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 09/08/2016.
 */
public class JumpingJackLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LwjglApplication app = new LwjglApplication(new JumpingJackGame(), config);
    }
}
