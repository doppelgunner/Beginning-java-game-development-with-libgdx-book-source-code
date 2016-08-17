package base_B;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class BaseGame extends Game
{
    // used to store resources common to multiple screens
    public Skin skin;
    
    public BaseGame()
    {
        skin = new Skin();
    }
    
    public abstract void create();

    public void dispose()
    {
        skin.dispose();
    }
}