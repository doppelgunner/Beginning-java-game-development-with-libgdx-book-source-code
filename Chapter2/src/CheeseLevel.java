import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * Created by robertoguazon on 02/07/2016.
 */
public class CheeseLevel implements Screen {
    private Game game;

    private Stage mainStage;
    private Stage uiStage;

    private AnimatedActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;

    private boolean win;

    private Action fadeInColorCycleForever;
    private Action spinShrinkFadeOut;

    private float timeElapsed;
    private Label timeLabel;

    //game world dimensions
    private final int mapWidth = 800;
    private final int mapHeight = 800;

    //window dimensions
    private final int viewWidth = 640;
    private final int viewHeight = 480;

    public CheeseLevel(Game game) {
        this.game = game;
        create();
    }

    public void create() {
        mainStage = new Stage();

        floor = new BaseActor();
        floor.setTexture(new Texture(Gdx.files.internal("assets/chapter2/tiles-800-800.jpg")));
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

        //time
        timeElapsed = 0;
        BitmapFont font = new BitmapFont();
        String text = "Time: 0";
        Label.LabelStyle style = new Label.LabelStyle(font,Color.NAVY);
        timeLabel = new Label(text, style);
        timeLabel.setFontScale(2);
        timeLabel.setPosition(500,440);

        //ui group layer
        uiStage = new Stage();
        uiStage.addActor(winText);
        uiStage.addActor(timeLabel);
    }

    @Override
    public void render(float dt) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            game.setScreen(new CheeseMenu(game));
        }

        //update
        mainStage.act(dt);

        mousey.setX(MathUtils.clamp(mousey.getX(),0,mapWidth - mousey.getWidth()));
        mousey.setY(MathUtils.clamp(mousey.getY(),0,mapHeight - mousey.getHeight()));

        uiStage.act(dt);

        //check win condition: mousey must be overlapping cheese
        Rectangle cheeseRectangle = cheese.getBoundingRectangle();
        Rectangle mouseyRectangle = mousey.getBoundingRectangle();

        if (!win && cheeseRectangle.overlaps(mouseyRectangle)) {
            winText.setVisible(true);
            win = true;

            cheese.addAction(spinShrinkFadeOut);
            winText.addAction(fadeInColorCycleForever);
        }

        if (!win) {
            timeElapsed += dt;
            timeLabel.setText("Time: " + (int)timeElapsed);
        }

        //draw graphics
        Gdx.gl.glClearColor(0.8f,0.8f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera adjustment
        Camera cam = mainStage.getCamera();
        //center camera on player
        cam.position.set(mousey.getX() + mousey.getOriginX(),mousey.getY() + mousey.getOriginY(),0);

        //bound camera to layout
        cam.position.x = MathUtils.clamp(cam.position.x, viewWidth / 2, mapWidth - viewWidth / 2);
        cam.position.y = MathUtils.clamp(cam.position.y, viewHeight / 2, mapHeight - viewHeight / 2);
        cam.update();

        mainStage.draw();
        uiStage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() {}
    @Override public void show() {}
    @Override public void hide() {}



}
