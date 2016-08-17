import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by robertoguazon on 02/07/2016.
 */
public class CheesePlease3 extends Game {

    public Stage mainStage;
    private BaseActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;

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

        mousey = new BaseActor();
        mousey.setTexture(new Texture(Gdx.files.internal("assets/chapter2/mouse.png")));
        mousey.setPosition(20,20);
        mainStage.addActor(mousey);

        winText = new BaseActor();
        winText.setTexture(new Texture(Gdx.files.internal("assets/chapter2/you-win.png")));
        winText.setPosition(170,60);
        winText.setVisible(false);
        mainStage.addActor(winText);
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

        if (cheeseRectangle.overlaps(mouseyRectangle)) {
            winText.setVisible(true);
        }

        //draw graphics
        Gdx.gl.glClearColor(0.8f,0.8f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
    }



}
