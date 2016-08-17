package demo_B;

import base.BaseGame;
import base.BaseScreen;
import base.util.ModelUtils;
import base3d.BaseActor3D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/**
 * Created by robertoguazon on 15/08/2016.
 */
public class DemoScreen extends BaseScreen {

    BaseActor3D player;

    public DemoScreen(BaseGame g) {
        super(g);
    }

    @Override
    public void create() {
        BaseActor3D screen = new BaseActor3D();
        Texture screenTex = new Texture(Gdx.files.internal("assets/chapter8/starfish-collector.png"),true);
        screenTex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ModelInstance screenInstance = ModelUtils.createBox(16,12,0.1f,screenTex,null);
        screen.setModelInstance(screenInstance);
        mainStage3D.addActor(screen);

        Texture texCrate = new Texture(Gdx.files.internal("assets/chapter8/crate.png"),true);
        BaseActor3D markerO = new BaseActor3D();
        ModelInstance modCrateO = ModelUtils.createBox(1,1,1, texCrate, Color.PURPLE);
        markerO.setModelInstance(modCrateO);
        markerO.setPosition(0,0,0);
        mainStage3D.addActor(markerO);

        BaseActor3D markerX = markerO.clone();
        markerX.setColor(Color.RED);
        markerX.setPosition(5,0,0);
        mainStage3D.addActor(markerX);

        BaseActor3D markerY = markerO.clone();
        markerY.setColor(Color.GREEN);
        markerY.setPosition(0,5,0);
        mainStage3D.addActor(markerY);

        BaseActor3D markerZ = markerO.clone();
        markerZ.setColor(Color.BLUE);
        markerZ.setPosition(0,0,5);
        mainStage3D.addActor(markerZ);

        player = new BaseActor3D();

        ModelInstance testModel = ModelUtils.createBox(1,1,1,texCrate,Color.YELLOW);
        player.setModelInstance(testModel);
        player.setPosition(0,1,8);
        mainStage3D.addActor(player);
        mainStage3D.setCameraPosition(3,4,10);
        mainStage3D.setCameraDirection(0,0,0);

    }

    @Override
    public void update(float dt) {
        float speed = 3.0f;
        float rotateSpeed = 45.0f;

        if (!(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                player.moveForward(speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                player.moveForward(-speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player.moveRight(-speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player.moveRight(speed * dt);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                player.moveUp(speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                player.moveUp(-speed * dt);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                mainStage3D.moveCameraForward(speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                mainStage3D.moveCameraForward(-speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                mainStage3D.moveCameraRight(-speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                mainStage3D.moveCameraRight(speed * dt);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                mainStage3D.moveCameraUp(speed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                mainStage3D.moveCameraUp(-speed * dt);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
                mainStage3D.turnCamera(-rotateSpeed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                mainStage3D.turnCamera(rotateSpeed * dt);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.T)) {
                mainStage3D.tiltCamera(rotateSpeed * dt);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.G)) {
                mainStage3D.tiltCamera(-rotateSpeed * dt);
            }
        }

    }
}
