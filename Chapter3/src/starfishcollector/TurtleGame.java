package starfishcollector;

import com.badlogic.gdx.Game;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class TurtleGame extends Game {

    @Override
    public void create() {
        TurtleLevel turtleLevel = new TurtleLevel(this);
        setScreen(turtleLevel);
    }
}
