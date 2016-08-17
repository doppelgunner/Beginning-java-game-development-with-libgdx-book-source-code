import base_gamepad.BaseGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class TurtleGame extends BaseGame {

    @Override
    public void create() {
        //init resources common to multiple screens and store to skin database
        BitmapFont uiFont = new BitmapFont(Gdx.files.internal("assets/chapter4/cooper.fnt"));
        uiFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skin.add("uiFont",uiFont);

        Label.LabelStyle uiLabelStyle = new Label.LabelStyle(uiFont, Color.BLUE);
        skin.add("uiLabelStyle",uiLabelStyle);

        TextButton.TextButtonStyle uiTextButtonStyle = new TextButton.TextButtonStyle();
        uiTextButtonStyle.font = uiFont;
        uiTextButtonStyle.fontColor = Color.NAVY;

        Texture upTex = new Texture(Gdx.files.internal("assets/chapter4/ninepatch-1.png"));
        skin.add("buttonUp",new NinePatch(upTex, 26,26,16,20));
        uiTextButtonStyle.up = skin.getDrawable("buttonUp");

        Texture overTex = new Texture(Gdx.files.internal("assets/chapter4/ninepatch-2.png"));
        skin.add("buttonOver", new NinePatch(overTex, 26,26,16,20));
        uiTextButtonStyle.over = skin.getDrawable("buttonOver");
        uiTextButtonStyle.overFontColor = Color.BLUE;

        Texture downTex = new Texture(Gdx.files.internal("assets/chapter4/ninepatch-3.png"));
        skin.add("buttonDown", new NinePatch(downTex, 26,26,16,20));
        uiTextButtonStyle.down = skin.getDrawable("buttonDown");
        uiTextButtonStyle.downFontColor = Color.BLUE;

        skin.add("uiTextButtonStyle", uiTextButtonStyle);

        Slider.SliderStyle uiSliderStyle = new Slider.SliderStyle();
        skin.add("sliderBack", new Texture(Gdx.files.internal("assets/chapter4/slider-after.png")));
        skin.add("sliderKnob", new Texture(Gdx.files.internal("assets/chapter4/slider-knob.png")));
        skin.add("sliderAfter", new Texture(Gdx.files.internal("assets/chapter4/slider-after.png")));
        skin.add("sliderBefore", new Texture(Gdx.files.internal("assets/chapter4/slider-before.png")));

        uiSliderStyle.background = skin.getDrawable("sliderBack");
        uiSliderStyle.knob = skin.getDrawable("sliderKnob");
        uiSliderStyle.knobAfter = skin.getDrawable("sliderAfter");
        uiSliderStyle.knobBefore = skin.getDrawable("sliderBefore");

        skin.add("uiSliderStyle",uiSliderStyle);

        //init and start the main game
        TurtleMenu turtleMenu = new TurtleMenu(this);
        setScreen(turtleMenu);
    }
}
