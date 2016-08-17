package jumpingjack;

import base_A.Box2DActor;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by robertoguazon on 09/08/2016.
 */
public class Coin extends Box2DActor {

    public Coin() {
        super();
    }

    @Override
    public void initializePhysics(World world) {
        setStatic();
        setShapeCircle();
        fixtureDef.isSensor = true;
        super.initializePhysics(world);
    }

    @Override
    public Coin clone() {
        Coin newbie = new Coin();
        newbie.copy(this);
        return newbie;
    }
}
