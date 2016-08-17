package balloonbuster;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class BalloonLauncher {

    public static void main(String[]args) {
        BalloonGame balloonGame = new BalloonGame();
        LwjglApplication lwjglApplication = new LwjglApplication(balloonGame);
    }

}
