package rectangledestroyer;

import base_B.BaseGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class RectangleDestroyerGame extends BaseGame {

    @Override
    public void create() {

        BitmapFont font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;

        skin.add("labelStyle", labelStyle);


        setScreen(new MenuScreen(this));
    }
}
