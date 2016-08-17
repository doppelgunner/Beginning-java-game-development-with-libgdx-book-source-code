import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by robertoguazon on 02/07/2016.
 */
public class Launcher3 {

    public static void main(String[] args) {
        CheesePlease3 cheesePlease3 = new CheesePlease3();
        LwjglApplication app = new LwjglApplication(cheesePlease3);
    }

}
