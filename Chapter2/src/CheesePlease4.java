import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;

/**
 * Created by robertoguazon on 02/07/2016.
 */
public class CheesePlease4 extends Game {


    public Stage mainStage;
    private AnimatedActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;

    private boolean win;

    private Action fadeInColorCycleForever;
    private Action spinShrinkFadeOut;


    @Override
    public void create() {
        mainStage = new Stage();

        floor = new BaseActor();
        floor.setTexture(new Texture(Gdx.files.internal("assets/chapter2/tiles.jpg")));
        floor.setPosition(0,0);
        mainStage.addActor(floor);

        cheese = new BaseActor();
        cheese.setTexture(new Texture(Gdx.files.internal("assets/chapter2/cheese.png")));
        cheese.setPosition(400,300);
        mainStage.addActor(cheese);

        cheese.setOrigin(cheese.getWidth() / 2, cheese.getHeight() / 2);


        mousey = new AnimatedActor();
        mousey.setTexture(new Texture(Gdx.files.internal("assets/chapter2/mouse.png")));
        mousey.setPosition(20,20);
        mousey.setOrigin(mousey.getWidth() / 2, mousey.getHeight() / 2);
        mainStage.addActor(mousey);

        winText = new BaseActor();
        winText.setTexture(new Texture(Gdx.files.internal("assets/chapter2/you-win.png")));
        winText.setPosition(170,60);
        winText.setVisible(false);
        mainStage.addActor(winText);



        win = false;

        spinShrinkFadeOut = Actions.parallel(
                Actions.alpha(1),
                Actions.rotateBy(360,1),
                Actions.scaleTo(0,0,1),
                Actions.fadeOut(1)
        );

        fadeInColorCycleForever = Actions.sequence(
                Actions.alpha(0),
                Actions.show(),
                Actions.fadeIn(2),
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(1,0,0,1), 1),
                                Actions.color(new Color(0,0,1,1), 1)
                        )
                )
        );

        //add anim to mousey
        TextureRegion[] frames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            String fileName = "assets/chapter2/mouse" + i + ".png";
            Texture tex = new Texture(Gdx.files.internal(fileName));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[i] = new TextureRegion(tex);
        }

        Array<TextureRegion> framesArray = new Array<TextureRegion>(frames);
        Animation anim = new Animation(0.1f, framesArray, Animation.PlayMode.LOOP_PINGPONG);
        mousey.setAnimation(anim);
    }

    @Override
    public void render() {
        //process input
        mousey.velocityX = 0;
        mousey.velocityY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            mousey.velocityX -= 100;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            mousey.velocityX += 100;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            mousey.velocityY += 100;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            mousey.velocityY -= 100;
        }

        //update
        float dt = Gdx.graphics.getDeltaTime();
        mainStage.act(dt);

        //check win condition: mousey must be overlapping cheese
        Rectangle cheeseRectangle = cheese.getBoundingRectangle();
        Rectangle mouseyRectangle = mousey.getBoundingRectangle();

        if (!win && cheeseRectangle.overlaps(mouseyRectangle)) {
            winText.setVisible(true);
            win = true;

            cheese.addAction(spinShrinkFadeOut);
            winText.addAction(fadeInColorCycleForever);
        }

        //draw graphics
        Gdx.gl.glClearColor(0.8f,0.8f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
    }

}
