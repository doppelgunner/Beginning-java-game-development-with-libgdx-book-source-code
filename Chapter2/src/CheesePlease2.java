import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by robertoguazon on 27/06/2016.
 */
public class CheesePlease2 extends Game {

    private SpriteBatch batch;
    private Sprite mouseySprite;
    private Sprite cheeseSprite;
    private Sprite floorSprite;
    private Sprite winTextSprite;
    private boolean win;

    @Override
    public void create() {
        batch = new SpriteBatch();

        mouseySprite = new Sprite(new Texture("assets/chapter2/mouse.png"));
        mouseySprite.setPosition(20,20);

        cheeseSprite = new Sprite(new Texture("assets/chapter2/cheese.png"));
        cheeseSprite.setPosition(400,300);

        floorSprite = new Sprite(new Texture("assets/chapter2/tiles.jpg"));
        floorSprite.setPosition(0,0);

        winTextSprite = new Sprite(new Texture("assets/chapter2/you-win.png"));
        winTextSprite.setPosition(170,60);

        win = false;
    }

    @Override
    public void render() {
        //process Input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            mouseySprite.translateX(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            mouseySprite.translateX(1);
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            mouseySprite.translateY(1);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            mouseySprite.translateY(-1);

        //check win condition
        Rectangle cheeseRectangle = cheeseSprite.getBoundingRectangle();
        Rectangle mouseyRectangle = mouseySprite.getBoundingRectangle();

        if (!win && cheeseRectangle.overlaps(mouseyRectangle)) {
            win = true;
        }

        //draw graphics
        Gdx.gl.glClearColor(0.8f,0.8f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        floorSprite.draw(batch);
        cheeseSprite.draw(batch);
        mouseySprite.draw(batch);

        if (win) {
            winTextSprite.draw(batch);
        }

        batch.end();
    }


}
