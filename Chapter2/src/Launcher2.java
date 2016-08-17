import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by robertoguazon on 27/06/2016.
 */
public class Launcher2 {

    public static void main(String[] args) {
        CheesePlease2 cheesePlease2 = new CheesePlease2();
        LwjglApplication launcher = new LwjglApplication(cheesePlease2);
    }
}
