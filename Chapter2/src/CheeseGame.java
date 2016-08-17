import com.badlogic.gdx.Game;

/**
 * Created by robertoguazon on 03/07/2016.
 */
public class CheeseGame extends Game {

    @Override
    public void create() {
        CheeseMenu cheeseMenu = new CheeseMenu(this);
        setScreen(cheeseMenu);
    }

}
