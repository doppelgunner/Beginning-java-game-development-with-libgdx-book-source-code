package planedodger;

import base_A.BaseGame;
import base_A.PhysicsActor;
import base_A.util.GameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by robertoguazon on 21/07/2016.
 */
public class PlaneDodgerGame extends BaseGame {

    public static PhysicsActor[] background;
    public static PhysicsActor[] ground;

    @Override
    public void create() {

        BitmapFont labelFont = new BitmapFont(Gdx.files.internal("assets/chapter6/planedodger/cooper.fnt"));
        labelFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skin.add("labelFont", labelFont);

        Label.LabelStyle labelStyle = new Label.LabelStyle(labelFont, Color.CYAN);
        skin.add("labelStyle", labelStyle);

        //public game objects
        //background
        background = new PhysicsActor[2];
        //sky
        background = new PhysicsActor[2];

        //ground
        PhysicsActor bg0 = new PhysicsActor();
        bg0.storeAnimation("default", new Texture(Gdx.files.internal("assets/chapter6/planedodger/sky.png")));
        bg0.setPosition(0,0);
        bg0.setVelocityXY(-50,0);
        background[0] = bg0;

        //clone of ground for infinite scrolling
        PhysicsActor bg1 = bg0.clone();
        bg1.setX(bg0.getWidth());
        background[1] = bg1;

        ground = new PhysicsActor[2];

        //ground
        PhysicsActor gr0 = new PhysicsActor();
        gr0.storeAnimation("default", new Texture(Gdx.files.internal("assets/chapter6/planedodger/ground.png")));
        gr0.setPosition(0,0);
        gr0.setVelocityXY(-200,0);
        gr0.setRectangleBoundary();
        ground[0] = gr0;

        //copy of ground for infinite scrolling
        PhysicsActor gr1 = gr0.clone();
        gr1.setX(gr0.getWidth());
        ground[1] = gr1;

        Animation anim = GameUtils.parseImageFiles("assets/chapter6/planedodger/planeGreen", ".png",
                3, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        skin.add("playerAnim", anim);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = labelFont;
        textButtonStyle.fontColor = Color.CYAN;

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


        setScreen(new MenuScreen(this));
    }
}
