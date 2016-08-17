package com.doppelgunner.cgol;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.doppelgunner.cgol.game.CGOLGame;

/**
 * Created by robertoguazon on 11/07/2016.
 */
public class CGOLLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        config.fullscreen = false;
        config.resizable = false;
        LwjglApplication lwjglApplication = new LwjglApplication(new CGOLGame(),config);
    }
}
