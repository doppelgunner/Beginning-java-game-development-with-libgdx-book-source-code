package com.doppelgunner.cgol.game;

import com.doppelgunner.base.BaseGame;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by robertoguazon on 11/07/2016.
 */
public class CGOLGame extends BaseGame {



    @Override
    public void create() {
        //Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.setLogLevel(Application.LOG_INFO);

        Gdx.app.log("graphics", "width = " + Gdx.graphics.getWidth() + ", height = " + Gdx.graphics.getHeight());
        CGOLLevel cgolLevel = new CGOLLevel(this);


        setScreen(cgolLevel);
    }

}
