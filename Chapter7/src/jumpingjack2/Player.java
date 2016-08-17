package jumpingjack2;

import base_A.Box2DActor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by robertoguazon on 09/08/2016.
 */
public class Player extends Box2DActor {

    public int groundCount;

    public Player() {
        super();
        groundCount = 0;
    }

    public void adjustGroundCount(int i) {
        groundCount += i;
    }

    public boolean isOnGround() {
        return groundCount > 0;
    }

    @Override
    public void initializePhysics(World world) {
        super.initializePhysics(world);

        FixtureDef bottomSensor = new FixtureDef();
        bottomSensor.isSensor = true;
        PolygonShape sensorShape = new PolygonShape();

        //center coordinates of sensor box - offset from body center
        float x = 0;
        float y = -20;

        //dimension of sensor box
        float w = getWidth() - 8;
        float h = getHeight();
        sensorShape.setAsBox(w / 200, h / 200, new Vector2(x / 200, y / 200), 0);
        bottomSensor.shape = sensorShape;

        //create and attach this new feature
        Fixture bottomFixture = body.createFixture(bottomSensor);
        bottomFixture.setUserData("bottom");
    }
}
