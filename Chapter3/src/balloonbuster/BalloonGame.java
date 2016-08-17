package balloonbuster;

import com.badlogic.gdx.Game;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class BalloonGame extends Game {

    @Override
    public void create() {
        BalloonLevel balloonLevel = new BalloonLevel(this);
        setScreen(balloonLevel);
    }

}
