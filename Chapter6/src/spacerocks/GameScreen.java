package spacerocks;

import base_A.*;
import base_A.util.GameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 17/07/2016.
 */
public class GameScreen extends BaseScreen {

    private BaseActor background;
    private PhysicsActor spaceship;
    private BaseActor rocketfire;

    //create "base" objects to clone later
    private PhysicsActor baseLaser;
    private AnimatedActor baseExplosion;

    private ArrayList<PhysicsActor> laserList;
    private ArrayList<PhysicsActor> rockList;
    private ArrayList<BaseActor> removeList;

    private int laserLimit;

    private boolean alive;

    //game world dimensions
    final int mapWidth = 800;
    final int mapHeight = 600;

    private boolean hittable;
    private float immuneTime;
    private float elapsedTime;

    private float audioVolume;
    private Sound laserSound;
    private Sound explosionSound;

    private boolean win;

    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void create() {
        alive = true;

        background = new BaseActor();
        background.setTexture(game.skin.get("backgroundTexture", Texture.class));
        background.setPosition(0,0);
        mainStage.addActor(background);

        spaceship = new PhysicsActor();
        Texture shipTex = new Texture("assets/chapter6/spacerocks/spaceship.png");
        shipTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceship.storeAnimation("default", shipTex);

        spaceship.setPosition(400,300);
        spaceship.setOriginCenter();
        spaceship.setMaxSpeed(200);
        spaceship.setDeceleration(20);
        spaceship.setEllipseBoundary();
        mainStage.addActor(spaceship);

        rocketfire = new BaseActor();
        rocketfire.setPosition(-28,24);
        Texture fireTex = new Texture("assets/chapter6/spacerocks/fire.png");
        fireTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        rocketfire.setTexture(fireTex);
        spaceship.addActor(rocketfire);

        baseLaser = new PhysicsActor();
        Texture laserTex = new Texture("assets/chapter6/spacerocks/laser.png");
        laserTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        baseLaser.storeAnimation("default", laserTex);

        baseLaser.setMaxSpeed(400);
        baseLaser.setDeceleration(0);
        baseLaser.setEllipseBoundary();
        baseLaser.setOriginCenter();
        baseLaser.setAutoAngle(true);

        laserList = new ArrayList<>();
        removeList = new ArrayList<>();
        rockList = new ArrayList<>();

        int numRocks = 6;
        for (int n = 0; n < numRocks; n++) {
            PhysicsActor rock = new PhysicsActor();
            String filename = "assets/chapter6/spacerocks/rock" + (n % 4) + ".png";
            Texture rockTex = new Texture(filename);
            rockTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            rock.storeAnimation("default", rockTex);

            rock.setPosition(800 * MathUtils.random(), 600 * MathUtils.random());
            rock.setOriginCenter();
            rock.setEllipseBoundary();
            rock.setAutoAngle(false);

            float speedUp = MathUtils.random(0.0f,1.0f);
            rock.setVelocityAS(360 * MathUtils.random(), 75 + 50 * speedUp);
            rock.addAction(
                    Actions.forever(
                            Actions.rotateBy(360, 2 - speedUp)
                    )
            );
            mainStage.addActor(rock);
            rockList.add(rock);
            rock.setParentList(rockList);
        }

        //explosion
        baseExplosion = new AnimatedActor();
        Animation explosionAnim = GameUtils.parseSpriteSheet("assets/chapter6/spacerocks/explosion.png",6,6,0.03f, Animation.PlayMode.NORMAL);
        baseExplosion.storeAnimation("default", explosionAnim);
        baseExplosion.setWidth(96);
        baseExplosion.setHeight(96);
        baseExplosion.setOriginCenter();

        hittable = false;
        immuneTime = 5;

        audioVolume = 0.5f;
        laserSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/spacerocks/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/chapter6/spacerocks/explosion.wav"));

        //addImmuneAction
        addImmunity();


        laserLimit = 4;
        win = false;
    }

    @Override
    public void update(float dt) {
        spaceship.setAccelerationXY(0,0);

        if (alive) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                spaceship.rotateBy(180 * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                spaceship.rotateBy(-180 * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                spaceship.addAccelerationAS(spaceship.getRotation(), 100);
            }
        }

        rocketfire.setVisible(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W));
        wrapAround(spaceship);

