package balloonbuster;

import base_gamepad.BaseActor;
import base_gamepad.BaseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by robertoguazon on 09/07/2016.
 */
public class BalloonLevel extends BaseScreen {

    private BaseActor background;

    private float spawnTimer;
    private float spawnInterval;

    private int popped;
    private int escaped;
    private int clickCount;

    private Label poppedLabel;
    private Label escapedLabel;
    private Label hitRatioLabel;

    //game world dimensions
    final int mapWidth = 640;
    final int mapHeight = 480;

    public BalloonLevel(Game g) {
        super(g);
    }

    @Override
    public void create() {
        background = new BaseActor();
        background.setTexture(new Texture(Gdx.files.internal("assets/chapter3/balloon_buster/sky.jpg")));
        background.setPosition(0,0);
        mainStage.addActor(background);

        spawnTimer = 0;
        spawnInterval = 0.5f;

        //set up user interface
        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle(font, Color.NAVY);

        popped = 0;
        poppedLabel = new Label("Popped: 0", style);
        poppedLabel.setFontScale(2);
        poppedLabel.setPosition(20,440);
        uiStage.addActor(poppedLabel);

        escaped = 0;
        escapedLabel = new Label("Escaped: 0", style);
        escapedLabel.setFontScale(2);
        escapedLabel.setPosition(220,440);
        uiStage.addActor(escapedLabel);

        clickCount = 0;
        hitRatioLabel = new Label("Hit Ratio: ---", style);
        hitRatioLabel.setFontScale(2);
        hitRatioLabel.setPosition(420,440);
        uiStage.addActor(hitRatioLabel);
    }

    @Override
    public void update(float dt) {
        spawnTimer += dt;
        //check time for next balloon spawn
        if (spawnTimer >= spawnInterval) {
            spawnTimer -= spawnInterval;
            Balloon b = new Balloon();
            b.addListener(
                    new InputListener() {

                        @Override
                        public boolean touchDown(InputEvent ev, float x, float y, int pointer, int button) {
                            popped++;
                            b.remove();
                            return true;
                        }
                    }
            );
            mainStage.addActor(b);
        }

        //remove balloons that are off-screen
        for (Actor a : mainStage.getActors()) {
            if (a.getX() > mapWidth || a.getY() > mapHeight) {
                escaped++;
                a.remove();
            }
        }

        //update user interface
        poppedLabel.setText("Popped: " + popped);
        escapedLabel.setText("Escaped: " + escaped);
        if (clickCount > 0) {
            int percent = (int) (100.0 * popped / clickCount);
            hitRatioLabel.setText("Hit Ratio: " + percent + "%");
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        clickCount++;
        return false;
    }

}
