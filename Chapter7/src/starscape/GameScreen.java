package starscape;

import base_A.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import javafx.scene.layout.Background;

/**
 * Created by robertoguazon on 07/08/2016.
 */
public class GameScreen extends BaseScreen {

    private PhysicsActor spaceship;
    private ParticleActor thruster;
    private ParticleActor baseExplosion;

    private float mapWidth = 800;
    private float mapHeight = 600;

    public GameScreen(BaseGame game) {
        super(game);

    }

    @Override
    public void create() {
        BaseActor background = new BaseActor();
        background.setTexture(new Texture(Gdx.files.internal("assets/chapter7/starscape/space.png")));
        background.setPosition(0,0);
        mainStage.addActor(background);

        spaceship = new PhysicsActor();
        Texture shipTex = new Texture(Gdx.files.internal("assets/chapter7/starscape/spaceship.png"));
        shipTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceship.storeAnimation("default", shipTex);
        spaceship.setPosition(400,300);
        spaceship.setOriginCenter();
        spaceship.setMaxSpeed(200);
        spaceship.setDeceleration(20);
        mainStage.addActor(spaceship);

        thruster = new ParticleActor();
        thruster.load("assets/chapter7/starscape/thruster.pfx", "assets/chapter7/starscape");
        BaseActor thrusterAdjuster = new BaseActor();
        thrusterAdjuster.setTexture(new Texture(Gdx.files.internal("assets/chapter7/starscape/blank.png")));
        thrusterAdjuster.addActor(thruster);
        thrusterAdjuster.setPosition(0,32);
        thrusterAdjuster.setRotation(90);
        thrusterAdjuster.setScale(0.25f);
        thruster.start();
        spaceship.addActor(thrusterAdjuster);

        baseExplosion = new ParticleActor();
        baseExplosion.load("assets/chapter7/starscape/explosion.pfx", "assets/chapter7/starscape");
    }

    @Override
    public void update(float dt) {
        spaceship.setAccelerationXY(0,0);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            spaceship.rotateBy(180 * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            spaceship.rotateBy(-180 * dt);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            spaceship.addAccelerationAS(spaceship.getRotation(), 100);
            thruster.start();
        } else {
            thruster.stop();
        }

        if (spaceship.getX() > mapWidth) {
            spaceship.setX(0 - spaceship.getWidth());
        } else if (spaceship.getX() + spaceship.getWidth() < 0) {
            spaceship.setX(mapWidth);
        }

        if (spaceship.getY() > mapHeight) {
            spaceship.setY(0 - spaceship.getHeight());
        } else if (spaceship.getY() + spaceship.getHeight() < 0) {
            spaceship.setY(mapHeight);
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.P) {
            togglePaused();
        }

        if (keycode == Input.Keys.R) {
            game.setScreen(new GameScreen(game));
        }

        if (keycode == Input.Keys.SPACE) {
            ParticleActor explosion = baseExplosion.clone();
            explosion.setPosition(MathUtils.random(800), MathUtils.random(600));
            explosion.start();
            mainStage.addActor(explosion);
        }

        return false;
    }
}