        removeList.clear();
        for (PhysicsActor laser : laserList) {
            wrapAround(laser);
            if (!laser.isVisible()) {
                removeList.add(laser);
            }

            //check if the laser hits any rock
            for (PhysicsActor rock : rockList) {
                if (laser.overlaps(rock, false)) {
                    removeList.add(laser);
                    removeList.add(rock);

                    AnimatedActor explosion = baseExplosion.clone();
                    explosion.moveToOrigin(rock);
                    mainStage.addActor(explosion);
                    explosion.addAction(
                            Actions.sequence(
                                    Actions.delay(1.08f),
                                    Actions.removeActor()
                            )
                    );

                    explosionSound.play(audioVolume);
                }
            }
        }

        //check if the spaceship collides with any rock
        if (hittable) {
            checkShipCollision();
        } else {
            if (elapsedTime > immuneTime) {
                hittable = true;
                elapsedTime = 0;

                //clear actions
                spaceship.clearActions();
                rocketfire.clearActions();
            } else {
                elapsedTime+=dt;
            }
        }

        for (BaseActor ba : removeList) {
            ba.destroy();
            Gdx.app.debug("destroyed", "" + ba);
        }

        for (PhysicsActor rock : rockList) {
            wrapAround(rock);
        }

        if (!alive) {
            createLabel("You lose!");
        } else if (!win) {
            if (rockList.size() == 0) {
                win = true;
                createLabel("You Win!");
            }
        }

    }

    public void wrapAround(BaseActor ba) {
        if (ba.getX() + ba.getWidth() < 0) {
            ba.setX(mapWidth);
        }
        if (ba.getX() > mapWidth) {
            ba.setX(-ba.getWidth());
        }
        if (ba.getY() + ba.getHeight() < 0) {
            ba.setY(mapHeight);
        }
        if (ba.getY() > mapHeight) {
            ba.setY(-ba.getHeight());
        }


    }

    @Override
    public boolean keyDown(int keyCode) {

        if (laserList.size() < laserLimit) {
            if (keyCode == Input.Keys.SPACE && alive) {
                PhysicsActor laser = baseLaser.clone();
                laser.moveToOrigin(spaceship);
                laser.setVelocityAS(spaceship.getRotation(), 400);
                laserList.add(laser);
                laser.setParentList(laserList);
                mainStage.addActor(laser);

                laser.addAction(
                        Actions.sequence(
                                Actions.delay(2),
                                Actions.fadeOut(0.5f),
                                Actions.visible(false)
                        )
                );

                laserSound.play(audioVolume);
            }
        }

        return false;
    }


    private void checkShipCollision() {
            if (rockList.size() > 0 && alive) {
                for (PhysicsActor rock : rockList) {
                    if (spaceship.overlaps(rock, false)) {
                        removeList.add(spaceship);
                        removeList.add(rock);
                        alive = false;

                        Action explosionAction = Actions.sequence(
                                Actions.delay(1.08f),
                                Actions.removeActor()
                        );

                        AnimatedActor explosion1 = baseExplosion.clone();
                        explosion1.moveToOrigin(spaceship);
                        mainStage.addActor(explosion1);
                        explosion1.addAction(explosionAction);

                        AnimatedActor explosion2 = baseExplosion.clone();
                        explosion2.moveToOrigin(rock);
                        mainStage.addActor(explosion2);
                        explosion2.addAction(explosionAction);

                        Gdx.app.log("collision", "spaceship x rock");
                        explosionSound.play(audioVolume);
                        break; //break out of loop
                    }
                }
            }
    }

    private void addImmunity() {

        spaceship.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.alpha(1f),
                                Actions.alpha(0.2f,0.5f),
                                Actions.alpha(1f,0.5f)
                        )
                )
        );
        rocketfire.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.alpha(1f),
                                Actions.alpha(0.2f,0.5f),
                                Actions.alpha(1f,0.5f)
                        )
                )
        );
        hittable = false;
    }

    private void createLabel(String text) {
        Label label = new Label(text, game.skin, "labelStyle");
        float labelWidth = label.getWidth();
        float labelHeight = label.getHeight();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float x = screenWidth / 2 - labelWidth / 2;
        float y = screenHeight / 2 - labelHeight / 2;
        label.setPosition(x,y);
        uiStage.addActor(label);

        Gdx.app.log("screen", "width = " + screenWidth + ", height = " + screenHeight);
        Gdx.app.log("label", "width = " + labelWidth + ", height = " + labelHeight);
        Gdx.app.log("label", "x = " + x + ", y = " + y);
    }

    @Override
    public void dispose() {
        laserSound.dispose();
        explosionSound.dispose();
    }
}
