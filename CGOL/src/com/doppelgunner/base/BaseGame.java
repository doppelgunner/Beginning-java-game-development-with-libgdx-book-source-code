package com.doppelgunner.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by robertoguazon on 10/07/2016.
 */
public abstract class BaseGame extends Game {

    //used to store resources common to multiple screens
    public Skin skin;

    public BaseGame() {
        skin = new Skin();
    }

    @Override
    public abstract void create();

    @Override
    public void dispose() {
        skin.dispose();
    }



}
