package touchpad;

import base_touchpad.BaseGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class CheeseGame extends BaseGame
{
    public void create() 
    {  
        // initialize resources common to multiple screens
        
        // Bitmap font
        
        BitmapFont uiFont = new BitmapFont(Gdx.files.internal("assets/chapter5/touchpad/cooper.fnt"));
        uiFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        skin.add("uiFont", uiFont);

        // Label style
        
        LabelStyle uiLabelStyle = new LabelStyle(uiFont, Color.ORANGE);

        skin.add("uiLabelStyle", uiLabelStyle);

        // TextButton style

        TextButtonStyle uiTextButtonStyle = new TextButtonStyle();

        uiTextButtonStyle.font      = uiFont;
        uiTextButtonStyle.fontColor = Color.ORANGE;
        
        Texture upTex = new Texture(Gdx.files.internal("assets/chapter5/touchpad/ninepatch-1.png"));
        skin.add("buttonUp", new NinePatch(upTex, 26,26,16,20));
        uiTextButtonStyle.up = skin.getDrawable("buttonUp");
        
        Texture overTex = new Texture(Gdx.files.internal("assets/chapter5/touchpad/ninepatch-2.png"));
        skin.add("buttonOver", new NinePatch(overTex, 26,26,16,20) );
        uiTextButtonStyle.over = skin.getDrawable("buttonOver");
        uiTextButtonStyle.overFontColor = Color.YELLOW;
        
        Texture downTex = new Texture(Gdx.files.internal("assets/chapter5/touchpad/ninepatch-3.png"));
        skin.add("buttonDown", new NinePatch(downTex, 26,26,16,20) );        
        uiTextButtonStyle.down = skin.getDrawable("buttonDown");
        uiTextButtonStyle.downFontColor = Color.YELLOW;		

        skin.add("uiTextButtonStyle", uiTextButtonStyle);
        
        MenuScreen ms = new MenuScreen(this);
        setScreen( ms );
    }
}
