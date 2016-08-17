package demo_B;

import base.BaseGame;

/**
 * Created by robertoguazon on 15/08/2016.
 */
public class DemoGame extends BaseGame {
    @Override
    public void create() {
        setScreen(new DemoScreen(this));
    }
}
