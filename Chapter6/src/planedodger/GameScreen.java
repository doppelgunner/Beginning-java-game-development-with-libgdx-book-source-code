package planedodger;

import base_A.*;
import base_A.util.GameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 21/07/2016.
 */
public class GameScreen extends BaseScreen {
    private PhysicsActor player;

    private PhysicsActor baseEnemy;
    private ArrayList<PhysicsActor> enemyList;
    private float enemyTimer;
    private float enemySpeed;

    private PhysicsActor baseStar;
    private ArrayList<PhysicsActor> starList;
    private float starTimer;

    private AnimatedActor baseSparkle;
    private AnimatedActor baseExplosion;

    private ArrayList<BaseActor> removeList;
    private boolean gameOver;

    //game world dimensions
    final int mapWidth = 800;
    final int mapHeight = 600;

    public GameScreen(BaseGame game) {
        super(game);
    }

    private Label gameOverLabel;

    private float audioVolume;
    private Sound captureSound;
    private Sound explosionSound;

    private Label starsCollectedLabel;
    private int starsCollected;

    private Label elapsedTimeLabel;
    private float elapsedTime;

    private Label scoreLabel;
    private int score;

    private Label rankLabel;
    private char rank;

    private TextButton retryButton;

    private FPSLogger fpsLogger;

    private boolean cheat;

    private int startX;
    private int startY;

