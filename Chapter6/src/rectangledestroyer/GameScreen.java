package rectangledestroyer;

import base_B.BaseActor;
import base_B.BaseGame;
import base_B.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class GameScreen extends BaseScreen {

    private Paddle paddle;
    private Ball ball;

    private Brick baseBrick;
    private ArrayList<Brick> brickList;

    private Powerup basePowerup;
    private ArrayList<Powerup> powerupList;

    private ArrayList<BaseActor> removeList;

    //game world dimensions
    final int mapWidth = 800;
    final int mapHeight = 600;

    private float audioVolume = 0.5f;
    private Sound breakSound;
    private Sound hitSound;
    private Sound powerupSound;

    private Label stateLabel;
    private Label clickLabel;

    private boolean lose;

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        paddle = new Paddle();
        Texture paddleTex = new Texture(Gdx.files.internal("assets/chapter6/rectangledestroyer/paddle.png"));
        paddleTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        paddle.setTexture(paddleTex);
        mainStage.addActor(paddle);

        baseBrick = new Brick();
        Texture brickTex = new Texture(Gdx.files.internal("assets/chapter6/rectangledestroyer/brick-gray.png"));
        baseBrick.setTexture(brickTex);
        baseBrick.setOriginCenter();

        brickList = new ArrayList<>();

        ball = new Ball();
        Texture ballTex = new Texture(Gdx.files.internal("assets/chapter6/rectangledestroyer/ball.png"));
        ball.storeAnimation("default", ballTex);
        ball.setPosition(400,200);
        ball.setVelocityAS(30,300);
        ball.setAccelerationXY(0, -10);
        mainStage.addActor(ball);

        basePowerup = new Powerup();
        basePowerup.setVelocityXY(0, -100);
        basePowerup.storeAnimation("paddle-expand", new Texture(Gdx.files.internal("assets/chapter6/rectangledestroyer/paddle-expand.png")));
        basePowerup.storeAnimation("paddle-shrink", new Texture(Gdx.files.internal("assets/chapter6/rectangledestroyer/paddle-shrink.png")));
        basePowerup.setOriginCenter();

        powerupList = new ArrayList<>();
        removeList = new ArrayList<>();

        Color[] colorArray = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PURPLE};
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 10; i++) {
                Brick brick = baseBrick.clone();
                brick.setPosition(8 + 80 * i, 500 - (24 + 16) * j);
                brick.setColor(colorArray[j]);
                brickList.add(brick);
                brick.setParentList(brickList);
                mainStage.addActor(brick);
            }
        }

        breakSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/rectangledestroyer/break.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/rectangledestroyer/hit.wav"));
        powerupSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/rectangledestroyer/powerup.wav"));


        setPaused(true);
        clickLabel = new Label("Click to Start", game.skin, "labelStyle");
        clickLabel.setFontScale(2.5f);
        clickLabel.setColor(Color.ORANGE);
        clickLabel.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.alpha(0,1.5f),
                                Actions.alpha(1,1.5f)
                        )
                )
        );

        stateLabel = new Label("", game.skin, "labelStyle");
        stateLabel.setFontScale(2.5f);
        lose = false;

        uiTable.add(stateLabel);
        uiTable.row();
        uiTable.add(clickLabel);
    }

    @Override
    public void update(float dt) {
        paddle.setPosition(Gdx.input.getX() - paddle.getWidth() / 2, 32);

        if (paddle.getX() < 0 ) {
            paddle.setX(0);
        }

        if (paddle.getX() + paddle.getWidth() > mapWidth) {
            paddle.setX(mapWidth - paddle.getWidth());
        }

        if (ball.getX() < 0) {
            ball.setX(0);
            ball.multVelocityX(-1);
        }

        if (ball.getX() + ball.getWidth() > mapWidth) {
            ball.setX(mapWidth - ball.getWidth());
            ball.multVelocityX(-1);
        }

        if (ball.getY() < 0) {
            stateLabel.setText("You Lose");
            stateLabel.setColor(Color.RED);
            ball.destroy();
        }

        if (ball.getY() + ball.getHeight() > mapHeight) {
            ball.setY(mapHeight - ball.getHeight());
            ball.multVelocityY(-1);
        }

        if (ball.overlaps(paddle,true)) {
            hitSound.play(audioVolume);
        }

        removeList.clear();

        for (Brick br : brickList) {

            if (ball.overlaps(br,true)) {
                removeList.add(br);
                if (Math.random() < 0.20) {
                    Powerup pow = basePowerup.clone();
                    pow.randomize();
                    pow.moveToOrigin(br);

                    pow.setScale(0,0);
                    pow.addAction(Actions.scaleTo(1,1,0.5f));
                    powerupList.add(pow);
                    pow.setParentList(powerupList);
                    mainStage.addActor(pow);
                }

                breakSound.play(audioVolume);
            }
        }

        for (Powerup pow: powerupList) {
            if (pow.overlaps(paddle)) {
                String powName = pow.getAnimationName();
                if (powName.equals("paddle-expand") && paddle.getWidth() < 256) {
                    paddle.addAction(Actions.sizeBy(32,0,0.5f));
                } else if (powName.equals("paddle-shrink") && paddle.getWidth() > 64) {
                    paddle.addAction(Actions.sizeBy(-32,0,0.5f));
                }

                removeList.add(pow);
                powerupSound.play(audioVolume);
            }
        }

        if (brickList.size() == 0 && !lose) {
            stateLabel.setText("You Win");
            stateLabel.setColor(Color.GREEN);
        }

        for (BaseActor b: removeList) {
            b.destroy();
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isPaused()) {
            uiTable.removeActor(clickLabel);
            setPaused(false);
        }
        return false;
    }
}
