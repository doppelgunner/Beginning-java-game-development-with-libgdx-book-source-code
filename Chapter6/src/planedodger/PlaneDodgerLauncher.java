package planedodger;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by robertoguazon on 21/07/2016.
 */
public class PlaneDodgerLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LwjglApplication lwjglApplication = new LwjglApplication(new PlaneDodgerGame(), config);
    }
}