    @Override
    public void create() {

        mainStage.addActor(PlaneDodgerGame.background[0]);
        mainStage.addActor(PlaneDodgerGame.background[1]);

        mainStage.addActor(PlaneDodgerGame.ground[0]);
        mainStage.addActor(PlaneDodgerGame.ground[1]);

        //player
        player = new PhysicsActor();

        startX = 200;
        startY = 300;

        player.storeAnimation("default", game.skin.get("playerAnim", Animation.class));
        player.setPosition(startX,startY);
        player.setAccelerationXY(0, -600); //gravity
        player.setOriginCenter();
        player.setEllipseBoundary();
        mainStage.addActor(player);

        //baseStar
        baseStar = new PhysicsActor();
        Texture starTex = new Texture(Gdx.files.internal("assets/chapter6/planedodger/star.png"));
        starTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        baseStar.storeAnimation("default", starTex);
        baseStar.setVelocityXY(-200,0);
        baseStar.setOriginCenter();
        baseStar.setEllipseBoundary();

        starList = new ArrayList<>();
        starTimer = 0;

        //baseSparkle
        baseSparkle = new AnimatedActor();
        Animation sparkleAnim = GameUtils.parseSpriteSheet("assets/chapter6/planedodger/sparkle.png",
                8,8, 0.01f, Animation.PlayMode.NORMAL);
        baseSparkle.storeAnimation("default", sparkleAnim);
        baseSparkle.setWidth(64);
        baseSparkle.setHeight(64);
        baseSparkle.setOriginCenter();

        //init removeList
        removeList = new ArrayList<>();

        //baseEnemy
        baseEnemy = new PhysicsActor();
        Animation redAnim = GameUtils.parseImageFiles("assets/chapter6/planedodger/planeRed", ".png",
                3, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        baseEnemy.storeAnimation("default", redAnim);
        baseEnemy.setWidth(baseEnemy.getWidth() * 1.25f);
        baseEnemy.setHeight(baseEnemy.getWidth() * 1.25f);
        baseEnemy.setOriginCenter();
        baseEnemy.setEllipseBoundary();

        enemyTimer = 0;
        enemySpeed = -250;
        enemyList = new ArrayList<PhysicsActor>();

        //baseExplosion
        baseExplosion = new AnimatedActor();
        Animation explosionAnim = GameUtils.parseSpriteSheet("assets/chapter6/planedodger/explosion.png",
                6,6,0.03f, Animation.PlayMode.NORMAL);
        baseExplosion.storeAnimation("default", explosionAnim);
        baseExplosion.setWidth(96);
        baseExplosion.setHeight(96);
        baseExplosion.setOriginCenter();

        gameOver = false;

        gameOverLabel = new Label("Game Over", game.skin, "labelStyle");
        gameOverLabel.setVisible(false);

        audioVolume = 0.5f;
        captureSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/planedodger/capture.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/planedodger/explosion.wav"));

        starsCollectedLabel = new Label("Stars collected: 0", game.skin, "labelStyle");
        starsCollectedLabel.setFontScale(0.75f);

        starsCollected = 0;
        score = 0;
        elapsedTime = 0;
        rank = 'E';

        elapsedTimeLabel = new Label("Time: 0s", game.skin, "labelStyle");
        elapsedTimeLabel.setFontScale(0.5f);
        elapsedTimeLabel.setVisible(false);

        scoreLabel = new Label("Score: 0", game.skin, "labelStyle");
        scoreLabel.setFontScale(0.5f);
        scoreLabel.setVisible(false);

        rankLabel = new Label("Rank: E", game.skin, "labelStyle");
        rankLabel.setFontScale(0.5f);
        rankLabel.setVisible(false);

        retryButton = new TextButton("Retry",game.skin,"textButtonStyle");
        retryButton.setVisible(false);
        retryButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                reset();
            }
        });

        fpsLogger = new FPSLogger();
        cheat = false;

        //add gui
        uiTable.add(starsCollectedLabel).expandX().left().padLeft(20).padTop(20);

        uiTable.row();
        uiTable.add().expandY();

        uiTable.row();
        uiTable.add(gameOverLabel);

        uiTable.row();
        uiTable.add(elapsedTimeLabel);

        uiTable.row();
        uiTable.add(scoreLabel);

        uiTable.row();
        uiTable.add(rankLabel);

        uiTable.row();
        uiTable.add().expandY();

        uiTable.row();
        uiTable.add(retryButton).padBottom(20);

    }

    @Override
    public void update(float dt) {
        fpsLogger.log();
        elapsedTime += dt;
        Gdx.app.debug("player", "x = " + player.getX() + ", y = " + player.getY());
        Gdx.app.debug("player", "speed = " + player.getSpeed());

        //manage background objects
        for (int i = 0; i < 2; i++) {
            PhysicsActor bg = PlaneDodgerGame.background[i];
            if (bg.getX() + bg.getWidth() < 0) {
                bg.setX(bg.getX() + 2 * bg.getWidth());
            }

            PhysicsActor gr = PlaneDodgerGame.ground[i];
            if (gr.getX() + gr.getWidth() < 0) {
                gr.setX(gr.getX() + 2 * gr.getWidth());
            }
        }

        //gameover ? - if true then exit update
        if (gameOver) {
            return;
        }

        /*
        //continuous event
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.addVelocityXY(0,25);
        }
        */

        //enemies
        enemyTimer += dt;
        if (enemyTimer > 3) {
            enemyTimer = 0;
            if (enemySpeed > -800) {
                enemySpeed -= 15;
            }
            PhysicsActor enemy = baseEnemy.clone();
            enemy.setPosition(900, MathUtils.random(100,500));
            enemy.setVelocityXY(enemySpeed, 0);

            enemy.setRotation(10);
            enemy.addAction(
                    Actions.forever(
                            Actions.sequence(
                                    Actions.rotateBy(-20,1),
                                    Actions.rotateBy(20,1)
                            )
                    )
            );
            enemyList.add(enemy);
            enemy.setParentList(enemyList);
            mainStage.addActor(enemy);
        }

        //collision detection
        if (player.getY() > mapHeight - player.getHeight()) {
            player.setVelocityXY(0,0);
            player.setY(mapHeight - player.getHeight());
        }

        //fix error - if screen is resized or moved
        if (player.getY() < 0 - player.getHeight()) {
            player.setY(PlaneDodgerGame.ground[0].getHeight());
        }

        //if cheat mode on
        if (cheat) {
            if (player.getX() < startX) {
                player.setVelocityXY(250,0);
            } else if (player.getX() > startX) {
                player.setX(startX);
                player.setVelocityXY(0,0);
            }
        }

        for (int i = 0; i < 2; i++) {
            PhysicsActor gr = PlaneDodgerGame.ground[i];
            if (player.overlaps(gr, true)) {
                player.setVelocityXY(0,0);
               }
        }


        starTimer += dt;
        if (starTimer > 1) {
            starTimer = 0;
            PhysicsActor star = baseStar.clone();
            star.setPosition(900, MathUtils.random(100,500));
            starList.add(star);
            star.setParentList(starList);
            mainStage.addActor(star);
        }

        //reset removeList
        removeList.clear();

        //collision star
        for (PhysicsActor star: starList) {

            //star is beyond the left screen
            if (star.getX() + star.getWidth() < 0) {
                removeList.add(star);
            }

            //player collides with the star
            if (player.overlaps(star, false)) {
                removeList.add(star);
                AnimatedActor sparkle = baseSparkle.clone();
                sparkle.moveToOrigin(star);
                sparkle.addAction(Actions.sequence(
                        Actions.delay(0.64f),
                        Actions.removeActor()
                ));
                mainStage.addActor(sparkle);

                starsCollected++;
                starsCollectedLabel.setText("Stars collected: " + starsCollected);

                captureSound.play(audioVolume);
            }
        }

        for (PhysicsActor enemy: enemyList) {
            //enemy is beyond left screen
            if (enemy.getX() + enemy.getWidth() < 0) {
                removeList.add(enemy);
            }

            //player collides with enemy
            if (player.overlaps(enemy, cheat)) {
                if (cheat) return;

                AnimatedActor explosion = baseExplosion.clone();
                explosion.moveToOrigin(player);
                explosion.addAction(Actions.sequence(
                        Actions.delay(1.08f),
                        Actions.removeActor()
                ));

                mainStage.addActor(explosion);
                removeList.add(player);
                gameOver = true;

                //set labels
                gameOverLabel.setVisible(true);

                score = computeScore(starsCollected, elapsedTime);

                elapsedTimeLabel.setText(String.format("Time: %.2fs", elapsedTime));
                scoreLabel.setText(String.format("Score: %,d", score));

                rank = computeRank(starsCollected, elapsedTime);
                rankLabel.setText("Rank: " + rank);

                elapsedTimeLabel.setVisible(true);
                scoreLabel.setVisible(true);
                rankLabel.setVisible(true);
                retryButton.setVisible(true);

                explosionSound.play(audioVolume);
            }
        }

        for (BaseActor ba: removeList) {
            ba.destroy();
        }

    }


    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.SPACE) {
            player.setVelocityXY(0,300);
        }

        if (keyCode == Input.Keys.C) {
            Gdx.app.log("game", "cheat mode on... enjoy!");
            cheat = !cheat;
        }

        return false;
    }

    private int computeScore(int stars, float seconds) {
        return (int)(100 * seconds + 200 * stars);
    }

    private char computeRank(int stars, float seconds) {
        int n = (int)(seconds + stars);
        if (n <= 20) {
            return 'E';
        } else if (n <= 40) {
            return 'D';
        } else if (n <= 60) {
            return 'C';
        } else if (n <= 80) {
            return 'B';
        } else if (n <= 100) {
            return 'A';
        }

        return 'S';
    }

    private void reset() {
        for (PhysicsActor star: starList) {
            removeList.add(star);
        }

        for (PhysicsActor enemy: enemyList) {
            removeList.add(enemy);
        }

        for (BaseActor actor: removeList) {
            actor.destroy();
        }

        enemyList.clear();
        removeList.clear();
        starList.clear();

        player = new PhysicsActor();

        player.storeAnimation("default", game.skin.get("playerAnim", Animation.class));
        player.setPosition(startX,startY);
        player.setAccelerationXY(0, -600); //gravity
        player.setOriginCenter();
        player.setEllipseBoundary();
        mainStage.addActor(player);

        starTimer = 0;
        enemyTimer = 0;
        enemySpeed = -250;
        gameOver = false;
        gameOverLabel.setVisible(false);
        audioVolume = 0.5f;
        starsCollected = 0;
        score = 0;
        elapsedTime = 0;
        rank = 'E';
        elapsedTimeLabel.setVisible(false);
        scoreLabel.setVisible(false);
        rankLabel.setVisible(false);
        retryButton.setVisible(false);
    }
}
