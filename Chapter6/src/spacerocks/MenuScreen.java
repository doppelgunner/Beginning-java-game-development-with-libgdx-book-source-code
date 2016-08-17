package spacerocks;

import base_A.BaseActor;
import base_A.BaseGame;
import base_A.BaseScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by robertoguazon on 17/07/2016.
 */
public class MenuScreen extends BaseScreen {

    public MenuScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        BaseActor background = new BaseActor();
        background.setPosition(0,0);
        uiTable.background(game.skin.getDrawable("backgroundTexture"));

        Label title = new Label("SpaceRocks", game.skin, "labelStyle");

        TextButton startButton = new TextButton("PLAY", game.skin, "textButtonStyle");
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
        });

        uiTable.add(title).padTop(100);
        uiTable.row();
        uiTable.add().expandY();
        uiTable.row();
        uiTable.add(startButton).padBottom(100);
    }

    @Override
    public void update(float dt) {

    }
}
