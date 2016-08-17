package fiftytwopickup;

import base_B.BaseGame;

/**
 * Created by robertoguazon on 05/08/2016.
 */
public class FiftyTwoPickupGame extends BaseGame {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }

}
