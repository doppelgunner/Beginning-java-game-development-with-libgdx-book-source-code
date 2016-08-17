package gamepad;

import base_gamepad.BaseGame;
import base_gamepad.BaseScreen;
import base_gamepad.controller.XboxGamepad;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.controllers.*;

public class MenuScreen extends BaseScreen
{
    public MenuScreen(BaseGame g)
    {
        super(g);
    }

    public void create() 
    {      
        Texture bgTex = new Texture(Gdx.files.internal("assets/chapter5/cheeseplease/tiles-1000-1000.jpg"), true);
        bgTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        game.skin.add( "bgTex", bgTex );
        uiTable.background( game.skin.getDrawable("bgTex") );

        Texture titleTex = new Texture(Gdx.files.internal("assets/chapter5/cheeseplease/cheese-please.png"), true);
        titleTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        Image titleImage = new Image( titleTex );

        Texture libgdxTex = new Texture(Gdx.files.internal("assets/chapter5/cheeseplease/created-libgdx.png"), true);
        libgdxTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        Image libgdxImage = new Image( libgdxTex );

        TextButton startButton = new TextButton("Start", game.skin, "uiTextButtonStyle");
        startButton.addListener(
            new InputListener()
            {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
                {  return true;  }  // continue processing?

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
                {
                    game.setScreen( new GameScreen(game) );
                }
            });
            
        uiTable.add(titleImage);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.row();
        uiTable.add(libgdxImage).expandX().right();
    }

    public void update(float dt) 
    {   

    }

    //ControllerListener
    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {

        if (buttonCode == XboxGamepad.BUTTON_START) {
            game.setScreen(new GameScreen(game));
        }

        return false;
    }
}