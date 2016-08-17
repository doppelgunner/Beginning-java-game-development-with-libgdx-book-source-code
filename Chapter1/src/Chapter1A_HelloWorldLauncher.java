import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by robertoguazon on 26/06/2016.
 */
public class Chapter1A_HelloWorldLauncher {

    public static void main(String[] args) {
        HelloWorldImage myProgram = new HelloWorldImage();
        LwjglApplication launcher = new LwjglApplication(myProgram);
    }

}
