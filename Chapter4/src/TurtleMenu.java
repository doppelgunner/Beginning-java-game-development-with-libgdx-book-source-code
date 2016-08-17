import base_gamepad.BaseGame;
import base_gamepad.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by robertoguazon on 10/07/2016.
 */
public class TurtleMenu extends BaseScreen {

    public TurtleMenu(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        Texture waterTex = new Texture(Gdx.files.internal("assets/chapter4/water.jpg"));
        waterTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        game.skin.add("waterTex",waterTex);
        uiTable.background(game.skin.getDrawable("waterTex"));

        Texture titleTex = new Texture(Gdx.files.internal("assets/chapter4/starfish-collector.png"));
        titleTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image titleImage = new Image(titleTex);

        Texture libgdxTex = new Texture(Gdx.files.internal("assets/chapter4/created-libgdx.png"));
        libgdxTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image libgdxImage = new Image(libgdxTex);

        TextButton startButton = new TextButton("Start", game.skin, "uiTextButtonStyle");
        startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TurtleLevel(game));
            }
        });

        TextButton quitButton = new TextButton("Quit", game.skin, "uiTextButtonStyle");
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        float w =startButton.getWidth();
        uiTable.add(titleImage).colspan(2);
        uiTable.row();
        uiTable.add(startButton);
        uiTable.add(quitButton).width(w);
        uiTable.row();
        uiTable.add(libgdxImage).colspan(2).right().padTop(50);
    }

    @Override
    public void update(float dt) {

    }
}
