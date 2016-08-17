package rectangledestroyer;

import base_B.BaseActor;
import com.badlogic.gdx.math.Rectangle;;

/**
 * Created by robertoguazon on 29/07/2016.
 */
public class Paddle extends BaseActor {

    public Paddle() {
        super();
    }

    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
