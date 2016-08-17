package gamepad;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
public class CheeseLauncher
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        
        // change configuration settings
        config.width = 800;
        config.height = 600;
        //config.resizable = false;
        config.title = "Cheese, Please!";
        
        CheeseGame myProgram = new CheeseGame();
        LwjglApplication launcher = new LwjglApplication( myProgram, config );
    }
}