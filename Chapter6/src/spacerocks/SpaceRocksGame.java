package spacerocks;

import base_A.BaseGame;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by robertoguazon on 17/07/2016.
 */
public class SpaceRocksGame extends BaseGame {

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Texture backgroundTexture = new Texture("assets/chapter6/spacerocks/space.png");
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skin.add("backgroundTexture", backgroundTexture);

        BitmapFont font = new BitmapFont(Gdx.files.internal("assets/chapter6/spacerocks/cooper.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skin.add("font", font);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.GREEN);
        skin.add("labelStyle", labelStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.GREEN;

        Texture upTexture = new Texture("assets/chapter6/spacerocks/ninepatch-1.png");
        skin.add("upTexture", new NinePatch(upTexture,26,26,16,20));
        textButtonStyle.up = skin.getDrawable("upTexture");

        Texture overTexture = new Texture("assets/chapter6/spacerocks/ninepatch-2.png");
        skin.add("overTexture", new NinePatch(overTexture,26,26,16,20));
        textButtonStyle.over = skin.getDrawable("overTexture");

        Texture downTexture = new Texture("assets/chapter6/spacerocks/ninepatch-3.png");
        skin.add("downTexture", new NinePatch(downTexture,26,26,16,20));
        textButtonStyle.down = skin.getDrawable("downTexture");

        skin.add("textButtonStyle", textButtonStyle);


        MenuScreen menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }
}
