package starscape;

import base_A.BaseGame;

/**
 * Created by robertoguazon on 07/08/2016.
 */
public class StarscapeGame extends BaseGame {


    @Override
    public void create() {

        setScreen(new GameScreen(this));
    }
}
