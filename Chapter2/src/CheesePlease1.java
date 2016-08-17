import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by robertoguazon on 27/06/2016.
 */
public class CheesePlease1 extends Game {

    private SpriteBatch batch;

    private Texture mouseyTexture;
    private float mouseyX;
    private float mouseyY;

    private Texture cheeseTexture;
    private float cheeseX;
    private float cheeseY;

    private Texture floorTexture;
    private Texture winMessage;

    private boolean win;

    @Override
    public void create() {
        batch = new SpriteBatch();

        mouseyTexture = new Texture("assets/chapter2/mouse.png");
        mouseyX = 20;
        mouseyY = 20;

        cheeseTexture = new Texture("assets/chapter2/cheese.png");
        cheeseX = 400;
        cheeseY = 300;

        floorTexture = new Texture("assets/chapter2/tiles.jpg");
        winMessage = new Texture("assets/chapter2/you-win.png");

        win = false;
    }

    @Override
    public void render() {
        //check user input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
            mouseyX--;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
            mouseyX++;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
            mouseyY++;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
            mouseyY--;


        //check if mousey overlaps with cheese
        if (!win) {
            win = checkCollision();
        }

        //clear screen and draw graphics
        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(floorTexture,0,0);
        batch.draw(cheeseTexture,cheeseX,cheeseY);
        batch.draw(mouseyTexture,mouseyX,mouseyY);

        if (win) {
            batch.draw(winMessage,170,60);
        }

        batch.end();
    }

    private boolean checkCollision() {

        if ( (mouseyX + mouseyTexture.getWidth() < cheeseX) || (mouseyX > cheeseX + cheeseTexture.getWidth()) ) return false;
        if ( (mouseyY + mouseyTexture.getHeight() < cheeseY) || (mouseyY > cheeseY + cheeseTexture.getHeight()) ) return false;

        return true;
    }


}
