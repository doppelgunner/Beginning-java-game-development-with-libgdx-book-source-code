package cheeseplease;

import base_gamepad.BaseActor;
import base_gamepad.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by robertoguazon on 06/07/2016.
 */
public class CheeseMenu extends BaseScreen {

    public CheeseMenu(Game game) {
        super(game);
    }

    @Override
    public void create() {
        BaseActor background = new BaseActor();
        background.setTexture(new Texture(Gdx.files.internal("assets/chapter3/cheese_please/tiles-menu.jpg")));
        uiStage.addActor(background);

        BaseActor tileText = new BaseActor();
        tileText.setTexture(new Texture(Gdx.files.internal("assets/chapter3/cheese_please/cheese-please.png")));
        tileText.setPosition(20, 100);
        uiStage.addActor(tileText);

        BitmapFont font = new BitmapFont();
        String text = " Press S to start, M for main menu ";
        Label.LabelStyle style = new Label.LabelStyle(font, Color.YELLOW);
        Label instructions = new Label(text, style);
        instructions.setFontScale(2);
        instructions.setPosition(100,50);
        //repeating color pulse effect
        instructions.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(1,1,0,1), 0.5f),
                                Actions.delay(0.5f),
                                Actions.color(new Color(0.5f,0.5f,0,1), 0.5f)
                        )
                ));

        uiStage.addActor(instructions);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.S) {
            game.setScreen(new CheeseLevel(game));
        }

        return false;
    }

}