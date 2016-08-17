package jumpingjack;

import base_A.BaseGame;

/**
 * Created by robertoguazon on 09/08/2016.
 */
public class JumpingJackGame extends BaseGame {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }

}
