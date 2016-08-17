import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by robertoguazon on 27/06/2016.
 */
public class Launcher1 {

    public static void main(String[] args) {
        CheesePlease1 cheesePlease1 = new CheesePlease1();
        LwjglApplication launcher = new LwjglApplication(cheesePlease1);
    }

}
