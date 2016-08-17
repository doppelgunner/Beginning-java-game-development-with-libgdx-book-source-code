import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by robertoguazon on 26/06/2016.
 */
public class HelloWorldImage extends Game {

    private Texture texture;
    private SpriteBatch spriteBatch;

    private final String imagePath = "assets/images/icons/Doppelgunner_ghost_64.png";

    @Override
    public void create() {
        texture = new Texture(imagePath);
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        spriteBatch.draw(texture,0,0);

        spriteBatch.end();
    }
}
